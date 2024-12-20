package Logic.Model;

import java.io.Serializable;
import java.util.Objects;

public class Product implements Serializable {
    private static final long serialVersionUID = 1L;

    private String productName;
    private double carbs;
    private double fats;
    private double protein;;
    private String type;
    private double quantity;
    private boolean isUsed;

    public Product(String name, double carbs, double fats, double protein, String type, double quantity, boolean isUsed ) {
        this.productName = name;
        this.carbs = carbs;
        this.fats = fats;
        this.protein = protein;
        this.type = type;
        this.quantity = quantity;
        this.isUsed = isUsed;
    }

    public double calculateCalories() {
        return (carbs * 4.15) + (fats * 9.45) + (protein * 5.65);
    }

    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public double getCarbs() {
        return carbs;
    }
    public void setCarbs(double carbs) {
        this.carbs = carbs;
    }

    public double getFats() {
        return fats;
    }
    public void setFats(double fats) {
        this.fats = fats;
    }
    public double getProtein() {
        return protein;
    }
    public void setProtein(double protein) {
        this.protein = protein;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public double getQuantity() {
        return quantity;
    }
    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
    public boolean getUsed(){
        return isUsed;
    }
    public void setUsed(boolean isUsed){
        this.isUsed = isUsed;
    }
    @Override
    public String toString() {
        return "Produkt: " + productName +
                "\nKategoria: " + type +
                "\nWęglowodany: " + carbs +
                "g\nTłuszcze: " + fats +
                "g\nBiałko: " + protein +
                "g\nKalorie: " + calculateCalories() + " kcal";
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Double.compare(product.carbs, carbs) == 0 &&
                Double.compare(product.fats, fats) == 0 &&
                Double.compare(product.protein, protein) == 0 &&
                Objects.equals(productName, product.productName);
    }
    @Override
    public int hashCode() {
        return Objects.hash(productName);
    }

}
