/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kelompok.spp.model.object;

/**
 *
 * @author rafly
 */
public class JurusanModel {

    public String id;
    public String nama;

    public JurusanModel(String id, String nama) {
        this.id = id;
        this.nama = nama;
    }

    @Override
    public String toString() {
        return nama;
    }
}
