package elitefour.pokemonduel.moves;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class MultiHitMove extends DamageMove {
    
    private final int minHits;
    private final int maxHits;
    
    public MultiHitMove(String name) {
        super(name);
        
        int tempMinHits = -1;
        int tempMaxHits = -1;
        
        try {
            File file = new File(Move.MOVE_DIRECTORY);
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
                            tempMinHits = Integer.parseInt(line);
                            break;
                        case 1:
                            tempMaxHits = Integer.parseInt(line);
                            break;
                    }
                }
            }
        } catch (IOException error) {
            error.printStackTrace(System.out);
        }
        
        this.minHits = tempMinHits;
        this.maxHits = tempMaxHits;
    }
    
    public int hits() {
        Random rng = new Random();
        int hits = rng.nextInt(maxHits - 1) + minHits;
        return hits;
    }
    
    public static String hitText(int hits) {
        return "Hit " + hits + " times!";
    }
}