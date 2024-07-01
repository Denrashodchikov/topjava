package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    @Autowired
    private MealService mealService;

    @Test
    public void create() {
        Meal created = mealService.create(getNewMeal(), USER_ID);
        Meal newMeal = getNewMeal();
        newMeal.setId(created.getId());
        assertMatch(created, newMeal);
        assertMatch(mealService.get(created.getId(), USER_ID), newMeal);
    }

    @Test
    public void duplicateDateTimeCreate() {
        assertThrows(DataAccessException.class, () -> mealService.create(new Meal(mealUser1.getDateTime(), "User description 1", 1), USER_ID));
    }

    @Test
    public void get() {
        Meal meal = mealService.get(MEAL_ID, USER_ID);
        assertMatch(meal, MealTestData.mealUser1);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> mealService.get(NOT_FOUND_MEAL_ID, USER_ID));
    }

    @Test
    public void getAnother() {
        assertThrows(NotFoundException.class, () -> mealService.get(mealAdmin1.getId(), USER_ID));
    }

    @Test
    public void delete() {
        mealService.delete(MEAL_ID, USER_ID);
        assertThrows(NotFoundException.class, () -> mealService.get(MEAL_ID, USER_ID));
    }

    @Test
    public void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> mealService.delete(NOT_FOUND_MEAL_ID, USER_ID));
    }

    @Test
    public void deletedAnother() {
        assertThrows(NotFoundException.class, () -> mealService.delete(mealAdmin1.getId(), USER_ID));
    }

    @Test
    public void update() {
        Meal updated = getUpdatedMeal();
        mealService.update(updated, USER_ID);
        assertMatch(mealService.get(MEAL_ID, USER_ID), getUpdatedMeal());
    }

    @Test
    public void updateAnother() {
        Meal updated = getUpdatedMeal();
        assertThrows(NotFoundException.class, () -> mealService.update(updated, ADMIN_ID));
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> allBetween = mealService.getBetweenInclusive(LocalDate.of(2024, 1, 1), LocalDate.of(2024, 3, 31), USER_ID);
        assertMatch(allBetween, mealUser4, mealUser5, mealUser6, mealUser7, mealUser8);
    }

    @Test
    public void getWithEmptyInclusive() {
        List<Meal> allBetween = mealService.getBetweenInclusive(null, null, USER_ID);
        assertMatch(allBetween, mealUser1, mealUser2, mealUser3, mealUser4, mealUser5, mealUser6, mealUser7, mealUser8);
    }

    @Test
    public void getAll() {
        List<Meal> all = mealService.getAll(USER_ID);
        assertMatch(all, mealUser1, mealUser2, mealUser3, mealUser4, mealUser5, mealUser6, mealUser7, mealUser8);
    }
}