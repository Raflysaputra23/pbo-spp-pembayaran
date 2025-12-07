/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kelompok.spp.model;

import java.sql.SQLException;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import kelompok.spp.database.Database;

/**
 *
 * @author rafly
 */
public class SppModel {

    private static final Database db = new Database();

    public static ObservableList<Map<String, Object>> getDataSppAll() {
        try {
            db.query("Select s.Foto, s.Nisn, s.NamaLengkap, s.Kelas, j.SingkatanJurusan, j.HargaJurusan from siswa as s join jurusan as j on s.JurusanID = j.JurusanID");
            db.execute();
            return db.resultSet();
        } catch (SQLException e) {
            System.out.println("Gagal get data: " + e);
            return FXCollections.observableArrayList();
        }
    }

    public static ObservableList<Map<String, Object>> searchDataSpp(String search) {
        try {
            db.query("Select s.Foto, s.Nisn, s.NamaLengkap, s.Kelas, j.SingkatanJurusan, j.HargaJurusan from siswa as s join jurusan as j on s.JurusanID = j.JurusanID WHERE CONCAT_WS(s.Nisn, s.NamaLengkap, s.Kelas, j.SingkatanJurusan) LIKE ?");
            db.bind(1, search + "%");
            db.execute();
            return db.resultSet();
        } catch (SQLException e) {
            System.out.println("Gagal get data: " + e);
            return FXCollections.observableArrayList();
        }
    }
}
