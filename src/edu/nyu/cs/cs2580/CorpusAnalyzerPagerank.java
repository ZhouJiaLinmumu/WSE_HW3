package edu.nyu.cs.cs2580;

import java.io.File;
import java.io.IOException;
import java.util.Vector;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import edu.nyu.cs.cs2580.SearchEngine.Options;

/**
 * @CS2580: Implement this class for HW3.
 */
public class CorpusAnalyzerPagerank extends CorpusAnalyzer {

  String _docInfoFile = "docinfo.inf";	
  
  final String _docInfoDelim = ";";
	
  public CorpusAnalyzerPagerank() {
	  try {
			Options options = new Options("conf/engine.conf");
			CorpusAnalyzerPagerank capr = new CorpusAnalyzerPagerank(options);
			long start = System.currentTimeMillis();
			capr.createDocInfo();
			long end = System.currentTimeMillis();
			System.out.println("time = " + (end - start));
			
		} catch (IOException e) { // TODO Auto-generated
			e.printStackTrace();
		}
  }
  public CorpusAnalyzerPagerank(Options options) {
    super(options);
    
    _docInfoFile = _options._indexPrefix + "/" + _docInfoFile;
  }
  
  public static void main(String args[]) {
	  new CorpusAnalyzerPagerank();
  }

  /**
   * This function processes the corpus as specified inside {@link _options}
   * and extracts the "internal" graph structure from the pages inside the
   * corpus. Internal means we only store links between two pages that are both
   * inside the corpus.
   * 
   * Note that you will not be implementing a real crawler. Instead, the corpus
   * you are processing can be simply read from the disk. All you need to do is
   * reading the files one by one, parsing them, extracting the links for them,
   * and computing the graph composed of all and only links that connect two
   * pages that are both in the corpus.
   * 
   * Note that you will need to design the data structure for storing the
   * resulting graph, which will be used by the {@link compute} function. Since
   * the graph may be large, it may be necessary to store partial graphs to
   * disk before producing the final graph.
   *
   * @throws IOException
   */
  @Override
  public void prepare() throws IOException {
    System.out.println("Preparing " + this.getClass().getName());
    return;
  }

  /**
   * This function computes the PageRank based on the internal graph generated
   * by the {@link prepare} function, and stores the PageRank to be used for
   * ranking.
   * 
   * Note that you will have to store the computed PageRank with each document
   * the same way you do the indexing for HW2. I.e., the PageRank information
   * becomes part of the index and can be used for ranking in serve mode. Thus,
   * you should store the whatever is needed inside the same directory as
   * specified by _indexPrefix inside {@link _options}.
   *
   * @throws IOException
   */
  @Override
  public void compute() throws IOException {
    System.out.println("Computing using " + this.getClass().getName());
    return;
  }

  /**
   * During indexing mode, this function loads the PageRank values computed
   * during mining mode to be used by the indexer.
   *
   * @throws IOException
   */
  @Override
  public Object load() throws IOException {
    System.out.println("Loading using " + this.getClass().getName());
    return null;
  }

  
  
    /**
     * This function will create Doc Info File containing Document Info in following format: as well as create a HashSet of 
     * Doc ID; Doc URI; Doc Title; Term Count
     * Also, 
     * @return a set of all Doc URI's(i.e; doc names) present in corpus
     * @throws IOException
     */
  public void createDocInfo() throws IOException{
	  	String corpusDirPath = _options._corpusPrefix;		
		System.out.println("Constructing index from: " + corpusDirPath);
		StringBuffer docInfo;
		int docId = 0;
		File corpusDir = new File(corpusDirPath);		
		for (File corpusFile : corpusDir.listFiles()) {
			docInfo = new StringBuffer();
			System.out.println("Processing Doc ID = "+docId);
			Document doc = Jsoup.parse(corpusFile, "UTF-8");
			String contents = doc.text();
			
			if (contents == null || doc == null) {
				System.out.println("null");
				return;
			}
			
			Vector<String> terms = Utilities.getStemmed(contents);
			String uri = doc.baseUri();
			uri = uri==null ? "" : uri;
			uri = new File(uri).getName();
			
			String title = doc.title().trim();
			title = title.length()==0 ? uri : title;
			
			int wordsInDoc = terms.size();
			
			docInfo.append(docId);
			docInfo.append(_docInfoDelim);		
			docInfo.append(uri);
			docInfo.append(_docInfoDelim);
			docInfo.append(title);
			docInfo.append(_docInfoDelim);
			docInfo.append(wordsInDoc); // total words in the document
			docInfo.append("\n");
			
			Utilities.writeToFile(_docInfoFile, docInfo.toString(), true);
			docId++;

		}
	 }
}
