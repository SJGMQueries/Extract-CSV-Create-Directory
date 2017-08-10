package com.sjgm.question;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ExtraxtCSVFileData {

	private static String ROOT_DIR = "C:/Users/basanta.kumar.hota/Desktop/test";

	public static List<String> extract(String fileName) throws IOException {
		List<String> paths = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(new File(
				fileName)))) {
			String line = null;
			String formattedPath = "";
			while ((line = br.readLine()) != null) {
				String s = line.split(",,,")[1];
				if (s.startsWith("Path")) {
					String data = s.substring(s.indexOf("{") + 1,
							s.indexOf("}"));
					for (String splitData : data.split(";")) {
						splitData = splitData
								.substring(splitData.indexOf("\\") + 1,
										splitData.length());
						formattedPath = splitData.replace("\\", "/");
						paths.add(formattedPath);
					}
				}
			}
		}
		System.out.println("Total No of path Found : " + paths.size());
		return paths;
	}

	public static void createDir(String fileName) {
		int count = 0;
		Path path = null;
		String actualPath = "";
		try {
			List<String> paths = extract(fileName);
			for (String s : paths) {
				count++;
				actualPath = ROOT_DIR + s;
				path = Paths.get(actualPath);
				if (!Files.exists(path)) {
					try {
						Files.createDirectories(path);
					} catch (IOException e) {
						System.out.println("Error while create Directory.");
					}
				}
			}
		} catch (IOException e) {
			System.out.println("Error Message : " + e.getLocalizedMessage());
		}
		System.out.println("Total Directory created : " + count);
	}

	public static void main(String[] args) throws IOException {
		createDir("C:/Users/basanta.kumar.hota/Downloads/logFile- ExtraFilesAndFolders Sample.csv");
	}
}
