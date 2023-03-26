package input;
import java.util.Scanner;
public class Input {

    Scanner sc = new Scanner(System.in);
    public int intInput(){
        int inp;
        inp = sc.nextInt();
        return inp;
    }
    public String strInput(){
        String inp;
        inp = sc.nextLine();
        return inp;
    }
}