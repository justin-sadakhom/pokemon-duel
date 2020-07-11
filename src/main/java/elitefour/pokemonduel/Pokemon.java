package elitefour.pokemonduel;

import java.util.ArrayList;

public class Pokemon {

    private String name;
    private Status status;
    private Type[] type;
    
    public Pokemon(String species) {
        
        name = species;
        status = new Status();
        type = new Type[2];
    }
    
    public String getName() {
        return name;
    }
    
    protected boolean hasStatus(Status.LoneStatus status) {
        return this.status.getLoneStatus().equals(status);
    }
    
    public boolean hasStatus(Status.MixStatus status) {
        
        ArrayList<Status.MixStatus> mixStatus = this.status.getMixStatus();
        
        for (int i = 0; i < mixStatus.size(); i++)
            if (mixStatus.get(i).equals(status))
                return true;
        
        return false;
    }
    
    public Type[] getType() {
        return type;
    }
}