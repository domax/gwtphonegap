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
package com.googlecode.gwtphonegap.client.file.js;

import java.util.Date;

import com.google.gwt.core.client.JavaScriptObject;
import com.googlecode.gwtphonegap.client.file.Metadata;

public final class MetaDataJsImpl extends JavaScriptObject implements Metadata {

	protected MetaDataJsImpl() {}

	@Override
	public native Date getModificationTime() /*-{
		return @com.googlecode.gwtphonegap.client.file.js.MetaDataJsImpl::createDate(D)(this.modificationTime.getTime());
	}-*/;

	@Override
	public native Long getSize() /*-{
		return @com.googlecode.gwtphonegap.client.file.js.MetaDataJsImpl::createSize(D)(this.size);
	}-*/;

	@Override
	public native String getType() /*-{
		return this.type;
	}-*/;

	@Override
	public native String getName() /*-{
		return this.name;
	}-*/;

	@Override
	public native String getFullPath() /*-{
		return this.fullPath;
	}-*/;

	private static Date createDate(double value) {
		return new Date(Math.round(value));
	}

	@SuppressWarnings("UnnecessaryBoxing")
	private static Long createSize(double value) {
		return new Long(Math.round(value));
	}
}