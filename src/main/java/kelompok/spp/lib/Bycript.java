/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kelompok.spp.lib;

import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author rafly
 */
public class Bycript {
    public static String passwordHash(String pass) {
        return BCrypt.hashpw(pass, BCrypt.gensalt(10));
    }
    
    public static boolean passwordVerify(String passUser, String passHash) {
        try {
            return BCrypt.checkpw(passUser, passHash);
        } catch(Exception e) {
            System.out.println("Error Bycrypt: "+e);
            return false;
        }
        
    }
}
