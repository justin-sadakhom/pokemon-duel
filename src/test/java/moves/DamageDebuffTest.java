package moves;

import elitefour.pokemonduel.DamageDebuff;
import elitefour.pokemonduel.Move;
import elitefour.pokemonduel.Pokemon;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DamageDebuffTest {
    
    private final Move psychic = new DamageDebuff("Psychic");
    private Pokemon attacker;
    private Pokemon defender;
    
    @BeforeEach
    void setup() {
        attacker = new Pokemon("Mew");
        defender = new Pokemon("Kangaskhan");
    }
    
    @Test
    void attackDebuff() {
        attacker.setMove(new DamageDebuff("Aurora Beam"), 0);
        
        boolean success = ((DamageDebuff)(attacker.moves(0))).
                useSecondary(attacker, defender);
        
        if (success)
            assertEquals(-1, defender.statStage(Pokemon.Stat.ATTACK));
        else
            assertEquals(0, defender.statStage(Pokemon.Stat.ATTACK));
    }
    
    @Test
    void specialDefenseDebuff() {
        attacker.setMove(psychic, 0);
        
        boolean success = ((DamageDebuff)(attacker.moves(0))).
                useSecondary(attacker, defender);
        
        if (success)
            assertEquals(-1, defender.statStage(Pokemon.Stat.SPECIAL_DEFENSE));
        else
            assertEquals(0, defender.statStage(Pokemon.Stat.SPECIAL_DEFENSE));
    }
    
    @Test
    void speedDebuff() {
        attacker.setMove(new DamageDebuff("Bubble Beam"), 0);
        
        boolean success = ((DamageDebuff)(attacker.moves(0))).
                useSecondary(attacker, defender);
        
        if (success)
            assertEquals(-1, defender.statStage(Pokemon.Stat.SPEED));
        else
            assertEquals(0, defender.statStage(Pokemon.Stat.SPEED));
    }
    
    @Test
    void battleTextSuccess() {
        attacker.setMove(psychic, 0);
        
        String result = ((DamageDebuff)(attacker.moves(0))).
                hitText(defender.name(), true);
        
        assertEquals("Kangaskhan's special defense fell!", result);
    }
    
    @Test
    void battleTextFailure() {
        attacker.setMove(psychic, 0);

        String result = ((DamageDebuff)(attacker.moves(0))).
                hitText(defender.name(), false);

        assertEquals(
            "Kangaskhan's special defense won't go any lower!",
            result
        );
    }
}