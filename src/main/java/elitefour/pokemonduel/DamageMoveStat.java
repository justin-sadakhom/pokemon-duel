package elitefour.pokemonduel;

import java.util.Random;

public class DamageMoveStat extends DamageMove {
    
    private final Pokemon.Stat affectedStat;
    private final int effectChance, stages;
    
    public DamageMoveStat(String name, Type type, Category category, int pp,
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
        
        if (rng.nextInt(effectChance) < 100)
            result = applyEffect(target);
        
        return result;
    }
    
    private boolean applyEffect(Pokemon target) {
        
        if (stages > 0)
            return target.raiseStatStage(affectedStat, stages);
        else
            return target.lowerStatStage(affectedStat, stages);
    }
    
    public int stages() {
        return stages;
    }
    
    public String affectedStat() {
        return affectedStat.name();
    }
    
    public static String text(String name, DamageMoveStat move, boolean success) {
        
        String message = name + "'s " + move.affectedStat().toLowerCase();
        
        // Affected stat stage is already at max / min.
        if (!success) {

            if (move.stages() > 0)
                return message + " won't go any higher!";
            else
                return message + " won't go any lower!";
        }

        // There's still room for change.
        else {

            switch (move.stages()) {
                case 1:
                    return message + " rose!";
                case 2:
                    return message + " rose sharply!";
                case -1:
                    return message + " fell!";
                default: // case -2
                    return message + " fell sharply!";
            }
        }
    }
}
