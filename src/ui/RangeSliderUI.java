package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicSliderUI;

import fc.RangeSlider;

public class RangeSliderUI extends BasicSliderUI {
	// Addition of a thumb
	private Rectangle upperThumbRect;
	int lastUpperValue;
	
	RangeSlider rslider;
	ChangeListener chlistener = new ChangeListener() {
		@Override
		public void stateChanged(ChangeEvent e) {
			// TODO
		}
	};

	public RangeSliderUI(RangeSlider rs) {
		super(rs);
		this.rslider = rs;
		rslider.addChangeListener(chlistener);
	}

	protected void calculateThumbSize() {
		// Calculate the lower thumb
		super.calculateThumbSize();
		upperThumbRect.setSize(thumbRect.width, thumbRect.height);
	}

	protected void calculateThumbLocation() {
		// Calculate the lower thumb
		super.calculateThumbLocation();

		// Calculate location of the upper thumb
		if (rslider.getSnapToTicks()) {
			int upperValue = rslider.getUpperValue();
			int snappedValue = upperValue;
			int majorTickSpacing = rslider.getMajorTickSpacing();
			int minorTickSpacing = rslider.getMinorTickSpacing();
			int tickSpacing = 0;

			if (minorTickSpacing > 0) {
				tickSpacing = minorTickSpacing;
			} else if (majorTickSpacing > 0) {
				tickSpacing = majorTickSpacing;
			}

			if (tickSpacing != 0) {
				if ((upperValue - rslider.getMinimum()) % tickSpacing != 0) {
					float temp = (float) (upperValue - rslider.getMinimum()) / (float) tickSpacing;
					int whichTick = Math.round(temp);
					if (temp - (int) temp == .5 && upperValue < lastUpperValue) {
						whichTick--;
					}
					snappedValue = rslider.getMinimum() + (whichTick * tickSpacing);
				}
				if (snappedValue != upperValue) {
					rslider.setValue(snappedValue);
				}
			}
		}
		int valuePosition = yPositionForValue(slider.getValue());
		thumbRect.x = trackRect.x;
		thumbRect.y = valuePosition - (thumbRect.height / 2);
	}

	public void paint(Graphics g, JComponent c) {
		super.paint(g, c);
		Rectangle clip = g.getClipBounds();
		if (upperThumbSelected) {
			// Paint lower thumb first, then upper thumb.
			if (clip.intersects(thumbRect)) {
				paintLowerThumb(g);
			}
			if (clip.intersects(upperThumbRect)) {
				paintUpperThumb(g);
			}

		} else {
			// Paint upper thumb first, then lower thumb.
			if (clip.intersects(upperThumbRect)) {
				paintUpperThumb(g);
			}
			if (clip.intersects(thumbRect)) {
				paintLowerThumb(g);
			}
		}
	}

	public void paintTrack(Graphics g) {
		// Draw track.
		super.paintTrack(g);

		Rectangle trackBounds = trackRect;
		int lowerY = thumbRect.x + (thumbRect.width / 2);
		int upperY = upperThumbRect.x + (upperThumbRect.width / 2);

		// Determine track position.
		int cx = (trackBounds.width / 2) - 2;

		// Save color and shift position.
		Color oldColor = g.getColor();
		g.translate(trackBounds.x + cx, trackBounds.y);

		// Draw selected range.
		g.setColor(rangeColor);
		for (int x = 0; x <= 3; x++) {
			g.drawLine(x, lowerY - trackBounds.y, x, upperY - trackBounds.y);
		}

		// Restore position and color.
		g.translate(-(trackBounds.x + cx), -trackBounds.y);
		g.setColor(oldColor);
	}
}
