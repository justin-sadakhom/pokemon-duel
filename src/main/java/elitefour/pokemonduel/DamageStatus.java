package elitefour.pokemonduel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class DamageStatus extends DamageMove {
    
    private final Status appliedStatus;
    private final int effectChance;
    
    public DamageStatus(String name) {
        
        super(name);
        
        Status tempStatus = new Status();
        int tempChance = -1;
        
        try {
            File file = new File("resources\\data\\moves.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));

            String line = "";

            while (line != null && !line.equals(name))
                line = reader.readLine();

            if (line.equals(name)) {
                
                for (int j = 0; j < 5; j++)
                    reader.readLine();
                
                for (int i = 0; i < 2; i++) {
                    line = reader.readLine();
                    
                    switch (i) {
                        case 0:
                            String status = line.toUpperCase();
                            
                            if (Status.containsLone(status))
                                tempStatus.setLoneStatus(
                                        Status.LoneStatus.valueOf(status)
                                );
                            else
                                tempStatus.addMixStatus(
                                        Status.MixStatus.valueOf(status)
                                );
                            
                            break;
                        case 1:
                            tempChance = Integer.parseInt(line);
                            break;
                    }
                }
            }
        } catch (IOException error) {
            error.printStackTrace(System.out);
        }
        
        appliedStatus = tempStatus;
        effectChance = tempChance;
    }
    
    @Override
    public int use(Pokemon user, Pokemon target) {
        return super.use(user, target);
    }
    
    public boolean useSecondary(Pokemon user, Pokemon target) {
        
        Random rng = new Random();
        boolean success = true;
        
        if (rng.nextInt(100) < effectChance)
            success = applyEffect(target);
        
        return success;
    }
    
    protected boolean applyEffect(Pokemon target) {
        if (appliedStatus.mixStatus().isEmpty())
            return target.setStatus(appliedStatus.loneStatus());
        else
            return target.setStatus(appliedStatus.mixStatus().get(0));
    }
    
    public static String hitText(Status.LoneStatus status, Pokemon target,
            boolean success) {
        
        if (!success)
            return "...but it failed.";
        
        switch (status) {
            case BURN:
                return target.name() + " was burned!";
            case FREEZE:
                return target.name() + " was frozen solid!";
            case PARALYSIS:
                return target.name() + " was paralyzed!";
            case POISON:
                return target.name() + " was poisoned!";
            case TOXIC:
                return target.name() + " was badly poisoned!";
            case SLEEP:
                return target.name() + " fell asleep!";
            default: // This should never be reached.
                return "";
        }
    }
    
    public static String hitText(Status.MixStatus status, Pokemon target,
            boolean success) {
        
        if (!success)
            return "...but it failed.";
        
        switch (status) {
            case CONFUSION:
                return target.name() + " became confused!";
            default:
                return "";
        }
    }
}
