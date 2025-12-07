/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kelompok.spp.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import kelompok.spp.lib.Alerts;
import kelompok.spp.lib.VisibleNode;
import kelompok.spp.model.ManajemenModel;
import org.kordamp.ikonli.javafx.FontIcon;

/**
 *
 * @author rafly
 */
public class ManajemenController implements Initializable {

    private ObservableList<Map<String, Object>> cardId = FXCollections.observableArrayList();

    @FXML
    TextField namaJurusan;
    @FXML
    TextField singkatanJurusan;
    @FXML
    TextField hargaJurusan;

    @FXML
    FlowPane containerJurusan;
    @FXML
    Button btnSubmit;
    @FXML
    VBox containerTambahJurusan;

    @FXML
    VBox containerEditJurusan;

    @FXML
    Button btnHapus;

    @FXML
    Button btnTambah;

    @FXML
    Button btnEdit;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        VisibleNode.hideNode(containerTambahJurusan);
        VisibleNode.hideNode(containerEditJurusan);
        cardId.clear();
        loadData();

        hargaJurusan.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().matches("\\d*")) {
                return change;
            }
            return null;
        }));

        btnSubmit.setOnAction(e -> {
            if (namaJurusan.getText().length() > 0 && singkatanJurusan.getText().length() > 0 && hargaJurusan.getText().length() > 0) {
                boolean response = ManajemenModel.addJurusan(namaJurusan.getText(), singkatanJurusan.getText(), Integer.parseInt(hargaJurusan.getText()));
                Alerts alert = new Alerts();
                if (response) {
                    alert.alertInformation("Informasi", "Jurusan " + namaJurusan.getText() + " berhasil ditambahkan");
                    loadData();
                } else {
                    alert.alertInformation("Informasi", "Jurusan " + namaJurusan.getText() + " gagal ditambahkan");
                }
                namaJurusan.setText("");
                singkatanJurusan.setText("");
                hargaJurusan.setText("");
            } else {
                Alerts alert = new Alerts();
                alert.alertInformation("Informasi", "Pastikan semua data terisi!");
            }

        });
    }

    private void loadData() {
        ObservableList<Map<String, Object>> dataJurusan = ManajemenModel.getDataJurusan();
        renderCard(dataJurusan);
    }

    private void renderCard(ObservableList<Map<String, Object>> dataJurusan) {
        containerJurusan.getChildren().clear();
        for (Map<String, Object> data : dataJurusan) {
            DropShadow shadow = new DropShadow();
            shadow.setBlurType(BlurType.TWO_PASS_BOX);
            shadow.setColor(Color.rgb(0, 0, 0, 0.2));

            HBox card = new HBox();
            card.getStyleClass().add("card");
            card.setPrefWidth(240);
            card.setPrefHeight(107);
            card.setEffect(shadow);

            VBox boxIcon = new VBox();
            boxIcon.setAlignment(Pos.CENTER);
            boxIcon.getStyleClass().add("bg-primary");
            boxIcon.setStyle("-fx-background-radius: 10");
            HBox.setHgrow(boxIcon, Priority.ALWAYS);
            FontIcon icon = new FontIcon("fas-user");
            icon.setIconSize(35);
            icon.setIconColor(Paint.valueOf("#fff"));
            boxIcon.getChildren().add(icon);

            VBox boxData = new VBox();
            HBox.setHgrow(boxData, Priority.ALWAYS);
            boxData.setAlignment(Pos.CENTER);
            boxData.setSpacing(5);

            Text nJurusan = new Text();
            nJurusan.setText(data.get("SingkatanJurusan").toString());
            nJurusan.getStyleClass().add("poppins-semibold");
            nJurusan.setStyle("-fx-font-size: 18; -fx-font-weight: bold");

            Text total = new Text();
            total.setText(data.get("TotalSiswa").toString());
            total.getStyleClass().add("poppins-regular");
            total.setStyle("-fx-font-size: 14");
            boxData.getChildren().addAll(nJurusan, total);

            card.getChildren().addAll(boxIcon, boxData);
            card.setOnMouseClicked(e -> {
                String id = data.get("JurusanID").toString();
                boolean find = cardId.stream()
                        .anyMatch(map -> map.get("JurusanID").toString().equals(id));

                if (find) {
                    cardId.removeIf(map -> map.get("JurusanID").toString().equals(id));
                    card.setStyle("-fx-background-color: #fff");
                } else {
                    card.setStyle("-fx-background-color: rgba(0,0,0,0.2)");
                    Map<String, Object> j = new HashMap<>();
                    j.put("JurusanID", data.get("JurusanID"));
                    j.put("NamaJurusan", data.get("NamaJurusan"));
                    j.put("SingkatanJurusan", data.get("SingkatanJurusan"));
                    j.put("HargaJurusan", data.get("HargaJurusan"));
                    cardId.add(j);
                }

                if (!cardId.isEmpty()) {
                    btnEdit.setDisable(false);
                    btnHapus.setDisable(false);
                } else {
                    btnEdit.setDisable(true);
                    btnHapus.setDisable(true);
                }
                renderInput();
            });
            containerJurusan.getChildren().add(card);
        }
    }

    @FXML
    public void handleTambahJurusan(ActionEvent e) {
        String triger = btnTambah.getId();
        if (triger.equals("open")) {
            btnTambah.setId("close");
            VisibleNode.showNode(containerTambahJurusan);
        } else {
            btnTambah.setId("open");
            VisibleNode.hideNode(containerTambahJurusan);
        }
    }

    @FXML
    public void handleHapusJurusan(ActionEvent e) throws SQLException {
        if (!cardId.isEmpty()) {
            Alerts alert = new Alerts();
            boolean result = alert.alertConfirmation("Konfirmasi", "Apakah anda yakin ingin menghapus data jurusan ini?");
            if (result) {
                boolean response = ManajemenModel.deleteJurusan(cardId);
                if (response) {
                    alert.alertInformation("Informasi", "Jurusan berhasil dihapus!");
                    loadData();
                } else {
                    alert.alertInformation("Informasi", "Jurusan gagal dihapus!");
                }
                btnHapus.setDisable(true);
                btnEdit.setDisable(true);
                cardId.clear();
            }
        }
    }

    public void renderInput() {
        containerEditJurusan.getChildren().clear();
        for (Map<String, Object> data : cardId) {
            HBox boxContainer = new HBox();
            boxContainer.setPrefHeight(Region.USE_COMPUTED_SIZE);
            boxContainer.setSpacing(10);

            VBox formGroup1 = new VBox();
            formGroup1.setPrefWidth(350);
            formGroup1.setPrefHeight(Region.USE_COMPUTED_SIZE);

            Label LabelnamaJurusan = new Label("Nama Jurusan");
            LabelnamaJurusan.getStyleClass().add("poppins-regular");
            LabelnamaJurusan.setStyle("-fx-font-size: 13");

            TextField inputNamaJurusan = new TextField();
            inputNamaJurusan.setText(data.get("NamaJurusan").toString());
            inputNamaJurusan.getStyleClass().addAll("input", "poppins-regular");
            inputNamaJurusan.setPadding(new Insets(6));
            inputNamaJurusan.setOnKeyReleased(e -> handleChangeInput(data.get("JurusanID").toString(), "NamaJurusan", inputNamaJurusan.getText()));

            formGroup1.getChildren().addAll(LabelnamaJurusan, inputNamaJurusan);

            VBox formGroup2 = new VBox();
            formGroup2.setPrefWidth(350);
            formGroup2.setPrefHeight(Region.USE_COMPUTED_SIZE);

            Label LabelsingkatanJurusan = new Label("Singkatan Jurusan");
            LabelsingkatanJurusan.getStyleClass().add("poppins-regular");
            LabelsingkatanJurusan.setStyle("-fx-font-size: 13");

            TextField inputSingkatanJurusan = new TextField();
            inputSingkatanJurusan.setText(data.get("SingkatanJurusan").toString());
            inputSingkatanJurusan.getStyleClass().addAll("input", "poppins-regular");
            inputSingkatanJurusan.setPadding(new Insets(6));
            inputSingkatanJurusan.setOnKeyReleased(e -> handleChangeInput(data.get("JurusanID").toString(), "SingkatanJurusan", inputSingkatanJurusan.getText()));

            formGroup2.getChildren().addAll(LabelsingkatanJurusan, inputSingkatanJurusan);

            VBox formGroup3 = new VBox();
            formGroup3.setPrefWidth(350);
            formGroup3.setPrefHeight(Region.USE_COMPUTED_SIZE);

            Label LabelHargaJurusan = new Label("Harga Jurusan");
            LabelHargaJurusan.getStyleClass().add("poppins-regular");
            LabelHargaJurusan.setStyle("-fx-font-size: 13");

            TextField inputHargaJurusan = new TextField();
            inputHargaJurusan.setText(data.get("HargaJurusan").toString());
            inputHargaJurusan.getStyleClass().addAll("input", "poppins-regular");
            inputHargaJurusan.setPadding(new Insets(6));
            inputHargaJurusan.setTextFormatter(new TextFormatter<>(change -> {
                if (change.getControlNewText().matches("\\d*")) {
                    return change;
                }
                return null;
            }));
            inputHargaJurusan.setOnKeyReleased(e -> handleChangeInput(data.get("JurusanID").toString(), "HargaJurusan", inputHargaJurusan.getText()));

            formGroup3.getChildren().addAll(LabelHargaJurusan, inputHargaJurusan);

            boxContainer.getChildren().addAll(formGroup1, formGroup2, formGroup3);
            containerEditJurusan.getChildren().add(boxContainer);
        }

        if (!cardId.isEmpty()) {
            Button btnSub = new Button();
            btnSub.setText("Submit");
            btnSub.setPrefWidth(77);
            btnSub.getStyleClass().add("bg-primary");
            btnSub.setCursor(Cursor.HAND);
            btnSub.getStyleClass().add("poppins-regular");
            containerEditJurusan.getChildren().add(btnSub);

            btnSub.setOnAction(e -> {
                try {
                    Alerts alert = new Alerts();
                    boolean result = alert.alertConfirmation("Konfirmasi", "Apakah anda yakin ingin mengubah data ini?");
                    if (result) {
                        boolean response = ManajemenModel.updateJurusan(cardId);
                        if (response) {
                            alert.alertInformation("Informasi", "Jurusan berhasil diubah");
                        } else {
                            alert.alertInformation("Informasi", "Jurusan gagal diubah");
                        }
                        loadData();
                        btnEdit.setDisable(true);
                        btnHapus.setDisable(true);
                        containerEditJurusan.getChildren().clear();
                        cardId.clear();
                    }
                } catch (SQLException ex) {
                    System.out.println("Error update jurusan: " + ex);
                    cardId.clear();
                }
            });
        }
    }

    public void handleEditJurusan(ActionEvent e) throws SQLException {
        if (!cardId.isEmpty()) {
            String triger = btnEdit.getId();
            if (triger.equals("open")) {
                VisibleNode.showNode(containerEditJurusan);
                btnEdit.setId("close");
            } else {
                VisibleNode.hideNode(containerEditJurusan);
                btnEdit.setId("open");
            }
            renderInput();
        }
    }

    public void handleChangeInput(String jurusanID, String nama, String value) {
        for (Map<String, Object> map : cardId) {
            if (map.get("JurusanID").toString().equals(jurusanID)) {
                switch (nama) {
                    case "NamaJurusan":
                        map.put("NamaJurusan", value);
                        break;
                    case "SingkatanJurusan":
                        map.put("SingkatanJurusan", value);
                        break;
                    case "HargaJurusan":
                        map.put("HargaJurusan", value);
                        break;
                }
            }
        }
    }

}
