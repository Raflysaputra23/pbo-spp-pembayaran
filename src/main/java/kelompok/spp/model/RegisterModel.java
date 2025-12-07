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
import kelompok.spp.lib.Bycript;

/**
 *
 * @author rafly
 */
public class RegisterModel {

    private static final Database db = new Database();

    public static boolean registerAdmin(String username, String namaLengkap, String email, String password, String role) {
        try {
//            CEK USER SUDAH TERDAFTAR ATAU BELUM
            if (getDataById(username, role) != null) {
                return false;
            }

//            HASH PASSWORD
            String passwordHash = Bycript.passwordHash(password.trim());

            db.query("Insert into users(Username, NamaLengkap, Email, Password, Role) VALUES (?,?,?,?,?)");
            db.bind(1, username.trim());
            db.bind(2, namaLengkap);
            db.bind(3, email.trim());
            db.bind(4, passwordHash);
            db.bind(5, role);
            db.execute();

            return db.rowCount() > 0;
        } catch (SQLException e) {
            System.out.println("Error register:" + e);
            return false;
        }

    }

    public static boolean register(String nisn, String namaLengkap, String kelas, String jurusan, String jenkel, String password, String role) {
        try {
//            CEK USER SUDAH TERDAFTAR ATAU BELUM
            if (getDataById(nisn, role) != null) {
                return false;
            }

//            HASH PASSWORD
            String passwordHash = Bycript.passwordHash(password.trim());

            db.query("Insert into siswa(Nisn, NamaLengkap, Kelas, JurusanID,Jenkel, Password, Role) VALUES (?,?,?,?,?,?,?)");
            db.bind(1, nisn.trim());
            db.bind(2, namaLengkap);
            db.bind(3, kelas.trim());
            db.bind(4, jurusan);
            db.bind(5, jenkel);
            db.bind(6, passwordHash);
            db.bind(7, role);
            db.execute();

            return db.rowCount() > 0;
        } catch (SQLException e) {
            System.out.println("Error register:" + e);
            return false;
        }

    }

    public static Map<String, Object> getDataById(String id, String role) {
        try {
            if (role.equals("user")) {
                db.query("select * from siswa where Nisn = ?");
            } else {
                db.query("select * from users where Username = ?");
            }
            db.bind(1, id);
            db.execute();
            return db.single();
        } catch (SQLException e) {
            System.out.println("Error get data: " + e);
            return null;
        }
    }

    public static ObservableList<Map<String, Object>> getJurusan() {
        try {
            db.query("Select JurusanID, SingkatanJurusan from jurusan");
            db.execute();
            return db.resultSet();
        } catch (SQLException e) {
            System.out.println("Error get Data: "+e);
            return FXCollections.observableArrayList();
        }
    }
}
