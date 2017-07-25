package com.kos.wordcounter.core;

import java.io.File;

/**
 * Created by Kos on 24.10.2016.
 */
public class StackFile {
	public File path;
	public String fileName;
	public StackFile next;

	public StackFile(File path, String fileName, StackFile next) {
		this.path = path;
		this.fileName = fileName;
		this.next = next;
	}
}
