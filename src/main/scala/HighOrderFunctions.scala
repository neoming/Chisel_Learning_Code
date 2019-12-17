
import chisel3._
import chisel3.util._
import chisel3.iotesters.{ChiselFlatSpec, Driver, PeekPokeTester}

object HighOrderFunctions extends App {
  def runMapExample(): Unit = {
    println(List(1, 2, 3, 4).map(x => x + 1)) // explicit argument list in function
    println(List(1, 2, 3, 4).map(_ + 1)) // equivalent to the above, but implicit arguments
    println(List(1, 2, 3, 4).map(_.toString + "a")) // the output element type can be different from the input element type

    println(List((1, 5), (2, 6), (3, 7), (4, 8)).map { case (x, y) => x * y }) // this unpacks a tuple, note use of curly braces

    // Related: Scala has a syntax for constructing lists of sequential numbers
    println(0 to 10) // to is inclusive , the end point is part of the result
    println(0 until 10) // until is exclusive at the end, the end point is not part of the result

    // Those largely behave like lists, and can be useful for generating indices:
    val myList = List("a", "b", "c", "d")
    println((0 until 4).map(myList(_)))
  }

  def runMapExercise(): Unit = {
    // Now you try:
    // Fill in the blanks (the ???) such that this doubles the elements of the input list.
    // This should return: List(2, 4, 6, 8)
    println(List(1, 2, 3, 4).map(x => x * 2))
  }

  def runZipWithIndexExample(): Unit = {
    print(s"${List(1, 2, 3, 4).zipWithIndex}\n")
    print(s"${List("a", "b", "c", "d").zipWithIndex}\n")
    print(s"${List(("a", "b"), ("c", "d"), ("e", "f"), ("g", "h")).zipWithIndex}\n")
  }

  def runReduceExample(): Unit = {
    println(List(1, 2, 3, 4).reduce((a, b) => a + b))  // returns the sum of all the elements
    println(List(1, 2, 3, 4).reduce(_ * _))  // returns the product of all the elements
    println(List(1, 2, 3, 4).map(_ + 1).reduce(_ + _))  // you can chain reduce onto the result of a map
  }

  def runFoldExample():Unit = {
    println(List(1, 2, 3, 4).fold(0)(_ + _))  // equivalent to the sum using reduce
    println(List(1, 2, 3, 4).fold(1)(_ + _))  // like above, but accumulation starts at 1
    println(List().fold(1)(_ + _))  // unlike reduce, does not fail on an empty input
  }

  def runFoldExercise():Unit = {
    // Now you try:
    // Fill in the blanks (the ???) such that this returns the double the product of the elements of the input list.
    // This should return: 2*(1*2*3*4) = 48
    // Note: unless empty list tolerance is needed, reduce is a much better fit here.
    println(List(1, 2, 3, 4).fold(2)(_*_))
  }

  class MyRoutingArbiter(numChannels: Int) extends Module {
    val io = IO(new Bundle {
      val in = Vec(numChannels, Flipped(Decoupled(UInt(8.W))))
      val out = Decoupled(UInt(8.W))
    } )

    // Your code here
    io.out.valid := io.in.map(_.valid).reduce(_||_)
    val channel = PriorityMux(
      io.in.map(_.valid).zipWithIndex.map{case(valid, index ) => (valid,index.U)}
    )
    io.out.bits := io.in(channel).bits
    for ((ready, index) <- io.in.map(_.ready).zipWithIndex) {
      ready := io.out.ready && channel === index.U
    }
  }

  // verify that the computation is correct
  class MyRoutingArbiterTester(c: MyRoutingArbiter) extends PeekPokeTester(c) {
    // Set input defaults
    for(i <- 0 until 4) {
      poke(c.io.in(i).valid, 0.U)
      poke(c.io.in(i).bits, i.U)
      poke(c.io.out.ready, 1.U)
    }

    expect(c.io.out.valid, 0)

    // Check single input valid behavior with backpressure
    for (i <- 0 until 4) {
      poke(c.io.in(i).valid, 1.U)
      expect(c.io.out.valid, 1)
      expect(c.io.out.bits, i)

      poke(c.io.out.ready, 0.U)
      expect(c.io.in(i).ready, 0)

      poke(c.io.out.ready, 1.U)
      poke(c.io.in(i).valid, 0.U)
    }

    // Basic check of multiple input ready behavior with backpressure
    poke(c.io.in(1).valid, 1.U)
    poke(c.io.in(2).valid, 1.U)
    expect(c.io.out.bits, 1)
    expect(c.io.in(1).ready, 1)
    expect(c.io.in(0).ready, 0)

    poke(c.io.out.ready, 0.U)
    expect(c.io.in(1).ready, 0)
  }

  def runDecoupledArbiter():Unit= {
    val works = Driver(() => new MyRoutingArbiter(4)) {
      c => new MyRoutingArbiterTester(c)
    }
    assert(works)        // Scala Code: if works == false, will throw an error
    println("SUCCESS!!") // Scala Code: if we get here, our tests passed!
  }

  def runAll():Unit = {
    runMapExample()
    runMapExercise()
    runZipWithIndexExample()
    runReduceExample()
    runFoldExample()
    runFoldExercise()
    runDecoupledArbiter()
  }

  runDecoupledArbiter()
}
