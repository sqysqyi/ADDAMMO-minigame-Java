package ADDAMMOLOL_0_1_x.AddAmmoUtil;

import java.util.ArrayList;
import java.util.Random;
import ADDAMMOLOL_0_1_x.AddAmmoMain.Game;
import ADDAMMOLOL_0_1_x.AddAmmoMain.Actions.Actions;
import ADDAMMOLOL_0_1_x.AddAmmoMain.Actions.ActionsLib;
import ADDAMMOLOL_0_1_x.AddAmmoMain.Players.Enemy;
import ADDAMMOLOL_0_1_x.AddAmmoMain.Players.Player;
import ADDAMMOLOL_0_1_x.AddAmmoMain.Players.Players;
import Samples.ADDAMMOLOL_0_0_1d.actions;
import Samples.ADDAMMOLOL_0_0_1d.game;

/**
 * There is how to thinking 
 * Fake AI internal version v0.0.1
 * NEED MORE WORKS!!!!!
 */

// Question Answer and Decision
/*
 * Q: 1/Got ambition?
 * A:   Y -> Q: 2/Have enough ammo?
 *      N -> (new ambiton) -> Q: 1/Got ambition?
 * 
 * Q: 2/Have enough ammo?( Actions action )
 * A:   Y -> Q: 10/Act it right now?
 *      N -> Q: 3/Add ammo?
 * 
 * Q: 3/Add ammo?
 * A:   Y -> Q: 3-1/Is safe now?
 *      N -> Q: 3-2/Will steal?
 * 
 *      Q; 3-1/Is safe now?
 *      A:  Y -> (do AddAmmo Action) -> (end of thinking)
 *          N -> Q: 4/Will call police?
 * 
 *      Q: 3-2/Will steal?
 *      A:  Y -> Q: 3-2-1/Will be arrested?
 *          N -> Q: 4/Will call police?
 * 
 *          Q; 3-2-1/Will be arrested?
 *          A:  Y -> 3-2-2/Bet???
 *              N -> (do steal actions) -> (end of thinking)
 *          Q: 3-2-2/Bet???
 *          A:  Y -> (do steal actions) -> (end of thinking)
 *              N -> 5/Will strike first?
 * 
 * Q: 4/Will call police?
 * A:   Y -> Q: 4-1/Will be suppressed?
 *      N -> Q: 5/Will strike first?
 * 
 *      Q; 4-1/Will be suppressed? //by a stronger thief
 *      A:  Y -> Q: 5/Will strike first?
 *          N -> Q: 4-2/Will be attacked?
 * 
 *      Q: 4-2/Will be attacked? //bu any possible attacting actions
 *          A:  Y -> Q: 4-3/Stronger?
 *              N -> (do police related) -> (end of thinking)
 * 
 *      Q: 4-3/Stronger?
 *          A:  Y -> (do police realted) -> (end of thinking)
 *              N -> Q: 5/Will strike first?
 * 
 * Q: 5/Will strike first?
 *      A:  Y -> (change ambition) -> 2/Have enough ammo?
 *          N -> Q: 6/Will defend yourself?
 * 
 * Q: 6/Will defend yourself?
 *      A:  Y -> Q: 6-1/Will receive too much damage?
 *          N -> Q: 7/Will plant something?
 * 
 *      Q: 6-1/Will receive too much damage?
 *          A;  Y -> (do Absolute_def Action) -> (end of thinking)
 *              N -> (do defense Actions) -> (end of thinking)
 * 
 * Q: 7/Will plant something?
 *      A:  Y -> Q: 7-1/For attacking?
 *          N -> Q: 8/Will heal yourself?
 * 
 *      Q: 7-1/For attacking?
 *          A:  Y -> 7-1-1/Will be arrested?
 *              N -> 7-2/For self-defending?
 * 
 *      Q: 7-1-1/Will be arrested?
 *          A:  Y -> 7-2/For self-defending
 *              N -> (do missile setup action) -> (end of thinking)
 * 
 *      Q: 7-2/For self-defending?
 *          A:  Y -> 7-2-1/Will be cancelled?
 *              N -> 8/Will heal yourself?
 * 
 *          Q: 7-2-1/Will be cancelled?
 *          A:  Y -> 8/Will heal yourself?
 *              N -> (do related actions) -> (end of thinking)
 * 
 * Q: 8/Will heal yourself?
 *      A:  Y -> Q: 8-1/Will be attacked?
 *          N -> Q; 9/Re-thinking?
 * 
 *      Q: 8-1/Will be attacked?
 *          A:  Y -> 8-2/Q: Is emergency?
 *              N -> (do heal action) -> (end of thinking)
 * 
 *      Q: 8-2/Is emergency?
 *      A:  Y -> (do heal action) -> (end of thinking)
 *          N -> 9/Re-thinking?
 * 
 * Q: 9/Re-thinking?
 *      A:  Y -> Q: 1/Got ambition?
 *          N -> (do "AddAmmo")
 * 
 * 
 * 
 * Q: 10/Act it right now?
 * A:   Y -> Q; 11/Is slain-able?
 *      N -> Q: 3/Add ammo?
 * 
 * Q: 11/Is slain-able?
 * A:   Y -> (do ambition) -> (end of thinking)
 *      N -> 12/Bet???
 * 
 * Q: 12/Bet???
 * A:   Y -> (do ambition) -> (end of thinking)
 *      N -> 4/Will call police?
 * 
 * 
 */


