package org.example;

import org.example.control.Control;
import org.example.control.Scraping;

import java.io.IOException;
import java.sql.SQLException;

public class Main {
    static Control control;
    public static void main(String[] args) throws IOException, SQLException {
        control = new Control();
        control.getProducts();
    }
}