package ADDAMMOLOL_0_1_x.AddAmmoUtil;

import java.util.concurrent.atomic.AtomicMarkableReference;

import ADDAMMOLOL_0_1_x.AddAmmoMain.Actions.Actions;
import ADDAMMOLOL_0_1_x.AddAmmoMain.Actions.ActionsLib;
import ADDAMMOLOL_0_1_x.AddAmmoMain.Players.Enemy;
import ADDAMMOLOL_0_1_x.AddAmmoMain.Players.Player;
import ADDAMMOLOL_0_1_x.AddAmmoMain.Players.Players;

// Question Answer and Decision
/**
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
 * Q: 6-1/Will receive too much damage?
 *      A;  Y -> (do Absolute_def Action) -> (end of thinking)
 *          N -> (do defense Actions) -> (end of thinking)
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
    D_2("Have enough ammo?", AM_Decision::ques2D2, AM_Decision::ans2D2),
    D_3("Add ammo?", AM_Decision::ques2D3, AM_Decision::ans2D3),
    D_3_1("Is safe now?",AM_Decision::ques2D3_1,AM_Decision::ans2D3_1),
    D_3_2("Will steal?",AM_Decision::ques2D3_2,AM_Decision::ans2D3_2),
    D_3_2_1("Will be arrested?",AM_Decision::ques2D3_2_1,AM_Decision::ans2D3_2_1),
    D_3_2_2("Bet?<Will be arrested?>",AM_Decision::ques2D3_2_2,AM_Decision::ans2D3_2_2),
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
    
    
    AM_Decision( String discription, Question q, Answer a){
        this.decisionDiscription = discription;
        this.question = q;
        this.answer = a;
        //this.holdingID = holding;

    }

    public static void start(int ambitionID, Enemy me, Player opponent){
        e = me;
        p = opponent;
        holdingID = ambitionID;
        nextDecision(D_1, ambitionID);
        
    }

    private static void nextDecision(AM_Decision decision, int currentID){
        decision.answer.doWith( decision.question );
    }

    public static Actions end(){
        //暂未实现
        return null;
    }

    public interface Question {
        boolean test();
    }
    public interface Answer {
        void doWith(Question q);
    }

    //****************************************************************************************** */
    //All questions here below
    static final boolean ques2D1(){
        return ActionsLib.searchActions(e.getAmbitious()) != null;
    } 
    static final void ans2D1(Question q){
        if(q.test()){
            nextDecision(D_1, holdingID);
        }else{
            e.setAmbitious(Enemy.createAmbitious());
            nextDecision(D_2, holdingID);
        }
    }
    
    static final boolean ques2D2(){
        return ActionsLib.searchActions(e.getAmbitious()).getAmmoCost() <= e.getAmmoLeft();
    }
    static final void ans2D2(Question q){
        if(q.test()){
            nextDecision(D_10, holdingID);
        }else{
            nextDecision(D_3, holdingID);
        }
    }
    
        static final boolean ques2D3(){
        return false;
    }
    static final void ans2D3(Question q){
        if(q.test()){
            nextDecision(D_3_1, holdingID);
        }else{
            nextDecision(D_3_2, holdingID);
        }
    }

    static final boolean ques2D3_1(){
        return false;
    }
    static final void ans2D3_1(Question q){
        if(q.test()){
            end();
        }else{
            nextDecision(D_4, holdingID);
        }
    }

    static final boolean ques2D3_2(){
        return false;
    }
    static final void ans2D3_2(Question q){
        if(q.test()){
            nextDecision(D_3_2_1, holdingID);
        }else{
            nextDecision(D_4, holdingID);
        }
    }

    static final boolean ques2D3_2_1(){
        return false;
    }
    static final void ans2D3_2_1(Question q){
        if(q.test()){
            nextDecision(D_3_2_2, holdingID);
        }else{
            end();
        }
    }

    static final boolean ques2D3_2_2(){
        return false;
    }
    static final void ans2D3_2_2(Question q){
        if(q.test()){
            end();
        }else{
            nextDecision(D_5, holdingID);
        }
    }
    
    static final boolean ques2D4(){
        return false;
    }
    static final void ans2D4(Question q){
        if(q.test()){
            nextDecision(D_4_1, holdingID);
        }else{
            nextDecision(D_5, holdingID);
        }
    }

    static final boolean ques2D4_1(){
        return false;
    }
    static final void ans2D4_1(Question q){
        if(q.test()){
            nextDecision(D_5, holdingID);
        }else{
            nextDecision(D_4_2, holdingID);
        }
    }

    static final boolean ques2D4_2(){
        return false;
    }
    static final void ans2D4_2(Question q){
        if(q.test()){
            nextDecision(D_4_3, holdingID);
        }else{
            nextDecision(D_5, holdingID);
        }
    }

    static final boolean ques2D4_3(){
        return false;
    }
    static final void ans2D4_3(Question q){
        if(q.test()){
            end();
        }else{
            nextDecision(D_5, holdingID);
        }
    }

    static final boolean ques2D5(){
        return false;
    }
    static final void ans2D5(Question q){
        if(q.test()){
            holdingID = 0;//un-finished
            nextDecision(D_2, holdingID);
        }else{
            nextDecision(D_6, holdingID);
        }
    }

    static final boolean ques2D6(){
        return false;
    }
    static final void ans2D6(Question q){
        if(q.test()){
            nextDecision(D_6_1, holdingID);
        }else{
            nextDecision(D_7, holdingID);
        }
    }

    static final boolean ques2D6_1(){
        return false;
    }
    static final void ans2D6_1(Question q){
        if(q.test()){
            end();
        }else{
            end();
        }
    }

    static final boolean ques2D7(){
        return false;
    }
    static final void ans2D7(Question q){
        if(q.test()){
            nextDecision(D_7_1, holdingID);
        }else{
            nextDecision(D_8, holdingID);
        }
    }

    static final boolean ques2D7_1(){
        return false;
    }
    static final void ans2D7_1(Question q){
        if(q.test()){
            nextDecision(D_7_1_1, holdingID);
        }else{
            nextDecision(D_7_2, holdingID);
        }
    }

    static final boolean ques2D7_1_1(){
        return false;
    }
    static final void ans2D7_1_1(Question q){
        if(q.test()){
            nextDecision(D_7_2, holdingID);
        }else{
            end();
        }
    }

    static final boolean ques2D7_2(){
        return false;
    }
    static final void ans2D7_2(Question q){
        if(q.test()){
            nextDecision(D_7_2_1, holdingID);
        }else{
            nextDecision(D_8, holdingID);
        }
    }

    static final boolean ques2D7_2_1(){
        return false;
    }
    static final void ans2D7_2_1(Question q){
        if(q.test()){
            nextDecision(D_8, holdingID);
        }else{
            end();
        }
    }

    static final boolean ques2D8(){
        return false;
    }
    static final void ans2D8(Question q){
        if(q.test()){
            nextDecision(D_8_1, holdingID);
        }else{
            nextDecision(D_9, holdingID);
        }
    }

    static final boolean ques2D8_1(){
        return false;
    }
    static final void ans2D8_1(Question q){
        if(q.test()){
            nextDecision(D_8_2, holdingID);
        }else{
            end();
        }
    }

    static final boolean ques2D8_2(){
        return false;
    }
    static final void ans2D8_2(Question q){
        if(q.test()){
            end();
        }else{
            nextDecision(D_9, holdingID);
        }
    }

    static final boolean ques2D9(){
        return false;
    }
    static final void ans2D9(Question q){
        if(q.test()){
            nextDecision(D_1, holdingID);
        }else{
            end();
        }
    }

    static final boolean ques2D10(){
        return false;
    }
    static final void ans2D10(Question q){
        if(q.test()){
            nextDecision(D_11, holdingID);
        }else{
            nextDecision(D_3, holdingID);
        }
    }

    static final boolean ques2D11(){
        return false;
    }
    static final void ans2D11(Question q){
        if(q.test()){
            end();
        }else{
            nextDecision(D_12, holdingID);
        }
    }

    static final boolean ques2D12(){
        return false;
    }
    static final void ans2D12(Question q){
        if(q.test()){
            end();
        }else{
            nextDecision(D_4, holdingID);
        }
    }
}

