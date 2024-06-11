package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealsMemoryStorage implements MealsStorage {
    private final ConcurrentMap<Integer, Meal> storage;
    AtomicInteger counter = new AtomicInteger(1);

    public MealsMemoryStorage() {
        storage = new ConcurrentHashMap<>();
        create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }

    @Override
    public Meal create(Meal meal) {
        meal.setId(counter.get());
        storage.put(counter.getAndIncrement(), meal);
        return meal;
    }

    @Override
    public Meal read(int id) {
        return storage.get(id);
    }

    @Override
    public Meal update(Meal meal) {
        if (read(meal.getId()) != null) {
            storage.put(meal.getId(), meal);
            return meal;
        }
        return null;
    }

    @Override
    public void delete(int id) {
        storage.remove(id);
    }

    public List<Meal> getAll() {
        return new ArrayList(storage.values());
    }
}
