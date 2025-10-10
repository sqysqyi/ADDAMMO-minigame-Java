package ADDAMMOLOL_0_1_x.AddAmmoUI;

import ADDAMMOLOL_0_1_x.AddAmmoMain.Start;

public interface FrameSize {
    //game frame size
    public static final int Frame_SIZE_WIDTH = Start.FRAME_WIDTH;

    public static final int Frame_SIZE_HEIGHT = Start.FRAME_HEIGHT;

    //select table size
    public static final int ST_SIZE_H = Frame_SIZE_HEIGHT/2;

    public static final int ST_SIZE_W = Frame_SIZE_WIDTH;

    //secend panel size
    public static final int P2_SIZE_H = Frame_SIZE_HEIGHT/4;

    public static final int P2_SIZE_W = Frame_SIZE_WIDTH;

    ////playe stats panel size
    /**/public static final int PSP_SIZE_H = Frame_SIZE_HEIGHT/4;

    /**/public static final int PSP_SIZE_W = Frame_SIZE_WIDTH/3 * 2;

    //action discription panel size
    /**/public static final int ADP_SIZE_H = PSP_SIZE_H;

    /**/public static final int ADP_SIZE_W = Frame_SIZE_WIDTH - PSP_SIZE_W - 10;

    //log panel size
    public static final int LP_SIZE_H = Frame_SIZE_HEIGHT - PSP_SIZE_H - ST_SIZE_H;

    public static final int LP_SIZE_W = Frame_SIZE_WIDTH;

    //specailized for StartingMenuPanel.java
    public static final int EMPTY_PANEL_WIDTH = Frame_SIZE_WIDTH/3;

    public static final int EMPTY_PANEL_HEIGHT = Frame_SIZE_HEIGHT/4;

    public static final int BUTTONAREA_WIDTH = Frame_SIZE_WIDTH/3;

    public static final int BOTTOM_GAP = Frame_SIZE_HEIGHT *2/3 - EMPTY_PANEL_HEIGHT; 
    
}
