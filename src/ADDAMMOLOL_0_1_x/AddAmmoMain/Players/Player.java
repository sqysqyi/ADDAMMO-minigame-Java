package ADDAMMOLOL_0_1_x.AddAmmoMain.Players;

import java.util.Scanner;

import ADDAMMOLOL_0_1_x.AddAmmoMain.GameStats;
import ADDAMMOLOL_0_1_x.AddAmmoMain.PlayerStats;
import ADDAMMOLOL_0_1_x.AddAmmoMain.Actions.Actions;

public class Player extends Players {
    
    protected Scanner scan;
    
    public Player(){
        super();
        //playerActions = new actions();
    }//空参构造
    public Player(int HP, int ammoLeft, PlayerStats playerActionStats, 
                    GameStats playerGameStats, Actions playerActions, String playerNameString) {
        //playerActions = new actions();
        super(HP, ammoLeft, playerActionStats, playerGameStats, playerActions, playerNameString);
        
        scan = new Scanner(System.in);
    }

/*************************************getter/setter 分割线******************************************* */ 
    

    public boolean checkEnoughAmmo(Actions actions){
        boolean checkEnoughAmmoResult = true;
        if(this.getAmmoLeft() - actions.getAmmoCost() < 0){
            checkEnoughAmmoResult = false;
        }
        return checkEnoughAmmoResult;
    }//never used seems :D

    @Override
    public int actionsSelecting(int playerInput,int playerAmmoLeft,GameStats playerGameStats){
        int selectedActionID = 101;//default ID,addAmmo;
        while (true) {
            
            /*************基本输入有效性检查*********************************************** */
            if(!scan.hasNextInt()){//是否为int类型
                System.out.println("Invaild input! Enter a integer type number!");
                scan.nextLine();
                System.out.printf("ID input: ");
                continue;
            }else{
                selectedActionID = scan.nextInt();
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

    public static int checkInput(Scanner input){
        return 0;
    }
    
}
