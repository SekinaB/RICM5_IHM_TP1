package fc;

import javax.swing.JSlider;

public class RangeSlider extends JSlider implements _RangeSlider {

	private int maximum;
	private int minimum;
	private int upperValue;
	private int value;

	public RangeSlider(int max, int min, int upperValue, int value) {
		this.maximum = max;
		this.minimum = min;
		this.upperValue = upperValue;
		this.value = value;
	}

	public RangeSlider(int max, int min) {
		this(max, min, max, min);
	}

	@Override
	public int getMaximum() {
		return maximum;
	}

	@Override
	public int getMinimum() {
		return minimum;
	}

	@Override
	public int getTopValue() {
		return upperValue;
	}

	@Override
	public int getValue() {
		return value;
	}

	@Override
	public void setMaximum(int max) {
		if (max > minimum) {
			this.maximum = max;
			if (upperValue > max) {
				upperValue = max;
			}
			if (value > max) {
				value = max;
			}
		}
	}

	@Override
	public void setMinimum(int min) {
		if (min < maximum) {
			this.minimum = min;
			if (upperValue < min) {
				upperValue = min;
			}
			if (value < min) {
				value = min;
			}
		}
	}

	@Override
	public void setTopValue(int val) {
		if (val <= maximum && val >= value) {
			this.upperValue = val;
		}
		if (val > maximum) {
			this.upperValue = maximum;
		}
		if (val < value) {
			this.upperValue = value;
		}
	}

	@Override
	public void setValue(int val) {
		if (val >= minimum && val <= upperValue) {
			this.value = val;
		}
		if (val < minimum) {
			this.value = minimum;
		}
		if (val > upperValue) {
			this.value = upperValue;
		}
	}
}
