package ADDAMMOLOL_0_1_x.AddAmmoMain.Players;

import ADDAMMOLOL_0_1_x.AddAmmoMain.Actions.Actions;

public class Player extends Players {

    public Player(){
        super();
        //playerActions = new actions();
    }//空参构造
    public Player(int HP, int ammoLeft, Actions playerActions, String playerNameString) {
        //playerActions = new actions();
        super(HP, ammoLeft, playerActions, playerNameString);
        
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
    public int actionsSelecting(int playerInput, Player player,PlayerStats playerStats){
        if(this.getAmmoLeft() < this.selectActions(playerInput).getAmmoCost()){
            return -1;
        }else if(playerInput == 602 && !playerStats.isMissileSettled()){//failing launch missile with no launcher settled
            return -2;
        }else{
            return playerInput;
        }
        
    }//虽然我知道这东西有点多余

}
