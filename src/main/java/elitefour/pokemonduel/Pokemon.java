package elitefour.pokemonduel;

import java.util.ArrayList;

public class Pokemon {
    
    private final String name;
    private final Type[] type;
    private final Move[] moves;
    private final Nature nature;
    
    private Status status;
    
    /* Statistics fields */
    
    private final int[] baseStats;
    private int[] statStages;
    private int[] stats;
    
    public Pokemon(String species, Nature nature) {
        
        name = species;
        type = new Type[2];
        moves = new Move[4];
        this.nature = nature;
        
        status = new Status();
        
        baseStats = new int[6];
        statStages = new int[7];
        stats = new int[8];
    }
    
    public String getName() {
        return name;
    }
    
    public Type[] getType() {
        return type;
    }
    
    public Move[] getMoves() {
        return moves;
    }
    
    public void setMove(Move move, int slot) {
        int index = slot - 1;
        moves[index] = move;
    }
    
    public Nature getNature() {
        return nature;
    }
    
    public Status getStatus() {
        return status;
    }
    
    public boolean hasStatus(Status.LoneStatus status) {
        return this.status.getLoneStatus().equals(status);
    }
    
    public boolean hasStatus(Status.MixStatus status) {
        
        ArrayList<Status.MixStatus> mixStatus = this.status.getMixStatus();
        
        for (int i = 0; i < mixStatus.size(); i++)
            if (mixStatus.get(i).equals(status))
                return true;
        
        return false;
    }
    
    public void setStatus(Status newStatus) {
        status = newStatus;
    }
}