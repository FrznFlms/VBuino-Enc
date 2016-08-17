package com;

import com.tools.FlowBuino;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

//The roots of the tree for VBuino
public class EncGfx extends JPanel {
    //"picture" refers to boolean[] B_C
    //"pixel" refers to an element of boolean[] B_C
    
    //VBuino's main window frame, refered as "JFrame"
    public JFrame main = new JFrame("VBuino Encoder");
    
    //A file filter looking for .vbuino files
    private static final FileNameExtensionFilter VBUINO_EXT = new FileNameExtensionFilter("VBuino Sprite", "vbuino");
    
    //The screen width and height(in pixels)
    public static final int S_WIDTH = 700;
    public static final int S_HEIGHT = 700;
    
    //The font VBuino uses(see the Font5x7 class)
    private final Font5x7 Msg = new Font5x7();
    
    //Variables that track the cursor's position on the picture
    public int cursor_x = 0;
    public int cursor_y = 0;
    
    //The draw color(white = true, black = false)
    public boolean drawColor = true;
    
    //This boolean keeps track of the LEFT, RIGHT and MIDDLE positions of the mouse
    //(being held = true, let go = false)
    public boolean hold = false;
    
    //This variable keeps track of what is currently being edited
    //(Size = 4, Width = 5, Height = 6)
    public int Sec = 0;
    
    //What is currently showing for the values of the Size, Width and Height
    public String SizeS = "10";
    public String WidthS = "16";
    public String HeightS = "16";
    
    //The mouse detectors that detects the presses of the right buttons
    //Calls AREAEXEC(int) with it's given value (see the AreaRun class)
    public final AreaRun SwapColor = new AreaRun(7);
    public final AreaRun Size = new AreaRun(3);
    public final AreaRun Width = new AreaRun(4);
    public final AreaRun Height = new AreaRun(5);
    public final AreaRun Encode = new AreaRun(8);
    public final AreaRun Save = new AreaRun(9);
    public final AreaRun Open = new AreaRun(10);
    public final AreaRun Decode = new AreaRun(11);
    
    //The picture's Width and Height
    public int B_W = 16;
    public int B_H = 16;
    
    //Each pixel's view Size(how big each pixel appears on the screen)
    public int B_S = 10;
    
    //All the color data contained in the picture
    //Each boolean represents one pixel(white = true, black = false)
    //To get or set a pixel, use this(x and y being the position of the pixel): B_C[x + y * B_W]
    public boolean[] B_C = new boolean[B_W * B_H];
    
    //The mouse detectors spread over the picture so it can detect where
    //your mouse is and move the cursor accordingly
    //To get a pixel detector corresponding to a certain pixel
    //use this(x and y being the posiion of the pixel): B_P[x + y * B_W]
    public PixelDetector[] B_P;
    
    //A Popup window that appears after pressing the "Encode" button
    //Displays the sprite in a binary formtted array and a hex formatted array
    private class ShowResults extends JFrame implements java.awt.event.ActionListener {
        //Text area storing the binary result
        private final JTextArea bin = new JTextArea();
        
        //Text area storing the hex result
        private final JTextArea hex = new JTextArea();
        
        //Scroll pane containing the binary text area
        private final JScrollPane binsp = new JScrollPane(bin);
        
        //Scroll pane containing the hex text area
        private final JScrollPane hexsp = new JScrollPane(hex);
        
        //A JPanel containing most of the window(so we can add 3 elements to a scroll pane)
        private final JPanel win = new JPanel();
        
        //A scroll pane containing the win JPanel
        private final JScrollPane winsp = new JScrollPane(win);
        
        //A button displaying "OK" that closes the window
        private final JButton ok = new JButton("OK");
        
        ShowResults() {
            //Creates the JFrame with title: "Results"
            super("Results");
            
            //Sets the layout of the win JPanel to the BorderLayout
            //so we can place the text areas and buttons appropriately
            win.setLayout(new BorderLayout());
            
            //Optional: Set the look and feel of the window to the current system look and feel
            try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());} catch (Exception e) {}
            
