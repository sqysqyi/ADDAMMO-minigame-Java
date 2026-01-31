package ADDAMMOLOL_0_1_x.AddAmmoMain.Players;

import java.util.HashMap;
import java.util.Random;
import ADDAMMOLOL_0_1_x.AddAmmoMain.Actions.Actions;
import ADDAMMOLOL_0_1_x.AddAmmoMain.Actions.ActionsLib;
import ADDAMMOLOL_0_1_x.AddAmmoUtil.AM_Decision;
import ADDAMMOLOL_0_1_x.AddAmmoUtil.AM_Recorder;

public class Enemy extends Players {
    private int selectedActionID, ambitiousActionID;
    private AM_Recorder recorder;
    private AM_Decision decision;
    private HashMap<Actions, Integer> weightMap;

    public Enemy() {
        super();
        // super(HP, ammoLeft, dangerous, legit, isSteal, isMissileSettled,
        // playerNameString);
    }

    public Enemy(int HP, int ammoLeft, Actions enemyActions, String playerNameString) {
        super(HP, ammoLeft,  enemyActions, playerNameString);
        init();

    }

    // actionsSelecting() start below
    /**
     * @param  num is meaningless just ignore it;
     */
    @Override
    public int actionsSelecting(int num, Players opponent , PlayerStats opponentPlayerStats) {
        return 0;
    }

    /**
     * As how the game played in real life, we / i tended to keep an ambitious 
     * action in mind. 
     * Thats why this method exists here :)
     * 
     * At present, the way "guessing" the ambitious action is EXTREMELY low
     * efficency, may improve days after?
     */
    public static int createAmbitious() {
        Random rand = new Random();
        int preSelectActionID;
        while (true) {
            int groupIndex = rand.nextInt(9);
            int itemIndex = rand.nextInt(3);

            preSelectActionID = (groupIndex + 1) * 100 + itemIndex + 1;
            // 排除条件如下：
            if (ActionsLib.searchActions(preSelectActionID) == null)
                continue;// 不存在的action舍弃
            if (ActionsLib.getActionAmmoCost(preSelectActionID) < 2)
                continue;// 子弹数小于2 的,拜托，有点追求好吧

            break;
        }
        return preSelectActionID;

    }
    public int getAmbitious(){
        return ambitiousActionID;
    }
    public void setAmbitious(int ID){
        this.ambitiousActionID = ID;
    }

    /******************************************************************************************************* */

    private final int for_tension = 0;
    private final int for_instability = 1;
    /**
     * This method is designed for simulating how computer decide the next action
     * would be
     * 
     * 
     */
   
    /********************************************************************************************************* */
// actionsSelecting() end here
    private static final int DEFAULT_WEIGHT = 1;

    private void init() {
        weightMap = new HashMap<>();
        for (Actions action : ActionsLib.getAll()) {
            weightMap.put(action, DEFAULT_WEIGHT);
        }
        recorder = new AM_Recorder();
        // actionSelectPool = new ArrayList<>();
    }

    public AM_Recorder getRecorder(){
        return this.recorder;
    }
    

    public void grasping(GraspRequest graspRequest) {
        graspRequest.graspTo(recorder);
    }

    public interface GraspRequest {
        
        void graspTo(AM_Recorder recorder);
        /**
         * local method used while enemy grasping round result
         * @param winner from Game.java
         * @return 1:enemy win, 0:tied, -1:this enemy lost
         * @see AddAmmoMain.Game
         */
        int resultResolve();

    }
}