public enum AM_Decision {
    //data from Enemy instance as follow
    //  opponent's HP ammoleft and player stats
    D_1("Q1: Got ambition?", AM_Decision::ques2D1, AM_Decision::ans2D1),
    D_2("Q2: Have enough ammo?", AM_Decision::ques2D2, AM_Decision::ans2D2),
    D_3("Q3: Add ammo?", AM_Decision::ques2D3, AM_Decision::ans2D3),
    D_3_1("Q3-1: Is safe now?",AM_Decision::ques2D3_1,AM_Decision::ans2D3_1),
    D_3_2("Q3-2: Will steal?",AM_Decision::ques2D3_2,AM_Decision::ans2D3_2),
    D_3_2_1("Q3-2-1: Will be arrested?",AM_Decision::ques2D3_2_1,AM_Decision::ans2D3_2_1),
    D_3_2_2("Q3-2-2: Bet?<Will be arrested?>",AM_Decision::ques2D3_2_2,AM_Decision::ans2D3_2_2),
    D_4("Will call police?",AM_Decision::ques2D4,AM_Decision::ans2D4),
    D_4_1("Will be suppressed?",AM_Decision::ques2D4_1,AM_Decision::ans2D4_1),
    D_4_2("Will be attacked?",AM_Decision::ques2D4_2,AM_Decision::ans2D4_2),
    D_4_3("Stronger?",AM_Decision::ques2D4_3,AM_Decision::ans2D4_3),
    D_5("Will strike first?",AM_Decision::ques2D5,AM_Decision::ans2D5),
    D_6("Will defense yourself?",AM_Decision::ques2D6,AM_Decision::ans2D6),
    D_6_1("Will receive too much damage?",AM_Decision::ques2D6_1,AM_Decision::ans2D6_1),
    D_7("Will plant something?",AM_Decision::ques2D7,AM_Decision::ans2D7),
    D_7_1("For attacking?",AM_Decision::ques2D7_1,AM_Decision::ans2D7_1),
    D_7_1_1("Will be arrested?",AM_Decision::ques2D7_1_1,AM_Decision::ans2D7_1_1),
    D_7_2("For defense?",AM_Decision::ques2D7_2,AM_Decision::ans2D7_2),
    D_7_2_1("Will be cancelled?",AM_Decision::ques2D7_2_1,AM_Decision::ans2D7_2_1),
    D_8("Will heal yourself?",AM_Decision::ques2D8,AM_Decision::ans2D8),
    D_8_1("Will be attacked?",AM_Decision::ques2D8_1,AM_Decision::ans2D8_1),
    D_8_2("Is in emergency?",AM_Decision::ques2D8_2,AM_Decision::ans2D8_2),
    D_9("Re-thinking?",AM_Decision::ques2D9,AM_Decision::ans2D9),
    D_10("Act it right now?",AM_Decision::ques2D10,AM_Decision::ans2D10),
    D_11("Is slain-able?",AM_Decision::ques2D11,AM_Decision::ans2D11),
    D_12("Do anyway?<Is slain-able>",AM_Decision::ques2D12,AM_Decision::ans2D12);
    
