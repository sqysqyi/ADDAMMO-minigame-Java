package game.sqysqyi.ADDAMMOLOL.AddAmmoUtil;

import game.sqysqyi.ADDAMMOLOL.AddAmmoMain.Game;
import game.sqysqyi.ADDAMMOLOL.AddAmmoMain.RoundStats;
import game.sqysqyi.ADDAMMOLOL.AddAmmoMain.Actions.Action;

import static game.sqysqyi.ADDAMMOLOL.AddAmmoMain.RoundStats.*;
/**
 * A recorder based on an eight-length array
 * 
 */
public final class AM_Recorder {

    private SubRecord[] records ;
    private int maxSize = 8; 
    
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

    public boolean isEmpty(){
        if(records[0].getSerial() == -1) return true;
        else return false;
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
                if(records[i].getSerial() >= maxSize - 1){
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
    public SubRecord newRecord(Action enemyActions, Action thisActions, RoundStats result){
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

    public void simpleSummary(){
        
        for(SubRecord r : records){ 
            if(r.getThisActions() == null) break;

            int d = r.getThisActions().getDangerous(),od = r.getOppActions().getDangerous();
            int l = r.getThisActions().getLegit(), ol = r.getThisActions().getLegit();
            int a = r.getThisActions().getAmmoCost(), oa = r.getOppActions().getAmmoCost();
            
            if(r.getResult() == WIN){
                Game.global_dangerous -= d - od;
                Game.global_legit -= l - ol;
                Game.global_peace -= a - oa;
            }else if(r.getResult() == LOST ){
                Game.global_dangerous += d - od;
                Game.global_legit += l - ol;
                Game.global_peace += a - oa;
            }else if(r.getResult() == TIED){
                Game.global_dangerous -= Math.max(d, od);
                Game.global_legit -= Math.max(l, ol);
                Game.global_peace -= Math.min(a, oa);
            }else{
                throw new IllegalArgumentException();
            }
        }   
    }

    public int genConfidence(){
        int confidence_cache = 500;
        int internal_counter = 1;
        RoundStats last_result = NONE;//as the default result index means: No last result record
        for(SubRecord r: records){
            internal_counter = r.getResult() == last_result? internal_counter++:1;
            confidence_cache = r.getResult() != WIN ? confidence_cache - 4*internal_counter:confidence_cache + 6*internal_counter;
            last_result = r.getResult();
        }
        return confidence_cache;
    }

    public SubRecord searchBySerial(int serial){
        for(SubRecord r: records){
            if(r.getSerial() == serial) return r;
        }
        return null;
    }

    /**
     * Specifically used during making decision
     * @param start the start serial wthin the Recorder
     * @param end the end serial within the Recorder
     * @param rule the method how you wanto filter the target records
     * @return if exist the record match the rules given
     * @see AM_Decision
     */
    public boolean ifExistBetween(int start, int end, AM_Carrier<Action> rule){
        
        for(SubRecord r: records){
            if(r.getSerial()< start || r.getSerial() > end) continue;
            if((rule.matchWith(r.getThisActions()) && r.getResult() == LOST) 
                || (rule.matchWith(r.getOppActions()) && r.getResult() == WIN)){
                return true;
            }
        }
        return false;
    }
    /**
     * From start serial to the very end of the recorder
     * @param start
     * @param rule
     * @return
     */
    public boolean ifExistBetween(int start, AM_Carrier<Action> rule){
        return ifExistBetween(start<currentSize()-1?start:currentSize()-1 , currentSize() - 1, rule);
    }
    /**
     * Get from end
     * @param target 
     * @param lengthTillEnd like recorder [1,2,3,4,5,6,7,8], with parameters (rule,lengthTillEnd),would get the result as:(rule, 3)->> [6,7,8] 
     * @return
     */
    public boolean ifExistBetween(AM_Carrier<Action> rule, int lengthTillEnd){
        if(currentSize() -1-lengthTillEnd < 0) return ifExistBetween(0, rule);
        else return ifExistBetween(currentSize()-1-lengthTillEnd, currentSize()-1, rule);
    }

    @Override
    public String toString(){
        StringBuilder out = new StringBuilder();
        for(SubRecord r: records){
            out.append(r.toString());
            out.append("\n\r");
        }
        return out.toString();
    }//for debug ueage 
    
    private class SubRecord {
        private Action opponentHistory,thisHistory;
        private int serial;
        private RoundStats result;

        private SubRecord(){
            this.serial = -1;
            this.opponentHistory = null;
            this.thisHistory = null;
            this.result = TIED;
        }
        private SubRecord(int serial, Action opponentHistory, Action thisHistory, RoundStats result) {
            this.serial = serial;
            this.opponentHistory = opponentHistory;
            this.thisHistory = thisHistory;
            this.result = result;
        }

        public Action getOppActions() {
            return opponentHistory;
        }
        public void setOppActions(Action opponentActions) {
            this.opponentHistory = opponentActions;
        }
        public Action getThisActions() {
            return thisHistory;
        }
        public void setThisActions(Action thisActions) {
            this.thisHistory = thisActions;
        }
        public int getSerial() {
            return serial;
        }
        public void setSerial(int serial) {
            this.serial = serial;
        }
        public RoundStats getResult() {
            return result;
        }
        public void setResult(RoundStats result) {
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

    public interface AM_Carrier<M> {
        boolean matchWith(M m);
        
    }
}


