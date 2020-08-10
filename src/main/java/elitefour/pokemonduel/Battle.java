package elitefour.pokemonduel;

import java.util.Random;

public class Battle {
    
    public enum Action {
        ATTACK,
        SWITCH
    }
    
    private final Trainer player, rival;
    private final GUI gui;
    
    private Battle(Pokemon[] firstTeam, Pokemon[] secondTeam) {
        
        player = new Trainer("Player", firstTeam);
        rival = new Trainer("Rival", secondTeam);
        gui = new GUI(firstTeam, secondTeam);
    }
    
    private void chooseLead() {
        
        gui.displayText("How will you start?");
        gui.waitForClick();
        
        int slot = gui.getButtonChoice();
        Random rng = new Random();
        
        player.setActive(player.team(slot - 4));
        rival.setActive(rival.team(rng.nextInt(rival.team().length)));
        
        gui.revealStatusBars();
    }
    
    private void loopBattle() {
        
        chooseLead();
        gui.update(player.active(), rival.active());
        
        while (stillStanding(player.team()) && stillStanding(rival.team())) {
            
            getPlayerAction();
            getRivalAction();
            playTurn();
        }
    }
    
    private boolean stillStanding(Pokemon[] team) {
        
        for (Pokemon pokemon : team)
            if (pokemon.currentHealth() > 0)
                return true;
        
        return false;
    }
    
    private void getPlayerAction() {
        
        Action action;

        gui.displayText("What will you do?");
        gui.waitForClick();
        
        int slot = gui.getButtonChoice();
        
        if (slot > 3) {
            action = Action.SWITCH;
            slot -= 4; // Offset by 4 because there are more than 4 buttons.
        }
        else
            action = Action.ATTACK;

        while ((action == Action.SWITCH && 
                invalidSwitch(player.team(), player.active(), slot)) ||
                (action == Action.ATTACK &&
                invalidMove(player.active(), slot))) {

            if (action == Action.SWITCH && player.team(slot).isFainted()) 

                gui.displayText(
                    "But " + player.team(slot).name() + " is out of energy!",
                    "Select a different Pokemon to send in."
                );

            else if (action == Action.SWITCH &&
                    player.team(slot) == player.active())

                gui.displayText(
                    player.active().name() + " is already in battle!",
                    "Select a different Pokemon to send in."
                );

            else if (action == Action.ATTACK &&
                    player.active().moves(slot).PP() == 0)

                gui.displayText(
                    "There's no PP left for this move!",
                    "Select a different move."
                );

            gui.waitForClick();
        }
        
        player.setAction(action);
        player.setChoice(slot);
    }
    
    private void getRivalAction() {
        
        Random rng = new Random();
        int numMoves = 0;
        
        for (int i = 0; i < rival.active().moves().length; i++)
            if (rival.active().moves(i) != null)
                numMoves += 1;
        
        int rivalChoice = rng.nextInt(numMoves);

        while (rival.active().moves()[rivalChoice].PP() == 0)
            rivalChoice = rng.nextInt(numMoves);
        
        rival.setAction(Action.ATTACK);
        rival.setChoice(rivalChoice);
    }
    
    private boolean invalidSwitch(Pokemon[] team, Pokemon current, int slot) {
        return team[slot].isFainted() || team[slot] == current;
    }
    
    private boolean invalidMove(Pokemon current, int slot) {
        return current.moves(slot).PP() == 0;
    }
    
