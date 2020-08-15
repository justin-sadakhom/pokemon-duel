package elitefour.pokemonduel.moves;

import elitefour.pokemonduel.Pokemon;
import elitefour.pokemonduel.Type;
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
        
        // Generate number of turns that the user is idle.
        if (idleTurns == 0) {
            idleTurns = 2;
            turnsLeft = idleTurns;
        }
        
        // Keep track of how long until user can move again.
        else if (turnsLeft > 0)
            turnsLeft -= 1;
        
        // When user is no longer idle...
        if (turnsLeft == 0) {
            idleTurns = 0;
            return target.deductHealth(damage(user, target));
        }
        
        // Still idle turns left.
        return -1; 
    }
    
    @Override
    protected int damage(Pokemon user, Pokemon target) {
        int damage = user.damageTaken(0) + user.damageTaken(1);
        return (int)(REFLECT_MULTIPLIER * damage * 
                typeAdvantage(type(), target.type()));
    }
    
    public static double typeAdvantage(Type moveType, Type[] targetType) {
        
        if (Arrays.asList(targetType).contains(Type.GHOST))
            return 0;
        else
            return 1;
    }
    
    public static String chargeText(String user) {
        return user + " is charging energy!";
    }
    
    public static String hitText(String user) {
        return user + " unleashed energy!";
    }
}