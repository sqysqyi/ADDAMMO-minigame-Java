package ADDAMMOLOL_0_0_1d;

import java.util.Scanner;

public abstract class mainPlayer {
    private int HP,ammoLeft;
    private playerStats playerStats;
    private gameStats gameStats;
    private String playerNameString;
    private actions playerActions;


    public mainPlayer(){

    }
    public mainPlayer(int HP, int ammoLeft, playerStats playerStats,
                    gameStats gameStats, actions playerActions, String playerNameString){
        this.HP = HP;
        this.ammoLeft = ammoLeft;
        this.playerStats = new playerStats();
        this.gameStats = gameStats;
        this.playerNameString = playerNameString;
    }

    public gameStats getGameStats() {
        return gameStats;
    }
    public void setGameStats(gameStats gameStats) {
        this.gameStats = gameStats;
    }
    public playerStats getPlayerStats() {
        return playerStats;
    }
    public void setPlayerStats(playerStats playerStats) {
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
        HP = hP;
    }
    public int getAmmoLeft() {
        return ammoLeft;
    }
    public void setAmmoLeft(int ammoLeft) {
        this.ammoLeft = ammoLeft;
    }
    
    
    public actions getPlayerActions(){
        return playerActions;
    }
    public void setPLayerActions(actions playerActions){
        this.playerActions = playerActions;
    }
    /*************************************getter/setter 分割线******************************************* */ 

    public actions selectActions(int ID){
        actions actions = actionsLib.searchActions(ID);    
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

    public int damageDealtTo(mainPlayer target){
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
    public void ammoRetureTo(mainPlayer player){//return your ammo as succeed in stealing enemy's non_addammo actions
        
        int newAmmoAmount = player.getAmmoLeft() + this.getPlayerActions().getAmmoCost();
        player.setAmmoLeft(newAmmoAmount);
    }
    public void checkHealing(){
        if(this.playerActions.getID() == 701 && this.getHP() < game.MAX_HP){
            this.setHP(getHP() + 1);
        }
    }

    public abstract int actionsSelecting(Scanner playerInput);
    public abstract int actionsSelecting(int opponentHP_left, int opponentAmmoLeft, gameStats opponentGameStats);



}
