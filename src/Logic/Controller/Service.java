package Logic.Controller;

import Logic.Model.Meal;
import Logic.Model.Product;

import javax.swing.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class Service implements Serializable {
    private ArrayList<Product> productsList;
    private ArrayList<Meal> mealsList;

    public Service(){
        productsList = new ArrayList<>();
        mealsList = new ArrayList<>();
    }

    public void addProduct(Product product){
        productsList.add(product);
    }
    public void addMeal(Meal meal){
        mealsList.add(meal);
    }
    public ArrayList<Meal> getMealsList(){
        return mealsList;
    }

    public ArrayList<Product> getProductsList() {
        return productsList;
    }
    public void setProductsList(java.util.ArrayList<Product> productsList) {
        this.productsList = productsList;
    }

    public void removeProduct(Product product){
        productsList.remove(product);
    }
    public void removeMeal(Meal meal){
        mealsList.remove(meal);
    }
}
