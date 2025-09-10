package ADDAMMOLOL_0_1_x.AddAmmoMain.Actions;

public class ActionsLib {
    
    @SuppressWarnings("unused")
    public static Actions searchActions(int ID){
        Actions actions = null ;
        switch (ID){
            //actions(int iD,String actionNameString, int ammoCost, int dangerous, int legit, boolean isStealable, int rawDmg, int rawDef)
            //100 ammo adding series
            case 101:
                actions = new Actions(101,"Add ammo",-1,-1,0,true,0,0);
                break;
            case 102:
                if(false){
                    actions = new Actions(102,"add 2 Ammo" , -2, -1, 0, true, 0, 0);
                }else{
                    actions = null;
                }
                break;
            //200 simple attacking series **********************************************************************************
            case 201:
                actions = new Actions(201, "Pistol shoot", 1, 1, 0, true, 1, 0);
                break;
            case 202:
                actions = new Actions(202, "Double shoot", 2, 1, 0, true, 2, 0);
                break;
            //300 simple defending series **********************************************************************************
            case 301:
                actions = new Actions(301,"Defending", 0, 0, 0, false, 0, 1);
                break;
            case 302:
                actions = new Actions(302, "Reflection", 1, 0, 0, false, 0, 1);
                break;
            //400 simple thiefing series ***********************************************************************************
            case 401:
                actions = new Actions(401, "Thief", 1, 1, -1, false, 0, 0);
                break;
            case 402:
                actions = new Actions(402,"Rouge",2,2,-2,false,0,0);
                break;
            //500 simple police series *************************************************************************************
            case 501:
                actions = new Actions(501,"Police",0,1,1,false,1,0);
                break;
            case 502:
                actions = new Actions(502,"Armed poilce",2,2,2,false,2,1);
                break;
            //600 advanced high-damage weapons *****************************************************************************
            case 601:
                actions = new Actions(601,"RPG",5,2,0,true,3,0);
                break;
            //700 healing and stuff series *********************************************************************************
            case 701:
                actions = new Actions(701,"Healing",1,0,0,true,0,0);
                break;
            default:
                actions = null;
                break;
        }
        return actions;
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
