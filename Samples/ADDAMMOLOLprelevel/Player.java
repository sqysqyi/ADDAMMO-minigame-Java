package ADDAMMOLOLprelevel;

import java.util.*;

public class Player{
    private int HP;
    private int ammo;
    private int damage;
    private int defence;
    private int danger;
    private int[] action;
    private boolean isMissileSettled;
    private String nameTag;
    

    public Player(int HP, int ammo , int damage, int defence, int danger, int[] action, boolean isMissileSettled,String nameTag){
        this.HP = HP;
        this.ammo = ammo;
        this.nameTag = nameTag;
        this.damage = damage;
        this.defence = defence;
        this.danger = danger;
        this.isMissileSettled = isMissileSettled;
        this.action = action;
    }
    
    public void setHP(int HP) {
        this.HP = HP;
    }
    public void setAmmo(int ammo){
        this.ammo = ammo;
    }
    public void setNameTag(String nameTag){
        this.nameTag = nameTag;
    }
    public void setDamage(int damage){
        this.damage = damage;
    }
    public void setDefence(int defence){
        this.defence = defence;
    }
    public void setDanger(int danger){
        this.danger = danger;
    }
    public void setMisslieSettle(boolean isMissileSettled){
        this.isMissileSettled = isMissileSettled;
    }
    public void setAction(int[] action){
        this.action = action;
    } 

    public int getHP() {
        return HP;
    }
    public int getAmmo() {
        return ammo;
    }
    public String getNameTag(){
        return nameTag;
    }
    public int getDamage(){
        return damage;
    }
    public int getDefence(){
        return defence;
    }
    public int getDanger(){
        return danger;
    }
    public boolean getMisslieSettle(){
        return isMissileSettled;
    }
    public int[] getAction(){
        return action;
    }

    public void reset3D(){
        this.setDamage(0);
        this.setDefence(0);
        this.setDanger(0);
    }
    
    
    public int[] cost_and_damage(int type, int index){
        int[][] ammoCost ={//对应的子弹消耗      
            {-1 ,0  ,1  ,2  ,1 },
            {1  ,0  ,2  ,1  ,-1 },
            {1  ,2  ,3  ,2  ,1  }
        };
        int[][] directDamage = {//直接伤害
            {0  ,0  ,0  ,0  ,0  },
            {0  ,1  ,0  ,2  ,0  },
            {1  ,2  ,3  ,2  ,0  }
        };
        int[] result_1 = {ammoCost[type][index],directDamage[type][index]};
        return result_1;
    }

    public int[] isDefence_and_isDanger(int type, int index){

        int[][] defence = {
            {0, 1,  1024,   1,  0 },
            {0, 1,  1   ,   2,  0 },
            {0, 0,  1   ,   1,  1 }
        };
        int[][] danger = {
            {0, 0 ,  0  ,  1 ,  0 },
            {1, -1,  2  ,  -2,  0 },
            {1, 2 ,  3  ,  2 ,  0 }
        };
        int[] result_2 = {defence[type][index],danger[type][index]};
        return result_2;
    }
    public void printActionTable(){
        System.out.println("====================================================================================");
        System.out.println("在下表中选择你要出的招：");
        System.out.println("action\\type    1           2           3               4               5           ");
        System.out.println("1:              加子弹(0)   盾(0)       全面防御(1)     建立导弹架(2)     药(1)       ");
        System.out.println("2:              小偷(1)     警察(0)     侠盗(2)         武警(1)         （占位）加子弹(0)    ");
        System.out.println("3:              手枪(1)     双枪(2)     发射导弹(3)     掏蛋(2)          防空(1)    ");
        System.out.println("现在你有 "+getAmmo()+" 颗子弹，并且剩余 "+ getHP() +" HP.");
        
        System.out.printf(">");
    }
    
