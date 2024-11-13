package Logic.Controller;

import Logic.Model.Meal;
import Logic.Model.Product;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Serialization {
    private Service service = new Service();
    //private static long  serialVersionUID;

    public Serialization(Service service) {
        this.service = service;
    }

    public void serializationOfProducts() {
        try (FileOutputStream fos = new FileOutputStream("productsData");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            oos.writeObject(service.getProductsList());

        } catch (IOException ioe) {
            System.out.println("Error while writing data: " + ioe);
            ioe.printStackTrace();
        }
    }

    public void deserializationOfProducts() {
        try (FileInputStream fis = new FileInputStream("productsData");
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            ArrayList<Product> productsList = (ArrayList<Product>) ois.readObject();
            service.getProductsList().clear();
            service.getProductsList().addAll(productsList);

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error while reading data: " + e);
            e.printStackTrace();
        }
    }
    public void serializationOfMeals() {
        try (FileOutputStream fos = new FileOutputStream("mealsData");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            oos.writeObject(service.getMealsList());

        } catch (IOException ioe) {
            System.out.println("Error while writing data: " + ioe);
            ioe.printStackTrace();
        }
    }

    public void deserializationOfMeals() {

        try (FileInputStream fis = new FileInputStream("mealsData");
             ObjectInputStream ois = new ObjectInputStream(fis)) {


            ArrayList<Meal> mealsList = (ArrayList<Meal>) ois.readObject();
            service.getMealsList().clear();
            service.getMealsList().addAll(mealsList);

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error while reading data: " + e);
            e.printStackTrace();
        }
    }
}

