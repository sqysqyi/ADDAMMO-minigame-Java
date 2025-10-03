package ADDAMMOLOL_0_1_x.AddAmmoMain;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;

//import javax.swing.JFrame;

public class Start {
    public static int FRAME_HEIGHT = 600;
    public static int FRAME_WIDTH = 800;
    private static final String version = "0.1.0 preview";

    JFrame gameFrame = new JFrame("ADD AMMO v" + version);

    // private static final int num = 0;
    public static void main(String[] args) throws Exception {
        System.out.println("AddAmmo " + version + ", gui snapshot ");

        SwingUtilities.invokeLater(() -> {
            try {
                new Start().startMain();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });

        // Math.abs(0);
        // System.exit(0);
        // Object.toString()

    }

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
        gameFrame.setContentPane(new Game());

    }

    public void menuBarSettings(JMenuBar menuBar) {
        JMenu Setting = new JMenu("设置");
        {
            JMenuItem exit = new JMenuItem("退出");
            exit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            });
            Setting.add(exit);

            JMenuItem restart = new JMenuItem("重新开始");
            restart.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    gameFrame.remove(gameFrame.getContentPane());

                    SwingUtilities.invokeLater(() -> {
                        try {
                            gameFrame.setContentPane(new Game());
                            gameFrame.revalidate();
                            gameFrame.repaint();
                        } catch (Exception e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                    });
   
                }
            });
            Setting.add(restart);
        }

        menuBar.add(Setting);
    }
}
