import java.awt.*;
import java.util.Stack;

public class Player {
    Color colourOfPieces;
    Piece [] pieces;
    public Player(Color colourOfPieces, int startingX, int startingY){
        this.colourOfPieces = colourOfPieces;
        pieces = new Piece[15];
        for(int i = 0; i<pieces.length; i++){
            pieces[i] = new Piece(startingX, startingY, colourOfPieces);
        }

    }

    public Piece[] getPieces(){
        return pieces;
    }




}
