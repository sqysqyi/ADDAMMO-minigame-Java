package ADDAMMOLOL_0_1_x.AddAmmoMain.Actions;

public final class Actions {
    private int ID;
    private int ammoCost;
    private int dangerous,legit;
    private boolean isStealable;
    private int rawDmg,rawDef;
    private String actionNameString;
    private int specificSign;
    
    
    public Actions(){

    }
    
    public Actions(int iD,String actionNameString, int ammoCost, int dangerous, int legit, int specificSign, boolean isStealable, int rawDmg, int rawDef) {
        this.ID = iD;
        this.ammoCost = ammoCost;
        this.dangerous = dangerous;
        this.legit = legit;
        this.specificSign = specificSign;
        this.isStealable = isStealable;
        this.rawDmg = rawDmg;
        this.rawDef = rawDef;
        this.actionNameString = actionNameString;
    }


    public int getID() {
        return ID;
    }

    public int getAmmoCost() {
        return ammoCost;
    }

    public int getDangerous() {
        return dangerous;
    }

    public int getLegit() {
        return legit;
    }

    public boolean isStealable() {
        return isStealable;
    }

    public int getRawDmg() {
        return rawDmg;
    }

    public int getRawDef() {
        return rawDef;
    }

    public String getActionNameString() {
        return actionNameString;
    }

    public void setID(int iD) {
        this.ID = iD;
    }

    public void setAmmoCost(int ammoCost) {
        this.ammoCost = ammoCost;
    }

    public void setDangerous(int dangerous) {
        this.dangerous = dangerous;
    }

    public void setLegit(int legit) {
        this.legit = legit;
    }

    public void setStealable(boolean isStealable) {
        this.isStealable = isStealable;
    }

    public void setRawDmg(int rawDmg) {
        this.rawDmg = rawDmg;
    }

    public void setRawDef(int rawDef) {
        this.rawDef = rawDef;
    }

    public void setActionNameString(String actionNameString) {
        this.actionNameString = actionNameString;
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
