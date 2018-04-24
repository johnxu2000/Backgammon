import java.util.Stack;

public class GameLogic {
    Stack[] stacks = new Stack[24];
    Boolean victory = false;
    public GameLogic() {
        for (int i = 0; i < 24; i++) {
            stacks[i] = new Stack();
        }
    }
    public Stack[] getStacks(){
            return stacks;
    }
    public void CheckPlayerPriority(){
    }
    public void CheckBoard(){
        while(victory = false){
            for (int i = 0; i > stacks.length; i++){
            }
        }
    }
}

