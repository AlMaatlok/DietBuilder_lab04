package Logic.GUI;

import Logic.Controller.Serialization;
import Logic.Controller.Service;
import Logic.Controller.Validator;
import Logic.Model.Meal;
import Logic.Model.Product;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class MealForms extends JPanel implements Serializable {
    private Service service;
    private Serialization serialization;
    private JComboBox<String> productCombo;
    private JTextField nameField, quantityOfProductField, quantityField;
    private JButton saveButton;
    private Product productToAdd;
    private Validator validator;
    private JComboBox<String> mealCombo;
    private Meal mealToDelete, mealToShow;

    public MealForms(String action, Service service, Serialization serialization) {
        this.service = service;
        this.serialization = serialization;
        this.validator = new Validator();

        serialization.deserializationOfProducts();
        serialization.deserializationOfMeals();

        if(action.equals("ADD")) {
            addMeal();
        }
        else if(action.equals("EDIT")) {
            editMeal();
        }
        else if(action.equals("DELETE")) {
            removeMeal();
        }
        else if(action.equals("SHOW")){
            showMeal();
        }
    }
    public void addMeal(){
        setLayout(new GridLayout(5, 2));

        getProductCombo();

        nameField = new JTextField();
        quantityOfProductField = new JTextField();
        quantityField = new JTextField();

        productCombo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedProductName = (String) productCombo.getSelectedItem();

                productToAdd = service.getProductsList().stream()
                        .filter(p -> p.getProductName().equals(selectedProductName))
                        .findFirst()
                        .orElse(null);
                if(productToAdd != null) {
                    quantityOfProductField.setText(String.valueOf(productToAdd.getQuantity()));
                }
            }
        });

        saveButton = new JButton("Dodaj");
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (productToAdd == null) {
                        JOptionPane.showMessageDialog(null, "Proszę wybrać produkt do edytowania.");
                        return;
                    }
                    String nameOfMeal = nameField.getText();
                    double quantity = Double.parseDouble(quantityField.getText());

                    if(!validator.validateNumber(quantity)){
                        JOptionPane.showMessageDialog(null, "Niepoprawny zapis");
                    }
                    Meal existingMeal = service.getMealsList().stream()
                            .filter(meal -> meal.getMealName().equalsIgnoreCase(nameOfMeal))
                            .findFirst()
                            .orElse(null);

                    if (existingMeal != null) {
                        existingMeal.addIngredient(productToAdd, quantity);
                        JOptionPane.showMessageDialog(null, "Dodano produkt do istniejącego posiłku: " + nameOfMeal);
                    } else {
                        Map<Product, Double> ingredients = new HashMap<>();

                        Product productFromList = service.getProductsList().stream()
                                .filter(p -> p.equals(productToAdd))
                                .findFirst()
                                .orElse(null);

                        if (productFromList != null) {
                            ingredients.put(productFromList, quantity);
                            productToAdd.setUsed(true);
                        }

                        Meal newMeal = new Meal(nameOfMeal, ingredients, false, service);
                        service.addMeal(newMeal);
                        JOptionPane.showMessageDialog(null, "Dodano nowy posiłek: " + nameOfMeal);
                    }
                    serialization.serializationOfProducts();
                    serialization.serializationOfMeals();

                }catch(NumberFormatException ex){
                    JOptionPane.showMessageDialog(null,"Błąd. Wprowadź poprawne dane");
                }
            }
        });
        add(new JLabel("Nazwa posiłku"));
        add(nameField);

        add(new JLabel("Produkty"));
        add(productCombo);

        add(new JLabel("Ilość produktu w jednej porcji"));
        quantityOfProductField.setEditable(false);
        add(quantityOfProductField);

        add(new JLabel("Ilość pojedyńczej porcji potrzebna do posiłku"));
        add(quantityField);

        add(saveButton);
    }
    public void editMeal() {
        setLayout(new GridLayout(5, 2));

        getMealCombo();

        getProductCombo();

        JTextField quantityField = new JTextField();

        mealCombo.addActionListener(e -> {
            String selectedMealName = (String) mealCombo.getSelectedItem();
            Meal selectedMeal = service.getMealsList().stream()
                    .filter(meal -> meal.getMealName().equals(selectedMealName))
                    .findFirst()
                    .orElse(null);

            if (selectedMeal != null) {
                productCombo.removeAllItems();
                selectedMeal.getIngredients().forEach((product, quantity) -> {
                    productCombo.addItem(product.getProductName() + " (" + quantity + " x " + product.getQuantity() + " g)");
                });
            }
        });

        JButton editButton = new JButton("Edytuj");
        editButton.addActionListener(e -> {
            try {
                String selectedProductInfo = (String) productCombo.getSelectedItem();
                if (selectedProductInfo == null) {
                    JOptionPane.showMessageDialog(null, "Proszę wybrać produkt do edytowania.");
                    return;
                }

                String selectedProductName = selectedProductInfo.split(" \\(")[0];
                double newQuantity = Double.parseDouble(quantityField.getText());
                String mealToEditName = (String) mealCombo.getSelectedItem();

                Meal selectedMeal = service.getMealsList().stream()
                        .filter(meal -> meal.getMealName().equals(mealToEditName))
                        .findFirst()
                        .orElse(null);

                if (selectedMeal != null) {
                    Product productToEdit = service.getProductsList().stream()
                            .filter(product -> product.getProductName().equals(selectedProductName))
                            .findFirst()
                            .orElse(null);

                    if (productToEdit != null) {
                        selectedMeal.editIngredient(productToEdit, newQuantity);
                        JOptionPane.showMessageDialog(null, "Ilość produktu zaktualizowana.");
                        serialization.serializationOfMeals();
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Błąd. Wprowadź poprawną ilość.");
            }
        });
        JButton deleteButton = new JButton("Usuń");
        deleteButton.addActionListener(e -> {
            try{
                String selectedProductInfo = (String) productCombo.getSelectedItem();
                if (selectedProductInfo == null) {
                    JOptionPane.showMessageDialog(null, "Proszę wybrać produkt do usunięcia.");
                    return;
                }
                String productToDeleteName = selectedProductInfo.split(" \\(")[0];
                String mealToEditName = (String) mealCombo.getSelectedItem();

                Meal selectedMeal = service.getMealsList().stream()
                        .filter(meal -> meal.getMealName().equals(mealToEditName))
                        .findFirst()
                        .orElse(null);

                if (selectedMeal != null) {
                    Product productToDelete = service.getProductsList().stream()
                            .filter(product -> product.getProductName().equals(productToDeleteName))
                            .findFirst()
                            .orElse(null);

                    if (productToDelete != null) {
                        selectedMeal.removeIngredient(productToDelete);
                        JOptionPane.showMessageDialog(null, "Produkt został usunięty");
                        serialization.serializationOfMeals();
                        serialization.serializationOfProducts();
                    }
                }

            }catch(NumberFormatException ex){
                JOptionPane.showMessageDialog(null, "Błąd.");
            }
        });

        add(new JLabel("Wybierz posiłek:"));
        add(mealCombo);

        add(new JLabel("Produkty:"));
        add(productCombo);

        add(new JLabel("Nowa ilość:"));
        add(quantityField);

        add(editButton);
        add(deleteButton);
    }
    public void removeMeal(){
        setLayout(new GridLayout(3,1));
        serialization.deserializationOfMeals();
        serialization.deserializationOfProducts();

        getMealCombo();

        nameField = new JTextField();

        mealCombo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedMealName = (String) mealCombo.getSelectedItem();

                mealToDelete = service.getMealsList().stream()
                        .filter(meal -> meal.getMealName().equals(selectedMealName))
                        .findFirst()
                        .orElse(null);
            }
        });

        add(new JLabel("Nazwa:"));
        add(mealCombo);

        saveButton = new JButton("Usuń");
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try{
                    if(mealToDelete == null){
                        JOptionPane.showMessageDialog(null,"Wybierz posiłek do usunięcia");
                    }
                    else {
                        if(mealToDelete.getUsed()){
                            JOptionPane.showMessageDialog(null,"Akcja niemożliwa! Posiłek znajduje się w planie posiłkowym.");
                        }
                        else {
                            service.removeMeal(mealToDelete);

                            serialization.serializationOfMeals();
                            serialization.serializationOfProducts();

                            JOptionPane.showMessageDialog(null, "Posiłek został usunięty!");

                            mealCombo.removeItem(mealToDelete.getMealName());
                            mealToDelete = null;
                        }
                    }
                }catch(NumberFormatException ex){
                    JOptionPane.showMessageDialog(null, "Proszę wybrać posiłek do edytowania.");
                }
            }
        });
        add(saveButton);
    }
    public void showMeal(){
        setLayout(new GridLayout(0,1));
        serialization.deserializationOfMeals();

        getMealCombo();
        add(mealCombo);

        mealCombo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedMealName = (String) mealCombo.getSelectedItem();

                mealToShow = service.getMealsList().stream()
                        .filter(meal -> meal.getMealName().equals(selectedMealName))
                        .findFirst()
                        .orElse(null);

                removeAll();
                add(mealCombo);

                if (mealToShow != null) {
                    add(new JLabel("Nazwa posiłku: " + selectedMealName));

                    if (!mealToShow.getIngredients().isEmpty()) {
                        add(new JLabel("Składniki:"));

                        for (Map.Entry<Product, Double> entry : mealToShow.getIngredients().entrySet()) {
                            Product ingredient = entry.getKey();
                            Double quantity = entry.getValue();
                            add(new JLabel("- " + ingredient.getProductName() + ": " + ingredient.getQuantity() + "g x " + quantity + "\n "));
                        }
                        add(new JLabel("Makroelementy:"));
                        for (Map.Entry<String, Double> entry : mealToShow.calculateNutrition().entrySet()) {
                            String nutrition = entry.getKey();
                            Double quantity = entry.getValue();
                            add(new JLabel("- " + nutrition + ": " + quantity + "g" ));
                        }
                        add(new JLabel("Suma kalori: " + mealToShow.getTotalCalories() + "kcal"));
                    } else {
                        add(new JLabel("Brak składników do wyświetlenia."));
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Nie znaleziono posiłku");
                }

                revalidate();
                repaint();
            }
        });
    }
    public JComboBox<String> getMealCombo() {
        mealCombo = new JComboBox<>(service.getMealsList().stream()
                .map(Meal::getMealName)
                .toArray(String[]::new));
        return mealCombo;
    }
    public JComboBox<String> getProductCombo(){
        productCombo = new JComboBox<>(service.getProductsList().stream()
                .map(Product::getProductName)
                .toArray(String[]::new));
        return productCombo;
    }
}