    public void PlayersAction(Player player){
        if(this.getNameTag().equals("enemy") ){
            //电脑行为

            while(true){
                int type; int index;
                
                if (player.getNameTag().equals("test")){
                    type = 0; index = 4;
                    this.setAmmo(1);//调试模式
                }else{
                    this.reset3D();
                    Random rand = new Random();
                    type = rand.nextInt(3);
                    index = rand.nextInt(5);
                    int[] AIresult = trickyAI(type, index, player);
                    type = AIresult[0];
                    index = AIresult[1];
                }
                int[] result_2 = {type, index};

                //根据玩家和自己的状态，适当调整动作表的内容：
                

                String[][] actions ={//自动随机选择动作
                    {"add-ammo" ,"shield"   ,"all-defense"  ,"set_misslie"  ,"heal"    },
                    {"thief"    ,"police"   ,"rouge"        ,"armed-police" ,"add-ammo"},
                    {"gun"      ,"duo-gun"  ,"missile"      ,"COCK-lover"   ,"Anti-air"}
                };

                int result[] = cost_and_damage(type, index);
                //int result_1[] = isDefence_and_isDanger(type, index);

                if(this.getAmmo() - result[0] < 0){
                    //System.out.println(this.getNameTag()+"\t没有足够弹药 for "+actions[type][index]+" 已重试...");
                    continue;
                }else{
                    this.setAmmo(this.getAmmo() - result[0]);
                    System.out.println(this.getNameTag()+"\t花费 "+ cost_and_damage(type, index)[0]+" 颗子弹 for "+actions[type][index]);

                    this.setDamage(cost_and_damage(type, index)[1]);
                    this.setDefence(isDefence_and_isDanger(type, index)[0]);
                    this.setDanger(isDefence_and_isDanger(type, index)[1]);
                    
                    this.setAction(result_2);
                    break;
                }

                //插入AI处

    
            }
            
        }else{
            //玩家行为
            printActionTable();              
            String[][] actions ={//选择动作
                {"add-ammo" ,"shield"   ,"all-defense"  ,"set_misslie"  ,"heal"    },
                {"thief"    ,"police"   ,"rouge"        ,"armed-police" ,"add-ammo"},
                {"gun"      ,"duo-gun"  ,"missile"      ,"COCK-lover"   ,"Anti-air"}
            };

            @SuppressWarnings("resource")
            Scanner input = new Scanner(System.in);//暂时 无法关闭input。。。
            while(true){
                reset3D();
                int type = input.nextInt()-1;
                int index = input.nextInt()-1;
                if(type > 3 || index > 5){
                    System.out.println("非法输入，请重新输入");
                    return;
                }
                System.out.println("test");
                int[] result = cost_and_damage(type, index);
                //int[] result_1 = isDefence_and_isDanger(type, index);
  
                if(this.getAmmo() - result[0] < 0){
                
                    System.out.println(this.getNameTag()+"\t没有足够弹药 for "+actions[type][index]+"\n>" );

            
                }else{
                    this.setAmmo(this.getAmmo() - result[0]) ;
                    System.out.println(this.getNameTag()+"\t花费 "+result[0]+" 颗子弹 for "+actions[type][index]);
                    this.setDamage(cost_and_damage(type, index)[1]);
                    this.setDefence(isDefence_and_isDanger(type, index)[0]);
                    this.setDanger(isDefence_and_isDanger(type, index)[1]);
                    int[] result_2 = {type ,index};

                    this.setAction(result_2);
                    break;
                    
                }
            } 
        } 
    }
        


