package com.noa.buttonselector.buttonselector.impl;

import com.ur.urcap.api.contribution.ProgramNodeContribution;
import com.ur.urcap.api.contribution.program.ProgramAPIProvider;
import com.ur.urcap.api.domain.data.DataModel;
import com.ur.urcap.api.domain.script.ScriptWriter;

public class BranchProgramNodeContribution implements ProgramNodeContribution {

	private final BranchProgramNodeView view;
	private final DataModel model;
	public static final String BRANCH_NAME_KEY = "branch_name";
	public static final String BRANCH_ID_KEY = "branch_id";

	public BranchProgramNodeContribution(ProgramAPIProvider apiProvider, BranchProgramNodeView view, DataModel model) {
		this.view = view;
		this.model = model;
	}

	public void configureBranch(String name, int choiceId) {
		model.set(BRANCH_NAME_KEY, name);
		model.set(BRANCH_ID_KEY, choiceId);
	}

	private String getBranchName() {
		return model.get(BRANCH_NAME_KEY, "Rama");
	}

	private int getBranchId() {
		return model.get(BRANCH_ID_KEY, 1);
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
		if (getBranchId() == 1) {
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