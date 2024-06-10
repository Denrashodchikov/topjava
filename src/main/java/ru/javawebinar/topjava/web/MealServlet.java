package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.storage.MapStorage;
import ru.javawebinar.topjava.util.DateUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.MealsUtil.filteredByStreams;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private MapStorage storage;
    AtomicInteger atomicInt = new AtomicInteger(10);

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = new MapStorage(MealsUtil.createMeals().stream().collect(Collectors.toConcurrentMap(Meal::getId,
                Function.identity())));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");
        String strId = request.getParameter("id");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("meals", filteredByStreams(storage.getAsList(), LocalTime.MIN, LocalTime.MAX, MealsUtil.CALORIES_PER_DAY));
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
            return;
        }
        Meal meal;
        switch (action) {
            case "delete":
                storage.delete(Integer.parseInt(strId));
                response.sendRedirect("meals");
                return;
            case "add":
                meal = new Meal(atomicInt.getAndIncrement(), null, null, 0);
                break;
            case "edit":
                meal = storage.read(Integer.parseInt(strId));
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("meal", meal);
        request.getRequestDispatcher(
                "/WEB-INF/jsp/edit.jsp"
        ).forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Integer id = Integer.parseInt(request.getParameter("id"));
        Meal meal;
        if (storage.read(id) == null) {
            meal = new Meal(id, null, null, 0);
            storage.create(meal);
        } else {
            meal = storage.read(id);
        }
        String dateTime = request.getParameter("dateTime");
        String description = request.getParameter("description");
        String calories = request.getParameter("calories");
        meal.setDateTime(DateUtil.of(dateTime));
        meal.setDescription(description);
        meal.setCalories(Integer.parseInt(calories));
        storage.update(meal);
        response.sendRedirect("meals");
    }
}
