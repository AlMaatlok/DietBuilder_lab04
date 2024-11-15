package Logic.Model;

import Logic.Controller.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Meal implements Serializable {
    private static final long serialVersionUID = 9084374006778229070L;
    private String mealName;
    private Map<Product, Double> Ingredients;
    private boolean isUsed;
    private Service service;

    public Meal(String name, Map<Product, Double> ingredients, boolean isUsed){
        this.mealName = name;
        this.Ingredients = ingredients;
        this.isUsed = isUsed;
        this.service = new Service();
    }

    public void addIngredient(Product product, double quantity){
        Ingredients.put(product, quantity);
        product.setUsed(true);
    }

    public void editIngredient(Product product, double newQuantity) {
        if (this.Ingredients.containsKey(product)) {
            this.Ingredients.put(product, newQuantity);
        } else {
            System.out.println("Produkt nie istnieje w składnikach.");
        }
    }
    public void removeIngredient(Product product){
        this.Ingredients.remove(product);
        if(!validateIsInMeal(product)){
            product.setUsed(false);
        }
    }

    public int getTotalCalories(){
        int total = 0;
        for(Product product : Ingredients.keySet()){
            total += product.calculateCalories();
        }
        return total;
    }
    public Map<String, Double> calculateNutrition(){
        double carbs = 0;
        double fats = 0;
        double proteins = 0;

        Map<String, Double> nutrition = new HashMap<>();

        for(Map.Entry<Product, Double> entry : Ingredients.entrySet()){
            Product product = entry.getKey();
            double quantity = entry.getValue();

            carbs += product.getCarbs()*quantity;
            fats += product.getFats()*quantity;
            proteins += product.getProtein()*quantity;
        }
        nutrition.put("Węglowodany", carbs);
        nutrition.put("Tłuszcze", fats);
        nutrition.put("Białka", proteins);

        return nutrition;
    }

    public String getMealName() {
        return mealName;
    }

    public Map<Product, Double> getIngredients() {
        return Ingredients;
    }
    public boolean getUsed(){
        return isUsed;
    }
    public void setUsed(boolean isUsed){
        this.isUsed = isUsed;
    }
    public boolean validateIsInMeal(Product product){
        boolean value = false;
        ArrayList<Meal> mealsList = service.getMealsList();
        for(Meal meal : mealsList) {
            if(meal.getIngredients().keySet().contains(product)){
                value = true;
            }
        }
        return value;
    }
}