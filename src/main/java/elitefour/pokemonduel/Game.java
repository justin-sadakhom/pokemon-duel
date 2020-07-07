package elitefour.pokemonduel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.Border;

public class Game implements ActionListener {
    
    static Boolean mouseClicked = false;
    static int pressedButton = -1;
    
    /* GUI COMPONENTS */
    
    private static JFrame window;
    private static JLabel allyPokemon, rivalPokemon;
    private static InfoField allyInfo, rivalInfo;
    private static JLabel battlefield, allyBase, rivalBase;
    private static JLabel textContainer, battleText1, battleText2;
    private static JLabel attackLabel, switchLabel;
    
    final private static JLabel[] allyTeam = new JLabel[6];
    final private static JLabel[] rivalTeam = new JLabel[6];
    final private static JButton[] moves = new JButton[4];
    final private static JButton[] switches = new JButton[6];
    
    final static Border BLACK_BORDER = BorderFactory.createLineBorder(Color.BLACK, 1);
    
    /* SPRITES */
    
    final static ImageIcon POKEBALL = new ImageIcon("resources\\sprites\\icon\\pokeball.png");
    final static ImageIcon GRAY_BALL = new ImageIcon("resources\\sprites\\icon\\grayball.png");
    final static ImageIcon FIELD_BG = new ImageIcon("resources\\background\\battlebgfield.png");
    final static ImageIcon ALLY_BASE = new ImageIcon("resources\\background\\playerbaseField.png");
    final static ImageIcon ENEMY_BASE = new ImageIcon("resources\\background\\enemybaseField.png");
    
