package com.noa.buttonselector.buttonselector.impl;

import com.ur.urcap.api.contribution.ProgramNodeContribution;
import com.ur.urcap.api.domain.script.ScriptWriter;

public class BranchProgramNodeContribution implements ProgramNodeContribution {

	private final BranchProgramNodeView view;
	private final boolean isGreen;

	public BranchProgramNodeContribution(BranchProgramNodeView view, boolean isGreen) {
		this.view = view;
		this.isGreen = isGreen;
	}

	private String getBranchName() {
		return isGreen ? "Si pulsa VERDE (DI0)" : "Si pulsa ROJO (DI1)";
	}

	@Override
	public void openView() {
		view.setInstructionText("Esta secuencia se ejecutará al pulsar: " + getBranchName());
	}

	@Override
	public void closeView() {}

	@Override
	public String getTitle() {
		return getBranchName();
	}

	@Override
	public boolean isDefined() {
		return true;
	}

	@Override
	public void generateScript(ScriptWriter writer) {
		if (isGreen) {
			writer.appendLine("if (btn_choice == 1):");
			writer.appendLine("  set_standard_digital_out(0, True)");
			writer.appendLine("  set_standard_digital_out(1, False)");
			writer.writeChildren();
		} else {
			writer.appendLine("elif (btn_choice == 2):");
			writer.appendLine("  set_standard_digital_out(0, False)");
			writer.appendLine("  set_standard_digital_out(1, True)");
			writer.writeChildren();
			writer.appendLine("end");
		}
	}
}