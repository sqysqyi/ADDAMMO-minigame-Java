package ADDAMMOLOLprelevel;

import java.util.*;

public class testSample {
    public static void main(String[] args) {
        System.out.println("This is a demo.");
        Scanner scan = new Scanner(System.in);

        while(true){//starting menu
            
            System.out.println("=============================================================================================");
            System.out.println("加子弹小游戏");
            System.out.printf("选择模式：\n"+
                              "(输入1以进行人机对抗; 输入2以了解游玩须知和规则。)");
                    
            int select = scan.nextInt();
            
            //有待解决非法输入不阻断的问题
            if (select == 1){
                 
                break;

            }
            else {
                System.out.println("抱歉，该选项暂不可用 :( \n");
                //scan.close();
            }
            
            //game begin
            //-----------------------------------------------------------------------------------------------
        }
        System.out.println("OK");
        System.out.println("接下来进行基本设置");

        String yourName ;
        do {System.out.printf("请输入你的昵称（不可为enemy）：");
            yourName = scan.next();
            
        } while ( yourName.equals("enemy"));
        System.out.println("");



        
        System.out.printf("请输入玩家初始血量：");
        
        
        int[] initialization = {0,0};

        Player player = new Player(10,2,0,0,0,initialization,false,yourName);
        Player enemy = new Player(10,2,0,0,0,initialization,false,"enemy");
        //scan.close();
        int tempInput = scan.nextInt();
        if( tempInput<= 0){
            player.setHP(5);
            enemy.setHP(5);
            System.out.println("非法输入，已自动设置为默认值5.");
        }
        else{
            player.setHP(tempInput);
            enemy.setHP(tempInput);
        }
        
        System.out.println();
       
        
        while (true){ 
            //Player TempPlayer = new Player(5,0,0,0,0,initialization,false,yourName);
            //Player TempEnemy = new Player(5,0,0,0,0,initialization,false,"enemy");//初始化
              
            player.PlayersAction(enemy);
            //System.out.println("---------------------------------------------------------------------------------------------");
            //TempEnemy = enemy;
            enemy.PlayersAction(player);
            //TempPlayer = player;



            //判断玩家与电脑的结果
            player.battle(enemy);
            enemy.battle(player);
            
            if(player.getDefence() < enemy.getDamage()){
                player.setHP(player.getHP()-enemy.getDamage());

            }
            if(enemy.getDefence() < player.getDamage()){
                enemy.setHP(enemy.getHP()-player.getDamage());

            }
            
            System.out.println("=============================================================================================");

            System.out.printf("\n\n");
            System.out.println("现在对手有 "+enemy.getAmmo()+ "颗子弹，并且剩余 "+enemy.getHP()+ " HP");
            
            /*********************************
             * xxx      （伤害 防御   危险度）
             *加子弹    （0，   0，     0   ）  
             *盾        （0，   1，     0   ）
             *全防      （0，   1024，  0   ）
             *导弹架    （0，   1，     0   ）
             *回血      （0，   0，     0   ）
             *
             *小偷      （0，   0，     1   ）
             *警察      （1，   1，     -1  ）
             *大盗      （0，   1，     2   ）
             *武警      （2，   2，     -2  ）
             *加子弹    （0，   0，     0   ）
             *
             * 一枪     （1，   0，     1   ）
             * 双枪     （2，   0，     1   ）
             * 导弹     （3，   1，     2   ）
             * 掏蛋     （2，   1，     3   ）
             * 防空     （0，   0，     0   ）
             * ******************************* */        

            if(player.getHP() <= 0){
                System.out.println("你被一个AI都算不上的人机打败了，菜鸡！！:P");
                break;
            }
            
            if(enemy.getHP() <= 0){
                System.out.println("电脑已被击败 GG!");
                break;
            }
        }

        scan.close();
        
    }
}
