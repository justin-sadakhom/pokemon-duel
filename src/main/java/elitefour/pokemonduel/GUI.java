package elitefour.pokemonduel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Insets;
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

public class GUI implements ActionListener {
    
    /* CONTROLS */
    
    private Boolean mouseClicked = false;
    private int buttonChoice = -1;
    
    /* COMPONENTS */
    
    private final JFrame window;
    private final JLabel playerPokemon, rivalPokemon;
    private final BattleBox playerInfo, rivalInfo;
    private final JLabel battlefield, playerBase, rivalBase;
    private final JLabel textContainer, battleText1, battleText2;
    private final JLabel attackLabel, switchLabel;
    private final JLabel[] playerTeamDisplay = new JLabel[6];
    private final JLabel[] rivalTeamDisplay = new JLabel[6];
    private final JButton[] moves = new JButton[4];
    private final JButton[] switches = new JButton[6];
    
    public static final Border BLACK_BORDER = 
            BorderFactory.createLineBorder(Color.BLACK, 1);
    
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
    
    private final Pokemon[] playerTeam, rivalTeam;
    
    public GUI(Pokemon[] playerTeam, Pokemon[] rivalTeam) {
        
        this.playerTeam = playerTeam;
        this.rivalTeam = rivalTeam;
        
        Font font = null;
        
        try {
            font = Font.createFont(
                Font.TRUETYPE_FONT,
                new FileInputStream("resources\\fonts\\pkmndp.ttf")
            );
        } catch (FontFormatException | IOException error) {
            error.printStackTrace(System.out);
        }
        
        window = new JFrame("Pokémon Duel");
        window.setLayout(null);
        window.setSize(660, 560);
        window.setLocationRelativeTo(null);
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
        
        playerInfo = new BattleBox(font, true);
        rivalInfo = new BattleBox(font, false);
        
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
            rivalTeamDisplay[i].setHorizontalAlignment(alignment);
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
            moves[i].setBorder(new RoundedBorder(10));
            moves[i].setFocusPainted(false);
            moves[i].addActionListener(this);
        }
        
        moves[0].setBounds(10, 400, 152, 40);
        moves[1].setBounds(168, 400, 152, 40);
        moves[2].setBounds(326, 400, 152, 40);
        moves[3].setBounds(484, 400, 152, 40);
        
        for (int i = 0; i < 6; i++) {
            switches[i].setBorder(new RoundedBorder(10));
            switches[i].setFocusPainted(false);
            switches[i].addActionListener(this);
        }
        
        switches[0].setBounds(10, 475, 100, 33);
        switches[1].setBounds(115, 475, 100, 33);
        switches[2].setBounds(220, 475, 100, 33);
        switches[3].setBounds(325, 475, 100, 33);
        switches[4].setBounds(430, 475, 100, 33);
        switches[5].setBounds(535, 475, 100, 33);
        
        for (int i = 0; i < playerTeam.length; i++) {
            String directory = "resources\\sprites\\icon\\" +
                    playerTeam[i].name().toLowerCase() + ".png";
            switches[i].setIcon(new ImageIcon(directory));
        }
        
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
        
        for (JLabel label : playerInfo.getLabels())
            window.add(label);
        
        for (JLabel label : rivalInfo.getLabels())
            window.add(label);
        
        window.add(playerPokemon);
        window.add(rivalPokemon);
        window.add(playerBase);
        window.add(rivalBase);
        window.add(battlefield);
        
        window.setVisible(true);
    }
    
    public void displayText(String output) {
        
        battleText1.setText("<html><font size=6>" + output + "</font></html>");
        battleText2.setText("");
        
        battleText1.repaint();
        battleText2.repaint();
        
        delay(2);
    }
    
    public void displayText(String output1, String output2) {
        
        delay(0.5);
        
        battleText1.setText("<html><font size=6>" + output1 + "</font></html>");
        battleText2.setText("<html><font size=6>" + output2 + "</font></html>");
        
        battleText1.repaint();
        battleText2.repaint();
        
        delay(2);
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
    
    public void waitForClick() {
        
        while (!mouseClicked)
            System.out.print("");
        
        mouseClicked = false;
    }
    
    public int getButtonChoice() {
        return buttonChoice;
    }
    
    public void revealStatusBars() {
        playerInfo.setVisible(true);
        rivalInfo.setVisible(true);
    }
    
    public void update(Pokemon front, Pokemon back) {
        
        playerInfo.update(front, true);
        rivalInfo.update(back, false);
        
        for (int i = 0; i < front.moves().length; i++) {
            moves[i].setText(front.moves()[i].name());
            moves[i].setToolTipText(front.moves()[i].PP() + " remaining");
        }
        
        for (int i = 0; i < playerTeam.length; i++)
            switches[i].setIcon(new ImageIcon("resources\\sprites\\icon\\" + 
                    playerTeam[i].name().toLowerCase() + ".png"));
        
        playerPokemon.setIcon(new ImageIcon("resources\\sprites\\back\\" + 
                front.name().toLowerCase() + ".png"));
        rivalPokemon.setIcon(new ImageIcon("resources\\sprites\\front\\" + 
                back.name().toLowerCase() + ".png"));
        
        for (int i = 0; i < playerTeam.length; i++)
            if (playerTeam[i].isFainted())
                playerTeamDisplay[i].setIcon(GRAY_BALL);
        
        for (int i = 0; i < rivalTeam.length; i++)
            if (rivalTeam[i].isFainted())
                rivalTeamDisplay[i].setIcon(GRAY_BALL);
        
        window.repaint();
        delay(1.5);
    }
    
    public void delay(double seconds) {
        
        try {
            Thread.sleep((long)(seconds * 1000));
        } catch (InterruptedException error) {
            error.printStackTrace(System.out);
        }
    }
    
    public void setPlayerPokemon(Pokemon active) {
        
        String directory = "C:\\Pokemon\\sprites\\back\\" + 
                active.name().toLowerCase() + ".png";
        playerPokemon.setIcon(new ImageIcon(directory));
    }
    
    public void setRivalPokemon(Pokemon active) {
        
        String directory = "C:\\Pokemon\\sprites\\back\\" + 
                active.name().toLowerCase() + ".png";
        rivalPokemon.setIcon(new ImageIcon(directory));
    }
}

class RoundedBorder implements Border {

    private final int radius;

    RoundedBorder(int radius) {
        this.radius = radius;
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(this.radius + 1, this.radius + 1,
                this.radius + 2, this.radius);
    }

    @Override
    public boolean isBorderOpaque() {
        return true;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y,
            int width, int height) {
        g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
    }
}
