package game.addammo.AddAmmoMain.actions;

public final class Action {
    private int ID;
    private int ammoCost;
    private int dangerous;
    private boolean isStealable;
    private int rawDmg,rawDef;
    private String actionNameString;
    public ActionEvent actionEvent;
    public ActionLabelsContainer labelsContainer;

     

    public Action(){
        
    }
    
    public Action(int iD,String actionNameString, int ammoCost, 
        int dangerous, int rawDmg, int rawDef,
        ActionEvent actionEvent, 
        ActionLabelsContainer labelsContainer) {
        this.ID = iD;
        this.ammoCost = ammoCost;
        this.dangerous = dangerous;
        this.actionEvent= actionEvent;
        this.rawDmg = rawDmg;
        this.rawDef = rawDef;
        this.actionNameString = actionNameString;
        this.labelsContainer = labelsContainer;
    }

    public ActionEvent getEvent(){
        return actionEvent;
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
    public void setActionStateDefault(){
        actionNameString = null;
        ammoCost = 0;
        dangerous = 0;
        ID = 0;
        rawDef = 0;
        rawDmg = 0;
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
