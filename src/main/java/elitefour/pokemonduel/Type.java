package elitefour.pokemonduel;

import java.util.Map;
import static java.util.Map.entry;

public enum Type {
    NONE,
    NORMAL,
    FIGHTING,
    FLYING,
    POISON,
    GROUND,
    ROCK,
    BUG,
    GHOST,
    STEEL,
    FIRE,
    WATER,
    GRASS,
    ELECTRIC,
    PSYCHIC,
    ICE,
    DRAGON,
    DARK;
    
    private static final Map<Type, Type[]> weaknesses = Map.ofEntries(
        entry(Type.NORMAL, new Type[]{Type.FIGHTING}),
        entry(Type.FIGHTING, new Type[]{Type.FLYING, Type.PSYCHIC}),
        entry(Type.FLYING, new Type[]{Type.ROCK, Type.ELECTRIC, Type.ICE}),
        entry(Type.POISON, new Type[]{Type.GROUND, Type.PSYCHIC}),
        entry(Type.GROUND, new Type[]{Type.WATER, Type.GRASS, Type.ICE}),
        entry(Type.ROCK, new Type[]{Type.FIGHTING, Type.GROUND, Type.STEEL,
            Type.WATER, Type.GRASS}),
        entry(Type.BUG, new Type[]{Type.FLYING, Type.ROCK, Type.FIRE}),
        entry(Type.GHOST, new Type[]{Type.GHOST}),
        entry(Type.STEEL, new Type[]{Type.FIGHTING, Type.GROUND, Type.FIRE}),
        entry(Type.FIRE, new Type[]{Type.GROUND, Type.ROCK, Type.WATER}),
        entry(Type.WATER, new Type[]{Type.GRASS, Type.ELECTRIC}),
        entry(Type.GRASS, new Type[]{Type.FLYING, Type.POISON, Type.BUG,
            Type.FIRE, Type.ICE}),
        entry(Type.ELECTRIC, new Type[]{Type.GROUND}),
        entry(Type.PSYCHIC, new Type[]{Type.BUG, Type.GHOST, Type.DARK}),
        entry(Type.ICE, new Type[]{Type.FIGHTING, Type.ROCK, Type.STEEL,
            Type.FIRE}),
        entry(Type.DRAGON, new Type[]{Type.ICE, Type.DRAGON}),
        entry(Type.DARK, new Type[]{Type.FIGHTING, Type.BUG})
    );
    
    private static final Map<Type, Type[]> resistances = Map.ofEntries(
        entry(Type.NORMAL, new Type[0]),
        entry(Type.FIGHTING, new Type[]{Type.ROCK, Type.BUG, Type.DARK})
        // INCOMPLETE SET!
    );
    
    private static final Map<Type, Type[]> immunities = Map.ofEntries(
        entry(Type.NORMAL, new Type[]{Type.GHOST}),
        entry(Type.FLYING, new Type[]{Type.GROUND}),
        entry(Type.GROUND, new Type[]{Type.ELECTRIC}),
        entry(Type.GHOST, new Type[]{Type.NORMAL}),
        entry(Type.STEEL, new Type[]{Type.POISON}),
        entry(Type.DARK, new Type[]{Type.PSYCHIC})
    );
    
    public Type[] weaknesses() {
        return weaknesses.get(this);
    }
    
    public Type[] resistances() {
        return resistances.get(this);
    }
    
    public Type[] immunities() {
        return immunities.get(this);
    }
}