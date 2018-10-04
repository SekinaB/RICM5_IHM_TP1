package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Demo application panel to display a range slider.
 */
public class Main extends JPanel {
    
    public static void main(String[] args) {
    	
    	
    		RangeSlider rangeSlider = new RangeSlider();
    		
    		rangeSlider.setSize(1000,700);
		rangeSlider.setMinimum(0);
        rangeSlider.setMaximum(10);
        rangeSlider.setMajorTickSpacing(10);
        rangeSlider.setMinorTickSpacing(1);
        rangeSlider.setPaintTicks(true);
        rangeSlider.setPaintLabels(true);
        rangeSlider.setValue(2);
        rangeSlider.setUpperValue(8);
        
        JPanel panel = new JPanel();
        panel.add(rangeSlider);	
        
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setTitle("Range Slider");
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
        
    }
}

