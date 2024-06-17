package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDateTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(MealRestController.class);

    private final MealService service;

    public MealRestController(MealService service) {
        this.service = service;
    }

    public Meal create(Meal meal) {
        log.info("userId {} create {}", SecurityUtil.authUserId(), meal);
        checkNew(meal);
        return service.create(SecurityUtil.authUserId(), meal);
    }

    public Meal update(Meal meal, int mealId) {
        log.info("userId {} update {}", SecurityUtil.authUserId(), meal);
        assureIdConsistent(meal, mealId);
        return service.update(SecurityUtil.authUserId(), meal);
    }

    public void delete(int mealId) {
        log.info("userId {} delete {}", SecurityUtil.authUserId(), mealId);
        service.delete(SecurityUtil.authUserId(), mealId);
    }

    public Meal get(int mealId) {
        log.info("userId {} get {}", SecurityUtil.authUserId(), mealId);
        return service.get(SecurityUtil.authUserId(), mealId);
    }

    public List<MealTo> getAll() {
        log.info("getAll");
        return MealsUtil.getTos(service.getAll(SecurityUtil.authUserId()), SecurityUtil.authUserCaloriesPerDay());
    }

    public List<MealTo> getAllByDate(String startDate, String endDate) {
        log.info("getAllByDate");
        return MealsUtil.getTos(service.getAllFilterByDate(SecurityUtil.authUserId(), LocalDateTime.parse(startDate), LocalDateTime.parse(endDate)),
                SecurityUtil.authUserCaloriesPerDay());
    }
}