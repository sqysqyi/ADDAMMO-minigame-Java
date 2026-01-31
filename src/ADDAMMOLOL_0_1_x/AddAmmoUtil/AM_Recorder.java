package ADDAMMOLOL_0_1_x.AddAmmoUtil;

import java.util.function.Predicate;

import ADDAMMOLOL_0_1_x.AddAmmoMain.Actions.Actions;
import ADDAMMOLOL_0_1_x.AddAmmoMain.Actions.ActionsLib;
import ADDAMMOLOL_0_1_x.AddAmmoUtil.DebugTool.Debug;
/**
 * A recorder based on an eight-length array
 * 
 */
public final class AM_Recorder{

    private SubRecord[] records ;
    private int maxSize = 4; 
    
    public AM_Recorder (){
        init();
        
    }
    private void init(){
        records = new SubRecord[maxSize];
        for(int i = 0; i< records.length; i++){
            records[i] = new SubRecord();
        }
    }
   
    //int[] serial {1, 0, 7, 6, 5, 4, 3, 2}
    //Act[] oppAct {A, C, n, n, n, n, n, n}
    //Act[] thsAct {B, D, n, n, n, n, n, n}
    //int[] result {0, 0, 0, 0, 0, 0, 0, 0}

    //int[] serial {3, 2, 1, 0, -1, -1, -1, -1}
    //Act[] oppAct {A, C, n, n, n, n, n, n}
    //Act[] thsAct {B, D, n, n, n, n, n, n}
    //int[] result {0, 0, 0, 0, 0, 0, 0, 0}

    //subRecord{0 aa bb 0}
    public int currentSize(){
        int counter = 0;
        for(SubRecord r : records){
            if(r.getSerial() == (maxSize - 1) ) return maxSize;//在找到max size - 1的序列号后说明整个记录表已经被填满
            if(r.getSerial() == -1) break;
            counter ++;
        }
        return counter;
    }
    //向记录器中添加记录
    //********************* begin of add()
    public void add(SubRecord inputRecord){
        for (int i = 0; i < records.length; i++) {
            if(inputRecord != null){
                if(records[i].getSerial() == -1){//对应数组为空，可以写入
                    //inputRecord.setSerial(0);
                    writeRecord(inputRecord,i);
                    
                    //actionSerial[i] = 0;
                    inputRecord = null;
                    
                    continue;
                }
                if(records[i].getSerial() >= 7){
                    //then add both actions into the recorder
                    //inputRecord.setSerial(i);
                    overwriteRecord(inputRecord,i);
                    continue;
                }
            }else{
                if(records[i].getSerial() == -1) break;       
            }
            records[i].setSerial(records[i].getSerial() +1);
        }
    }
    public SubRecord newRecord(Actions enemyActions, Actions thisActions, int result){
        return new SubRecord(0,enemyActions, thisActions, result);
    }
    private void writeRecord(SubRecord input, int at){   
        records[at] = input;
    }
    private void overwriteRecord(SubRecord input, int at){
        writeRecord(input, at);
    }//unimplement methods
    //**************** end of the add()
    
