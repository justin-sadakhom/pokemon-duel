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
    public Object[] use(Pokemon user, Pokemon target) {
        
        Object results[] = super.use(user, target);
        Random rng = new Random();
        boolean success = false;
        
        if (rng.nextInt(effectChance) < 100)
            success = applyEffect(target);
        
        return new Object[]{results[0], success};
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
}
