package com.kos.wordcounter.core;

import com.kos.wordcounter.core.io.FilenameUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Класс открывает текстовый файл даже из архива (1 уровень вложенности)
 * Created by Kos on 24.10.2016.
 */
public class TextFileReader {

	public static String readFromPath(String fileName, String encoding) throws IOException {
		File p = new File(fileName);

		ByteArrayOutputStream result = new ByteArrayOutputStream();
		//List<String> result=new ArrayList<>();
		try {
			if (p.isFile()) {
				fromFile(result,p);
			}

			File u = p;
			StackFile parents = null;
			String complete = "";

			while (u != null) {
				if ("zip".equals(getExtension(u.getName()).toLowerCase())) {
					parents = new StackFile(u, complete, parents);
				}
				complete = (complete.isEmpty()) ? u.getName() : (u.getName() + "/" + complete);

				u = u.getParentFile();
			}

			while (parents != null) {
				if (parents.path.isFile()) {
					fromZip(result,parents.path, parents.fileName );
					parents = null;
				} else {
					parents = parents.next;
				}
			}

			return result.toString(encoding);
		} finally {
			result.close();
		}
	}

	public static File extractFileFromPath(String fileName) {
		return extractFileFromPath(new File(fileName));
	}

	public static File extractFileFromPath(File pathFile){
		try {
			if (pathFile.isFile()) {
				return pathFile;
			}
			File u = pathFile;

			while (u != null) {
				if ("zip".equals(getExtension(u.getName()).toLowerCase())) {
					if (u.isFile())
						return u;
				}
				u = u.getParentFile();
			}
		}catch (Exception ignored){

		}
		return null;

	}



	public static long getFileDate(String fileName) {
		try {
			File p = extractFileFromPath(fileName);
			if (p!=null)
				return p.lastModified();
		}catch (Exception e){

		}
		return 0L;
	}

	public static long getFileDate(File file) {
		try {
			File p = extractFileFromPath(file);
			if (p!=null)
				return p.lastModified();
		}catch (Exception e){

		}
		return 0L;
	}

	public static List<String> readLinesFromPath(String fileName, String encoding) throws IOException {
		File p = new File(fileName);

		List<String> result=new ArrayList<>();

			if (p.isFile()) {
				fromFile(result,p,encoding);
			}

			File u = p;
			StackFile parents = null;
			String complete = "";

			while (u != null) {
				if ("zip".equals(getExtension(u.getName()).toLowerCase())) {
					parents = new StackFile(u, complete, parents);
				}
				complete = (complete.isEmpty()) ? u.getName() : (u.getName() + "/" + complete);

				u = u.getParentFile();
			}

			while (parents != null) {
				if (parents.path.isFile()) {
					fromZip(result,parents.path, parents.fileName ,encoding);
					parents = null;
				} else {
					parents = parents.next;
				}
			}

		return result;
	}


	public static String getExtension(String fileName) {
		return FilenameUtils.getExtension(fileName).toLowerCase();
	}

	public static void fromZip(ByteArrayOutputStream result, File in, String fileName ) throws IOException {
		boolean b = true;

		ZipFile zipFile = new ZipFile(in);
		try {
			Enumeration<? extends ZipEntry> entries = zipFile.entries();
			while (entries.hasMoreElements() && b) {
				ZipEntry entry = entries.nextElement();

				if (!entry.isDirectory()) {

					if (fileName.equals(entry.getName())) {
						b = false;
						readBytesFromStreamClose(result,zipFile.getInputStream(entry));
					}
				}
			}
		} finally {
			zipFile.close();
		}
	}

	public static void fromZip(List<String> result, File in, String fileName , String encoding) throws IOException {
		boolean b = true;

		ZipFile zipFile = new ZipFile(in);
		try {
			Enumeration<? extends ZipEntry> entries = zipFile.entries();
			while (entries.hasMoreElements() && b) {
				ZipEntry entry = entries.nextElement();

				if (!entry.isDirectory()) {

					if (fileName.equals(entry.getName())) {
						b = false;
						readLinesFromStreamClose(result,zipFile.getInputStream(entry),encoding);
					}
				}
			}
		} finally {
			zipFile.close();
		}
	}


	public static void fromFile(ByteArrayOutputStream result, File in) throws IOException {
		readBytesFromStreamClose(result,new FileInputStream(in));

	}

	public static void fromFile(List<String> result, File in, String encoding) throws IOException {
		readLinesFromStreamClose(result,new FileInputStream(in),encoding);

	}


	public static void readBytesFromStream(ByteArrayOutputStream result, InputStream stream) throws IOException {
		byte[] buffer = new byte[1024];
		int length = 0;
		while ((length = stream.read(buffer)) > 0) {
			result.write(buffer, 0, length);
		}
	}

	public static void readBytesFromStreamClose(ByteArrayOutputStream result, InputStream stream) throws IOException {
		try {
			readBytesFromStream(result, stream);
		} finally {
			stream.close();
		}
	}

	public static void readLinesFromStreamClose(List<String> result, InputStream stream, String encoding) throws IOException {
		BufferedReader reader=new BufferedReader(new InputStreamReader(stream,encoding));
		try {
			String line;
			while ((line = reader.readLine()) != null) {
				result.add(line);
			}
		} finally {
			reader.close();
		}
	}

}
