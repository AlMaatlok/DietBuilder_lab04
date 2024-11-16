package Logic.Controller;

import Logic.Model.Diet;
import Logic.Model.Meal;
import Logic.Model.Product;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

public class ShoppingList {
    private Diet diet;

    public ShoppingList(Diet diet) {
        this.diet = diet;
    }

    public Map<String, Map<String, Double>> generateShoppingList() {
        Map<String, Map<String, Double>> shoppingList = new HashMap<>();

        for (Meal meal : diet.getMeals()) {
            for (Map.Entry<Product, Double> entry : meal.getIngredients().entrySet()) {
                Product product = entry.getKey();
                double quantity = entry.getValue();

                String category = product.getType();
                String productName = product.getProductName();
                Double quantityOfProduct = product.getQuantity()*quantity;

                shoppingList.putIfAbsent(category, new HashMap<>());

                Map<String, Double> productsInCategory = shoppingList.get(category);
                productsInCategory.merge(productName, quantityOfProduct, Double::sum);
            }
        }
        return shoppingList;
    }

    public void generatePDF(String filePath) {
        Map<String, Map<String, Double>> shoppingList = generateShoppingList();

        try {
            PdfWriter writer = new PdfWriter(filePath);
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument);

            document.add(new Paragraph("Lista Zakupów"));
            document.add(new Paragraph(" "));

            for (String category : shoppingList.keySet()) {
                document.add(new Paragraph("Kategoria: " + category));
                for (Map.Entry<String, Double> entry : shoppingList.get(category).entrySet()) {
                    document.add(new Paragraph(" - " + entry.getKey() + ": " + entry.getValue() + "g"));
                }
                document.add(new Paragraph(" "));
            }

            document.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}