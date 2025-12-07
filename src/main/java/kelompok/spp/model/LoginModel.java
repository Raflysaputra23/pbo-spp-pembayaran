/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kelompok.spp.model;

import java.sql.SQLException;
import java.util.Map;
import kelompok.spp.database.Database;
import kelompok.spp.lib.Bycript;
import kelompok.spp.lib.UserIsLogin;

/**
 *
 * @author rafly
 */
public class LoginModel {

    private static final Database db = new Database();

    public static boolean login(String username, String password, String role) {
        try {
            if (role.equals("user")) {
                db.query("Select * from siswa where Nisn = ?");
                db.bind(1, username);
                db.execute();
                
                Map<String, Object> dataUser = db.single();
                if (dataUser != null) {
                    boolean cekPassword = Bycript.passwordVerify(password, dataUser.get("Password").toString());
                    if(cekPassword) UserIsLogin.setSession(dataUser.get("Nisn").toString(), dataUser.get("NamaLengkap").toString(), dataUser.get("Password").toString(), dataUser.get("Role").toString());
                    return cekPassword;
                }
            } else {
                db.query("Select * from users where Username = ?");
                db.bind(1, username);
                db.execute();

                Map<String, Object> dataUser = db.single();
                if (dataUser != null) {
                    boolean cekPassword = Bycript.passwordVerify(password, dataUser.get("Password").toString());
                    if(cekPassword) UserIsLogin.setSession(dataUser.get("UserID").toString(),dataUser.get("NamaLengkap").toString(), dataUser.get("Password").toString(), dataUser.get("Role").toString());
                    return cekPassword;
                }
            }
            
            return false;
        } catch (SQLException e) {
            System.out.println("Error login: "+e);
            return false;
        }

    }
}
