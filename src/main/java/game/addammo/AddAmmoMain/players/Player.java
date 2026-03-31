package game.addammo.AddAmmoMain.players;

import game.addammo.AddAmmoMain.actions.Action;
import game.addammo.AddAmmoMain.actions.ActionX;
import game.addammo.AddAmmoUtil.Anotations.Nullable;

public class Player extends Players {

    public Player(){
        super();
        //playerActions = new actions();
    }//空参构造
    public Player(int HP, int ammoLeft, Action playerActions, String playerNameString) {
        //playerActions = new actions();
        super(HP, ammoLeft, playerActions, playerNameString);
        
    }

/*************************************getter/setter 分割线******************************************* */ 
    

    public boolean checkEnoughAmmo(Action actions){
        boolean checkEnoughAmmoResult = true;
        if(this.getAmmoLeft() - actions.getAmmoCost() < 0){
            checkEnoughAmmoResult = false;
        }
        return checkEnoughAmmoResult;
    }//never used seems :D

    @Override
    public int actionsSelecting(int playerInput, @Nullable Player player,PlayerStats playerStats){
        if(ActionX.isNull(playerInput)){
            return -3;
        }else if(playerInput == 602 && !playerStats.isMissileSettled){//failing launch missile with no launcher settled
            return -2;
        }else if(this.getAmmoLeft() < this.selectActions(playerInput).getAmmoCost()){
            return -1;
        }else{
            return playerInput;
        }
        
    }

}
