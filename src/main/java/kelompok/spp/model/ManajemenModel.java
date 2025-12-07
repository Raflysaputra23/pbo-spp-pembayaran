/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kelompok.spp.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import kelompok.spp.database.Database;

/**
 *
 * @author rafly
 */
public class ManajemenModel {

    private static final Database db = new Database();

    public static ObservableList<Map<String, Object>> getDataJurusan() {
        try {
            db.query("Select j.JurusanID, j.SingkatanJurusan, j.NamaJurusan, j.HargaJurusan, COUNT(s.Nisn) as TotalSiswa from jurusan as j left join siswa as s on s.JurusanID = j.JurusanID GROUP BY j.JurusanID, j.SingkatanJurusan, j.NamaJurusan, j.HargaJurusan");
            db.execute();
            return db.resultSet();
        } catch (SQLException e) {
            System.out.println("Error get data: " + e);
            return FXCollections.observableArrayList();
        }
    }

    public static boolean addJurusan(String nama, String singkatan, int harga) {
        try {
            Random rand = new Random();
            int kode = rand.nextInt(9000) + 1000;
            String kodeJurusan = kode + singkatan;
            db.query("insert into jurusan(JurusanID, NamaJurusan, SingkatanJurusan, HargaJurusan) VALUES (?,?,?,?)");
            db.bind(1, kodeJurusan);
            db.bind(2, nama);
            db.bind(3, singkatan);
            db.bind(4, harga);
            db.execute();
            return db.rowCount() > 0;
        } catch (SQLException e) {
            System.out.println("Error tambah jurusan: " + e);
            return false;
        }
    }

    public static boolean deleteJurusan(ObservableList<Map<String, Object>> idJurusan) throws SQLException {
        try {
            db.beginTransaction();
            for (Map<String, Object> id : idJurusan) {
                db.query("delete from jurusan WHERE JurusanID = ?");
                db.bind(1, id.get("JurusanID").toString());
                db.execute();
            }
            db.commit();
            return db.rowCount() > 0;
        } catch (SQLException e) {
            db.rollback();
            System.out.println("Error tambah jurusan: " + e);
            return false;
        }
    }

    public static boolean updateJurusan(ObservableList<Map<String, Object>> data) throws SQLException {
        try {
            
            db.beginTransaction();
            for (Map<String, Object> j : data) {
                db.query("Update jurusan set NamaJurusan = ?, SingkatanJurusan = ?, HargaJurusan = ? WHERE JurusanID = ?");
                db.bind(1, j.get("NamaJurusan").toString());
                db.bind(2, j.get("SingkatanJurusan").toString());
                db.bind(3, j.get("HargaJurusan"));
                db.bind(4, j.get("JurusanID").toString());
                db.execute();
            }
            db.commit();
            return db.rowCount() > 0;
        } catch (SQLException e) {
            db.rollback();
            System.out.println("Error tambah jurusan: " + e);
            return false;
        }
    }
}