    public void battle(Player player){
        //离谱的机制判定 QAQ
        

        //int[] thisPlayersAction = this.PlayersAction();
        if (this.getAction() == player.getAction()){
            return;
        }

        if (this.getDanger() < 0){//警察的行为
            if (player.getDanger() > -this.getDanger() || player.getDanger() == 0 || player.getDanger() < 0){
                //对面更狠，或者是自己人，或者老实人，啥都干不了
            }else{
                player.setHP(player.getHP()-this.getDamage());
                player.reset3D();
                //自己更狠，对面丧失全部能力
            }
            if (player.getDanger() == 0){
                this.reset3D();//既然对面没有危险性，警察就自废武功
            }
        }else if(this.getDanger() == 0){//不是警察，接着判断
            
            if(this.action[0] == 0 && this.action[1] == 4){//特指回血
                if(player.getDamage() != 0 && player.getDanger() > 0){
                    player.setDamage(player.getDamage()*2);//喝药被打造成二倍伤害
                }else{
                    this.setHP(this.getHP()+1);
                }
            }
            if((this.action[0] == 2 && this.action[1] == 4) && (player.action[0] == 2 && player.action[1] == 2)){
                //特指防空炮对上导弹
                player.reset3D();
                System.out.println("导弹被击落！");//无论打得出来，所有3D都失效，即打下来了
            }
        }
        else{//danger > 0的危险分子

            boolean isMissileSet = this.action[0] == 0 && this.action[1] == 3;
            if(this.getDamage() == 0 && !isMissileSet ){//特指小偷类,例外一个导弹架
                if(( player.getDanger() < 0 && this.getDanger() > 0-player.getDanger() )|| player.getDanger() == 0){//对面是不如你的警察或者平民
                    this.setAmmo(this.getAmmo()+player.getAmmo());//所有子弹偷到手
                    player.setAmmo(0);
                    if(player.action[0] == 0 && player.action[1] == 4){//特指喝药
                        this.setHP(this.getHP()+1);
                        player.setHP(player.getHP()-1);//顺便偷走药
                    }
                
                }else if(player.getDanger() < 0 && this.getDanger() <= 0-player.getDanger()){//对面比你叼，要被制裁
                    //do nothing...
                
                }
            }else if(isMissileSet){//特指导弹架
                if(player.getDamage() > 0){
                    this.isMissileSettled = false;
                    return;//只要对面能对你造成伤害，或者已经有了导弹架，导弹架就架不成
                }else if(player.getDamage() == 0 && this.isMissileSettled == false){
                    this.isMissileSettled = true;
                }    
            }
            
            if(this.action[0] == 2 && this.action[1] == 2){//特指打导弹
                if(!this.isMissileSettled){//没有导弹架
                    this.reset3D();//无害化处理
                    System.out.println(this.getNameTag()+ "在没有导弹发射架的情况下试图发射导弹，失败。");
                }else{
                    System.out.println(this.getNameTag()+ "的导弹已发射！导弹架已损毁。");
                    this.setMisslieSettle( isMissileSettled = false);
                    //null
                    //原来咋样就咋样
                }
            }  
        }
    }

    public int[] trickyAI(int type, int index, Player player){
        int [] result = {type, index};
        if(this.getAmmo() < 2){
            switch (index) {
                case 2:
                    result[0] = 0; result[1] = 0;
                    break;
                case 3:    
                    result[0] = 1; result[1] = 0;
                    break;
                case 4: 
                    result[0] = 1; result[1] = 1;
                    break;
                default:
                    result[0] = type; result[1] = index;
            }
            
        }
        else if(this.getAmmo() > 3){
            if( index == 0 ){
                result[1] = 3; 
            }
        
        }
        
        if (this.isMissileSettled){
            if(type == 0 && index == 3){
                result[0] = 2; result[1] = 2;
            }
        }
        if (player.isMissileSettled){
            if (index == 4){
                result[0] = 2;

            }else if(index == 1){
                result[0] = 0; result[1] = 2;
            }
        }
        return result;
    }
    
}

    /*********************************
     * xxx      （伤害 防御   危险度）
     *加子弹    （0，   0，     0   ）  
     *盾        （0，   1，     0   ）
     *全防      （0，   1024，  0   ）
     *导弹架    （0，   1，     1   ）
     *回血      （0，   0，     0   ）
     *
     *小偷      （0，   0，     1   ）
     *警察      （1，   1，     -1  ）
     *大盗      （0，   1，     2   ）
     *武警      （2，   2，     -2  ）
     *加子弹    （0，   0，     0   ）
     *
     * 一枪     （1，   0，     1   ）
     * 双枪     （2，   0，     2   ）
     * 导弹     （3，   1，     3   ）
     * 掏蛋     （2，   1，     1   ）
     * 防空     （0，   1，     0   ）
     * ******************************* */        
    //Coming soon lmao.
    //public void 
    

