package elitefour.pokemonduel;

public class StatusMove extends Move {
    
    public StatusMove(String name, Type type, Category category,
            int pp, int power, int accuracy) {
        
        super(name, type, category, pp, power, accuracy);
    }
    
    @Override
    public int use(Pokemon user, Pokemon target) {
        deductPP(1);
        return 1;
    }
}