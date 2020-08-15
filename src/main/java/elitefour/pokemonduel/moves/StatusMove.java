package elitefour.pokemonduel.moves;

import elitefour.pokemonduel.Pokemon;
import elitefour.pokemonduel.moves.Move;

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