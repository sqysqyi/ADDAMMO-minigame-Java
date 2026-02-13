package ADDAMMOLOL_0_1_x.AddAmmoMain.Actions;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

//所有可能会对对手造成伤害的actions的dangerous属性必须为正值；
//所有绝对不可能使players成为 more dangerous player的actions应该为dangerous 为-1；
//所有警察小偷系列的legit属性不可为0
//->这就引出来所有警察legit值必须为正值
//->所有小偷的legit值必须为负值，对应actions正义/盗窃程度越高，值的绝对值越大

//specific sign作用暂定
//
/**
 * Here is all actions definitions
 * 
 * You can create an action instance with the static method provided below:
 * static Actions searchActions(int ID)
 * 
 * @author sqysqyi
 *  
 */

public enum ActionsLib{
    //100 series ammos
    ADD_AMMO(
        101,"Add ammo",-1,-1,0,-1,true,0,0,
        "You can have +1 ammo"),
    //200 series simple attacking
    PISTOL_SHOOT(
        201, "Pistol shoot", 1, 1, 0, 0,true, 1, 0,
        "Consume 1 ammo to shoot enemy"),
    DOUBLE_SHOOT(
        202, "Double shoot", 2, 1, 0, 0,true, 2, 0,
        "Consume 2 ammos to shhot enemy"),
    //300 series simple defending
    DEF(
        301,"Defending", 0, 0, 0,0, false, 0, 1,
        "Buff yourself +1 defence to reduce 1 damage from enemy"),
    ABSLOTE_DEF(
        302, "Advance Defending", 1, 0, 0, 0,false, 0, 8,
        "Allow you reflect specific damage back to enemy (not working now)"),
    // 400 series thieives
    THIEF(
        401, "Thief", 1, 1, -1, 0,false, 0, 0,
        "Steal ammos or items from ONE enemy"),
    ROUGE(
        402,"Rouge",2,2,-2,0,false,0,0,
        "Steal stuffs nut more powerful than thief"),
    //500 series polices
    POLICE(
        501,"Police",0,1,1,0,false,1,0,
        "Always ready to stop any possible illegal actions in battle"),
    ARMED_POLICE(
        502,"Armed poilce",2,2,2,0,false,2,1,
        "Justice, strong than normal police"),
    //600 series advance attacking
    RPG(
        601,"RPG",5,2,0,0,true,3,0,
        "You know, a powerful. unbreakable weapon dealing 3 damage"),
    MISSILE(
        602, "Misslie !", 2, 2, -2, 0,false, 3, 0,
        "A deadly weapon, require a launcher settled"),
    //700 series healing
    HEALING(
        701,"Healing",1,0,0,0,true,0,0,
        "+1 HP, or receive x2 coming damage while consuming"),
    //800 series equipments
    MISSILE_LAUNCHER(
        801, "Missile Launcher", 2, 0, -2, 0,false, 0, 0,
        "You need this to set missile off! Protect it well"),
    MINE(
        802, "Mine", 2, 0, 0, 0,true, 0, 0,
        "Heavily damage who attack you with short-range and thief, expired after 3 rounds"),
    // 900 series spcialist
    ENGINEER(901, "Engineer", 1, 1, -1, 0,false, 0, 1,
    "Disassembling target's vaild device, if they had");



    private final int ID;
    private final String actionName;
    private final int ammoCost;
    private final int dangerous;
    private final int legit;
    private final boolean isStealable;
    private final int rawDmg;
    private final int rawDef;
    private final String actionDescription;
    private final int specificSign;
    //private final int[] specificSignArray;

    
    ActionsLib(
        int ID, String actionName, int ammoCost, 
        int dangerous,int legit, int specificSign,
        boolean isStealable,
        int rawDmg, int rawDef,
        String actionDescription){
            this.ID = ID;
            this.actionName = actionName;
            this.ammoCost = ammoCost;
            this.dangerous = dangerous;
            this.legit = legit;
            this.isStealable = isStealable;
            this.rawDmg = rawDmg;
            this.rawDef = rawDef;
            this.actionDescription = actionDescription;
            this.specificSign = specificSign;

        }

    public Actions toAction(){
        return new Actions(ID, actionName, ammoCost, dangerous, legit, specificSign,isStealable, rawDmg, rawDef);
    }

    public String getActionDescription(){
        return actionDescription;
    }

    private static ActionsLib get(int ID){

        for(ActionsLib action : values()){
            if(action.ID == ID) return action;    
        }
        return null;
    }

    /**
     * get a list containing all the actions in ActionsLib
     * @return a list contains all the actions here, can also be used to create a new list
     */
    public static ArrayList<Actions> getAll(){
        ArrayList<Actions> all = new ArrayList<>();
        for(ActionsLib action : values()){
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

    public static Actions searchActions(int ID){
        ActionsLib elementAction = ActionsLib.get(ID);
        return elementAction != null?elementAction.toAction():null;
    }

    /**
     * Search Action Description by ID
     * @param ID
     * @return action description; String
     */
    public static String searchActionDescription(int ID){
        ActionsLib elementAction = ActionsLib.get(ID);
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

    public static ArrayList<Actions> toID_list(ArrayList<Actions> source){
        ArrayList<Actions> result = new ArrayList<>();
        for (Actions element: source){
            result.add(element);
        }
        return result;
    }

    public static ArrayList<Actions> searchWtihCondition(Predicate<Actions> conditon){
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
        return ActionsLib.searchActions(ID) == null;
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
}