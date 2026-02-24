package ADDAMMOLOL_0_1_x.AddAmmoMain.Actions;

import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import ADDAMMOLOL_0_1_x.AddAmmoMain.Players.Players;
import ADDAMMOLOL_0_1_x.AddAmmoUtil.AM_RNGenerator;

public enum ActionX{
    //100 series ammos
    ADD_AMMO(
        101,"Add ammo",0,
        "You can have +1 ammo",
        -1,0,0,0,
        true,
        ActionX::DO_ADD_AMMO
        ),
    //200 series simple attacking
    PISTOL_SHOOT(
        201, "Pistol shoot", 1,
        "Consume 1 ammo to shoot enemy",
        1, 1, 0,0,
        true, 
        ActionX::DO_PISTAL_SHOOT
        ),
    DOUBLE_SHOOT(
        202, "Double shoot", 2, 
        "Consume 2 ammos to shoot enemy",
        1, 2, 0,0, 
        true, 
        ActionX::DO_DOUBLE_SHOOT
        ),
    //300 series simple defending
    DEF(
        301,"Defending", 0, 
        "Buff yourself +1 defence to reduce 1 damage from enemy",
        0, 0,1, 0, 
        false, 
        ActionX::DO_DEF
        ),
    ABSLOTE_DEF(
        302, "Advance Defending", 1, 
        "Allow you reflect specific damage back to enemy (not working now)",
        0, 0, 1024,0, 
        false, 
        ActionX::DO_ABSLUTE_DEF
        ),
    // 400 series thieives
    THIEF(
        401, "Thief", 1, 
        "Steal ammos or items from ONE enemy",
        1, 0, 0,-1, 
        false, 
        ActionX::DO_THIEF
        ),
    ROUGE(
        402,"Rouge",2,
        "Steal stuffs nut more powerful than thief",
        2,0,0,-2,
        false,
        ActionX::DO_ROUGE
        ),
    //500 series polices
    POLICE(
        501,"Police",0,
        "Always ready to stop any possible illegal actions in battle",
        1,1,0,1,
        false,
        ActionX::DO_POLICE
        ),
    ARMED_POLICE(
        502,"Armed poilce",2,
        "Justice, strong than normal police",
        2,2,1,2,
        false,
        ActionX::DO_ARMED_POLICE
        ),
    //600 series advance attacking
    RPG(
        601,"RPG",5,
        "You know, a powerful. unbreakable weapon dealing 3 damage",
        2,3,0,0,
        true,
        ActionX::DO_RPG
        ),
    MISSILE(
        602, "Misslie !", 2, 
        "A deadly weapon, require a launcher settled",
        2, 3, 0,0,
        true,
        ActionX::DO_MISSILE
        ),
    //700 series healing
    HEALING(
        701,"Healing",1,
        "+1 HP, or receive x2 coming damage while consuming",
        0,0,0,0,
        true,
        ActionX::DO_HEALING
        ),
    //800 series equipments
    MISSILE_LAUNCHER(
        801, "Missile Launcher", 2,
        "You need this to set missile off! Protect it well",
        0, 0, 1,-1, 
        false, 
        ActionX::DO_MISSILE_LAUNCHER
        ),
    MINE(
        802, "Mine", 2, 
        "Heavily damage who attack you with short-range and thief, expired after 3 rounds",
        0, 2, 0,-1, 
        true, 
        ActionX::DO_MINE_PLANTING
        ),
    // 900 series spcialist
    ENGINEER(
        901, "Engineer", 1, 
        "Disassembling target's vaild device, if they had",
        0, 0, 1,-2, 
        true, 
        ActionX::DO_ENGNIEER
        );



    private final int ID;
    private final String actionName;
    private final int ammoCost;
    private final int dangerous;
    private final int legit;
    private final boolean isStealable;
    private final int rawDmg;
    private final int rawDef;
    private final String actionDescription;
    private final ActionEvent actionEvent;
    //private final int[] specificSignArray;

    ActionX(
        int ID, String actionName, int ammoCost,
        String actionDescription,
        int dangerous, int rawDmg, int rawDef, int legit,
        boolean isStealable,
        ActionEvent actionEvent ){
            this.ID = ID;
            this.actionName = actionName;
            this.ammoCost = ammoCost;
            this.actionDescription = actionDescription;
            this.dangerous = dangerous;
            this.rawDmg = rawDmg;
            this.rawDef = rawDef;
            this.legit = legit;
            this.isStealable = isStealable;
            this.actionEvent = actionEvent;
        }

    public Action toAction(){
        return new Action(ID, actionName, ammoCost, dangerous, legit, actionEvent ,isStealable, rawDmg, rawDef);
    }
    /************************************************************************************* */

