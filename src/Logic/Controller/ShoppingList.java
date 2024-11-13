package Logic.Controller;

import Logic.Model.Diet;
import Logic.Model.Meal;
import Logic.Model.Product;

import java.util.HashMap;
import java.util.Map;

public class ShoppingList {
    private Diet diet;

    public ShoppingList(Diet diet) {
        this.diet = diet;
    }

    public Map<String, Map<String, Double>> generateShoppingList() {
        Map<String, Map<String, Double>> shoppingList = new HashMap<>();

        for(Meal meal : diet.getMeals()) {
            for(Map.Entry<Product, Double> entry : meal.getIngredients().entrySet()){
                Product product = entry.getKey();
                double quantity = entry.getValue();

                String category = product.getType();
                String productName = product.getProductName();

                shoppingList.putIfAbsent(category, new HashMap<>());

                Map<String, Double> productsInCategory = shoppingList.get(category);

                productsInCategory.merge(productName,quantity, Double::sum);
            }
        }
        return shoppingList;
    }
    // Metoda pomocnicza do wyświetlania listy zakupów
    public void print() {
        Map<String, Map<String, Double>> shoppingList = generateShoppingList();
        for (String category : shoppingList.keySet()) {
            System.out.println("Kategoria: " + category);
            for (Map.Entry<String, Double> entry : shoppingList.get(category).entrySet()) {
                System.out.println(" - " + entry.getKey() + ": " + entry.getValue() + "g");
            }
        }
    }
}
