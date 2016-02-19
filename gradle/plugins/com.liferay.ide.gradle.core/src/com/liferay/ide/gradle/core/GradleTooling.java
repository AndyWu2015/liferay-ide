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

package com.liferay.ide.gradle.core;

import com.liferay.blade.gradle.model.CustomModel;
import com.liferay.ide.core.util.CoreUtil;
import com.liferay.ide.core.util.FileUtil;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.file.Files;
import java.util.Set;

import org.eclipse.core.runtime.Platform;
import org.gradle.tooling.GradleConnector;
import org.gradle.tooling.ModelBuilder;
import org.gradle.tooling.ProjectConnection;

/**
 * @author Gregory Amerson
 * @author Terry Jia
 */
public class GradleTooling
{

    public static <T> T getModel( Class<T> modelClass, File cacheDir, File projectDir ) throws Exception
    {

        T retval = null;

        final GradleConnector connector = GradleConnector.newConnector();
        connector.forProjectDirectory( projectDir );

        ProjectConnection connection = null;

        try
        {
            connection = connector.connect();

            ModelBuilder<T> modelBuilder = (ModelBuilder<T>) connection.model( modelClass );

            String modelLocation = Platform.getBundle( "com.liferay.blade.gradle.model" ).getLocation();

            File modelBundle = new File( modelLocation.replaceAll( "reference:", "" ).replaceAll( "file:", "" ) );

            String pluginLocation = Platform.getBundle( "com.liferay.blade.gradle.plugin" ).getLocation();

            File pluginBundle = new File( pluginLocation.replaceAll( "reference:", "" ).replaceAll( "file:", "" ) );

            final String initScriptTemplate =
                CoreUtil.readStreamToString( GradleTooling.class.getResourceAsStream( "init.gradle" ) );

            String initScriptContents = initScriptTemplate.replaceFirst(
                "%model%", modelBundle.toString().replaceAll( "\\\\", "/" ) ).replaceFirst(
                    "%plugin%", pluginBundle.toString().replaceAll( "\\\\", "/" ) );

            File scriptFile = Files.createTempFile( "ide", "init.gradle" ).toFile();

            FileUtil.writeFileFromStream( scriptFile, new ByteArrayInputStream( initScriptContents.getBytes() ) );

            modelBuilder.withArguments( "--init-script", scriptFile.getAbsolutePath() );

            retval = modelBuilder.get();
        }
        finally
        {
            if( connection != null )
            {
                connection.close();
            }
        }

        return retval;
    }

    public static Set<File> getOutputFiles( File cacheDir, File buildDir ) throws Exception
    {

        final CustomModel model = getModel( CustomModel.class, cacheDir, buildDir );

        return model.getOutputFiles();
    }

}
