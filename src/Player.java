import java.awt.*;
import java.util.Stack;

public class Player {
    Color colourOfPieces;
    Stack[] stacks;
    public Player(Color colourOfPieces, int startingX, int startingY){
        this.colourOfPieces = colourOfPieces;
        stacks = new Stack[24];
        for(int i = 0; i<24; i++){
            stacks[i] = new Stack();
            stacks[i].push(new Piece(600, 400; colourOfPieces));
        }
    }

    public Stack[] getStacks(){
        return stacks;
    }




}
