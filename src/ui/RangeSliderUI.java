package ui;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.ComponentListener;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JComponent;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicSliderUI;

public class RangeSliderUI extends BasicSliderUI {

	private Rectangle upperThumbRect;
	private boolean upperThumbSelected;
	
	private transient boolean lowerDragging;
    private transient boolean upperDragging;

	public RangeSliderUI(RangeSlider b) {
		super(b);
	}
	
	// This function will add a new rectangle for the second thumb
    @Override
    public void installUI(JComponent c) {
        upperThumbRect = new Rectangle();
        super.installUI(c);
    }
    
    @Override
    protected TrackListener createTrackListener(JSlider slider) {
    		//TODO
        return new RangeTrackListener();
    }

    @Override
    protected ChangeListener createChangeListener(JSlider slider) {
        return new ChangeHandler();
    }
    
    @Override
    protected void calculateThumbSize() {
	    	super.calculateThumbSize();
	    	// Creates an upper thumb that has the same size as the other thumb
	    upperThumbRect.setSize(thumbRect.width, thumbRect.height );
    }
    
    @Override
    protected void calculateThumbLocation() {
    		super.calculateThumbLocation();
    	
        if ( slider.getSnapToTicks() ) {
        	
            int upperValue = slider.getValue()+ slider.getExtent();
            int snappedValue = upperValue;
            
            int minorTickSpacing = slider.getMinorTickSpacing();
            int majorTickSpacing = slider.getMajorTickSpacing();
            
            int tickSpacing = 0;

            
            // Inspired from the method getTickSpacing() in super
            if (minorTickSpacing > 0) {
                tickSpacing = minorTickSpacing;
            } else if (majorTickSpacing > 0) {
                tickSpacing = majorTickSpacing;
            } 
            
            if ( tickSpacing != 0 ) {
                // If it's not on a tick, change the value
                if ( (upperValue - slider.getMinimum()) % tickSpacing != 0 ) {
                    float temp = (float)(upperValue - slider.getMinimum()) / (float)tickSpacing;
                    int whichTick = Math.round( temp );
                    snappedValue = slider.getMinimum() + (whichTick * tickSpacing);
                }

                if( snappedValue != upperValue ) {
                		slider.setExtent(snappedValue - slider.getValue());
                }
            }
        }

        if ( slider.getOrientation() == JSlider.HORIZONTAL ) {
            int upperPosition = xPositionForValue(slider.getValue() + slider.getExtent());

            upperThumbRect.x = upperPosition - (upperThumbRect.width / 2);
            upperThumbRect.y = trackRect.y;
        }
        else {
            int upperPosition = yPositionForValue(slider.getValue() + slider.getExtent());

            upperThumbRect.x = trackRect.x;
            upperThumbRect.y = upperPosition - (upperThumbRect.height / 2);
        }
    }
    
    
    @Override
    protected Dimension getThumbSize() {
        return new Dimension(11, 20);
    }
    
    @Override
    public void paint( Graphics g, JComponent c )   {
    		super.paint(g,c);
    	
        Rectangle clipR = g.getClipBounds();

        if(upperThumbSelected) {
	        	if (clipR.intersects(thumbRect)) {
	    			//TODO
	            paintLowerThumb(g);
	        }
        		if (clipR.intersects(upperThumbRect)) {
        			//TODO
                paintUpperThumb(g);
            }	
        }
        else {
	        	if (clipR.intersects(upperThumbRect)) {
	    			//TODO
	            paintUpperThumb(g);
	        }
	    		if (clipR.intersects(thumbRect)) {
	    			//TODO
	            paintLowerThumb(g);
	        }
        }  
    }    

