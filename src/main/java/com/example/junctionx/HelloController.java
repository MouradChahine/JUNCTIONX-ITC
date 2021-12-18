package com.example.junctionx;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.jfree.graphics2d.svg.SVGGraphics2D;
import org.jfree.graphics2d.svg.ViewBox;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.*;
import java.util.Hashtable;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class HelloController implements Initializable {
    @FXML
    private Label SurveyCount,noProd;


    @FXML
    private Button btnAll,hamoud,nike,lg,dell,rouiba,link    ;
    @FXML
    private Pane acueil, Survey;
    @FXML
    private Pane searchInterface;

    @FXML
    private Button btnAlim;

    @FXML
    private Button btnAppliance;

    @FXML
    private Button btnCloths;
    @FXML
    private ToggleButton orderBy;

    @FXML
    private Button btnCosmetics;
    @FXML
    private HBox hboxItems;
    @FXML
    private VBox Menu;
    @FXML
    private Button btnComputers;

    @FXML
    private Button btnBack;

    @FXML
    private TextField searchField;

    String categorie;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        acueil.setVisible(true);
        searchInterface.setVisible(false);
        Survey.setVisible(false);

        String rqst ="SELECT * FROM products order by prix";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(rqst)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getInt("id"));
                FXMLLoader loader = new FXMLLoader(getClass().getResource("item.fxml"));
                Parent p = (Parent) loader.load();
                ItemController controller1 = loader.getController();
                System.out.println(controller1);
                String rate = String.valueOf(rs.getDouble("note"));
                String price = String.valueOf(rs.getInt("prix"))+" DA";
                System.out.println("rate ="+rate+" price = "+price+" nom = "+rs.getString("nom_prod")+" type = "+rs.getString("type") +" categ = "+rs.getString("categorie") +" descr = "+rs.getString("description"));
                controller1.setItems(rs.getString("nom_prod"),rs.getString("type"),rs.getString("categorie"),rs.getString("description"),price,rate);
                hboxItems.getChildren().add(p);
            }
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        }


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
    public void surveyIncrement () {
        String [] s = SurveyCount.getText().split(" : ");
        int a = Integer.parseInt(s[1]);
        if (a==4) {
            link.setVisible(true);
        }
        a++;
        SurveyCount.setText(s[0]+" : "+a);
    }
    public void OpenSurvey(ActionEvent e) {
        if (e.getSource()==hamoud) {
            hamoud.setDisable(true);
            File htmlFile = new File("getCoupan/Hamoud form.html");
            try {
                Desktop.getDesktop().browse(htmlFile.toURI());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        if (e.getSource()==nike) {
            nike.setDisable(true);
        }
        if (e.getSource()==lg) {
            lg.setDisable(true);
        }
        if (e.getSource()==dell) {
            dell.setDisable(true);
        }
        if (e.getSource()==rouiba) {
            rouiba.setDisable(true);
        }
    surveyIncrement();
    }
    public void SearchInterface() {
        acueil.setVisible(false);
        searchInterface.setVisible(true);
        ActionEvent e = new ActionEvent(btnAll, null);
        setCategory(e);
    }
    public void Survey () {
        acueil.setVisible(false);
        Survey.setVisible(true);

    }
    public void setCategory(ActionEvent e) {
        Menu.getChildren().forEach((child) -> {
            if (child instanceof Button) {
                child.getStyleClass().clear();
                child.getStyleClass().add("button1");
            }
        });
        if (e.getSource()==btnAll) {
            categorie="";
            applyPressedStyle(btnAll);
        }if (e.getSource()==btnAlim) {
            categorie=btnAlim.getText();
            applyPressedStyle(btnAlim);
        }if (e.getSource()==btnAppliance) {
            categorie=btnAppliance.getText();
            applyPressedStyle(btnAppliance);
        }if (e.getSource()==btnCloths) {
            categorie=btnCloths.getText();
            applyPressedStyle(btnCloths);
        }if (e.getSource()==btnComputers) {
            categorie=btnComputers.getText();
            applyPressedStyle(btnComputers);
        }if (e.getSource()==btnCosmetics) {
            categorie=btnCosmetics.getText();
            applyPressedStyle(btnCosmetics);
        }
        FillTable();
    }

    private void applyPressedStyle(Button button) {
        button.getStyleClass().clear();
        button.getStyleClass().add("button-press");
    }
    String order="prix";
    public void toggle() {
    if (orderBy.isSelected()) {
        orderBy.setText("RATE");
        order="note";
    } else {
        orderBy.setText("PRICE");
        order="prix";
    }
    FillTable();
    }
    public void Back () {
        acueil.setVisible(true);
        searchInterface.setVisible(false);
        Survey.setVisible(false);

    }
    public void FillTable () {
        hboxItems.getChildren().clear();
        /*for (int i=0;i<5;i++) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("item.fxml"));
            try {
                Parent p = (Parent) loader.load();
                ItemController controller1 = loader.getController();
                hboxItems.getChildren().add(p);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
    String rqst ="SELECT * FROM products WHERE nom_prod LIKE ? and categorie LIKE ? ORDER BY "+order;
    if (order.equals("prix")) rqst=rqst+" ASC"; else rqst=rqst+" DESC";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(rqst)) {
            pstmt.setString(1,"%"+searchField.getText()+"%");
            pstmt.setString(2,"%"+categorie+"%");
            System.out.println(rqst);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getInt("id"));
                FXMLLoader loader = new FXMLLoader(getClass().getResource("item.fxml"));
                Parent p = (Parent) loader.load();
                ItemController controller1 = loader.getController();
                System.out.println(controller1);
                String rate = String.valueOf(rs.getDouble("note"));
                String price = String.valueOf(rs.getInt("prix"))+" DA";
                System.out.println("rate ="+rate+" price = "+price+" nom = "+rs.getString("nom_prod")+" type = "+rs.getString("type") +" categ = "+rs.getString("categorie") +" descr = "+rs.getString("description"));
                controller1.setItems(rs.getString("nom_prod"),rs.getString("type"),rs.getString("categorie"),rs.getString("description"),price,rate);
                hboxItems.getChildren().add(p);
            }
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        }
    if(hboxItems.getChildren().size()==0){
    noProd.setVisible(true);
    }
    else
    {noProd.setVisible(false);}
    }
    public void browse() {
        File htmlFile = new File("getCoupan/getCoupan.html");
        try {
            Desktop.getDesktop().browse(htmlFile.toURI());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static BufferedImage getQRCode(String targetUrl, int width,
                                          int height) {
        try {
            Hashtable<EncodeHintType, Object> hintMap = new Hashtable<>();

            hintMap.put(EncodeHintType.ERROR_CORRECTION,
                    ErrorCorrectionLevel.L);
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix byteMatrix = qrCodeWriter.encode(targetUrl,
                    BarcodeFormat.QR_CODE, width, height, hintMap);
            int CrunchifyWidth = byteMatrix.getWidth();

            BufferedImage image = new BufferedImage(CrunchifyWidth,
                    CrunchifyWidth, BufferedImage.TYPE_INT_RGB);
            image.createGraphics();

            Graphics2D graphics = (Graphics2D) image.getGraphics();
            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, CrunchifyWidth, CrunchifyWidth);
            graphics.setColor(Color.BLACK);

            for (int i = 0; i < CrunchifyWidth; i++) {
                for (int j = 0; j < CrunchifyWidth; j++) {
                    if (byteMatrix.get(i, j)) {
                        graphics.fillRect(i, j, 1, 1);
                    }
                }
            }
            return image;
        } catch (WriterException e) {
            e.printStackTrace();
            throw new RuntimeException("Error getting QR Code");
        }

    }

    public static String getQRCodeSvg(String targetUrl, int width, int height, boolean withViewBox) {
        SVGGraphics2D g2 = new SVGGraphics2D(width, height);
        BufferedImage qrCodeImage = getQRCode(targetUrl, width, height);
        g2.drawImage(qrCodeImage, 0, 0, width, height, null);

        ViewBox viewBox = null;
        if (withViewBox) {
            viewBox = new ViewBox(0, 0, width, height);
        }

        return g2.getSVGElement(null, true, viewBox, null, null);
    }


}