            //Always show the scroll bars for the text area scroll panes
            binsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            binsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            hexsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            hexsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            
            //Add the text area scroll panes to the "win" JPanel
            win.add(binsp, BorderLayout.NORTH);
            win.add(hexsp, BorderLayout.CENTER);
            
            //Tell the button to execute actionPerformed(ActionEvent) when clicked
            ok.addActionListener(this);
            
            //Add the button to the "win" JPanel
            win.add(ok, BorderLayout.SOUTH);
            
            //Tell the "winsp" scroll pane to always show the vertical scroll bar and sometimes the horizontal one
            winsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            winsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            
            //Add the "winsp" scroll pane to the window
            add(winsp);
            
            //Set the text for the text areas to exportBinary(int, int, boolean[]) and exportHex(int, int, boolean[])
            bin.setText(exportBinary(B_W, B_H, B_C));
            hex.setText(exportHex(B_W, B_H, B_C));
            
            //Set the size of the window
            setSize(600, 500);
            
            //Show the window
            setVisible(true);
        }
        
        //Called when the button "ok" is pressed
        @Override public void actionPerformed(java.awt.event.ActionEvent e) {
            //Hide the window
            setVisible(false);
            
            //Destroy the window and clean up
            dispose();
        }
    }
    
    //Do a paint bucket fill in the color paint_color at the coordinates X and Y
    void paintBucket(int X, int Y, boolean paint_color) {
        //Get the color the bucket is trying to fill
        boolean bucket_color = B_C[X + Y * B_W];
        
        //Define the logic of the paint bucket
        class PaintCell {
            //What pixel the cell I should focus on
            private final int cell_x;
            private final int cell_y;
            
            //If I should do anything
            private final boolean cell_active;
            
            PaintCell(int cell_x, int cell_y) {
                //Set the pixel I should focus on
                this.cell_x = cell_x;
                this.cell_y = cell_y;
                
                //Check if I am on the board, if not: set cell_active to false
                cell_active = cell_x >= 0 && cell_x < B_W && cell_y >= 0 && cell_y < B_H;
            }
            
            //Bucket this pixel, and create 4 more PaintCells to look beside it
            void paintSelf() {
                if (bucket_color != paint_color && cell_active && B_C[cell_x + cell_y * B_W] == bucket_color) {
                    //If the pixel I'm on isn't the same color as the color I'm try to paint it as and
                    //I am an active cell and the pixel I'm on is the color I'm looking to paint
                    
                    //Paint the pixel
                    B_C[cell_x + cell_y * B_W] = paint_color;
                    
                    //Create four other PaintCells to check the pixels around it
                    new PaintCell(cell_x + 1, cell_y).paintSelf();
                    new PaintCell(cell_x - 1, cell_y).paintSelf();
                    new PaintCell(cell_x, cell_y + 1).paintSelf();
                    new PaintCell(cell_x, cell_y - 1).paintSelf();
                }
            }
        }
        
        //Start the paint bucket
        new PaintCell(X, Y).paintSelf();
    }
    
    //Executed once one of the AreaRun classes has had a mouse press on them (see the AreaRun class)
    void AREAEXEC(int VAL) {
        if (Sec > 0) {
            //If something is already being edited
            
            //Set the Size, Width and Height back to their default values
            //Basicly cancel the last edit
            SizeS = "" + B_S;
            WidthS = "" + B_W;
            HeightS = "" + B_H;
        }
        
        //Create a file chooser, to be or not to be used
        JFileChooser select = new JFileChooser();
        
        //Add and recommend the ".vbuino" extension
        select.addChoosableFileFilter(VBUINO_EXT);
        select.setFileFilter(VBUINO_EXT);
        
        switch (VAL) {
            //If the Size, Width or Height button was pressed, clear it's corresponding
            //display string, then set "Sec" to a value depending on the button that was pressed:
            //(Size = 4, Width = 5, Height = 6)
            case 3: SizeS = ""; Sec = 4; break;
            case 4: WidthS = ""; Sec = 5; break;
            case 5: HeightS = ""; Sec = 6; break;
            
            //If the "Color" button is pressed, swap the draw color
            case 7: drawColor = !drawColor; break;
            
            //If the "Encode" button is pressed, show the results(see the ShowResult class)
            case 8: new ShowResults(); break;
            
            //If the "Save" button is pressed, ask where to save and what to name the file
            case 9:
                
                //Ask where to save the file
                if (select.showSaveDialog(main) == JFileChooser.APPROVE_OPTION) {
                    //If the user finished "saving" the file
                    
                    //Create a new FlowBuino that writes to the file that the user
                    //selected plus a ".vbuino" at the end so it saves as a .vbuino file(see the FlowBuino class)
                    FlowBuino fb = new FlowBuino(select.getSelectedFile().getPath() + ".vbuino");
                    
                    //Write the picture to the file
                    fb.write(B_W, B_H, B_C);
                }
                break;
            case 10:
                //Ask what file to open
                if (select.showOpenDialog(main) == JFileChooser.APPROVE_OPTION) {
                    //If the user finished saving the file
                    
                    //Create a new FlowBuino that reads from the file the user selected(see the FlowBuino class)
                    FlowBuino fb = new FlowBuino(select.getSelectedFile().getPath());
                    
                    //Try getting the width of the file
                    fb.getW();
                    
                    if (!fb.Sprite.Failed){
                        //If it could get the Width of the file
                        
                        //Reset the sprite and the mouse detectors with this new Width and Height
                        newBoard(fb.getW(), fb.getH());
                        
                        //Make the new Width and Height visible
                        WidthS = Integer.toString(B_W);
                        HeightS = Integer.toString(B_H);
                        
                        //Get and set the picture in the file to the current picture
                        B_C = fb.getSpr();
                    }
                }
                break;
            case 11: new DecodeInput(); break;
            default: break;
        }
        
        //Update the screen
        repaint();
    }
    
    //Placed all over the picture to detect where the mouse is and if it is being pressed or not
    public class PixelDetector extends JPanel implements MouseListener {
        //The location of the pixel I should focus on
        private int X;
        private int Y;
        
        public PixelDetector(int X, int Y) {
            //Tells me what to do once a mouse has moved over me, clicked me, etc...
            addMouseListener(this);
            
            //Sets the pixel I should focus on
            this.X = X;
            this.Y = Y;
        }
        
        //Do nothing if the mouse leaves my area
        @Override public void mouseExited(MouseEvent input) {}
        
        //If the mouse enters my area
        @Override public void mouseEntered(MouseEvent input) {
            //Set the cursor to my location
            cursor_x = X;
            cursor_y = Y;
            
            //Check if the pixel I am on should be drawn on
            drawPixel(X, Y);
        }
        
        //If the mouse is pressed in my area
        @Override public void mousePressed(MouseEvent input) {
            //Set the "hold" variable to true, stating that the mouse is currently being held down
            hold = true;
            
            //Try drawing a pixel in my location
            drawPixel(X, Y);
        }
        
        //If the mouse is released in my area
        @Override public void mouseReleased(MouseEvent input) {
            //Set the "hold" variable to false, stating that the mouse is no longer being held
            hold = false;
        }
        
        //Do nothing if the mouse is clicked
        @Override public void mouseClicked(MouseEvent input) {}
    }
    
    //Placed over the button to make it detect the mouse click
    public class AreaRun extends JPanel implements MouseListener {
        //What I should call AREAEXEC(int) with
        private int Val = 0;
        
        public AreaRun(int Val) {
            //Tells me what to do when a mouse clicks me
            addMouseListener(this);
            
            //Sets what I should call AREAEXEC(int) with
            this.Val = Val;
        }
        
        //Do nothing if the mouse exits my area
        @Override public void mouseExited(MouseEvent input) {}
        
        //Do nothing if the mouse enters my area
        @Override public void mouseEntered(MouseEvent input) {}
        
        //Call AREAEXEC(int) with Val when the mouse is pressed in my area
        @Override public void mousePressed(MouseEvent input) { AREAEXEC(Val); }
        
        //Do nothing if the mouse is released in my area
        @Override public void mouseReleased(MouseEvent input) {}
        
        //Do nothing if the mouse is clicked in my area
        @Override public void mouseClicked(MouseEvent input) {}
    }

    //Reset all the mouse detectors
    public void updateMouse() {
        if (B_P != null) {
            //If the PixelDetector[] B_P has been initialized
            
            //Remove all the elements in B_P from the main window frame
            for (PixelDetector a : B_P) main.remove(a);
            
            //Remove all the buttons(causes a bug if not removed and the added)
            main.remove(SwapColor);
            main.remove(Size);
            main.remove(Width);
            main.remove(Height);
            main.remove(Save);
            main.remove(Open);
            main.remove(Encode);
            main.remove(Decode);
        }
        
        //Initialize or Reinitialize the array
        B_P = new PixelDetector[B_W * B_H];
        
        //Initialize each element of the array
        for (int y = 0; y < B_H; y++) {
            for (int x = 0; x < B_W; x++) {
                //Initialize the detector
                B_P[x + y * B_W] = new PixelDetector(x, y);
                
                //Set the size and location of the detector
                B_P[x + y * B_W].setSize(B_S, B_S);
                B_P[x + y * B_W].setLocation(x * B_S, y * B_S);
                
                //Add the detector to the window frame
                main.add(B_P[x + y * B_W]);
            }
        }
        
        //Add all the buttons back
        main.add(SwapColor);
        main.add(Size);
        main.add(Width);
        main.add(Height);
        main.add(Save);
        main.add(Open);
        main.add(Encode);
        main.add(Decode);
    }
    
    //Reset the board with the newly provided Width and Height
    public void newBoard(int W, int H) {
        //Set the Width and Height to the newly provided Width and Height
        B_W = W;
        B_H = H;
        
        //Reset the board
        B_C = new boolean[W * H];
        
        //Set the board to the current draw color
        for (int a = 0; a < B_C.length; a++) B_C[a] = drawColor;
        
        //Reset the mouse detectors to this new Width and Height
        updateMouse();
        
        //Reset the cursor to 0, 0 so we don't edit a pixel not part of our sprite
        cursor_x = 0;
        cursor_y = 0;
    }
    
    public static int toTheClosestMultipleOf(int Of, int Num) { while (Num % Of != 0) Num++; return Num; }
    
    //Returns true if the char provided is a number(0 through 9)
    public static boolean validNum(char num) { return (int) num >= '0' && (int) num <= '9'; }
    
    //Check if the mouse is being held("hold" = true), if so set the pixel on the picture to the current draw color
    public void drawPixel(int x, int y) { if (hold) B_C[x + y * B_W] = drawColor; repaint(); }
    
    //Scales a 2D array to a new Width and Height
    public static boolean[] scale(int prevW, int prevH, int W, int H, boolean[] target) {
        //Create a new array the size of the new Width and Height
        System.out.println(W);
        System.out.println(H);
        boolean[] result = new boolean[W * H];
        
        //Cycle through all the elements in the previous 2D array and add them to the new array
        for (int y = 0; y < prevH; y++)
            for (int x = 0; x < prevW; x++)
                try { result[x + y * W] = target[x + y * prevW]; } catch (ArrayIndexOutOfBoundsException e) {}
        
        //Return the new array
        return result;
    }
    
    //Squish 8 booleans into one int(I'm not sure if java provides something to do this)
    public static int comp8bool(boolean[] target) {
        return (target[7] ? 128 : 0)
                + (target[6] ? 64 : 0)
                + (target[5] ? 32 : 0)
                + (target[4] ? 16 : 0)
                + (target[3] ? 8 : 0)
                + (target[2] ? 4 : 0)
                + (target[1] ? 2 : 0)
                + (target[0] ? 1 : 0);
    }
    
    //Unsqish 1 squished int into 8 bools
    public static boolean[] decomp8Int(int target) {
        boolean[] result = new boolean[8];
        if (target >= 128) { target -= 128; result[0] = true; }
        if (target >= 64) { target -= 64; result[1] = true; }
        if (target >= 32) { target -= 32; result[2] = true; }
        if (target >= 16) { target -= 16; result[3] = true; }
        if (target >= 8) { target -= 8; result[4] = true; }
        if (target >= 4) { target -= 4; result[5] = true; }
        if (target >= 2) { target -= 2; result[6] = true; }
        if (target >= 1) { target -= 1; result[7] = true; }
        return result;
    }
    
    //Returns a String that can be used as a gamebuino sprite
    public String exportBinary(int W, int H, boolean[] target) {
        if (W % 8 != 0) {
            //If the width is not a multiple of 8
            
            //Create a new width that is the closest multiple of the actual width
            int newW = toTheClosestMultipleOf(8, W);
            
            //Scale the array with this new Width in mind
            target = scale(W, H, newW, H, target);
            
            //Set the original Width to this new Width
            W = newW;
        }
        
        //Start a String containing the code for the sprite
        String result = "const unsigned char PROGMEM sprite[] = {";
        
        //Add the Width and the Height to the array
        result += W + ", " + H + ",\n";
        
        //Look at each 8 pixels of the picture and display them like this: "B12345678,"
        for (int y = 0; y < H; y++) {
            for (int x = 0; x < W; x += 8) {
                result += "B"
                        + (!target[x + y * W] ? 1 : 0)
                        + (!target[1 + x + y * W] ? 1 : 0)
                        + (!target[2 + x + y * W] ? 1 : 0)
                        + (!target[3 + x + y * W] ? 1 : 0)
                        + (!target[4 + x + y * W] ? 1 : 0)
                        + (!target[5 + x + y * W] ? 1 : 0)
                        + (!target[6 + x + y * W] ? 1 : 0)
                        + (!target[7 + x + y * W] ? 1 : 0)
                        + ",";
            }
            result += "\n";
        }
        //End the sprite code and return it
        result += "};";
        return result;
    }
    
    public String exportHex(int W, int H, boolean[] target) {
        if (W % 8 != 0) {
            //If the width is not a multiple of 8
            
            //Create a new width that is the closest multiple of the actual width
            int newW = toTheClosestMultipleOf(8, W);
            
            //Scale the array with this new Width in mind
            target = scale(W, H, newW, H, target);
            
            //Set the original Width to this new Width
            W = newW;
        }
        
        //Start a String containing the code for the sprite
        String result = "const unsigned char PROGMEM sprite[] = {";
        
        //Add the Width and the Height to the array
        result += W + ", " + H + ",\n";
        
        //Look at each 8 pixels of the picture and display them like this: "0x12,"
        for (int y = 0; y < H; y++) {
            for (int x = 0; x < W; x += 8) {
                boolean[] hex = {
                    !target[7 + x + y * W],
                    !target[6 + x + y * W],
                    !target[5 + x + y * W],
                    !target[4 + x + y * W],
                    !target[3 + x + y * W],
                    !target[2 + x + y * W],
                    !target[1 + x + y * W],
                    !target[x + y * W]};
                int num = comp8bool(hex);
                result += "0x"
                        + Integer.toHexString(num)
                        + ",";
            }
        }
        //End the sprite code and return it
        result += "\n};";
        return result;
    }
    
    private class DecodeInput extends JFrame implements java.awt.event.ActionListener {
        //Instructions on how to use the decoder
        private final JLabel inst = new JLabel("Paste the array contents in this text area below");
        
        //Text area where the user puts the text to decode
        private final JTextArea input = new JTextArea();
        
        //Scroll pane containing the input text area
        private final JScrollPane inputsp = new JScrollPane(input);
        
        //Radio buttons to select if you want to decode a binary or hex array
        private final JRadioButton bin = new JRadioButton("Binary");
        private final JRadioButton hex = new JRadioButton("Hexcidecimal");
        
        //Button group that links both radio buttons
        private final ButtonGroup binhexch = new ButtonGroup();
        
        //Panel containing both radio buttons
        private final JPanel radio = new JPanel();
        
        //Main window JPanel
        private final JPanel win = new JPanel();
        
        //Window scroll pane
        private final JScrollPane winsp = new JScrollPane(win);
        
        //Ok button that starts the decoding
        private final JButton ok = new JButton("OK");
        
        DecodeInput() {
            //Creaes a new frame with the title "Decode"
            super("Decode");
            
            //Set the the "win" JPanel layout to a border layout
            win.setLayout(new BorderLayout());
            
            //Set the look and feel to the system look and feel
            try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());} catch (Exception e) {}
            
            //Always show the scrollbars for the input text
            inputsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            inputsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            
            //Add the instructions to the window
            win.add(inst, BorderLayout.NORTH);
            
            //Select the binary radio button by default
            bin.setSelected(true);
            
            //Add both buttons to the button group
            binhexch.add(bin);
            binhexch.add(hex);
            
            //Add both buttons to a JPanel(to be grouped together)
            radio.add(bin);
            radio.add(hex);
            
            //Add both the radio buttons and the input text area
            win.add(radio, BorderLayout.EAST);
            win.add(inputsp, BorderLayout.WEST);
            
            //Tell the "ok" button to execute actionPerformed(ActionEvent) when pressed
            ok.addActionListener(this);
            
            //Add the "ok" button
            win.add(ok, BorderLayout.SOUTH);
            
            //Say the window can sometimes have a horizontal scrollbar, but alwarys has a vertical one
            winsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            winsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            
            //Define the size of the text area(only way that worked for me)
            input.setText("\t\t\t\t\n\n\n\n\n\n\n\n\n\n\n");
            
            //Add the window and the window scroll pane
            add(winsp);
            
            //Set the size of the window
            setSize(600, 500);
            
            //Display the window
            setVisible(true);
        }
        
        //Called when the button "ok" is pressed
        @Override public void actionPerformed(java.awt.event.ActionEvent e) {
            //Select which decoder to use, depending on the active radio button
            if (bin.isSelected()) decodeBinary(input.getText());
            if (hex.isSelected()) decodeHex(input.getText());
            
            //Hide the window
            setVisible(false);
            
            //Destroy the window and clean up
            dispose();
        }
    }
    
    public void decodeBinary(String data) {
        char[] code = data.toCharArray();
        boolean[] newSprite;
        int newWidth = 0;
        int newHeight = 0;
        int readAt = 0;
        boolean shouldClose = false;
        String wS = "0";
        for (int a = readAt; a < code.length; a++) {
            if (code[a] != ',') { if (validNum(code[a])) wS += code[a]; }
            else shouldClose = true;
            newWidth = Integer.parseInt(wS);
            readAt = a + 1;
            if (shouldClose) break;
        }
        shouldClose = false;
        String hS = "0";
        for (int a = readAt; a < code.length; a++) {
            if (code[a] != ',') { if (validNum(code[a])) hS += code[a]; }
            else shouldClose = true;
            newHeight = Integer.parseInt(hS);
            readAt = a + 1;
            if (shouldClose) break;
        }
        int readFromSprite = 0;
        newSprite = new boolean[toTheClosestMultipleOf(8, newWidth) * newHeight];
        for (int a = readAt; a < code.length; a++) {
            if (code[a] == 'B') {
                for (int b = 0; b < 8; b++) if (code[a + b + 1] == '0') newSprite[readFromSprite + b] = true;
                readFromSprite += 8;
                a += 8;
            }
        }
        newSprite = scale(toTheClosestMultipleOf(8, newWidth), newHeight, newWidth, newHeight, newSprite);
        newBoard(newWidth, newHeight);
        B_C = newSprite;
        repaint();
    }
    
    public void decodeHex(String data) {
        char[] code = data.toCharArray();
        boolean[] newSprite;
        int newWidth = 0;
        int newHeight = 0;
        int readAt = 0;
        boolean shouldClose = false;
        String wS = "0";
        for (int a = readAt; a < code.length; a++) {
            if (code[a] != ',') { if (validNum(code[a])) wS += code[a]; }
            else shouldClose = true;
            newWidth = Integer.parseInt(wS);
            readAt = a + 1;
            if (shouldClose) break;
        }
        shouldClose = false;
        String hS = "0";
        for (int a = readAt; a < code.length; a++) {
            if (code[a] != ',') { if (validNum(code[a])) hS += code[a]; }
            else shouldClose = true;
            newHeight = Integer.parseInt(hS);
            readAt = a + 1;
            if (shouldClose) break;
        }
        int readFromSprite = 0;
        newSprite = new boolean[toTheClosestMultipleOf(8, newWidth) * newHeight];
        for (int a = readAt; a < code.length; a++) {
            if (code[a] == '0' && code[a + 1] == 'x') {
                boolean[] decomped = decomp8Int(Integer.parseInt("" + code[a + 2] + code[a + 3], 16));
                for (int b = 0; b < 8; b++) newSprite[readFromSprite + b] = !decomped[b];
                readFromSprite += 8;
                a += 3;
            }
        }
        newSprite = scale(toTheClosestMultipleOf(8, newWidth), newHeight, newWidth, newHeight, newSprite);
        newBoard(newWidth, newHeight);
        B_C = newSprite;
        repaint();
    }
    
    //Update the screen(call as repaint())
    @Override public void paintComponent(Graphics gr) {
        //Cast the Graphics object into a Graphics2D object
        Graphics2D g = (Graphics2D) gr;
        
        //Set the background color to white and draw it over the screen(refresh the screen)
        g.setColor(Color.white);
        g.fillRect(0, 0, S_WIDTH, S_HEIGHT);
        
        //Draw all the pixels in the picture
        for (int B_Y = 0; B_Y < B_H; B_Y++) {
            for (int B_X = 0; B_X < B_W; B_X++) {
                g.setColor(new Color(B_C[B_X + B_Y * B_W] ? 0xFFFFFF : 0x000000));
                g.fillRect(B_X * B_S, B_Y * B_S, B_S, B_S);
            }
        }
        
        //Give each pixel a black border
        g.setColor(Color.black);
        for (int B_Y = 0; B_Y < B_H; B_Y++) {
            for (int B_X = 0; B_X < B_W; B_X++) {
                g.drawRect(B_X * B_S, B_Y * B_S, B_S, B_S);
            }
        }
        
        //Draw the cursor
        g.setColor(Color.red);
        g.drawRect(cursor_x * B_S, cursor_y * B_S, B_S, B_S);
        
        //Draw all the buttons
        Msg.Color = drawColor ? 0xAAAAAA : 0x000000;
        Msg.write(g, "Color", 600, 0, 2);
        Msg.Color = 0x888888;
        Msg.write(g, "S:" + SizeS, 600, 20, 2);
        Msg.Color = 0x666666;
        Msg.write(g, "W:" + WidthS, 600, 40, 2);
        Msg.write(g, "H:" + HeightS, 600, 60, 2);
        Msg.Color = 0x000000;
        Msg.write(g, "Save", 600, 80, 2);
        Msg.write(g, "Open", 600, 100, 2);
        Msg.Color = 0xAAAAAA;
        Msg.write(g, "Encode", 600, 120, 2);
        Msg.write(g, "Decode", 600, 140, 2);
    }
}
