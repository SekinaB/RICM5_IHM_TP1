package ui;

public interface _RangeSlider {
	int getExtent();
	int getMaximum();
	int getMinimum();
	int getValue();
	boolean getValueIsAdjusting();
	void setExtent(int ext);
	void setMaximum(int max);
	void setMinimum(int min );
	void setRangeProperties(int val, int ext, int min, int max, boolean adjusting);
	void setValue(int val);
	void setValueIsAdjusting(boolean adjusting);
}
