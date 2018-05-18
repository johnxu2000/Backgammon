import java.awt.*;
/*
The player class controls the pieces. The player is able to place the pieces on the correct spots and check which moves they have made
in order for the Board class to draw them properly. The player is in charge of all the attributes that are individually important for
each player
 */
import java.util.Stack;

public class Player {
    Color colourOfPieces;
    Piece[] pieces; //holds all the pieces of a player
    Stack<Piece> hitPieces = new Stack<Piece>(); //hitPieces of a player is treated as a stack
    boolean pieceOnBoard = true;
    int numPiecesOffBoard;
    int score = 0;
    public Player(Color colourOfPieces){
        this.colourOfPieces = colourOfPieces;
        pieces = new Piece[15];
        set(); //sets up the game from the player's view
        numPiecesOffBoard = 0;

    }

    /*
    Checks if the moving piece is one of the player's pieces and if so it returns the Piece that is being moved
     */
    public Piece movePieceCheck(Piece piece){
        Piece movingPiece = null;
        for(int i = 0; i < getPieces().length; i++){
            if (getPieces()[i] == piece){
                movingPiece = getPieces()[i];
            }
        }
        return movingPiece;
    }

    //If all of the players pieces are in the home quarter of the board, then they can start moving their pieces off the board
    //This method checks this ability
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

    //adds a hit piece to the stack of hitPieces
    public void addHitPiece(Piece piece){
        hitPieces.push(piece);
    }

    //When finding the piece that goes offBoard, this method changes the status of the piece and adds 1 to the number of pieces off the board
    public void offBoardPiece(Piece piece){
        for(int i = 0; i < getPieces().length; i++){
            if (getPieces()[i] == piece){
                getPieces()[i].setOffBoard();
                numPiecesOffBoard++;
            }
        }
    }

    //This method checks to see if the player has won, depending on if all 15 of teh player's pieces are off the board
    public boolean checkWin() {
        if(getNumPiecesOffBoard() == 15){
            return true;
        }
        else{
            return false;
        }
    }

    //returns the stack containing the hit pieces
    public Stack<Piece> getHitPieces() {
        return hitPieces;
    }

    //Returns a boolean depending on whether the player has an pieces that were hit
    public boolean hasHitPiece(){
        if(hitPieces.size() != 0){
            return true;
        }
        else{
            return false;
        }
    }

    //returns array of all of the player's pieces
    public Piece[] getPieces(){
        return pieces;
    }

    //Resets the player's pieces on the board
    public void set(){
        for(int i = 0; i < 15; i++){
            pieces[i] = new Piece(pieceOnBoard, colourOfPieces);
        }

    }

    /*
   Checks to see if there are pieces that are lower than the lowest dice value. This way the player can start taking
   off pieces that are closer to the board because they don't have pieces that are farther away from the ending point
     */
    public boolean piecesAreClose(int diceFaceValue){
        boolean canGoOffBoard = true;
        for(int i = 0; i < 15; i++){
            if(getPieces()[i].getX() < 710 - (diceFaceValue*50) || getPieces()[i].getX() > 740){ //checks using coordinates of the pieces
                canGoOffBoard = false;
            }
        }
        return canGoOffBoard;
    }

    //returns number of pieces off the board
    public int getNumPiecesOffBoard(){
        return  numPiecesOffBoard;
    }

    //Sets the colour of the pieces if the user wants to change the colour
    public void setColourOfPieces(Color colour){
        for(int i = 0; i < getPieces().length; i++){
            getPieces()[i].setColour(colour);
        }
    }

    //returns the number of games won by the player
    public int getScore(){
        return score;
    }

    //adds a point to the number of games won by the player
    public void addPoint(){
        score++;
    }




}
