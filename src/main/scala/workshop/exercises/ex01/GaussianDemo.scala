// Adapted from the FACTORIE tutorial examples:
//   src/main/scala/cc/factorie/tutorial/GaussianDemo.scala
// Elements are described in the User's Guide:
//   http://factorie.cs.umass.edu/tutorials/UsersGuide01Introduction.scala.html

package workshop.exercises.ex01

import cc.factorie._
import cc.factorie.directed._
import la.{DenseTensor2, Tensor2, DenseTensor1, Tensor1}
import cc.factorie.directed.{MaximizeMultivariateGaussianCovariance, MaximizeMultivariateGaussianMean, MultivariateGaussian, Gaussian}
import cc.factorie.variable.{TensorVariable, DoubleVariable}
import cc.factorie.infer.Maximize

/**
 * Univariate Gaussian.
 * Creates a model for holding factors that connect random variables
 * for holding mean and variance with 1000 samples from a Gaussian. 
 * Then it re-estimates by maximum likelihood the mean and variance 
 * from the sampled data.
 */
object GaussianDemo {
  def main(args: Array[String]): Unit = {
    println("UnivariateGaussian Demo:")

    // A Model is a collection of factors. A DirectedModel has directed edges.
    implicit val model = DirectedModel()
    implicit val random = new scala.util.Random(0)

    // FACTORIE's fancy version of mutable double variable.
    val mean = new DoubleVariable(10)
    val variance = new DoubleVariable(1.0)

    val origMean = mean.value
    val origVariance = variance.value
    println(s"Original mean:     $origMean")
    println(s"Original variance: $origVariance\n")

    // Create 1000 directed factors with values given by sampling the Gaussian
    // distribution. 
    val data = for {
      i <- 1 to 1000
    } yield new DoubleVariable :~ Gaussian(mean, variance)
    // The last expression:
    //   new DoubleVariable :~ Gaussian(mean, variance) 
    // is actually this:
    //   new DoubleVariable.:~(Gaussian(mean, variance))(model, random)

    println("10 samples:")
    data.take(10).foreach(println(_))
    println()

    // Could use these. Give the same results:
    // Maximize(mean)
    // Maximize(variance)
    MaximizeGaussianMean(mean, model)
    MaximizeGaussianVariance(variance, model)
    check("Estimates using MaximizeGaussianMean(mean, model) and MaximizeGaussianVariance(variance, model)",
      mean, variance, origMean, origVariance)
  }

  def check(msg: String, 
      mean: DoubleVariable, variance: DoubleVariable, 
      origMean: Double, origVariance: Double) = {
    println(msg)
    println(s"Estimated mean:     ${mean.value}")
    println(s"Estimated variance: ${variance.value}")
    assert(math.abs((mean.value / origMean) - 1.0) < .05, "Mean estimate failed")
    assert(math.abs((variance.value / origVariance) - 1.0) < .05, "Variance estimate failed")
  }
}

/**
 * Multivariate Gaussian.
 * A two-dimensional model.
 */
object MultivariateGaussianDemo {
  def main(args:Array[String]): Unit = {
    val dimensions = if (args.length > 0) args(0).toInt else 3
    println(s"Multivariate Gaussian Demo: $dimensions dimensions")

    implicit val model = DirectedModel()
    implicit val random = new scala.util.Random(0)
    val mean = new TensorVariable[Tensor1](new DenseTensor1(dimensions, 0.0))
    val variance = new TensorVariable[Tensor2](
      new DenseTensor2(Array.tabulate(dimensions, dimensions) {
        (i, j) => if (i == j) 1.0 else 0.0
      }
    ))
    println("Original mean="+mean.value)
    println("Original variance="+variance.value)


    val data = for {
      i <- 1 to 1000
    } yield new TensorVariable[Tensor1] :~ MultivariateGaussian(mean, variance)
    // Similar to before, 
    //  new TensorVariable[Tensor1] :~ MultivariateGaussian(mean, variance)
    // is actually
    //  new TensorVariable[Tensor1].:~(MultivariateGaussian(mean, variance))(model, random)

    println("10 samples:")
    data.take(10).foreach(println(_))
    println()

    MaximizeMultivariateGaussianMean(mean, model)
    MaximizeMultivariateGaussianCovariance(variance, model)
    println("MaximizeMultivariateGaussianMean and MaximizeMultivariateGaussianCovariance:")
    println(s"Estimated mean:     ${mean.value}")
    println(s"Estimated variance: ${variance.value}")
  }
}