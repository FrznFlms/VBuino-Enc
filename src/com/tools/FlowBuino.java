package com.tools;

//Used to write and read VBuino sprites
public class FlowBuino {
    //The name(and path) of the sprite
    public final String Name;
    
    //The FlowFile I use to write the sprite(see the FlowFile class)
    public final FlowFile Sprite;
    
    public FlowBuino(String Name) {
        //Sets my name and initializes my FlowFile
        this.Name = Name;
        Sprite = new FlowFile(Name);
    }
    
    //Writes the provided Width, Height and Sprite to the FlowFile
    public void write(int W, int H, boolean[] S) {
        //Creates a data array to write to the FlowFile
        int[] data = new int[W * H + 2];
        
        //Sets the first 2 numbers of the file to the Width and Height
        data[0] = W;
        data[1] = H;
        
        //Writes the sprite after the Width and Height
        for (int a = 0; a < S.length; a++) data[a + 2] = S[a] ? 0xFF : 0x00;
        
        //Write the data to the FlowFile
        Sprite.write(data);
    }
    
    //Get the Width of the sprite
    public int getW() { return Sprite.read(1)[0]; }
    
    //Get the Height of the sprite
    public int getH() { return Sprite.read(2)[1]; }
    
    //Get the sprite
    public boolean[] getSpr() {
        //Get the file
        int[] Original = Sprite.read(getW() * getH() + 2);
        
        //Create an array the size of the sprite
        boolean[] Final = new boolean[getW() * getH()];
        
        //Copy the sprite to "Final"
        for (int a = 0; a < Final.length; a++) Final[a] = Original[a + 2] == 0xFF;
        
        //Return the sprite
        return Final;
    }
}
