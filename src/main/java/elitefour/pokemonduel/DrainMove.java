package elitefour.pokemonduel;

public class DrainMove extends DamageMove {
    
    public DrainMove(String name) {
        super(name);
    }
    
    @Override
    public int use(Pokemon user, Pokemon target) {
        return super.use(user, target);
    }
    
    public void useSecondary(Pokemon user, int damage) {
        user.restoreHealth(damage / 2);
    }
    
    public static String hitText(String name) {
        return name + " had its energy drained!";
    }
}
