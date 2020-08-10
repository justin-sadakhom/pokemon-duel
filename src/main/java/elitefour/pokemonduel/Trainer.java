package elitefour.pokemonduel;

public class Trainer {
    
    private final Pokemon[] team;
    private final String name;
    private Battle.Action action;
    private int choice;
    private Pokemon active;
    
    public Trainer(String name, Pokemon[] team) {
        this.team = team;
        this.name = name;
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
        
        active = replacement;
    }
}