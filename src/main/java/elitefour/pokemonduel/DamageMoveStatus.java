package elitefour.pokemonduel;

import java.util.Random;

public class DamageMoveStatus extends DamageMove {
    
    private final Status appliedStatus;
    private final int effectChance;
    
    public DamageMoveStatus(String name, Type type, Category category,
            int pp, int power, int accuracy, Status status, int chance) {
        
        super(name, type, category, pp, power, accuracy);
        appliedStatus = status;
        effectChance = chance;
    }
    
    @Override
    public Object[] use(Pokemon user, Pokemon target) {
        
        Object[] results = super.use(user, target);
        Random rng = new Random();
        boolean success = true;
        
        if (rng.nextInt(effectChance) < 100)
            success = applyEffect(target);
        
        return new Object[]{results[0], success};
    }
    
    private boolean applyEffect(Pokemon target) {
        if (appliedStatus.mixStatus().isEmpty())
            return target.setStatus(appliedStatus.loneStatus());
        else
            return target.setStatus(appliedStatus.mixStatus().get(0));
    }
}
