package elitefour.pokemonduel.util;

public class Fraction {
    
    private int numerator;
    private int denominator;
    
    public Fraction(int numerator, int denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
    }
    
    public int current() {
        return numerator;
    }
    
    public void setCurrent(int value) {
        numerator = value;
    }
    
    public int maximum() {
        return denominator;
    }
    
    public void setMaximum(int value) {
        denominator = value;
    }
}
