package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

public class MapStorage implements Storage {
    private ConcurrentMap<Integer, Meal> storage;

    public MapStorage(ConcurrentMap<Integer, Meal> storage) {
        this.storage = storage;
    }

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

    public List<Meal> getAsList() {
        return new ArrayList(storage.values());
    }
}
