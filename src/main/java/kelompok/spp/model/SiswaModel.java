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
public class SiswaModel {

    private static final Database db = new Database();

    public static ObservableList<Map<String, Object>> getDataSiswaAll() {
        try {
            db.query("select s.Nisn, s.NamaLengkap, s.Foto, s.Email, s.Jenkel, s.Kelas, j.SingkatanJurusan as Jurusan from siswa as s join jurusan as j on j.JurusanID = s.JurusanID");
            db.execute();
            return db.resultSet();
        } catch (SQLException e) {
            System.out.println("Error get data: " + e);
            return FXCollections.observableArrayList();
        }

    }

    public static ObservableList<Map<String, Object>> searchDataSiswaAll(String search) {
        try {
            db.query("select s.Nisn, s.NamaLengkap, s.Foto, s.Email, s.Jenkel, s.Kelas, j.SingkatanJurusan as Jurusan from siswa as s join jurusan as j on j.JurusanID = s.JurusanID where s.Nisn LIKE ? OR s.NamaLengkap LIKE ? OR s.Kelas LIKE ? OR j.SingkatanJurusan LIKE ? OR J.NamaJurusan LIKE ?");
            db.bind(1, search + "%");
            db.bind(2, search + "%");
            db.bind(3, search + "%");
            db.bind(4, search + "%");
            db.bind(5, search + "%");
            db.execute();
            return db.resultSet();
        } catch (SQLException e) {
            System.out.println("Get data error: " + e);
            return FXCollections.observableArrayList();
        }
    }

    public static ObservableList<Map<String, Object>> getDataSiswaAll(int limit) {
        try {
            db.query("select s.Nisn, s.NamaLengkap, s.Foto, s.Email, s.Jenkel, s.Kelas, j.SingkatanJurusan as Jurusan from siswa as s join jurusan as j on j.JurusanID = s.JurusanID LIMIT ?");
            db.bind(1, limit);
            db.execute();
            return db.resultSet();
        } catch (SQLException e) {
            System.out.println("Get data error: " + e);
            return FXCollections.observableArrayList();
        }

    }

    public static Boolean deleteDataSiswaById(String nisn) {
        try {
            db.query("DELETE FROM siswa where Nisn = ?");
            db.bind(1, nisn);
            db.execute();
            return db.rowCount() > 0;
        } catch (SQLException e) {
            System.out.println("Errordelete data: " + e);
            return false;
        }
    }
}
