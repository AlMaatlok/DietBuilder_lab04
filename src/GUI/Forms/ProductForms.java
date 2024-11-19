package GUI.Forms;

import Logic.Controller.Serialization;
import Logic.Controller.Service;
import Logic.Controller.Validator;
import Logic.Model.Product;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProductForms extends JPanel {
    private JTextField nameField,fatsField, carbsField, proteinField, quantityField;
    private Service service;
    private JButton saveButton;
    private JComboBox<String> categoryCombo;
    private JComboBox<String> productCombo;
    private Product productToEdit;
    private Product productToDelete;
    private Validator validator ;
    private Serialization serialization;

    public ProductForms(String action, Service service, Serialization serialization, Validator validator) {
        this.service = service;
        this.serialization = serialization;
        this.validator = validator;

        serialization.deserializationOfProducts();

        if("ADD".equals(action)) {
            saveProduct();
        }
        else if("EDIT".equals(action)) {
            editProduct();
        }
        else if("DELETE".equals(action)){
            removeProduct();
        }

    }
    public void saveProduct(){
        setLayout(new GridLayout(7, 2));

        String categories[] = {"Owoce", "Warzywa", "Nabiał", "Mięso", "Inne"};
        nameField = new JTextField();
        carbsField = new JTextField();
        fatsField = new JTextField();
        proteinField = new JTextField();
        quantityField = new JTextField();

        add(new JLabel("Nazwa:"));
        add(nameField);

        add(new JLabel("Węglowodany (g):"));
        add(carbsField);

        add(new JLabel("Tłuszcze (g):"));
        add(fatsField);

        add(new JLabel("Białko (g):"));
        add(proteinField);

        add(new JLabel("Kategoria:"));
        categoryCombo = new JComboBox<>(categories);
        categoryCombo.setSelectedIndex(0);
        add(categoryCombo);

        add(new JLabel("Ilość (g):"));
        add(quantityField);

        saveButton = new JButton("Zapisz");
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String name = nameField.getText();
                    double carbs = Double.parseDouble(carbsField.getText());
                    double fats = Double.parseDouble(fatsField.getText());
                    double protein = Double.parseDouble(proteinField.getText());
                    String category = categoryCombo.getSelectedItem().toString();
                    double quantity = Double.parseDouble(quantityField.getText());

                    if(!validator.validateNumber(carbs) || !validator.validateNumber(fats) || !validator.validateNumber(protein) || !validator.validateNumber(quantity)){
                        JOptionPane.showMessageDialog(null, "Niepoprawny zapis");
                    }

                    Product newProduct = new Product(name, carbs, fats, protein, category, quantity, false);

                    if(validator.validateOriginalProduct(newProduct, service)){
                        JOptionPane.showMessageDialog(null, "Taki produkt już istnieje");
                    }
                    if(!validator.validateSum(newProduct)){
                        JOptionPane.showMessageDialog(null, "Suma makroelementów większa od porcji produktu.");
                    }
                    else {
                    service.addProduct(newProduct);
                    serialization.serializationOfProducts();

                    JOptionPane.showMessageDialog(null,"Produkt został dodany!");
                    }
                }catch(NumberFormatException ex){
                    JOptionPane.showMessageDialog(null,"Błąd. Wprowadź poprawne dane");
                }
            }
        });
        add(saveButton);
    }
    public void editProduct(){
        setLayout(new GridLayout(8, 2));

        String categories[] = {"Owoce", "Warzywa", "Nabiał", "Mięso", "Inne"};
        serialization.deserializationOfProducts();

        getProductCombo();

        nameField = new JTextField();
        carbsField = new JTextField();
        fatsField = new JTextField();
        proteinField = new JTextField();
        categoryCombo = new JComboBox<>(categories);
        quantityField = new JTextField();

        productCombo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedProductName = (String) productCombo.getSelectedItem();

                productToEdit = service.getProductsList().stream()
                        .filter(p -> p.getProductName().equals(selectedProductName))
                        .findFirst()
                        .orElse(null);

                if (productToEdit != null) {
                    fillEditForm();
                }
            }
        });

        add(new JLabel("Nazwa:"));
        add(productCombo);

        add(new JLabel("Węglowodany (g):"));
        add(carbsField);

        add(new JLabel("Tłuszcze (g):"));
        add(fatsField);

        add(new JLabel("Białko (g):"));
        add(proteinField);

        add(new JLabel("Kategoria:"));
        categoryCombo.setSelectedIndex(0);
        add(categoryCombo);

        add(new JLabel("Ilość (g):"));
        add(quantityField);

        saveButton = new JButton("Edytuj");
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (productToEdit == null) {
                        JOptionPane.showMessageDialog(null, "Proszę wybrać produkt do edytowania.");
                        return;
                    }
                    String name = productCombo.getSelectedItem().toString();
                    double carbs = Double.parseDouble(carbsField.getText());
                    double fats = Double.parseDouble(fatsField.getText());
                    double protein = Double.parseDouble(proteinField.getText());
                    String category = categoryCombo.getSelectedItem().toString();
                    double quantity = Double.parseDouble(quantityField.getText());
                    if(!validator.validateNumber(carbs) || !validator.validateNumber(fats) || !validator.validateNumber(protein) || !validator.validateNumber(quantity)){
                        JOptionPane.showMessageDialog(null, "Niepoprawny zapis");
                    }
                    else{

                    productToEdit.setProductName(name);
                    productToEdit.setCarbs(carbs);
                    productToEdit.setFats(fats);
                    productToEdit.setProtein(protein);
                    productToEdit.setType(category);
                    productToEdit.setQuantity(quantity);
                    serialization.serializationOfProducts();

                    JOptionPane.showMessageDialog(null,"Produkt został edytowany!");
                    }
                }catch(NumberFormatException ex){
                    JOptionPane.showMessageDialog(null,"Błąd. Wprowadź poprawne dane.");
                }
            }
        });
        add(saveButton);
    }

    private void removeProduct(){
        setLayout(new GridLayout(3,1));
        serialization.deserializationOfProducts();

        getProductCombo();

        nameField = new JTextField();

        productCombo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedProductName = (String) productCombo.getSelectedItem();

                productToDelete = service.getProductsList().stream()
                        .filter(p -> p.getProductName().equals(selectedProductName))
                        .findFirst()
                        .orElse(null);
            }
        });

        add(new JLabel("Nazwa:"));
        add(productCombo);


        saveButton = new JButton("Usuń");
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try{
                    if(productToDelete == null){
                        JOptionPane.showMessageDialog(null, "Proszę wybrać produkt do usunięcia");
                    }
                    else if(productToDelete.getUsed()){
                        JOptionPane.showMessageDialog(null, "Akcja niemożliwa. Produkt znajduje się w posiłku.");
                    }
                    else {
                        service.removeProduct(productToDelete);
                        serialization.serializationOfProducts();
                        JOptionPane.showMessageDialog(null, "Produkt został usunięty!");

                        productCombo.removeItem(productToDelete.getProductName());
                        productToDelete = null;
                    }
                }catch(NumberFormatException ex){
                    JOptionPane.showMessageDialog(null, "Proszę wybrać produkt do edytowania.");
                }
            }
        });
        add(saveButton);
    }
    public JComboBox<String> getProductCombo(){
        productCombo = new JComboBox<>(service.getProductsList().stream()
                .map(Product::getProductName)
                .toArray(String[]::new));
        return productCombo;
    }
    private void fillEditForm() {
        nameField.setText(productToEdit.getProductName());
        carbsField.setText(String.valueOf(productToEdit.getCarbs()));
        fatsField.setText(String.valueOf(productToEdit.getFats()));
        proteinField.setText(String.valueOf(productToEdit.getProtein()));
        categoryCombo.setSelectedItem(productToEdit.getType());
        quantityField.setText(String.valueOf(productToEdit.getQuantity()));
    }
}