    public String getActionDescription(){
        new Object();
        return actionDescription;
    }

    private static ActionX get(int ID){

        for(ActionX action : values()){
            if(action.ID == ID) return action;    
        }
        return null;
    }

    /**
     * get a list containing all the actions in ActionsLib
     * @return a list contains all the actions here, can also be used to create a new list
     */
    public static ArrayList<Action> getAll(){
        ArrayList<Action> all = new ArrayList<>();
        for(ActionX action : values()){
            all.add(action.toAction());
        }
        return all;
    }

    //********************************************************************************* */

    /**
     * Search Action by ID from the ActionsLib
     * @param ID 
     * @return an action found by ID provided above
     */

    public static Action searchActions(int ID){
        ActionX elementAction = ActionX.get(ID);
        return elementAction != null?elementAction.toAction():null;
    }

    /**
     * Search Action Description by ID
     * @param ID
     * @return action description; String
     */
    public static String searchActionDescription(int ID){
        ActionX elementAction = ActionX.get(ID);
        return elementAction != null?elementAction.getActionDescription():null;
    }

    /**
     * 获取一行的所有行动，按索引查询
     * @param set all actions in this line
     * @return a list contains the action id required
     */
    public static ArrayList<Integer> searchActionsSet(int set){
        
        ArrayList<Integer> actionIDSet = new ArrayList<>();
        for(int i = 1; i < 4;i++){
            if(searchActions(set*100 + i) == null) break;
            else actionIDSet.add(set*100+i);
        }
        return actionIDSet;
    }
    //************************************************************************************ */

    public static ArrayList<Action> toID_list(ArrayList<Action> source){
        ArrayList<Action> result = new ArrayList<>();
        for (Action element: source){
            result.add(element);
        }
        return result;
    }

    public static ArrayList<Action> searchWtihCondition(Predicate<Action> conditon){
        return 
        getAll()
        .stream()
        .filter(conditon)
        .collect(Collectors.toCollection(ArrayList::new));
    }
    
    public static boolean canAfford(int ID, int ammoLeft){
        try{
            return searchActions(ID).getAmmoCost() <= ammoLeft;
        }catch(NullPointerException npe){
            return false;
        }
    }
    public static boolean isNull(int ID){
        return ActionX.searchActions(ID) == null;
    }


    public static String getActionName(int actionID){
        return searchActions(actionID)==null?"<null>":searchActions(actionID).getActionNameString();
    }
    public static int getActionAmmoCost(int actionID){
        return searchActions(actionID)==null?-128:searchActions(actionID).getAmmoCost();
    }
    public static int getActionRawDamage(int actionID){
        return searchActions(actionID)== null?-128:searchActions(actionID).getRawDmg();
    }
    public static int getActionRawDefense(int aactionID){
        return searchActions(aactionID)== null?-128:searchActions(aactionID).getRawDef();
    }
    public static int getActionDangetous(int actionID){
        return searchActions(actionID)==null?-128:searchActions(actionID).getDangerous();
    }

    /*Impliments of ActionEvent below xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx */
    
