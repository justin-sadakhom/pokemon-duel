package elitefour.pokemonduel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Map;

public class Pokemon {
    
    public enum Stat {
        ATTACK,
        DEFENSE,
        SPECIAL_ATTACK,
        SPECIAL_DEFENSE,
        SPEED,
        ACCURACY,
        EVASION
    }
    
    private final String name;
    private final Type[] type;
    private final Move[] moves;
    private final Nature nature;
    private Status status;
    
    /* Statistics fields */
    
    private final int[] baseStats, ivs, evs;
    private Map<Stat, Integer> statStages;
    private int[] stats;
    
    /* Constructor with default values for fields. */
    
    public Pokemon(String species) {
        
        this.name = species;
        
        Type[] tempType = new Type[2];
        
        try {
            tempType = initType();
        } catch (Exception error) {
            error.printStackTrace(System.out);
        }
        
        this.type = tempType;
        this.moves = new Move[4];
        this.nature = Nature.Hardy;
        this.status = new Status();
        
        int[] tempBaseStats = new int[6];
        
        try {
            tempBaseStats = initBaseStats();
        } catch (Exception error) {
            error.printStackTrace(System.out);
        }
        
        this.baseStats = tempBaseStats;
        this.ivs = new int[6];
        this.evs = new int[6];
        this.statStages = Map.of(Stat.ATTACK, 0, Stat.DEFENSE, 0,
                Stat.SPECIAL_ATTACK, 0, Stat.SPECIAL_DEFENSE, 0,
                Stat.ACCURACY, 0, Stat.EVASION, 0);
        this.stats = new int[8];
    }
    
    /* Constructor that requires specific parameters. */
    
    public Pokemon(String species, Nature nature,
            int[] ivs, int[] evs, Move[] moves) {
        
        this.name = species;
        
        Type[] tempType = new Type[2];
        
        try {
            tempType = initType();
        } catch (Exception error) {
            error.printStackTrace(System.out);
        }
        
        this.type = tempType;
        this.moves = moves;
        this.nature = nature;
        this.status = new Status();
        
        int[] tempBaseStats = new int[6];
        
        try {
            tempBaseStats = initBaseStats();
        } catch (Exception error) {
            error.printStackTrace(System.out);
        }
        
        this.baseStats = tempBaseStats;
        this.ivs = ivs;
        this.evs = evs;
        this.statStages = Map.of(Stat.ATTACK, 0, Stat.DEFENSE, 0,
                Stat.SPECIAL_ATTACK, 0, Stat.SPECIAL_DEFENSE, 0,
                Stat.ACCURACY, 0, Stat.EVASION, 0);
        this.stats = new int[8];
    }
    
    private Type[] initType() throws Exception {
        
        File file = new File("resources\\data\\pokemon_species.txt");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        
        String line = "";
        String[] typing = new String[2];
        
        while (line != null && !line.equals(name))
            line = reader.readLine();
        
        if (line.equals(name)) {
            line = reader.readLine();
            typing = line.split("/");
        }
            
        return new Type[]{Type.valueOf(typing[0]), Type.valueOf(typing[1])};
    }
    
    private int[] initBaseStats() throws Exception {
        
        File file = new File("resources\\data\\pokemon_species.txt");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        
        String line = "";
        String[] species_stats = new String[2];
        
        while (line != null && !line.equals(name))
            line = reader.readLine();
        
        if (line.equals(name)) {
            
            for (int i = 0; i < 2; i++)
                line = reader.readLine();
            
            species_stats = line.split("/");
        }
        
        return new int[]{Integer.parseInt(species_stats[0]),
            Integer.parseInt(species_stats[1]),
            Integer.parseInt(species_stats[2]),
            Integer.parseInt(species_stats[3]),
            Integer.parseInt(species_stats[4]),
            Integer.parseInt(species_stats[5]),};
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