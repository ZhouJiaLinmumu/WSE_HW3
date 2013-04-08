package edu.nyu.cs.cs2580;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Spearman {

	public static void main(String[] args) {

		// Check for invalid invocation of program
		if (args.length != 2) {
			System.out.println("Error in parameters...");
			System.out
					.println("Usage: java edu.nyu.cs.cs2580.Spearman <PATH-TO-PAGERANKS> <PATH-TO-NUMVIEWS>");
		} else {
			File pageRankFile = new File(args[0]);
			if (!pageRankFile.exists()) {
				System.out.println("Error: File " + args[0]
						+ " does not exist.");
				System.exit(0);
			}

			File numViewsFile = new File(args[0]);
			if (!numViewsFile.exists()) {
				System.out.println("Error: File " + args[0]
						+ " does not exist.");
				System.exit(0);
			}

			double spearmanCoeff = computeSpearmanCoeff(pageRankFile,
					numViewsFile);
		}
	}

	private static double computeSpearmanCoeff(File pageRankFile,
			File numViewsFile) {

		List<Pair<Integer, Double>> pageRankInfo = new ArrayList<Pair<Integer, Double>>();
		List<Pair<Integer, Integer>> numViewsInfo = new ArrayList<Pair<Integer, Integer>>();

		readPageRankInfo(pageRankFile, pageRankInfo, "\t");
		readNumViewsInfo(numViewsFile, numViewsInfo, "\t");
		
		if(pageRankInfo.size() != numViewsInfo.size()) {
			System.out.println("Error: <PATH-TO-PAGERANKS> and <PATH-TO-NUMVIEWS> must contain same number of information.");
			System.exit(0);
		}
		
		

		return 0;
	}

	private static void readNumViewsInfo(File numViewsFile,
			List<Pair<Integer, Integer>> numViewsInfo, String delim) {
		/*
		 * Initialize the data structure to store info, if not already 
		 * initialized
		 */
		if (numViewsInfo == null) {
			numViewsInfo = new ArrayList<Pair<Integer, Integer>>();
		} else {
			numViewsInfo.clear();
		}

		try {
			BufferedReader br = new BufferedReader(new FileReader(numViewsFile));
			String line = "";
			String[] docPagerankInfo;
			while ((line = br.readLine()) != null) {
				docPagerankInfo = line.split(delim);
				numViewsInfo.add(new Pair<Integer, Integer>(Integer
						.parseInt(docPagerankInfo[0]), Integer
						.parseInt(docPagerankInfo[1])));
			}
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void readPageRankInfo(File pageRankFile,
			List<Pair<Integer, Double>> pageRankInfo, String delim) {
		/*
		 * Initialize the data structure to store info, if not already 
		 * initialized
		 */
		if (pageRankInfo == null) {
			pageRankInfo = new ArrayList<Pair<Integer, Double>>();
		} else {
			pageRankInfo.clear();
		}

		try {
			BufferedReader br = new BufferedReader(new FileReader(pageRankFile));
			String line = "";
			String[] docPagerankInfo;
			while ((line = br.readLine()) != null) {
				docPagerankInfo = line.split(delim);
				pageRankInfo.add(new Pair<Integer, Double>(Integer
						.parseInt(docPagerankInfo[0]), Double
						.parseDouble(docPagerankInfo[1])));
			}
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
}
