package Logic.Model;

import java.util.ArrayList;

public class Diet {
    private ArrayList<Meal> meals;
    String name;

    public Diet(String name) {
        meals = new ArrayList<>();
        this.name = name;
    }
    public void addMeal(Meal meal) {
        meals.add(meal);
    }
    public void removeMeal(Meal meal) {
        meals.remove(meal);
    }
    public void setMeals(ArrayList<Meal> meals) {
        this.meals = meals;
    }
    public ArrayList<Meal> getMeals() {
        return meals;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}