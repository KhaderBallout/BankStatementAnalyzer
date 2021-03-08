import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

public class BankStatementAnalyzer {

	public static void main(String[] args) throws FileNotFoundException {

		String fileName = "C:/Users/Ramiz/OneDrive/Bethlehem University/current/SWER342 - Majed/Assignments/Assignment 1 - Maintainability/BankStatementAnalyzer/src/main/resources/bankstatement.csv";
		File file = new File(fileName);
		Scanner s = new Scanner(file);
		ArrayList<String[]> graph = new ArrayList<String[]>();
		while (s.hasNext()) {
			String data = s.nextLine();
			String[] values = data.split(",");
			graph.add(values);
		}
		s.close();
		String[] last = graph.get(graph.size() - 1);
		double last2 = convertToNumber(last[last.length - 1]);
		String[] init = graph.get(1);
		double init2 = convertToNumber(init[init.length - 1]);
		String isProfit = checkBalance(init2, last2);

		System.out.println("---------------------------");
		System.out.println(isProfit);
		System.out.println("---------------------------");
		ArrayList<String[]> transactions = new ArrayList<String[]>();

		for (int i = graph.size() - 1; i > 1; i--) {
			String[] temp = new String[2];
			temp[0] = graph.get(i)[1];
			String temp2 = graph.get(i)[2];
			temp[1] = temp2 == "" ? "$0.00" : temp2;
			transactions.add(temp);
		}

		System.out.println("Number of back transations: " + transactions.size());
		System.out.println("---------------------------");
		Collections.sort(transactions, new Comparator<String[]>() {

			public int compare(String[] o1, String[] o2) {
				Double sa = convertToNumber(o1[1]);
				Double sb = convertToNumber(o2[1]);

				int v = sa.compareTo(sb);

				return v;

				// it can also return 0, and 1
			}
		});
		topThreeTransactions(transactions);
		System.out.println("---------------------------");
		HashMap<String, Double> map = new HashMap<String, Double>();
		for (String[] arr : transactions) {
			if (map.containsKey(arr[0])) {
				map.put(arr[0], map.get(arr[0]) + convertToNumber(arr[1]));
			} else {
				map.put(arr[0], convertToNumber(arr[1]));
			}
		}
		System.out.println("The category that is spent on the most is: [" + getKey(map, Collections.max(map.values())) + "] at a value of: [$" + Collections.max(map.values()) +"]"); 
		System.out.println("---------------------------");
	}

	public static <K, V> K getKey(Map<K, V> map, V value) {
		for (Map.Entry<K, V> entry : map.entrySet()) {
			if (value.equals(entry.getValue())) {
				return entry.getKey();
			}
		}
		return null;
	}

	private static void topThreeTransactions(ArrayList<String[]> transactions) {
		String[] top1 = transactions.get(transactions.size() - 1);
		String[] top2 = transactions.get(transactions.size() - 2);
		String[] top3 = transactions.get(transactions.size() - 3);
		System.out.println("Top 1 transaction is: [" + top1[0] + "] With Value: [" + top1[1] + "]");
		System.out.println("Top 2 transaction is: [" + top2[0] + "] With Value: [" + top2[1] + "]");
		System.out.println("Top 3 transaction is: [" + top3[0] + "] With Value: [" + top3[1] + "]");
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
