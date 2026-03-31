package game.addammo.InternalTests;

import game.addammo.AddAmmoMain.Game;
import game.addammo.AddAmmoUI.GameConfigPanel;
import game.addammo.AddAmmoUI.StartingMenuPanel;
import game.addammo.AddAmmoUI.StartingMenuPanel.StartingButtonsListener;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import java.awt.FlowLayout;

public class Renderer_t implements Runnable {

    public static int FRAME_HEIGHT = 600;
    public static int FRAME_WIDTH = 800;

    private StartingMenuPanel startingMenuPanel;
    private GameConfigPanel configPanel;

    JFrame gameFrame = new JFrame("ADD AMMO test v" + Start_t.version_t);

    public Renderer_t() {

    }

    /**
     * Currently this thread need be created as an EDT.
     */
    @Override
    public void run() {
        gameFrame.setResizable(false);
        gameFrame.setIconImage(null);
        gameFrame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        gameFrame.setLocationRelativeTo(null); // 窗口居中
        gameFrame.setVisible(true);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

                });
                startingMenuPanel.add(configPanel);
                startingMenuPanel.revalidate();
                startingMenuPanel.repaint();
            }
        });
        gameFrame.setContentPane(startingMenuPanel);

    }

    private void restartGames() {

        try {
            //game = new Game();
            gameFrame.setContentPane(new ClientRender_t());
            gameFrame.revalidate();
            gameFrame.repaint();
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }
}
