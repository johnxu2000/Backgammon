import java.util.Stack;
/*
(John did this class) - Storing the pieces onto the stacks for management.
 */
public class Stacks {
    Stack[] stacks = new Stack[24];
    public Stacks() {
        for (int i = 0; i < 24; i++) {
            stacks[i] = new Stack();
        }
    }
    public Stack[] getStacks(){
            return stacks;
    }
}

