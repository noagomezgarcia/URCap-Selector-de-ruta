package com.noa.buttonselector.buttonselector.impl;

import java.awt.Component;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import com.ur.urcap.api.contribution.ContributionProvider;
import com.ur.urcap.api.contribution.program.swing.SwingProgramNodeView;

public class BranchProgramNodeView implements SwingProgramNodeView<BranchProgramNodeContribution> {

	private JLabel infoLabel = new JLabel();

	@Override
	public void buildUI(JPanel panel, ContributionProvider<BranchProgramNodeContribution> provider) {
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		infoLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel.add(infoLabel);
		panel.add(Box.createVerticalStrut(10));
		panel.add(new JLabel("Añade movimientos del robot dentro de este bloque."));
	}

	public void setInstructionText(String text) {
		infoLabel.setText(text);
	}
}