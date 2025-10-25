package ADDAMMOLOL_0_1_x.AddAmmoUI.GameUI_Set.Game;

import javax.swing.JLabel;
import javax.swing.JPanel;

import ADDAMMOLOL_0_1_x.AddAmmoMain.Game;
import ADDAMMOLOL_0_1_x.AddAmmoMain.Start;
import ADDAMMOLOL_0_1_x.AddAmmoMain.Players.*;
import ADDAMMOLOL_0_1_x.AddAmmoUI.Updatable;
import ADDAMMOLOL_0_1_x.AddAmmoUI.initUI;

import java.awt.Color;

public final class PlayerStatsPanel extends JPanel implements initUI,Updatable{
    JLabel playerStatsTitleLabel,enemyStatsTitleLabel;
    JLabel playerHP_Label,enemyHP_Label;
    JLabel playerAmmoleftLabel,enemyAmmoLeftLabel ;
    JLabel playerSelectedActionLabel,enemySelectedActionLabel;

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
        
        add(playerStatsTitleLabel);
        add(new JLabel());
        add(enemyStatsTitleLabel);
        add(new JLabel());
        
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
    }
}