    private final Question question;
    private final Answer answer;
    private final String decisionDiscription;
    private static Enemy e; 
    private static Players p;
    private static int holdingID;
    private static int timer = 0;
    private static Actions[] result;
    private static boolean isOccupied = false;
    
    
    AM_Decision( String discription, Question q, Answer a){
        this.decisionDiscription = discription;
        this.question = q;
        this.answer = a;

    }

    public synchronized static void start(int ambitionID, Enemy me, Player opponent){
        if(!isOccupied){
            timer = 0;
            e = me;
            p = opponent;
            holdingID = ambitionID;
            nextDecision(D_1, ambitionID);
        }else{
            System.out.println("The instence is currently occupied, check if there is a previous one not being closed or read.");
            throw new IllegalStateException("The instence is currently occupied!");
            
        }
    }
    private static boolean clearChain(){
        try {
            timer = 0;
            e = null ; p = null;
            holdingID = 0;
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            isOccupied = false;
        }
    }

    public static void forceClose(){
        clearChain();
    }

    private static void nextDecision(AM_Decision decision, int currentID){
        timer ++;
        
        if(timer > 36) end(holdingID, holding ->{
            Actions r1 = ActionsLib.searchActions(holding);
            Actions r2 = ActionsLib.ADD_AMMO.toAction();
            Actions r3 = ActionsLib.DEF.toAction();
            Actions r4 = ActionsLib.PISTOL_SHOOT.toAction();
            System.out.println( "36th");

            return new Actions[]{r1,r2,r3,r4};
        });
        else {
            decision.answer.doWith( decision.question );
            System.out.println(decision.decisionDiscription);    
        }

    }

    protected static Actions[] end(int holdingID, Call_End<Actions> ce){
        
        if (ce == null) {
            result = new Actions[]{ActionsLib.searchActions(holdingID)};
        }else {
            result = ce.doFinally(holdingID);
        }
        clearChain();
        return result;
    }

    /**
     * ATTENTION! 
     * This is a temperary methond to obtain the result from logic chain, and also the only one
     * ALWAYS check there is start() before this method or this would throw exception
     * The result formed will be terminated after executed this method
     * @throws IllegalArgumentException
     * @return the finally action array contains what should be chosen from
     */
    public static Actions[] readResults(){
        Actions[] temp = result;
        if(temp == null) {
            temp = new Actions[]{ActionsLib.ADD_AMMO.toAction()};
            throw new IllegalStateException("Must get result AFTER creating logic chain!!!!");
        }
        result = null;
        return temp;
    }

    public interface Question {
        boolean test();
    }
    public interface Answer {
        void doWith(Question q);
    }
    public interface Call_End<E> {
        /**
         * what to do at end of thinking chain, if only one element needed, return the array with lenght 1.
         * @param holding
         * @return E[] which contains elements.
         */
        E[] doFinally(int holding);
        
    }

    //****************************************************************************************** */
    //All questions here below

    //Q1: Got ambition?
    static final boolean ques2D1(){
        return ActionsLib.searchActions(holdingID) != null;
    } 
    static final void ans2D1(Question q){
        if(q.test()){
            nextDecision(D_2, holdingID);
        }else{
            holdingID = Enemy.createAmbitious();
            nextDecision(D_1, holdingID);
        }
    }
    
