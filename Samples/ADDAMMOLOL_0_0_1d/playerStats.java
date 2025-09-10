package ADDAMMOLOL_0_0_1d;


public class playerStats {
    private int rawDmg,rawDef;
    private boolean isThief,isPolice,isEngineer;

    //记录玩家的每回合输出的状态，便于进行比较
    //包括 玩家将要造成的伤害，获得的防御，是否是小偷、警察和工程师的属性
    public playerStats(){
        resetDmgDefThief();
    }

    public playerStats(int rawDmg, int rawDef, boolean isThief, boolean isPolice, boolean isEngineer) {
        this.rawDmg = rawDmg;
        this.rawDef = rawDef;
        this.isThief = isThief;
        this.isPolice = isPolice;
        this.isEngineer = isEngineer;
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

    public void resetDmgDefThief(){
        this.setRawDmg(0);
        this.setRawDef(0);
        this.setThief(false);
        this.setPolice(false);
        this.setEngineer(false);
    }

    
    
}
    

