package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class MealTestData {
    public static final int MEAL_ID = 100003;
    public static final int MEAL_ID_DUB = 100006;
    public static final int NOT_FOUND_MEAL = 1;
    public static final Meal meal = new Meal(MEAL_ID, LocalDateTime.of(2024, 6, 23, 18, 30, 20), "Test desc", 1000);
    public static final Meal mealDub = new Meal(LocalDateTime.of(2024, 1, 1, 12, 30, 20), "Dublicate desc", 123);
    public static final Meal mealDubUpd = new Meal(MEAL_ID_DUB, LocalDateTime.of(2023, 12, 31, 11, 11, 11), "Dublicate desc Dub", 321);

    public static Meal getNewMeal() {
        return new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "new_desc", 1000);
    }

    public static Meal getUpdatedMeal() {
        Meal updated = new Meal(meal);
        updated.setDescription("updatedDesc");
        updated.setDateTime(LocalDateTime.of(2000, 12, 31, 23, 59, 59));
        updated.setCalories(123);
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().ignoringFields("id").isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveComparison().ignoringFields("id").isEqualTo(expected);
    }
}
