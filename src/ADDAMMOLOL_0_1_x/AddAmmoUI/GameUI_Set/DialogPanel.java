package ADDAMMOLOL_0_1_x.AddAmmoUI.GameUI_Set;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import ADDAMMOLOL_0_1_x.AddAmmoMain.Game;

public final class DialogPanel extends JPanel{

    JButton cancelButton = new JButton(),confirmButton = new JButton();
    private ButtonSelectingListener buttonSelectingListener;
    
    private final int dialogWidth = 400;
    private final int dialogHeight = 200;  
    
    public static final int INFO_DIALOG = 0;
    public static final int WARNING_DIALOG = 1;
    
    public DialogPanel(){
        this(0,"message");

    }
    public DialogPanel(String message){
        this(0,message);
    }
    public DialogPanel(int type, String message){
        
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(dialogWidth,dialogHeight));
        setBorder(new LineBorder(Color.BLUE, 3));

        JPanel titlePanel = new JPanel();
        JPanel textPanel = new JPanel();
        JPanel buttonPanel = new JPanel();

        titlePanel.setPreferredSize(new Dimension(dialogWidth, 30));
        switch(type){
            case INFO_DIALOG:{
                titlePanel.setBackground(Color.GRAY);
                titlePanel.setForeground(Color.WHITE);
                titlePanel.add(new JLabel(" (?) Info  "));
                break;
            }
            case WARNING_DIALOG:{
                titlePanel.setBackground(Color.RED);
                titlePanel.setForeground(Color.WHITE);
                titlePanel.add(new JLabel("/!\\ Warning  "));
                break;
            }
        }
        add(titlePanel,BorderLayout.NORTH);

        textPanel.setPreferredSize(new Dimension(dialogWidth, 130));
        textPanel.setBackground(Color.WHITE);
        textPanel.add(new JLabel(message));
        add(textPanel, BorderLayout.CENTER);

        buttonPanel.setPreferredSize(new Dimension(dialogWidth, 40));
        buttonPanel.setLayout(new GridLayout(1, 4, 0, 10));
            buttonPanel.add(new JLabel());
                confirmButton.setText("Confirm");
            buttonPanel.add(confirmButton);
                cancelButton.setText("Cancel");
            buttonPanel.add(cancelButton);
            buttonPanel.add(new JLabel());
        add(buttonPanel, BorderLayout.SOUTH);

        confirmButton.addActionListener(e -> {
            //暂时还没想好，先不写
            //onConfirmClicked();
        });
        cancelButton.addActionListener(e ->{
            //返回之前仍在进行的游戏
            onCancelSelected();
        });
    }
    private void onCancelSelected(){
        if(buttonSelectingListener != null){
            buttonSelectingListener.onCancelClicked();
        }
        for(ActionListener listener : cancelButton.getActionListeners()){
            cancelButton.removeActionListener(listener);
        }
        for(ActionListener listener : confirmButton.getActionListeners()){
            confirmButton.removeActionListener(listener);
        }
    }

    public interface ButtonSelectingListener {
        
        default void onConfirmClicked(){};
        void onCancelClicked();
    }

    public void addButtonSelectingListener(ButtonSelectingListener listener) {
        this.buttonSelectingListener = listener;
    }




}
