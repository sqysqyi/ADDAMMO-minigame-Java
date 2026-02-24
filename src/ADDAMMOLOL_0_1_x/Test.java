package ADDAMMOLOL_0_1_x;

import ADDAMMOLOL_0_1_x.AddAmmoMain.Actions.Action;
import ADDAMMOLOL_0_1_x.AddAmmoMain.Actions.ActionX;
import ADDAMMOLOL_0_1_x.AddAmmoMain.Players.Enemy;
import ADDAMMOLOL_0_1_x.AddAmmoMain.Players.Player;
import ADDAMMOLOL_0_1_x.AddAmmoMain.Players.Players;
import ADDAMMOLOL_0_1_x.AddAmmoUtil.AM_Decision;

public class Test {
    public static void main(String[] args) {
        Player p = new Player(100, 100, ActionX.RPG.toAction(), "player");
        Enemy e = new Enemy(100, 100, ActionX.ROUGE.toAction(), "enemy");

        e.getPlayerActions().getEvent().doWhen(0, e);
        p.getPlayerActions().getEvent().doWhen(0, p);

        e.getPlayerActions().getEvent().doWhen(1, e, new Players[]{p});




    }
}
