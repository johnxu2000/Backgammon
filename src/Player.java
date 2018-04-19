import java.awt.*;

public class Player {
    Color colourOfPieces;
    public Player(Color colourOfPieces){
        this.colourOfPieces = colourOfPieces;
        colourOfPieces = Color.blue;
        Object [] pieces = new Object[15];
        for(int i = 0; i<5; i++){
            pieces [i] = new Piece(300,30,colourOfPieces);
        }
    }

}
