package ADDAMMOLOL_0_1_x.AddAmmoMain.Players;

import java.util.Scanner;

import ADDAMMOLOL_0_1_x.AddAmmoMain.GameStats;
import ADDAMMOLOL_0_1_x.AddAmmoMain.PlayerStats;
import ADDAMMOLOL_0_1_x.AddAmmoMain.Actions.Actions;

public class Player extends Players {
    
   
    
    public Player(){
        super();
        //playerActions = new actions();
    }//空参构造
    public Player(int HP, int ammoLeft, PlayerStats playerActionStats, 
                    GameStats playerGameStats, Actions playerActions, String playerNameString) {
        //playerActions = new actions();
        super(HP, ammoLeft, playerActionStats, playerGameStats, playerActions, playerNameString);
        
    }

/*************************************getter/setter 分割线******************************************* */ 
    

    public boolean checkEnoughAmmo(Actions actions){
        boolean checkEnoughAmmoResult = true;
        if(this.getAmmoLeft() - actions.getAmmoCost() < 0){
            checkEnoughAmmoResult = false;
        }
        return checkEnoughAmmoResult;
    }//never used seems :D

    @Override
    public int actionsSelecting(int playerInput,int playerAmmoLeft,GameStats playerGameStats){
        int selectedActionID;
        if(playerAmmoLeft < this.selectActions(playerInput).getAmmoCost()){
            selectedActionID = -1;
        }else{
            selectedActionID = playerInput;
        }
        return selectedActionID;
    }//虽然我知道这东西有点多余

}
