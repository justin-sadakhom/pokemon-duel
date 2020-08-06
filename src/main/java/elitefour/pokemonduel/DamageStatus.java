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
    
    protected boolean applyEffect(Pokemon target) {
        if (appliedStatus.mixStatus().isEmpty())
            return target.setStatus(appliedStatus.loneStatus());
        else
            return target.setStatus(appliedStatus.mixStatus().get(0));
    }
    
    public static String hitText(Status.LoneStatus status, Pokemon target,
            boolean success) {
        
        if (!success)
            return "...but it failed.";
        
        switch (status) {
            case BURN:
                return target.name() + " was burned!";
            case FREEZE:
                return target.name() + " was frozen solid!";
            case PARALYSIS:
                return target.name() + " was paralyzed!";
            case POISON:
                return target.name() + " was poisoned!";
            case TOXIC:
                return target.name() + " was badly poisoned!";
            case SLEEP:
                return target.name() + " fell asleep!";
            default: // This should never be reached.
                return "";
        }
    }
    
    public static String hitText(Status.MixStatus status, Pokemon target,
            boolean success) {
        
        if (!success)
            return "...but it failed.";
        
        switch (status) {
            case CONFUSION:
                return target.name() + " became confused!";
            default:
                return "";
        }
    }
}
