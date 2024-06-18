package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.DateTimeUtil.isBetween;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);
    private final Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(1, meal));
        MealsUtil.meals2.forEach(meal -> save(2, meal));
    }

    @Override
    public Meal save(int userId, Meal meal) {
        log.info("save {} for userId {}", meal, userId);
        Map<Integer, Meal> mealMap = repository.computeIfAbsent(userId, ConcurrentHashMap::new);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            mealMap.put(meal.getId(), meal);
            return meal;
        }
        return mealMap.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int userId, int id) {
        log.info("delete meal {} for userId {}", id, userId);
        Map<Integer, Meal> mealMap = repository.get(userId);
        return mealMap != null && mealMap.remove(id) != null;
    }

    @Override
    public Meal get(int userId, int id) {
        log.info("get meal {} for userId {}", id, userId);
        Map<Integer, Meal> mealMap = repository.get(userId);
        return mealMap != null ? mealMap.get(id) : null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        log.info("getAll for user {}", userId);
        return filterByDate(userId, meal -> true);
    }

    @Override
    public List<Meal> getAll(int userId, LocalDate startDate, LocalDate endDate) {
        log.info("getAll filtered by date for user {}", userId);
        return filterByDate(userId, meal -> isBetween(meal.getDate(), startDate, endDate));
    }

    private List<Meal> filterByDate(int userId, Predicate<Meal> predicate) {
        Map<Integer, Meal> mealMap = repository.get(userId);
        return mealMap == null ? Collections.emptyList() :
                mealMap.values().stream()
                        .filter(predicate)
                        .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                        .collect(Collectors.toList());
    }
}