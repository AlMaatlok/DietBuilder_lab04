package Logic.GUI;

import Logic.Controller.Serialization;
import Logic.Controller.Service;
import Logic.Model.Diet;
import Logic.Model.Meal;
import Logic.Model.Product;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Map;

public class MainWindow extends JFrame {
    private JMenuBar menuBar;
    private JMenu menuProducts;
    private JMenu menuMeals;
    private JMenu menuDiet;
    private JMenuItem menuAddProduct, menuRemoveProduct, menuEditProduct;
    private JMenuItem menuAddMeal, menuRemoveMeal, menuEditMeal, menuShowMeals;
    private JMenuItem menuAddDiet, menuRemoveDiet, menuShowDiet, menuGenerate;
    private JPanel mainPanel;
    private Service service;
    private Serialization serialization;

    public MainWindow() {
        setTitle("Diet Builder");
        this.service = new Service();
        this.serialization = new Serialization(service);
        serialization.deserializationOfProducts();
        serialization.deserializationOfMeals();
        serialization.deserializationOfDiets();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        menuBar = new JMenuBar();

        menuProducts = new JMenu("Produkty");
        menuAddProduct = new JMenuItem("Dodaj");
        menuProducts.add(menuAddProduct);
        menuEditProduct = new JMenuItem("Edytuj");
        menuProducts.add(menuEditProduct);
        menuRemoveProduct = new JMenuItem("Usuń");
        menuProducts.add(menuRemoveProduct);


        menuMeals = new JMenu("Posiłki");
        menuAddMeal = new JMenuItem("Dodaj");
        menuMeals.add(menuAddMeal);
        menuEditMeal = new JMenuItem("Edytuj");
        menuMeals.add(menuEditMeal);
        menuRemoveMeal = new JMenuItem("Usuń");
        menuMeals.add(menuRemoveMeal);
        menuShowMeals = new JMenuItem("Lista posiłków");
        menuMeals.add(menuShowMeals);

        menuDiet = new JMenu("Plany posiłkowe");
        menuAddDiet = new JMenuItem("Stwórz");
        menuDiet.add(menuAddDiet);
        menuRemoveDiet = new JMenuItem("Usuń");
        menuDiet.add(menuRemoveDiet);
        menuShowDiet = new JMenuItem("Lista planów");
        menuDiet.add(menuShowDiet);
        menuGenerate = new JMenuItem("Wygeneruj listę zakupów");
        menuDiet.add(menuGenerate);


        menuBar.add(menuProducts);
        menuBar.add(menuMeals);
        menuBar.add(menuDiet);

        setJMenuBar(menuBar);

        mainPanel = new JPanel();
        mainPanel.setLayout(new CardLayout());

        add(mainPanel);

        menuAddProduct.addActionListener(e -> openAddProduct());
        menuEditProduct.addActionListener(e -> openEditProduct());
        menuRemoveProduct.addActionListener(e -> openRemoveProduct());

        menuAddMeal.addActionListener(e -> openAddMeal());
        menuEditMeal.addActionListener(e -> openEditMeal());
        menuRemoveMeal.addActionListener(e -> openRemoveMeal());
        menuShowMeals.addActionListener(e -> openShowMeals());

        menuAddDiet.addActionListener(e -> openAddDiet());
        menuRemoveDiet.addActionListener(e -> openRemoveDiet());
        menuShowDiet.addActionListener(e -> openShowDiet());

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("Liczba produktów w liście: " + service.getProductsList().size());

                for (Product product : service.getProductsList()) {
                    System.out.println("Nazwa: " + product.getProductName());
                    System.out.println("Kategoria: " + product.getType());
                    System.out.println("Węglowodany: " + product.getCarbs() + " g");
                    System.out.println("Tłuszcze: " + product.getFats() + " g");
                    System.out.println("Białko: " + product.getProtein() + " g");
                    System.out.println("Ilość: " + product.getQuantity() + " g");
                    System.out.println("Czy użyty: " + product.getUsed());
                    System.out.println("-------------------------");
                }
                System.out.println("Liczba posiłków w liście: " + service.getMealsList().size());

                for (Meal meal : service.getMealsList()) {
                    System.out.println("Nazwa posiłku: " + meal.getMealName());
                    System.out.println("Składniki:");
                    for (Map.Entry<Product, Double> entry : meal.getIngredients().entrySet()) {
                        Product ingredient = entry.getKey();
                        Double quantity = entry.getValue();
                        System.out.println("- " + ingredient.getProductName() + ": " + ingredient.getQuantity() + "g x "+ quantity);
                    }
                    System.out.println("-------------------------");
                }
                System.out.println("Liczba planów w liście: " + service.getDietsList().size());
                for (Diet diet : service.getDietsList()) {
                    System.out.println("Nazwa planu: " + diet.getName());
                    System.out.println("Posiłki:");
                    for (Meal meal : diet.getMeals()) {
                        System.out.println("- " + meal.getMealName());
                    }
                    System.out.println("-------------------------");
                }
                serialization.serializationOfProducts();
            }
        });
    }
    private void openAddProduct() {
        ProductForms addProductPanel = new ProductForms("ADD", service);

        productPanel(addProductPanel);
    }
    private void openEditProduct() {
        ProductForms addProductPanel = new ProductForms("EDIT", service);

        productPanel(addProductPanel);
    }
    private void openRemoveProduct() {
        ProductForms addProductPanel = new ProductForms("DELETE", service);

        productPanel(addProductPanel);
    }
    private void openAddMeal() {
        MealForms addMealPanel = new MealForms("ADD", service);

        mealPanel(addMealPanel);
    }
    private void openEditMeal() {
        MealForms addMealPanel = new MealForms("EDIT", service);

        mealPanel(addMealPanel);
    }
    private void openRemoveMeal() {
        MealForms addMealPanel = new MealForms("DELETE", service);

        mealPanel(addMealPanel);
    }
    private void openShowMeals(){
        MealForms addMealPanel = new MealForms("SHOW", service);

        mealPanel(addMealPanel);
    }
    private void openAddDiet() {
        DietForms addDietPanel = new DietForms("ADD", service);

        dietPanel(addDietPanel);
    }
    private void openRemoveDiet() {
        DietForms addDietPanel = new DietForms("REMOVE", service);

        dietPanel(addDietPanel);
    }
    private void openShowDiet() {
        DietForms addDietPanel = new DietForms("SHOW", service);

        dietPanel(addDietPanel);
    }
    private void openGenerateShoppingList(){
    }
    public void productPanel(ProductForms panel){
        mainPanel.removeAll();
        mainPanel.add(panel);
        mainPanel.revalidate();
        mainPanel.repaint();
    }
    public void mealPanel(MealForms panel){
        mainPanel.removeAll();
        mainPanel.add(panel);
        mainPanel.revalidate();
        mainPanel.repaint();
    }
    public void dietPanel(DietForms panel){
        mainPanel.removeAll();
        mainPanel.add(panel);
        mainPanel.revalidate();
        mainPanel.repaint();
    }
}