package se.phew.aoc.days;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Day21 extends Challenge {

    HashMap<String, String> map = new HashMap<>();
    ArrayList<Food> foods = new ArrayList<>();

    public Day21() {
        super();

        for (String line : lines) {
            foods.add(new Food(line));
        }

        do {
            for (Food food : foods) {
                ArrayList<String> allergens = (ArrayList<String>) food.allergens.clone();
                for (String allergen : allergens) {
                    String ingredient = findIngredientFor(allergen);
                    if (ingredient != null) {
                        map.put(ingredient, allergen);
                        removeFromAllFoods(ingredient, allergen);
                    }
                }
            }
        } while (!allAllergensIdentified());

        int count = 0;
        for (Food food : foods) {
            count += food.ingredients.size();
        }

        printAnswer(1, count);

        ArrayList<String> allergens = new ArrayList<>(map.values());
        Collections.sort(allergens);

        String result = "";
        for (String allergen : allergens) {
            for (String ingredient : map.keySet()) {
                if (map.get(ingredient).equals(allergen)) {
                    result += ingredient + ",";
                }
            }
        }
        result = result.substring(0, result.length() - 1);

        printAnswer(2, result);
    }

    private void removeFromAllFoods(String ingredient, String allergen) {
        for (Food food : foods) {
            if (food.ingredients.contains(ingredient)) {
                food.ingredients.remove(ingredient);
            }
            if (food.allergens.contains(allergen)) {
                food.allergens.remove(allergen);
            }
        }
    }

    private String findIngredientFor(String allergen) {
        int allergenCount = 0;
        HashMap<String, Integer> ingredientCount = new HashMap<>();
        for (Food food : foods) {
            if (food.allergens.contains(allergen)) {
                allergenCount++;
                for (String ingredient : food.ingredients) {
                    if (!ingredientCount.containsKey(ingredient)) {
                        ingredientCount.put(ingredient, 1);
                    } else {
                        ingredientCount.put(ingredient, ingredientCount.get(ingredient) + 1);
                    }
                }
            }
        }
        ArrayList<String> candidates = new ArrayList<>();
        for (String ingredient : ingredientCount.keySet()) {
            if (ingredientCount.get(ingredient) == allergenCount) {
                candidates.add(ingredient);
            }
        }
        if (candidates.size() == 1) {
            return candidates.get(0);
        } else {
            return null;
        }

    }

    private boolean allAllergensIdentified() {
        for (Food food : foods) {
            if (food.allergens.size() > 0) {
                return false;
            }
        }
        return true;
    }

    class Food {
        ArrayList<String> ingredients = new ArrayList<>();
        ArrayList<String> allergens = new ArrayList<>();

        public Food(String input) {
            input = input.replaceAll("\\)", "");
            String[] split = input.split(" \\(contains ");

            for (String i : split[0].split(" ")) {
                ingredients.add(i);
            }

            for (String a : split[1].split(", ")) {
                allergens.add(a);
            }
        }
    }
}
