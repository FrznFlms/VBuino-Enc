package com;

import java.awt.*;

//Used to draw text on the screen
public class Font5x7 {
    
    //Something to make creating FTRANS by hand a whole lot easier
    //(instead of "false" -> 5 letters, use "F" -> 1 letter)
    static final boolean F = false;
    static final boolean T = true;
    
    //The color to draw the text in
    public int Color = 0xFFFFFF;
    
    //The Width and Height of each letter
    static final int FWIDTH = 5;
    static final int FHEIGHT = 7;
    
    //All the data for each letter
    static final boolean[] FTRANS = {
        T,T,F,T,T,
        T,F,T,F,T,
        F,T,T,T,F,
        F,F,F,F,F,
        F,T,T,T,F,
        F,T,T,T,F,
        F,T,T,T,F,
        
        F,F,F,F,T,
        F,T,T,T,F,
        F,T,T,T,F,
        F,F,F,F,T,
        F,T,T,T,F,
        F,T,T,T,F,
        F,F,F,F,T,
        
        T,F,F,F,T,
        F,T,T,T,T,
        F,T,T,T,T,
        F,T,T,T,T,
        F,T,T,T,T,
        F,T,T,T,T,
        T,F,F,F,T,
        
        F,F,F,F,T,
        F,T,T,T,F,
        F,T,T,T,F,
        F,T,T,T,F,
        F,T,T,T,F,
        F,T,T,T,F,
        F,F,F,F,T,
        
        F,F,F,F,F,
        F,T,T,T,T,
        F,T,T,T,T,
        F,F,F,F,T,
        F,T,T,T,T,
        F,T,T,T,T,
        F,F,F,F,F,
        
        F,F,F,F,F,
        F,T,T,T,T,
        F,T,T,T,T,
        F,F,F,T,T,
        F,T,T,T,T,
        F,T,T,T,T,
        F,T,T,T,T,
        
        T,F,F,F,F,
        F,T,T,T,T,
        F,T,T,T,T,
        F,T,T,T,T,
        F,T,F,F,F,
        F,T,T,T,F,
        T,F,F,F,T,
        
        F,T,T,T,F,
        F,T,T,T,F,
        F,T,T,T,F,
        F,F,F,F,F,
        F,T,T,T,F,
        F,T,T,T,F,
        F,T,T,T,F,
        
        F,F,F,F,F,
        T,T,F,T,T,
        T,T,F,T,T,
        T,T,F,T,T,
        T,T,F,T,T,
        T,T,F,T,T,
        F,F,F,F,F,
        
        F,F,F,F,F,
        T,T,T,F,T,
        T,T,T,F,T,
        T,T,T,F,T,
        T,T,T,F,T,
        T,T,T,F,T,
        F,F,F,T,T,
        
        F,T,T,T,F,
        F,T,T,T,F,
        F,T,T,T,F,
        F,T,T,F,T,
        F,F,F,T,T,
        F,T,T,F,T,
        F,T,T,T,F,
        
        F,T,T,T,T,
        F,T,T,T,T,
        F,T,T,T,T,
        F,T,T,T,T,
        F,T,T,T,T,
        F,T,T,T,T,
        F,F,F,F,F,
        
        F,T,T,T,F,
        F,F,T,F,F,
        F,T,F,T,F,
        F,T,T,T,F,
        F,T,T,T,F,
        F,T,T,T,F,
        F,T,T,T,F,
        
        F,T,T,T,F,
        F,T,T,T,F,
        F,F,T,T,F,
        F,T,F,T,F,
        F,T,T,F,F,
        F,T,T,T,F,
        F,T,T,T,F,
        
        T,F,F,F,T,
        F,T,T,T,F,
        F,T,T,T,F,
        F,T,T,T,F,
        F,T,T,T,F,
        F,T,T,T,F,
        T,F,F,F,T,
        
        F,F,F,F,T,
        F,T,T,T,F,
        F,T,T,T,F,
        F,F,F,F,T,
        F,T,T,T,T,
        F,T,T,T,T,
        F,T,T,T,T,
        
        T,F,F,F,T,
        F,T,T,T,F,
        F,T,T,T,F,
        F,T,T,T,F,
        F,T,T,T,F,
        T,F,F,F,T,
        T,T,T,T,F,
        
        F,F,F,F,T,
        F,T,T,T,F,
        F,T,T,T,F,
        F,F,F,F,T,
        F,T,T,T,F,
        F,T,T,T,F,
        F,T,T,T,F,
        
        T,F,F,F,F,
        F,T,T,T,T,
        F,T,T,T,T,
        T,F,F,F,T,
        T,T,T,T,F,
        T,T,T,T,F,
        F,F,F,F,T,
        
        F,F,F,F,F,
        T,T,F,T,T,
        T,T,F,T,T,
        T,T,F,T,T,
        T,T,F,T,T,
        T,T,F,T,T,
        T,T,F,T,T,
        
        F,T,T,T,F,
        F,T,T,T,F,
        F,T,T,T,F,
        F,T,T,T,F,
        F,T,T,T,F,
        F,T,T,T,F,
        T,F,F,F,T,
        
        F,T,T,T,F,
        F,T,T,T,F,
        F,T,T,T,F,
        F,T,T,T,F,
        F,T,T,T,F,
        T,F,T,F,T,
        T,T,F,T,T,
        
        F,T,T,T,F,
        F,T,T,T,F,
        F,T,T,T,F,
        F,T,T,T,F,
        F,T,F,T,F,
        F,T,F,T,F,
        T,F,T,F,T,
        
        F,T,T,T,F,
        T,F,T,F,T,
        T,F,T,F,T,
        T,T,F,T,T,
        T,F,T,F,T,
        T,F,T,F,T,
        F,T,T,T,F,
        
        F,T,T,T,F,
        T,F,T,F,T,
        T,T,F,T,T,
        T,T,F,T,T,
        T,T,F,T,T,
        T,T,F,T,T,
        T,T,F,T,T,
        
        F,F,F,F,F,
        T,T,T,T,F,
        T,T,T,F,T,
        T,T,F,T,T,
        T,F,T,T,T,
        F,T,T,T,T,
        F,F,F,F,F,
        
        T,T,T,T,T,
        T,T,T,T,T,
        T,T,T,T,T,
        T,T,T,T,T,
        T,T,T,T,T,
        T,T,T,T,T,
        T,T,T,T,T,
        
        T,T,T,T,T,
        T,T,T,T,T,
        T,T,T,T,T,
        T,T,T,T,T,
        T,T,T,T,T,
        T,F,F,T,T,
        T,F,F,T,T,
        
        T,F,F,T,T,
        T,F,F,T,T,
        T,F,F,T,T,
        T,F,F,T,T,
        T,F,F,T,T,
        T,T,T,T,T,
        T,F,F,T,T,
        
        T,F,F,F,T,
        F,T,T,T,F,
        T,T,T,T,F,
        T,T,F,F,T,
        T,T,F,T,T,
        T,T,T,T,T,
        T,T,F,T,T,
        
        T,T,F,T,T,
        T,F,F,T,T,
        T,T,F,T,T,
        T,T,F,T,T,
        T,T,F,T,T,
        T,T,F,T,T,
        F,F,F,F,F,
        
        T,F,F,F,T,
        F,T,T,T,F,
        T,T,T,T,F,
        T,T,T,F,T,
        T,T,F,T,T,
        T,F,T,T,T,
        F,F,F,F,F,
        
        F,F,F,F,T,
        T,T,T,T,F,
        T,T,T,T,F,
        T,T,F,F,T,
        T,T,T,T,F,
        T,T,T,T,F,
        F,F,F,F,T,
        
        T,T,F,F,T,
        T,F,T,F,T,
        F,T,T,F,T,
        F,F,F,F,F,
        T,T,T,F,T,
        T,T,T,F,T,
        T,T,T,F,T,
        
        F,F,F,F,F,
        F,T,T,T,T,
        F,T,T,T,T,
        T,F,F,F,T,
        T,T,T,T,F,
        T,T,T,T,F,
        F,F,F,F,T,
        
        T,T,F,F,T,
        T,F,T,T,T,
        F,T,T,T,T,
        F,F,F,F,T,
        F,T,T,T,F,
        F,T,T,T,F,
        T,F,F,F,T,
        
        F,F,F,F,F,
        T,T,T,T,F,
        T,T,T,F,T,
        T,T,T,F,T,
        T,T,F,T,T,
        T,T,F,T,T,
        T,T,F,T,T,
        
        T,F,F,F,T,
        F,T,T,T,F,
        F,T,T,T,F,
        T,F,F,F,T,
        F,T,T,T,F,
        F,T,T,T,F,
        T,F,F,F,T,
        
        T,F,F,F,T,
        F,T,T,T,F,
        F,T,T,T,F,
        T,F,F,F,F,
        T,T,T,T,F,
        T,T,T,F,T,
        T,F,F,T,T,
        
        T,F,F,F,T,
        F,T,T,F,F,
        F,T,F,T,F,
        F,T,F,T,F,
        F,T,F,T,F,
        F,F,T,T,F,
        T,F,F,F,T,
        
        T,T,T,T,F,
        T,T,T,F,T,
        T,T,T,F,T,
        T,T,F,T,T,
        T,F,T,T,T,
        T,F,T,T,T,
        F,T,T,T,T,
        
        F,T,T,T,T,
        T,F,T,T,T,
        T,F,T,T,T,
        T,T,F,T,T,
        T,T,T,F,T,
        T,T,T,F,T,
        T,T,T,T,F,
        
        T,T,T,T,T,
        T,T,T,T,T,
        T,T,T,T,T,
        T,T,T,T,T,
        T,T,F,T,T,
        T,T,F,T,T,
        T,F,T,T,T,
        
        T,F,F,T,T,
        T,F,F,T,T,
        T,T,T,T,T,
        T,T,T,T,T,
        T,T,T,T,T,
        T,F,F,T,T,
        T,F,F,T,T,
    };
    
