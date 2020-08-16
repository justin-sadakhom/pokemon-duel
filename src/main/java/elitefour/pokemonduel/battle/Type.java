package elitefour.pokemonduel.battle;

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
    
    public static final Map<Type, Type[]> weaknesses = Map.ofEntries(
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
    
    public static final Map<Type, Type[]> resistances = Map.ofEntries(
        entry(Type.NORMAL, new Type[0]),
        entry(Type.FIGHTING, new Type[]{Type.ROCK, Type.BUG, Type.DARK}),
        entry(Type.FLYING, new Type[]{Type.FIGHTING, Type.BUG, Type.GRASS}),
        entry(Type.POISON, new Type[]{Type.FIGHTING, Type.POISON, Type.BUG,
            Type.GRASS}),
        entry(Type.GROUND, new Type[]{Type.POISON, Type.ROCK}),
        entry(Type.ROCK, new Type[]{Type.NORMAL, Type.FLYING, Type.POISON,
            Type.FIRE}),
        entry(Type.BUG, new Type[]{Type.FIGHTING, Type.GROUND, Type.GRASS}),
        entry(Type.GHOST, new Type[]{Type.POISON, Type.BUG}),
        entry(Type.STEEL, new Type[]{Type.NORMAL, Type.FLYING, Type.ROCK,
        Type.BUG, Type.STEEL, Type.GRASS, Type.PSYCHIC, Type.ICE, Type.DRAGON}),
        entry(Type.FIRE, new Type[]{Type.BUG, Type.STEEL, Type.FIRE, Type.GRASS,
        Type.ICE}),
        entry(Type.WATER, new Type[]{Type.STEEL, Type.FIRE, Type.WATER,
            Type.ICE}),
        entry(Type.GRASS, new Type[]{Type.GROUND, Type.WATER, Type.GRASS,
            Type.ELECTRIC}),
        entry(Type.ELECTRIC, new Type[]{Type.FLYING, Type.STEEL,
            Type.ELECTRIC}),
        entry(Type.PSYCHIC, new Type[]{Type.FIGHTING, Type.PSYCHIC}),
        entry(Type.ICE, new Type[]{Type.ICE}),
        entry(Type.DRAGON, new Type[]{Type.FIRE, Type.WATER, Type.GRASS,
            Type.ELECTRIC}),
        entry(Type.DARK, new Type[]{Type.GHOST, Type.DARK})
    );
    
    public static final Map<Type, Type[]> immunities = Map.ofEntries(
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