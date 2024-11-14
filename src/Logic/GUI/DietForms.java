package Logic.GUI;

import Logic.Controller.Serialization;
import Logic.Controller.Service;
import Logic.Model.Diet;
import Logic.Model.Meal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class DietForms extends JPanel {
    private JComboBox mealCombo, dietCombo;
    private JTextField dietNameField;
    private Service service = new Service();
    private Meal mealToAdd;
    private JButton saveButton;
    private Serialization serialization;
    private Diet dietToDelete, dietToShow;

    public DietForms(String action, Service service){
        this.service = service;
        this.serialization = new Serialization(service);
        serialization.deserializationOfDiets();
        serialization.deserializationOfMeals();

        if (action.equals("ADD")) {
            addDiet();
        }
        else if(action.equals("REMOVE")){
            removeDiet();
        }
        else if(action.equals("SHOW")){
            showDiet();
        }
    }

    public void addDiet() {
        setLayout(new GridLayout(3, 2));

        getMealCombo();

        dietNameField = new JTextField();

        mealCombo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedMealName = (String) mealCombo.getSelectedItem();

                mealToAdd = service.getMealsList().stream()
                        .filter(p -> p.getMealName().equals(selectedMealName))
                        .findFirst()
                        .orElse(null);
            }
        });

        saveButton = new JButton("Dodaj");
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (mealToAdd == null) {
                        JOptionPane.showMessageDialog(null, "Proszę wybrać posiłek do do dodania.");
                        return;
                    }
                    String nameOfDiet = dietNameField.getText();

                    Diet existingDiet = service.getDietsList().stream()
                            .filter(diet -> diet.getName().equalsIgnoreCase(nameOfDiet))
                            .findFirst()
                            .orElse(null);

                    if (existingDiet != null) {
                        existingDiet.addMeal(mealToAdd);
                        JOptionPane.showMessageDialog(null, "Dodano posiłek do istniejącego planu: " + nameOfDiet);
                    } else {
                        ArrayList<Meal> mealInDiet = new ArrayList<>();
                        mealInDiet.add(mealToAdd);
                        Diet newDiet = new Diet(nameOfDiet, mealInDiet);
                        service.addDiet(newDiet);
                        JOptionPane.showMessageDialog(null, "Dodano nowy plan posiłków: " + nameOfDiet);
                    }
                    serialization.serializationOfDiets();

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Błąd. Wprowadź poprawne dane");
                }
            }
        });
        add(new JLabel("Nazwa planu"));
        add(dietNameField);

        add(new JLabel("Posiłki"));
        add(mealCombo);

        add(saveButton);
    }
    public void removeDiet(){
        setLayout(new GridLayout(3,1));
        serialization.deserializationOfMeals();

        getDietCombo();

        dietNameField = new JTextField();

        dietCombo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedDietName = (String) dietCombo.getSelectedItem();

                dietToDelete = service.getDietsList().stream()
                        .filter(diet -> diet.getName().equals(selectedDietName))
                        .findFirst()
                        .orElse(null);
            }
        });

        add(new JLabel("Nazwa:"));
        add(dietCombo);

        saveButton = new JButton("Usuń");
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try{
                    if(dietToDelete == null){
                        JOptionPane.showMessageDialog(null,"Wybierz plan do usunięcia");
                    }
                    else {
                        service.removeDiet(dietToDelete);
                        serialization.serializationOfDiets();
                        JOptionPane.showMessageDialog(null, "Plan został usunięty!");

                        dietCombo.removeItem(dietToDelete.getName());
                        dietToDelete = null;
                    }
                }catch(NumberFormatException ex){
                    JOptionPane.showMessageDialog(null, "Proszę wybrać plan do edytowania.");
                }
            }
        });
        add(saveButton);
    }
    public void showDiet(){
        setLayout(new GridLayout(0,1));
        serialization.deserializationOfDiets();

        getDietCombo();
        add(dietCombo);

        dietCombo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedDietName = (String) dietCombo.getSelectedItem();

                dietToShow = service.getDietsList().stream()
                        .filter(diet -> diet.getName().equals(selectedDietName))
                        .findFirst()
                        .orElse(null);

                removeAll();
                add(dietCombo);

                if (dietToShow != null) {
                    add(new JLabel("Nazwa planu: " + selectedDietName));

                    if (!dietToShow.getMeals().isEmpty()) {
                        add(new JLabel("Posiłki:"));

                        for (Meal meal : dietToShow.getMeals()) {
                            add(new JLabel("- " + meal.getMealName()));
                        }
                        add(new JLabel("Suma kalori: " + dietToShow.getTotalCalories() + "kcal"));
                    } else {
                        add(new JLabel("Brak składników do wyświetlenia."));
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Nie znaleziono planu");
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
    public JComboBox<String> getDietCombo(){
        dietCombo = new JComboBox<>(service.getDietsList().stream()
                .map(Diet::getName)
                .toArray(String[]::new));
        return dietCombo;
    }
}