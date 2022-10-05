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

    //Player array
    public Player[] players;

    //Index of current player in player array
    public int currentPlayerIndex;

    //The current and next player
    public static Player currentPlayer, nextPlayer;

    //Game's current color
    public static Colors currentColor;

    //Keeps track of whether there is a winner
    public boolean winner = false;

    //Decides whether turns are reversed or not
    public boolean turnsReversed = false;

    //Colors for console output
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_GREEN = "\u001B[32m";

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
            players[i] = new Player(this, "Player " + (i + 1));
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

        //Initialize the game's current color
        currentColor = playedPile.peek().getColor();

        //Initialize the first player's turn
        currentPlayerIndex = 0;
        rotatePlayerTurn();
    }

    public Colors getCurrentColor() {
        return currentColor;
    }

    public void rotatePlayerTurn() {
        // Treating the players array as a circular array.
        if (!turnsReversed) {
            currentPlayer = players[currentPlayerIndex++ % players.length];
        }
        else {
            currentPlayer = players[currentPlayerIndex-- % players.length];
        }
        nextPlayer = players[currentPlayerIndex % players.length];
    }

    public void reverseTurnOrder() {
        turnsReversed = !turnsReversed;
    }

    public boolean checkForWinner(Player currentPlayer) {
        return currentPlayer.getHand().isEmpty();
    }

    public static void setCurrentColor(Colors color) {
        currentColor = color;
    }

    public static void main(String[] args)  {
        Scanner scanner = new Scanner(System.in);
        Game game = new Game(1);
        Card currentCard;
        Stack<Card> playedPile;
        ArrayList<Card> hand;
        int choice;
        while (!game.winner) {
            currentCard = game.playedPile.peek();
            playedPile = game.playedPile;
            hand = currentPlayer.getHand();
            System.out.println("\nCurrent card: " + currentCard + "\nCurrent color: " + currentColor);

            if (!currentPlayer.hasPlayableCard(currentCard, game)) {
                System.out.println(currentPlayer.getName() + " has to draw a card.");
                currentPlayer.drawFromMainPile(game.mainPile, 1);
                game.rotatePlayerTurn();
                continue;
            }
            System.out.println("Current Player: " + currentPlayer);
            System.out.println("Next Player: " + nextPlayer.getName());
            do {
                System.out.print("Enter index of card to play: ");
                choice = scanner.nextInt();
            } while (choice < 0 || choice >= currentPlayer.getHand().size());
            currentPlayer.playCard(hand.get(choice), playedPile);
            game.winner = game.checkForWinner(currentPlayer);
            if (game.winner) {
                System.out.println(currentPlayer.getName() + " wins!");
                System.exit(0);
            }
            game.rotatePlayerTurn();
        }
    }
}
