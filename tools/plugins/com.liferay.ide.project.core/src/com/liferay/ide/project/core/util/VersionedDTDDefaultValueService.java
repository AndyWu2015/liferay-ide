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

package com.liferay.ide.project.core.util;

import com.liferay.ide.core.util.StringPool;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.sapphire.DefaultValueService;
import org.eclipse.sapphire.Element;
import org.eclipse.sapphire.Resource;
import org.eclipse.sapphire.modeling.xml.RootXmlResource;

import org.w3c.dom.Document;

/**
 * @author Gregory Amerson
 */
public class VersionedDTDDefaultValueService extends DefaultValueService {

	public VersionedDTDDefaultValueService(Pattern systemIdPattern) {
		_systemIdPattern = systemIdPattern;
	}

	@Override
	protected String compute() {
		String defaultVersion = null;

		Resource resource = context(Element.class).resource();

		RootXmlResource xmlResource = resource.adapt(RootXmlResource.class);

		if (xmlResource != null) {
			Document document = xmlResource.getDomDocument();

			if ((document != null) && (document.getDoctype() != null)) {
				String systemId = document.getDoctype().getSystemId();

				Matcher matcher = _systemIdPattern.matcher(systemId);

				if (matcher.matches()) {
					defaultVersion = matcher.group(1);
				}
			}
		}

		if (defaultVersion == null) {

			// default should be 6.0.0

			defaultVersion = "6.0.0";
		}

		return defaultVersion.replaceAll(StringPool.UNDERSCORE, ".");
	}

	private final Pattern _systemIdPattern;

}