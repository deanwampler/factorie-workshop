// Adapted from FACTORIE example in cc.factorie.tutorial.BookInfoGain.

package workshop.exercises.ex04
import scala.io.Source
import collection.mutable.ArrayBuffer
import java.io.File
import cc.factorie._
import app.classify
import cc.factorie.variable.{LabeledCategoricalVariable, BinaryFeatureVectorVariable, CategoricalVectorDomain, CategoricalDomain}
import cc.factorie.app.classify.backend.OnlineLinearMulticlassTrainer
import cc.factorie.app.strings.Stopwords
import cc.factorie.util.TopN

/** 
 * Analyze text to compute the _information gain_ (Kullback–Leibler divergence).
 * In sbt, run it like this:
 *   run-main workshop.exercises.ex04.BookInfoGain \ 
 *     data/enron_spam_ham/spam100.txt data/enron_spam_ham/ham100.txt
 * or
 *   run-main workshop.exercises.ex04.BookInfoGain data/prog_scala/
 */
object BookInfoGain {
  
  // Categories: 1 for each word.
  object DocumentDomain extends CategoricalVectorDomain[String]
  
  class Document(labelString: String, words:Seq[String]) 
      extends BinaryFeatureVectorVariable[String](words) {
    def domain = DocumentDomain
    var label = new Label(labelString, this)
  }

  object LabelDomain extends CategoricalDomain[String]

  case class Label(name: String, document: Document) extends LabeledCategoricalVariable(name) {
    def domain = LabelDomain
  }

  var useBoostedClassifier = false

  val help = """
    |BookInfoGain
    |usage: scala -cp ... workshop.exercises.ex04.BookInfoGain path1 ...
    |where:
    |  pathN      Directories or files with text to analyze. Need two or more files.
    |""".stripMargin

  def main(args: Array[String]): Unit = {
    implicit val random = new scala.util.Random(0)
    if (args.length == 0) {
      println("You must specify at least one directory path or two files for classification.")
      println(help)
      System.exit(1)
    }

    // Read data and create Variables
    var docLabels = (args foldLeft (new ArrayBuffer[Label])) {
      (labels, arg) => labels ++ computeDocLabels(new File(arg))
    } 

    // A function "value" that converts a label to a document
    val labelToDocument = (l: Label) => l.document

    def topN(label: String, tops: TopN[String]): Unit = {
      println(s"$label (top ${tops.size}):")
      tops foreach (s => println("  "+s))
      println()
    }

    // Calculate the information gain between all features (words) and their labels.
    println("==== Info Gains:")
    val infogains = new classify.InfoGain(docLabels, labelToDocument)
    topN("Info Gains", infogains.top(20))
    
    // Calculate the information gain between the binary variable 
    // "in class" / "not in class" and the binary variable 
    // "has feature" / "does not have feature" for every
    // (label,feature) combination. 
    println("==== Per-label Info Gain:")
    val plig = new classify.PerLabelInfoGain(docLabels, labelToDocument)
    for (label <- LabelDomain) topN(label.category, plig.top(label, 20))
    
    // Calculate the weighted log-odds ratio:
    //   p(w|c) * log(p(w|c)/p(w|!c)) for each word w and label c.
    // Ranks highly those words that substantially contribute to positively
    // predicting label c.  
    println("==== Per-label Log Odds:")
    val pllo = new classify.PerLabelLogOdds(docLabels, (l: Label) => l.document)
    for (label <- LabelDomain) topN(label.category, pllo.top(label, 20))

    // Make a test/train split
    val (testSet, trainSet) = docLabels.shuffle.split(0.5)
    val trainLabels = new collection.mutable.ArrayBuffer[Label] ++= trainSet
    val testLabels  = new collection.mutable.ArrayBuffer[Label] ++= testSet

    println("Training online classifier, with 'chunks' of the training examples...")
    val trainer = new OnlineLinearMulticlassTrainer()
    val classifier = trainer.train(trainLabels, trainLabels.map(_.document))

    println()
    val dom = trainLabels.head.domain
    val testTrial  = new classify.Trial[Label,Document#Value](classifier, dom, _.document.value)
    val trainTrial = new classify.Trial[Label,Document#Value](classifier, dom, _.document.value)
    testTrial  ++= testLabels
    trainTrial ++= trainLabels

    println(s"Label ${trainLabels.head}, Domain $dom:")
    println(s"Train accuracy = ${trainTrial.accuracy} (# labels = ${trainLabels.size})")
    println(s"Test  accuracy = ${testTrial.accuracy} (# labels = ${testLabels.size})")
  }


  def computeDocLabels(file: File): ArrayBuffer[Label] = {
    if (!file.exists) {
      throw new IllegalArgumentException(file.getPath + " does not exist!")
    } else if (file.isDirectory) {
      (file.listFiles foldLeft (new ArrayBuffer[Label])) {
        (labels, child) => labels ++ computeDocLabels(child)
      }
    } else {
      computeDocLabelsForFile(file)
    }
  }
    
  def  computeDocLabelsForFile(file: File): ArrayBuffer[Label] = {
    println("===== Reading " + file.getPath)
    try {
      val tokenizeRE = "\\w+".r
      // When used to analyze SPAM and maybe HAM, must use this encoding.
      val lineIterator = Source.fromFile(file, "ISO-8859-1").getLines
      (lineIterator foldLeft ArrayBuffer.empty[Label]) { (labels, line) =>
        tokenizeRE.findAllIn(line).toSeq.grouped(500).foreach { words => 
        labels += new Document(
          file.getName, words.filter(!Stopwords.contains(_))).label
        }
        labels
      }
    } catch {
      case mie: java.nio.charset.MalformedInputException =>
        println(s"$mie thrown for $file. Skipping...")
        ArrayBuffer.empty  
    }
  }
}