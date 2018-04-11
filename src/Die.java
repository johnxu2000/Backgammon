import java.awt.*;

import static java.lang.Math.random;

public class Die
{
    // Processing - instance variables
    private final int MAX = 6;
    private int faceValue;
    int upperX, upperY;
    public Die(int x, int y)
    {
        faceValue = 1;
        upperX = x;
        upperY = y;
    }

    public void draw(Graphics page)
    {
        Graphics2D g2d = (Graphics2D) page;
        Font font = new Font("Arial", Font.PLAIN, 100);
        g2d.setFont(font);

        g2d.drawString(getStringFaceValue(), upperX, upperY);

    }
    //Processing......
    //accessor - returns a random nomber for the die
    /**
     * Constructor for objects of class Die
     */

    //mutator - changes the face value of the die to a set value
    public int roll()
    {
        faceValue = (int)(Math.random() * 6) + 1;
        return faceValue;
    }

    public void setFaceValue(int value)
    {
        faceValue = value;
    }
    //accessor - returns the face value of the die
    public int getFaceValue()
    {
        return faceValue;
    }
    //accesor - returns the face value as a string
    private String getStringFaceValue()
    {
        String sFaceValue = "";
        switch(faceValue)
        {
            case 1:
                sFaceValue = "\u2680";
                break;
            case 2:
                sFaceValue = "\u2681";
                break;
            case 3:
                sFaceValue = "\u2682";
                break;
            case 4:
                sFaceValue = "\u2683";
                break;
            case 5:
                sFaceValue = "\u2684";
                break;
            case 6:
                sFaceValue = "\u2685";
                break;
            default:
                sFaceValue = "\u2680";
        }

        return sFaceValue;
    }
    public String toString()
    {
        String result = Integer.toString(faceValue);

        return result;
    }
}
