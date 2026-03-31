package game.addammo.AddAmmoUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import static game.addammo.AddAmmoUI.FrameSize.*;

public class StartingMenuPanel extends JPanel implements initUI{
    JLabel title = new JLabel("Add Ammo! ");
    JPanel titlePanel;
    JPanel leftEmptyPanel,rightEmptyPanel,bottomPanel,buttonAreaPanel;
    JLabel buttonText_1,buttonText_2;

    private boolean configReady = false,startReady =false;
    private StartingButtonsListener startingButtonsListener;
    
    public StartingMenuPanel(){
        this(0);
    }
    public StartingMenuPanel(int unknown){
        initualizeUI();
    }

    @Override
    public void initualizeUI(){
        setLayout(new BorderLayout());

        titlePanel = new JPanel();
        titlePanel.setPreferredSize(new Dimension(Frame_SIZE_WIDTH, Frame_SIZE_HEIGHT/3));
        titlePanel.add(title);
        add(titlePanel, BorderLayout.NORTH);

        leftEmptyPanel = new JPanel();
        leftEmptyPanel.setPreferredSize(new Dimension(EMPTY_PANEL_WIDTH,EMPTY_PANEL_HEIGHT));
        add(leftEmptyPanel, BorderLayout.WEST);

        rightEmptyPanel = new JPanel();
        rightEmptyPanel.setPreferredSize(new Dimension(EMPTY_PANEL_WIDTH,EMPTY_PANEL_HEIGHT));
        add(rightEmptyPanel, BorderLayout.EAST);

        buttonAreaPanel = new JPanel(new GridLayout(2, 1, 0, 20));
        buttonAreaPanel.setPreferredSize(new Dimension(BUTTONAREA_WIDTH, EMPTY_PANEL_HEIGHT));
            JPanel startPanel = new JPanel();
            JLabel startLabel = new JLabel("Start!");
            startPanel.setBorder(new LineBorder(Color.BLUE, 3));
            startLabel.setHorizontalAlignment(SwingConstants.CENTER);
            startLabel.setVerticalAlignment(SwingConstants.CENTER);
            startPanel.setBackground(Color.WHITE);
            startPanel.add(startLabel);
            startPanel.addMouseListener(new MouseListener() {

                @Override
                public void mouseClicked(MouseEvent e) {
                    // TODO Auto-generated method stub
                    onStartButtonSelected();

                }

                @Override
                public void mousePressed(MouseEvent e) {
                    // TODO Auto-generated method stub
                    startPanel.setBackground(Color.GRAY);
                    startReady = true;
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    // TODO Auto-generated method stub
                    startPanel.setBackground(Color.WHITE);
                    if(startReady){
                        System.out.println("game starting !");
                        onStartButtonSelected();
                    } 
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    // TODO Auto-generated method stub
                    startLabel.setText("> Start! <");

                }

                @Override
                public void mouseExited(MouseEvent e) {
                    // TODO Auto-generated method stub
                    startLabel.setText("Start!");
                    //startPanel.setBackground(Color.WHITE);
                    startReady = false;
                }
                
            });
        buttonAreaPanel.add(startPanel);

            JPanel cfgStartPanel = new JPanel();
            JLabel cfgStartLabel = new JLabel("Config then start!");

            cfgStartPanel.setBorder(new LineBorder(Color.BLUE, 3));
            cfgStartPanel.setBackground(Color.WHITE);
            cfgStartLabel.setHorizontalAlignment(SwingConstants.CENTER);
            cfgStartLabel.setVerticalAlignment(SwingConstants.CENTER);
            cfgStartPanel.add(cfgStartLabel);
            cfgStartPanel.addMouseListener(new MouseListener() {

                @Override
                public void mouseClicked(MouseEvent e) {
                    // TODO Auto-generated method stub
                    onConfigButtonSelected();
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    // TODO Auto-generated method stub
                    cfgStartPanel.setBackground(Color.GRAY);
                    configReady = true;
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    // TODO Auto-generated method stub
                    cfgStartPanel.setBackground(Color.WHITE);

                    if(configReady){
                        System.out.println("new config panel will popup");
                        onConfigButtonSelected();
                    } 
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    // TODO Auto-generated method stub
                    cfgStartLabel.setText("> Config then start! <");
                    
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    // TODO Auto-generated method stub
                    cfgStartLabel.setText("Config then start! ");
                    configReady = false;
                }
                
            });

        buttonAreaPanel.add(cfgStartPanel);

        add(buttonAreaPanel,BorderLayout.CENTER);

        bottomPanel = new JPanel();
        bottomPanel.setPreferredSize(new Dimension(Frame_SIZE_WIDTH, BOTTOM_GAP));
        add(bottomPanel,BorderLayout.SOUTH);
    }

    public void onStartButtonSelected(){
        if(startingButtonsListener != null){
            startingButtonsListener.startButtonClicked();
        }
    }
    public void onConfigButtonSelected(){
        if(startingButtonsListener != null){
            startingButtonsListener.configButtonClicked();
        }
    }

    public interface StartingButtonsListener {
        
        void startButtonClicked();;
        void configButtonClicked();;
        
    }
    public void addStartingButtonsListener(StartingButtonsListener listener){
        this.startingButtonsListener = listener;
    }

    public void setSubCompVisible(boolean setVisible){
        
    }

}
