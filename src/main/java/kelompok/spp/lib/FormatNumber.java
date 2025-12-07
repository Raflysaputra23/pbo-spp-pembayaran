/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kelompok.spp.lib;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 *
 * @author rafly
 */
public class FormatNumber {

    public static String format(String number) {
        int n = Integer.parseInt(number);
        DecimalFormatSymbols simbol = new DecimalFormatSymbols(new Locale("id", "ID"));
        simbol.setGroupingSeparator('.');
        simbol.setDecimalSeparator(',');
        DecimalFormat df = new DecimalFormat("#,##0", simbol);

        return df.format(n);
    }
}
