import java.awt.*;
/*
John - This class's purpose is to create the pieces objects that the player, and board need in order to play the game.
The pieces's width and height are fixed values and there are only two different colours of the pieces.
The piece class itself can show the status of a piece if it got off the board.
 */

public class Piece {
    int x = 0;
    int y = 0;
    boolean status; //If the piece is off the board
    Color colour;

    public Piece(boolean onBoard, Color colour){
        this.status = onBoard;
        this.colour = colour;
    }
    public void draw(Graphics g){
        if(onBoard()) {
            g.setColor(colour);
            g.fillOval(x, y, 30, 30);
        }
    }

    public boolean onBoard(){
        return status;
    }

    public void setOffBoard(){
        status = false;
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
    public int getY(){ return y; }

    public Color getColour() {
        return colour;
    }

    public void setColour(Color pieceColours){
        colour = pieceColours;
    }
}
