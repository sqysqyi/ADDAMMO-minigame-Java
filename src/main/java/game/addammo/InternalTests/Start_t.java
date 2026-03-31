package game.addammo.InternalTests;

import javax.swing.SwingUtilities;

import game.addammo.AddAmmoMain.Game;
import game.addammo.AddAmmoUI.GameConfigPanel;
import game.addammo.AddAmmoUI.StartingMenuPanel;

public class Start_t implements Runnable {
    public static final String version_t = "0.1.4t";
    
    public Game game;
    private StartingMenuPanel startingMenuPanel;
    private GameConfigPanel configPanel;

    public static int setMaxHP = 3;
    public static int setDefaultAmmo = 1;
    public static final int setMaxPlayers = 2;

    @Override
    public void run() {
        
        Thread rendererThread = new Thread(() ->{
            SwingUtilities.invokeLater( () -> {
                    try {
                        new Renderer_t();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            );
        }, "RendererThread");
        Thread logicThread = new Thread(new Logic_t(), "LogicThread");
        
        logicThread.start();
        rendererThread.start();
    }
}
