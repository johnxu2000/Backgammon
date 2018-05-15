import java.awt.*;
/*
The player class controls the pieces. The player is able to place the pieces on the correct spots and check which moves they have made
in order for the Board class to draw them properly.
 */
import java.util.ArrayList;
import java.util.Stack;

public class Player {
    Color colourOfPieces;
    Piece[] pieces;
    Stack<Piece> hitPieces = new Stack<Piece>();
    ArrayList<Piece> offBoardPieces = new ArrayList<Piece>();
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

    public boolean piecesAreHome(){
        int numPiecesHome = 0;
        for(int i = 0; i < 15; i++){
            if(getPieces()[i].getX() > 400 && getPieces()[i].getX() < 750){
                numPiecesHome++;
            }
        }
        if(numPiecesHome == 15){
            return true;
        }
        else{
            return false;
        }
    }

    public void addHitPiece(Piece piece){
        hitPieces.push(piece);
    }

    public void addOffBoardPiece(Piece piece){
        for(int i = 0; i < getPieces().length; i++){
            if (getPieces()[i] == piece){
                getPieces()[i].setOffBoard();
            }
        }
    }

    public boolean checkWin() {
        int numPiecesOffBoard = 0;
        for (int i = 0; i < 15; i++) {
            if (getPieces()[i].onBoard()) {
                numPiecesOffBoard++;
            }
        }
        if(numPiecesOffBoard == 15){
            return true;
        }
        else{
            return false;
        }
    }


    public Stack<Piece> getHitPieces() {
        return hitPieces;
    }


    public boolean hasHitPiece(){
        if(hitPieces.size() != 0){
            return true;
        }
        else{
            return false;
        }
    }

    public Piece[] getPieces(){
        return pieces;
    }




}