    private void playTurn() {
        
        Trainer first, second;
        Pokemon faster = Pokemon.compareSpeed(player.active(), rival.active());
        
        if (player.active() == faster) {
            first = player;
            second = rival;
        }
        else {
            first = rival;
            second = player;
        }
        
        // Faster player switches.
        if (first.action() == Action.SWITCH) {
            attemptSwitch(first, first.team(first.choice()));
            gui.update(player.active(), rival.active());
        }
        
        // Slower player switches.
        if (second.action() == Action.SWITCH) {
            attemptSwitch(second, second.team(second.choice()));
            gui.update(player.active(), rival.active());
        }
        
        // Faster player attacks.
        if (first.action() == Action.ATTACK) {
            attemptAttack(first.active(), first.choice(), second.active());
            gui.update(player.active(), rival.active());

            if (second.active().isFainted()) {
                gui.displayText(second.active().name() + " fainted!");
                gui.delay(2);

                if (!stillStanding(second.team()))
                    
                    gui.displayText(
                        second.name() + " is out of usable Pokemon!",
                        first.name() + " wins!"
                    );

                else {
                    int slot;
                    
                    if (second.name().equals("Player")) {
                        
                        gui.displayText("Select a Pokemon to send in.");
                        gui.waitForClick();
                        slot = gui.getButtonChoice() - 4;

                        while (second.team(slot).isFainted()) {
                            
                            gui.displayText("But " + second.team(slot).name() +
                                "is out of energy!",
                                "Select a Pokemon to send in."
                            );
                            gui.waitForClick();
                            slot = gui.getButtonChoice() - 4;
                        }
                    }
                    
                    else {
                        Random rng = new Random();
                        slot = rng.nextInt(second.team().length);

                        while (second.team(slot).isFainted())
                            slot = rng.nextInt(second.team().length);
                    }
                    
                    attemptSwitch(second, second.team(slot));
                }
            }
        }
        
        // Slower player attacks.
        if (second.action() == Action.ATTACK) {
            attemptAttack(second.active(), second.choice(), first.active());
            gui.update(player.active(), rival.active());

            if (faster.isFainted()) {
                gui.displayText(first.active().name() + " fainted!");
                gui.delay(2);

                if (!stillStanding(first.team()))
                    
                    gui.displayText(
                        first.name() + " is out of usable Pokemon!",
                        second.name() + " wins!"
                    );

                else {
                    int slot;
                    
                    if (first.name().equals("Player")) {
                        
                        gui.displayText("Select a Pokemon to send in.");
                        gui.waitForClick();
                        slot = gui.getButtonChoice() - 4;

                        while (first.team(slot).isFainted()) {
                            gui.displayText(
                                "But " + first.team(slot).name() +
                                "is out of energy!",
                                "Select a Pokemon to send in."
                            );
                            gui.waitForClick();
                            slot = gui.getButtonChoice() - 4;
                        }
                    }
                    
                    else {
                        Random rng = new Random();
                        slot = rng.nextInt(first.team().length);

                        while (first.team(slot).isFainted())
                            slot = rng.nextInt(first.team().length);
                    }
                    
                    attemptSwitch(first, first.team(slot));
                }
            }
        }
    }
    
    private void attemptSwitch(Trainer trainer, Pokemon in) {
        
        gui.displayText(
            trainer.name() + " withdraws " + trainer.active().name() + "!"
        );
        gui.delay(2);

        trainer.setActive(in);
        
        if (trainer.name().equals("Player"))
            gui.setPlayerPokemon(in);
        else
            gui.setRivalPokemon(in);
        
        gui.displayText(
            trainer.name() + " sends out " + trainer.active().name() + "!"
        );
        gui.delay(2);
    }
    
    private void attemptAttack(Pokemon attacker, int slot, Pokemon defender) {
        
        Object[] result = attacker.immobilizedBy();
        Status obstacle = (Status)result[0];
        boolean blocked = (boolean)result[1];
        
        // Attacker had no status effects.
        if (obstacle.isEmpty())
            processAttack(attacker, slot, defender);
        
        // Display battle text for attacker breaking through immobilization.
        else if (!blocked) {
                
            // Check volatile status.
            switch (obstacle.loneStatus()) {

                case FREEZE:
                    gui.displayText(attacker.name() + " thawed out!");

                case SLEEP:
                    gui.displayText(attacker.name() + " woke up!");
            }
            
            // Check non-volatile status.
            if (!obstacle.mixStatus().isEmpty()) {
                
                if (obstacle.mixStatus().contains(Status.MixStatus.CONFUSION)) {
                    gui.displayText(attacker.name() + " is confused!");
                    gui.delay(2);
                    gui.displayText(attacker.name() + " snapped out of confusion!");
                }
                
                if (obstacle.mixStatus().contains(Status.MixStatus.INFATUATION)) {
                    gui.displayText(attacker.name() + " is in love",
                            "with the foe's " + defender.name() + "!");
                    gui.delay(2);
                }
            }
            
            processAttack(attacker, slot, defender);
        }
        
        // Display battle text for attacker being immobilized.
        else {
            
            // Check volatile status.
            if (obstacle.loneStatus() != Status.LoneStatus.NONE) {
                
                switch (obstacle.loneStatus()) {
                    
                    case FREEZE:
                        gui.displayText(attacker.name() + " is frozen solid!");
                        
                    case PARALYSIS:
                        gui.displayText(attacker.name() + " is paralyzed!",
                                "It can't move!");
                        
                    case SLEEP:
                        gui.displayText(attacker.name() + " is fast asleep.");
                }
            }
            
            // Check non-volatile status.
            else {
                
                if (obstacle.mixStatus().contains(Status.MixStatus.RECHARGE))
                    gui.displayText(attacker.name() + " must recharge!");
                
                if (obstacle.mixStatus().contains(Status.MixStatus.CONFUSION)) {
                    
                    gui.displayText(attacker.name() + " is confused!");
                    gui.delay(2);
                    gui.displayText("It hurt itself in confusion!");
                    
                    // Calculate and apply self-inflicted damage.
                    defender.deductHealth(DamageMove.confusionDamage(defender));
                }
                
                if (obstacle.mixStatus().contains(Status.MixStatus.INFATUATION)) {
                    gui.displayText(attacker.name() + " is in love"
                            , "with the foe's " + defender.name() + "!");
                    gui.delay(2);
                    gui.displayText(attacker.name() + " is immobilized by love!");
                    gui.update(player.active(), rival.active());
                }
            }
        }
    }
    
