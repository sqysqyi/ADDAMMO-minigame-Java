package ADDAMMOLOL_0_1_x.AddAmmoMain.Players;

import java.util.Scanner;

import ADDAMMOLOL_0_1_x.AddAmmoMain.Game;
import ADDAMMOLOL_0_1_x.AddAmmoMain.GameStats;
import ADDAMMOLOL_0_1_x.AddAmmoMain.PlayerStats;
import ADDAMMOLOL_0_1_x.AddAmmoMain.Actions.Actions;
import ADDAMMOLOL_0_1_x.AddAmmoMain.Actions.ActionsLib;
import ADDAMMOLOL_0_1_x.AddAmmoUtil.RNGenerator;

public abstract class Players {
    private int HP,ammoLeft;
    private PlayerStats playerStats;
    private GameStats gameStats;
    private String playerNameString;
    private Actions playerActions;


    public Players(){

    }
    public Players(int HP, int ammoLeft, PlayerStats playerStats,
                    GameStats gameStats, Actions playerActions, String playerNameString){
        this.HP = HP;
        this.ammoLeft = ammoLeft;
        this.playerStats = new PlayerStats();
        this.gameStats = gameStats;
        this.playerNameString = playerNameString;
    }

    public GameStats getGameStats() {
        return gameStats;
    }
    public void setGameStats(GameStats gameStats) {
        this.gameStats = gameStats;
    }
    public PlayerStats getPlayerStats() {
        return playerStats;
    }
    public void setPlayerStats(PlayerStats playerStats) {
        this.playerStats = playerStats;
    }
    public String getPlayerNameString() {
        return playerNameString;
    }
    public void setPlayerNameString(String playerNameString) {
        this.playerNameString = playerNameString;
    }
    public int getHP() {
        return HP;
    }
    public void setHP(int hP) {
        this.HP = hP;
    }
    public int getAmmoLeft() {
        return ammoLeft;
    }
    public void setAmmoLeft(int ammoLeft) {
        this.ammoLeft = ammoLeft;
    }
    
    
    public Actions getPlayerActions(){
        return playerActions;
    }
    public void setPlayerActions(Actions playerActions){
        this.playerActions = playerActions;
    }
    /*************************************getter/setter 分割线******************************************* */ 

    public Actions selectActions(int ID){
        Actions actions = ActionsLib.searchActions(ID);    
        return actions;
    }
    
    public void checkIsSteal_or_Police(){
        if(this.getPlayerActions().getLegit() < 0 && !this.playerStats.isPolice()){
            this.playerStats.setThief(true);
        }else if(this.getPlayerActions().getLegit() > 0 && !this.playerStats.isThief()){
            this.playerStats.setPolice(true);
        }   
    }
    

    public void setStateDefault(){
        this.playerStats.resetDmgDefThief();
        this.ammoLeft = 0;
        this.HP = 3;
    } 

    public int damageDealtTo(Players target){
        int damageDealt = 0;

        damageDealt = this.getPlayerActions().getRawDmg() - target.getPlayerActions().getRawDef();
        if(damageDealt < 0 ){
            damageDealt = 0;
        }
        return damageDealt;
    }
    public void generalActivating(){
        this.playerStats.setRawDef(this.getPlayerActions().getRawDef());

        int ammoleft = this.getAmmoLeft()-this.getPlayerActions().getAmmoCost();
        this.setAmmoLeft(ammoleft);
        this.checkIsSteal_or_Police();
    }

    public void winActivating(){
        //player.setRawDef(playerActions.getRawDef());
        this.playerStats.setRawDmg(this.getPlayerActions().getRawDmg());

        if(this.getPlayerActions().getLegit() < 0){
            this.playerStats.setThief(true);
        }else if(this.getPlayerActions().getLegit() > 0){
            this.playerStats.setPolice(true);
        }
        
    }    
    public void ammoRetureTo(Players player){//return your ammo as succeed in stealing enemy's non_addammo actions
        
        int newAmmoAmount = player.getAmmoLeft() + this.getPlayerActions().getAmmoCost();
        player.setAmmoLeft(newAmmoAmount);
    }
    public void checkHealing(){
        if(this.playerActions.getID() == 701 && this.getHP() < Game.MAX_HP){
            this.setHP(getHP() + 1);
        }
    }

    public abstract int actionsSelecting(int optional_Index, int AmmoLeft, GameStats GameStats);

    public static Players dangerousComparing(Players player1,Players player2){
        Players winPlayer = null;
        if(player1.getPlayerActions().getDangerous() > 0){//都有危险性，必须要打
            if(player2.getPlayerActions().getDangerous() > player1.getPlayerActions().getDangerous()){//有实力，但没比过
                winPlayer = player2;
            }
            else if(player2.getPlayerActions().getDangerous() < player1.getPlayerActions().getDangerous()){//有实力，也打过了
                winPlayer = player1;
            }
            else{
                //掷色子 50%概率
                if(player1.getPlayerActions().getLegit() * player2.getPlayerActions().getLegit() > 0){
                    //同行，不干架，
                }else if(player1.getPlayerActions().getLegit() * player2.getPlayerActions().getLegit() < 0){//if same dangerous,cops vs crims and always cop win
                    winPlayer = player1.getPlayerStats().isPolice()? player1:player2;
                }else{
                    if(RNGenerator.rateGenerator(50)){
                        winPlayer = player1;
                    }else{
                        winPlayer = player2;
                    }
                }   
            }
        }
        else if(player1.getPlayerActions().getDangerous() == 0){
            if(player2.getPlayerActions().getDangerous() > 0){
                winPlayer = player2;
            }else{
                //相安无事，返回空
                //可以删掉这个else
            }
        }    
        else{   //player1.getDangerous() < 0 你就是个DD
            if(player2.getPlayerActions().getDangerous() > 0){
                winPlayer = player2;
            }
        }
        return winPlayer;
    }


}
