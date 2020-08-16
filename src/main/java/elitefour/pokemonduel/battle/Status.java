package elitefour.pokemonduel.battle;

import java.util.ArrayList;

public class Status {
    
    public enum LoneStatus {
        NONE,
        BURN,
        FREEZE,
        PARALYSIS,
        POISON,
        TOXIC,
        SLEEP
    }

    public enum MixStatus {
        BIND,
        TRAP,
        CONFUSION,
        CURSE,
        INFATUATION,
        LEECH,
        RECHARGE,
        SEMI_INVULNERABLE,
        SUBSTITUTE
    }
    
    private LoneStatus loneStatus;
    private final ArrayList<MixStatus> mixStatus;
    
    public Status() {
        loneStatus = LoneStatus.NONE;
        mixStatus = new ArrayList<>();
    }
    
    public LoneStatus loneStatus() {
        return loneStatus;
    }
    
    public void setLoneStatus(LoneStatus status) {
        loneStatus = status;
    }
    
    public void clearLoneStatus() {
        loneStatus = LoneStatus.NONE;
    }
    
    public boolean hasLoneStatus() {
        return loneStatus != LoneStatus.NONE;
    }
    
    public ArrayList<MixStatus> mixStatus() {
        return mixStatus;
    }
    
    public void addMixStatus(MixStatus status) {
        mixStatus.add(status);
    }
    
    public void remove(MixStatus status) {
        mixStatus.remove(status);
    }
    
    public void clearMixStatus() {
        mixStatus.clear();
    }
    
    public boolean hasMixStatus() {
        return !mixStatus().isEmpty();
    }
    
    public boolean has(MixStatus condition) {
        return mixStatus.contains(condition);
    }
    
    public boolean isEmpty() {
        return loneStatus == Status.LoneStatus.NONE && mixStatus.isEmpty();
    }
    
    public static boolean containsLone(String test) {

        for (Status.LoneStatus choice : Status.LoneStatus.values())
            if (choice.name().equals(test))
                return true;

        return false;
    }
    
    public static boolean containsMix(String test) {

        for (Status.MixStatus choice : Status.MixStatus.values())
            if (choice.name().equals(test))
                return true;

        return false;
    }
}