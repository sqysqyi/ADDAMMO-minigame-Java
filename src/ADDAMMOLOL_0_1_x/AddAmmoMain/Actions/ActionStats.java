package ADDAMMOLOL_0_1_x.AddAmmoMain.Actions;

public class ActionStats {
    private boolean isMissileSettled,isMineActivated;
    //记录游戏过程中玩家激活的状态，现有和规划的是：导弹发射架&地雷
    public ActionStats(){
        this.setMineActivated(false);
        this.setMissileSettled(false);
        //default stats 
    };

    public ActionStats(boolean isMissileSettled, boolean isMineActivated){
        this.isMineActivated = isMineActivated;
        this.isMissileSettled = isMissileSettled;
        
    }

    public boolean isMissileSettled() {
        return isMissileSettled;
    }

    public void setMissileSettled(boolean isMissileSettled) {
        this.isMissileSettled = isMissileSettled;
    }

    public boolean isMineActivated() {
        return isMineActivated;
    }

    public void setMineActivated(boolean isMineActivated) {
        this.isMineActivated = isMineActivated;
    }


}
