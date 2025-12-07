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


public class SiswaModel extends UserModel {
    private String NISN;
    private String jenkel;
    
    public SiswaModel(String userId, String name, String pass, String role) {
        super(userId, name, pass, role);
    }
    
    public void setNisn(String nisn) {
        this.NISN = nisn;
    }
    
    public void setJenkel(String jenkel) {
        this.jenkel = jenkel;
    }
    
    public String getNisn() {
        return this.NISN;
    }
    
    public String getJenkel() {
        return this.jenkel;
    }
}
