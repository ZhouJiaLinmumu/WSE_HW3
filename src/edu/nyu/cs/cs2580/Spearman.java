package edu.nyu.cs.cs2580;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Spearman {
	
	/*
	 * If numviews of 2 docs is same, they are sorted according to lexicographical 
	 * order of their uri. This map is used to store that mapping and will be used 
	 * for sorting.
	 */
	Map<Integer, String> docIdUriMapping = null;

	public Spearman(String[] args) {
		// Check for invalid invocation of program
		if (args.length != 2) {
			System.out.println("Error in parameters...");
			System.out
					.println("Usage: java edu.nyu.cs.cs2580.Spearman <PATH-TO-PAGERANKS> <PATH-TO-NUMVIEWS>");
		} else {
			
			docIdUriMapping = getDocidUriMapping();
			
			File pageRankFile = new File(args[0]);
			if (!pageRankFile.exists()) {
				System.out.println("Error: File " + args[0]
						+ " does not exist.");
				System.exit(0);
			}

			File numViewsFile = new File(args[1]);
			if (!numViewsFile.exists()) {
				System.out.println("Error: File " + args[1]
						+ " does not exist.");
				System.exit(0);
			}

			String pageRankDelim = "\t";
			String numViewsDelim = "\t";

			double spearmanCoeff = computeSpearmanCoeff(pageRankFile,
					pageRankDelim, numViewsFile, numViewsDelim);

			System.out.println("spearmanCoeff = " + spearmanCoeff);
		}
	}

	public static void main(String[] args) {
		new Spearman(args);
	}

	private double computeSpearmanCoeff(File pageRankFile,
			String pageRankDelim, File numViewsFile, String numViewsDelim) {

		List<Pair<Integer, Double>> pageRankInfo = new ArrayList<Pair<Integer, Double>>();
		List<Pair<Integer, Integer>> numViewsInfo = new ArrayList<Pair<Integer, Integer>>();

		readPageRankInfo(pageRankFile, pageRankInfo, "\t");
		readNumViewsInfo(numViewsFile, numViewsInfo, "\t");
		
		if (pageRankInfo.size()==0 || numViewsInfo.size()==0) {
			return 0;
		}

		// Get rank values from pagerank info
		Map<Integer, Integer> rankValuesPageRank = new HashMap<Integer, Integer>();
		getRankValues(pageRankInfo, rankValuesPageRank);

		// Get rank values from numviews info
		Map<Integer, Integer> rankValuesNumViews = new HashMap<Integer, Integer>();
		getRankValues(numViewsInfo, rankValuesNumViews);

		return getSpearmanCoeff(rankValuesPageRank, rankValuesNumViews);
	}

	private double getSpearmanCoeff(
			Map<Integer, Integer> rankValuesPageRank,
			Map<Integer, Integer> rankValuesNumViews) {
		
		// check for null inputs
		if(rankValuesPageRank==null || rankValuesNumViews==null) {
			return 0;
		}

		double coeff = 0;		
		Integer x = 0; // Holds rank value of a document obtained through page rank
		Integer y = 0; // Holds rank value of a document obtained through numviews
		long sum = 0;
		
		int n = rankValuesPageRank.size();
		// 
		if (n == 0) {
			return 0;
		}

		// Loop through all the docids
		for (Integer docid : rankValuesPageRank.keySet()) {
			x = rankValuesPageRank.get(docid);
			y = rankValuesNumViews.get(docid);
			
			// If numviews for the docid is not available, it is assumed to be zero.
			y = y==null? 0 : y;

			sum += Math.pow(x - y, 2);
		}

		coeff = 1 - (6.0 * sum) / (n * (n * n - 1));

		return coeff;
	}

	private <U extends Number> Map<Integer, Integer> getRankValues(
			List<Pair<Integer, U>> info, Map<Integer, Integer> rankValues) {

		if (info == null || info.size() == 0 || rankValues == null) {
			return null;
		}
		
		// 
		
		System.out.println("size = " + docIdUriMapping.size());
		
		// Sort the input list
		Utilities.sort(info, docIdUriMapping, true);

		int rankIndex = 1;
		int docid;

		// Assign rank values for each docid in the input list according to
		// their page rank
		for (Pair<Integer, U> docPageRank : info) {
			docid = docPageRank.getFirstElement();
			rankValues.put(docid, rankIndex++);
		}

		return rankValues;
	}

	private Map<Integer, String> getDocidUriMapping() {

		BufferedReader br;
		String line;
		String _docInfoFile = "data/index/docinfo.inf";
		final String _docInfoDelim = ";";
		Map<Integer, String> _docIdUriMap = new HashMap<Integer, String>();
		
		// load doc info file
		System.out.println("Loading documents info from : " + _docInfoFile);

		try {
			br = new BufferedReader(new FileReader(_docInfoFile));
			
			String[] info;
			DocumentIndexed dIndexed;
			
			while ((line = br.readLine()) != null) {
				info = line.split(_docInfoDelim);
				
				int dId = Integer.parseInt(info[0]);
				dIndexed = new DocumentIndexed(dId);
				dIndexed.setUrl(info[1]);
				dIndexed.setTitle(info[2]);
				long totalWordsInDoc = Long.parseLong(info[3]);
				dIndexed.setTotalWords(totalWordsInDoc);
				_docIdUriMap.put(dId, info[1]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("Loading document info done ...");
		
		return _docIdUriMap;
	}

	private void readNumViewsInfo(File numViewsFile,
			List<Pair<Integer, Integer>> numViewsInfo, String delim) {
		
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

	private void readPageRankInfo(File pageRankFile,
			List<Pair<Integer, Double>> pageRankInfo, String delim) {

		if (pageRankInfo == null) {
			return;
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
