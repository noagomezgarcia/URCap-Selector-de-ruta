package com.noa.buttonselector.buttonselector.impl;

import java.util.Iterator;
import com.ur.urcap.api.contribution.ProgramNodeContribution;
import com.ur.urcap.api.contribution.program.ProgramAPIProvider;
import com.ur.urcap.api.domain.data.DataModel;
import com.ur.urcap.api.domain.program.structure.TreeNode;
import com.ur.urcap.api.domain.script.ScriptWriter;

public class BranchProgramNodeContribution implements ProgramNodeContribution {

	private final ProgramAPIProvider apiProvider;
	private final BranchProgramNodeView view;

	public BranchProgramNodeContribution(ProgramAPIProvider apiProvider, BranchProgramNodeView view, DataModel model) {
		this.apiProvider = apiProvider;
		this.view = view;
	}

	// Detecta automáticamente si es el primer hijo (Verde) o el segundo (Rojo)
	private boolean isGreenBranch() {
		TreeNode myNode = apiProvider.getProgramAPI().getProgramModel().getRootTreeNode(this);
		TreeNode parentNode = myNode.getParent();
		if (parentNode != null) {
			Iterator<TreeNode> siblings = parentNode.getChildren().iterator();
			if (siblings.hasNext()) {
				return siblings.next().equals(myNode);
			}
		}
		return true;
	}

	private String getBranchName() {
		return isGreenBranch() ? "Si pulsa VERDE (DI0)" : "Si pulsa ROJO (DI1)";
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
		if (isGreenBranch()) {
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