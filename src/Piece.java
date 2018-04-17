import java.awt.*;

public class Piece {
    int x,y;
    Color colour;

    public Piece(int x, int y, Color colour){
        this.x = x;
        this.y = y;
        this.colour = colour;
    }
    public void draw(Graphics g){
        g.setColor(colour);
        g.fillOval(x,y,30,30);
    }

    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y){
        this.y = y;
    }

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }

    public Color getColour() {
        return colour;
    }
}
