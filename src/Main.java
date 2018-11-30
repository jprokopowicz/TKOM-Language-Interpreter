import com.lexer.Lexer;
import com.lexer.StreamReader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) {
        if(args.length == 1){
            try {
                FileInputStream inputStream = new FileInputStream(args[0]);
                Lexer lexer = new Lexer(new StreamReader(inputStream));
                //Lexer operations
            } catch (FileNotFoundException exc){
                System.out.println("No such file or invalid argument: " + exc.getMessage());
            } catch (Exception exc){
                System.out.println("Other exception: "+ exc.getMessage());
            }
        } else {
            System.out.println("No given file");
        }
    }
}
