package ui;

import javax.swing.JSlider;

public class RangeSlider extends JSlider implements _RangeSlider {

	private int maximum;
	private int minimum;
	private int bsup;
	private int binf;

	public RangeSlider(int max, int min, int bsup, int binf) throws Exception {
		if (min > binf || binf > bsup || bsup > max) {
			throw new Exception("RangeSlider couldn't be create");
		}
		this.maximum = max;
		this.minimum = min;
		this.bsup = bsup;
		this.binf = binf;
	}

	public RangeSlider(int max, int min) throws Exception {
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
	public int getBSup() {
		return bsup;
	}

	@Override
	public int getBInf() {
		return binf;
	}

	@Override
	public void setMaximum(int max) {
		if (max > minimum) {
			this.maximum = max;
			if (bsup > max) {
				bsup = max;
			}
			if (binf > max) {
				binf = max;
			}
		}
	}

	@Override
	public void setMinimum(int min) {
		if (min < maximum) {
			this.minimum = min;
			if (bsup < min) {
				bsup = min;
			}
			if (binf < min) {
				binf = min;
			}
		}
	}

	@Override
	public void setBSup(int bsup) {
		if (bsup <= maximum && bsup >= binf) {
			this.bsup = bsup;
		}
		if (bsup > maximum) {
			this.bsup = maximum;
		}
		if (bsup < binf) {
			this.bsup = binf;
		}
	}

	@Override
	public void setBInf(int binf) {
		if (binf >= minimum && binf <= bsup) {
			this.binf = binf;
		}
		if (binf < minimum) {
			this.binf = minimum;
		}
		if (binf > bsup) {
			this.binf = bsup;
		}
	}
}
