package elitefour.pokemonduel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public abstract class Move {
    
    public enum Category {
        PHYSICAL,
        SPECIAL,
        STATUS
    }
    
    private final String name;
    private final Type type;
    private final Category category;
    private final int maxPP;
    private final int power;
    private final int accuracy;
    private int currentPP;
    
    public Move(String name) {
        
        Type tempType = Type.NONE;
        Category tempCategory = Category.STATUS;
        int pp = -1;
        int tempPower = -1;
        int tempAccuracy = -1;
        
        try {
            File file = new File("resources\\data\\moves.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));

            String line = "";

            while (line != null && !line.equals(name))
                line = reader.readLine();

            if (line.equals(name)) {
                
                for (int i = 0; i < 5; i++) {
                    line = reader.readLine();
                    
                    switch (i) {
                        case 0:
                            tempType = Type.valueOf(line.toUpperCase());
                            break;
                        case 1:
                            tempCategory = Category.valueOf(line.toUpperCase());
                            break;
                        case 2:
                            pp = Integer.parseInt(line);
                            break;
                        case 3:
                            if (line.equals("-"))
                                tempPower = 0;
                            else
                                tempPower = Integer.parseInt(line);
                            break;
                        case 4:
                            if (line.equals("-"))
                                tempAccuracy = 0;
                            else
                                tempAccuracy = Integer.parseInt(line);
                            break;
                    }
                }
            }
        } catch (IOException error) {
            error.printStackTrace(System.out);
        }
        
        this.name = name;
        this.type = tempType;
        this.category = tempCategory;
        this.maxPP = this.currentPP = pp;
        this.power = tempPower;
        this.accuracy = tempAccuracy;
    }
    
    public String name() {
        return name;
    }
    
    public Type type() {
        return type;
    }
    
    public Category category() {
        return category;
    }
    
    public int maxPP() {
        return maxPP;
    }
    
    public int PP() {
        return currentPP;
    }
    
    public void addPP(int addition) {
        
        if (currentPP + addition <= maxPP)
            currentPP += addition;
        else
            currentPP = maxPP;
    }
    
    public void deductPP(int deduction) {
        
        if (currentPP - deduction >= 0)
            currentPP -= deduction;
        else
            currentPP = 0;
    }
    
    public int power() {
        return power;
    }
    
    private double hitChance(Pokemon user, Pokemon target) {
        
        if (accuracy == 0)
            return 100;
        else
            return user.hiddenStat(Pokemon.Stat.ACCURACY) * accuracy * 
                    target.hiddenStat(Pokemon.Stat.EVASION);
    }
    
    public boolean isHit(Pokemon user, Pokemon target) {
        Random rng = new Random();
        return rng.nextInt(100) < hitChance(user, target);
    }
    
    public static String attemptText(String user, String moveName) {
        return user + " used " + moveName + "!";
    }
    
    public static String missText(String user) {
        return user + "'s attack missed!";
    }
    
    public abstract int use(Pokemon user, Pokemon target);
}