    final int this_win = 1;
    final int this_lost = -1;
    //final int tied = 0;
    public Actions[][] rcmdActions(){
        Actions[][] result = new Actions[3][];
        //如果当前record有效大小小于3，则因历史记录不足而无法生成结果，具体结果将在enemy中处理
        if (currentSize() < 3) return null;
        
        int counter = 3;
        int i = 0;
        boolean collecting = false;
        while(i < currentSize()){
            SubRecord r = records[i];
            if(r.getSerial() == currentSize()){ 
               collecting = true;
               break;
            }
            i++;
        }
        if (collecting) {
            while (counter > 0) {
                SubRecord r = records[i];
                result[3 - counter] = sift(r);
                counter--;
                i++;
                if (i >= currentSize()) i = 0;
                
            }
        }else{
            throw new IllegalArgumentException();
        }
        

        return result;
        
    }
    /*public int[] formReports(){
        int tension = 0;//局势的紧张度————下一回合玩家出现伤害性动作的度量
        int instability=0;//电脑认为的“玩家”下回合出现“非法”(legit < 0)的动作的度量
        //
        //以下暂行
        for(SubRecord record : records){//headache wtf it is ??????
            if (record == null) break;

        
            
            if(record.getResult() == this_win){
                tension += (record.getThisActions().getDangerous() - record.getOppActions().getDangerous());
                //紧张度增加为胜者与负者的dangerous差值
                instability += (record.getOppActions().getLegit() - record.getThisActions().getLegit());
                //玩家不稳定度负值为更易出小偷/正值更易出警察
            }else if(record.getResult() == win_win){
                tension -= Math.min(record.getThisActions().getDangerous(),record.getOppActions().getDangerous());
                //局势稍缓和
                instability += Math.min(record.getOppActions().getLegit(), record.getThisActions().getLegit());
                //玩家更有可能再来
            }else if(record.getResult() == this_lost){
                tension -= (record.getThisActions().getDangerous() - record.getOppActions().getDangerous());
                //刚得逞，不会有资源发动行动
                instability -= (record.getOppActions().getLegit() - record.getThisActions().getLegit());
                //玩家得逞，有可能放松警惕
            }else{
                throw new IllegalArgumentException("The record.getResult(),int should only be -1, 0 or 1");
                //game would crash this way
            }
        }

        int[] reports = {tension, instability};
        Debug.print("tension: "+tension+", instability: "+instability);
        return reports;
    }*/
    private Actions[] sift(SubRecord r) {
        if(r.getResult() == this_win){
            //在这一回合，this指电脑获胜，所以需要提防玩家重新尝试该actions
            return 
            (Actions[])ActionsLib.searchWtihCondition( t ->{
                //返回满足筛选条件的结果：
                //遵循能强则强的原则
                /*condition #1  */
                boolean c1 = t.getDangerous() >= r.getThisActions().getDangerous();

                /*condition #2  */
                boolean c2 = t.getLegit() * r.getOppActions().getLegit() > 0;

                /*condition #3  */
                boolean c3 = t.getID() == r.getOppActions().getID();

                /*condition #4  */
                boolean c4 = t.getID() == r.getThisActions().getID();

                return c1 || c2 || c3 || c4;
            })
            .toArray();
        }else if(r.getResult() == this_lost){
            return null;

        }else{
            return null;
        }
    }

    @Override
    public String toString(){
        StringBuilder out = new StringBuilder();
        for(SubRecord r: records){
            out.append(r.toString());
            out.append("\n\r");
        }
        return out.toString();
    }
    
    private class SubRecord {
        private Actions opponentHistory,thisHistory;
        private int serial,result;

        private SubRecord(){
            this.serial = -1;
            this.opponentHistory = null;
            this.thisHistory = null;
            this.result = 0;
        }
        private SubRecord(int serial, Actions opponentHistory, Actions thisHistory, int result) {
            this.serial = serial;
            this.opponentHistory = opponentHistory;
            this.thisHistory = thisHistory;
            this.result = result;
        }

        public Actions getOppActions() {
            return opponentHistory;
        }
        public void setOppActions(Actions opponentActions) {
            this.opponentHistory = opponentActions;
        }
        public Actions getThisActions() {
            return thisHistory;
        }
        public void setThisActions(Actions thisActions) {
            this.thisHistory = thisActions;
        }
        public int getSerial() {
            return serial;
        }
        public void setSerial(int serial) {
            this.serial = serial;
        }
        public int getResult() {
            return result;
        }
        public void setResult(int result) {
            this.result = result;
        }

        @Override
        public String toString(){
            return "["+"<serial number>"+getSerial()+
                    "<opntHis>"+getOppActions()+
                    "<thisHis>"+getThisActions()+
                    "<result>"+getResult()+"]";
        }

    }

}