    //Have enough ammo?
    static final boolean ques2D2(){
        return ActionsLib.searchActions(holdingID).getAmmoCost() <= e.getAmmoLeft();
    }
    static final void ans2D2(Question q){
        if(q.test()){
            nextDecision(D_10, holdingID);
        }else{
            nextDecision(D_3, holdingID);
        }
    }
    
    //Should add ammo now?
    static final boolean ques2D3(){
        return 
        ActionsLib.searchActions(holdingID).getAmmoCost() - e.getAmmoLeft() <= 1 ||
        p.getAmmoLeft() < 1;
    }
    static final void ans2D3(Question q){
        if(q.test()){
            nextDecision(D_3_1, holdingID);
        }else{
            nextDecision(D_3_2, holdingID);
        }
    }

    //Is safe now?
    static final boolean ques2D3_1(){
        return 
        Game.global_dangerous < 1 &&
        Game.global_legit > 0 &&
        Game.global_peace > 0;
    }
    static final void ans2D3_1(Question q){
        if(q.test()){
            end(holdingID, holding ->{
                return new Actions[]{ActionsLib.ADD_AMMO.toAction()};
            });
        }else{
            nextDecision(D_4, holdingID);
        }
    }

    //Will u steal?
    static final boolean ques2D3_2(){
        return 
        (p.getAmmoLeft() - e.getAmmoLeft() >= 2 || Game.global_dangerous > 3) &&
        e.getAmmoLeft() <= ActionsLib.searchActions(holdingID).getAmmoCost() * 0.7f;
    }
    static final void ans2D3_2(Question q){
        if(q.test()){
            nextDecision(D_3_2_1, holdingID);
        }else{
            nextDecision(D_4, holdingID);
        }
    }

    //if u stole, will be arrested?
    static final boolean ques2D3_2_1(){
        return 
        e.getRecorder().ifExistBetween(actions->{
            return actions.getLegit() > 0;
        }, 3);
    }
    static final void ans2D3_2_1(Question q){
        if(q.test()){
            nextDecision(D_3_2_2, holdingID);
        }else{
            end(holdingID, holding ->{
                ArrayList<Actions> temp = 
                ActionsLib.searchWtihCondition(condition ->{
                    return condition.getLegit() < 0 && condition.getDangerous() < 0;
                });

                return temp.toArray(new Actions[temp.size()]);
            });
        }
    }

    //bet???
    static final boolean ques2D3_2_2(){
        return AM_RNGenerator.newRNG(e.confidence());
    }
    static final void ans2D3_2_2(Question q){
        if(q.test()){
            end(holdingID, holding ->{
                ArrayList<Actions> temp = 
                ActionsLib.searchWtihCondition(condition ->{
                    return condition.getLegit() < 0 && condition.getDangerous() < 0;
                });

                return temp.toArray(new Actions[temp.size()]);
            });
        }else{
            nextDecision(D_5, holdingID);
        }
    }
    
    //Will call police? 
    

    static final boolean ques2D4(){
        return e.getAmmoLeft() >= 3 && AM_RNGenerator.newRNG(750);
    }
    static final void ans2D4(Question q){
        if(q.test()){
            nextDecision(D_4_1, holdingID);
        }else{
            nextDecision(D_5, holdingID);
        }
    }

    //Will be suppressed?
    
    static final boolean ques2D4_1(){
        return p.getAmmoLeft() >= 3 || e.getRecorder().ifExistBetween(actions ->actions.getLegit() < 0,3);
    }
    static final void ans2D4_1(Question q){
        if(q.test()){
            nextDecision(D_4_2, holdingID);
        }else{
            nextDecision(D_5, holdingID);
        }
    }

    //Will be attacked?
    
    static final boolean ques2D4_2(){
        return (p.getAmmoLeft() > 0 && AM_RNGenerator.newRNG(600)) 
        || (p.getAmmoLeft() > 3 && e.getRecorder().ifExistBetween(actions -> actions.getDangerous() > 0 && actions.getLegit() <= 0, 1));
    }
    static final void ans2D4_2(Question q){
        if(q.test()){
            nextDecision(D_4_3, holdingID);
        }else{
            nextDecision(D_5, holdingID);
        }
    }

