package org.example.control;

import org.example.logic.Product;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;

public class Scraping {

    Scraping(){
        System.setProperty("webdriver.edge.driver", "C:\\Users\\cplaz\\OneDrive\\Documentos\\edgedriver_win64\\msedgedriver.exe");
    }
    private static String url = "https://www.ktronix.com/computadores-tablet/computadores-portatiles/c/BI_104_KTRON";

    public ArrayList<String> getLinks() throws IOException {
        ArrayList<String> list = new ArrayList<>();
        WebDriver driver = new EdgeDriver();
        driver.get(url);
        System.out.println("Iniciando Lista");
        for (WebElement link : driver.findElements(By.tagName("a"))) {
            if(link.getAttribute("class").equals("js-algolia-product-click js-algolia-product-title")) {
                list.add(link.getAttribute("href"));
            }
            if(list.size()>=5){
                break;
            }
        }
        System.out.println("Lista Completada");
        driver.quit();
        return list;
    }

    public Product getProduct(String url) throws IOException {
        WebDriver driver = new EdgeDriver();
        driver.get(url);
        driver.manage().window().maximize();
        WebElement headerTitleDiv = driver.findElement(By.className("new-container__header__title"));
        WebElement h1ElementTitle = headerTitleDiv.findElement(By.tagName("h1"));
        WebElement headerPriceSpan = driver.findElement(By.id("js-original_price"));
        WebElement codeSpan = driver.findElement(By.className("js-ean-pdp"));
        Product product = new Product( codeSpan.getText(), h1ElementTitle.getText(), Double.parseDouble(headerPriceSpan.getText().replaceAll("[^\\d]", "")));
        driver.quit();
        return product;
    }
}