    public void paintLowerThumb(Graphics g)  {
    		Rectangle knobBounds = thumbRect;
        int w = knobBounds.width;
        int h = knobBounds.height;

        g.translate(knobBounds.x, knobBounds.y);

        if ( slider.isEnabled() ) {
            g.setColor(slider.getBackground());
        }
        else {
            g.setColor(slider.getBackground().darker());
        }
        
        if ( slider.getOrientation() == JSlider.HORIZONTAL ) {
            int cw = w / 2;
            g.fillRect(1, 1, w-3, h-1-cw);
            Polygon p = new Polygon();
            p.addPoint(1, h-cw);
            p.addPoint(cw-1, h-1);
            p.addPoint(w-2, h-1-cw);
            g.fillPolygon(p);

            g.setColor(getHighlightColor());
            g.drawLine(0, 0, w-2, 0);
            g.drawLine(0, 1, 0, h-1-cw);
            g.drawLine(0, h-cw, cw-1, h-1);

            g.setColor(Color.black);
            g.drawLine(w-1, 0, w-1, h-2-cw);
            g.drawLine(w-1, h-1-cw, w-1-cw, h-1);

            g.setColor(getShadowColor());
            g.drawLine(w-2, 1, w-2, h-2-cw);
            g.drawLine(w-2, h-1-cw, w-1-cw, h-2);
        }
        else {  // vertical
            int cw = h / 2;
            
			g.fillRect(1, 1, w-1-cw, h-3);
			Polygon p = new Polygon();
			p.addPoint(w-cw-1, 0);
			p.addPoint(w-1, cw);
			p.addPoint(w-1-cw, h-2);
			g.fillPolygon(p);
			
			g.setColor(getHighlightColor());
			g.drawLine(0, 0, 0, h - 2);                  // left
			g.drawLine(1, 0, w-1-cw, 0);                 // top
			g.drawLine(w-cw-1, 0, w-1, cw);              // top slant
			
			g.setColor(Color.black);
			g.drawLine(0, h-1, w-2-cw, h-1);             // bottom
			g.drawLine(w-1-cw, h-1, w-1, h-1-cw);        // bottom slant
			
			g.setColor(getShadowColor());
			g.drawLine(1, h-2, w-2-cw,  h-2 );         // bottom
			g.drawLine(w-1-cw, h-2, w-2, h-cw-1 );     // bottom slant
        }
        g.translate(-knobBounds.x, -knobBounds.y);
    }
    
    public void paintUpperThumb(Graphics g)  {
    		Rectangle knobBounds = upperThumbRect;
        int w = knobBounds.width;
        int h = knobBounds.height;

        g.translate(knobBounds.x, knobBounds.y);

        if ( slider.isEnabled() ) {
            g.setColor(slider.getBackground());
        }
        else {
            g.setColor(slider.getBackground().darker());
        }

        if ( slider.getOrientation() == JSlider.HORIZONTAL ) {
            int cw = w / 2;
            g.fillRect(1, 1, w-3, h-1-cw);
            Polygon p = new Polygon();
            p.addPoint(1, h-cw);
            p.addPoint(cw-1, h-1);
            p.addPoint(w-2, h-1-cw);
            g.fillPolygon(p);

            g.setColor(getHighlightColor());
            g.drawLine(0, 0, w-2, 0);
            g.drawLine(0, 1, 0, h-1-cw);
            g.drawLine(0, h-cw, cw-1, h-1);

            g.setColor(Color.black);
            g.drawLine(w-1, 0, w-1, h-2-cw);
            g.drawLine(w-1, h-1-cw, w-1-cw, h-1);

            g.setColor(getShadowColor());
            g.drawLine(w-2, 1, w-2, h-2-cw);
            g.drawLine(w-2, h-1-cw, w-1-cw, h-2);
        }
        else {  // vertical
            int cw = h / 2;
            
			g.fillRect(1, 1, w-1-cw, h-3);
			Polygon p = new Polygon();
			p.addPoint(w-cw-1, 0);
			p.addPoint(w-1, cw);
			p.addPoint(w-1-cw, h-2);
			g.fillPolygon(p);
			
			g.setColor(getHighlightColor());
			g.drawLine(0, 0, 0, h - 2);                  // left
			g.drawLine(1, 0, w-1-cw, 0);                 // top
			g.drawLine(w-cw-1, 0, w-1, cw);              // top slant
			
			g.setColor(Color.black);
			g.drawLine(0, h-1, w-2-cw, h-1);             // bottom
			g.drawLine(w-1-cw, h-1, w-1, h-1-cw);        // bottom slant
			
			g.setColor(getShadowColor());
			g.drawLine(1, h-2, w-2-cw,  h-2 );         // bottom
			g.drawLine(w-1-cw, h-2, w-2, h-cw-1 );     // bottom slant
        }
        g.translate(-knobBounds.x, -knobBounds.y);
    }
    
    public void setUpperThumbLocation(int x, int y)  {
    		Rectangle upperUnionRect = new Rectangle();
    		upperUnionRect.setBounds( thumbRect );

        upperThumbRect.setLocation( x, y );

        SwingUtilities.computeUnion( upperThumbRect.x, upperThumbRect.y, upperThumbRect.width, upperThumbRect.height, upperUnionRect );
        slider.repaint( upperUnionRect.x, upperUnionRect.y, upperUnionRect.width, upperUnionRect.height );
    }
    
    public class ChangeHandler implements ChangeListener {
        public void stateChanged(ChangeEvent arg0) {
            if (!lowerDragging && !upperDragging) {
                calculateThumbLocation();
                slider.repaint();
            }
        }
    }
    
    public class RangeTrackListener extends TrackListener {
    		@Override
        public void mousePressed(MouseEvent e) {
    			
    		}
    		
    		@Override
        public void mouseReleased(MouseEvent e) {
    			
    		}
    		
    		@Override
    		public void mouseDragged(MouseEvent e) {  
    			
    		}
    	}

}