    //wip
    //Stronger? 这里何意味？
    static final boolean ques2D4_3(){
        return AM_RNGenerator.newRNG(850);
    }
    static final void ans2D4_3(Question q){
        if(q.test()){
            end(holdingID, holding ->{
                ArrayList<Actions> temp = 
                ActionsLib.searchWtihCondition(condition ->{
                    return condition.getLegit() > 0 && condition.getDangerous() > 0;
                });

                return temp.toArray(new Actions[temp.size()]);    
            });
        }else{
            nextDecision(D_5, holdingID);
        }
    }

    //Will strike first?
    static final boolean ques2D5(){
        return AM_RNGenerator.newRNG(600);
    }
    static final void ans2D5(Question q){
        if(q.test()){

            ArrayList<Actions> poolList = ActionsLib.searchWtihCondition(
                action->action.getDangerous() > 0 
                && action.getLegit() == 0 
                && action.getAmmoCost() <= e.getAmmoLeft());
            Actions[] pool = poolList.toArray(new Actions[poolList.size()]);

            
            holdingID = poolList.size() != 0?pool[new Random().nextInt(pool.length)].getID():holdingID;
            
            nextDecision(D_2, holdingID);
        }else{
            nextDecision(D_6, holdingID);
        }
    }

    //Will defend yourself?
    static final boolean ques2D6(){
        return e.getHP() < game.MAX_HP * 0.3f && Game.global_dangerous > 1 && AM_RNGenerator.newRNG(500);
    }
    static final void ans2D6(Question q){
        if(q.test()){
            nextDecision(D_6_1, holdingID);
        }else{
            nextDecision(D_7, holdingID);
        }
    }

    //Will receive too much damage?
    static final boolean ques2D6_1(){
        return Game.global_dangerous > 3 || p.getAmmoLeft() > 3;
    }
    static final void ans2D6_1(Question q){
        if(q.test()){
            holdingID  = ActionsLib.ABSLOTE_DEF.toAction().getID();
            end(holdingID, null);
        }else{
            holdingID = ActionsLib.DEF.toAction().getID();
            end(holdingID, null);
        }
    }

    //Will plant something?
    static final boolean ques2D7(){
        return !e.getPlayerStats().isMineReady()|| !e.getPlayerStats().isMissileSettled() && AM_RNGenerator.newRNG(750);
    }
    static final void ans2D7(Question q){
        if(q.test()){
            nextDecision(D_7_1, holdingID);
        }else{
            nextDecision(D_8, holdingID);
        }
    }

    //D_7_1("For attacking?",AM_Decision::ques2D7_1,AM_Decision::ans2D7_1),
    //D_7_1_1("Will be arrested?",AM_Decision::ques2D7_1_1,AM_Decision::ans2D7_1_1),
    //D_7_2("For defense?",AM_Decision::ques2D7_2,AM_Decision::ans2D7_2),
    //D_7_2_1("Will be cancelled?",AM_Decision::ques2D7_2_1,AM_Decision::ans2D7_2_1),
    static final boolean ques2D7_1(){
        return AM_RNGenerator.newRNG(250);//0.25 * (1 - 0.75)* 0.33 so here is 250
    }
    static final void ans2D7_1(Question q){
        if(q.test()){
            nextDecision(D_7_1_1, holdingID);
        }else{
            nextDecision(D_7_2, holdingID);
        }
    }

    static final boolean ques2D7_1_1(){
        return 
        e.getRecorder().ifExistBetween(actions->{
            return e.getRecorder().isEmpty()?false:actions.getLegit() > 0;
        }, 3) && AM_RNGenerator.newRNG(400);
    }
    static final void ans2D7_1_1(Question q){
        if(q.test()){
            nextDecision(D_7_2, holdingID);
        }else{
            holdingID = ActionsLib.MISSILE_LAUNCHER.toAction().getID();
            end(holdingID, null);
        }
    }

