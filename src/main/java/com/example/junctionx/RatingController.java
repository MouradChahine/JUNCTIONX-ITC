package com.example.junctionx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class RatingController implements Initializable{
    @FXML
    private ImageView img1,img2,img3,img4,img5;
    String nom;
    int rating=1;
    File file = new File("et1.png");
    File file1 = new File("et2.png");
    //Image image = new Image(file.toURI().toString());
    //Image image1 = new Image(file1.toURI().toString());
    Image image = new Image(getClass().getResourceAsStream("et1.png"));
    Image image1 = new Image(getClass().getResourceAsStream("et2.png"));

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setNomProd(String nom) {
        this.nom=nom;
    }
    public void Rate1(MouseEvent mouseEvent) {
        img2.setImage(image);
        img3.setImage(image);
        img4.setImage(image);
        img5.setImage(image);
        rating=1;
    }
    public void Rate2(MouseEvent mouseEvent) {
        img2.setImage(image1);
        img3.setImage(image);
        img4.setImage(image);
        img5.setImage(image);
        rating=2;
    }

    public void Rate3(MouseEvent mouseEvent) {
        img2.setImage(image1);
        img3.setImage(image1);
        img4.setImage(image);
        img5.setImage(image);
        rating=3;
    }

    public void Rate4(MouseEvent mouseEvent) {
        img2.setImage(image1);
        img3.setImage(image1);
        img4.setImage(image1);
        img5.setImage(image);
        rating=4;
    }

    public void Rate5(MouseEvent mouseEvent) {
        img2.setImage(image1);
        img3.setImage(image1);
        img4.setImage(image1);
        img5.setImage(image1);
        rating=5;
    }
    private Connection connect() {
        try {
            // SQLite connection string
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:junctionx.sqlite";
            Connection conn = null;
            try {
                try {
                    conn = DriverManager.getConnection(url);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return conn;
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public void Submit(){
        int id = 0; double rate = 0;
        String rqst ="SELECT id,note FROM products WHERE nom_prod='"+nom+"'";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(rqst)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
            id =rs.getInt("id");
            rate =rs.getDouble("note");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    rate=(rate+rating)/2;
        rate =(int) Math.floor(rate*10);
        rate=rate/10;
         rqst ="UPDATE products SET note="+rate+" WHERE id="+id;
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(rqst)) {
            pstmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Stage stage = (Stage) img1.getScene().getWindow();
        stage.close();
    }
}
