package Logic.Controller;

import Logic.Model.Meal;
import Logic.Model.Product;

import java.util.ArrayList;

public class Validator {
    private Service service;

    public Validator() {
        this.service = new Service();
    }

    public boolean validateNumber(Double number) {
        if (number < 0)
            return false;
        else return true;
    }
    public boolean validateOriginalProduct(Product product) {
        boolean value = true;
        for(Product product1 : service.getProductsList()) {
            if (product.getProductName().equalsIgnoreCase(product1.getProductName()))
                value = false;
        }
        return value;
    }
    public boolean validateIsInMeal(Product product){
            boolean value = false;
            ArrayList<Meal> mealsList = service.getMealsList();
            for(Meal meal: mealsList) {
                if(meal.getIngredients().keySet().contains(product)){
                    value = true;
                }
            }
            return value;
        }
}
