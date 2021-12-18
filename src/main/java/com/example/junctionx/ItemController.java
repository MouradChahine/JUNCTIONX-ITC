package com.example.junctionx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ItemController implements Initializable {
    @FXML
    private Label prodName;

    @FXML
    private Label type;

    @FXML
    private Label category;

    @FXML
    private Label description;

    @FXML
    private Label Prix;

    @FXML
    private ImageView img;

    @FXML
    private Label rate;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void setItems (String nom, String type, String category, String desc, String price, String rate) {
        prodName.setText(nom);
        this.type.setText(type);
        this.category.setText(category);
        this.description.setText(desc);
        this.rate.setText(rate);
        this.Prix.setText(price);
        String imageName = nom+".png";
        this.img.setImage(new Image(getClass().getResourceAsStream(imageName)));
    }
    public void Rate () {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Rating.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load());
            RatingController ratingController = fxmlLoader.getController();
            ratingController.setNomProd(prodName.getText());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void Localisation(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Local.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load());
            LocalController localController = fxmlLoader.getController();
            localController.setImg(prodName.getText());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
