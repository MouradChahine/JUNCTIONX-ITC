package com.example.junctionx;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ProductSearch {
    private String prodName, type, category;
    private String id, prix;
    private String rate;

    public StringProperty getProdName() {
        return  new SimpleStringProperty(prodName);
    }

    public StringProperty getType() {
        return new SimpleStringProperty(type);
    }

    public StringProperty getCategory() {
        return new SimpleStringProperty(category);
    }

    public StringProperty getId() {
        SimpleStringProperty simpleStringProperty = new SimpleStringProperty(id);
        return simpleStringProperty;
    }

    public StringProperty getPrix() {
        SimpleStringProperty simpleStringProperty = new SimpleStringProperty(prix);
        return simpleStringProperty;    }

    public StringProperty getRate() {
        SimpleStringProperty simpleStringProperty = new SimpleStringProperty(rate);
        return simpleStringProperty;
    }

    public ProductSearch(String prodName, String type, String category, String id, String prix, String rate) {
        this.category=category;
        this.prix=prix;
        this.id=id;
        this.type=type;
        this.prodName=prodName;
        this.rate=rate;
    }
}
