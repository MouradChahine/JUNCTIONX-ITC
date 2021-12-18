package com.example.junctionx;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class LocalController implements Initializable {
    @FXML
    private Button close;
    @FXML
    private ImageView img;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void close () {
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }
    public void setImg (String link) {
        String imageName = link+"local.png";
        this.img.setImage(new Image(getClass().getResourceAsStream(imageName)));

    }
}