    //The current letter being drawn
    boolean[] CurrentTrans = {
        T,T,T,T,T,
        T,T,T,T,T,
        T,T,T,T,T,
        T,T,T,T,T,
        T,T,T,T,T,
        T,T,T,T,T,
        T,T,T,T,T,
    };
    
    //Draw a sprite by giving a color, the telling which areas not to draw
    private void drawSprite(Graphics2D g, int x, int y, int W, int H, int S, int color, boolean[] Trans) {
        //Set the color to the provided color
        g.setColor(new Color(color));
        
        //Draw each area
        for (int cy = 0; cy < H; cy++) {
            for (int cx = 0; cx < W; cx++) {
                //If the area is supposed to be NOT transparent, draw it
                if (!Trans[cx + cy * W]) g.fillRect(x + cx * S, y + cy * S, S, S);
            }
        }
    }
    
    public void write(Graphics2D g, String msg, int x, int y, int s) {
        //Get the positions of each letter as a byte array
        byte[] m = conv(msg);
        
        //Run through each letter
        for (int Letter = 0; Letter < m.length; Letter++) {
            //Put the letter data in the array "CurrentTrans"
            for (int yy = 0; yy < FHEIGHT; yy++) {
                for (int xx = 0; xx < FWIDTH; xx++) {
                    CurrentTrans[xx + (yy * FWIDTH)] = FTRANS[m[Letter] * (FWIDTH * FHEIGHT) + xx + (yy * FWIDTH)];
                }
            }
            //Draw "CurrentTrans" in the proper position
            drawSprite(g, x + Letter * FWIDTH * s + Letter * (s * 2), y, FWIDTH, FHEIGHT, s, Color, CurrentTrans);
        }
    }
    
