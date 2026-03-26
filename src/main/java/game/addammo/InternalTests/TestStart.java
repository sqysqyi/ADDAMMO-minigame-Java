package game.addammo.InternalTests;

import javax.swing.JFrame;
import java.awt.FlowLayout;

import game.addammo.AddAmmoMain.Game;
import game.addammo.AddAmmoUI.gameRender.GameConfigPanel;
import game.addammo.AddAmmoUI.gameRender.StartingMenuPanel;
import game.addammo.AddAmmoUI.gameRender.StartingMenuPanel.StartingButtonsListener;

public class TestStart implements Runnable{
    
    public static String version_t= "0.1.4t";


    public static int FRAME_HEIGHT = 600;
    public static int FRAME_WIDTH = 800;

    public Game game;
    private StartingMenuPanel startingMenuPanel;
    private GameConfigPanel configPanel;

    public static int setMaxHP = 3;
    public static int setDefaultAmmo = 1;
    public static final int setMaxPlayers = 2;

    JFrame gameFrame = new JFrame("ADD AMMO test mode v" + version_t);

    @Override
    public void run() {
        System.out.println("testing...");

        initWindow();
        startingMenuPanel = new StartingMenuPanel();
        startingMenuPanel.addStartingButtonsListener(new StartingButtonsListener() {
            @Override
            public void startButtonClicked() {
                gameFrame.remove(startingMenuPanel);
                restartGames();
            }

            @Override
            public void configButtonClicked() {
                startingMenuPanel.removeAll();

                startingMenuPanel.setLayout(new FlowLayout());

                configPanel = new GameConfigPanel();
                configPanel.addConfigConfirmListener(() -> {
                    gameFrame.remove(gameFrame.getContentPane());

                    restartGames();
                });
                startingMenuPanel.add(configPanel);
                startingMenuPanel.revalidate();
                startingMenuPanel.repaint();
            }
        });
        gameFrame.setContentPane(startingMenuPanel);

    }
    void initWindow(){
        gameFrame.setResizable(false);
        gameFrame.setIconImage(null);
        gameFrame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        gameFrame.setLocationRelativeTo(null); // 窗口居中
        gameFrame.setVisible(true);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    void restartGames(){
        try {
            new Game_t();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
