module kelompok.spp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;
    requires mysql.connector.j;
    requires jbcrypt;
    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.fontawesome5;

    opens kelompok.spp.controller to javafx.fxml;
    exports kelompok.spp;
    exports kelompok.spp.controller;
}
