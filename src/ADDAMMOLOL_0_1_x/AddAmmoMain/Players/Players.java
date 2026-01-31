package ADDAMMOLOL_0_1_x.AddAmmoMain.Players;

import ADDAMMOLOL_0_1_x.AddAmmoMain.Start;
import ADDAMMOLOL_0_1_x.AddAmmoMain.Actions.Actions;
import ADDAMMOLOL_0_1_x.AddAmmoMain.Actions.ActionsLib;
import ADDAMMOLOL_0_1_x.AddAmmoUtil.AM_RNGenerator;

public abstract class Players {
    private int HP, ammoLeft;
    private PlayerStats playerStats;
    // private ActionStats gameStats;
    private String playerNameString;
    private Actions playerActions;

    public Players() {}

    public Players(int HP, int ammoLeft,
            Actions playerActions, String playerNameString) {
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

    public Actions getPlayerActions() {
        return playerActions;
    }

    public void setPlayerActions(Actions playerActions) {
        this.playerActions = playerActions;
    }

    /*************************************
     * getter/setter 分割线*******************************************
     */

    public Actions selectActions(int ID) {
        Actions actions = ActionsLib.searchActions(ID);
        return actions;
    }

    public void checkIsSteal_or_Police() {
        if (this.getPlayerActions().getLegit() < 0 && !this.playerStats.isPolice()) {
            this.playerStats.setThief(true);
            this.playerStats.setPolice(false);
        } else if (this.getPlayerActions().getLegit() > 0 && !this.playerStats.isThief()) {
            this.playerStats.setPolice(true);
            this.playerStats.setThief(false);
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

    public void generalActivating() {
        this.playerStats.setRawDef(this.getPlayerActions().getRawDef());

        int ammoleft = this.getAmmoLeft() - this.getPlayerActions().getAmmoCost();
        this.setAmmoLeft(ammoleft);
        this.checkIsSteal_or_Police();
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

    /**
     * Need override this method actionSelecting(int,int,PlayerStats),when creating a PLayers instance
     * 
     * @param optional_Index this can be optional
     * @param AmmoLeft Ammo left
     * @param playerStats Player stats
     * @return the final action ID
     */
    public abstract int actionsSelecting(int input, Players player, PlayerStats playerStats);

    /**
     * Comparing players' actions and give out whoever win this round
     * @param player1 usually the player
     * @param player2 usually the enemy, doesnt matter tho
     * @return if is NULL, it was a tie round; or return who won
     */
    public static Players Comparing(Players player1, Players player2) {

        int player1Dangerous = player1.getPlayerActions().getDangerous();
        int player2Dangerous = player2.getPlayerActions().getDangerous();

        if (PlayerStats.equalWithOnce(player1.getPlayerStats(), player2.getPlayerStats()))
            return null;// 先把同行剔除
        if (player1Dangerous > player2Dangerous) {
            if (player1.getPlayerStats().isPolice()) {
                // ***add exceptional rules below: */
                // if(player2.getPlayerActions().getID() == 701) return null;
                if (player2.getPlayerStats().isEngineer())
                    return null;
                if (player2Dangerous <= 0)
                    return null;
                /************* */
                return player1;
            } else if (player1.getPlayerStats().isThief()) {
                /**** add exceptional rules below: */
                // if(player2.getPlayerStats().isThief()) return null;
                /************* */
                return player1;
            } else {
                return player1;
            }
        } else if (player1Dangerous == player2Dangerous) {
            if (player1Dangerous <= 0)
                return null;
            else {
                // ****add exceptional rules below: */

                /************** */
                AM_RNGenerator.isActivated = true;
                return AM_RNGenerator.rateGenerator(50) ? player1 : player2;

            }
        } else {
            if (player2.getPlayerStats().isPolice()) {
                // ***add exceptional rules below: */
                // if(player1.getPlayerStats().isPolice()) return null;
                if (player1.getPlayerStats().isEngineer())
                    return null;
                if (player1Dangerous <= 0)
                    return null;
                /************* */
                return player2;
            } else if (player2.getPlayerStats().isThief()) {
                // ***exceptional rules below: */
                // if(player1.getPlayerStats().isThief()) return null;
                /************* */
                return player2;
            } else {
                return player2;
            }

        }
    }

    @Override
    public String toString() {

        return "[" + this.playerNameString + ">> RawDef: " + this.getPlayerStats().getRawDef() +
                ", RawDmg; " + this.getPlayerStats().getRawDmg() +
                ", AmmoCost: " + this.getPlayerActions().getAmmoCost() +
                ", IsStealing: " + this.getPlayerStats().isThief() +
                ", IsPolice: " + this.getPlayerStats().isPolice() + "]";
    }


    /**
     * Inner class PlayerStats 
     */
    public class PlayerStats {
        private int rawDmg, rawDef;
        private boolean isThief, isPolice, isEngineer;
        private boolean isMissileSettled, isMineReady;

        public boolean isMissileSettled() {
            return isMissileSettled;
        }

        public void setMissileSettled(boolean isMissileSettled) {
            this.isMissileSettled = isMissileSettled;
        }

        public boolean isMineReady() {
            return isMineReady;
        }

        public void setMineReady(boolean isMineReady) {
            this.isMineReady = isMineReady;
        }

        public int getRawDmg() {
            return rawDmg;
        }

        public void setRawDmg(int rawDmg) {
            this.rawDmg = rawDmg;
        }

        public int getRawDef() {
            return rawDef;
        }

        public void setRawDef(int rawDef) {
            this.rawDef = rawDef;
        }

        public boolean isThief() {
            return isThief;
        }

        public void setThief(boolean isThief) {
            this.isThief = isThief;
        }

        public boolean isPolice() {
            return isPolice;
        }

        public void setPolice(boolean isPolice) {
            this.isPolice = isPolice;
        }

        public boolean isEngineer() {
            return isEngineer;
        }

        public void setEngineer(boolean isEngineer) {
            this.isEngineer = isEngineer;
        }

        public PlayerStats() {
            resetDmgDefThief();
            resetAllSettlements();
        }

        public void resetDmgDefThief() {
            this.setRawDmg(0);
            this.setRawDef(0);
            this.setThief(false);
            this.setPolice(false);
            this.setEngineer(false);
        }

        public void resetAllSettlements() {
            this.setMissileSettled(false);
            this.setMineReady(false);
        }

        public static boolean equalWithOnce(PlayerStats player1Stats, PlayerStats player2Stats) {

            if (player1Stats.isEngineer == player2Stats.isEngineer && player1Stats.isEngineer == true)
                return true;
            if (player1Stats.isPolice == player2Stats.isPolice && player1Stats.isPolice == true)
                return true;
            if (player1Stats.isThief == player2Stats.isThief && player1Stats.isPolice == true)
                return true;
            return false;
        }

    }
}
