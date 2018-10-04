package ui;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
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
	
	private Color rangeColor = Color.RED;
	
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
				paintLowerThumb(g);
			}
			if (clipR.intersects(upperThumbRect)) {
				paintUpperThumb(g);
			}	
		}
		else {
			if (clipR.intersects(upperThumbRect)) {
				paintUpperThumb(g);
			}
			if (clipR.intersects(thumbRect)) {
				paintLowerThumb(g);
			}
		}  
	}    

	@Override
	public void paintThumb(Graphics g) {
		// This function should be empty or else, a rectangle will be drawn under the one we paint in paintUpperThumb method
	}
    
	public void paintTrack(Graphics g) {
		// Draw track.
		super.paintTrack(g);

		Rectangle trackBounds = trackRect;

		int lowerX = thumbRect.x + (thumbRect.width / 2);
		int upperX = upperThumbRect.x + (upperThumbRect.width / 2);

		// Determine track position.
		int cy = (trackBounds.height / 2) - 2;

		// Save color and shift position.
		Color oldColor = g.getColor();
		g.translate(trackBounds.x, trackBounds.y + cy);

		// Draw selected range.
		g.setColor(rangeColor);
		for (int y = 0; y <= 3; y++) {
			g.drawLine(lowerX - trackBounds.x, y, upperX - trackBounds.x, y);
		}

		// Restore position and color.
		g.translate(-trackBounds.x, -(trackBounds.y + cy));
		g.setColor(oldColor);
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
		slider.repaint();
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
			
			if (!slider.isEnabled()) {
				return;
			}
			
			boolean upperThumbIsPressed = false;
			boolean lowerThumbIsPressed = false;
			
			currentMouseX = e.getX();
			currentMouseY = e.getY();
			
			if (slider.isRequestFocusEnabled()) {
				slider.requestFocus();
			}
	
			if (upperThumbSelected) {
				if (upperThumbRect.contains(currentMouseX, currentMouseY)) {
					upperThumbIsPressed = true;
				} else if (thumbRect.contains(currentMouseX, currentMouseY)) {
					lowerThumbIsPressed = true;
				}
			} else {
				if (thumbRect.contains(currentMouseX, currentMouseY)) {
					lowerThumbIsPressed = true;
				} else if (upperThumbRect.contains(currentMouseX, currentMouseY)) {
					upperThumbIsPressed = true;
				}
			}
			
			if(lowerThumbIsPressed) {
				offset = currentMouseX - thumbRect.x;
				upperThumbSelected = false;
				lowerDragging = true;
				return;
			}
			lowerDragging = false;
			
			if(upperThumbIsPressed) {
				offset = currentMouseX - upperThumbRect.x;
				upperThumbSelected = true;
				upperDragging = true;
				return;
			}
			upperDragging = false;
		}
    		
		@Override
		public void mouseReleased(MouseEvent e) {
			lowerDragging = false;
			upperDragging = false;
			slider.setValueIsAdjusting(false);
			super.mouseReleased(e);
		}
    		
		@Override
		public void mouseDragged(MouseEvent e) {
			int thumbMiddle;

			if (!slider.isEnabled()) {
				return;
			}
			if (!lowerDragging && !upperDragging) {
				return;
			}

			currentMouseX = e.getX();
			currentMouseY = e.getY();
			slider.setValueIsAdjusting(true);
			
			if (lowerDragging) {

				int halfThumbWidth = thumbRect.width / 2;
				int thumbLeft = e.getX() - offset;
				int trackLeft = trackRect.x;
				int trackRight = trackRect.x + (trackRect.width - 1);
				int hMax = xPositionForValue(slider.getMaximum() -
						slider.getExtent());
	
				if (drawInverted()) {
					trackLeft = hMax;
				}
				else {
					trackRight = hMax;
				}
				thumbLeft = Math.max(thumbLeft, trackLeft - halfThumbWidth);
				thumbLeft = Math.min(thumbLeft, trackRight - halfThumbWidth);

				thumbMiddle = thumbLeft + halfThumbWidth;
               
				int thumbRight = thumbLeft + thumbRect.width;
				if (valueForXPosition(thumbRight) < ((RangeSlider) slider).getUpperValue()) {
					setThumbLocation(thumbLeft, thumbRect.y);
					slider.setValue(valueForXPosition(thumbMiddle));
				}
			}
            
			if (upperDragging) {	
				int halfThumbWidth = upperThumbRect.width / 2;
				int thumbLeft = e.getX() - offset;
				int trackLeft = trackRect.x;
				int trackRight = trackRect.x + trackRect.width - 1;
				int hMax = xPositionForValue(slider.getMaximum());

				if (drawInverted())
					trackLeft = hMax;
				else
					trackRight = hMax;
                
				thumbLeft = Math.max(thumbLeft, trackLeft - halfThumbWidth);
				thumbLeft = Math.min(thumbLeft, trackRight - halfThumbWidth);
				int thumbRight = thumbLeft + thumbRect.width;
				thumbMiddle = thumbLeft + halfThumbWidth;

				if (valueForXPosition(thumbLeft) > slider.getValue()) {
					setUpperThumbLocation(thumbRight, upperThumbRect.y);
					((RangeSlider) slider).setUpperValue(valueForXPosition(thumbMiddle));
				}
			}   
		}
	}
}

