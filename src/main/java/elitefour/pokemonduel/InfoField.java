package elitefour.pokemonduel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JComponent;
import javax.swing.JLabel;

public class InfoField extends JComponent {
    
    final private JLabel healthBar, healthBG, name;
    
    public InfoField(Font font, boolean ally) {
        
        healthBar = new JLabel();
        healthBar.setOpaque(true);
        healthBar.setBackground(Color.decode("#49c613"));
        
        healthBG = new JLabel();
        healthBG.setBackground(Color.white);
        
        name = new JLabel();
        name.setFont(font);
        name.setHorizontalAlignment((int)Component.CENTER_ALIGNMENT);
        
        if (ally) {
            healthBar.setBounds(355, 250, 150, 13);
            healthBG.setBounds(355, 250, 150, 13);
            name.setBounds(360, 220, 150, 25);
        }
        
        else {
            healthBar.setBounds(140, 75, 150, 13);
            healthBG.setBounds(140, 75, 150, 13);
            name.setBounds(145, 45, 150, 25);
        }
    }
    
    public void update(Pokemon pokemon, boolean ally) {
        
        double health = pokemon.healthPercent();
        
        if (ally)
            healthBar.setBounds(355, 250, (int)(health * 1.5), 13);
        else
            healthBar.setBounds(140, 75, (int)(health * 1.5), 13);
        
        name.setText("<html><font size=6>" + pokemon.name() + "</font></html>");
    }
    
    public JLabel[] getLabels() {
        return new JLabel[]{healthBar, healthBG, name};
    }
    
    @Override
    public void setVisible(boolean state) {
        healthBar.setVisible(state);
        healthBG.setVisible(state);
        name.setVisible(state);
        
        healthBar.setBorder(GUI.BLACK_BORDER);
        healthBG.setBorder(GUI.BLACK_BORDER);
    }
}