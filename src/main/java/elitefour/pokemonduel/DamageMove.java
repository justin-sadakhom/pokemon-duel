package elitefour.pokemonduel;

import java.util.Arrays;
import java.util.Random;

public class DamageMove extends Move {
    
    private boolean isCrit;
    
    public DamageMove(String name, Type type, Category category,
            int pp, int power, int accuracy) {
        
        super(name, type, category, pp, power, accuracy);
        isCrit = false;
    }
    
    @Override
    public int use(Pokemon user, Pokemon target) {
        deductPP(1);
        return target.deductHealth(damage(user, target));
    }
    
    protected int damage(Pokemon user, Pokemon target) {
        
        int a, d;
        isCrit = false; // Reset crit every time.
        
        if (category().equals(Category.PHYSICAL)) {
            a = user.effectiveStat(Pokemon.Stat.ATTACK);
            d = target.effectiveStat(Pokemon.Stat.DEFENSE);
        }
        
        else {
            a = user.effectiveStat(Pokemon.Stat.SPECIAL_ATTACK);
            d = target.effectiveStat(Pokemon.Stat.SPECIAL_DEFENSE);
        }
        
        int base = (((2 * user.level()) / 5 + 2) * power() * a / d) / 50 + 2;
        
        return (int)(base * modifier(user, target));
    }
    
    private double modifier(Pokemon user, Pokemon target) {
        
        return critical(user.hiddenStat(Pokemon.Stat.CRITICAL)) * random() *
                STAB(user.type(), this.type()) *
                typeAdvantage(this.type(), target.type()) *
                burn(user.status().loneStatus(), this.category());
    }
    
    private double critical(double critChance) {
        
        Random rng = new Random();
        double num = rng.nextDouble();
        
        if (num <= critChance) {
            isCrit = true;
            return 1.5;
        }
        else
            return 1.0;
    }
    
    private static double random() {

        Random rng = new Random();
        return (rng.nextInt(15) + 85) / 100;
    }
    
    private double STAB(Type[] userType, Type moveType) {
        
        if (Arrays.asList(userType).contains(moveType))
            return 1.5;
        else
            return 1.0;
    }
    
    public static double typeAdvantage(Type moveType, Type[] targetType) {
        
        double multiplier = 1.0;
        
        if (Arrays.asList(targetType[0].immunities()).contains(moveType) || 
                Arrays.asList(targetType[1].immunities()).contains(moveType))
            return 0;
        
        if (Arrays.asList(targetType[0].weaknesses()).contains(moveType))
            multiplier *= 2.0;
        
        if (Arrays.asList(targetType[1].weaknesses()).contains(moveType))
            multiplier *= 2.0;
        
        if (Arrays.asList(targetType[0].resistances()).contains(moveType))
            multiplier *= 0.5;
        
        if (Arrays.asList(targetType[1].resistances()).contains(moveType))
            multiplier *= 0.5;
        
        return multiplier;
    }
    
    private double burn(Status.LoneStatus userStatus, Category moveCategory) {
        
        if (userStatus.equals(Status.LoneStatus.BURN) &&
                moveCategory.equals(Move.Category.PHYSICAL))
            return 0.5;
        else
            return 1.0;
    }
    
    public static int confusionDamage(Pokemon victim) {
        
        final int POWER = 40;
        int a = victim.stat(Pokemon.Stat.ATTACK);
        int d = victim.stat(Pokemon.Stat.DEFENSE);
        
        return (int)((((2 * victim.level()) / 5 + 2) * POWER * a / d) / 50 + 2 * 
                random());
    }
    
    public static String hitText(Type moveType, Type[] defenderType) {
        
        double multiplier = typeAdvantage(moveType, defenderType);

        if (multiplier == 2.0)
            return "It was super effective!";
        else if (multiplier == 0.5)
            return "It's not very effective...";
        else // multiplier == 0
            return "It had no effect...";
    }
    
    public boolean isCrit() {
        return isCrit;
    }
    
    public static String critText() {
        return "A critical hit!";
    }
}