    private void processAttack(Pokemon user, int slot, Pokemon target) {
        
        Move move = user.moves()[slot];
        gui.displayText(Move.attemptText(user.name(), move.name()));
        gui.delay(2);
        
        // Damaging move.
        if (move instanceof DamageMove) {
            int hits = 1;
            
            if (move instanceof MultiHitMove)
                hits = ((MultiHitMove)move).hits();
            
            Random rng = new Random();
            
            // Move misses.
            if (rng.nextInt(100) > move.hitChance(user, target)) {
                gui.displayText(Move.missText(user.name()));
                gui.delay(2);
            }
            
            // Move lands.
            else {
                
                for (int i = 0; i < hits; i++) {
                    int damage = user.useMove(slot, target);

                    double multiplier = 
                        DamageMove.typeAdvantage(move.type(), target.type());

                    if (multiplier != 1.0)
                        gui.displayText(DamageMove.hitText(move.type(),
                                target.type()));

                    if (((DamageMove)move).isCrit())
                        gui.displayText(DamageMove.critText());

                    if (move instanceof DamageDebuff) {

                        boolean success = ((DamageDebuff)move).
                                useSecondary(target, user);
                        gui.displayText(DamageDebuff.hitText(target.name(),
                                (DamageDebuff)move, success));
                    }

                    else if (move instanceof DrainMove) {
                        ((DrainMove)move).useSecondary(user, target, damage);
                        gui.displayText(DrainMove.hitText(target.name()));
                    }
                    
                    else if (move instanceof MultiHitMove)
                        gui.displayText(MultiHitMove.hitText(hits));
                }
            }
        }
        
        // Non-damaging move.
        else {
            
            Random rng = new Random();
            int result = user.useMove(slot, target);

            // Move misses.
            if (rng.nextInt(100) < move.hitChance(user, target))
                gui.displayText(Move.missText(user.name()));
            
            // Move hits.
            else {
                
                if (move instanceof Buff) {
                    boolean success = result == 1;
                    gui.displayText(Buff.hitText(user.name(), (Buff)move, success));
                }
            }
        }
    }
    
    private static void startBattle() {
        
        // DEMO
        Pokemon[] teamOne = new Pokemon[1];
        teamOne[0] = new Pokemon("Venusaur");
        teamOne[0].setMove(new DamageMove("Mega Drain"), 1);
        teamOne[0].setMove(new Buff("Growth"), 2);
        teamOne[0].setMove(new DamageMove("Vine Whip"), 3);
        teamOne[0].setMove(new DamageMove("Tackle"), 4);
        
        Pokemon[] teamTwo = new Pokemon[1];
        teamTwo[0] = new Pokemon("Charizard");
        teamTwo[0].setMove(new DamageMove("Flamethrower"), 1);
        
        Battle game = new Battle(teamOne, teamTwo);
        game.loopBattle();
    }
    
    public static void main(String[] args) {
        startBattle();
    }
}