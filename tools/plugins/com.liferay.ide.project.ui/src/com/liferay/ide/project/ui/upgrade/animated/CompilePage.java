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

package com.liferay.ide.project.ui.upgrade.animated;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.liferay.ide.project.ui.upgrade.action.CompileAction;
import com.liferay.ide.ui.util.UIUtil;

/**
 * @author Andy Wu
 * @author Simon Jiang
 * @author Joye Luo
 */
public class CompilePage extends Page
{

    PageAction[] actions = { new PageFinishAction(), new PageSkipAction() };

    public CompilePage( Composite parent, int style, LiferayUpgradeDataModel dataModel )
    {
        super( parent, style, dataModel );
        GridLayout layout = new GridLayout( 1, true );
        this.setLayout( layout );

        Label title = new Label( this, SWT.LEFT );
        title.setText( "Compile" );
        title.setFont( new Font( null, "Times New Roman", 16, SWT.NORMAL ) );

        Text content = new Text( this, SWT.MULTI );
        final String descriptor =
            "This step will try to package your upgraded projects to see if it can run successfully.\n" +
                "If it failed, you can see error logs in console view.\n";
        content.setText( descriptor );
        content.setEditable( false );
        content.setBackground( getDisplay().getSystemColor( SWT.COLOR_WIDGET_BACKGROUND ) );

        Button compileButton = new Button( this, SWT.PUSH );
        compileButton.setText( "Compile" );
        compileButton.addSelectionListener( new SelectionAdapter()
        {

            @Override
            public void widgetSelected( SelectionEvent e )
            {
                Action compile = new CompileAction( "compile", UIUtil.getActiveShell() );
                compile.run();
            }
        } );

        setActions( actions );
        this.setPageId( COMPILE_PAGE_ID );
    }
}
