package ADDAMMOLOL_0_1_x;

import ADDAMMOLOL_0_1_x.AddAmmoMain.Actions.ActionsLib;
import ADDAMMOLOL_0_1_x.AddAmmoMain.Players.Enemy;
import ADDAMMOLOL_0_1_x.AddAmmoMain.Players.Enemy.GraspRequest;
import ADDAMMOLOL_0_1_x.AddAmmoMain.Players.Player;
import ADDAMMOLOL_0_1_x.AddAmmoUtil.AM_Decision;
import ADDAMMOLOL_0_1_x.AddAmmoUtil.AM_Recorder;

public class Test {
    public static void main(String[] args) {
        Enemy e = new Enemy();
        Player p = new Player();
        
        AM_Decision.start(601, e, p);
        

    }
}
