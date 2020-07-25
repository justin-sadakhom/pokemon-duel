package elitefour.pokemonduel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import static java.util.Map.entry;

enum Modifier {
    POSITIVE,
    NEUTRAL,
    NEGATIVE
}

public class Pokemon {
    
    public enum Stat {
        HEALTH,
        ATTACK,
        DEFENSE,
        SPECIAL_ATTACK,
        SPECIAL_DEFENSE,
        SPEED,
        ACCURACY,
        EVASION
    }
    
    /* Constants */
    
    private final Map<Integer, Double> REGULAR_STAT_STAGES = Map.ofEntries(
        entry(-6, 2.0/8.0),
        entry(-5, 2.0/7.0),
        entry(-4, 2.0/6.0),
        entry(-3, 2.0/5.0),
        entry(-2, 2.0/4.0),
        entry(-1, 2.0/3.0),
        entry(0, 2.0/2.0),
        entry(1, 3.0/2.0),
        entry(2, 4.0/2.0),
        entry(3, 5.0/2.0),
        entry(4, 6.0/2.0),
        entry(5, 7.0/2.0),
        entry(6, 8.0/2.0)
    );
    
    private final Map<Integer, Double> PRECISION_STAT_STAGES = Map.ofEntries(
        entry(-6, 3.0/9.0),
        entry(-5, 3.0/8.0),
        entry(-4, 3.0/7.0),
        entry(-3, 3.0/6.0),
        entry(-2, 3.0/5.0),
        entry(-1, 3.0/4.0),
        entry(0, 3.0/3.0),
        entry(1, 4.0/3.0),
        entry(2, 5.0/3.0),
        entry(3, 6.0/3.0),
        entry(4, 7.0/3.0),
        entry(5, 8.0/3.0),
        entry(6, 9.0/3.0)
    );
            
    /* Regular fields */
    
    private final String name;
    private final Type[] type;
    private final Move[] moves;
    private final Nature nature;
    private Status status;
    private int level;
    
    /* Statistics fields */
    
    private final Map<Stat, Integer> baseStats, evs, ivs;
    private Map<Stat, Integer> stats, statStages, effectiveStats;
    private int currentHealth;
    
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
        this.nature = Nature.HARDY;
        this.status = new Status();
        this.level = 100;
        
        Map<Stat, Integer> tempBaseStats = Collections.EMPTY_MAP;
        
        try {
            tempBaseStats = initBaseStats();
        } catch (Exception error) {
            error.printStackTrace(System.out);
        }
        
        this.baseStats = tempBaseStats;
        this.evs = Map.of(Stat.HEALTH, 85, Stat.ATTACK, 85,
            Stat.DEFENSE, 85, Stat.SPECIAL_ATTACK, 85,
            Stat.SPECIAL_DEFENSE, 85, Stat.SPEED, 85);
        this.ivs = Map.of(Stat.HEALTH, 31, Stat.ATTACK, 31,
            Stat.DEFENSE, 31, Stat.SPECIAL_ATTACK, 31,
            Stat.SPECIAL_DEFENSE, 31, Stat.SPEED, 31);
        this.statStages = Map.of(Stat.ATTACK, 0, Stat.DEFENSE, 0,
            Stat.SPECIAL_ATTACK, 0, Stat.SPECIAL_DEFENSE, 0,
            Stat.SPEED, 0, Stat.ACCURACY, 0, Stat.EVASION, 0);
        
