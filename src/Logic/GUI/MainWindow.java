package Logic.GUI;

import Logic.Controller.Serialization;
import Logic.Controller.Service;
import Logic.Controller.Validator;
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
    private JMenu menuShoppingList;
    private JMenuItem menuAddProduct, menuRemoveProduct, menuEditProduct;
    private JMenuItem menuAddMeal, menuRemoveMeal, menuEditMeal, menuShowMeals;
    private JMenuItem menuAddDiet, menuRemoveDiet, menuEditDiet;
    private JMenuItem menuGenerate;
    private JPanel mainPanel;
    private Service service;
    private Validator validator ;
    private Serialization serialization;

    public MainWindow() {
        setTitle("Diet Builder");
        this.service = new Service();
        this.validator = new Validator();
        this.serialization = new Serialization(service);
        serialization.deserializationOfProducts();
        serialization.deserializationOfMeals();
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

        menuDiet = new JMenu("Diety");
        menuAddDiet = new JMenuItem("Stwórz");
        menuDiet.add(menuAddDiet);
        menuEditDiet = new JMenuItem("Edytuj");
        menuDiet.add(menuEditDiet);
        menuRemoveDiet = new JMenuItem("Usuń");
        menuDiet.add(menuRemoveDiet);

        menuShoppingList = new JMenu("Lista zakupów");
        menuGenerate = new JMenuItem("Wygeneruj");
        menuShoppingList.add(menuGenerate);


        menuBar.add(menuProducts);
        menuBar.add(menuMeals);
        menuBar.add(menuDiet);
        menuBar.add(menuShoppingList);

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
                serialization.serializationOfProducts();
            }
        });

    }
    private void openAddProduct() {
        ProductForms addProductPanel = new ProductForms("ADD", service);

        mainPanel.removeAll();
        mainPanel.add(addProductPanel);
        mainPanel.revalidate();
        mainPanel.repaint();
    }
    private void openEditProduct() {
        ProductForms addProductPanel = new ProductForms("EDIT", service);

        mainPanel.removeAll();
        mainPanel.add(addProductPanel);
        mainPanel.revalidate();
        mainPanel.repaint();
    }
    private void openRemoveProduct() {
        ProductForms addProductPanel = new ProductForms("DELETE", service);

        mainPanel.removeAll();
        mainPanel.add(addProductPanel);
        mainPanel.revalidate();
        mainPanel.repaint();
    }
    private void openAddMeal() {
        MealForms addMealPanel = new MealForms("ADD", service);

        mainPanel.removeAll();
        mainPanel.add(addMealPanel);
        mainPanel.revalidate();
        mainPanel.repaint();
    }
    private void openEditMeal() {
        MealForms addMealPanel = new MealForms("EDIT", service);

        mainPanel.removeAll();
        mainPanel.add(addMealPanel);
        mainPanel.revalidate();
        mainPanel.repaint();
    }
    private void openRemoveMeal() {
        MealForms addMealPanel = new MealForms("DELETE", service);

        mainPanel.removeAll();
        mainPanel.add(addMealPanel);
        mainPanel.revalidate();
        mainPanel.repaint();
    }
    private void openShowMeals(){
        MealForms addMealPanel = new MealForms("SHOW", service);

        mainPanel.removeAll();
        mainPanel.add(addMealPanel);
        mainPanel.revalidate();
        mainPanel.repaint();
    }
    private void openEditDiet() {

    }
    private void openAddDiet() {

    }
    private void openRemoveDiet() {

    }
    private void openEditDiet(Diet diet) {

    }
    private void openGenerateShoppingList(){

    }
}
