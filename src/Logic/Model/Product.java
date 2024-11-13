package Logic.Model;

import java.io.Serializable;

public class Product implements Serializable {
    private static final long serialVersionUID = 1L;

    private String productName;
    private double carbs;
    private double fats;
    private double protein;;
    private String type;
    private double quantity;

    public Product(String name, double carbs, double fats, double protein, String type, double quantity) {
        this.productName = name;
        this.carbs = carbs;
        this.fats = fats;
        this.protein = protein;
        this.type = type;
        this.quantity = quantity;
    }
    public double calculateCalories() {
        return (carbs * 4) + (fats * 9) + (protein * 4);
    }

    public void editProduct(String name, double carbs, double fats, double protein, String type, double quantity) {
        this.productName = name;
        this.carbs = carbs;
        this.fats = fats;
        this.protein = protein;
        this.type = type;
        this.quantity = quantity;
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

    public String toString() {
        return "Produkt: " + productName +
                "\nKategoria: " + type +
                "\nWęglowodany: " + carbs +
                "g\nTłuszcze: " + fats +
                "g\nBiałko: " + protein +
                "g\nKalorie: " + calculateCalories() + " kcal";
    }
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Product)) return false;
        Product other = (Product) obj;
        return this.productName.equals(other.productName);
    }

    public int hashCode() {
        return productName.hashCode();
    }

}
