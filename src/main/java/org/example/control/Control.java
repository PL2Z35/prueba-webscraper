package org.example.control;

import org.example.logic.Product;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class Control {

    public void getProducts() throws IOException, SQLException {
        ProductDao productDao = new ProductDao();
        ArrayList<Product> productArrayList = new ArrayList<>();
        Scraping scraping = new Scraping();
        ArrayList<String> links = scraping.getLinks();
        for(String link: links){
            Product product = scraping.getProduct(link);
            productArrayList.add(product);
            if(productDao.existProduct(product)){
                productDao.updateProduct(product);
                System.out.println("Producto " + productArrayList.size() + " Agregado");
            }else{
                productDao.addProduct(product);
                System.out.println("Producto " + productArrayList.size() + " Modificado");
            }
        }
        toString(productArrayList);
    }

    public void toString(ArrayList<Product> products){
        for(Product product: products){
            System.out.println(product.toString());
        }
    }
}
