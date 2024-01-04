import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    @Test
    public void setCurrentColorTest() {
        Game game = new Game(1);
        Game.setCurrentColor(Colors.BLUE);
        assertEquals(game.getCurrentColor(), Colors.BLUE);

        Game.setCurrentColor(Colors.COLORLESS);
        assertEquals(game.getCurrentColor(), Colors.COLORLESS);
    }

    @Test
    public void resetMainAndPlayedPileTest() {
        Game game = new Game(1);
        game.resetMainAndPlayedPile();
        assertEquals(game.playedPile.size(), 1);
        assertEquals(game.mainPile.size(), 100);
    }

    @Test
    public void isValidCardTest() {
        assertTrue(Game.isValidCard(new WildCard(WildCardType.Wild), new NumberCard(Colors.BLUE, 0)));
        assertTrue(Game.isValidCard(new WildCard(WildCardType.DrawFour), new NumberCard(Colors.BLUE, 0)));
        assertTrue(Game.isValidCard(new NumberCard(Colors.BLUE, 8), new NumberCard(Colors.BLUE, 0)));
    }

    @Test
    public void isNotValidCardTest() {
        Game.setCurrentColor(Colors.BLUE);
        assertFalse(Game.isValidCard(new ActionCard(ActionType.Reverse, Colors.RED), new NumberCard(Colors.BLUE, 2)));
    }
}
