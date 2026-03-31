package game.addammo.AddAmmoMain.players;

import static game.addammo.AddAmmoMain.RoundStats.*;

import game.addammo.AddAmmoMain.RoundStats;
import game.addammo.AddAmmoMain.actions.Action;
import game.addammo.AddAmmoMain.actions.ActionX;
import game.addammo.AddAmmoMain.comparator.Comparator;
import game.addammo.AddAmmoMain.comparator.Relationship;

public abstract class Players {
    private int HP, ammoLeft;
    private PlayerStats playerStats;
    private String playerNameString;
    private Action playerActions;

    public Players() {
        this.HP = 1;
        this.ammoLeft = 1;
        this.playerActions = ActionX.ADD_AMMO.toAction();
        this.playerStats = new PlayerStats();
        this.playerNameString = "test";
    }

    public Players(int HP, int ammoLeft,
            Action playerActions, String playerNameString) {
        this.HP = HP;
        this.ammoLeft = ammoLeft;
        this.playerStats =  new PlayerStats();
        this.playerActions = playerActions;
        this.playerNameString = playerNameString;
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

    public Action getPlayerActions() {
        return playerActions;
    }

    public void setPlayerActions(Action playerActions) {
        this.playerActions = playerActions;
    }



    public Action selectActions(int ID) {
        Action actions = ActionX.searchActions(ID);
        return actions;
    }

    public void stolenActivated(Action stolenAction, Players stolenFrom){
        System.out.println("???");
        getPlayerStats().basicApply(stolenAction);
        stolenAction.getEvent().doWhen(RoundStats.WIN, this, new Players[]{stolenFrom} );
    }

    /**
     * Need override this method actionSelecting(int,int,PlayerStats),when creating a PLayers instance
     * 
     * @param optional_Index this can be optional
     * @param AmmoLeft Ammo left
     * @param playerStats Player stats
     * @return the final action ID
     */
    public abstract int actionsSelecting(int input, Player player, PlayerStats playerStats);

    public void ammoRefund(Action a){
        this.ammoLeft += a.getAmmoCost();
    }

    public RoundStats myLastRoundStats(Comparator activeComparator){
        for(Relationship r: activeComparator.getToDoList()){
            if(r.from == this){
                return r.stats;
            }
        }
        return TIED;
    }

    @Override
    public String toString() {

        return "[" + this.playerNameString + ">> RawDef: " + this.getPlayerStats().rawDef +
                ", RawDmg; " + this.getPlayerStats().rawDmg +
                ", AmmoCost: " + this.getPlayerActions().getAmmoCost()+
                ", IsStealing: " + this.getPlayerStats().isThief +
                ", IsPolice: " + this.getPlayerStats().isPolice + "]";
    }


    /**
     * Inner class PlayerStats 
     * @see Players
     */
    public class PlayerStats {
        public int rawDmg, rawDef;
        public boolean isThief, isPolice, isEngineer;
        public boolean isMissileSettled, isMineReady;
        public boolean healingFlag;
        public RoundStats roundStats = NONE;

        private int mineTimer = 0;


        public PlayerStats setHealingFlag(boolean healingFlag) {
            this.healingFlag = healingFlag;
            return this;
        }

        public PlayerStats setMissileSettled(boolean isMissileSettled) {
            this.isMissileSettled = isMissileSettled;
            return this;
        }

        public PlayerStats setMineReady(boolean isMineReady) {
            if(isMineReady){
                this.mineTimer = 3;
            }
            this.isMineReady = isMineReady;
            return this;
        }
        public boolean mineExpired(){
            System.out.println("mine timer: " + this.mineTimer);
            if(this.mineTimer == 0) {
                this.setMineReady(false);
                return true;
            }
            else{
                this.mineTimer -- ;
                return false; 
            }     
        }

        public PlayerStats setRawDmg(int rawDmg) {
            this.rawDmg = rawDmg;
            return this;
        }

        public PlayerStats setRawDef(int rawDef) {
            this.rawDef = rawDef;
            return this;
        }

        public PlayerStats setThief(boolean isThief) {
            this.isThief = isThief;
            return this;
        }

        public PlayerStats setPolice(boolean isPolice) {
            this.isPolice = isPolice;
            return this;
        }

        public PlayerStats setEngineer(boolean isEngineer) {
            this.isEngineer = isEngineer;
            return this;
        }

        public PlayerStats tag(RoundStats stats){
            this.roundStats = stats;
            return this;
        }

        public PlayerStats() {
            resetDmgDefThief();
            resetAllSettlements();
        }

        public void resetDmgDefThief() {
            this
            .setRawDmg(0)
            .setRawDef(0)
            .setThief(false)
            .setPolice(false)
            .setEngineer(false)
            .setHealingFlag(false)
            .tag(RoundStats.NONE);
        }

        /**
         * This method ONLY affect damage and defense 
         * @param a the action you would apply on
         */
        public PlayerStats basicApply(Action a){
            this
            .setRawDef(a.getRawDef())
            .setRawDmg(a.getRawDmg());
            return this;
        }


        public void resetAllSettlements() {
            this
            .setMissileSettled(false)
            .setMineReady(false);
        }

        public static boolean equalWithOnce(PlayerStats player1Stats, PlayerStats player2Stats) {

            if (player1Stats.isEngineer == player2Stats.isEngineer && player1Stats.isEngineer == true)
                return true;
            if (player1Stats.isPolice == player2Stats.isPolice && player1Stats.isPolice == true)
                return true;
            if (player1Stats.isThief == player2Stats.isThief && player1Stats.isThief == true)
                return true;
            return false;
        }

    }
}
