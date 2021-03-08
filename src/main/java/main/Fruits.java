package main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Fruits {

	public static void main(String[] args) throws IOException {

		File fruitText = new File("C:\\Users\\Microsoft\\eclipse-workspace\\Files\\src\\main\\resources\\ListOfFruits.txt");
		Scanner input = new Scanner(fruitText);
		File file ;

		String folderName = "" ;
		while (input.hasNextLine()) {
			String line = input.nextLine();
			if (line.contains("FAMILY")) {
				folderName = line;
				int i = folderName.indexOf(" ");
				folderName = folderName.substring(0, i);
				System.out.println(folderName);
				new File("C:\\Users\\Microsoft\\eclipse-workspace\\Files\\src\\main\\resources\\"+folderName).mkdir();
			}else {
				file = new File("C:\\Users\\Microsoft\\eclipse-workspace\\Files\\src\\main\\resources\\"+folderName+"\\"+line);
				if (file.createNewFile())
				{
				    System.out.println("File is created!");
				    FileWriter writer = new FileWriter(file);
				    writer.write("Hello to Fruits:"+line);
				    writer.close();
				} else {
				    System.out.println("File already exists.");
				}
			}
		}

		input.close();
	}
}
