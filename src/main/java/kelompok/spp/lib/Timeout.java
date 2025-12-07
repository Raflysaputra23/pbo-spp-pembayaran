/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kelompok.spp.lib;

import javafx.animation.PauseTransition;
import javafx.util.Duration;

/**
 *
 * @author rafly
 */


public class Timeout {
    public static void setTimeout(Runnable callback, int detik) {
        PauseTransition timeout = new PauseTransition(Duration.seconds(detik));
        timeout.setOnFinished(e -> callback.run());
        timeout.play();
    }
}
