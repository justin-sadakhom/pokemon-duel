package moves;

import elitefour.pokemonduel.moves.MultiHitMove;
import elitefour.pokemonduel.battle.Pokemon;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MultiHitMoveTest {
    
    private Pokemon attacker;
    private Pokemon defender;
    
    @BeforeEach
    void setup() {
        attacker = new Pokemon("Mew");
        defender = new Pokemon("Kangaskhan");
    }
    
    @Test
    void minTwoMaxTwo() {
        attacker.setMove(new MultiHitMove("Bonemerang"), 0);
        int hits = ((MultiHitMove)(attacker.moves(0))).hits();
        assertEquals(2, hits);
    }
    
    @Test
    void minTwoMaxFive() {
        attacker.setMove(new MultiHitMove("Pin Missile"), 0);
        int hits = ((MultiHitMove)(attacker.moves(0))).hits();
        assertEquals(true, 2 <= hits && hits <= 5);
    }
    
    @Test void battleText() {
        attacker.setMove(new MultiHitMove("Bonemerang"), 0);
        int hits = ((MultiHitMove)(attacker.moves(0))).hits();
        String result = MultiHitMove.hitText(hits);
        assertEquals("Hit 2 times!", result);
    }
}