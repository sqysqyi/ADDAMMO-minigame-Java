package ADDAMMOLOL_0_0_1d;

public class actionsLib {
    
    public static actions searchActions(int ID){
        actions actions = null ;
        switch (ID){
            //actions(int iD,String actionNameString, int ammoCost, int dangerous, int legit, boolean isStealable, int rawDmg, int rawDef)
            //100 ammo adding series
            case 101:
                actions = new actions(101,"Add Ammo",-1,-1,0,true,0,0);
                break;
            case 102:
                if(game.Crazymode()){
                    actions = new actions(102,"Add 2 Ammo" , -2, -1, 0, true, 0, 0);
                }else{
                    actions = null;
                }
                break;
            //200 simple attacking series **********************************************************************************
            case 201:
                actions = new actions(201, "pistol shoot", 1, 1, 0, true, 1, 0);
                break;
            case 202:
                actions = new actions(202, "double shoot", 2, 1, 0, true, 2, 0);
                break;
            //300 simple defending series **********************************************************************************
            case 301:
                actions = new actions(301,"defending", 0, 0, 0, false, 0, 1);
                break;
            case 302:
                actions = new actions(302, "reflection", 1, 0, 0, false, 0, 1);
                break;
            //400 simple thiefing series ***********************************************************************************
            case 401:
                actions = new actions(401, "thief", 1, 1, -1, false, 0, 0);
                break;
            case 402:
                actions = new actions(402,"rouge",2,2,-2,false,0,0);
                break;
            //500 simple police series *************************************************************************************
            case 501:
                actions = new actions(501,"police",0,1,1,false,1,0);
                break;
            case 502:
                actions = new actions(502,"armed poilce",2,2,2,false,2,1);
                break;
            //600 advanced high-damage weapons *****************************************************************************
            case 601:
                actions = new actions(601,"RPG",5,2,0,true,3,0);
                break;
            //700 healing and stuff series *********************************************************************************
            case 701:
                actions = new actions(701,"Healing",1,0,0,true,0,0);
                break;
            default:
                actions = null;
                break;
        }
        return actions;
    }

}
