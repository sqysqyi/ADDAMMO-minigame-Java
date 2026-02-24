package ADDAMMOLOL_0_1_x.AddAmmoMain.Actions;

import ADDAMMOLOL_0_1_x.AddAmmoMain.Players.Players;

public interface ActionEvent {
    void doWhen(int roundResult, Players applier,Players[] targets);

    default void doWhen(int roundStatus, Players applier){
        doWhen(roundStatus, applier, null);
    }

    public static final int round_win = 1;
    public static final int round_tied = 0;
    public static final int round_lost = -1;
    public static final int round_before = 2;
}

