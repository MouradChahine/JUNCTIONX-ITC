module com.example.junctionx {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.xerial.sqlitejdbc;
    requires java.sql;
    requires java.desktop;
    requires com.google.zxing;
    requires jfreesvg;
    opens com.example.junctionx to javafx.fxml;
    exports com.example.junctionx;
}