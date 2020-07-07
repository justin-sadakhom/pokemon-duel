package elitefour.pokemonduel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JComponent;
import javax.swing.JLabel;

public class InfoField extends JComponent {
    
    final private JLabel healthBar, healthBG, healthPercent, name;
    
    public InfoField(Font font, boolean ally) {
        
        healthBar = new JLabel();
        healthBar.setOpaque(true);
        healthBar.setBackground(Color.decode("#49c613"));
        
        healthBG = new JLabel();
        healthBG.setBackground(Color.white);
        
        healthPercent = new JLabel();
        healthPercent.setForeground(Color.white);
        healthPercent.setBackground(Color.decode("#767778"));
        healthPercent.setHorizontalAlignment((int)Component.CENTER_ALIGNMENT);
        
        name = new JLabel();
        name.setFont(font);
        name.setHorizontalAlignment((int)Component.CENTER_ALIGNMENT);
        
        if (ally) {
            healthBG.setBounds(355, 250, 150, 13);
            name.setBounds(360, 220, 150, 25);
        }
        
        else {
            healthBG.setBounds(140, 75, 150, 13);
            name.setBounds(145, 45, 150, 25);
        }
    }
}