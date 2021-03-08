package readCsv;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

public class BankStatementAnalyzer {

	public static void main(String[] args) throws FileNotFoundException {
		
		String fileName = "C:\\Users\\Microsoft\\Downloads\\sheet-SWER - Sheet1.csv";
		File file = new File(fileName);
		Scanner s = new Scanner(file);
		ArrayList<String[]> graph = new ArrayList<String[]>();
		while (s.hasNext()) {
			String data = s.nextLine();
			String[] values = data.split(",");
			graph.add(values);
		}
		String[] last = graph.get(graph.size() - 1);
		double last2 = convertToNumber(last[last.length - 1]);
		String[] init = graph.get(1);
		double init2 = convertToNumber(init[init.length - 1]);

		String str = checkBalance(init2, last2);
		
		System.out.println("---------------------------");
		System.out.println(str);
		System.out.println("---------------------------");
		System.out.println("Number of transactions " + (graph.size() - 2));

		Double[] number = new Double[graph.size()-2];
		String[] company = new String[graph.size()-2];
		for (int i = 2; i < graph.size(); i++) {
			String value = graph.get(i)[2];
			String key = graph.get(i)[1];
			number[i-2] = convertToNumber(value);
			company[i-2] = key;
			
		}
		bubbleSort(number,company);
		System.out.println(Arrays.toString(number));
		System.out.println(Arrays.toString(company));
        System.out.println("---------------------------");
        System.out.println("Top 1 expenses is " + number[number.length-1] + " company:" + company[company.length-1]);
        System.out.println("Top 2 expenses is " + number[number.length-2] + " company:" + company[company.length-2]);
        System.out.println("Top 3 expenses is " + number[number.length-3] + " company:" + company[company.length-3]);
        System.out.println("---------------------------");
        
		Set<String> hash_Set = new HashSet<String>();
		for (int i = 2; i < graph.size(); i++) {
			hash_Set.add(graph.get(i)[1]);
		}
		
		HashMap<String, Double> category = new HashMap<String, Double>();
		for(String key : hash_Set) {
			category.put(key, 0.0);
		}
		for (int i = 2; i < graph.size(); i++) {
			if(category.containsKey(graph.get(i)[1])) {
				category.put(graph.get(i)[1], category.get(graph.get(i)[1])+ convertToNumber(graph.get(i)[2]));
			}
		}
		
		for (String key : category.keySet()) {
			System.out.println(key +"   "+category.get(key));
		}
		System.out.println("---------------------------");
		s.close();
	}

	private static void bubbleSort(Double[] number, String[] company) {
		int n = number.length;
		for (int i = 0; i < n - 1; i++)
			for (int j = 0; j < n - i - 1; j++) {
				double a = number[j];
				double b = number[j + 1];
				if (a > b) {
					double temp = a;
					number[j] = b;
					number[j + 1] = temp;
					String temp2 = company[j];
					company[j] = company[j+1];
					company[j+1] = temp2;
				}
			}
	}

	public static String checkBalance(double init, double last) {
		return last < init ? "Profit" : "Loss";
	}

	private static double convertToNumber(String number) {
		if (number.contains("(") && number.contains(")")) {
			String temp = number.replace("(", "");
			temp = temp.replace(")", "");
			temp = temp.replace("$", "");
			return -1 * Double.parseDouble(temp);

		} else if (number.isEmpty()) {
			return 0;

		} else {
			String temp = number.replace("$", "");
			return Double.parseDouble(temp);

		}
	}
}
