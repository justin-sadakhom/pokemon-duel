package elitefour.pokemonduel;

import java.util.Random;

public class DamageStatus extends DamageMove {
    
    private final Status appliedStatus;
    private final int effectChance;
    
    public DamageStatus(String name, Type type, Category category,
            int pp, int power, int accuracy, Status status, int chance) {
        
        super(name, type, category, pp, power, accuracy);
        appliedStatus = status;
        effectChance = chance;
    }
    
    @Override
    public int use(Pokemon user, Pokemon target) {
        return super.use(user, target);
    }
    
    public boolean useSecondary(Pokemon user, Pokemon target) {
        
        Random rng = new Random();
        boolean success = true;
        
        if (rng.nextInt(100) < effectChance)
            success = applyEffect(target);
        
        return success;
    }
    
    private boolean applyEffect(Pokemon target) {
        if (appliedStatus.mixStatus().isEmpty())
            return target.setStatus(appliedStatus.loneStatus());
        else
            return target.setStatus(appliedStatus.mixStatus().get(0));
    }
}
