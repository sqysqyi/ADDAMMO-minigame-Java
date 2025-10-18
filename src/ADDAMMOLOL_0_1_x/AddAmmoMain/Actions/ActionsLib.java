package ADDAMMOLOL_0_1_x.AddAmmoMain.Actions;

public enum ActionsLib{
    ADD_AMMO(
        101,"Add ammo",-1,-1,0,-1,true,0,0,
        "You can have +1 ammo"),
    
    PISTOL_SHOOT(
        201, "Pistol shoot", 1, 1, 0, 0,true, 1, 0,
        "Consume 1 ammo to shoot enemy"),
    DOUBLE_SHOOT(
        202, "Double shoot", 2, 1, 0, 0,true, 2, 0,
        "Consume 2 ammos to shhot enemy"),

    DEF(
        301,"Defending", 0, 0, 0,0, false, 0, 1,
        "Buff yourself +1 defence to reduce 1 damage from enemy"),
    ADVANCE_DEF(
        302, "Advance Defending", 1, 0, 0, 0,false, 0, 8,
        "Allow you reflect specific damage back to enemy (not working now)"),

    THIEF(
        401, "Thief", 1, 1, -1, 0,false, 0, 0,
        "Steal ammos or items from ONE enemy"),
    ROUGE(
        402,"Rouge",2,2,-2,0,false,0,0,
        "Steal stuffs nut more powerful than thief"),

    POLICE(
        501,"Police",0,1,1,0,false,1,0,
        "Always ready to stop any possible illegal actions in battle"),
    ARMED_POLICE(
        502,"Armed poilce",2,2,2,0,false,2,1,
        "Justice, strong than normal police"),

    RPG(
        601,"RPG",5,2,0,0,true,3,0,
        "You know, a powerful. unbreakable weapon dealing 3 damage"),
    MISSILE(
        602, "Misslie !", 2, 2, -2, 0,false, 3, 0,
        "A deadly weapon, require a launcher settled"),

    HEALING(
        701,"Healing",1,0,0,0,true,0,0,
        "+1 HP, or receive x2 coming damage while consuming"),

    MISSILE_LAUNCHER(
        801, "Missile Launcher", 2, 0, -2, 0,false, 0, 0,
        "You need this to set missile off! Protect it well"),
    MINE(
        802, "Mine", 2, 0, 0, 0,true, 0, 0,
        "Heavily damage who attack you with short-range and thief, expired after 3 rounds"),
        
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

    //************************************************************************************************************** */
    //from original "ActionsLib.java"

    public static Actions searchActions(int ID){
        ActionsLib elementAction = ActionsLib.get(ID);
        return elementAction != null?elementAction.toAction():null;
    }
    public static String searchActionDescription(int ID){
        ActionsLib elementAction = ActionsLib.get(ID);
        return elementAction != null?elementAction.getActionDescription():null;
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