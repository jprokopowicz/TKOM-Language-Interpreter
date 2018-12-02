import com.lexer.Lexer;
import com.lexer.StreamReader;
import com.lexer.Token;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) {
        if(args.length == 1){
            try {
                FileInputStream inputStream = new FileInputStream(args[0]);
                Lexer lexer = new Lexer(new StreamReader(inputStream));
                //Lexer operations
                System.out.println("start");
                while(lexer.getToken().getType() != Token.Type.end_of_bytes_){
                    Token token = lexer.readNextToken();
                    System.out.println(token.getPosition().sign + "\t" + token.getPosition().line + "\t" + Token.tokenNames.get(token.getType()) + ":\t\t" +  token.getValue());
                }
                System.out.println("end");
            } catch (FileNotFoundException exc){
                System.out.println("No such file or invalid argument: " + exc.getMessage());
            } catch (Exception exc){
                System.out.println("Other exception: "+ exc.getMessage());
                exc.printStackTrace();
            }
        } else if(args.length == 0){
            System.out.println("No given file");
        } else {
            System.out.println("Too many arguments");
        }
    }
}