    //This takes a string and returns a byte array which stores the position of each corresponding letter
    //Example: JELLO -> 9,4,11,11,14,
    //There might be a better way to do this though.
    public byte[] conv(String m) {
        char[] ms = m.toLowerCase().toCharArray();
        byte[] result = new byte[ms.length];
        for (int a = 0; a < ms.length; a++) {
            switch (ms[a]) {
                case 'a': result[a] = 0; break;
                case 'b': result[a] = 1; break;
                case 'c': result[a] = 2; break;
                case 'd': result[a] = 3; break;
                case 'e': result[a] = 4; break;
                case 'f': result[a] = 5; break;
                case 'g': result[a] = 6; break;
                case 'h': result[a] = 7; break;
                case 'i': result[a] = 8; break;
                case 'j': result[a] = 9; break;
                case 'k': result[a] = 10; break;
                case 'l': result[a] = 11; break;
                case 'm': result[a] = 12; break;
                case 'n': result[a] = 13; break;
                case 'o': result[a] = 14; break;
                case 'p': result[a] = 15; break;
                case 'q': result[a] = 16; break;
                case 'r': result[a] = 17; break;
                case 's': result[a] = 18; break;
                case 't': result[a] = 19; break;
                case 'u': result[a] = 20; break;
                case 'v': result[a] = 21; break;
                case 'w': result[a] = 22; break;
                case 'x': result[a] = 23; break;
                case 'y': result[a] = 24; break;
                case 'z': result[a] = 25; break;
                case ' ': result[a] = 26; break;
                case '.': result[a] = 27; break;
                case '!': result[a] = 28; break;
                case '?': result[a] = 29; break;
                case '1': result[a] = 30; break;
                case '2': result[a] = 31; break;
                case '3': result[a] = 32; break;
                case '4': result[a] = 33; break;
                case '5': result[a] = 34; break;
                case '6': result[a] = 35; break;
                case '7': result[a] = 36; break;
                case '8': result[a] = 37; break;
                case '9': result[a] = 38; break;
                case '0': result[a] = 39; break;
                case '/': result[a] = 40; break;
                case '\\': result[a] = 41; break;
                case ',': result[a] = 42; break;
                case ':': result[a] = 43; break;
                default: System.err.println("A Font Error Has Occured: Font5x7");
            }
        }
        return result;
    }
}
