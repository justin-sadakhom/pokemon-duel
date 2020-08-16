package moves;

import elitefour.pokemonduel.battle.Pokemon;
import elitefour.pokemonduel.battle.Type;
import elitefour.pokemonduel.moves.DamageMove;
import elitefour.pokemonduel.moves.Move;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MoveTest {
    
    private Move testMove;
    
    @BeforeEach
    void setup() {
        // Initialize as DamageMove because Move is an abstract class.
        testMove = new DamageMove("Psychic");
    }
    
    @Test
    void name() {
        assertEquals("Psychic", testMove.name());
    }
    
    @Test
    void type() {
        assertEquals(Type.PSYCHIC, testMove.type());
    }
    
    @Test
    void category() {
        assertEquals(Move.Category.SPECIAL, testMove.category());
    }
    
    @Test
    void PP() {
        assertEquals(10, testMove.PP());
        assertEquals(10, testMove.maxPP());
    }
    
    @Test
    void deductPPNone() {
        testMove.deductPP(0);
        assertEquals(10, testMove.PP());
    }
    
    @Test
    void deductPPOne() {
        testMove.deductPP(1);
        assertEquals(9, testMove.PP());
        
        testMove.deductPP(10);
        assertEquals(0, testMove.PP());
    }
    
    @Test
    void deductPPAll() {
        testMove.deductPP(10);
        assertEquals(0, testMove.PP());
    }
    
    @Test
    void deductPPOverload() {
        testMove.deductPP(11);
        assertEquals(0, testMove.PP());
    }
    
    @Test
    void addPPNone() {
        testMove.addPP(0);
        assertEquals(10, testMove.PP());
    }
    
    @Test
    void addPPOne() {
        testMove.deductPP(1);
        testMove.addPP(1);
        assertEquals(10, testMove.PP());
    }
    
    @Test
    void addPPAll() {
        testMove.deductPP(10);
        testMove.addPP(10);
        assertEquals(10, testMove.PP());
    }
    
    @Test
    void addPPOverload() {
        testMove.addPP(1);
        assertEquals(10, testMove.PP());
    }
    
    @Test
    void power() {
        assertEquals(90, testMove.power());
    }
    
    @Test
    void accuracy() {
        assertEquals(100, testMove.accuracy());
    }
    
    @Test
    void goodMoveAccuracy() {
        Pokemon user = new Pokemon("Mew");
        Pokemon target = new Pokemon("Kangaskhan");
        assertEquals(true, testMove.isHit(user, target));
    }
    
    @Test
    void perfectMoveAccuracy() {
        Pokemon user = new Pokemon("Mew");
        Pokemon target = new Pokemon("Kangaskhan");
        
        testMove.setAccuracy(0); // This guarantees the move will always hit.
        assertEquals(true, testMove.isHit(user, target));
    }
    
    @Test
    void attemptText() {
        String result = testMove.attemptText("Mew");
        assertEquals("Mew used Psychic!", result);
    }
    
    @Test
    void missText() {
        String result = testMove.missText("Mew");
        assertEquals("Mew's attack missed!", result);
    }
    
    @Test
    void bothHighPriorityMoves() {
        Move one = new DamageMove("Quick Attack");
        Move two = new DamageMove("Quick Attack");
        String result = Move.comparePriority(one, two);
        assertEquals(true, result.equals("one") || result.equals("two"));
    }
    
    @Test
    void oneHigherPriority() {
        Move one = new DamageMove("Quick Attack");
        Move two = testMove;
        assertEquals("one", Move.comparePriority(one, two));
    }
    
    @Test
    void bothNoPriorityMove() {
        Move one = testMove;
        Move two = testMove;
        assertEquals("none", Move.comparePriority(one, two));
    }
}