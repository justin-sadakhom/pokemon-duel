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
    public void use(Pokemon user, Pokemon target) {
        
        super.use(user, target);
        
        Random rng = new Random();
        
        if (rng.nextInt(effectChance) < 100)
            applyEffect(target);
    }
    
    private void applyEffect(Pokemon target) {
        
        if (stages > 0)
            target.raiseStatStage(affectedStat, stages);
        else
            target.lowerStatStage(affectedStat, stages);
    }
}
