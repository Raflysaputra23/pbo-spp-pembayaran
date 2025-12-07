/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kelompok.spp.model;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import kelompok.spp.database.Database;

/**
 *
 * @author rafly
 */
public class PaymentModel {

    private static final Database db = new Database();

    public static ObservableList<Map<String, Object>> getBulan(String nisn) {
        try {
            db.query("Select b.BulanID as BulanID, b.NamaBulan as NamaBulan, p.TranksaksiID as Lunas from bulan as b left join payment as p on p.BulanID = b.BulanID AND p.SiswaID = ?");
            db.bind(1, nisn);
            db.execute();
            return db.resultSet();
        } catch (SQLException e) {
            System.out.println("Error get data: " + e);
            return FXCollections.observableArrayList();
        }

    }

    public static Map<String, Object> getHargaJurusan(String nisn) {
        try {
            db.query("Select j.HargaJurusan from siswa as s join jurusan as j on j.JurusanID = s.JurusanID where s.Nisn = ?");
            db.bind(1, nisn);
            db.execute();
            return db.single();
        } catch (SQLException e) {
            System.out.println("Error get data: " + e);
            return null;
        }
    }

    public static boolean bayarSpp(ArrayList<String> bulan, String nisn, double harga) throws SQLException {
        long number = (long) (Math.random() * 1_000_000_0000L);
        String noTrx = String.format("%010d", number);
        try {
            db.query("insert into tranksaksi(TranksaksiID, MetodePay, TotalHarga, CreateAt) values (?,?,?,?)");
            db.bind(1, noTrx);
            db.bind(2, "Cash");
            db.bind(3, harga * bulan.size());
            db.bind(4, LocalDateTime.now());
            db.execute();

            boolean response = db.rowCount() > 0;
            if (!response) {
                return false;
            }

            db.beginTransaction();
            for (String b : bulan) {
                db.query("insert into payment(TranksaksiID, BulanID, SiswaID, Harga) values (?,?,?,?)");
                db.bind(1, noTrx);
                db.bind(2, b);
                db.bind(3, nisn);
                db.bind(4, harga);
                db.execute();
            }
            db.commit();
            return db.rowCount() > 0;
        } catch (SQLException e) {
            db.rollback();
            deleteTranksaksi(noTrx);
            System.out.println("Gagal bayar spp: " + e);
            return false;
        }

    }

    public static boolean deleteTranksaksi(String noTrx) {
        try {
            db.query("DELETE from tranksaksi where TranksaksiID = ?");
            db.bind(1, noTrx);
            db.execute();
            return db.rowCount() > 0;
        } catch (SQLException e) {
            System.out.println("Gagal menghapus tranksaksi: " + e);
            return false;
        }
    }
}
