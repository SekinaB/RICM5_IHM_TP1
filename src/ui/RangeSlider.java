package ui;

import javax.swing.JSlider;

public class RangeSlider extends JSlider {

	public RangeSlider() {
		setOrientation(HORIZONTAL);
	}
	
	public RangeSlider(int max, int min) throws Exception {
		super(min,max);
		setOrientation(HORIZONTAL);
	}

	@Override
	public void updateUI() {
		setUI(new RangeSliderUI(this));
		updateLabelUIs();
	}
	
	@Override
	public int getValue() {
		return super.getValue();
	}

	@Override
	public void setValue(int value) {
		int oldValue = getValue();
		if (oldValue == value) {
			return;
		}
		int oldExtent = getExtent();
		int newValue = Math.min(Math.max(getMinimum(), value), oldValue + oldExtent);
		int newExtent = oldExtent + oldValue - newValue;

		getModel().setRangeProperties(newValue, newExtent, getMinimum(), 
				getMaximum(), getValueIsAdjusting());
	}
	
	public int getUpperValue() {
		return getValue() + getExtent();
	}
	
	public void setUpperValue(int value) {
		int lowerValue = getValue();
		int newExtent = Math.min(Math.max(0, value - lowerValue), getMaximum() - lowerValue);
   
		setExtent(newExtent);
	}

}
