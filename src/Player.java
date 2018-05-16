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
    boolean pieceOnBoard = true;
    int numPiecesOffBoard;
    public Player(Color colourOfPieces){
        this.colourOfPieces = colourOfPieces;
        pieces = new Piece[15];
        set();
        numPiecesOffBoard = 0;

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
            if(getPieces()[i].getX() > 400 && getPieces()[i].getX() < 740){
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

    public void offBoardPiece(Piece piece){
        for(int i = 0; i < getPieces().length; i++){
            if (getPieces()[i] == piece){
                getPieces()[i].setOffBoard();
                numPiecesOffBoard++;
            }
        }
    }

    public boolean checkWin() {
        if(getNumPiecesOffBoard() == 15){
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

    public void set(){
        for(int i = 0; i < 15; i++){
            pieces[i] = new Piece(0, 0, pieceOnBoard, colourOfPieces);
        }

    }

    public boolean piecesAreClose(int diceFaceValue){
        boolean canGoOffBoard = true;
        for(int i = 0; i < 15; i++){
            if(getPieces()[i].getX() < 710 - (diceFaceValue*50) || getPieces()[i].getX() > 740){
                canGoOffBoard = false;
            }
        }
        return canGoOffBoard;
    }

    public int getNumPiecesOffBoard(){
        return  numPiecesOffBoard;
    }




}