    private static final void DO_ADD_AMMO(int roundStatus,Players applier, Players[] targets){
        switch(roundStatus){
            case ActionEvent.round_before,ActionEvent.round_win:{
                applier.getPlayerStats().basicApply(ADD_AMMO.toAction());
                applier.setAmmoLeft(applier.getAmmoLeft() + 1);
                break;
            }
        }
        
    }
    private static final void DO_PISTAL_SHOOT(int roundStatus, Players applier, Players[] targets){
        switch (roundStatus) {
            case ActionEvent.round_before:{
                applier.getPlayerStats().basicApply(PISTOL_SHOOT.toAction());
                applier.setAmmoLeft(applier.getAmmoLeft() - PISTOL_SHOOT.toAction().getAmmoCost());
                break;
            }
            case ActionEvent.round_win:{
                if(targets[0].getPlayerStats().isMineReady() && AM_RNGenerator.newRNG(500)){
                    applier.setHP(applier.getHP() - MINE.toAction().getRawDmg());
                    break;
                }
                targets[0].setHP(targets[0].getHP() - applier.getPlayerStats().getRawDmg());
                break;
            }
        }
    }
    private static final void DO_DOUBLE_SHOOT(int roundStatus, Players applier, Players[] targets){
        switch(roundStatus){
            case ActionEvent.round_before:{
                applier.getPlayerStats().basicApply(DOUBLE_SHOOT.toAction());
                applier.setAmmoLeft(applier.getAmmoLeft() - DOUBLE_SHOOT.toAction().getAmmoCost());
                break;
            }
            case ActionEvent.round_win:{
                if(targets[0].getPlayerStats().isMineReady() && AM_RNGenerator.newRNG(500)){
                    applier.setHP(applier.getHP() - MINE.toAction().getRawDmg());
                    break;
                }
                targets[0].setHP(targets[0].getHP() - applier.getPlayerStats().getRawDmg());
                break;
            }
        }
    }
    private static final void DO_DEF(int roundStatus, Players applier, Players[] targets){
        switch(roundStatus){
            case ActionEvent.round_before:{
                applier.setAmmoLeft(applier.getAmmoLeft() - DEF.toAction().getAmmoCost());
                applier.getPlayerStats().setRawDef(DEF.toAction().getRawDef());
                break;
            }
        }
    }
    private static final void DO_ABSLUTE_DEF(int roundStatus, Players applier, Players[] targets){
        switch(roundStatus){
            case ActionEvent.round_before:{
                applier.setAmmoLeft(applier.getAmmoLeft() - ABSLOTE_DEF.toAction().getAmmoCost());
                applier.getPlayerStats().setRawDef(ABSLOTE_DEF.toAction().getRawDef());
                break;
            }    
        }
    }
    private static final void DO_THIEF(int roundStatus, Players applier, Players[] targets){
        switch(roundStatus){
            case ActionEvent.round_before:{
                applier.setAmmoLeft(applier.getAmmoLeft() - THIEF.toAction().getAmmoCost());
                applier.getPlayerStats().setThief(true);
                break;
            }
            case ActionEvent.round_win:{
                
                if(targets[0].getPlayerStats().isMineReady() && AM_RNGenerator.newRNG(500)){
                    applier.setHP(applier.getHP() - MINE.toAction().getRawDmg());
                    break;
                }
                if(targets[0].getPlayerActions().equals(MINE.toAction())){
                    applier.setHP(applier.getHP() - MINE.toAction().getRawDmg());
                    break;
                }
                if(targets[0].getPlayerActions().equals(ADD_AMMO.toAction())){
                    applier.setAmmoLeft(targets[0].getAmmoLeft() + applier.getAmmoLeft());
                    targets[0].setAmmoLeft(0);
                    break;
                }
                if(targets[0].getPlayerActions().isStealable()){
                    applier.stolenActivated(targets[0].getPlayerActions(), targets[0]);
                    break;  
                }
                
            }
        }
    }
    private static final void DO_ROUGE(int roundStatus, Players applier, Players[] targets){
        switch(roundStatus){
            case ActionEvent.round_before:{
                applier.setAmmoLeft(applier.getAmmoLeft() - ROUGE.toAction().getAmmoCost());
                applier.getPlayerStats().setThief(true);
                break;
            }
            case ActionEvent.round_win:{
                if(targets[0].getPlayerStats().isMineReady() && AM_RNGenerator.newRNG(150)){
                    applier.setHP(applier.getHP() - MINE.toAction().getRawDmg());
                    break;
                }
                if(targets[0].getPlayerActions().equals(MINE.toAction())){
                    applier.setHP(applier.getHP() - MINE.toAction().getRawDmg());
                    break;
                }
                if(targets[0].getPlayerActions().isStealable()){
                    applier.stolenActivated(targets[0].getPlayerActions(), targets[0]);
                }

                applier.setAmmoLeft(applier.getAmmoLeft() + targets[0].getAmmoLeft());
                targets[0].setAmmoLeft(0);
                break;
            }
        }
    }
    private static final void DO_POLICE(int roundStatus, Players applier, Players[] targets){
        applier.getPlayerStats().setPolice(true);
        switch(roundStatus){
            case ActionEvent.round_before:{
                applier.getPlayerStats().basicApply(POLICE.toAction());
                applier.setAmmoLeft(applier.getAmmoLeft() - POLICE.toAction().getAmmoCost());
                applier.getPlayerStats().setPolice(true);
                break;
            }
            case ActionEvent.round_win:{
                if(targets[0].getPlayerStats().isMineReady() && AM_RNGenerator.newRNG(750)){
                    applier.setHP(applier.getHP() - MINE.toAction().getRawDmg());
                    break;
                }
                if(targets[0].getPlayerStats().isThief()){
                    targets[0].setHP(targets[0].getHP() - applier.getPlayerActions().getRawDmg());
                    break;
                }
                if(targets[0].getPlayerActions().getDangerous() > 0 && !targets[0].getPlayerStats().isPolice()){
                    targets[0].setHP(targets[0].getHP() - applier.getPlayerActions().getRawDmg());
                    break;
                }
            }
        }
    }
    private static final void DO_ARMED_POLICE(int roundStatus, Players applier, Players[] targets){
        switch(roundStatus){
            case ActionEvent.round_before:{
                applier.getPlayerStats().basicApply(ARMED_POLICE.toAction());
                applier.setAmmoLeft(applier.getAmmoLeft() - ARMED_POLICE.toAction().getAmmoCost());
                applier.getPlayerStats().setPolice(true);
                break;
            }
            case ActionEvent.round_win:{
                if(targets[0].getPlayerStats().isThief()){
                    targets[0].setHP(targets[0].getHP() - applier.getPlayerActions().getRawDmg());
                    return;
                }
                if(targets[0].getPlayerActions().getDangerous() > 0 && !targets[0].getPlayerStats().isPolice()){
                    targets[0].setHP(targets[0].getHP() - applier.getPlayerActions().getRawDmg());
                    return;
                }
            }
        }
    }

