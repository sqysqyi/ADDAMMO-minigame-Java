package ADDAMMOLOL_0_1_x.AddAmmoUI.GameUI_Set;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import ADDAMMOLOL_0_1_x.AddAmmoMain.Start;
import ADDAMMOLOL_0_1_x.AddAmmoUI.initUI;

public final class GameConfigPanel extends JPanel implements initUI {

    private static int gcpWidth = 400, gcpHeight = 200;

    private JButton confirmButton = new JButton();
    private int definedMaxHP = Start.setMaxHP == 0 ? 3 : Start.setMaxHP;
    private int definedDefaultAmmo = Start.setDefaultAmmo == 0 ? 1 : Start.setDefaultAmmo;
    private ConfigConfirmListener ccListener;
    
    public GameConfigPanel(){
        
        initualizeUI();
    
    }
    public void initualizeUI(){

        setBackground(Color.GRAY);
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(gcpWidth, gcpHeight));
        setBorder(new LineBorder(Color.BLUE, 3));

        JPanel titlePanel = new JPanel();
        JPanel textPanel = new JPanel();
        JPanel buttonPanel = new JPanel();

        titlePanel.setPreferredSize(new Dimension(gcpWidth, 30));
        titlePanel.setBackground(Color.GRAY);
        titlePanel.setForeground(Color.WHITE);
        titlePanel.add(new JLabel(" (...) Config  "));
        add(titlePanel, BorderLayout.NORTH);

        add(new JPanel(),BorderLayout.WEST);
        add(new JPanel(),BorderLayout.EAST);

        textPanel.setPreferredSize(new Dimension(gcpWidth/2,130));
        textPanel.setLayout(new GridLayout(3,2));
            textPanel.add(new JLabel("Set Max HP: "));
                JTextField maxHPField = new JTextField(definedMaxHP + "");
            textPanel.add(maxHPField);

            textPanel.add(new JLabel("Set Default Ammo: "));
                JTextField defaultAmmoField = new JTextField(definedDefaultAmmo+"");
            textPanel.add(defaultAmmoField);

            textPanel.add(new JLabel("Set Max Players:"));
                JTextField maxPlayerField = new JTextField("2");
                maxPlayerField.setEditable(false);
            textPanel.add(maxPlayerField);
        add(textPanel, BorderLayout.CENTER);

        buttonPanel.setPreferredSize(new Dimension(gcpWidth,40));
        buttonPanel.setLayout(new GridLayout(1,3, 0, 10));
            buttonPanel.add(new JLabel());
                confirmButton.setText("Confirm");
            buttonPanel.add(confirmButton);
            buttonPanel.add(new JLabel());
        add(buttonPanel, BorderLayout.SOUTH);

        confirmButton.addActionListener( e ->{
            try{
                int HP = Integer.parseInt(maxHPField.getText().trim());
                int ammo = Integer.parseInt(defaultAmmoField.getText().trim());

                if(HP <= 0) throw new IllegalArgumentException();
                if(ammo <= 0) throw new IllegalArgumentException();

                if(ccListener != null) ccListener.onConfirmed();

                Start.setMaxHP = HP;
                Start.setDefaultAmmo = ammo;
                
            }
            catch(NumberFormatException nfe){
                JOptionPane.showMessageDialog(
                    this,
                    "Invalid input, only numders allowed !",
                    "Error!", 
                    JOptionPane.ERROR_MESSAGE);
            }
            catch(IllegalArgumentException iae){
                JOptionPane.showMessageDialog(
                    this,
                    "Found illegal numbers.\n Both HP and ammo should be positive...",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    public interface ConfigConfirmListener {
        
        void onConfirmed();
        //not recommand for a lambda description here
    }

    public void addConfigConfirmListener(ConfigConfirmListener listener){
        this.ccListener = listener;
    }
}


