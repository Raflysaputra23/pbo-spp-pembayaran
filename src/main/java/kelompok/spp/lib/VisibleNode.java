/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kelompok.spp.lib;

import javafx.scene.Node;

/**
 *
 * @author rafly
 */
public class VisibleNode {

    public static void showNode(Node n) {
        n.setVisible(true);
        n.setManaged(true);
    }

    public static void hideNode(Node n) {
        n.setVisible(false);
        n.setManaged(false);
    }

    public static void showNodeTimer(Node n, int detik) {
        showNode(n);
        Timeout.setTimeout(() -> {
            hideNode(n);
        }, detik);
    }
}
