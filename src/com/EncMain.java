package com;

import javax.swing.*;
import java.awt.event.*;

//The top of the tree for VBuino
public class EncMain implements KeyListener {

    //Graphics JPanel
    private final EncGfx gfx = new EncGfx();

    //Startup code/Setup VBuino
    protected void Start() {

        //Set the size and location of the JFrame
        gfx.main.setSize(EncGfx.S_WIDTH, EncGfx.S_HEIGHT);
        gfx.main.setLocation(20, 20);

        //Create a new "board" and clear the picture
        gfx.newBoard(16, 16);

        //Add the graphics to the JFrame
        gfx.main.add(gfx);

        //Add the Key Listener to the JFrame
        gfx.main.addKeyListener(this);

        //Due to the way the graphics is setup, the window should not be resizable
        gfx.main.setResizable(false);

        //Make sure the JFrame closes when the X button is pressed
        gfx.main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Show the JFrame
        gfx.main.setVisible(true);

        //Set the location and size of all the buttons on the right side of the JFrame
        gfx.SwapColor.setLocation(600, 0);
        gfx.SwapColor.setSize(100, 20);
        gfx.Size.setLocation(600, 20);
        gfx.Size.setSize(100, 20);
        gfx.Width.setLocation(600, 40);
        gfx.Width.setSize(100, 20);
        gfx.Height.setLocation(600, 60);
        gfx.Height.setSize(100, 20);
        gfx.Save.setLocation(600, 80);
        gfx.Save.setSize(100, 20);
        gfx.Open.setLocation(600, 100);
        gfx.Open.setSize(100, 20);
        gfx.Encode.setLocation(600, 120);
        gfx.Encode.setSize(100, 20);
        gfx.Decode.setLocation(600, 140);
        gfx.Decode.setSize(100, 20);
    }

    //Code to execute once a key has been pressed on the keyboard
    @Override public void keyPressed(KeyEvent e) {
        //Gets the key code
        int code = e.getKeyCode();
        
        if (gfx.validNum(e.getKeyChar())) {
            //If the key that was pressed is a number
            
            switch (gfx.Sec) {
                case 4: gfx.SizeS += e.getKeyChar(); break; //If the user is currntly editing the Size, show the new number
                case 5: gfx.WidthS += e.getKeyChar(); break; //If the user is currently editing the Width, show the new number
                case 6: gfx.HeightS += e.getKeyChar(); break; //If the user is currently editing the Height, show the new number
                default: break;
            }
        }
        
        //If the user pressed ENTER
        if (code == KeyEvent.VK_ENTER) {
            switch (gfx.Sec) {
                case 4:
                    //If the user was editing the Size
                    
                    gfx.B_S = Integer.parseInt(gfx.SizeS); //Change the size to what the user entered
                    gfx.updateMouse(); //Reset all the mouse detectors to make up for this new size
                    break;
                case 5:
                    //If the user was editing the Width of the picture
                    
                    gfx.B_W = Integer.parseInt(gfx.WidthS); //Change the Width to what the user entered
                    
                    //Check if the width is reasonable(to prevent a crash and a save corruption)
                    if (gfx.B_W > 84) { //If what the user entered was above 84(the screen width of the gamebuino)
                        gfx.B_W = 84; //Set the screen Width back down to 84
                        gfx.WidthS = "84"; //Show that the screen Width is 84
                    }
                    gfx.newBoard(gfx.B_W, gfx.B_H); //Refresh the board to account for this new Width
                    break;
                case 6:
                    //If the user was editing the Height of the picture
                    
                    gfx.B_H = Integer.parseInt(gfx.HeightS); //Change the height to what the user entered
                    
                    //Check if the height is resonable(to prevent a crash and save corruption)
                    if (gfx.B_H > 48) { //If what the user entered was above 48(the screen height of the gamebuino)
                        gfx.B_H = 48; //Set the screen height back down to 48
                        gfx.HeightS = "48"; //Show that the screen height is 48
                    }
                    gfx.newBoard(gfx.B_W, gfx.B_H); //Refresh the board to account for this new height
                    break;
                default: break;
            }
            gfx.Sec = 0; //Say the user is no longer editing anything
        }
        
        //If the user presses P, pick the color the cursor is currently on top of
        if (code == KeyEvent.VK_P) gfx.drawColor = gfx.B_C[gfx.cursor_x + gfx.cursor_y * gfx.B_W];
        
        //If the user presses B, paint bucket the pixel the cursor is on top of with the selected color
        if (code == KeyEvent.VK_B) gfx.paintBucket(gfx.cursor_x, gfx.cursor_y, gfx.drawColor);
        
        //If the user presses S, start editing the size of the screen
        //So if the user sets the width and size too high(so the picture covers the right buttons)
        //The user can still save themselves from not being able to click size
        if (code == KeyEvent.VK_S) gfx.AREAEXEC(3);
        
        //If the user presses ESCAPE, close VBuino
        if (code == KeyEvent.VK_ESCAPE) {gfx.main.dispose(); System.exit(0);}
        
        //Show the changes
        gfx.repaint();
    }
    
    //Do nothing once a key has been released
    @Override public void keyReleased(KeyEvent e) {}
    
    //Do nothing once a key has been typed
    @Override public void keyTyped(KeyEvent e) {}
    
    //Start VBuino once the program is run
    public static void main(String[] args) {
        //Start VBuino
        new EncMain().Start();
    }
}
