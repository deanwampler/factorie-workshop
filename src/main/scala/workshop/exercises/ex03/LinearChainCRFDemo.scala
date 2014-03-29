// Adapted from ExampleLinearChainCRF in 
// http://factorie.cs.umass.edu/tutorials/UsersGuide01Introduction.scala.html

object LinearChainCRFDemo extends App {

  import cc.factorie.Factorie._   // The base library: variables, factors
  import cc.factorie.la           // Linear algebra: tensors, dot-products, etc.
  import cc.factorie.optimize.{BatchTrainer, ConjugateGradient}   // Gradient-based optimization and training
  import cc.factorie.infer.{BP, InferByBPLoopy}     // Belief Propagation
  import scala.language.reflectiveCalls

  // Declare random variable types
  // A domain and variable type for storing words
  object TokenDomain extends CategoricalDomain[String]
  class Token(str:String) extends CategoricalVariable(str) { def domain = TokenDomain }
  // A domain and variable type for storing part-of-speech tags
  object LabelDomain extends CategoricalDomain[String]
  class Label(str:String, val token:Token) extends LabeledCategoricalVariable(str) { def domain = LabelDomain }
  class LabelSeq extends scala.collection.mutable.ArrayBuffer[Label]
  // Create random variable instances from data
  val data = List("See/V Spot/N run/V", "Spot/N is/V a/DET big/J dog/N", "He/N is/V fast/J") // Just a toy amount of data for this example
  val labelSequences = for (sentence <- data) yield new LabelSeq ++= sentence.split(" ").map(s => { val a = s.split("/"); new Label(a(1), new Token(a(0)))})
  // Define a model structure
  val model = new Model with Parameters {
    // Two families of factors, where factor scores are dot-products of sufficient statistics and weights.  (The weights will set in training below.)
    val markov = new DotFamilyWithStatistics2[Label,Label] { val weights = Weights(new la.DenseTensor2(LabelDomain.size, LabelDomain.size)) }
    val observ = new DotFamilyWithStatistics2[Label,Token] { val weights = Weights(new la.DenseTensor2(LabelDomain.size, TokenDomain.size)) }
    // Given some variables, return the collection of factors that neighbor them.
    def factors(labels:Iterable[Var]) = labels match {
      case labels:LabelSeq => labels.map(label => new observ.Factor(label, label.token)) ++ labels.sliding(2).map(window => new markov.Factor(window.head, window.last))
    }
  }
  // Learn parameters
  val trainer = new BatchTrainer(model.parameters, new ConjugateGradient)
  // was InferByBPChainSum
  trainer.trainFromExamples(labelSequences.map(labels => new LikelihoodExample(labels, model, InferByBPLoopy)))
  // Inference on the same data.  We could let FACTORIE choose the inference method, 
  // but here instead we specify that is should use max-product belief propagation specialized to a linear chain
  labelSequences.foreach(labels => BP.inferChainMax(labels, model))
  // Print the learned parameters on the Markov factors.
  println(model.markov.weights)
  // Print the inferred tags
  labelSequences.foreach(_.foreach(l => println("Token: " + l.token.value + " Label: " + l.value)))
}