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
package com.googlecode.gwtphonegap.client.file.browser.service;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.googlecode.gwtphonegap.client.file.DirectoryEntry;
import com.googlecode.gwtphonegap.client.file.EntryBase;
import com.googlecode.gwtphonegap.client.file.FileCallback;
import com.googlecode.gwtphonegap.client.file.FileEntry;
import com.googlecode.gwtphonegap.client.file.FileError;
import com.googlecode.gwtphonegap.client.file.FileObject;
import com.googlecode.gwtphonegap.client.file.FileSystem;
import com.googlecode.gwtphonegap.client.file.FileWriter;
import com.googlecode.gwtphonegap.client.file.Flags;
import com.googlecode.gwtphonegap.client.file.Metadata;
import com.googlecode.gwtphonegap.client.file.browser.DirectoryEntryBrowserImpl;
import com.googlecode.gwtphonegap.client.file.browser.FileEntryBrowserImpl;
import com.googlecode.gwtphonegap.client.file.browser.FileErrorException;
import com.googlecode.gwtphonegap.client.file.browser.FileObjectBrowserImpl;
import com.googlecode.gwtphonegap.client.file.browser.FileSystemBrowserImpl;
import com.googlecode.gwtphonegap.client.file.browser.FileWriterBrowserImpl;
import com.googlecode.gwtphonegap.client.file.browser.MetaDataBrowserImpl;
import com.googlecode.gwtphonegap.client.file.browser.dto.FileObjectDTO;
import com.googlecode.gwtphonegap.client.file.browser.dto.FileSystemDTO;
import com.googlecode.gwtphonegap.client.file.browser.dto.FileSystemEntryDTO;
import com.googlecode.gwtphonegap.client.file.browser.dto.FileWriterDTO;
import com.googlecode.gwtphonegap.client.file.browser.dto.FlagsDTO;
import com.googlecode.gwtphonegap.client.file.browser.dto.MetaDataDTO;
import com.googlecode.gwtphonegap.collection.shared.CollectionFactory;
import com.googlecode.gwtphonegap.collection.shared.LightArray;


/**
 * @author Daniel Kurka
 * 
 */
public class FileSystemController {
	private static final FileRemoteServiceAsync service = GWT.create(FileRemoteService.class);

	public FileSystemController() {

	}

	/**
	 * @param fullPath
	 * @param callback
	 */
	public void readDirectory(String fullPath, final FileCallback<LightArray<EntryBase>, FileError> callback) {
		service.readDirectory(fullPath, new AsyncCallback<ArrayList<FileSystemEntryDTO>>() {

			@Override
			public void onFailure(Throwable caught) {
				handleError(callback, caught);

			}

			@Override
			public void onSuccess(ArrayList<FileSystemEntryDTO> result) {

				LightArray<EntryBase> entries = CollectionFactory.constructArray();
				for (FileSystemEntryDTO entry : result) {

					EntryBase base;
					if (entry.isFile()) {
						base = new FileEntryBrowserImpl(entry, FileSystemController.this);
					} else {
						base = new DirectoryEntryBrowserImpl(entry, FileSystemController.this);
					}
					entries.push(base);
				}
				callback.onSuccess(entries);

			}
		});
	}

	/**
	 * @param fileSystemType
	 * @param size
	 * @param callback
	 */
	public void requestFileSystem(int fileSystemType, int size, final FileCallback<FileSystem, FileError> callback) {
		service.requestFileSystem(fileSystemType, size, new AsyncCallback<FileSystemDTO>() {

			@Override
			public void onSuccess(FileSystemDTO result) {
				FileSystemBrowserImpl fileSystemBrowserImpl = new FileSystemBrowserImpl(result, FileSystemController.this);
				callback.onSuccess(fileSystemBrowserImpl);

			}

			@Override
			public void onFailure(Throwable caught) {
				handleError(callback, caught);

			}
		});

	}

	/**
	 * @param fullPath
	 * @param callback
	 */
	public void readParent(String fullPath, final FileCallback<DirectoryEntry, FileError> callback) {
		service.getParent(fullPath, new AsyncCallback<FileSystemEntryDTO>() {

			@Override
			public void onFailure(Throwable caught) {
				handleError(callback, caught);

			}

			@Override
			public void onSuccess(FileSystemEntryDTO result) {
				callback.onSuccess(new DirectoryEntryBrowserImpl(result, FileSystemController.this));

			}
		});

	}