    private Game() {
        
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
        allyBase = new JLabel();
        rivalBase = new JLabel();
        
        allyPokemon = new JLabel();
        rivalPokemon = new JLabel();
        
        allyInfo = new InfoField(font, true);
        rivalInfo = new InfoField(font, false);
        
        attackLabel = new JLabel("<html><font size=5><font color=#7b241c><i>Attack</i></font></html>");
        switchLabel = new JLabel("<html><font size=5><font color=#154360><i>Switch</i></font></html>");
        
        for (int i = 0; i < 4; i++)
            moves[i] = new JButton();
        
        for (int i = 0; i < 6; i++) {
            switches[i] = new JButton();
            allyTeam[i] = new JLabel();
            rivalTeam[i] = new JLabel();
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
        
        allyBase.setBounds(18, 241, 512, 66);
        allyBase.setIcon(ALLY_BASE);
        
        rivalBase.setBounds(322, 103, 256, 128);
        rivalBase.setIcon(ENEMY_BASE);
        
        allyPokemon.setBounds(150, 147, 160, 160);
        rivalPokemon.setBounds(367, 44, 160, 160);
        
        allyTeam[0].setBounds(10, 19, 43, 43);
        allyTeam[0].setIcon(POKEBALL);
        allyTeam[0].setHorizontalAlignment((int)Component.CENTER_ALIGNMENT);
        
        allyTeam[1].setBounds(10, 68, 43, 43);
        allyTeam[1].setIcon(POKEBALL);
        allyTeam[1].setHorizontalAlignment((int)Component.CENTER_ALIGNMENT);
        
        allyTeam[2].setBounds(10, 117, 43, 43);
        allyTeam[2].setIcon(POKEBALL);
        allyTeam[2].setHorizontalAlignment((int)Component.CENTER_ALIGNMENT);
        
        allyTeam[3].setBounds(10, 166, 43, 43);
        allyTeam[3].setIcon(POKEBALL);
        allyTeam[3].setHorizontalAlignment((int)Component.CENTER_ALIGNMENT);
        
        allyTeam[4].setBounds(10, 215, 43, 43);
        allyTeam[4].setIcon(POKEBALL);
        allyTeam[4].setHorizontalAlignment((int)Component.CENTER_ALIGNMENT);
        
        allyTeam[5].setBounds(10, 264, 43, 43);
        allyTeam[5].setIcon(POKEBALL);
        allyTeam[5].setHorizontalAlignment((int)Component.CENTER_ALIGNMENT);
        
        rivalTeam[0].setBounds(592, 19, 43, 43);
        rivalTeam[0].setIcon(POKEBALL);
        rivalTeam[0].setHorizontalAlignment((int)Component.CENTER_ALIGNMENT);
        
        rivalTeam[1].setBounds(592, 68, 43, 43);
        rivalTeam[1].setIcon(POKEBALL);
        rivalTeam[1].setHorizontalAlignment((int)Component.CENTER_ALIGNMENT);
        
        rivalTeam[2].setBounds(592, 117, 43, 43);
        rivalTeam[2].setIcon(POKEBALL);
        rivalTeam[2].setHorizontalAlignment((int)Component.CENTER_ALIGNMENT);
        
        rivalTeam[3].setBounds(592, 166, 43, 43);
        rivalTeam[3].setIcon(POKEBALL);
        rivalTeam[3].setHorizontalAlignment((int)Component.CENTER_ALIGNMENT);
        
        rivalTeam[4].setBounds(592, 215, 43, 43);
        rivalTeam[4].setIcon(POKEBALL);
        rivalTeam[4].setHorizontalAlignment((int)Component.CENTER_ALIGNMENT);
        
        rivalTeam[5].setBounds(592, 264, 43, 43);
        rivalTeam[5].setIcon(POKEBALL);
        rivalTeam[5].setHorizontalAlignment((int)Component.CENTER_ALIGNMENT);
        
        moves[0].setBounds(10, 400, 152, 40);
        moves[0].setFocusPainted(false);
        moves[0].addActionListener(this);
        
        moves[1].setBounds(168, 400, 152, 40);
        moves[1].setFocusPainted(false);
        moves[1].addActionListener(this);
        
        moves[2].setBounds(326, 400, 152, 40);
        moves[2].setFocusPainted(false);
        moves[2].addActionListener(this);
        
        moves[3].setBounds(484, 400, 152, 40);
        moves[3].setFocusPainted(false);
        moves[3].addActionListener(this);
        
        switches[0].setBounds(10, 475, 100, 33);
        switches[0].setFocusPainted(false);
        switches[0].addActionListener(this);
        
        switches[1].setBounds(115, 475, 100, 33);
        switches[1].setFocusPainted(false);
        switches[1].addActionListener(this);
        
        switches[2].setBounds(220, 475, 100, 33);
        switches[2].setFocusPainted(false);
        switches[2].addActionListener(this);
        
        switches[3].setBounds(325, 475, 100, 33);
        switches[3].setFocusPainted(false);
        switches[3].addActionListener(this);
        
        switches[4].setBounds(430, 475, 100, 33);
        switches[4].setFocusPainted(false);
        switches[4].addActionListener(this);
        
        switches[5].setBounds(535, 475, 100, 33);
        switches[5].setFocusPainted(false);
        switches[5].addActionListener(this);
        
        for (int i = 0; i < 4; i++)
            window.add(moves[i]);
        
        for (int i = 0; i < 6; i++) {
            window.add(switches[i]);
            window.add(allyTeam[i]);
            window.add(rivalTeam[i]);
        }

        window.add(battleText1);
        window.add(battleText2);
        window.add(textContainer);
        window.add(attackLabel);
        window.add(switchLabel);
        window.add(allyInfo);
        window.add(rivalInfo);
        window.add(allyPokemon);
        window.add(rivalPokemon);
        window.add(allyBase);
        window.add(rivalBase);
        window.add(battlefield);
        
        window.setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent event) {
            
        if (event.getSource() == moves[0]) {
            mouseClicked = true;
            pressedButton = 0;
        }

        else if (event.getSource() == moves[1]) {
            mouseClicked = true;
            pressedButton = 1;
        }

        else if (event.getSource() == moves[2]) {
            mouseClicked = true;
            pressedButton = 2;
        }

        else if (event.getSource() == moves[3]) {
            mouseClicked = true;
            pressedButton = 3;
        }

        else if (event.getSource() == switches[0]) {
            mouseClicked = true;
            pressedButton = 4;
        }

        else if (event.getSource() == switches[1]) {
            mouseClicked = true;
            pressedButton = 5;
        }

        else if (event.getSource() == switches[2]) {
            mouseClicked = true;
            pressedButton = 6;
        }

        else if (event.getSource() == switches[3]) {
            mouseClicked = true;
            pressedButton = 7;
        }

        else if (event.getSource() == switches[4]) {
            mouseClicked = true;
            pressedButton = 8;
        }

        else if (event.getSource() == switches[5]) {
            mouseClicked = true;
            pressedButton = 9;
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        startBattle();
    }
    
    private static void startBattle() {
        
        Game game = new Game();
    }
}