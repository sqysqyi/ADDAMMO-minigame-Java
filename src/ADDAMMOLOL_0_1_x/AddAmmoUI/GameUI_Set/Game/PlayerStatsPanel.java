package ADDAMMOLOL_0_1_x.AddAmmoUI.GameUI_Set.Game;

import javax.swing.JLabel;
import javax.swing.JPanel;

import ADDAMMOLOL_0_1_x.AddAmmoMain.Start;
import ADDAMMOLOL_0_1_x.AddAmmoMain.Players.*;
import ADDAMMOLOL_0_1_x.AddAmmoUI.Updatable;
import ADDAMMOLOL_0_1_x.AddAmmoUI.initUI;

import java.awt.Color;
import java.awt.GridLayout;

public final class PlayerStatsPanel extends JPanel implements initUI,Updatable{
    JPanel playerLauncherAndMine, enemyLauncherAndMine;
    JLabel playerStatsTitleLabel,enemyStatsTitleLabel;
    JLabel playerHP_Label,enemyHP_Label;
    JLabel playerAmmoleftLabel,enemyAmmoLeftLabel ;
    JLabel playerSelectedActionLabel,enemySelectedActionLabel;
    JLabel p_launcherSignal,e_launcherSignal;
    JLabel p_mineSignal,e_mineSignal;

    public PlayerStatsPanel(){
        initualizeUI();
    }


    @Override
    public void initualizeUI(){
        playerStatsTitleLabel = new JLabel("Your stats");
            playerStatsTitleLabel.setOpaque(false);
            playerStatsTitleLabel.setBackground(Color.DARK_GRAY);
            playerStatsTitleLabel.setForeground(Color.WHITE);
            //playerStatsTitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // 水平居中
            //playerStatsTitleLabel.setHorizontalAlignment(SwingConstants.CENTER); // 文本居中
        
        enemyStatsTitleLabel = new JLabel("Enemy Stats");
            enemyStatsTitleLabel.setForeground(Color.WHITE);
            
        playerHP_Label = new JLabel("HP: " +Start.setMaxHP );
            playerHP_Label.setForeground(Color.WHITE);
            //playerHP_Label.setAlignmentX(Component.CENTER_ALIGNMENT);
            //playerHP_Label.setHorizontalAlignment(SwingConstants.CENTER);        

        enemyHP_Label = new JLabel("HP: " + Start.setMaxHP);
            enemyHP_Label.setForeground(Color.WHITE);
              
        playerAmmoleftLabel = new JLabel("Ammo: " + Start.setDefaultAmmo );
            playerAmmoleftLabel.setForeground(Color.WHITE);
            //playerAmmoleftLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            //playerAmmoleftLabel.setHorizontalAlignment(SwingConstants.CENTER);

        enemyAmmoLeftLabel = new JLabel("Ammo: " + Start.setDefaultAmmo);
            enemyAmmoLeftLabel.setForeground(Color.WHITE);

        playerSelectedActionLabel = new JLabel("Action: It's your turn"  );
            playerSelectedActionLabel.setForeground(Color.WHITE);

        enemySelectedActionLabel = new JLabel("Action: Unknown yet");
            enemySelectedActionLabel.setForeground(Color.WHITE);

        playerLauncherAndMine = new JPanel();
            playerLauncherAndMine.setBackground(Color.BLACK);
            playerLauncherAndMine.setLayout(new GridLayout(1, 2));
            p_launcherSignal = new JLabel("<L>");
            p_mineSignal = new JLabel("<M>");

            p_launcherSignal.setForeground(Color.DARK_GRAY);
            p_mineSignal.setForeground(Color.DARK_GRAY);

            p_launcherSignal.setBackground(Color.BLACK);
            p_mineSignal.setBackground(Color.BLACK);

            playerLauncherAndMine.add(p_launcherSignal);
            playerLauncherAndMine.add(p_mineSignal);

        enemyLauncherAndMine = new JPanel();
            enemyLauncherAndMine.setBackground(Color.BLACK);
            enemyLauncherAndMine.setLayout(new GridLayout(1, 2));
        
            e_launcherSignal = new JLabel("<L>");
            e_mineSignal = new JLabel("<M>");

            e_launcherSignal.setForeground(Color.DARK_GRAY);
            e_mineSignal.setForeground(Color.DARK_GRAY);

            e_launcherSignal.setBackground(Color.BLACK);
            e_mineSignal.setBackground(Color.BLACK);

            enemyLauncherAndMine.add(e_launcherSignal);
            enemyLauncherAndMine.add(e_mineSignal);
        
        add(playerStatsTitleLabel);
        add(playerLauncherAndMine);
        add(enemyStatsTitleLabel);
        add(enemyLauncherAndMine);
        
        add(playerHP_Label);
        add(new JLabel());
        add(enemyHP_Label); 
        add(new JLabel());
        
        add(playerAmmoleftLabel);
        add(new JLabel());
        add(enemyAmmoLeftLabel);
        add(new JLabel());
        
        add(playerSelectedActionLabel);
        add(new JLabel());
        add(enemySelectedActionLabel);
        add(new JLabel());
    }
    
    @Override 
    public void update(Players player, Players enemy){
        playerHP_Label.setText("HP: " + player.getHP());
        if(player.getHP() <= 1){
            playerHP_Label.setForeground(Color.RED);
        }else{
            playerHP_Label.setForeground(Color.WHITE);
        }
        enemyHP_Label.setText("HP: " + enemy.getHP());
        if(enemy.getHP() <= 1){
            enemyHP_Label.setForeground(Color.RED);
        }else{
            enemyHP_Label.setForeground(Color.WHITE);
        }

        playerAmmoleftLabel.setText("Ammo: " + player.getAmmoLeft());
        enemyAmmoLeftLabel.setText("Ammo: " + enemy.getAmmoLeft());

        playerSelectedActionLabel.setText("Action: " + player.getPlayerActions().getActionNameString());
        enemySelectedActionLabel.setText("Action: " + enemy.getPlayerActions().getActionNameString());

        if(player.getPlayerStats().isMineReady()){
            p_mineSignal.setForeground(Color.RED);
        }else{
            p_mineSignal.setForeground(Color.DARK_GRAY);
        }
        if(enemy.getPlayerStats().isMineReady()){
            e_mineSignal.setForeground(Color.RED);
        }else{
            e_mineSignal.setForeground(Color.DARK_GRAY);
        }

        if(player.getPlayerStats().isMissileSettled()){
            p_launcherSignal.setForeground(Color.GREEN);
        }else{
            p_launcherSignal.setForeground(Color.DARK_GRAY);
        }
        if(enemy.getPlayerStats().isMissileSettled()){
            e_launcherSignal.setForeground(Color.GREEN);
        }else{
            e_launcherSignal.setForeground(Color.DARK_GRAY);
        }
    }
}