	/**
	 * @param entry
	 * @param callback
	 */
	public void readAsText(FileObject entry, AsyncCallback<String> callback) {
		service.readAsText(entry.getFullPath(), callback);

	}

	/**
	 * @param fileEntryBrowserImpl
	 * @param callback
	 */
	public void createWriter(FileEntryBrowserImpl fileEntryBrowserImpl, final FileCallback<FileWriter, FileError> callback) {
		service.createWriter(fileEntryBrowserImpl.getFullPath(), fileEntryBrowserImpl.getName(), new AsyncCallback<FileWriterDTO>() {

			@Override
			public void onFailure(Throwable caught) {
				handleError(callback, caught);

			}

			@Override
			public void onSuccess(FileWriterDTO result) {
				FileWriterBrowserImpl fileWriterBrowserImpl = new FileWriterBrowserImpl(result, FileSystemController.this);
				callback.onSuccess(fileWriterBrowserImpl);

			}
		});
	}

	/**
	 * @param result
	 * @param text
	 * @param callback
	 */
	public void writeFile(FileWriterDTO result, String text, AsyncCallback<FileWriterDTO> callback) {
		service.writeFile(result, text, callback);

	}

	/**
	 * @param fullPath
	 * @param callback
	 */
	public void getMetaData(String fullPath, final FileCallback<Metadata, FileError> callback) {
		service.getMetaData(fullPath, new AsyncCallback<MetaDataDTO>() {

			@Override
			public void onFailure(Throwable caught) {
				handleError(callback, caught);

			}

			@Override
			public void onSuccess(MetaDataDTO result) {

				callback.onSuccess(new MetaDataBrowserImpl(result));

			}
		});

	}

	/**
	 * @param absPath
	 * @param flags
	 * @param callback
	 */
	public void getFile(String absPath, Flags flags, final FileCallback<FileEntry, FileError> callback) {
		service.getFile(absPath, new FlagsDTO(flags), new AsyncCallback<FileSystemEntryDTO>() {

			@Override
			public void onFailure(Throwable caught) {
				handleError(callback, caught);

			}

			@Override
			public void onSuccess(FileSystemEntryDTO result) {
				callback.onSuccess(new FileEntryBrowserImpl(result, FileSystemController.this));

			}
		});

	}

	public void removeDirectory(String fullPath, final FileCallback<Boolean, FileError> callback) {
		service.removeDirectory(fullPath, new AsyncCallback<Boolean>() {

			@Override
			public void onFailure(Throwable caught) {
				handleError(callback, caught);

			}

			@Override
			public void onSuccess(Boolean result) {
				callback.onSuccess(Boolean.TRUE);

			}
		});

	}

	/**
	 * @param fullPath
	 * @param callback
	 */
	public void removeFile(String fullPath, final FileCallback<Boolean, FileError> callback) {
		service.removeFile(fullPath, new AsyncCallback<Boolean>() {

			@Override
			public void onFailure(Throwable caught) {
				handleError(callback, caught);

			}

			@Override
			public void onSuccess(Boolean result) {
				callback.onSuccess(Boolean.TRUE);

			}
		});

	}

	/**
	 * @param callback
	 * @param caught
	 */
	private void handleError(final FileCallback<?, FileError> callback, Throwable caught) {
		if (caught instanceof FileErrorException) {
			FileErrorException fileErrorException = (FileErrorException) caught;
			callback.onFailure(fileErrorException);
		} else {
			callback.onFailure(new FileErrorException(FileError.INVALID_STATE_ERR));
		}
	}

	/**
	 * @param fileFullPath
	 * @param directoryFullPath
	 * @param newName
	 * @param callback
	 */
	public void moveFile(String fileFullPath, String directoryFullPath, String newName, final FileCallback<FileEntry, FileError> callback) {
		service.moveFile(fileFullPath, directoryFullPath, newName, new AsyncCallback<FileSystemEntryDTO>() {

			@Override
			public void onFailure(Throwable caught) {
				handleError(callback, caught);

			}

			@Override
			public void onSuccess(FileSystemEntryDTO result) {
				FileEntryBrowserImpl impl = new FileEntryBrowserImpl(result, FileSystemController.this);
				callback.onSuccess(impl);

			}
		});
	}

