package elitefour.pokemonduel;

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
    
    LoneStatus loneStatus() {
        return loneStatus;
    }
    
    void setLoneStatus(LoneStatus status) {
        loneStatus = status;
    }
    
    void clearLoneStatus() {
        loneStatus = LoneStatus.NONE;
    }
    
    ArrayList<MixStatus> mixStatus() {
        return mixStatus;
    }
    
    void addMixStatus(MixStatus status) {
        mixStatus.add(status);
    }
    
    void remove(MixStatus status) {
        mixStatus.remove(status);
    }
    
    void clearMixStatus() {
        mixStatus.clear();
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