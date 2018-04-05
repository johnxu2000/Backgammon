import static java.lang.Math.random;

public class Die {
    int die;
    public void roll(){
        die = (int) (Math.random()*6) + 1;
    }
    public void drawDie(){

    }
}
