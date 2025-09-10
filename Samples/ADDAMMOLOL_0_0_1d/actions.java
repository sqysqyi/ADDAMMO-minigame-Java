package ADDAMMOLOL_0_0_1d;

public class actions {
    private int ID;
    private int ammoCost;
    private int dangerous,legit;
    private boolean isStealable;
    private int rawDmg,rawDef;
    private String actionNameString;
    
    public actions(){

    }
    
    public actions(int iD,String actionNameString, int ammoCost, int dangerous, int legit, boolean isStealable, int rawDmg, int rawDef) {
        this.ID = iD;
        this.ammoCost = ammoCost;
        this.dangerous = dangerous;
        this.legit = legit;
        this.isStealable = isStealable;
        this.rawDmg = rawDmg;
        this.rawDef = rawDef;
        this.actionNameString = actionNameString;
    }

    public String getActionNameString() {
        return actionNameString;
    }
    public void setActionNameString(String actionNameString) {
        this.actionNameString = actionNameString;
    }
    public int getID() {
        return ID;
    }
    public void setID(int iD) {
        ID = iD;
    }
    public int getAmmoCost() {
        return ammoCost;
    }
    public void setAmmoCost(int ammoCost) {
        this.ammoCost = ammoCost;
    }
    public int getDangerous() {
        return dangerous;
    }
    public void setDangerous(int dangerous) {
        this.dangerous = dangerous;
    }
    public int getLegit() {
        return legit;
    }
    public void setLegit(int legit) {
        this.legit = legit;
    }
    public boolean isStealable() {
        return isStealable;
    }
    public void setStealable(boolean isStealable) {
        this.isStealable = isStealable;
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

    public void setActionStateDefault(){
        this.setActionNameString(null);
        this.setAmmoCost(0);
        this.setDangerous(0);
        this.setID(0);
        this.setLegit(0);
        this.setRawDef(0);
        this.setRawDmg(0);
        this.setStealable(true);

    }
}
