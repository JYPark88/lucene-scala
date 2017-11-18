import java.io.File
import java.util.Date

import org.apache.lucene.document.{Document, Field, StringField, TextField}
import org.apache.lucene.index.{IndexWriter, IndexWriterConfig}
import org.apache.lucene.store.FSDirectory

/**
  * user : Nolan (박재영) 
  * email : jypark88@coupang.com
  * date : 2017. 11. 18.
  * time : PM 6:21
  */
object Indexer extends App {
//  val directory = new RAMDirectory
  val directory = FSDirectory.open(new File(s"./index/index-${new Date().getTime}").toPath)
  val config = new IndexWriterConfig()

  val writer = new IndexWriter(directory, config)

  def addDoc(w: IndexWriter, title: String, isbn: String) = {
    val doc = new Document
    doc.add(new TextField("title", title, Field.Store.YES))
    doc.add(new StringField("isbn", isbn, Field.Store.YES))
    w.addDocument(doc)
  }

  addDoc(writer, "Lucene in Action", "193398817")
  addDoc(writer, "Lucene for Dummies", "55320055Z")
  addDoc(writer, "Managing Gigabytes", "55063554A")
  addDoc(writer, "The Art of Computer Science", "9900333X")
  addDoc(writer, "luCene fantastic", "1111777777A")

  println(writer.numDocs + " document(s) indexed.")

  writer.close

}
