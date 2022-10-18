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
    
}
