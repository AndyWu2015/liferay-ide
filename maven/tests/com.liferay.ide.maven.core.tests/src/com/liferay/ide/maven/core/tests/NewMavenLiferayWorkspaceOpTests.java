/*******************************************************************************
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
 *
 *******************************************************************************/

package com.liferay.ide.maven.core.tests;

import static org.junit.Assert.assertTrue;

import com.liferay.ide.core.util.CoreUtil;
import com.liferay.ide.core.util.FileUtil;
import com.liferay.ide.project.core.workspace.NewLiferayWorkspaceOp;

import java.io.File;

import org.eclipse.core.runtime.IPath;
import org.eclipse.sapphire.modeling.ProgressMonitor;
import org.junit.Test;

/**
 * @author Andy Wu
 */
public class NewMavenLiferayWorkspaceOpTests
{

    @Test
    public void testNewMavenLiferayWorkspaceOp() throws Exception
    {
        NewLiferayWorkspaceOp op = NewLiferayWorkspaceOp.TYPE.instantiate();

        String projectName = "test-liferay-workspace";

        IPath workspaceLocation = CoreUtil.getWorkspaceRoot().getLocation();

        op.setWorkspaceName( projectName );
        op.setUseDefaultLocation( false );
        op.setLocation( workspaceLocation.toPortableString() );
        op.setProjectProvider( "maven-liferay-workspace" );

        op.execute( new ProgressMonitor() );

        String projectLocation = workspaceLocation.append( projectName ).toPortableString();

        File pomFile = new File( projectLocation, "pom.xml" );

        assertTrue( pomFile.exists() );

        String content = FileUtil.readContents( pomFile );

        assertTrue( content.contains("com.liferay.portal.tools.bundle.support") );
    }
}
