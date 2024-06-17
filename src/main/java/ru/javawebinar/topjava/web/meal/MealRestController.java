package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {
    private final Logger log = LoggerFactory.getLogger(MealRestController.class);

    private final MealService service;
    private final int authUserId = SecurityUtil.authUserId();
    private final int authUserCaloriesPerDay = SecurityUtil.authUserCaloriesPerDay();

    public MealRestController(MealService service) {
        this.service = service;
    }

    public Meal create(Meal meal) {
        log.info("userId {} create {}", authUserId, meal);
        checkNew(meal);
        return service.create(authUserId, meal);
    }

    public void update(Meal meal, int mealId) {
        log.info("userId {} update {}", authUserId, meal);
        assureIdConsistent(meal, mealId);
        service.update(authUserId, meal);
    }

    public void delete(int mealId) {
        log.info("userId {} delete {}", authUserId, mealId);
        service.delete(authUserId, mealId);
    }

    public Meal get(int mealId) {
        log.info("userId {} get {}", authUserId, mealId);
        return service.get(authUserId, mealId);
    }

    public List<MealTo> getAll() {
        log.info("getAll");
        return MealsUtil.getTos(service.getAll(authUserId), authUserCaloriesPerDay);
    }

    public List<MealTo> getAllByDate(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        log.info("getAllByDate from {} {} to {} {}", startDate, startTime, endDate, endTime);
        Collection<Meal> meals = service.getAllFilterByDate(authUserId,
                Optional.ofNullable(startDate).orElse(LocalDate.MIN), Optional.ofNullable(endDate).orElse(LocalDate.MAX));
        return MealsUtil.getFilteredTos(meals, authUserCaloriesPerDay,
                Optional.ofNullable(startTime).orElse(LocalTime.MIN), Optional.ofNullable(endTime).orElse(LocalTime.MAX));
    }
}