package ADDAMMOLOL_0_1_x.AddAmmoMain.Players;

import java.util.ArrayList;
import java.util.Random;

import ADDAMMOLOL_0_1_x.AddAmmoMain.Game;
import ADDAMMOLOL_0_1_x.AddAmmoMain.Start;
import ADDAMMOLOL_0_1_x.AddAmmoMain.Actions.Actions;
import ADDAMMOLOL_0_1_x.AddAmmoMain.Actions.ActionStats;

public class Enemy extends Players {
    private int selectedActionID;
    private ArrayList<Integer> actionsIdSelectionPool = new ArrayList<Integer>();

    //private final int group_num = 3;
    //private final int type_num = 1; 
    
    
    public Enemy(){
        super();
        //super(HP, ammoLeft, dangerous, legit, isSteal, isMissileSettled, playerNameString);
    }
    
    public Enemy(int HP, int ammoLeft,PlayerStats playerActionStats, ActionStats enemyGameStats, Actions enemyActions, String playerNameString) {
        super(HP, ammoLeft,playerActionStats, enemyGameStats, enemyActions, playerNameString);
        //TODO Auto-generated constructor stub
    }

    @Override
    public int actionsSelecting(int opponentHP_left, int opponentAmmoLeft, ActionStats opponentGameStats){
        
        actionsIdSelectionPool.clear();
        //actionsIdSelectionPool.trimToSize();;
        
        Random rand = new Random();
        //actionsIdSelectionPool.add(101);
        //首要任务，见玩家的状态行事

        //action pool 常驻动作：
        actionsIdSelectionPool.add(101);//add ammo
        actionsIdSelectionPool.add(201);//shoot
        //actionsIdSelectionPool.add(401);//steal
        //actionsIdSelectionPool.add(501);//police
        
        if(opponentAmmoLeft == 0){//玩家没子弹
            actionsIdSelectionPool.add(201);//愣着干啥？开枪！
            actionsIdSelectionPool.add(202);//多开两枪
            actionsIdSelectionPool.add(601);//最好开一炮
            
            
            if(this.getHP() < Start.setMaxHP){
                actionsIdSelectionPool.add(701);//试图回血
            }
        }else{//玩家有子弹！

            actionsIdSelectionPool.add(301);//准备防御
            actionsIdSelectionPool.add(201);
            actionsIdSelectionPool.add(601);
            actionsIdSelectionPool.add(401);//steal
            actionsIdSelectionPool.add(501);//police
            
            if(this.getAmmoLeft() < 3){
                actionsIdSelectionPool.add(101);//子弹少了，记得加点
            }
            int tempIndex = opponentAmmoLeft > 4?4:opponentAmmoLeft;
            switch (tempIndex) {
                case 4:
                    actionsIdSelectionPool.add(402);
                case 3:
                    actionsIdSelectionPool.add(402);
                case 2:
                    actionsIdSelectionPool.add(502);
                break;
            }
        }

        if(this.getAmmoLeft() > 1 && opponentAmmoLeft > 0){
            actionsIdSelectionPool.add(501);
            actionsIdSelectionPool.add(502);
            if(opponentAmmoLeft > 2){
                actionsIdSelectionPool.add(402);//子弹挺多啊，给你好好偷一下
            }
        }
        if(this.getAmmoLeft() > 0 && opponentAmmoLeft > 1){
            actionsIdSelectionPool.add(401);
            actionsIdSelectionPool.add(402);
        }
        if(this.getHP() < 2){
            actionsIdSelectionPool.add(701);
            if(opponentAmmoLeft == 0){
                actionsIdSelectionPool.add(701);
            }
        } 
        
        int actionsPoolIndex,preSelectedActionID;

        System.out.printf("action selectinng pool comtains: ");        
        for(int i = 0 ; i < actionsIdSelectionPool.size(); i++){
            System.out.printf(" "+actionsIdSelectionPool.get(i));
        }
        System.out.println(" The action pool size is: "+actionsIdSelectionPool.size());

        
        while(true){
            actionsPoolIndex = rand.nextInt(actionsIdSelectionPool.size());

            preSelectedActionID = actionsIdSelectionPool.remove(actionsPoolIndex);

            if(this.selectActions(preSelectedActionID).getAmmoCost() > this.getAmmoLeft() ){
                System.out.printf("*");//retry
                continue;
            }else{
                selectedActionID = preSelectedActionID;
                break;
            }
        }
        return selectedActionID;
        //return 701;//test part
    }

    public int actionsSelecting(int useless){
        return 0;
    }

    
}