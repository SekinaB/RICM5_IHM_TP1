package homefinder;

import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import ui.RangeSlider;

public class Main {

	private Map map;
	private RangeSlider bedroomRS;
	private RangeSlider priceRS;
	private JFrame frame = new JFrame("Homefinder");

	public void init() {
		Map map = new Map(400, 400, 20, 1, 7, 50, 500);

		this.bedroomRS = new RangeSlider(7, 1);
		this.priceRS = new RangeSlider(500, 50);

		frame.setSize(800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel subPanel = new JPanel();
		subPanel.setLayout(new BorderLayout());

		JPanel jpRange = new JPanel();
		JPanel jpBed = new JPanel();
		JPanel jpPrice = new JPanel();

		jpBed.setLayout(new BoxLayout(jpBed, BoxLayout.LINE_AXIS));
		bedroomRS.setMajorTickSpacing(10);
		bedroomRS.setMinorTickSpacing(1);
		bedroomRS.setPaintTicks(true);
		bedroomRS.setPaintLabels(true);
		bedroomRS.setValue(1);
		bedroomRS.setUpperValue(7);
		jpBed.add(bedroomRS);

		jpPrice.setLayout(new BoxLayout(jpPrice, BoxLayout.LINE_AXIS));
		priceRS.setMajorTickSpacing(100);
		priceRS.setMinorTickSpacing(10);
		priceRS.setPaintTicks(true);
		priceRS.setPaintLabels(true);
		priceRS.setValue(50);
		priceRS.setUpperValue(500);
		jpPrice.add(priceRS);

		jpRange.setLayout(new BoxLayout(jpRange, BoxLayout.PAGE_AXIS));
		jpRange.add(jpBed);
		jpRange.add(jpPrice);

		subPanel.add(jpRange);

		JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, map, subPanel);
		sp.setDividerLocation(500);
		frame.add(sp);

		frame.setVisible(true);
	}

	public static void main(String[] arguments) {

		Main m = new Main();
		m.init();

	}
}
