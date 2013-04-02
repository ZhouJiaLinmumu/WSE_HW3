<<<<<<< HEAD
package edu.nyu.cs.cs2580;

/**
 * Grading criteria.
 * 
 * Grading will be done via the public APIs for the main classes:
 *   Indexer, Ranker, Document, Query, CorpusAnalyzer, LogMiner
 * Do NOT change the public APIs for those classes.
 * 
 * In HW3, we will examine your implementation through the following tasks.
 *
 * NOTE: In addition to the normal score-based ranking of the results, you must
 * return: 1) PageRank for each result; 2) NumViews for each result.
 *
 *  a) PageRank verification: we will issue a set of queries to retrieve results
 *     using your search engine and examine the PageRanks of the results
 *     returned. 25 points.
 *
 *  b) NumViews verification: we will issue a set of queries to retrieve results
 *     using your search engine and examine the NumViews of the results
 *     returned. 10 points.
 *
 *  c) See writeup for grading on comparing PageRank and NumViews (15 points)
 *     and pseudo-relevance feedback (50 points).
 *
 * @author congyu
 */
public class Grader {
  Indexer _indexer;
  Ranker _ranker;

  public Grader() { }

  public void setIndexer(Indexer indexer) {
    _indexer = indexer;
  }

  public void setRanker(Ranker ranker) {
    _ranker = ranker;
  }

  public static void main(String[] args) {
  }
}
=======
package edu.nyu.cs.cs2580;

/**
 * Grading criteria.
 * 
 * Grading will be done via the public APIs for the four main classes:
 *   Indexer, Ranker, Document, Query
 * Do NOT change the public APIs for those classes.
 * 
 * In HW2, we will examine your index implementation through the following
 * tasks. Note that scanning the full set of documents during serving will get
 * ZERO credit.
 *
 *  1) Proper index construction and loading (5 + 5 + 10 points)
 *     Run index construction to construct the index. Then perform the index
 *     loading, and check that the documents and terms are properly loaded.
 *     5 points each for doconly and occurrence indices, 10 points for
 *     compressed index.
 *
 *  2) Conjunctive retrieval for doconly inverted index (20 points)
 *     Given a query, such as [new york], documents containing all terms in the
 *     query are returned.  We will use a hidden set of test queries.
 *
 *  3) Phrase+Conjunctive retrieval for occurrence inverted index (20 points)
 *     Given a query, such as ["new york city" film], documents containing all
 *     the phrases and terms are returned.  We will use a hidden set of test
 *     queries.
 *
 *  4) Phrase+Conjunctive retrieval for compressed inverted index (20 points)
 *     Same as above. We will use a hidden set of test queries. Full credit for
 *     this task will depend on the size of the index you are constructing.
 *
 *  5) Relevance based retrieval with your favorite Ranker and compressed
 *     inverted index (20 points)
 *     We will use a hidden set of test queries to roughly examine the retrieval
 *     relevance of your whole system.
 *
 *  6) Bonus: efficient implementation of {@link Indexer.documentTermFrequency}
 *     within {@link IndexerInvertedCompressed}. (10 points)
 *
 * @author congyu
 */
public class Grader {
  Indexer _indexer;
  Ranker _ranker;

  public Grader() { }

  public void setIndexer(Indexer indexer) {
    _indexer = indexer;
  }

  public void setRanker(Ranker ranker) {
    _ranker = ranker;
  }

  public static void main(String[] args) {
  }
}
>>>>>>> b1134a55b26b55bfe6c1f90f6cf1982a0c832601
