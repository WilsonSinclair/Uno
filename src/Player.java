import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import java.util.Stack;

public class Player {

    //The player's hand
    private ArrayList<Card> hand;

    //The player's in game name
    private final String NAME;

    //Instance of the current game in progress
    public Game game;

    public Player(Game game, String name) {
        hand = new ArrayList<>();
        this.game = game;
        NAME = name;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }
    public String getName() { return NAME; }

    public void populateHand(Stack<Card> pile) {
        // 7 cards for each player
        for (int i = 0; i < 7; i++) {
            hand.add(pile.pop());
        }
    }

    public void playCard(Card card, Stack<Card> playedPile) {
        if (playedPile.isEmpty()) return;
        if (card.getWildCardType() == WildCardType.Wild || card.getWildCardType() == WildCardType.DrawFour) {
            playedPile.push(card);
            System.out.println("New color? [Red, Yellow, Blue, Green]: ");
            String color;

            do {
                Scanner scanner = new Scanner(System.in);
                color = scanner.nextLine().toUpperCase();
                switch (color) {
                    case "RED" -> Game.setCurrentColor(Colors.RED);
                    case "YELLOW" -> Game.setCurrentColor(Colors.YELLOW);
                    case "BLUE" -> Game.setCurrentColor(Colors.BLUE);
                    case "GREEN" -> Game.setCurrentColor(Colors.GREEN);
                    default -> System.out.println("Invalid color! Choose again!");
                }
            } while(!isValidColor(color));

            if (card.getWildCardType() == WildCardType.DrawFour) {
                System.out.println(game.curPlayerNode.player.getName() + " draws 4 cards.");
                Game.nextPlayer.drawFromMainPile(game.mainPile, 4);
                game.rotatePlayerTurn();
            }
        }
        else if (card.getActionType() != null) {
            assert card.getActionType().equals(playedPile.peek().getActionType());
            switch (card.getActionType()) {
                case DrawTwo -> {
                    playedPile.push(card);
                    Game.setCurrentColor(card.getColor());
                    System.out.println(Game.nextPlayer.getName() + " draws 2 cards...");
                    Game.nextPlayer.drawFromMainPile(game.mainPile, 2);
                    game.rotatePlayerTurn();
                }
                case Reverse -> {
                    playedPile.push(card);
                    Game.setCurrentColor(card.getColor());
                    if (game.players.getSize() > 2) {
                        game.reverseTurnOrder();
                    }
                    else {
                        game.rotatePlayerTurn();
                    }
                }
                case Skip -> {
                    playedPile.push(card);
                    Game.setCurrentColor(card.getColor());
                    game.rotatePlayerTurn();
                }
                default -> System.out.println("Error! Unrecognized ActionType.");
            }
        }
        else if (card.getColor() == game.getCurrentColor() || game.getCurrentColor() == Colors.COLORLESS) {
            playedPile.push(card);
            Game.setCurrentColor(card.getColor());
        }
        else if (card.getNumber().equals(game.playedPile.peek().getNumber())) {
            playedPile.push(card);
            Game.setCurrentColor(card.getColor());
        }
        hand.remove(card);
    }

    public boolean hasPlayableCard(Card card, Game game) {
        //If the first card in the game is a wild card or draw 4.
        if (game.getCurrentColor() == Colors.COLORLESS) { return true; }
        Colors color = game.getCurrentColor();
        ActionType type = card.getActionType();
        Integer number = card.getNumber();
        for (Card c : hand) {
            if (c.getClass() != ActionCard.class && c.getClass() != WildCard.class) {
                if (c.getColor() == color || Objects.equals(c.getNumber(), number)) return true;
            }
            if (c.getClass() == ActionCard.class) {
                if (c.getColor() == color || c.getActionType() == type) return true;
            }
            if (c.getClass() == WildCard.class) {
               if (c.getColor() == game.getCurrentColor() || c.getWildCardType() != null) return true;
            }
        }
        return false;
    }

    public boolean isValidColor(String color) {
       return color.equalsIgnoreCase("RED")
               || color.equalsIgnoreCase("GREEN")
               || color.equalsIgnoreCase("BLUE")
               || color.equalsIgnoreCase("YELLOW");
    }

    public void drawFromMainPile(Stack<Card> mainPile, int numberToDraw) {
        if (game.mainPile.size() < numberToDraw) {
            game.resetMainAndPlayedPile();
        }

        while (numberToDraw != 0) {
            hand.add(mainPile.pop());
            numberToDraw--;
        }
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        int index = 0;

        builder.append(getName()).append(": [ ");
        for (Card card : hand) {
            switch (card.getColor()) {
                case RED -> {
                    builder.append(Game.ANSI_RED).append(card).append(Game.ANSI_RESET);
                }
                case GREEN -> {
                    builder.append(Game.ANSI_GREEN).append(card).append(Game.ANSI_RESET);
                }
                case BLUE -> {
                    builder.append(Game.ANSI_BLUE).append(card).append(Game.ANSI_RESET);
                }
                case YELLOW -> {
                    builder.append(Game.ANSI_YELLOW).append(card).append(Game.ANSI_RESET);
                }
                default -> builder.append(card);
            }
            builder.append(" (");
            builder.append(index++);
            builder.append(") ");
        }
        builder.append("]");
        return builder.toString();
    }
}
