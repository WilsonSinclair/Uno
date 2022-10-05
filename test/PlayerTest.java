import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    @Test
    public void hasPlayableCardWhenFirstCardIsDrawFourTest() {
        Game game = new Game(1);
        game.setCurrentColor(Colors.COLORLESS);
        assertTrue(game.currentPlayer.hasPlayableCard(new WildCard(WildCardType.DrawFour), game));
    }

    @Test
    public void hasPlayableCardWhenFirstCardIsWildCardTest() {
        Game game = new Game(1);
        game.setCurrentColor(Colors.COLORLESS);
        assertTrue(game.currentPlayer.hasPlayableCard(new WildCard(WildCardType.Wild), game));
    }
}