package com.noa.buttonselector.buttonselector.impl;

import java.awt.Component;
import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import com.ur.urcap.api.contribution.ContributionProvider;
import com.ur.urcap.api.contribution.program.swing.SwingProgramNodeView;

public class SelectorProgramNodeView implements SwingProgramNodeView<SelectorProgramNodeContribution> {

	private JSlider blinkSlider = new JSlider(100, 1000, 300);
	private JLabel valueLabel = new JLabel("300 ms");

	@Override
	public void buildUI(JPanel panel, final ContributionProvider<SelectorProgramNodeContribution> provider) {
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		JLabel title = new JLabel("Velocidad de parpadeo en espera (ms):");
		title.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel.add(title);
		panel.add(Box.createVerticalStrut(10));

		Box sliderBox = Box.createHorizontalBox();
		sliderBox.setAlignmentX(Component.LEFT_ALIGNMENT);

		blinkSlider.setPreferredSize(new Dimension(250, 30));
		blinkSlider.setMaximumSize(blinkSlider.getPreferredSize());

		blinkSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int val = blinkSlider.getValue();
				valueLabel.setText(val + " ms");
				if (!blinkSlider.getValueIsAdjusting()) {
					provider.get().onBlinkRateChanged(val);
				}
			}
		});

		sliderBox.add(blinkSlider);
		sliderBox.add(Box.createHorizontalStrut(10));
		sliderBox.add(valueLabel);
		panel.add(sliderBox);
	}

	public void setBlinkRate(int ms) {
		blinkSlider.setValue(ms);
		valueLabel.setText(ms + " ms");
	}
}