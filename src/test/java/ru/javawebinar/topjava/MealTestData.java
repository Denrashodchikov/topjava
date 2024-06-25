package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int MEAL_ID = START_SEQ + 3;
    public static final int NOT_FOUND_MEAL_ID = 1;
    public static final Meal mealUser1 = new Meal(MEAL_ID, LocalDateTime.of(2024, 6, 23, 18, 30, 20), "User description 1", 1000);
    public static final Meal mealUser2 = new Meal(MEAL_ID + 1, LocalDateTime.of(2024, 6, 23, 12, 30, 20), "User description 2", 123);
    public static final Meal mealUser3 = new Meal(MEAL_ID + 2, LocalDateTime.of(2024, 6, 23, 11, 11, 11), "User description 3", 321);
    public static final Meal mealUser4 = new Meal(MEAL_ID + 3, LocalDateTime.of(2024, 1, 1, 20, 0, 0), "User description 4", 200);
    public static final Meal mealUser5 = new Meal(MEAL_ID + 4, LocalDateTime.of(2024, 1, 1, 15, 30, 40), "User description 5", 150);
    public static final Meal mealUser6 = new Meal(MEAL_ID + 5, LocalDateTime.of(2024, 1, 1, 14, 10, 50), "User description 6", 1230);
    public static final Meal mealUser7 = new Meal(MEAL_ID + 6, LocalDateTime.of(2024, 1, 1, 13, 15, 20), "User description 7", 1000);
    public static final Meal mealUser8 = new Meal(MEAL_ID + 7, LocalDateTime.of(2024, 1, 1, 12, 30, 20), "User description 8", 800);
    public static final Meal mealAdmin1 = new Meal(MEAL_ID + 8, LocalDateTime.of(2024, 2, 4, 10, 0, 0), "Admin description 1", 1000);

    public static Meal getNewMeal() {
        return new Meal(LocalDateTime.of(2024, 12, 1, 12, 0, 0), "new_desc", 1000);
    }

    public static Meal getUpdatedMeal() {
        Meal updated = new Meal(mealUser1);
        updated.setDescription("updatedDesc");
        updated.setDateTime(LocalDateTime.of(2000, 12, 31, 23, 59, 59));
        updated.setCalories(123);
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }
}
