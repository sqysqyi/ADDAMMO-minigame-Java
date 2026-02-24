package ADDAMMOLOL_0_1_x.AddAmmoMain.Actions;

import ADDAMMOLOL_0_1_x.AddAmmoMain.Players.Players;

public final class Action {
    private int ID;
    private int ammoCost;
    private int dangerous,legit;
    private boolean isStealable;
    private int rawDmg,rawDef;
    private String actionNameString;
    private ActionEvent actionEvent;
    private int specificSign;

    public Action(){
        
    }
    
    public Action(int iD,String actionNameString, int ammoCost, int dangerous, int legit, ActionEvent actionEvent, boolean isStealable, int rawDmg, int rawDef) {
        this.ID = iD;
        this.ammoCost = ammoCost;
        this.dangerous = dangerous;
        this.legit = legit;
        this.actionEvent= actionEvent;
        this.isStealable = isStealable;
        this.rawDmg = rawDmg;
        this.rawDef = rawDef;
        this.actionNameString = actionNameString;
    }

    //legacy
    public Action(int iD,String actionNameString, int ammoCost, int dangerous, int legit, int specificSign, boolean isStealable, int rawDmg, int rawDef){
        this.ID = iD;
        this.ammoCost = ammoCost;
        this.dangerous = dangerous;
        this.legit = legit;
        this.specificSign= specificSign;
        this.isStealable = isStealable;
        this.rawDmg = rawDmg;
        this.rawDef = rawDef;
        this.actionNameString = actionNameString;
    }

    public ActionEvent getEvent(){
        return actionEvent;
    }
    
    public int getSpecificSign() {
        return specificSign;
    }

    public void setSpecificSign(int specificSign) {
        this.specificSign = specificSign;
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

    @Override
    public String toString(){
        return getActionNameString()==null?"null":getActionNameString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ID;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Action other = (Action) obj;
        if (ID != other.ID)
            return false;
        return true;
    }
}
