package elitefour.pokemonduel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
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
        EVASION,
        CRITICAL
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
    
    private final Map<Integer, Double> ACCURACY_STAT_STAGES = Map.ofEntries(
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
    
    private final Map<Integer, Double> EVASION_STAT_STAGES = Map.ofEntries(
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
    
    private final Map<Integer, Double> CRIT_STAT_STAGES = Map.ofEntries(
        entry(0, 1.0/24.0),
        entry(1, 1.0/8.0),
        entry(2, 1.0/2.0),
        entry(3, 1.0),
        entry(4, 1.0),
        entry(5, 1.0)
    );
            
    /* Regular fields */
    
    private final String name;
    private final Type[] type;
    private final Move[] moves;
    private final Nature nature;
    private final Status status;
    private int level, sleepCounter, confusionCounter;
    
    /* Statistics fields */
    
    private final Map<Stat, Integer> baseStats, evs, ivs;
    private HashMap<Stat, Integer> stats, statStages;
    private HashMap<Stat, Double> hiddenStats;
    private int currentHealth;
    
    /* Constructor with default values for fields. */
    
    public Pokemon(String species) {
        
        this.name = species;
        this.type = initType();
        this.moves = new Move[4];
        this.nature = Nature.HARDY;
        this.status = new Status();
        this.level = 100;
        
        this.baseStats = initBaseStats();
        this.evs = Map.of(
            Stat.HEALTH, 85,
            Stat.ATTACK, 85,
            Stat.DEFENSE, 85,
            Stat.SPECIAL_ATTACK, 85,
            Stat.SPECIAL_DEFENSE, 85,
            Stat.SPEED, 85
        );
        this.ivs = Map.of(
            Stat.HEALTH, 31,
            Stat.ATTACK, 31,
            Stat.DEFENSE, 31,
            Stat.SPECIAL_ATTACK, 31,
            Stat.SPECIAL_DEFENSE, 31,
            Stat.SPEED, 31
        );
        this.statStages = new HashMap();
        this.statStages.put(Stat.ATTACK, 0);
        this.statStages.put(Stat.DEFENSE, 0);
        this.statStages.put(Stat.SPECIAL_ATTACK, 0);
        this.statStages.put(Stat.SPECIAL_DEFENSE, 0);
        this.statStages.put(Stat.SPEED, 0);
        this.statStages.put(Stat.ACCURACY, 0);
        this.statStages.put(Stat.EVASION, 0);
        this.statStages.put(Stat.CRITICAL, 0);
        
        calcStats();
        currentHealth = stats.get(Stat.HEALTH);
    }
    
    /* Constructor that requires specific parameters. */
    
    public Pokemon(String species, Nature nature, int level,
            int[] ivs, int[] evs, Move[] moves) {
        
        this.name = species;
        this.type = initType();
        this.moves = moves;
        this.nature = nature;
        this.status = new Status();
        this.level = level;
        
        this.baseStats = initBaseStats();
        this.evs = Map.of(
            Stat.HEALTH, evs[0],
            Stat.ATTACK, evs[1],
            Stat.DEFENSE, evs[2],
            Stat.SPECIAL_ATTACK, evs[3],
            Stat.SPECIAL_DEFENSE, evs[4],
            Stat.SPEED, evs[5]
        );
        this.ivs = Map.of(
            Stat.HEALTH, ivs[0],
            Stat.ATTACK, ivs[1],
            Stat.DEFENSE, ivs[2],
            Stat.SPECIAL_ATTACK, ivs[3],
            Stat.SPECIAL_DEFENSE, ivs[4], 
            Stat.SPEED, ivs[5]
        );
        this.statStages = new HashMap();
        this.statStages.put(Stat.ATTACK, 0);
        this.statStages.put(Stat.DEFENSE, 0);
        this.statStages.put(Stat.SPECIAL_ATTACK, 0);
        this.statStages.put(Stat.SPECIAL_DEFENSE, 0);
        this.statStages.put(Stat.SPEED, 0);
        this.statStages.put(Stat.ACCURACY, 0);
        this.statStages.put(Stat.EVASION, 0);
        this.statStages.put(Stat.CRITICAL, 0);
        
        calcStats();
        currentHealth = stats.get(Stat.HEALTH);
    }
    
    private Type[] initType() {
        
        Type[] tempType = new Type[2];
        
        try {
            File file = new File("resources\\data\\pokemon_species.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));

            String line = "";
            String[] typing = new String[2];

            while (line != null && !line.equals(name))
                line = reader.readLine();

            if (line.equals(name)) {
                line = reader.readLine();
                
                if (line.contains("/"))
                    typing = line.split("/");
                else
                    typing[0] = line;
            }

            tempType[0] = Type.valueOf(typing[0].toUpperCase());
            
            if (typing[1].isEmpty())
                tempType[1] = Type.NONE;
            else
                tempType[1] = Type.valueOf(typing[1].toUpperCase());
            
        } catch (IOException error) {
            error.printStackTrace(System.out);
        }
        
        return tempType;
    }
    
    private Map<Stat, Integer> initBaseStats() {
        
        Map<Stat, Integer> tempBaseStats = new HashMap<>();
        
        try {
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
            
            tempBaseStats.put(Stat.HEALTH, species_stats[0]);
            tempBaseStats.put(Stat.ATTACK, species_stats[1]);
            tempBaseStats.put(Stat.DEFENSE, species_stats[2]);
            tempBaseStats.put(Stat.SPECIAL_ATTACK, species_stats[3]);
            tempBaseStats.put(Stat.SPECIAL_DEFENSE, species_stats[4]);
            tempBaseStats.put(Stat.SPEED, species_stats[5]);

        } catch (IOException | NumberFormatException error) {
            error.printStackTrace(System.out);
        }
        
        return tempBaseStats;
    }
    
    public String name() {
        return name;
    }
    
    public Type[] type() {
        return type;
    }
    
    public Move[] moves() {
        return moves;
    }
    
    public Move moves(int slot) {
        return moves[slot];
    }
    
    public void setMove(Move move, int slot) {
        int index = slot - 1;
        moves[index] = move;
    }
    
    public int useMove(int slot, Pokemon target) {
        return moves[slot].use(this, target);
    }
    
    public Nature nature() {
        return nature;
    }
    
    public Status status() {
        return status;
    }
    
    public boolean hasStatus() {
        return !status.isEmpty();
    }
    
    public boolean hasStatus(Status.LoneStatus status) {
        return this.status.loneStatus().equals(status);
    }
    
    public boolean hasStatus(Status.MixStatus status) {
        
        ArrayList<Status.MixStatus> mixStatus = this.status.mixStatus();
        
        for (int i = 0; i < mixStatus.size(); i++)
            if (mixStatus.get(i).equals(status))
                return true;
        
        return false;
    }
    
    public boolean setStatus(Status.LoneStatus status) {
        
        if (hasStatus())
            return false;
        
        else if ((Arrays.asList(type).contains(Type.POISON) || 
                Arrays.asList(type).contains(Type.STEEL)) && 
                status == Status.LoneStatus.POISON)
            return false;
        
        else if (Arrays.asList(type).contains(Type.FIRE) &&
                status == Status.LoneStatus.BURN)
            return false;
        
        else {
            this.status.setLoneStatus(status);
            return true;
        }
    }
    
    public boolean setStatus(Status.MixStatus status) {
        
        if (hasStatus(status))
            return false;
        else {
            this.status.addMixStatus(status);
            return true;
        }
    }
    
    public void clearStatus() {
        status.clearLoneStatus();
        status.clearMixStatus();
    }
    
    public void clearTempStatus() {
        status.clearMixStatus();
    }
    
    public Object[] immobilizedBy() {
        
        Random rng = new Random();
        Status obstacle = new Status();
        
        switch (status.loneStatus()) {
            
            case FREEZE:
                obstacle.setLoneStatus(Status.LoneStatus.FREEZE);
                
                if (rng.nextInt(5) < 1)
                    status.clearLoneStatus();
                else
                    return new Object[]{obstacle, true};
               
            case PARALYSIS:
                obstacle.setLoneStatus(Status.LoneStatus.PARALYSIS);
                
                if (rng.nextInt(4) >= 1)
                    return new Object[]{obstacle, true};
                
            case SLEEP:
                sleepCounter -= 1;
                obstacle.setLoneStatus(Status.LoneStatus.SLEEP);
                
                if (sleepCounter == 0)
                    status.clearLoneStatus();
                else
                    return new Object[]{obstacle, true};
        }
        
        if (!status.mixStatus().isEmpty()) {
            
            if (status.mixStatus().contains(Status.MixStatus.RECHARGE)) {
                status.remove(Status.MixStatus.RECHARGE);
                obstacle.addMixStatus(Status.MixStatus.RECHARGE);
                return new Object[]{obstacle, true};
            }
            
            if (status.mixStatus().contains(Status.MixStatus.CONFUSION)) {
                confusionCounter -= 1;
                obstacle.addMixStatus(Status.MixStatus.CONFUSION);
                
                if (confusionCounter == 0)
                    status.clearMixStatus();
                else
                    return new Object[]{obstacle, true};
            }
            
            if (status.mixStatus().contains(Status.MixStatus.INFATUATION)) {
                obstacle.addMixStatus(Status.MixStatus.INFATUATION);
                
                if (rng.nextInt(2) >= 1)
                    return new Object[]{obstacle, true};
            }
        }
        
        return new Object[]{obstacle, false};
    }
    
    public int level() {
        return level;
    }
    
    public void raiseLevel() {
        level += 1;
        calcStats(); // Level change also changes stats.
    }
    
    private void calcStats() {
        
        stats = new HashMap<>();
        stats.put(Stat.HEALTH, calcHealth());
        stats.put(Stat.ATTACK, calcAttack());
        stats.put(Stat.DEFENSE, calcDefense());
        stats.put(Stat.SPECIAL_ATTACK, calcSpecialAttack());
        stats.put(Stat.SPECIAL_DEFENSE, calcSpecialDefense());
        stats.put(Stat.SPEED, calcSpeed());
        
        hiddenStats = new HashMap<>();
        hiddenStats.put(Stat.ACCURACY, 1.0);
        hiddenStats.put(Stat.EVASION, 1.0);
        hiddenStats.put(Stat.CRITICAL, 1.0);
    }
    
    private int calcHealth() {
        
        return (int)((((2 * baseStats.get(Stat.HEALTH) + ivs.get(Stat.HEALTH) + 
                (evs.get(Stat.HEALTH) / 4)) * level) / 100) + level + 10);
    }
    
    private int calcAttack() {
        
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
    
    private int calcDefense() {
        
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
    
    private int calcSpecialAttack() {
        
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
    
    private int calcSpecialDefense() {
        
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
    
    private int calcSpeed() {
        
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
    
    public static Pokemon compareSpeed(Pokemon first, Pokemon second) {
        
        if (first.stat(Stat.SPEED) > second.stat(Stat.SPEED))
            return first;
        
        else if (first.stat(Stat.SPEED) < second.stat(Stat.SPEED))
            return second;
        
        else {
            Random coinFlip = new Random();
            boolean heads = coinFlip.nextBoolean();
            
            if (heads)
                return first;
            else
                return second;
        }
    }
    
    public int currentHealth() {
        return currentHealth;
    }
    
    public int deductHealth(int damage) {
        
        int deduction;
        
        if (damage < 0)
            damage = 1;
        
        if (currentHealth - damage < 0) {
            deduction = currentHealth;
            currentHealth = 0;
        }
        
        else {
            deduction = damage;
            currentHealth -= damage;
        }
        
        return deduction;
    }
    
    public void restoreHealth(int healing) {
        
        if (currentHealth + healing > stat(Stat.HEALTH))
            currentHealth = stat(Stat.HEALTH);
        else
            currentHealth += healing;
    }
    
    public double healthPercent() {
        return Math.floor((double)currentHealth /
                (double)stats.get(Stat.HEALTH) * 100);
    }
    
    public boolean isFainted() {
        return currentHealth == 0;
    }
    
    public int statStage(Stat stat) {
        return statStages.get(stat);
    }
    
    public boolean raiseStatStage(Stat stat, int amount) {
        
        int maxStage;
        
        if (stat.equals(Stat.CRITICAL))
            maxStage = 5;
        else
            maxStage = 6;
            
        int currentStage = statStage(stat);
        
        if (currentStage + amount <= maxStage) {
            statStages.replace(stat, currentStage + amount);
            return true;
        }
        else {
            statStages.replace(stat, maxStage);
            return false;
        }
    }
    
    public boolean lowerStatStage(Stat stat, int amount) {
        
        int minStage;
        
        if (stat.equals(Stat.CRITICAL))
            minStage = 0;
        else
            minStage = -6;
        
        int currentStage = statStage(stat);
        
        if (currentStage + amount >= minStage) {
            statStages.replace(stat, currentStage - amount);
            return true;
        }
        else {
            statStages.replace(stat, minStage);
            return false;
        }
    }
    
    public void resetStatStages() {
        statStages.keySet().forEach(key -> {
            statStages.replace(key, 0);
        });
    }
    
    public int stat(Stat stat) {
        return stats.get(stat);
    }
    
    public double hiddenStat(Stat stat) {
        switch(stat) {
            case ACCURACY:
                return hiddenStats.get(stat) * 
                        ACCURACY_STAT_STAGES.get(statStage(stat));
                
            case EVASION:
                return hiddenStats.get(stat) * 
                        EVASION_STAT_STAGES.get(statStage(stat));

            default: // case CRITICAL
                return hiddenStats.get(stat) * 
                        CRIT_STAT_STAGES.get(statStage(stat));
        }
    }
    
    public int effectiveStat(Stat stat) {
        return (int)(stat(stat) * REGULAR_STAT_STAGES.get(statStage(stat)));
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