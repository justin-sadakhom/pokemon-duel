package elitefour.pokemonduel;

import java.util.Random;

public class MultiHitMove extends DamageMove {
    
    public MultiHitMove(String name, Type type, Category category,
            int pp, int power, int accuracy) {
        
        super(name, type, category, pp, power, accuracy);
    }
    
    @Override
    public int use(Pokemon user, Pokemon target) {
        deductPP(1);
        return target.deductHealth(damage(user, target));
    }
    
    public int hits() {
        Random rng = new Random();
        int hits = rng.nextInt(4) + 2; // Generates number between 2-5.
        return hits;
    }
    
    public static String hitText(int hits) {
        return "Hit " + hits + " times!";
    }
}