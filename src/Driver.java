import java.util.Scanner;

public class Driver {

    public static void main(String[] args) {
        System.out.print("Number of Players: ");
        new Game(new Scanner(System.in).nextInt()).play();
    }
}
