package elitefour.pokemonduel.moves;

import elitefour.pokemonduel.battle.Pokemon;
import elitefour.pokemonduel.battle.Type;
import java.util.Arrays;

public class DamageReflect extends DamageMove {
    
    private final double REFLECT_MULTIPLIER = 2.0;
    private int idleTurns;
    private int turnsLeft;
    
    public DamageReflect(String move) {
        super(move);
        idleTurns = 0;
        turnsLeft = -1;
    }
    
    @Override
    public int use(Pokemon user, Pokemon target) {
        
        // When user is no longer idle, reflect damage.
        if (turnsLeft == 0) {
            idleTurns = 0;
            return target.deductHealth(damage(user, target));
        }
        
        // Generate number of turns that the user is idle.
        if (idleTurns == 0) {
            
            switch (name()) {
                case "Bide":
                    idleTurns = 2;
                case "Counter":
                    idleTurns = 1;
            }
            
            turnsLeft = idleTurns;
        }

        turnsLeft -= 1;
        
        // Still idle turns left.
        return -1; 
    }
    
    @Override
    protected int damage(Pokemon user, Pokemon target) {
        int damage = user.damageTaken(0) + user.damageTaken(1);
        return (int)(REFLECT_MULTIPLIER * damage * typeAdvantage(type(), target.type()));
    }
    
    public static double typeAdvantage(Type moveType, Type[] targetType) {
        
        if (Arrays.asList(targetType).contains(Type.GHOST))
            return 0;
        else
            return 1;
    }
    
    public boolean isCharging() {
        return turnsLeft == 1;
    }
    
    public boolean firstUse() {
        return turnsLeft == -1;
    }
    
    public static String chargeText(String user) {
        return user + " is charging energy!";
    }
    
    public static String hitText(String user) {
        return user + " unleashed energy!";
    }
}