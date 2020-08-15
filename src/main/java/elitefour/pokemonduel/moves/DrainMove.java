package elitefour.pokemonduel.moves;

import elitefour.pokemonduel.Pokemon;

public class DrainMove extends DamageMove {
    
    public DrainMove(String name) {
        super(name);
    }
    
    public void useSecondary(Pokemon user, int damage) {
        if (damage / 2 < 1)
            user.restoreHealth(1);
        else
            user.restoreHealth(damage / 2);
    }
    
    public static String hitText(String name) {
        return name + " had its energy drained!";
    }
}
