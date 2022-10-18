import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    @Test
    public void hasPlayableCardWhenFirstCardIsDrawFourTest() {
        Game game = new Game(1);
        Game.setCurrentColor(Colors.COLORLESS);
        assertTrue(Game.currentPlayer.hasPlayableCard(new WildCard(WildCardType.DrawFour), game));
    }

    @Test
    public void hasPlayableCardWhenFirstCardIsWildCardTest() {
        Game game = new Game(1);
        Game.setCurrentColor(Colors.COLORLESS);
        assertTrue(Game.currentPlayer.hasPlayableCard(new WildCard(WildCardType.Wild), game));
    }

    @Test
    public void populateHandTest() {
        Game game = new Game(2);
        Player player = game.curPlayerNode.player;
        Player nextPlayer = game.nextPlayerNode.player;

        assertEquals(7, player.getHand().size());
        assertEquals(7, nextPlayer.getHand().size());
    }

    @Test
    public void isValidColorTest() {
        Game game = new Game(1);
        Player player = game.curPlayerNode.player;

        assertTrue(player.isValidColor("BLUE"));
        assertTrue(player.isValidColor("red"));
        assertTrue(player.isValidColor("yeLLow"));
        assertTrue(player.isValidColor("gREeN"));
        assertTrue(player.isValidColor("REd"));
        assertFalse(player.isValidColor("Random"));
        assertFalse(player.isValidColor("greeeen"));
    }

    @Test
    public void drawFromMainPileTest() {
        Game game = new Game(1);
        game.curPlayerNode.player.drawFromMainPile(game.mainPile, 4);

        // one card is put into the played pile and seven more are given to the player's hand.
        assertEquals(96, game.mainPile.size());
    }
}