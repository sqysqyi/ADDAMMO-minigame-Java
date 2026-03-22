package game.sqysqyi.ADDAMMOLOL.AddAmmoMain.Players;

import game.sqysqyi.ADDAMMOLOL.AddAmmoMain.Start;
import game.sqysqyi.ADDAMMOLOL.AddAmmoMain.Actions.Action;
import game.sqysqyi.ADDAMMOLOL.AddAmmoMain.Actions.ActionEvent;
import game.sqysqyi.ADDAMMOLOL.AddAmmoMain.Actions.ActionX;
import game.sqysqyi.ADDAMMOLOL.AddAmmoMain.Comparator.Comparator;
import game.sqysqyi.ADDAMMOLOL.AddAmmoMain.Comparator.Relationship;

import static game.sqysqyi.ADDAMMOLOL.AddAmmoMain.RoundStats.NONE;
import static game.sqysqyi.ADDAMMOLOL.AddAmmoMain.RoundStats.TIED;

import game.sqysqyi.ADDAMMOLOL.AddAmmoMain.RoundStats;
import game.sqysqyi.ADDAMMOLOL.AddAmmoUtil.AM_RNGenerator;

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

    /*************************************
     * getter/setter 分割线*******************************************
     */

    public Action selectActions(int ID) {
        Action actions = ActionX.searchActions(ID);
        return actions;
    }

    public void checkIsSteal_or_Police() {
        if (this.getPlayerActions().getLegit() < 0 && !this.playerStats.isPolice) {
            this.playerStats
            .setThief(true)
            .setPolice(false);
        } else if (this.getPlayerActions().getLegit() > 0 && !this.playerStats.isThief) {
            this.playerStats
            .setPolice(true)
            .setThief(false);
        }
    }

    public void setStateDefault() {
        this.playerStats.resetDmgDefThief();
        this.ammoLeft = 1;
        this.HP = 3;
    }

    public int damageDealtTo(Players target) {
        int damageDealt = 0;

        damageDealt = this.getPlayerActions().getRawDmg() - target.getPlayerActions().getRawDef();
        if (damageDealt < 0) {
            damageDealt = 0;
        }
        return damageDealt;
    }

    /**
     * Legacy method 
     */
    public void generalActivating() {
        //this.playerStats.setRawDef(this.getPlayerActions().getRawDef());
        int ammoleft = this.getAmmoLeft() - this.getPlayerActions().getAmmoCost();
        this.setAmmoLeft(ammoleft);

    }

    public void winActivating() {
        // player.setRawDef(playerActions.getRawDef());
        this.playerStats.setRawDmg(this.getPlayerActions().getRawDmg());

    }

    public void ammoRetureTo(Players player) {// return your ammo as succeed in stealing enemy's non_addammo actions

        int newAmmoAmount = player.getAmmoLeft() + this.getPlayerActions().getAmmoCost();
        player.setAmmoLeft(newAmmoAmount);
    }

    public void checkHealing(int damageReceived) {
        if (this.playerActions.getID() == 701) {
            if (damageReceived <= 0 && this.HP < Start.setMaxHP) {
                this.HP += 1;
                System.out.println("debug");
            } else if (damageReceived > 0) {
                this.HP -= damageReceived;
            }

        }
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

    /**
     * Comparing players' actions and give out whoever win this round
     * @param player1 usually the player
     * @param player2 usually the enemy, doesnt matter tho
     * @return if is NULL, it was a tie round; or return who won
     */
    @Deprecated
    public static Players Comparing(Players player1, Players player2) {

        int player1Dangerous = player1.getPlayerActions().getDangerous();
        int player2Dangerous = player2.getPlayerActions().getDangerous();

        if (PlayerStats.equalWithOnce(player1.getPlayerStats(), player2.getPlayerStats()))
            return null;// 先把同行剔除
        if (player1Dangerous > player2Dangerous) {
            if (player1.getPlayerStats().isPolice) {
                // ***add exceptional rules below: */
                // if(player2.getPlayerActions().getID() == 701) return null;
                if (player2.getPlayerStats().isEngineer)
                    return null;
                if (player2Dangerous <= 0)
                    return null;
                /************* */
                return player1;
            } else if (player1.getPlayerStats().isThief) {
                /**** add exceptional rules below: */
                //if(player2.getPlayerStats().isMineReady()) return player2;
                /************* */
                return player1;

            } else {
                return player1;
            }
        } else if (player1Dangerous == player2Dangerous) {
            if (player1Dangerous <= 0)
                return null;
            if (player1.getPlayerStats().isPolice && player2.getPlayerStats().isThief) return player1;
            if (player2.getPlayerStats().isPolice && player1.getPlayerStats().isThief) return player2; 
            
            else {
                // ****add exceptional rules below: */

                /************** */
                AM_RNGenerator.isActivated = true;
                return AM_RNGenerator.rateGenerator(50) ? player1 : player2;

            }
        } else {
            if (player2.getPlayerStats().isPolice) {
                // ***add exceptional rules below: */
                // if(player1.getPlayerStats().isPolice()) return null;
                if (player1.getPlayerStats().isEngineer)
                    return null;
                if (player1Dangerous <= 0)
                    return null;
                /************* */
                return player2;
            } else if (player2.getPlayerStats().isThief) {
                // ***exceptional rules below: */
                if(player1.getPlayerStats().isMineReady) return player1;
                /************* */
                return player2;
            } else {
                return player2;
            }

        }
    }

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
