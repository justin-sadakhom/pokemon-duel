package elitefour.pokemonduel;

public class DrainMove extends DamageMove {
    
    public DrainMove(String name, Type type, Category category,
            int pp, int power, int accuracy) {
        
        super(name, type, category, pp, power, accuracy);
    }
    
    @Override
    public int use(Pokemon user, Pokemon target) {
        return super.use(user, target);
    }
    
    public void useSecondary(Pokemon user, Pokemon target, int damage) {
        user.restoreHealth(damage / 2);
    }
    
    public static String text(String name) {
        return name + " had its energy drained!";
    }
}
