package ui;

import javax.swing.JFrame;

public class Main {
	public static void main(String args[]) {
		JFrame frame = new JFrame();
		frame.setTitle("RangeSlider");
		frame.setSize(400, 300);

		try {
			RangeSlider rslider = new RangeSlider(50, 0, 40, 10);
			frame.getContentPane().add(rslider);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		frame.setVisible(true);
	}
}
