/*
 * Copyright 2011 Daniel Kurka
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.googlecode.gwtphonegap.client.file.browser;

import java.util.Date;

import com.googlecode.gwtphonegap.client.file.Metadata;
import com.googlecode.gwtphonegap.client.file.browser.dto.MetaDataDTO;


/**
 * @author Daniel Kurka
 * 
 */
public class MetaDataBrowserImpl implements Metadata {

	private final MetaDataDTO result;

	/**
	 * @param result
	 */
	public MetaDataBrowserImpl(MetaDataDTO result) {
		this.result = result;
	}

	@Override
	public Date getModificationTime() {
		return result.getModificationTime();
	}

	@Override
	public Long getSize() {
		return result.getSize();
	}

	@Override
	public String getType() {
		return result.getType();
	}

	@Override
	public String getName() {
		return result.getName();
	}

	@Override
	public String getFullPath() {
		return result.getFullPath();
	}
}
