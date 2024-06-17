package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public interface MealRepository {
    // null if updated meal does not belong to userId
    Meal save(int userId, Meal meal);

    // false if meal does not belong to userId
    boolean delete(int userId, int id);

    // null if meal does not belong to userId
    Meal get(int userId, int id);

    // ORDERED dateTime desc
    default List<Meal> getAll(int userId, LocalDate startDate, LocalDate endDate) {
        return filterByDate(userId, meal -> meal.getDate().compareTo(Optional.ofNullable(startDate).orElse(LocalDate.MIN)) >= 0
                && meal.getDate().compareTo(Optional.ofNullable(endDate).orElse(LocalDate.MAX)) <= 0);
    }

    List<Meal> filterByDate(int userId, Predicate<Meal> predicate);
}
