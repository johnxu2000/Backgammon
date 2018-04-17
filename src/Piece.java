import java.awt.*;

public class Piece {
    private Color colour;
    private int width;
    private int height;
    private int x;
    private int y;
    private boolean canMove;
    public Piece(Color colour, int width, int height, int x, int y, boolean canMove) {
        this.colour = colour;
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.canMove = canMove;
    }
    public void draw(Graphics page){
        page.setColor(colour);
        page.drawOval(width,height,x,y);
    }
    public Color getColour(){
        return colour;
    }
}
