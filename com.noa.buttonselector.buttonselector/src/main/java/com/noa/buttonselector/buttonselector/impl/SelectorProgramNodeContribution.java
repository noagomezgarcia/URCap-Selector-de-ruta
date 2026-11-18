package com.noa.buttonselector.buttonselector.impl;

import com.ur.urcap.api.contribution.ProgramNodeContribution;
import com.ur.urcap.api.contribution.program.CreationContext;
import com.ur.urcap.api.contribution.program.ProgramAPIProvider;
import com.ur.urcap.api.domain.ProgramAPI;
import com.ur.urcap.api.domain.data.DataModel;
import com.ur.urcap.api.domain.program.ProgramModel;
import com.ur.urcap.api.domain.program.nodes.ProgramNodeFactory;
import com.ur.urcap.api.domain.program.structure.TreeNode;
import com.ur.urcap.api.domain.program.structure.TreeStructureException;
import com.ur.urcap.api.domain.script.ScriptWriter;
import com.ur.urcap.api.domain.undoredo.UndoRedoManager;
import com.ur.urcap.api.domain.undoredo.UndoableChanges;

public class SelectorProgramNodeContribution implements ProgramNodeContribution {

	private final ProgramAPIProvider apiProvider;
	private final SelectorProgramNodeView view;
	private final DataModel model;
	private final UndoRedoManager undoRedoManager;

	private static final String BLINK_KEY = "blink_ms";
	private static final int DEFAULT_BLINK = 300;

	public SelectorProgramNodeContribution(ProgramAPIProvider apiProvider, SelectorProgramNodeView view,
			DataModel model, CreationContext context) {
		this.apiProvider = apiProvider;
		this.view = view;
		this.model = model;
		this.undoRedoManager = apiProvider.getProgramAPI().getUndoRedoManager();

		if (context.getNodeCreationType() == CreationContext.NodeCreationType.NEW) {
			createInitialBranches();
		}
	}

	private void createInitialBranches() {
		ProgramAPI programAPI = apiProvider.getProgramAPI();
		ProgramModel programModel = programAPI.getProgramModel();
		ProgramNodeFactory nf = programModel.getProgramNodeFactory();
		TreeNode root = programModel.getRootTreeNode(this);

		try {
			root.setChildSequenceLocked(true);

			// Crear Rama Verde (DI0 / DO0) -> ID 1
			TreeNode greenTreeNode = root.addChild(nf.createURCapProgramNode(BranchProgramNodeService.class));
			((BranchProgramNodeContribution) greenTreeNode.getProgramNode()).configureBranch("Si pulsa VERDE (DI0)", 1);

			// Crear Rama Roja (DI1 / DO1) -> ID 2
			TreeNode redTreeNode = root.addChild(nf.createURCapProgramNode(BranchProgramNodeService.class));
			((BranchProgramNodeContribution) redTreeNode.getProgramNode()).configureBranch("Si pulsa ROJO (DI1)", 2);

		} catch (TreeStructureException e) {
			e.printStackTrace();
		}
	}

	public void onBlinkRateChanged(final int ms) {
		undoRedoManager.recordChanges(new UndoableChanges() {
			@Override
			public void executeChanges() {
				model.set(BLINK_KEY, ms);
			}
		});
	}

	private int getBlinkRate() {
		return model.get(BLINK_KEY, DEFAULT_BLINK);
	}

	@Override
	public void openView() {
		view.setBlinkRate(getBlinkRate());
	}

	@Override
	public void closeView() {}

	@Override
	public String getTitle() {
		return "Selector por Botón (" + getBlinkRate() + "ms)";
	}

	@Override
	public boolean isDefined() {
		return true;
	}

	@Override
	public void generateScript(ScriptWriter writer) {
		double sleepSeconds = getBlinkRate() / 1000.0;

		writer.appendLine("local btn_choice = 0");
		writer.appendLine("local blink_timer = 0");
		writer.appendLine("local toggle = True");
		writer.appendLine("while (btn_choice == 0):");
		writer.appendLine("  if (get_standard_digital_in(0) == True):");
		writer.appendLine("    btn_choice = 1");
		writer.appendLine("  elif (get_standard_digital_in(1) == True):");
		writer.appendLine("    btn_choice = 2");
		writer.appendLine("  end");
		writer.appendLine("  if (blink_timer >= " + sleepSeconds + "):");
		writer.appendLine("    set_standard_digital_out(0, toggle)");
		writer.appendLine("    set_standard_digital_out(1, not toggle)");
		writer.appendLine("    toggle = not toggle");
		writer.appendLine("    blink_timer = 0");
		writer.appendLine("  end");
		writer.appendLine("  sync()");
		writer.appendLine("  blink_timer = blink_timer + get_steptime()");
		writer.appendLine("end");

		// Llama a los dos nodos hijos para que escriban sus bloques if / elif
		writer.writeChildren();

		// Apagar ambas luces al terminar la rama elegida
		writer.appendLine("set_standard_digital_out(0, False)");
		writer.appendLine("set_standard_digital_out(1, False)");
	}
}