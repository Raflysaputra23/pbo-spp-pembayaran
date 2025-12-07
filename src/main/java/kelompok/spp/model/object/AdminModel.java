/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kelompok.spp.model.object;

import kelompok.spp.model.object.UserModel;

/**
 *
 * @author rafly
 */
public class AdminModel extends UserModel {
    private String NIM;
    
    public AdminModel(String userId, String username, String password, String role) {
        super(userId, username, password, role);
    }
     
    public void setNIM(String nim) {
        this.NIM = nim;
    }
    
    public String getNIM() {
        return this.NIM;
    }
        
}
