/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kelompok.spp.model;

import java.sql.SQLException;
import java.util.Map;
import javafx.collections.ObservableList;
import kelompok.spp.database.Database;

/**
 *
 * @author rafly
 */
public class DetailModel {
    private static final Database db = new Database();
    
    public static ObservableList<Map<String, Object>> getSppById(String nisn) {
        try {
            db.query("Select b.BulanID as BulanID, b.NamaBulan as NamaBulan, p.TranksaksiID as Lunas from bulan as b left join payment as p on p.BulanID = b.BulanID AND p.SiswaID = ?");
            db.bind(1, nisn);
            db.execute();
            return db.resultSet();
        } catch(SQLException e) {
            System.out.println("Error get data: "+e);
            return null;
        }
    }
    
    public static Map<String, Object> getNama(String nisn) {
         try {
            db.query("Select NamaLengkap from siswa where Nisn = ?");
            db.bind(1, nisn);
            db.execute();
            return db.single();
        } catch(SQLException e) {
            System.out.println("Error get data: "+e);
            return null;
        }
    }
}
