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
    private ArrayList<MixStatus> mixStatus;
    
    public Status() {
        
        loneStatus = LoneStatus.NONE;
        mixStatus = new ArrayList<>();
    }
    
    LoneStatus getLoneStatus() {
        return loneStatus;
    }
    
    void setLoneStatus(LoneStatus status) {
        loneStatus = status;
    }
    
    void clearLoneStatus() {
        loneStatus = LoneStatus.NONE;
    }
    
    ArrayList<MixStatus> getMixStatus() {
        return mixStatus;
    }
    
    void addMixStatus(MixStatus status) {
        mixStatus.add(status);
    }
    
    void removeMixStatus(MixStatus status) {
        mixStatus.remove(status);
    }
    
    void clearMixStatus() {
        mixStatus.clear();
    }
}