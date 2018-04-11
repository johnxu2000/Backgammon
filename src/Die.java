import static java.lang.Math.random;

public class Die
{
    // Processing - instance variables
    private final int MAX = 6;
    private int faceValue;

    //Processing......
    //accessor - sets the faceValue to 1
    public Die()
    {
        faceValue = 1;
    }
    //accessor - returns a random nomber for the die
    /**
     * Constructor for objects of class Die
     */
    public int roll()
    {
        faceValue = (int)(Math.random() * MAX) + 1;

        return faceValue;
    }
    //mutator - changes the face value of the die to a set value
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
    public String toString()
    {
        String result = Integer.toString(faceValue);

        return result;
    }
}
