package elitefour.pokemonduel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Buff extends StatusMove {
    
    private final Pokemon.Stat affectedStat;
    private final int stages;
    
    public Buff(String name) {
        
        super(name);
        
        Pokemon.Stat tempStat = Pokemon.Stat.CRITICAL;
        int tempStage = -1;
        
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
                            tempStat = Pokemon.Stat.valueOf(line.toUpperCase());
                            break;
                        case 1:
                            tempStage = Integer.parseInt(line);
                    }
                }
            }
        } catch (IOException error) {
            error.printStackTrace(System.out);
        }
        
        this.affectedStat = tempStat;
        this.stages = tempStage;
    }
    
    @Override
    public int use(Pokemon user, Pokemon target) {
        
        deductPP(1);
        
        if (user.raiseStatStage(affectedStat, stages))
            return 1;
        else
            return 0;
    }
    
    public int stages() {
        return stages;
    }
    
    public String affectedStat() {
        return affectedStat.name();
    }
    
    public static String hitText(String name, Buff move, boolean success) {
        
        String message = name + "'s " + move.affectedStat().toLowerCase();
        
        // Affected stat stage is already at max.
        if (!success)
            return message + " won't go any higher!";

        // There's still room for change.
        else {

            switch (move.stages()) {
                case 1:
                    return message + " rose!";
                default: // case 2
                    return message + " rose sharply!";
            }
        }
    }
}