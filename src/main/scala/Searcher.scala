import java.io.File

import org.apache.lucene.analysis.standard.StandardAnalyzer
import org.apache.lucene.document.Document
import org.apache.lucene.index.{DirectoryReader, IndexReader}
import org.apache.lucene.queryparser.classic.QueryParser
import org.apache.lucene.search.{IndexSearcher, Query}
import org.apache.lucene.store.{FSDirectory, RAMDirectory}

/**
  * user : Nolan (박재영) 
  * email : jypark88@coupang.com
  * date : 2017. 11. 18.
  * time : PM 6:40
  */
object Searcher extends App {
  val indexRoot = new File("./index")

  val indexOption = indexRoot.listFiles(_.isDirectory)
      .map(_.getName)
      .filter(_.startsWith("index-"))
      .map(fileName => fileName.slice(6, fileName.length))
      .map(_.toLong)
      .reduceOption((first, second) => if (first > second) first else second)

  if (indexOption.isEmpty) {
    println("not indexed")
    System.exit(1)
  }

  val directory = FSDirectory.open(new File(s"./index/index-${indexOption.get}").toPath)
//  val directory: RAMDirectory = new RAMDirectory

  val reader: IndexReader = DirectoryReader.open(directory)
  val searcher: IndexSearcher = new IndexSearcher(reader)

  val analyzer = new StandardAnalyzer
  val queryString: String = "lucene dummies"

  val query: Query = new QueryParser("title", analyzer).parse(queryString)

  val results = searcher.search(query, 10)
  println(results.totalHits + " result(s).")

  results.scoreDocs
    .sortWith(_.score > _.score)
    .map(item => searcher.doc(item.doc))
    .foreach((document: Document) =>
      println(s"${document.get("isbn")}\t${document.get("title")}")
    )

  reader.close()
}
