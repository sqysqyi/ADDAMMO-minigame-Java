package ADDAMMOLOL_0_1_x;

import javax.swing.SwingUtilities;

import ADDAMMOLOL_0_1_x.AddAmmoMain.Start;

public class Main {
    
    public static void main(String[] args) {
        System.out.println("AddAmmo " + Start.version + ", gui snapshot ");

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
