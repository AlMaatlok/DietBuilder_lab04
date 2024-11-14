package Logic.Model;

import java.io.Serializable;
import java.util.ArrayList;

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

    public ArrayList<Meal> getMeals() {
        return meals;
    }
    public String getName() {
        return name;
    }
    public double getTotalCalories(){
        double total = 0;
        for(Meal meal : getMeals()){
            total += meal.getTotalCalories();
        }
        return total;
    }
}