package elitefour.pokemonduel.moves;

import elitefour.pokemonduel.battle.Battle;
import elitefour.pokemonduel.battle.Pokemon;
import elitefour.pokemonduel.battle.Type;
import elitefour.pokemonduel.util.Fraction;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public abstract class Move {
    
    private final static String[] PLUS_PRIORITY_MOVES = new String[]{"Bide", "Quick Attack"};
    private final static String[] MINUS_PRIORITY_MOVES = new String[]{"Counter"};
    
    protected final static String MOVE_DIRECTORY = "resources\\data\\moves.txt";
    
    public enum Category {
        PHYSICAL,
        SPECIAL,
        STATUS
    }
    
    private final String name;
    private final Type type;
    private final Category category;
    private final Fraction PP;
    private final int power;
    private final int accuracy;
    
    public Move(String name) {
        
        Type tempType = Type.NONE;
        Category tempCategory = Category.STATUS;
        int tempPP = -1;
        int tempPower = -1;
        int tempAccuracy = -1;
        
        try {
            File file = new File(MOVE_DIRECTORY);
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
                            tempPP = Integer.parseInt(line);
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
        this.PP = new Fraction(tempPP, tempPP);
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
    
    public int PP() {
        return PP.current();
    }
    
    public int maxPP() {
        return PP.maximum();
    }
    
    public void addPP(int addition) {
        
        if (PP() + addition <= maxPP())
            PP.setCurrent(PP() + addition);
        else
            PP.setCurrent(maxPP());
    }
    
    public void deductPP(int deduction) {
        
        if (PP() - deduction >= 0)
            PP.setCurrent(PP() - deduction);
        else
            PP.setCurrent(0);
    }
    
    public int power() {
        return power;
    }
    
    public int accuracy() {
        return accuracy;
    }
    
    public boolean isHit(Pokemon user, Pokemon target) {
        double hitChance = hitChance(user, target);
        return Battle.checkOdds(hitChance);
    }
    
    private double hitChance(Pokemon user, Pokemon target) {
        
        if (accuracy == 0)
            return 100;
        
        else {
            double userAccuracy = user.hiddenStat(Pokemon.Stat.ACCURACY);
            double targetEvasion = target.hiddenStat(Pokemon.Stat.EVASION);
            return userAccuracy * this.accuracy * targetEvasion;
        }
    }
    
    public abstract int use(Pokemon user, Pokemon target);
    
    public String attemptText(String user) {
        return user + " used " + name + "!";
    }
    
    public String missText(String user) {
        return user + "'s attack missed!";
    }
    
    public static String comparePriority(Move one, Move two) {
        
        if (Arrays.asList(PLUS_PRIORITY_MOVES).contains(one.name) &&
                Arrays.asList(PLUS_PRIORITY_MOVES).contains(two.name)) {
            
            boolean heads = Battle.checkOdds(50); // 50% chance to return true.
            
            if (heads)
                return "one";
            else
                return "two";
        }
        
        else if (Arrays.asList(PLUS_PRIORITY_MOVES).contains(one.name) ||
                Arrays.asList(MINUS_PRIORITY_MOVES).contains(two.name))
            return "one";
        
        else if (Arrays.asList(PLUS_PRIORITY_MOVES).contains(two.name) ||
                Arrays.asList(MINUS_PRIORITY_MOVES).contains(one.name))
            return "two";
        
        else
            return "none";
    }
}
