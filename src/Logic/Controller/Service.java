package Logic.Controller;

import Logic.Model.Diet;
import Logic.Model.Meal;
import Logic.Model.Product;

import javax.swing.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class Service implements Serializable {
    private ArrayList<Product> productsList;
    private ArrayList<Meal> mealsList;
    private ArrayList<Diet> dietsList;

    public Service(){
        productsList = new ArrayList<>();
        mealsList = new ArrayList<>();
        dietsList = new ArrayList<>();
    }

    public void addProduct(Product product){
        productsList.add(product);
    }
    public void addMeal(Meal meal){
        mealsList.add(meal);
    }
    public void addDiet(Diet diet){
        dietsList.add(diet);
    }
    public ArrayList<Meal> getMealsList(){
        return mealsList;
    }

    public ArrayList<Product> getProductsList() {
        return productsList;
    }
    public ArrayList<Diet> getDietsList(){
        return dietsList;
    }
    public void removeProduct(Product product){
        productsList.remove(product);
    }
    public void removeMeal(Meal meal){
        mealsList.remove(meal);
    }

    public void removeDiet(Diet diet){
        dietsList.remove(diet);
    }
    public void setProductsList(java.util.ArrayList<Product> productsList) {
        this.productsList = productsList;
    }
}
