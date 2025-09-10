package ADDAMMOLOL_0_0_1d;

import java.util.Scanner;

public class player extends mainPlayer {
    
    //private Scanner playerInput;
    private int selectedActionID = 101;//default ID,addAmmo;

    public player(){
        super();
        //playerActions = new actions();
    }//空参构造
    public player(int HP, int ammoLeft, playerStats playerActionStats, 
                    gameStats playerGameStats, actions playerActions, String playerNameString) {
        //playerActions = new actions();
        super(HP, ammoLeft, playerActionStats, playerGameStats, playerActions, playerNameString);
    }

/*************************************getter/setter 分割线******************************************* */ 
    

    public boolean checkEnoughAmmo(actions actions){
        boolean checkEnoughAmmoResult = true;
        if(this.getAmmoLeft() - actions.getAmmoCost() < 0){
            checkEnoughAmmoResult = false;
        }
        return checkEnoughAmmoResult;
    }//never used seems :D

    @Override
    public int actionsSelecting(Scanner playerInput){
        while (true) {
            /*************基本输入有效性检查*********************************************** */
            if(!playerInput.hasNextInt()){//是否为int类型
                System.out.println("Invaild input! Enter a integer type number!");
                playerInput.nextLine();
                System.out.printf("ID input: ");
                continue;
            }else{
                selectedActionID = playerInput.nextInt();
            }
            if(selectedActionID < 101){//不合法ID输入
                System.out.println("Invaild input! Try again...");
                System.out.printf("ID input: ");
                continue;
            }
            if(this.selectActions(selectedActionID) == null){//
                System.out.println("Couldn't find this action ID, Try another one pls ...");
                System.out.printf("ID input: ");
                continue;
            }
            /**************具体有效性输入检查********************************************** */
            if(this.selectActions(selectedActionID).getAmmoCost() > this.getAmmoLeft()){
                System.out.println("Couldn't active this action, because you don't have enough ammo...");
                System.out.printf("ID input:");
                continue;
            }//子弹剩余检查
            break;
            
        }

        return selectedActionID;
    }
    public int actionsSelecting(int opponentHP_left, int opponentAmmoLeft, gameStats enemyGameStats){
        //enemy.actionsSelecting(int opponentHP_left, int opponentAmmoLeft, boolean isOpponentSettledMissile);
        System.out.println("Wrong method reference! ");
        return 0;
    }


}
