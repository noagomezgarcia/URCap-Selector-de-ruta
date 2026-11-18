package com.noa.buttonselector.buttonselector.impl;

import java.util.Locale;
import com.ur.urcap.api.contribution.ViewAPIProvider;
import com.ur.urcap.api.contribution.program.ContributionConfiguration;
import com.ur.urcap.api.contribution.program.CreationContext;
import com.ur.urcap.api.contribution.program.ProgramAPIProvider;
import com.ur.urcap.api.contribution.program.swing.SwingProgramNodeService;
import com.ur.urcap.api.domain.data.DataModel;

public class SelectorProgramNodeService implements SwingProgramNodeService<SelectorProgramNodeContribution, SelectorProgramNodeView> {

	@Override
	public String getId() {
		return "ButtonSelectorNode";
	}

	@Override
	public void configureContribution(ContributionConfiguration configuration) {
		configuration.setChildrenAllowed(true);
	}

	@Override
	public String getTitle(Locale locale) {
		return "Selector por Botón";
	}

	@Override
	public SelectorProgramNodeView createView(ViewAPIProvider apiProvider) {
		return new SelectorProgramNodeView();
	}

	@Override
	public SelectorProgramNodeContribution createNode(ProgramAPIProvider apiProvider, SelectorProgramNodeView view,
			DataModel model, CreationContext context) {
		return new SelectorProgramNodeContribution(apiProvider, view, model, context);
	}
}