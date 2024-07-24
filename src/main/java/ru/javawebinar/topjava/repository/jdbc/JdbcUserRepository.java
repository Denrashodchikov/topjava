package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.ValidationUtil;

import java.util.*;

@Repository
@Transactional(readOnly = true)
public class JdbcUserRepository implements UserRepository {

    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;

    @Autowired
    public JdbcUserRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertUser = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Transactional
    @Override
    public User save(User user) {
        ValidationUtil.validate(user);
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);
        Set<Role> roles = user.getRoles();
        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            user.setId(newKey.intValue());
        } else if (namedParameterJdbcTemplate.update("""
                   UPDATE users SET name=:name, email=:email, password=:password, 
                   registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id
                """, parameterSource) == 0) {
            return null;
        }
        jdbcTemplate.update("DELETE FROM user_role WHERE user_id=?", user.id());
        insertRolesToUser(user, roles);
        return user;
    }

    private void insertRolesToUser(User user, Set<Role> roles) {
        jdbcTemplate.batchUpdate(
                "INSERT INTO user_role (user_id, role) VALUES(?,?)",
                roles,
                roles.size(),
                (ps, role) -> {
                    ps.setInt(1, user.id());
                    ps.setString(2, role.name());
                });
    }

    @Transactional
    @Override
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE id=?", ROW_MAPPER, id);
        return getUserRoles(users);
    }

    @Override
    public User getByEmail(String email) {
        List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        return getUserRoles(users);
    }

    @Override
    public List<User> getAll() {
        List<User> users = jdbcTemplate.query("SELECT * FROM users ORDER BY name, email", ROW_MAPPER);
        Map<Integer, Set<Role>> rolesMap = new HashMap<>();
        jdbcTemplate.query("SELECT * FROM user_role", rs -> {
            int user_id = rs.getInt("user_id");
            rolesMap.computeIfAbsent(user_id, u -> new HashSet<>()).add(Role.valueOf(rs.getString("role")));
        });
        users.forEach(user -> user.setRoles(rolesMap.get(user.getId())));
        return users;
    }

    private User getUserRoles(List<User> users) {
        User user = DataAccessUtils.singleResult(users);
        if (user != null) {
            List<Role> roles = jdbcTemplate.query("SELECT role FROM user_role WHERE user_id=?", new SingleColumnRowMapper<>(Role.class), user.getId());
            user.setRoles(roles);
        }
        return user;
    }
}
