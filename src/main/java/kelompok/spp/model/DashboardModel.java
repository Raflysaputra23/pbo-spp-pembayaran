/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kelompok.spp.model;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Map;
import javafx.collections.ObservableList;
import kelompok.spp.database.Database;

/**
 *
 * @author rafly
 */
public class DashboardModel {

    private static final Database db = new Database();

    public static Map<String, Object> getTotalData(String nisn) {
        try {
            db.query("Select COUNT(*) as totalSemua, (Select COUNT(*) from siswa where Jenkel = 'perempuan') as totalSiswi, (Select COUNT(*) from siswa where Jenkel = 'laki-laki') as totalSiswa, (Select COUNT(DISTINCT t.TranksaksiID) from tranksaksi as t join payment as p on p.TranksaksiID = t.TranksaksiID join siswa as s on s.Nisn = p.SiswaID where s.Nisn = ?) as totalHistory from siswa");
            db.bind(1, nisn);
            db.execute();
            return db.single();
        } catch (SQLException e) {
            System.out.println("Error get data: " + e);
            return null;
        }

    }

    public static Map<String, Object> getTotalData() {
        try {
            db.query("Select COUNT(*) as totalSemua, (Select COUNT(*) from siswa where Jenkel = 'perempuan') as totalSiswi, (Select COUNT(*) from siswa where Jenkel = 'laki-laki') as totalSiswa, (Select COUNT(DISTINCT t.TranksaksiID) from tranksaksi as t join payment as p on p.TranksaksiID = t.TranksaksiID join siswa as s on s.Nisn = p.SiswaID) as totalHistory from siswa");
            db.execute();
            return db.single();
        } catch (SQLException e) {
            System.out.println("Error get data: " + e);
            return null;
        }
    }

    public static Map<String, Object> getBayaranBulanIni(String nisn) {
        try {
            int bulan = LocalDate.now().getMonthValue();
            String[] namaBulan = {"Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember"};
            String bulanSekarang = namaBulan[bulan - 1];

            db.query("Select t.TranksaksiID from tranksaksi as t join payment as p on p.TranksaksiID = t.TranksaksiID AND p.SiswaID = ? join bulan as b on p.BulanID = b.BulanID where B.NamaBulan = ?");
            db.bind(1, nisn);
            db.bind(2, bulanSekarang);
            db.execute();
            return db.single();
        } catch (SQLException e) {
            System.out.println("Get data error: " + e);
            return null;
        }
    }

    public static ObservableList<Map<String, Object>> getBayaranSemuaBulan(String nisn) {
        try {
            db.query("Select t.TranksaksiID from bulan as b join payment as p on p.BulanID = b.BulanID join tranksaksi as t on t.TranksaksiID = p.TranksaksiID join siswa as s on s.Nisn = p.SiswaID and s.Nisn = ?");
            db.bind(1, nisn);
            db.execute();
            return db.resultSet();
        } catch (SQLException e) {
            System.out.println("Get data error: " + e);
            return null;
        }
    }

    public static Map<String, Object> getHargaJurusan(String nisn) {
        try {

            db.query("Select j.HargaJurusan from siswa join jurusan as j on siswa.JurusanID = j.JurusanID where siswa.Nisn = ?");
            db.bind(1, nisn);
            db.execute();
            return db.single();
        } catch (SQLException e) {
            System.out.println("Get data error: " + e);
            return null;
        }
    }
}
