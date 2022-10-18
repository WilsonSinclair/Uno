import java.util.Scanner;

public class Driver {

    public static void main(String[] args) {
        System.out.print("Number of Players: ");
        Scanner scanner = new Scanner(System.in);
        new Game(scanner.nextInt()).play();
        scanner.close();
    }
}
