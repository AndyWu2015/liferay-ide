package com.liferay.ide.studio.ui;

import java.net.URI;
import java.net.URISyntaxException;

import org.eclipse.equinox.p2.ui.ProvisioningUI;

/**]
 * @author Andy Wu
 */
public class AddLiferayUpdateSiteAction
{

    public void addUpdateSite()
    {
        ProvisioningUI ui = ProvisioningUI.getDefaultUI();

        URI uri = null;

        try
        {
            uri = new URI( "https://releases.liferay.com/tools/ide/latest/stable/" );
        }
        catch( URISyntaxException e )
        {
            e.printStackTrace();
        }

        ui.getRepositoryTracker().addRepository(uri, "Liferay IDE Stable releases", ui.getSession());
    }

}
