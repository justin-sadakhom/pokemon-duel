package elitefour.pokemonduel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.Border;

public class Battle implements ActionListener {
    
    public enum Action {
        ATTACK,
        SWITCH
    }
    
    /* CONTROLS */
    
    static Boolean mouseClicked = false;
    static int buttonChoice = -1;
    
    /* GUI COMPONENTS */
    
    private final JFrame window;
    private final JLabel playerPokemon, rivalPokemon;
    private final InfoField playerInfo, rivalInfo;
    private final JLabel battlefield, playerBase, rivalBase;
    private final JLabel textContainer, battleText1, battleText2;
    private final JLabel attackLabel, switchLabel;
    private final JLabel[] playerTeamDisplay = new JLabel[6];
    private final JLabel[] rivalTeamDisplay = new JLabel[6];
    private final JButton[] moves = new JButton[4];
    private final JButton[] switches = new JButton[6];
    private final Border BLACK_BORDER = 
            BorderFactory.createLineBorder(Color.BLACK, 1);
    
    /* SPRITES */
    
    private final ImageIcon POKEBALL = 
            new ImageIcon("resources\\sprites\\icon\\pokeball.png");
    private final ImageIcon GRAY_BALL = 
            new ImageIcon("resources\\sprites\\icon\\grayball.png");
    private final ImageIcon FIELD_BG = 
            new ImageIcon("resources\\background\\battlebgfield.png");
    private final ImageIcon ALLY_BASE = 
            new ImageIcon("resources\\background\\playerbaseField.png");
    private final ImageIcon ENEMY_BASE = 
            new ImageIcon("resources\\background\\enemybaseField.png");
    
    /* BATTLE COMPONENTS */
    
    private final Pokemon[] playerTeam;
    private final Pokemon[] rivalTeam;
    private Pokemon playerMon, rivalMon;
    
