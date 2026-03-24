package game.addammo.AddAmmoMain.actions;

import game.addammo.AddAmmoMain.RoundStats;
import game.addammo.AddAmmoMain.players.Players;

public interface ActionEvent {
    void doWhen(RoundStats stats, Players applier,Players[] targets);

    default void doWhen(RoundStats stats, Players applier){
        doWhen(stats, applier, null);
    }

    /*public static final int round_win = 1;
    public static final int round_tied = 0;
    public static final int round_lost = -1;
    public static final int round_before = 2; */
}

