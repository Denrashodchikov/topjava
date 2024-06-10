package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.HashMap;
import java.util.Map;

public class MapStorage implements Storage{
    private Map<Integer, Meal> storage = new HashMap<>();

    @Override
    public void create(Meal meal) {
        storage.put(meal.getId(), meal);
    }

    @Override
    public Meal read(Integer id) {
        return storage.get(id);
    }

    @Override
    public void update(Meal meal) {
        storage.put(meal.getId(), meal);
    }

    @Override
    public void delete(Integer id) {
        storage.remove(id);
    }
}
