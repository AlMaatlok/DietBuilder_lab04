package Logic.Controller;

import Logic.Model.Product;


public class Validator {

    public Validator() {}

    public boolean validateNumber(Double number) {
        if (number < 0)
            return false;
        else return true;
    }
    public boolean validateOriginalProduct(Product product, Service service) {
        boolean value = false;
        for(Product product1 : service.getProductsList()) {
            if (product.getProductName().equalsIgnoreCase(product1.getProductName()))
                value = true;
        }
        return value;
    }
    public boolean validateSum(Product product) {
        double sum;
        sum = product.getCarbs() + product.getProtein() + product.getFats();
        if(sum > product.getQuantity()){
            return false;
        }
        else return true;
    }
}