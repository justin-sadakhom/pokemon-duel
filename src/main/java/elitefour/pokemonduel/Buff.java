package elitefour.pokemonduel;

public class Buff extends Move {
    
    private final Pokemon.Stat affectedStat;
    private final int stages;
    
    public Buff(String name, Type type, Category category, int pp,
            int power, int accuracy, Pokemon.Stat stat, int stage) {
        
        super(name, type, category, pp, power, accuracy);
        this.affectedStat = stat;
        this.stages = stage;
    }
    
    @Override
    public int use(Pokemon user, Pokemon target) {
        
        deductPP(1);
        
        if (target.raiseStatStage(affectedStat, stages))
            return 1;
        else
            return 0;
    }
    
    public static String text(String name, DamageDebuff move, boolean success) {
        
        String message = name + "'s " + move.affectedStat().toLowerCase();
        
        // Affected stat stage is already at max.
        if (!success)
            return message + " won't go any higher!";

        // There's still room for change.
        else {

            switch (move.stages()) {
                case 1:
                    return message + " rose!";
                default: // case 2
                    return message + " rose sharply!";
            }
        }
    }
}