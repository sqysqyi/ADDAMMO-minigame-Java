package ADDAMMOLOL_0_1_x.AddAmmoMain;

import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;

import ADDAMMOLOL_0_1_x.AddAmmoUI.GameUI_Set.GameConfigPanel;
import ADDAMMOLOL_0_1_x.AddAmmoUI.GameUI_Set.StartingMenuPanel;
import ADDAMMOLOL_0_1_x.AddAmmoUI.GameUI_Set.StartingMenuPanel.StartingButtonsListener;

public class Start {
    public static int FRAME_HEIGHT = 600;
    public static int FRAME_WIDTH = 800;
    public static final String version = "0.1.1";

    public Game game;
    private StartingMenuPanel startingMenuPanel;
    private GameConfigPanel configPanel;

    public static int setMaxHP = 3;
    public static int setDefaultAmmo = 1;
    public static final int setMaxPlayers = 2;

    JFrame gameFrame = new JFrame("ADD AMMO v" + version);

    // private static final int num = 0;

    public void startMain() throws Exception {
        JMenuBar gameMenuBar = new JMenuBar();
        menuBarSettings(gameMenuBar);
        gameFrame.setJMenuBar(gameMenuBar);

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

                    restartGames();
                });
                startingMenuPanel.add(configPanel);
                startingMenuPanel.revalidate();
                startingMenuPanel.repaint();
            }
        });
        gameFrame.setContentPane(startingMenuPanel);

    }

    public void menuBarSettings(JMenuBar menuBar) {
        JMenu setting = new JMenu("设置");
        {
            JMenuItem exit = new JMenuItem("退出");
            exit.addActionListener(e -> System.exit(0));
            setting.add(exit);

            JMenuItem restart = new JMenuItem("重新开始当前游戏");
            restart.addActionListener(e -> {
                if (game != null)
                    gameFrame.remove(gameFrame.getContentPane());

                restartGames();

            });
            setting.add(restart);

            JMenuItem resetting = new JMenuItem("重新开始一个新游戏");
            resetting.addActionListener(e -> {
                if (game != null) {
                    // gameFrame.remove(gameFrame.getContentPane());
                    game.setSubCompVisible(false);

                    game.setLayout(new FlowLayout());
                    DialogPanel newGameConfigDialog = new DialogPanel("Are you sure to restart a new game? ");
                    newGameConfigDialog.addButtonSelectingListener(new DialogPanel.ButtonSelectingListener() {

                        @Override
                        public void onConfirmClicked() {
                            game.startNewConfigGame_From(newGameConfigDialog);
                            configPanel = new GameConfigPanel();
                            configPanel.addConfigConfirmListener(() -> {
                                gameFrame.remove(gameFrame.getContentPane());

                                restartGames();
                            });
                            game.add(configPanel);
                        }

                        @Override
                        public void onCancelClicked() {
                            game.continueGame_From(newGameConfigDialog);
                        }

                    });
                    game.add(newGameConfigDialog);
                }
            });
            setting.add(resetting);
        }

        menuBar.add(setting);
    }

    private void restartGames() {
        SwingUtilities.invokeLater(() -> {
            try {
                game = new Game();
                gameFrame.setContentPane(game);
                gameFrame.revalidate();
                gameFrame.repaint();
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });
    }
}
