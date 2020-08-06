package elitefour.pokemonduel;

import java.util.Random;

public class DamageDebuff extends DamageMove {
    
    private final Pokemon.Stat affectedStat;
    private final int effectChance, stages;
    
    public DamageDebuff(String name, Type type, Category category, int pp,
            int power, int accuracy, Pokemon.Stat stat, int chance, int stage) {
        
        super(name, type, category, pp, power, accuracy);
        this.affectedStat = stat;
        this.effectChance = chance;
        this.stages = stage;
    }
    
    @Override
    public int use(Pokemon user, Pokemon target) {
        return super.use(user, target);
    }
    
    public boolean useSecondary(Pokemon user, Pokemon target) {
        
        Random rng = new Random();
        boolean result = false;
        
        if (rng.nextInt(100) < effectChance)
            result = applyEffect(target);
        
        return result;
    }
    
    private boolean applyEffect(Pokemon target) {
        return target.lowerStatStage(affectedStat, stages);
    }
    
    public int stages() {
        return stages;
    }
    
    public String affectedStat() {
        return affectedStat.name();
    }
    
    public static String hitText(String name, DamageDebuff move, boolean success) {
        
        String message = name + "'s " + move.affectedStat().toLowerCase();
        
        // Affected stat stage is already at min.
        if (!success)
            return message + " won't go any lower!";

        // There's still room for change.
        else {

            switch (move.stages()) {
                case -1:
                    return message + " fell!";
                default: // case -2
                    return message + " fell sharply!";
            }
        }
    }
}
