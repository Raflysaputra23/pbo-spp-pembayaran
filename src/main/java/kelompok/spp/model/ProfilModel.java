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
import kelompok.spp.lib.Bycript;
/**
 *
 * @author rafly
 */
public class ProfilModel {

    private static final Database db = new Database();

    public static Map<String, Object> getDataUser(String username, String role) {
        try {
            if (role.equals("user")) {
                db.query("Select s.Nisn as Username, s.Foto, s.Email, s.NoTelp, s.Kelas, j.*, s.Jenkel, s.TanggalLahir as TanggalLahir, s.Role, s.Password from siswa as s join jurusan as j on j.JurusanID = s.JurusanID where s.Nisn = ?");
                db.bind(1, username);
                db.execute();
                return db.single();
            } else {
                db.query("Select u.UserID, u.Username, u.Foto, u.Email, u.NoTelp, u.Role, u.Password from users as u where u.UserID = ?");
                db.bind(1, username);
                db.execute();
                return db.single();
            }

        } catch (SQLException e) {
            System.out.println("Error get data: " + e);
            return null;
        }
    }

    public static ObservableList<Map<String, Object>> getJurusan() {
        try {

            db.query("Select JurusanID, SingkatanJurusan, NamaJurusan from jurusan");
            db.execute();
            return db.resultSet();
        } catch (SQLException e) {
            System.out.println("Error get data: " + e);
            return null;
        }
    }

    public static boolean updateDataUser(String nisn, String email, String noTelp, String kelas, String jurusan, String jenkel, LocalDate tglLahir) {
        try {
            db.query("Update siswa set Nisn = ?, Email = ?, NoTelp = ?, Kelas = ?, JurusanID = ?, Jenkel = ?, TanggalLahir = ? where Nisn = ?");
            db.bind(1, nisn);
            db.bind(2, email);
            db.bind(3, noTelp);
            db.bind(4, kelas);
            db.bind(5, jurusan);
            db.bind(6, jenkel);
            db.bind(7, tglLahir);
            db.bind(8, nisn);
            db.execute();
            return db.rowCount() > 0;
        } catch (SQLException e) {
            System.out.println("Error get data: " + e);
            return false;
        }
    }

    public static boolean updateDataUser(String id, String username, String email, String noTelp, String status) {
        try {
            db.query("Update users set Username = ?, Email = ?, NoTelp = ?, Role = ? where UserID = ?");
            db.bind(1, username);
            db.bind(2, email);
            db.bind(3, noTelp);
            db.bind(4, status);
            db.bind(5, id);
            db.execute();
            return db.rowCount() > 0;
        } catch (SQLException e) {
            System.out.println("Error get data: " + e);
            return false;
        }
    }

    public static boolean updateDataUser(String id, String password, String role) {
        try {
            String passwordHash = Bycript.passwordHash(password.trim());
            if (role.equals("user")) {
                db.query("Update siswa set Password = ? where Nisn = ?");
            } else {
                db.query("Update users set Password = ? where UserID = ?");
            }
            db.bind(1, passwordHash);
            db.bind(2, id);
            db.execute();
            return db.rowCount() > 0;
        } catch (SQLException e) {
            System.out.println("Error get data: " + e);
            return false;
        }
    }
}
