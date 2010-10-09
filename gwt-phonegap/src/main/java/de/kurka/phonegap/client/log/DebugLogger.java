/*
 * Copyright 2010 Daniel Kurka
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
package de.kurka.phonegap.client.log;

/**
 * 
 * This class provides access to the debugging console.
 * 
 * 
 * @author Daniel Kurka
 *
 */
public class DebugLogger {

	public native boolean isAvailable() /*-{
		if(!(typeof($wnd.debug) == "undefined"))
		{
		return true;
		}
		return false;
	}-*/;

	public native void info(String message) /*-{
		$wnd.debug.log(message);
	}-*/;

	public native void warn(String message) /*-{
		$wnd.debug.warn(message);
	}-*/;

	public native void error(String message) /*-{
		$wnd.debug.error(message);
	}-*/;
}
