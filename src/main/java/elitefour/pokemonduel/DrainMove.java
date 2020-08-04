package elitefour.pokemonduel;

public class DrainMove extends DamageMove {
    
    public DrainMove(String name, Type type, Category category,
            int pp, int power, int accuracy) {
        
        super(name, type, category, pp, power, accuracy);
    }
    
    @Override
    public Object[] use(Pokemon user, Pokemon target) {
        Object[] result = super.use(user, target);
        user.restoreHealth((int)result[0] / 2);
        return new Object[]{result[0], true};
    }
}
