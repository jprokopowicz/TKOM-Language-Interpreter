import com.lexer.Position;
import com.lexer.Token;

public class Main {

    public static void main(String[] args) {
        try {
            Token token = new Token(Token.Type.invalid_, "invalid", new Position(2, 3));
            token.getPosition().print();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
