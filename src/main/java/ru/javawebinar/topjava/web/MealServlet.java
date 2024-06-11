package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.storage.MealsMemoryStorage;
import ru.javawebinar.topjava.storage.MealsStorage;
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

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.MealsUtil.filteredByStreams;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private MealsStorage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = new MealsMemoryStorage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            log.debug("redirect to meals");
            request.setAttribute("meals", filteredByStreams(storage.getAll(), LocalTime.MIN, LocalTime.MAX, MealsUtil.CALORIES_PER_DAY));
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
            return;
        }
        Meal meal;
        String strId = request.getParameter("id");
        switch (action) {
            case "delete":
                storage.delete(Integer.parseInt(strId));
                response.sendRedirect("meals");
                log.debug("delete meal with id = " + Integer.parseInt(strId));
                return;
            case "add":
                meal = new Meal(LocalDateTime.now(), null, 0);
                log.debug("redirect to add");
                break;
            case "edit":
                meal = storage.read(Integer.parseInt(strId));
                log.debug("redirect to edit");
                break;
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
        String dateTime = request.getParameter("dateTime");
        String description = request.getParameter("description");
        String calories = request.getParameter("calories");
        if (id.isEmpty()) {
            createMeal(dateTime, description, calories);
        } else {
            updateMeal(Integer.parseInt(id), dateTime, description, calories);
        }
        response.sendRedirect("meals");
    }

    private void createMeal(String dateTime, String description, String calories) {
        Meal meal = storage.create(new Meal(DateUtil.of(dateTime), description, Integer.parseInt(calories)));
        log.debug("create meal with id = " + meal.getId());
    }

    private void updateMeal(Integer id, String dateTime, String description, String calories) {
        Meal meal = storage.read(id);
        meal.setDateTime(DateUtil.of(dateTime));
        meal.setDescription(description);
        meal.setCalories(Integer.parseInt(calories));
        storage.update(meal);
        log.debug("update meal with id = " + meal.getId());
    }
}
