package ADDAMMOLOL_0_1_x;

import ADDAMMOLOL_0_1_x.AddAmmoMain.Actions.ActionsLib;
import ADDAMMOLOL_0_1_x.AddAmmoMain.Players.Enemy;
import ADDAMMOLOL_0_1_x.AddAmmoMain.Players.Enemy.GraspRequest;
import ADDAMMOLOL_0_1_x.AddAmmoUtil.AM_Recorder;

public class Test {
    public static void main(String[] args) {
        Enemy e = new Enemy(3, 0, ActionsLib.searchActions(101), "e");
        for (int i = 0; i < 3; i++) {
            e.grasping(new GraspRequest() {

                @Override
                public void graspTo(AM_Recorder recorder) {
                    recorder.add(recorder.newRecord(
                            ActionsLib.searchActions(101),
                            ActionsLib.searchActions(101),
                            resultResolve()));
                }

                @Override
                public int resultResolve() {
                    return 1;
                }

            });
        }
        AM_Recorder recorder = e.getRecorder();
        System.out.println("Done");

    }
}
