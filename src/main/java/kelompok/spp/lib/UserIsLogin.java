/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kelompok.spp.lib;

import kelompok.spp.model.object.SiswaModel;
import kelompok.spp.model.object.AdminModel;
import kelompok.spp.model.object.UserModel;


/**
 *
 * @author rafly
 */
public class UserIsLogin {
    private static UserModel user;
    
    
    public static void setSession(String userId, String username, String password, String role) {
        if("admin".equals(role)) {
            UserIsLogin.user = new AdminModel(userId, username, password, role);
        } else {
            UserIsLogin.user = new SiswaModel(userId, username, password, role);
        }
    }
    
    public static UserModel getSession() {
        return UserIsLogin.user;
    }
    
    public static void deleteSession() {
        UserIsLogin.user = null;
    }
}
