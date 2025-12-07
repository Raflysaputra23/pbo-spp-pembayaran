/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kelompok.spp.controller;

import java.net.URL;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import kelompok.spp.lib.Alerts;
import kelompok.spp.lib.UserIsLogin;
import kelompok.spp.model.SiswaModel;
import kelompok.spp.model.object.UserModel;
import org.kordamp.ikonli.javafx.FontIcon;

/**
 *
 * @author rafly
 */
public class SiswaController implements Initializable {

    private final UserModel userIsLogin = UserIsLogin.getSession();

    @FXML
    private TableView<Map<String, Object>> table;

    @FXML
    private TableColumn<Map<String, Object>, Number> No;

    @FXML
    private TableColumn<Map<String, Object>, String> Nisn;

    @FXML
    private TableColumn<Map<String, Object>, String> NamaLengkap;

    @FXML
    private TableColumn<Map<String, Object>, String> Jenkel;

    @FXML
    private TableColumn<Map<String, Object>, String> Kelas;

    @FXML
    private TableColumn<Map<String, Object>, String> Jurusan;

    @FXML
    private TableColumn<Map<String, Object>, Void> Aksi;

    @FXML
    private TextField inputSearch;

    @FXML
    private MenuButton buttonView;

    @FXML
    private MenuItem semua;

    @FXML
    private MenuItem sedikit;

    @FXML
    private MenuItem sedang;

    @FXML
    private MenuItem banyak;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<Map<String, Object>> dataUsers = SiswaModel.getDataSiswaAll();

        No.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Number item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : String.valueOf(getIndex() + 1));
            }
        });

        Nisn.setCellValueFactory(cd
                -> new SimpleStringProperty(String.valueOf(cd.getValue().get("Nisn")))
        );

        NamaLengkap.setCellValueFactory(cd
                -> new SimpleStringProperty(String.valueOf(cd.getValue().get("NamaLengkap")))
        );

        Jenkel.setCellValueFactory(cd
                -> new SimpleStringProperty(String.valueOf(cd.getValue().get("Jenkel").equals("perempuan") ? "P" : "L"))
        );

        Kelas.setCellValueFactory(cd
                -> new SimpleStringProperty(String.valueOf(cd.getValue().get("Kelas")))
        );

        Jurusan.setCellValueFactory(cd
                -> new SimpleStringProperty(String.valueOf(cd.getValue().get("Jurusan")))
        );

        Aksi.setCellFactory(col -> new TableCell<>() {
            private final Button btnDelete = new Button();
            private final HBox box = new HBox(btnDelete);

            {
                box.setAlignment(Pos.CENTER_LEFT);
                btnDelete.setOnAction(e -> {
                    Alerts alert = new Alerts();
                    boolean response = alert.alertConfirmation("Konfirmasi", "Apakah anda yakin ingin menghapus data ini?");
                    if (response) {
                        Map<String, Object> row = getTableView().getItems().get(getIndex());
                        String nisn = String.valueOf(row.get("Nisn"));
                        handleHapus(nisn);
                    }
                });
                btnDelete.getStyleClass().add("bg-danger");
                btnDelete.setPrefWidth(55);
                btnDelete.setPrefHeight(45);
                btnDelete.setCursor(Cursor.HAND);
                btnDelete.setAlignment(Pos.CENTER);
                FontIcon iconTrash = new FontIcon("far-trash-alt");
                iconTrash.setIconSize(8);
                iconTrash.setIconColor(Paint.valueOf("#fff"));
                btnDelete.setGraphic(iconTrash);
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : box);
            }
        });

        if (userIsLogin != null & userIsLogin.getRole().equals("user")) {
            table.getColumns().remove(Aksi);
        }

        table.getItems().setAll(dataUsers);

        semua.setOnAction(e -> {
            String text = ((MenuItem) e.getSource()).getText();
            handleLimit(text);
        });
        sedikit.setOnAction(e -> {
            String text = ((MenuItem) e.getSource()).getText();
            handleLimit(text);
        });
        sedang.setOnAction(e -> {
            String text = ((MenuItem) e.getSource()).getText();
            handleLimit(text);
        });
        banyak.setOnAction(e -> {
            String text = ((MenuItem) e.getSource()).getText();
            handleLimit(text);
        });

    }

    public void handleSearch(KeyEvent e) {
        ObservableList<Map<String, Object>> dataUsers = SiswaModel.searchDataSiswaAll(inputSearch.getText());
        table.getItems().setAll(dataUsers);
    }

    public void handleLimit(String value) {
        int parseValue = value.equals("All") ? 0 : Integer.parseInt(value);
        ObservableList<Map<String, Object>> dataUsers;
        if (parseValue == 0) {
            dataUsers = SiswaModel.getDataSiswaAll();
        } else {
            dataUsers = SiswaModel.getDataSiswaAll(parseValue);
        }
        buttonView.setText(parseValue == 0 ? "All" : String.valueOf(parseValue));
        table.getItems().setAll(dataUsers);
    }

    private void handleHapus(String nisn) {
        boolean response = SiswaModel.deleteDataSiswaById(nisn);
        Alerts alert = new Alerts();
        if (response) {
            alert.alertInformation("Informasi", "Data berhasil dihapus");
        } else {
            alert.alertInformation("Informasi", "Data gagal dihapus");
        }

        ObservableList<Map<String, Object>> dataUsers = SiswaModel.getDataSiswaAll();
        table.getItems().setAll(dataUsers);
    }

}
