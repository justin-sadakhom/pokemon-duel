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
    private final byte maxPP;
    private final byte power;
    private final byte accuracy;
    
    private byte currentPP;
    
    public Move(String name, Type type, Category category,
            byte pp, byte power, byte accuracy) {
        
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
    
    public byte maxPP() {
        return maxPP;
    }
    
    public byte PP() {
        return currentPP;
    }
    
    public void addPP(byte addition) {
        
        if (currentPP + addition <= maxPP)
            currentPP += addition;
    }
    
    public void deductPP(byte deduction) {
        
        if (currentPP - deduction >= 0)
            currentPP -= deduction;
    }
    
    public byte power() {
        return power;
    }
    
    public byte accuracy() {
        return accuracy;
    }
}
