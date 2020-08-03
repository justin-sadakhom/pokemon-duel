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
    public void use(Pokemon user, Pokemon target) {
        
        super.use(user, target);
        
        Random rng = new Random();
        
        if (rng.nextInt(effectChance) < 100)
            applyEffect(target);
    }
    
    private void applyEffect(Pokemon target) {
        if (appliedStatus.mixStatus().isEmpty())
            target.setStatus(appliedStatus.loneStatus());
        else
            target.setStatus(appliedStatus.mixStatus().get(0));
    }
}
