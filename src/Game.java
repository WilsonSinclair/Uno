import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.Stack;

public class Game {

    //The main pile cards are drawn from.
    public Stack<Card> mainPile;
    //The pile of cards that have been played already.
    public Stack<Card> playedPile;

    //The entire deck that is to be shuffled into the mainPile.
    public ArrayList<Card> cards;

    public Player[] players;
    public int currentPlayerIndex;
    public Player currentPlayer;
    public static Colors currentColor;

    public boolean winner = false;

    public Game(int numPlayers) {
        if (numPlayers <= 0) {
            System.out.println("Number of players must be equal to or greater than 1.");
            return;
        }
        cards = new ArrayList<>();
        mainPile = new Stack<>();
        playedPile = new Stack<>();

        //Creating the players.
        players = new Player[numPlayers];
        for (int i = 0; i < numPlayers; i++) {
            players[i] = new Player();
        }

        //Adding the 76 Number Cards (19 of each color)
        for (Colors color : Colors.values()) {
            if (color == Colors.COLORLESS) continue;
            cards.add(new NumberCard(color, 0));
            for (int i = 1; i <= 9; i++) {
                cards.add(new NumberCard(color, i));
                cards.add(new NumberCard(color, i));
            }
        }

        //Adding the 24 Action Cards.
        for (Colors color : Colors.values()) {
            if (color == Colors.COLORLESS) continue;
            for (ActionType type : ActionType.values()) {
                cards.add(new ActionCard(type, color));
                cards.add(new ActionCard(type, color));
            }
        }

        //Adding the 8 Wild Cards.
        for (WildCardType type : WildCardType.values()) {
            for (int i = 0; i < 4; i++) {
                cards.add(new WildCard(type));
            }
        }

        //Shuffle the playing cards.
        Collections.shuffle(cards);

        //Pushing the cards into the mainPile.
        for (Card card : cards) {
            mainPile.push(card);
        }

        //Initialize the players' hands.
        for (Player player : players) {
            player.populateHand(mainPile);
        }

        //Place one card in the played pile to start.
        playedPile.push(mainPile.pop());
        currentColor = playedPile.peek().getColor();

        currentPlayerIndex = 0;
        currentPlayer = players[currentPlayerIndex];
    }

    public Colors getCurrentColor() {
        return currentColor;
    }

    public void rotatePlayerTurn() {
        if (currentPlayerIndex == players.length - 1) {
            currentPlayerIndex = 0;
        }
        else {
            currentPlayerIndex++;
        }
        currentPlayer = players[currentPlayerIndex];
    }

    public static void setCurrentColor(Colors color) {
        currentColor = color;
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        Game game = new Game(1);
        Card currentCard;
        Player currentPlayer;
        Stack<Card> playedPile;
        ArrayList<Card> hand;
        while (!game.winner) {
            Runtime.getRuntime().exec("clear"); // clears the terminal in Linux
            currentCard = game.playedPile.peek();
            currentPlayer = game.currentPlayer;
            playedPile = game.playedPile;
            hand = currentPlayer.getHand();
            System.out.println("\nCurrent card: " + currentCard + "\nCurrent color: " + currentColor);

            if (!currentPlayer.hasPlayableCard(currentCard, game)) {
                System.out.println("Drawing a card...");
                currentPlayer.drawFromMainPile(game.mainPile);
                continue;
            }
            System.out.println("Current Player: " + currentPlayer);
            System.out.print("Enter index of card to play: ");
            currentPlayer.playCard(hand.get(scanner.nextInt()), playedPile);
        }
    }
}
