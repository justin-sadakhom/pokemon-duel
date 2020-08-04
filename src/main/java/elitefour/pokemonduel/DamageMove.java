package elitefour.pokemonduel;

import java.util.Arrays;
import java.util.Random;

public class DamageMove extends Move {
    
    public DamageMove(String name, Type type, Category category,
            int pp, int power, int accuracy) {
        
        super(name, type, category, pp, power, accuracy);
    }
    
    @Override
    public Object[] use(Pokemon user, Pokemon target) {
        deductPP(1);
        return new Object[]{target.deductHealth(damage(user, target)), true};
    }
    
    private int damage(Pokemon user, Pokemon target) {
        
        int a, d;
        
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
        
        if (num <= critChance)
            return 1.5;
        else
            return 1.0;
    }
    
    private double random() {

        Random rng = new Random();
        return (rng.nextInt(15) + 85) / 100;
    }
    
    private double STAB(Type[] userType, Type moveType) {
        
        if (Arrays.asList(userType).contains(moveType))
            return 1.5;
        else
            return 1.0;
    }
    
    private double typeAdvantage(Type moveType, Type[] targetType) {
        
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
}