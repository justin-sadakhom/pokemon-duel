package moves;

import elitefour.pokemonduel.DrainMove;
import elitefour.pokemonduel.Pokemon;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DrainMoveTest {
    
    private Pokemon attacker;
    private Pokemon defender;
    
    @BeforeEach
    void setup() {
        attacker = new Pokemon("Mew");
        attacker.setMove(new DrainMove("Mega Drain"), 0);
        defender = new Pokemon("Kangaskhan");
    }
    
    @Test
    void basicDrain() {
        attacker.deductHealth(100);
        
        int damage = attacker.useMove(0, defender);
        assertEquals(true, damage >= 34 && damage <= 41);
        assertEquals(defender.maxHealth() - defender.currentHealth(), damage);
        
        ((DrainMove)(attacker.moves(0))).useSecondary(attacker, damage);
        assertEquals(
            attacker.maxHealth() - 100 + (damage / 2),
            attacker.currentHealth()
        );
    }
    
    @Test
    void minDamageDrain() {
        attacker.deductHealth(1);
        defender.deductHealth(371); // Left at 1 health.
        
        int damage = attacker.useMove(0, defender);
        assertEquals(1, damage);
        assertEquals(defender.isFainted(), true);
        
        ((DrainMove)(attacker.moves(0))).useSecondary(attacker, damage);
        assertEquals(attacker.maxHealth(), attacker.currentHealth());
    }
    
    @Test void battleText() {
        String result = DrainMove.hitText(defender.name());
        assertEquals(defender.name() + " had its energy drained!", result);
    }
}