    static final boolean ques2D7_2(){
        return AM_RNGenerator.newRNG(333);//0.25 = (1 - 0.25) * 0.33... ,thats why rate = 333 here
    }
    static final void ans2D7_2(Question q){
        if(q.test()){
            nextDecision(D_7_2_1, holdingID);
        }else{
            nextDecision(D_8, holdingID);
        }
    }

    static final boolean ques2D7_2_1(){
        return 
        e.getRecorder().ifExistBetween(actions->{
            return e.getRecorder().isEmpty()?false:actions.getLegit() > 0;
        }, 3) && AM_RNGenerator.newRNG(200);
    }
    static final void ans2D7_2_1(Question q){
        if(q.test()){
            nextDecision(D_8, holdingID);
        }else{
            holdingID = ActionsLib.MINE.toAction().getID();
            end(holdingID, null);
        }
    }

    //D_8("Will heal yourself?",AM_Decision::ques2D8,AM_Decision::ans2D8),
    //D_8_1("Will be attacked?",AM_Decision::ques2D8_1,AM_Decision::ans2D8_1),
    //D_8_2("Is in emergency?",AM_Decision::ques2D8_2,AM_Decision::ans2D8_2),
    static final boolean ques2D8(){
        return e.getHP() !=  game.MAX_HP;
    }
    static final void ans2D8(Question q){
        if(q.test()){
            nextDecision(D_8_1, holdingID);
        }else{
            nextDecision(D_9, holdingID);
        }
    }

    static final boolean ques2D8_1(){
        return Game.global_dangerous > 0 && !e.getRecorder().ifExistBetween(actions -> actions.getDangerous() > 0,3);
    }
    static final void ans2D8_1(Question q){
        if(q.test()){
            nextDecision(D_8_2, holdingID);
        }else{
            holdingID = ActionsLib.HEALING.toAction().getID();
            end(holdingID, null);
        }
    }

    static final boolean ques2D8_2(){
        return e.getHP() == 1 && (e.getAmmoLeft() == 1 || p.getAmmoLeft() > 1);
    }
    static final void ans2D8_2(Question q){
        if(q.test()){
            holdingID = ActionsLib.HEALING.toAction().getID();
            end(holdingID, null);
        }else{
            nextDecision(D_9, holdingID);
        }
    }

    //D_9("Re-thinking?",AM_Decision::ques2D9,AM_Decision::ans2D9),
    //D_10("Act it right now?",AM_Decision::ques2D10,AM_Decision::ans2D10),
    //D_11("Is slain-able?",AM_Decision::ques2D11,AM_Decision::ans2D11),
    //D_12("Do anyway?<Is slain-able>",AM_Decision::ques2D12,AM_Decision::ans2D12);
    static final boolean ques2D9(){
        return AM_RNGenerator.newRNG(800);
    }
    static final void ans2D9(Question q){
        if(q.test()){
            nextDecision(D_1, holdingID);
        }else{
            holdingID = ActionsLib.ADD_AMMO.toAction().getID();
            end(holdingID, null);
        }
    }

    static final boolean ques2D10(){
        return p.getAmmoLeft() != 0;
    }
    static final void ans2D10(Question q){
        if(q.test()){
            nextDecision(D_11, holdingID);
        }else{
            nextDecision(D_3, holdingID);
        }
    }

    static final boolean ques2D11(){
        return p.getHP() <= ActionsLib.searchActions(holdingID).getRawDmg() && p.getAmmoLeft() < 3;
    }
    static final void ans2D11(Question q){
        if(q.test()){
            end(holdingID, null);
        }else{
            nextDecision(D_12, holdingID);
        }
    }

    static final boolean ques2D12(){
        return 
        (e.getHP() >= 2 && AM_RNGenerator.newRNG(800)) 
        || (e.getAmmoLeft() > 6 && ActionsLib.searchActions(holdingID).getLegit() < 0);
    }
    static final void ans2D12(Question q){
        if(q.test()){
            end(holdingID, null);
        }else{
            nextDecision(D_4, holdingID);
        }
    }
}

