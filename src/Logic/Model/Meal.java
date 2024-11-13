package Logic.Model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Meal implements Serializable {
    private static final long serialVersionUID = 9084374006778229070L;
    private String mealName;
    private Map<Product, Double> Ingredients;

    public Meal(String name, Map<Product, Double> ingredients){
        this.mealName = name;
        this.Ingredients = ingredients;
    }

    public void addIngredient(Product product, double quantity){
        Ingredients.put(product, quantity);
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
    }

    public int getTotalCarbs(){
        int total = 0;
        for(Product product : Ingredients.keySet()){
            total+=product.calculateCalories();
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

            carbs += product.getCarbs()*quantity/product.getQuantity();
            fats += product.getFats()*quantity/product.getQuantity();
            proteins += product.getProtein()*quantity/product.getQuantity();
        }
        nutrition.put("Węglowodany", carbs);
        nutrition.put("Tłuszcze", fats);
        nutrition.put("Białka", proteins);

        return nutrition;
    }

    public String getMealName() {
        return mealName;
    }
    public void setMealName(String mealName) {
        this.mealName = mealName;
    }
    public Map<Product, Double> getIngredients() {
        return Ingredients;
    }
    public void setIngredients(Map<Product, Double> ingredients) {
        this.Ingredients = ingredients;
    }
}