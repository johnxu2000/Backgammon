import java.awt.*;
/*
(John did this class) - This class's purpose is to create the pieces objects that the player, and board need in order to play the game.
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

    //draws the piece
    public void draw(Graphics g){
        if(onBoard()) {
            g.setColor(colour);
            g.fillOval(x, y, 30, 30);
        }
    }

    //Returns if the piece is on the board
    public boolean onBoard(){
        return status;
    }

    //sets the on/off board status of the piece
    public void setOffBoard(){
        status = false;
    }

    //Sets the x coordinate when drawing the piece
    public void setX(int x) {
        this.x = x;
    }
    //Sets the y coordinate when drawing the piece
    public void setY(int y){
        this.y = y;
    }

    //gets the x coordinate of the piece
    public int getX(){
        return x;
    }

    //gets the y coordinate of the piece
    public int getY(){ return y; }

    //Returns the colour of the piece
    public Color getColour() {
        return colour;
    }

    //sets the colour of the piece is the player changed the colour
    public void setColour(Color pieceColours){
        colour = pieceColours;
    }
}
