package game.sqysqyi.ADDAMMOLOL;

import javax.swing.SwingUtilities;

import game.sqysqyi.ADDAMMOLOL.AddAmmoMain.Start;

public class Main {
    
    public static void main(String[] args) {
        System.out.println("AddAmmo " + Start.version + ", bug fixes still wip");

        SwingUtilities.invokeLater(() -> {
            try {
                new Start().startMain();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                    e.printStackTrace();
            }
        });

    }


}
