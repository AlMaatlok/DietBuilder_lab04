package Logic.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Diet implements Serializable {
    private static final long serialVersionUID = -5124367644085037337L;

    private ArrayList<Meal> meals;
    String name;

    public Diet(String name, ArrayList<Meal> meals) {
        this.name = name;
        this.meals = meals;
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
    public double getTotalCalories(){
        double total = 0;
        for(Meal meal : getMeals()){
            total += meal.getTotalCalories();
        }
        return total;
    }
}