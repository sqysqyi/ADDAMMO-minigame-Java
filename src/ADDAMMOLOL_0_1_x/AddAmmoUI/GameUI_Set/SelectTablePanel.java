package ADDAMMOLOL_0_1_x.AddAmmoUI.GameUI_Set;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import ADDAMMOLOL_0_1_x.AddAmmoMain.Game;
import ADDAMMOLOL_0_1_x.AddAmmoMain.Start;
import ADDAMMOLOL_0_1_x.AddAmmoMain.Actions.ActionsLib;

import ADDAMMOLOL_0_1_x.AddAmmoUI.initUI;

public final class SelectTablePanel extends JPanel implements initUI {
    JTextField playerInputTextField;
    JPanel tablePanel;
    JLabel[][] tableElements = new JLabel[9][4];
    private ActionListener inputListener;
    private TextChangeListener textChangeListener;

    public SelectTablePanel(){
        initualizeUI();
        playerInputTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent E){
                if(inputListener != null && !Game.isGameOver){
                    inputListener.actionPerformed(E);
                }
            }
        });
    }

    @Override
    public void initualizeUI(){
        setLayout(new BorderLayout());
            playerInputTextField = new JTextField(1){
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    if (getText().isEmpty() && !isFocusOwner()) {
                    g.setColor(Color.GRAY);
                        g.drawString("Action code", 5,
                                getHeight() / 2 + g.getFontMetrics().getAscent() / 2 - 2);
                    }
                }
            };
            playerInputTextField.setPreferredSize(new Dimension(Start.FRAME_WIDTH-10, 20));

        
        

            tablePanel = new JPanel(new GridLayout(9,4,2,2));
            tablePanel.setPreferredSize(new Dimension(Start.FRAME_WIDTH, Start.FRAME_HEIGHT/2-90));
            
            for(int i = 0; i< tableElements.length; i++){
                for (int j = 0; j < tableElements[i].length; j++) {
                    if(j==0){
                        tableElements[i][j] = new JLabel("series: "+(i+1));
                        tablePanel.add(tableElements[i][j]);
                    }else{
                        int tempID = 100*(i+1)+j;
                        tableElements[i][j] = new JLabel("| "+tempID+ ": "+ActionsLib.getActionName(tempID) + 
                                                "\t  ("+ActionsLib.getActionAmmoCost(tempID)+")");
                        tablePanel.add(tableElements[i][j]);
                    }
                    
                }
            }
        add(tablePanel, BorderLayout.SOUTH);
            
        playerInputTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateLabelColor();
                callTextChanged();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateLabelColor();
                callTextChanged();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateLabelColor();
                callTextChanged();
            }
        });    
        add(playerInputTextField, BorderLayout.NORTH);
    }
    private void callTextChanged() {
        if (textChangeListener != null) {
            textChangeListener.onTextChanged(playerInputTextField.getText());
        }
    }

    public void setInputListener(ActionListener listener){
        this.inputListener = listener;
    }
    public void setTextChangeListener(TextChangeListener listener){
        this.textChangeListener = listener;
    }
    
    public String getInput(){
        String inputString = playerInputTextField.getText();

        return inputString;
    }

    public void clearInput(){
        playerInputTextField.setText("");
        resetLabelColor();
    }

    public void resetLabelColor(){
        for(int i = 0; i < tableElements.length; i++){
            for (int j = 0; j < tableElements[i].length; j++) {
                if(j != 0 && tableElements[i][j] != null) {
                    tableElements[i][j].setForeground(Color.BLACK);
                }
            }
        }
    }

    public void updateLabelColor(){
        String text = playerInputTextField.getText().trim();;
        resetLabelColor();
        if(text.isEmpty()){
            return;
        }
        
        try {
            int inputID = Integer.parseInt(text);
            int col = inputID % 100;
            int row = inputID / 100 - 1;
            if(col <= 3 && col > 0){
                if(row <= 8 && row >= 0){
                    tableElements[row][col].setForeground(Color.GREEN);
                }
            }
            //repaint();
        } catch (NumberFormatException e) {
            // 输入不是数字，不进行高亮
        } 
    }

    public interface TextChangeListener {
    
        public void onTextChanged(String textInput);
    }


}
