import java.awt.*;
/*
The player class controls the pieces. The player is able to place the pieces on the correct spots and check which moves they have made
in order for the Board class to draw them properly.
 */

public class Player {
    Color colourOfPieces;
    Piece[] pieces;
    boolean pieceOnBoard = true;
    public Player(Color colourOfPieces, int startingX, int startingY){
        this.colourOfPieces = colourOfPieces;
        pieces = new Piece[15];
        for(int i = 0; i < 15; i++){
            if(i == 2){
                startingX -= 550;
            }
            if(i == 7){
                startingX += 200;
            }
            if(i == 10){
                startingX += 100;
            }
            pieces[i] = new Piece(startingX, startingY, i, pieceOnBoard, colourOfPieces);
        }

    }

    public Piece movePieceCheck(Piece piece){
        Piece movingPiece = null;
        for(int i = 0; i < getPieces().length; i++){
            if (getPieces()[i] == piece){
                movingPiece = getPieces()[i];
            }
        }
        return movingPiece;
    }


    public Piece[] getPieces(){
        return pieces;
    }




}
