package com.noa.buttonselector.buttonselector.impl;

import java.util.Locale;
import com.ur.urcap.api.contribution.ViewAPIProvider;
import com.ur.urcap.api.contribution.program.ContributionConfiguration;
import com.ur.urcap.api.contribution.program.CreationContext;
import com.ur.urcap.api.contribution.program.ProgramAPIProvider;
import com.ur.urcap.api.contribution.program.swing.SwingProgramNodeService;
import com.ur.urcap.api.domain.data.DataModel;

public class GreenBranchProgramNodeService implements SwingProgramNodeService<BranchProgramNodeContribution, BranchProgramNodeView> {

	@Override
	public String getId() {
		return "GreenBranchNode";
	}

	@Override
	public void configureContribution(ContributionConfiguration configuration) {
		configuration.setChildrenAllowed(true);
		configuration.setUserInsertable(false);
	}

	@Override
	public String getTitle(Locale locale) {
		return "Si pulsa VERDE (DI0)";
	}

	@Override
	public BranchProgramNodeView createView(ViewAPIProvider apiProvider) {
		return new BranchProgramNodeView();
	}

	@Override
	public BranchProgramNodeContribution createNode(ProgramAPIProvider apiProvider, BranchProgramNodeView view,
			DataModel model, CreationContext context) {
		return new BranchProgramNodeContribution(view, true);
	}
}