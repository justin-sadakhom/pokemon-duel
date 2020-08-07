package elitefour.pokemonduel;

public class StatusMove extends Move {
    
    public StatusMove(String name) {
        super(name);
    }
    
    @Override
    public int use(Pokemon user, Pokemon target) {
        deductPP(1);
        return 1;
    }
}