	/**
	 * @param filePath
	 * @param dirPath
	 * @param newName
	 * @param callback
	 */
	public void copyFile(String filePath, String dirPath, String newName, final FileCallback<FileEntry, FileError> callback) {
		service.copyFile(filePath, dirPath, newName, new AsyncCallback<FileSystemEntryDTO>() {

			@Override
			public void onFailure(Throwable caught) {
				handleError(callback, caught);

			}

			@Override
			public void onSuccess(FileSystemEntryDTO result) {
				FileEntryBrowserImpl impl = new FileEntryBrowserImpl(result, FileSystemController.this);
				callback.onSuccess(impl);

			}
		});

	}

	private String basePath;

	/**
	 * @param basePath
	 *            the basePath to set
	 */
	public void setBasePath(String basePath) {
		if (!basePath.endsWith("/")) {
			basePath = basePath + "/";
		}
		this.basePath = basePath;

	}

	public String toURI(String fullPath) {
		if (basePath == null) {
			throw new IllegalStateException("you have to set basePath on the fileSystem phonegap emulation before using this -> phonegap.getFile()");
		}
		return basePath + fullPath;
	}

	/**
	 * @param fullPath
	 * @param callback
	 */
	public void getFileObject(String fullPath, final FileCallback<FileObject, FileError> callback) {
		service.getFileObject(fullPath, new AsyncCallback<FileObjectDTO>() {

			@Override
			public void onFailure(Throwable caught) {
				handleError(callback, caught);

			}

			@Override
			public void onSuccess(FileObjectDTO result) {
				callback.onSuccess(new FileObjectBrowserImpl(result));

			}
		});

	}

	/**
	 * @param absPath
	 * @param flags
	 * @param callback
	 */
	public void getDirectory(String absPath, Flags flags, final FileCallback<DirectoryEntry, FileError> callback) {
		service.getDirectory(absPath, new FlagsDTO(flags), new AsyncCallback<FileSystemEntryDTO>() {

			@Override
			public void onFailure(Throwable caught) {
				handleError(callback, caught);

			}

			@Override
			public void onSuccess(FileSystemEntryDTO result) {
				callback.onSuccess(new DirectoryEntryBrowserImpl(result, FileSystemController.this));

			}
		});

	}

	/**
	 * @param fullPath
	 * @param callback
	 */
	public void removeRecursively(String fullPath, final FileCallback<Boolean, FileError> callback) {
		service.removeRecursively(fullPath, new AsyncCallback<Boolean>() {

			@Override
			public void onFailure(Throwable caught) {
				handleError(callback, caught);

			}

			@Override
			public void onSuccess(Boolean result) {
				callback.onSuccess(result);

			}
		});
	}

	/**
	 * @param fullPath
	 * @param newParent
	 * @param newName
	 * @param callback
	 */
	public void moveDirectory(String fullPath, String newParent, String newName, final FileCallback<DirectoryEntry, FileError> callback) {
		service.moveDirectory(fullPath, newParent, newName, new AsyncCallback<FileSystemEntryDTO>() {

			@Override
			public void onFailure(Throwable caught) {
				handleError(callback, caught);

			}

			@Override
			public void onSuccess(FileSystemEntryDTO result) {
				callback.onSuccess(new DirectoryEntryBrowserImpl(result, FileSystemController.this));

			}
		});

	}

	/**
	 * @param fullPath
	 * @param newDir
	 * @param newName
	 * @param callback
	 */
	public void copyDirectory(String fullPath, String newDir, String newName, final FileCallback<DirectoryEntry, FileError> callback) {
		service.copyDiretory(fullPath, newDir, newName, new AsyncCallback<FileSystemEntryDTO>() {

			@Override
			public void onFailure(Throwable caught) {
				handleError(callback, caught);

			}

			@Override
			public void onSuccess(FileSystemEntryDTO result) {
				callback.onSuccess(new DirectoryEntryBrowserImpl(result, FileSystemController.this));

			}
		});

	}

	/**
	 * @param entry
	 * @param callback
	 */
	public void readAsDataUrl(FileObject entry, AsyncCallback<String> callback) {
		service.readAsDataUrl(entry.getFullPath(), callback);

	}
}
