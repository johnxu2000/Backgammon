import java.awt.*;

public class Player {
    Color colourOfPieces;
    Piece[] pieces;
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
            pieces[i] = new Piece(startingX, startingY, i, colourOfPieces);
        }

    }

    public void movePiece(int pieceInitialSpot, int pieceFinalSpot, Piece piece){
        piece.setX(piece.getX() + ((pieceFinalSpot - pieceInitialSpot) * -50));
    }

    public Piece[] getPieces(){
        return pieces;
    }




}
