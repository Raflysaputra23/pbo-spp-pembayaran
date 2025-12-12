/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kelompok.spp.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import kelompok.spp.lib.Alerts;
import kelompok.spp.lib.FormatNumber;
import kelompok.spp.lib.UserIsLogin;
import kelompok.spp.model.PaymentModel;
import kelompok.spp.model.object.UserModel;

/**
 *
 * @author rafly
 */
public class PaymentController implements Initializable {

    private final UserModel userIsLogin = UserIsLogin.getSession();
    private final ArrayList<String> k = new ArrayList<>();

    @FXML
    private TilePane containerCard;

    @FXML
    private Text totalHarga;

    @FXML
    private Button submit;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        k.clear();
        ObservableList<Map<String, Object>> dataBulan = PaymentModel.getBulan(userIsLogin.getUserId());
        Map<String, Object> hargaJurusan = PaymentModel.getHargaJurusan(userIsLogin.getUserId());
        int convertHarga = Integer.parseInt(hargaJurusan.get("HargaJurusan").toString());
        renderCard(dataBulan, convertHarga);

        submit.setOnAction(e -> {
            try {
                Alerts alert = new Alerts();
                ComboBox<String> combo = new ComboBox<>();
                combo.setItems(FXCollections.observableArrayList("Dana", "Ovo", "BRI", "BNI"));
                combo.setPromptText("Pilih Metode Pembayaran");
                combo.getStyleClass().add("input");
                combo.setPadding(new Insets(6));
                VBox group = new VBox(10);
                group.setAlignment(Pos.CENTER);

                Label label = new Label("Metode Pembayaran");
                label.getStyleClass().add("poppins-regular");
                label.setStyle("-fx-fill: #fff; -fx-text-fill: #fff");
                group.getChildren().addAll(label, combo);

                boolean result = alert.alertConfirmation("Konfirmasi Pembayaran", "Apakah anda yakin ingin membayar spp ini?");
                if (result) {
                    boolean res = alert.alertCustom("Pembayaran", group);
                    if (res) {
                        if (combo.getValue() != null) {
                            boolean response = PaymentModel.bayarSpp(k, userIsLogin.getUserId(), convertHarga, combo.getValue());
                            if (response) {
                                alert.alertInformation("Informasi", "Anda berhasil membayar SPP sebanyak " + k.size() + " bulan.");
                            } else {
                                alert.alertInformation("Informasi", "Anda gagal membayar SPP sebanyak " + k.size() + " bulan, coba ulangi lagi.");
                            }
                            ObservableList<Map<String, Object>> dataBulanNew = PaymentModel.getBulan(userIsLogin.getUserId());
                            renderCard(dataBulanNew, convertHarga);
                            totalHarga.setText("0");
                        } else {
                            alert.alertInformation("Informasi", "Metode Pembayaran Tidak Boleh Kosong!");
                        }

                    }

                }

            } catch (SQLException ex) {
                System.out.println("Ada kesalahan: " + ex);
            }
        });
    }

    private void keranjang(Button btn, String bulanId, int hargaJurusan) {
        if (k.contains(bulanId)) {
            k.remove(bulanId);
            btn.getStyleClass().add("bg-primary");
            btn.getStyleClass().remove("bg-success");
        } else {
            btn.getStyleClass().remove("bg-primary");
            btn.getStyleClass().add("bg-success");
            k.add(bulanId);
        }

        if (!k.isEmpty()) {
            submit.setDisable(false);
        } else {
            submit.setDisable(true);
        }

        int hargaPerbulan = hargaJurusan * k.size();
        totalHarga.setText(FormatNumber.format(String.valueOf(hargaPerbulan)));
    }

    private void renderCard(ObservableList<Map<String, Object>> dataBulan, int hargaJurusan) {
        containerCard.getChildren().clear();
        k.clear();

        for (Map<String, Object> data : dataBulan) {
            VBox card = new VBox();
            card.setAlignment(Pos.CENTER);
            card.getStyleClass().add("card");
            card.setPrefWidth(240);
            card.setPrefHeight(120);
            card.setSpacing(5);
            card.setFillWidth(false);

            Text bulan = new Text();
            bulan.setText(data.get("NamaBulan").toString());
            bulan.getStyleClass().add("poppins-semibold");
            bulan.setStyle("-fx-font-size: 18px; -fx-font-weight: bold");

            Text keterangan = new Text();
            keterangan.setText(data.get("Lunas") == null ? "Belum Lunas" : "Lunas");
            keterangan.getStyleClass().add("poppins-semibold");
            keterangan.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-fill: #fff");

            HBox boxText = new HBox(keterangan);
            boxText.setAlignment(Pos.CENTER);
            boxText.setPadding(new Insets(4, 8, 4, 8));
            String warnaBgBox = (data.get("Lunas") == null) ? "-fx-background-color: #ff0000" : "-fx-background-color: #00ff00";
            boxText.setStyle("-fx-background-radius: 8; " + warnaBgBox);

            Button btn = new Button();
            btn.setPrefWidth(80);
            btn.setPrefHeight(30);
            btn.setText("+ CARD");
            btn.getStyleClass().addAll("bg-primary", "poppins-regular");
            btn.setStyle("-fx-text-fill: #fff");
            if (data.get("Lunas") != null) {
                btn.getStyleClass().add("bg-success");
                btn.setDisable(true);
            }
            btn.setCursor(Cursor.HAND);
            btn.setOnAction(e -> keranjang(btn, data.get("BulanID").toString(), hargaJurusan));

            card.getChildren().addAll(bulan, boxText, btn);
            containerCard.getChildren().add(card);
        }
    }

}
