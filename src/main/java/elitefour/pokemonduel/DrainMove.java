package elitefour.pokemonduel;

public class DrainMove extends DamageMove {
    
    public DrainMove(String name, Type type, Category category,
            int pp, int power, int accuracy) {
        
        super(name, type, category, pp, power, accuracy);
    }
    
    @Override
    public int use(Pokemon user, Pokemon target) {
        int damage = super.use(user, target);
        return damage / 2;
    }
}
