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
package com.googlecode.gwtphonegap.client.file.browser.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Daniel Kurka
 * 
 */
public class MetaDataDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9050831658160127146L;
	private Date lastModified;
	private long size;
	private String type;
	private String name;
	private String fullPath;

	//GWT
	@SuppressWarnings("unused")
	private MetaDataDTO() {
		this(null, 0, null, null, null);
	}

	public MetaDataDTO(Date lastModified, long size, String type, String name, String fullPath) {
		this.lastModified = lastModified;
		this.size = size;
		this.type = type;
		this.name = name;
		this.fullPath = fullPath;
	}

	/**
	 * @return
	 */
	public Date getModificationTime() {
		return lastModified;
	}

	public long getSize() {
		return size;
	}

	public String getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public String getFullPath() {
		return fullPath;
	}
}
