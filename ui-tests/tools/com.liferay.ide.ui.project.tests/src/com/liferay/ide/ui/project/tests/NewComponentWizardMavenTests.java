/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.ide.ui.project.tests;

import com.liferay.ide.ui.liferay.SwtbotBase;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Terry Jia
 */
public class NewComponentWizardMavenTests extends SwtbotBase {

	@Test
	public void createComponentPortlet() {
	}

	@Test
	public void createComponentModelListener() {
		String projectName = "test-component-model-listener-maven";

		wizardAction.openNewLiferayModuleWizard();
		wizardAction.prepareLiferayModuleMaven(projectName, MVC_PORTLET);
		wizardAction.finishToWait();

		wizardAction.openNewLiferayComponentClassWizard();
		wizardAction.prepareComponentClass(projectName, MODEL_LISTENER);
		wizardAction.openSelectModelClassAndServiceDialog();

		dialogAction.prepareText("*com.liferay.blogs.kernel.model.BlogsEntry");
		dialogAction.confirm();

		wizardAction.finishToWait();

		Assert.assertTrue(
			viewAction.visibleProjectFileTry(
				projectName, "src/main/java", "test.component.maven.portlet", "TestComponentMavenModelListener.java"));

		viewAction.deleteProject(projectName);
	}

	@Test
	public void createComponentServiceWrapper() {
	}

}