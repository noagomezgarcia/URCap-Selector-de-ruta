package com.noa.buttonselector.buttonselector.impl;

import java.util.Locale;
import com.ur.urcap.api.contribution.ViewAPIProvider;
import com.ur.urcap.api.contribution.program.ContributionConfiguration;
import com.ur.urcap.api.contribution.program.CreationContext;
import com.ur.urcap.api.contribution.program.ProgramAPIProvider;
import com.ur.urcap.api.contribution.program.swing.SwingProgramNodeService;
import com.ur.urcap.api.domain.data.DataModel;

public class BranchProgramNodeService implements SwingProgramNodeService<BranchProgramNodeContribution, BranchProgramNodeView> {

	@Override
	public String getId() {
		return "ButtonBranchNode";
	}

	@Override
	public void configureContribution(ContributionConfiguration configuration) {
		configuration.setChildrenAllowed(true); // Permite meter movimientos dentro de esta rama
		configuration.setUserInsertable(false); // Solo el nodo padre puede crear esta rama
	}

	@Override
	public String getTitle(Locale locale) {
		return "Ruta Botón";
	}

	@Override
	public BranchProgramNodeView createView(ViewAPIProvider apiProvider) {
		return new BranchProgramNodeView();
	}

	@Override
	public BranchProgramNodeContribution createNode(ProgramAPIProvider apiProvider, BranchProgramNodeView view,
			DataModel model, CreationContext context) {
		return new BranchProgramNodeContribution(apiProvider, view, model);
	}
}