    private Battle(Pokemon[] firstTeam, Pokemon[] secondTeam) {
        
        playerTeam = firstTeam;
        rivalTeam = secondTeam;
        
        Font font = null;
        
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("resources\\fonts\\pkmndp.ttf"));
        } catch (FontFormatException | IOException error) {
            error.printStackTrace(System.out);
        }
        
        window = new JFrame("Pokémon Duel");
        window.setLayout(null);
        window.setSize(660, 560);
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        textContainer = new JLabel();
        battleText1 = new JLabel("");
        battleText2 = new JLabel("");
        
        battlefield = new JLabel();
        playerBase = new JLabel();
        rivalBase = new JLabel();
        
        playerPokemon = new JLabel();
        rivalPokemon = new JLabel();
        
        playerInfo = new InfoField(font, true);
        rivalInfo = new InfoField(font, false);
        
        attackLabel = new JLabel("<html><font size=5><font color=#7b241c><i>Attack</i></font></html>");
        switchLabel = new JLabel("<html><font size=5><font color=#154360><i>Switch</i></font></html>");
        
        for (int i = 0; i < 4; i++)
            moves[i] = new JButton();
        
        for (int i = 0; i < 6; i++) {
            switches[i] = new JButton();
            playerTeamDisplay[i] = new JLabel();
            rivalTeamDisplay[i] = new JLabel();
        }
        
        textContainer.setBounds(66, 307, 512, 63);
        textContainer.setBorder(BLACK_BORDER);
        textContainer.setOpaque(true);
        textContainer.setBackground(Color.white);
        
        battleText1.setBounds(86, 304, 472, 31);
        battleText1.setFont(font);
        
        battleText2.setBounds(86, 336, 472, 31);
        battleText2.setFont(font);
        
        attackLabel.setBounds(10, 375, 625, 20);
        switchLabel.setBounds(10, 450, 625, 20);
        
        battlefield.setBounds(66, 19, 512, 288);
        battlefield.setIcon(FIELD_BG);
        
        playerBase.setBounds(18, 241, 512, 66);
        playerBase.setIcon(ALLY_BASE);
        
        rivalBase.setBounds(322, 103, 256, 128);
        rivalBase.setIcon(ENEMY_BASE);
        
        playerPokemon.setBounds(150, 147, 160, 160);
        rivalPokemon.setBounds(367, 44, 160, 160);
        
        for (int i = 0; i < 6; i++) {
            
            int alignment = (int)Component.CENTER_ALIGNMENT;
            
            playerTeamDisplay[i].setIcon(POKEBALL);
            playerTeamDisplay[i].setHorizontalAlignment(alignment);
            rivalTeamDisplay[i].setIcon(POKEBALL);
            rivalTeamDisplay[0].setHorizontalAlignment(alignment);
        }
        
        playerTeamDisplay[0].setBounds(10, 19, 43, 43);
        playerTeamDisplay[1].setBounds(10, 68, 43, 43);
        playerTeamDisplay[2].setBounds(10, 117, 43, 43);
        playerTeamDisplay[3].setBounds(10, 166, 43, 43);
        playerTeamDisplay[4].setBounds(10, 215, 43, 43);
        playerTeamDisplay[5].setBounds(10, 264, 43, 43);
        
        rivalTeamDisplay[0].setBounds(592, 19, 43, 43);
        rivalTeamDisplay[1].setBounds(592, 68, 43, 43);
        rivalTeamDisplay[2].setBounds(592, 117, 43, 43);
        rivalTeamDisplay[3].setBounds(592, 166, 43, 43);
        rivalTeamDisplay[4].setBounds(592, 215, 43, 43);
        rivalTeamDisplay[5].setBounds(592, 264, 43, 43);
        
        for (int i = 0; i < 4; i++) {
            moves[i].setFocusPainted(false);
            moves[i].addActionListener(this);
        }
        
        moves[0].setBounds(10, 400, 152, 40);
        moves[1].setBounds(168, 400, 152, 40);
        moves[2].setBounds(326, 400, 152, 40);
        moves[3].setBounds(484, 400, 152, 40);
        
        for (int i = 0; i < 6; i++) {
            switches[i].setFocusPainted(false);
            switches[i].addActionListener(this);
        }
        
        switches[0].setBounds(10, 475, 100, 33);
        switches[1].setBounds(115, 475, 100, 33);
        switches[2].setBounds(220, 475, 100, 33);
        switches[3].setBounds(325, 475, 100, 33);
        switches[4].setBounds(430, 475, 100, 33);
        switches[5].setBounds(535, 475, 100, 33);
        
        for (JButton move : moves)
            window.add(move);
        
        for (int i = 0; i < switches.length; i++) {
            window.add(switches[i]);
            window.add(playerTeamDisplay[i]);
            window.add(rivalTeamDisplay[i]);
        }

        window.add(battleText1);
        window.add(battleText2);
        window.add(textContainer);
        window.add(attackLabel);
        window.add(switchLabel);
        window.add(playerInfo);
        window.add(rivalInfo);
        window.add(playerPokemon);
        window.add(rivalPokemon);
        window.add(playerBase);
        window.add(rivalBase);
        window.add(battlefield);
        
        window.setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent event) {
        
        if (event.getSource() == moves[0]) {
            mouseClicked = true;
            buttonChoice = 0;
        }

        else if (event.getSource() == moves[1]) {
            mouseClicked = true;
            buttonChoice = 1;
        }

        else if (event.getSource() == moves[2]) {
            mouseClicked = true;
            buttonChoice = 2;
        }

        else if (event.getSource() == moves[3]) {
            mouseClicked = true;
            buttonChoice = 3;
        }

        else if (event.getSource() == switches[0]) {
            mouseClicked = true;
            buttonChoice = 4;
        }

        else if (event.getSource() == switches[1]) {
            mouseClicked = true;
            buttonChoice = 5;
        }

        else if (event.getSource() == switches[2]) {
            mouseClicked = true;
            buttonChoice = 6;
        }

        else if (event.getSource() == switches[3]) {
            mouseClicked = true;
            buttonChoice = 7;
        }

        else if (event.getSource() == switches[4]) {
            mouseClicked = true;
            buttonChoice = 8;
        }

        else if (event.getSource() == switches[5]) {
            mouseClicked = true;
            buttonChoice = 9;
        }
    }
    
    private void waitForClick() {
        
        while (!mouseClicked)
            System.out.print("");
        
        mouseClicked = false;
    }
    
    private void displayText(String output) {
        
        battleText1.setText("<html><font size=6>" + output + "</font></html>");
        battleText2.setText("");
        
        battleText1.repaint();
        battleText2.repaint();
    }
    
    private void displayText(String output1, String output2) {
        
        battleText1.setText("<html><font size=6>" + output1 + "</font></html>");
        battleText2.setText("<html><font size=6>" + output2 + "</font></html>");
        
        battleText1.repaint();
        battleText2.repaint();
    }
    
    private void delay(double seconds) {
        
        try {
            Thread.sleep((long)(seconds * 1000));
        } catch (InterruptedException error) {
            error.printStackTrace(System.out);
        }
    }
    
    private void revealUI() {
        playerInfo.setOpaque(true);
        rivalInfo.setOpaque(true);
    }
    
    private void updateUI() {
        
        playerInfo.update(playerMon);
        rivalInfo.update(rivalMon);
        
        for (int i = 0; i < 4; i++) {
            moves[i].setText(playerMon.moves()[i].name());
            moves[i].setToolTipText(playerMon.moves()[i].PP() + " remaining");
        }
        
        for (int i = 0; i < 6; i++)
            switches[i].setIcon(new ImageIcon("C:\\Pokemon\\sprites\\icon\\" + 
                    playerTeam[i].name().toLowerCase() + ".png"));
        
        playerPokemon.setIcon(new ImageIcon("C:\\Pokemon\\sprites\\back\\" + 
                playerMon.name().toLowerCase() + ".png"));
        rivalPokemon.setIcon(new ImageIcon("C:\\Pokemon\\sprites\\front\\" + 
                rivalMon.name().toLowerCase() + ".png"));
        
        for (int i = 0; i < 6; i++) {
            if (playerTeam[i].currentHealth() == 0)
                playerPokemon.setIcon(GRAY_BALL);
            if (rivalTeam[i].currentHealth() == 0)
                rivalPokemon.setIcon(GRAY_BALL);
        }
        
        window.repaint();
        delay(1.5);
    }
    
    private void chooseLead() {
        
        displayText("How will you start?");
        waitForClick();
        
        int slot = buttonChoice;
        Random rng = new Random();
        
        setPlayerPokemon(playerTeam[slot]);
        setRivalPokemon(rivalTeam[rng.nextInt(6)]);
        
        playerInfo.setVisible(true);
        rivalInfo.setVisible(true);
    }
    
    public void loopBattle() {
        
        chooseLead();
        revealUI();
        updateUI();
        
        while (stillStanding(playerTeam) && stillStanding(rivalTeam)) {
            
            int[] actionArray = getAction();
            Action playerAction;
            
            if (actionArray[0] == 1)
                playerAction = Action.SWITCH;
            else
                playerAction = Action.ATTACK;
            
            int rivalChoice;
            Random rng = new Random();
            rivalChoice = rng.nextInt(4);
            
            while (rivalMon.moves()[rivalChoice].PP() == 0)
                rivalChoice = rng.nextInt(4);
            
            playTurn(playerAction, actionArray[1], Action.ATTACK, rivalChoice);
        }
    }
    
    private boolean stillStanding(Pokemon[] team) {
        
        for (Pokemon pokemon : team)
            if (pokemon.currentHealth() > 0)
                return true;
        
        return false;
    }
    
    private int[] getAction() {
        
        Action action;

        displayText("What will you do?");
        waitForClick();

        if (Battle.buttonChoice > 3)
            action = Action.SWITCH;

        else
            action = Action.ATTACK;

        while ((action == Action.SWITCH &&
                    playerTeam[buttonChoice - 4].isFainted()) ||
                (action == Action.SWITCH &&
                    playerTeam[buttonChoice - 4] == playerMon) ||
                (action == Action.ATTACK &&
                    playerMon.moves()[buttonChoice].PP() == 0)) {

            if (action == Action.SWITCH &&
                    playerTeam[buttonChoice - 4].isFainted()) 

                displayText("But " + playerTeam[buttonChoice - 4].name() + 
                " is out of energy!",
                "Select a different Pokemon to send in.");

            else if (action == Action.SWITCH &&
                    playerTeam[buttonChoice - 4] == playerMon)

                displayText(playerMon.name() + " is already in battle!",
                "Select a different Pokemon to send in.");

            else if (action == Action.ATTACK &&
                    playerMon.moves()[buttonChoice].PP() == 0)

                displayText("There's no PP left for this move!",
                "Select a different move.");

            waitForClick();
        }
        
        if (action == Action.SWITCH)
            return new int[]{1, buttonChoice - 4};
        else
            return new int[]{2, buttonChoice};
    }
    
    private void playTurn(Action playerAction, int playerChoice,
            Action rivalAction, int rivalChoice) {
        
        Pokemon faster = Pokemon.compareSpeed(playerMon, rivalMon);
        
        // Player moves first.
        if (playerMon == faster) {
            
            if (playerAction == Action.SWITCH) {
                processSwitch(playerMon, playerTeam[playerChoice], "Player");
                updateUI();
            }
            
            if (rivalAction == Action.SWITCH) {
                processSwitch(rivalMon, rivalTeam[rivalChoice], "Rival");
                updateUI();
            }
            
            if (playerAction == Action.ATTACK) {
                processAttack(playerMon, playerChoice, rivalMon);
                updateUI();
                
                if (rivalMon.currentHealth() == 0) {
                    displayText(rivalMon.name() + " fainted!");
                    delay(1);
                    
                    if (!stillStanding(rivalTeam))
                        displayText("Rival is out of usable Pokemon!",
                                "Player wins!");
                    
                    else {
                        Random rng = new Random();
                        int slot = rng.nextInt(6);
                        
                        while (rivalTeam[slot].currentHealth() == 0)
                            slot = rng.nextInt(6);
                        
                        processSwitch(rivalMon, rivalTeam[slot], "Rival");
                    }
                }
            }
            
            if (rivalAction == Action.ATTACK) {
                processAttack(rivalMon, rivalChoice, playerMon);
                updateUI();
                
                if (playerMon.currentHealth() == 0) {
                    displayText(playerMon.name() + " fainted!");
                    delay(1);
                    
                    if (!stillStanding(playerTeam))
                        displayText("Player is out of usable Pokemon!",
                                "Rival wins!");
                    
                    else {
                        displayText("Select a Pokemon to send in.");
                        waitForClick();
                        int slot = buttonChoice - 4;
                        
                        while (rivalTeam[slot].currentHealth() == 0) {
                            displayText("But " + rivalTeam[slot].name() +
                                    "is out of energy!",
                                    "Select a Pokemon to send in.");
                            waitForClick();
                            slot = buttonChoice - 4;
                        }
                        
                        processSwitch(playerMon, playerTeam[slot], "Player");
                    }
                }
            }
        }
        
        // Rival moves first.
        else {
            
            if (rivalAction == Action.SWITCH) {
                processSwitch(rivalMon, rivalTeam[rivalChoice], "Rival");
                updateUI();
            }
            
            if (playerAction == Action.SWITCH) {
                processSwitch(playerMon, playerTeam[playerChoice], "Player");
                updateUI();
            }
            
            if (rivalAction == Action.ATTACK) {
                processAttack(rivalMon, rivalChoice, playerMon);
                updateUI();
                
                if (playerMon.currentHealth() == 0) {
                    displayText(playerMon.name() + " fainted!");
                    delay(1);
                    
                    if (!stillStanding(playerTeam))
                        displayText("Player is out of usable Pokemon!",
                                "Rival wins!");
                    
                    else {
                        displayText("Select a Pokemon to send in.");
                        waitForClick();
                        int slot = buttonChoice - 4;
                        
                        while (rivalTeam[slot].currentHealth() == 0) {
                            displayText("But " + rivalTeam[slot].name() +
                                    "is out of energy!",
                                    "Select a Pokemon to send in.");
                            waitForClick();
                            slot = buttonChoice - 4;
                        }
                        
                        processSwitch(playerMon, playerTeam[slot], "Player");
                    }
                }
            }
            
            if (playerAction == Action.ATTACK) {
                processAttack(playerMon, playerChoice, rivalMon);
                updateUI();
                
                if (rivalMon.currentHealth() == 0) {
                    displayText(rivalMon.name() + " fainted!");
                    delay(1);
                    
                    if (!stillStanding(rivalTeam))
                        displayText("Rival is out of usable Pokemon!",
                                "Player wins!");
                    
                    else {
                        Random rng = new Random();
                        int slot = rng.nextInt(6);
                        
                        while (rivalTeam[slot].currentHealth() == 0)
                            slot = rng.nextInt(6);
                        
                        processSwitch(rivalMon, rivalTeam[slot], "Rival");
                    }
                }
            }
        }
    }
    
    private void setPlayerPokemon(Pokemon pokemon) {
        playerMon = pokemon;
        playerPokemon.setIcon(new ImageIcon("C:\\Pokemon\\sprites\\back\\" + 
                playerMon.name().toLowerCase() + ".png"));
        displayText("Player sends out " + playerMon.name() + "!");
        delay(2);
    }
    
    private void setRivalPokemon(Pokemon pokemon) {
        rivalMon = pokemon;
        rivalPokemon.setIcon(new ImageIcon("C:\\Pokemon\\sprites\\back\\" + 
                rivalMon.name().toLowerCase() + ".png"));
        displayText("Rival sends out " + rivalMon.name() + "!");
        delay(2);
    }
    
    private void processSwitch(Pokemon out, Pokemon in, String user) {
        
        displayText(user + " withdraws " + out.name() + "!");
        out.clearTempStatus();
        out.resetStatStages();
        delay(1.5);

        if (user.equals("Player"))
            playerMon = in;
        else
            rivalMon = in;
        
        displayText(user + " sends out " + in.name() + "!");
        delay(1.5);
    }
    
    private void processAttack(Pokemon attacker, int slot, Pokemon defender) {
        
        Object[] result = attacker.immobilizedBy();
        Status obstacle = (Status)result[0];
        boolean blocked = (boolean)result[1];
        
        // Attacker had no status effects.
        if (obstacle.loneStatus() == Status.LoneStatus.NONE &&
                obstacle.mixStatus().isEmpty()) {
            
            Move move = attacker.moves()[slot];
            
            displayText(attacker.name() + " used " + move.name() + "!");
            Object[] moveResult = attacker.useMove(slot, defender);
            delay(0.5);
            
            if (move instanceof DamageMoveStat) {
                
                int stages = ((DamageMoveStat)move).stages();
                String message = defender.name() + "'s " + 
                        ((DamageMoveStat)move).affectedStat().toLowerCase();
                
                // Affected stat stage is already at max / min.
                if (!(boolean)moveResult[1]) {
                    
                    if (((DamageMoveStat) move).stages() > 0)
                        displayText(message + " won't go any higher!");
                    else
                        displayText(message + " won't go any lower!");
                }
                
                // There's still room for change.
                else {
                    
                    switch (stages) {
                        case 1:
                            displayText(message + " rose!");
                            break;
                        case 2:
                            displayText(message + " rose sharply!");
                            break;
                        case -1:
                            displayText(message + " fell!");
                            break;
                        default: // case -2
                            displayText(message + " fell sharply!");
                            break;
                    }
                }
            }
        }
        
        // Attacker had status effects, but broke through immobilization.
        else if (!blocked) {
                
            switch (obstacle.loneStatus()) {

                case FREEZE:
                    displayText(attacker.name() + " thawed out!");

                case SLEEP:
                    displayText(attacker.name() + " woke up!");
            }
            
            if (!obstacle.mixStatus().isEmpty()) {
                
                if (obstacle.mixStatus().contains(Status.MixStatus.CONFUSION)) {
                    displayText(attacker.name() + " is confused!");
                    delay(0.5);
                    displayText(attacker.name() + " snapped out of confusion!");
                }
                
                if (obstacle.mixStatus().contains(Status.MixStatus.INFATUATION)) {
                    displayText(attacker.name() + " is in love"
                            , "with the foe's " + defender.name() + "!");
                    delay(1);
                }
            }
            
            attacker.useMove(slot, defender);
        }
        
        // Attacker had status effects and was immobilized.
        else {
            
            if (!obstacle.mixStatus().isEmpty()) {
                
                if (obstacle.mixStatus().contains(Status.MixStatus.RECHARGE))
                    displayText(attacker.name() + " must recharge!");
                
                if (obstacle.mixStatus().contains(Status.MixStatus.CONFUSION)) {
                    
                    displayText(attacker.name() + " is confused!");
                    delay(0.5);
                    displayText("It hurt itself in confusion!");
                    
                    // Calculate and apply self-inflicted damage.
                }
                
                if (obstacle.mixStatus().contains(Status.MixStatus.INFATUATION)) {
                    displayText(attacker.name() + " is in love"
                            , "with the foe's " + defender.name() + "!");
                    delay(1);
                    displayText(attacker.name() + " is immobilized by love!");
                }
            }
        }
    }
    
    private static void startBattle() {
        Battle game = new Battle(new Pokemon[6], new Pokemon[6]);
    }
    
    public static void main(String[] args) {
        startBattle();
    }
}