        calcStats();
        currentHealth = stats.get(Stat.HEALTH);
    }
    
    /* Constructor that requires specific parameters. */
    
    public Pokemon(String species, Nature nature, int level,
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
        this.level = level;
        
        Map<Stat, Integer> tempBaseStats = Collections.EMPTY_MAP;
        
        try {
            tempBaseStats = initBaseStats();
        } catch (Exception error) {
            error.printStackTrace(System.out);
        }
        
        this.baseStats = tempBaseStats;
        this.evs = Map.of(Stat.HEALTH, evs[0], Stat.ATTACK, evs[1],
            Stat.DEFENSE, evs[2], Stat.SPECIAL_ATTACK, evs[3],
            Stat.SPECIAL_DEFENSE, evs[4], Stat.SPEED, evs[5]);
        this.ivs = Map.of(Stat.HEALTH, ivs[0], Stat.ATTACK, ivs[1],
            Stat.DEFENSE, ivs[2], Stat.SPECIAL_ATTACK, ivs[3],
            Stat.SPECIAL_DEFENSE, ivs[4], Stat.SPEED, ivs[5]);
        this.statStages = Map.of(Stat.ATTACK, 0, Stat.DEFENSE, 0,
            Stat.SPECIAL_ATTACK, 0, Stat.SPECIAL_DEFENSE, 0,
            Stat.ACCURACY, 0, Stat.EVASION, 0);
        
        calcStats();
        currentHealth = stats.get(Stat.HEALTH);
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
    
    private Map<Stat, Integer> initBaseStats() throws Exception {
        
        File file = new File("resources\\data\\pokemon_species.txt");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        
        String line = "";
        int[] species_stats = new int[6];
        
        while (line != null && !line.equals(name))
            line = reader.readLine();
        
        if (line.equals(name)) {
            
            for (int i = 0; i < 2; i++)
                line = reader.readLine();
            
            String[] temp = line.split("/");
            
            for (int i = 0; i < 6; i++)
                species_stats[i] = Integer.parseInt(temp[i]);
        }
        
        return Map.of(Stat.HEALTH, species_stats[0],
                Stat.ATTACK, species_stats[1],
                Stat.DEFENSE, species_stats[2],
                Stat.SPECIAL_ATTACK, species_stats[3],
                Stat.SPECIAL_DEFENSE, species_stats[4],
                Stat.SPEED, species_stats[5]);
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
    
    public void clearStatus() {
        status.clearLoneStatus();
        status.clearMixStatus();
    }
    
    public int getLevel() {
        return level;
    }
    
    public void raiseLevel() {
        level += 1;
        calcStats(); // Level change also changes stats.
    }
    
    private void calcStats() {
        
        stats.put(Stat.HEALTH, calcHealth());
        stats.put(Stat.ATTACK, calcAttack());
        stats.put(Stat.DEFENSE, calcDefense());
        stats.put(Stat.SPECIAL_ATTACK, calcSpecialAttack());
        stats.put(Stat.SPECIAL_DEFENSE, calcSpecialDefense());
        stats.put(Stat.SPEED, calcSpeed());
        stats.put(Stat.ACCURACY, 1);
        stats.put(Stat.EVASION, 1);
    }
    
    private int calcHealth() {
        
        return (int)((((2 * baseStats.get(Stat.HEALTH) + ivs.get(Stat.HEALTH) + 
                (evs.get(Stat.HEALTH) / 4)) * level) / 100) + level + 10);
    }
    
    public int calcAttack() {
        
        switch(nature) {
            case LONELY:
            case BRAVE:
            case ADAMANT:
            case NAUGHTY:
                return statFormula(baseStats.get(Stat.ATTACK),
                        ivs.get(Stat.ATTACK), evs.get(Stat.ATTACK),
                        Modifier.POSITIVE);
            case BOLD:
            case TIMID:
            case MODEST:
            case CALM:
                return statFormula(baseStats.get(Stat.ATTACK),
                        ivs.get(Stat.ATTACK), evs.get(Stat.ATTACK),
                        Modifier.NEGATIVE);
            default:
                return statFormula(baseStats.get(Stat.ATTACK),
                        ivs.get(Stat.ATTACK), evs.get(Stat.ATTACK),
                        Modifier.NEUTRAL);
        }
    }
    
    public int calcDefense() {
        
        switch(nature) {
            case BOLD:
            case IMPISH:
            case LAX:
            case RELAXED:
                return statFormula(baseStats.get(Stat.DEFENSE),
                        ivs.get(Stat.DEFENSE), evs.get(Stat.DEFENSE),
                        Modifier.POSITIVE);
            case LONELY:
            case MILD:
            case GENTLE:
            case HASTY:
                return statFormula(baseStats.get(Stat.DEFENSE),
                        ivs.get(Stat.DEFENSE), evs.get(Stat.DEFENSE),
                        Modifier.NEGATIVE);
            default:
                return statFormula(baseStats.get(Stat.DEFENSE),
                        ivs.get(Stat.DEFENSE), evs.get(Stat.DEFENSE),
                        Modifier.NEUTRAL);
        }
    }
    
    public int calcSpecialAttack() {
        
        switch(nature) {
            case MODEST:
            case MILD:
            case RASH:
            case QUIET:
                return statFormula(baseStats.get(Stat.SPECIAL_ATTACK),
                        ivs.get(Stat.SPECIAL_ATTACK),
                        evs.get(Stat.SPECIAL_ATTACK), Modifier.POSITIVE);
            case ADAMANT:
            case IMPISH:
            case CAREFUL:
            case JOLLY:
                return statFormula(baseStats.get(Stat.SPECIAL_ATTACK),
                        ivs.get(Stat.SPECIAL_ATTACK),
                        evs.get(Stat.SPECIAL_ATTACK), Modifier.NEGATIVE);
            default:
                return statFormula(baseStats.get(Stat.SPECIAL_ATTACK),
                        ivs.get(Stat.SPECIAL_ATTACK),
                        evs.get(Stat.SPECIAL_ATTACK), Modifier.NEUTRAL);
        }
    }
    
    public int calcSpecialDefense() {
        
        switch(nature) {
            case CALM:
            case GENTLE:
            case CAREFUL:
            case SASSY:
                return statFormula(baseStats.get(Stat.SPECIAL_DEFENSE),
                        ivs.get(Stat.SPECIAL_DEFENSE),
                        evs.get(Stat.SPECIAL_DEFENSE), Modifier.POSITIVE);
            case NAUGHTY:
            case RASH:
            case LAX:
            case NAIVE:
                return statFormula(baseStats.get(Stat.SPECIAL_DEFENSE),
                        ivs.get(Stat.SPECIAL_DEFENSE),
                        evs.get(Stat.SPECIAL_DEFENSE), Modifier.NEGATIVE);
            default:
                return statFormula(baseStats.get(Stat.SPECIAL_DEFENSE),
                        ivs.get(Stat.SPECIAL_DEFENSE),
                        evs.get(Stat.SPECIAL_DEFENSE), Modifier.NEUTRAL);
        }
    }
    
    public int calcSpeed() {
        
        switch(nature) {
            case NAIVE:
            case JOLLY:
            case HASTY:
            case TIMID:
                return statFormula(baseStats.get(Stat.SPEED),
                        ivs.get(Stat.SPEED), evs.get(Stat.SPEED),
                        Modifier.POSITIVE);
            case BRAVE:
            case RELAXED:
            case QUIET:
            case SASSY:
                return statFormula(baseStats.get(Stat.SPEED),
                        ivs.get(Stat.SPEED), evs.get(Stat.SPEED),
                        Modifier.NEGATIVE);
            default:
                return statFormula(baseStats.get(Stat.SPEED),
                        ivs.get(Stat.SPEED), evs.get(Stat.SPEED),
                        Modifier.NEUTRAL);
        }
    }
    
    public int currentHealth() {
        return currentHealth;
    }
    
    public void deductHealth(int damage) {
        
        if (currentHealth - damage < 0)
            currentHealth = 0;
        else
            currentHealth -= damage;
    }
    
    public void restoreHealth(int healing) {
        
        if (currentHealth + healing > getStat(Stat.HEALTH))
            currentHealth = getStat(Stat.HEALTH);
        else
            currentHealth += healing;
    }
    
    public int getStat(Stat stat) {
        return stats.get(stat);
    }
    
    
    
    private int statFormula(int baseStat, int IV, int EV, Modifier mod) {
        
        double result = Math.floor(((2 * baseStat + IV + (EV / 4)) * this.level) 
                / 100) + 5;
        
        switch(mod) {
            case POSITIVE:
                return (int)(Math.floor(result) * 1.1);
            case NEGATIVE:
                return (int)(Math.floor(result) * 0.9);
            default:
                return (int)Math.floor(result);
        }
    }
}