    private static final void DO_RPG(int roundStatus, Players applier, Players[] targets){
        switch(roundStatus){
            case ActionEvent.round_before:{
                applier.getPlayerStats().basicApply(RPG.toAction());
                applier.setAmmoLeft(applier.getAmmoLeft() - RPG.toAction().getAmmoCost());
                break;
            }
            case ActionEvent.round_win:{
                targets[0].setHP(targets[0].getHP() - applier.getPlayerStats().getRawDmg());
                break;
            }
        }
    }
    private static final void DO_MISSILE(int roundStatus, Players applier, Players[] targets){
        switch(roundStatus){
            case ActionEvent.round_before:{
                if(applier.getPlayerStats().isMissileSettled()){
                    applier.getPlayerStats().basicApply(MISSILE.toAction());
                }
                applier.setAmmoLeft(applier.getAmmoLeft() - MISSILE.toAction().getAmmoCost());
                break;
            }
            case ActionEvent.round_win:{
                targets[0].getPlayerStats().setRawDef(0);//强行破防
                targets[0].setHP(targets[0].getHP() - applier.getPlayerActions().getRawDmg());
                applier.getPlayerStats().setMissileSettled(false);
                break;
            }
        }
    }
    private static final void DO_HEALING(int roundStatus, Players applier, Players[] targets){
        switch(roundStatus){
            case ActionEvent.round_before:{
                applier.getPlayerStats().basicApply(HEALING.toAction());
                applier.setAmmoLeft(applier.getAmmoLeft() - HEALING.toAction().getAmmoCost());
                applier.getPlayerStats().setHealingFlag(true);
                break;
            }
            case ActionEvent.round_tied,ActionEvent.round_win:{
                applier.setHP(applier.getHP() + 1);
                applier.getPlayerStats().setHealingFlag(false);
                break;
            }
        }
    }
    private static final void DO_MISSILE_LAUNCHER(int roundStatus, Players applier, Players[] targets){
        switch(roundStatus){
            case ActionEvent.round_before:{
                applier.setAmmoLeft(applier.getAmmoLeft() - MISSILE_LAUNCHER.toAction().getAmmoCost());
                applier.getPlayerStats().setRawDef(1);
                break;
            }
            case ActionEvent.round_lost:{
                if(targets[0].getPlayerStats().isThief()){
                    applier.getPlayerStats().setMissileSettled(true);
                }
            }
            case ActionEvent.round_win,ActionEvent.round_tied:{
                applier.getPlayerStats().setMissileSettled(true);
                break;
            }
        }
    }
    private static final void DO_MINE_PLANTING(int roundStatus, Players applier, Players[] targets){
        
        switch(roundStatus){
            case ActionEvent.round_before:{
                applier.setAmmoLeft(applier.getAmmoLeft() - MINE.toAction().getAmmoCost());
                //applier.getPlayerStats().setMineReady(true);
                break;
            }
            case ActionEvent.round_win,ActionEvent.round_tied:{
                applier.getPlayerStats().setMineReady(true);
                break;
            }
        }
    }
    private static final void DO_ENGNIEER(int roundStatus, Players applier, Players[] targets){
        applier.getPlayerStats().setEngineer(true);
        switch(roundStatus){
            case ActionEvent.round_before:{
                applier.getPlayerStats().basicApply(ENGINEER.toAction());
                applier.setAmmoLeft(applier.getAmmoLeft() - ENGINEER.toAction().getAmmoCost());
                applier.getPlayerStats().setEngineer(true);
                break;
            }
            case ActionEvent.round_win,ActionEvent.round_tied:{
                if(targets[0].getPlayerStats().getRawDef() > 0){
                    targets[0].getPlayerStats().setRawDef(0);
                    return;
                }else if(targets[0].getPlayerStats().isMineReady()){
                    targets[0].getPlayerStats().setMineReady(false);
                    return;
                }else if(targets[0].getPlayerStats().isMissileSettled()){
                    targets[0].getPlayerStats().setMissileSettled(false);
                    return;
                }else{
                    applier.getPlayerStats().setRawDef(1);
                    return;
                }
            }
        }
    }
    
}
