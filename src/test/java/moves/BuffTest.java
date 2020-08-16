package moves;

import elitefour.pokemonduel.moves.Buff;
import elitefour.pokemonduel.battle.Pokemon;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BuffTest {
    
    private Pokemon attacker;
    
    @BeforeEach
    void setup() {
        attacker = new Pokemon("Mew");
    }
    
    @Test
    void attackBuffOneStage() {
        attacker.setMove(new Buff("Sharpen"), 0);
        attacker.useMove(0, attacker);
        assertEquals(1, attacker.statStage(Pokemon.Stat.ATTACK));
    }
    
    @Test
    void attackBuffTwoStages() {
        attacker.setMove(new Buff("Swords Dance"), 0);
        attacker.useMove(0, attacker);
        assertEquals(2, attacker.statStage(Pokemon.Stat.ATTACK));
    }
    
    @Test
    void defenseBuffOneStage() {
        attacker.setMove(new Buff("Defense Curl"), 0);
        attacker.useMove(0, attacker);
        assertEquals(1, attacker.statStage(Pokemon.Stat.DEFENSE));
    }
    
    @Test
    void defenseBuffTwoStages() {
        attacker.setMove(new Buff("Barrier"), 0);
        attacker.useMove(0, attacker);
        assertEquals(2, attacker.statStage(Pokemon.Stat.DEFENSE));
    }
    
    @Test
    void specialDefenseBuff() {
        attacker.setMove(new Buff("Amnesia"), 0);
        attacker.useMove(0, attacker);
        assertEquals(2, attacker.statStage(Pokemon.Stat.SPECIAL_DEFENSE));
    }
    
    @Test
    void speedBuff() {
        attacker.setMove(new Buff("Agility"), 0);
        attacker.useMove(0, attacker);
        assertEquals(2, attacker.statStage(Pokemon.Stat.SPEED));
    }
    
    @Test
    void growth() {
        attacker.setMove(new Buff("Growth"), 0);
        attacker.useMove(0, attacker);
        assertEquals(1, attacker.statStage(Pokemon.Stat.ATTACK));
        assertEquals(1, attacker.statStage(Pokemon.Stat.SPECIAL_ATTACK));
    }
    
    @Test
    void battleTextSuccessOneStage() {
        attacker.setMove(new Buff("Sharpen"), 0);
        attacker.useMove(0, attacker);
        
        ArrayList<Boolean> success = new ArrayList<>();
        success.add(Boolean.TRUE);
        
        ArrayList<String> result = 
                ((Buff)(attacker.moves(0))).hitText(attacker.name(), success);
        
        ArrayList<String> expected = new ArrayList<>();
        expected.add("Mew's attack rose!");
        
        assertEquals(expected, result);
    }
    
    @Test
    void battleTextSuccessTwoStages() {
        attacker.setMove(new Buff("Swords Dance"), 0);
        
        ArrayList<Boolean> success = new ArrayList<>();
        success.add(Boolean.TRUE);
        
        ArrayList<String> result = 
                ((Buff)(attacker.moves(0))).hitText(attacker.name(), success);
        
        ArrayList<String> expected = new ArrayList<>();
        expected.add("Mew's attack rose sharply!");
        
        assertEquals(expected, result);
    }
    
    @Test
    void battleTextOneFailure() {
        attacker.setMove(new Buff("Swords Dance"), 0);
        
        ArrayList<Boolean> success = new ArrayList<>();
        success.add(Boolean.FALSE);
        
        ArrayList<String> result = 
                ((Buff)(attacker.moves(0))).hitText(attacker.name(), success);
        
        ArrayList<String> expected = new ArrayList<>();
        expected.add("Mew's attack won't go any higher!");
        
        assertEquals(expected, result);
    }
    
    @Test
    void battleTextBothSuccesses() {
        attacker.setMove(new Buff("Growth"), 0);
        
        ArrayList<Boolean> success = new ArrayList<>();
        success.add(Boolean.TRUE);
        success.add(Boolean.TRUE);
        
        ArrayList<String> result = 
                ((Buff)(attacker.moves(0))).hitText(attacker.name(), success);
        
        ArrayList<String> expected = new ArrayList<>();
        expected.add("Mew's attack rose!");
        expected.add("Mew's special attack rose!");
        
        assertEquals(expected, result);
    }
    
    @Test
    void battleTextFirstSuccessSecondFailure() {
        attacker.setMove(new Buff("Growth"), 0);
        
        ArrayList<Boolean> success = new ArrayList<>();
        success.add(Boolean.TRUE);
        success.add(Boolean.FALSE);
        
        ArrayList<String> result = 
                ((Buff)(attacker.moves(0))).hitText(attacker.name(), success);
        
        ArrayList<String> expected = new ArrayList<>();
        expected.add("Mew's attack rose!");
        expected.add("Mew's special attack won't go any higher!");
        
        assertEquals(expected, result);
    }
    
    @Test
    void battleTextFirstFailureSecondSuccess() {
        attacker.setMove(new Buff("Growth"), 0);
        
        ArrayList<Boolean> success = new ArrayList<>();
        success.add(Boolean.FALSE);
        success.add(Boolean.TRUE);
        
        ArrayList<String> result = 
                ((Buff)(attacker.moves(0))).hitText(attacker.name(), success);
        
        ArrayList<String> expected = new ArrayList<>();
        expected.add("Mew's attack won't go any higher!");
        expected.add("Mew's special attack rose!");
        
        assertEquals(expected, result);
    }
    
    @Test
    void battleTextBothFailures() {
        attacker.setMove(new Buff("Growth"), 0);
        
        ArrayList<Boolean> success = new ArrayList<>();
        success.add(Boolean.FALSE);
        success.add(Boolean.FALSE);
        
        ArrayList<String> result = 
                ((Buff)(attacker.moves(0))).hitText(attacker.name(), success);
        
        ArrayList<String> expected = new ArrayList<>();
        expected.add("Mew's attack won't go any higher!");
        expected.add("Mew's special attack won't go any higher!");
        
        assertEquals(expected, result);
    }
}