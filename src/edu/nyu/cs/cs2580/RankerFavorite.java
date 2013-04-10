package edu.nyu.cs.cs2580;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Vector;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import edu.nyu.cs.cs2580.QueryHandler.CgiArguments;
import edu.nyu.cs.cs2580.SearchEngine.Options;

/**
 * @CS2580: Implement this class for HW2 based on a refactoring of your favorite
 *          Ranker (except RankerPhrase) from HW1. The new Ranker should no
 *          longer rely on the instructors' {@link IndexerFullScan}, instead it
 *          should use one of your more efficient implementations.
 * 
 *          This class implements QL Ranker
 */
public class RankerFavorite extends Ranker {

	private double lambda = 0.5;

	public RankerFavorite(Options options, CgiArguments arguments,
			Indexer indexer) {
		super(options, arguments, indexer);
		System.out.println("Using Ranker: " + this.getClass().getSimpleName());
	}

	public void setLambda(double lambda) {
		this.lambda = lambda;
	}

	@Override
	public Vector<ScoredDocument> runQuery(Query query, int numResults) {
		System.out.println("in numResults");
		PriorityQueue<ScoredDocument> retrieval_results = new PriorityQueue<ScoredDocument>();
		// query.processQuery();

		System.out.println("getting results");
		DocumentIndexed di = (DocumentIndexed) _indexer.nextDoc(query, -1);
		System.out.println("got 1st result");
		while (di != null) {
			System.out.println("in while");
			retrieval_results.add(scoreDocument(query, di));
			di = (DocumentIndexed) _indexer.nextDoc(query, di._docid);
		}
		
		System.out.println("num of results = " + retrieval_results.size());

		// return only top numResults elements
		Vector<ScoredDocument> sortedResults = new Vector<ScoredDocument>();
		for (int i = 0; i < numResults && retrieval_results.peek() != null; i++) {
			sortedResults.add(retrieval_results.poll());
		}

		return sortedResults;
	}	

	public ScoredDocument scoreDocument(Query query, DocumentIndexed doc) {
		// TODO: adjust term frequency and total word count for doc due to
		// phrases

		double score = getLMPScore(query, doc);

		// TODO: restore original term frequencies and total word count

		return new ScoredDocument(doc, score);
	}

	public double getLMPScore(Query query, Document d) {
		return runquery(query, d._docid).get_score();
	}

	public double getQueryLikelihood(String term, int docid) {

		DocumentIndexed dIndexed;
		int termFreqInDoc = 0;
		long totalWordsInDoc = 0;

		dIndexed = (DocumentIndexed) _indexer.getDoc(docid);

		// TODO: termFreqInDoc and totalWordsInDoc should change because of
		// presence of phrases
		termFreqInDoc = _indexer.documentTermFrequency(term, dIndexed.getUrl());
		totalWordsInDoc = dIndexed.getTotalWords();

		// System.out.println("docid = " + docid);
		// System.out.println("term freq for - " + term + " = " +
		// termFreqInDoc);
		// System.out.println("total words in doc = " + totalWordsInDoc);

		double ql = 0d;

		if (totalWordsInDoc > 0) {
			ql = termFreqInDoc * 1.0d / totalWordsInDoc;
		}
		// return (getFrequid(term, did) /
		// getTotalNumberOfWordsInaDocument(did));

		return ql;
	}

	public ScoredDocument runquery(Query query, int docid) {

		// query.processQuery();

		// Build query vector
		// Vector<String> qv = Utilities.getStemmed(query._query);
		Vector<String> qv = new Vector<String>();
		for (String term : query._tokens) {
			qv.add(term);
		}

		DocumentIndexed dIndexed = (DocumentIndexed) _indexer.getDoc(docid);

		// System.out.println("tot = " + _indexer.totalTermFrequency());
		double score = 0.0;
		for (int i = 0; i < qv.size(); ++i) {
			score += Math.log((1 - lambda)
					* (getQueryLikelihood(qv.get(i), docid))
					+ (lambda)
					* (_indexer.corpusTermFrequency(qv.get(i)) / _indexer
							.totalTermFrequency()));
		}

		// antilog
		// score = Math.pow(Math.E, score);

		return new ScoredDocument(dIndexed, score);
	}
}