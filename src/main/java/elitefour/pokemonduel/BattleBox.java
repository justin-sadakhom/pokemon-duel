package elitefour.pokemonduel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class BattleBox {
    
    private final ImageIcon PLAYER_BATTLE_BOX = new ImageIcon(
        "resources\\interface\\playerBattleBox.png"
    );
    private final ImageIcon RIVAL_BATTLE_BOX = new ImageIcon(
        "resources\\interface\\rivalBattleBox.png"
    );
    
    private final JLabel healthBar, name, background;
    
    public BattleBox(Font font, boolean ally) {
        
        name = new JLabel();
        name.setFont(font);
        name.setHorizontalAlignment((int)Component.CENTER_ALIGNMENT);
        name.setVisible(false);
        
        healthBar = new JLabel();
        healthBar.setOpaque(true);
        healthBar.setBackground(Color.decode("#49c613"));
        healthBar.setVisible(false);
        
        background = new JLabel();
        background.setVisible(false);
        
        if (ally) {
            name.setBounds(332, 218, 150, 25);
            background.setIcon(PLAYER_BATTLE_BOX);
            background.setBounds(318, 211, 260, 86);
        }
        
        else {
            name.setBounds(45, 76, 150, 25);
            background.setIcon(RIVAL_BATTLE_BOX);
            background.setBounds(66, 57, 278, 86);
        }
    }
    
    public void update(Pokemon pokemon, boolean ally) {
        
        double health = pokemon.healthPercent();
        
        if (ally)
            healthBar.setBounds(455, 253, (int)(health * 0.95), 4);
        else
            healthBar.setBounds(185, 111, (int)(health * 0.95), 4);

        if (20 < pokemon.healthPercent() &&
                pokemon.healthPercent() <= 50)
            healthBar.setBackground(Color.decode("#fcbd03"));
        
        else if (pokemon.healthPercent() < 20)
            healthBar.setBackground(Color.decode("#f85828"));
        
        else
            healthBar.setBackground(Color.decode("#49c613"));
        
        name.setText("<html><font size=6>" + pokemon.name() + "</font></html>");
    }
    
    public JLabel[] getLabels() {
        return new JLabel[]{name, healthBar, background};
    }
    
    public void setVisible(boolean state) {
        background.setVisible(true);
        healthBar.setVisible(state);
        name.setVisible(state);
    }
}