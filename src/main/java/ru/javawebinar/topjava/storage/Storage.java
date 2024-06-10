package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

public interface Storage {

    void create(Meal meal);

    Meal read(Integer id);

    void update(Meal meal);

    void delete(Integer id);

}

