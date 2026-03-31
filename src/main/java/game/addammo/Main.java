package game.addammo;

import static game.addammo.StartConfig.*;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import game.addammo.AddAmmoMain.GameClient;

public class Main {
    private GameClient gameClient;
    public static void main(String[] args) throws Exception {
        System.out.println("AddAmmo " + StartConfig.version + ", bug fixes still wip");
        new Main().start();
        
    }

    private void start() throws Exception {

        
        SwingUtilities.invokeLater(() -> {
            gameClient = new GameClient("ADD AMMO v" + StartConfig.version);
            initClient();
        });
    }

    public void initClient() {
        gameClient.setResizable(false);
        gameClient.setIconImage(null);
        gameClient.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        gameClient.setLocationRelativeTo(null); // 窗口居中
        gameClient.setVisible(true);
        gameClient.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    


}
