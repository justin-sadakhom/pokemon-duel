package elitefour.pokemonduel;

public class Trainer {
    
    private final Pokemon[] team;
    private final String name;
    private final Battle.Action action;
    private final int choice;
    private Pokemon active;
    
    public Trainer(Pokemon[] team, String name, Battle.Action action,
            int choice, Pokemon active) {
        this.team = team;
        this.name = name;
        this.action = action;
        this.choice = choice;
        this.active = active;
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
    
    public int choice() {
        return choice;
    }
    
    public Pokemon active() {
        return active;
    }
    
    public void setActive(Pokemon replacement) {
        active = replacement;
    }
}