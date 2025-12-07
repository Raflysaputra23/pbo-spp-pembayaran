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
public class HistoryModel {

    private static final Database db = new Database();

    public static ObservableList<Map<String, Object>> getDataHistoryAll(String sort) {
        try {
            if (sort.equals("ASC")) {
                db.query("select t.TranksaksiID, t.MetodePay, t.TotalHarga, t.CreateAt, GROUP_CONCAT(b.NamaBulan SEPARATOR ', ') as NamaBulan, GROUP_CONCAT(p.Harga SEPARATOR ', ') as Harga, GROUP_CONCAT(DISTINCT s.Nisn) as Nisn from tranksaksi as t join payment as p on p.TranksaksiID = t.TranksaksiID join bulan as b on b.BulanID = p.BulanID join siswa as s on s.Nisn = p.SiswaID GROUP BY t.TranksaksiID, t.MetodePay, t.TotalHarga, t.CreateAt ORDER BY t.CreateAt ASC");
                db.execute();
                return db.resultSet();
            } else {
                db.query("select t.TranksaksiID, t.MetodePay, t.TotalHarga, t.CreateAt, GROUP_CONCAT(b.NamaBulan SEPARATOR ', ') as NamaBulan, GROUP_CONCAT(p.Harga SEPARATOR ', ') as Harga, GROUP_CONCAT(DISTINCT s.Nisn) as Nisn from tranksaksi as t join payment as p on p.TranksaksiID = t.TranksaksiID join bulan as b on b.BulanID = p.BulanID join siswa as s on s.Nisn = p.SiswaID GROUP BY t.TranksaksiID, t.MetodePay, t.TotalHarga, t.CreateAt ORDER BY t.CreateAt DESC");
                db.execute();
                return db.resultSet();
            }
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }

    }

    public static ObservableList<Map<String, Object>> getDataHistoryById(String nisn, String sort) {
        try {
            if (sort.equals("ASC")) {
                db.query("select t.TranksaksiID, t.MetodePay, t.TotalHarga, t.CreateAt, GROUP_CONCAT(b.NamaBulan SEPARATOR ', ') as NamaBulan, GROUP_CONCAT(p.Harga SEPARATOR ', ') as Harga, GROUP_CONCAT(DISTINCT s.Nisn) as Nisn from tranksaksi as t join payment as p on p.TranksaksiID = t.TranksaksiID join bulan as b on b.BulanID = p.BulanID join siswa as s on s.Nisn = p.SiswaID WHERE s.Nisn = ? GROUP BY t.TranksaksiID, t.MetodePay, t.TotalHarga, t.CreateAt ORDER BY t.CreateAt ASC");
                db.bind(1, nisn);
                db.execute();
                return db.resultSet();
            } else {
                db.query("select t.TranksaksiID, t.MetodePay, t.TotalHarga, t.CreateAt, GROUP_CONCAT(b.NamaBulan SEPARATOR ', ') as NamaBulan, GROUP_CONCAT(p.Harga SEPARATOR ', ') as Harga, GROUP_CONCAT(DISTINCT s.Nisn) as Nisn from tranksaksi as t join payment as p on p.TranksaksiID = t.TranksaksiID join bulan as b on b.BulanID = p.BulanID join siswa as s on s.Nisn = p.SiswaID WHERE s.Nisn = ? GROUP BY t.TranksaksiID, t.MetodePay, t.TotalHarga, t.CreateAt ORDER BY t.CreateAt DESC");
                db.bind(1, nisn);
                db.execute();
                return db.resultSet();
            }
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }

    }

    public static ObservableList<Map<String, Object>> searchDataHistoryAll(String key) {
        try {

            db.query("select t.TranksaksiID, t.MetodePay, t.TotalHarga, t.CreateAt, GROUP_CONCAT(b.NamaBulan SEPARATOR ', ') as NamaBulan, GROUP_CONCAT(p.Harga SEPARATOR ', ') as Harga, GROUP_CONCAT(DISTINCT s.Nisn) as Nisn from tranksaksi as t join payment as p on p.TranksaksiID = t.TranksaksiID join bulan as b on b.BulanID = p.BulanID join siswa as s on s.Nisn = p.SiswaID where t.TranksaksiID LIKE ? OR s.Nisn LIKE ? GROUP BY t.TranksaksiID, t.MetodePay, t.TotalHarga, t.CreateAt ORDER BY t.CreateAt ASC");
            db.bind(1, key+"%");
            db.bind(2, key+"%");
            db.execute();
            return db.resultSet();

        } catch (SQLException e) {
            System.out.println(e);
            return FXCollections.observableArrayList();
        }

    }
    
    public static ObservableList<Map<String, Object>> searchDataHistoryAll(String key, String id) {
        try {

            db.query("select t.TranksaksiID, t.MetodePay, t.TotalHarga, t.CreateAt, GROUP_CONCAT(b.NamaBulan SEPARATOR ', ') as NamaBulan, GROUP_CONCAT(p.Harga SEPARATOR ', ') as Harga, GROUP_CONCAT(DISTINCT s.Nisn) as Nisn from tranksaksi as t join payment as p on p.TranksaksiID = t.TranksaksiID join bulan as b on b.BulanID = p.BulanID join siswa as s on s.Nisn = p.SiswaID where (t.TranksaksiID LIKE ? OR s.Nisn LIKE ?) and s.Nisn = ? GROUP BY t.TranksaksiID, t.MetodePay, t.TotalHarga, t.CreateAt ORDER BY t.CreateAt ASC");
            db.bind(1, key+"%");
            db.bind(2, key+"%");
            db.bind(3, id);
            db.execute();
            return db.resultSet();

        } catch (SQLException e) {
            System.out.println(e);
            return FXCollections.observableArrayList();
        }

    }
    
    

}
