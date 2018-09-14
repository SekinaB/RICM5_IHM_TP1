package ui;

public class RangeSlider implements _RangeSlider{
	
	private int maximum;
	private int minimum;
	private int bsup;
	private int binf;
	
	public RangeSlider(int max, int min, int bsup, int binf){
		this.maximum = max;
		this.minimum = min;
		this.bsup = bsup;
		this.binf = binf;	
	}
	
	public RangeSlider(int max, int min){
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
		if(max > this.minimum){
			this.maximum = max;	
		}
		else {
			//TODO: raise an exception
			System.out.println("Max < Min");
		}
	}

	@Override
	public void setMinimum(int min) {
		if(min < this.maximum){
			this.minimum = min;	
		}
		else {
			//TODO: raise an exception
			System.out.println("Min > Max");
		}
	}

	@Override
	public void setBSup(int bsup) {
		if(bsup <= this.maximum && bsup >= this.minimum && bsup >= this.binf){
			this.bsup = bsup;
		}
		else {
			//TODO: raise an exception
			System.out.println("bsup > Max or bsup < Min or bsup < binf");
		}
	}

	@Override
	public void setBInf(int binf) {
		if(binf <= this.maximum && binf >= this.minimum && binf <= this.bsup){
			this.binf = binf;
		}
		else {
			//TODO: raise an exception
			System.out.println("binf > Max or binf < Min or binf < bsup");	
		}
	}
}
