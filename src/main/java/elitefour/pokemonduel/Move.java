package elitefour.pokemonduel;

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
    
    public Move(String name, Type type, Category category,
            int pp, int power, int accuracy) {
        
        this.name = name;
        this.type = type;
        this.category = category;
        this.maxPP = pp;
        this.currentPP = pp;
        this.power = power;
        this.accuracy = accuracy;
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
    
    public int accuracy() {
        return accuracy;
    }
    
    public abstract Object[] use(Pokemon user, Pokemon target);
}
