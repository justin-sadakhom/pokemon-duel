package elitefour.pokemonduel.battle;

public class Trainer {
    
    private final Pokemon[] team;
    private final String name;
    private Battle.Action action;
    private int choice;
    private Pokemon active;
    
    /* Bide fields */
    public boolean bideActive;
    public boolean bideUserIn;
    
    public Trainer(String name, Pokemon[] team) {
        this.team = team;
        this.name = name;
        bideActive = false;
        bideUserIn = false;
    }
    
    public Pokemon[] team() {
        return team;
    }
    
    public Pokemon team(int slot) {
        return team[slot];
    }
    
    public String name() {
        return name;
    }
    
    public Battle.Action action() {
        return action;
    }
    
    public void setAction(Battle.Action action) {
        this.action = action;
    }
    
    public int choice() {
        return choice;
    }
    
    public void setChoice(int choice) {
        this.choice = choice;
    }
    
    public Pokemon active() {
        return active;
    }
    
    public void setActive(Pokemon replacement) {
        
        if (active != null) {
            active.clearTempStatus();
            active.resetStatStages();
        }
        
        if (bideUserIn)
            bideUserIn = false;
        
        active = replacement;
    }
}