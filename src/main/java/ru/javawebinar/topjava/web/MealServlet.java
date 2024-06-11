package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.storage.MealsStorage;
import ru.javawebinar.topjava.storage.MemoryMealsStorage;
import ru.javawebinar.topjava.util.DateUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.MealsUtil.filteredByStreams;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private MealsStorage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = new MemoryMealsStorage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = Optional.ofNullable(request.getParameter("action")).orElse("default");
        Meal meal;
        String strId = request.getParameter("id");
        switch (action) {
            case "delete":
                log.debug("delete meal with id = {}", strId);
                storage.delete(Integer.parseInt(strId));
                response.sendRedirect("meals");
                return;
            case "add":
                log.debug("redirect to add");
                meal = new Meal(LocalDateTime.now(), null, 0);
                break;
            case "edit":
                log.debug("redirect to edit");
                meal = storage.read(Integer.parseInt(strId));
                break;
            case "default":
            default:
                log.debug("redirect to meals");
                request.setAttribute("meals", filteredByStreams(storage.getAll(), LocalTime.MIN, LocalTime.MAX, MealsUtil.CALORIES_PER_DAY));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                return;
        }
        request.setAttribute("meal", meal);
        request.getRequestDispatcher("/mealEdit.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        LocalDateTime dateTime = DateUtil.of(request.getParameter("dateTime"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        if (id.isEmpty()) {
            createMeal(dateTime, description, calories);
        } else {
            updateMeal(Integer.parseInt(id), dateTime, description, calories);
        }
        response.sendRedirect("meals");
    }

    private void createMeal(LocalDateTime dateTime, String description, int calories) {
        log.debug("create meal with dateTime={}, description={}, calories={}", dateTime, description, calories);
        Meal meal = storage.create(new Meal(dateTime, description, calories));
        log.debug("successful creation {}", meal);
    }

    private void updateMeal(Integer id, LocalDateTime dateTime, String description, int calories) {
        log.debug("update meal with id = {}", id);
        storage.update(new Meal(id, dateTime, description, calories));
    }
}
