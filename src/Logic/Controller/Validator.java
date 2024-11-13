package Logic.Controller;

import Logic.Model.Product;

public class Validator {
    private Service service;

    public Validator() {
        this.service = new Service();
    }

public boolean validateName(String name) {
    for(Product product : service.getProductsList() ){
        if(product.getProductName().equalsIgnoreCase(name)){
            return false;
        }
    }
    return true;
}
public boolean validateNumber(Double number) {
    if(number < 0)
    return false;
    else return true;
    }
}
