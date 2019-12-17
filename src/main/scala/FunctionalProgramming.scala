
object FunctionalProgramming extends App {

  def customFunctions(): Unit = {
    // No inputs or outputs (two versions).
    def hello1(): Unit = print("Hello!")

    def hello2 = print("Hello again!")

    // Math operation: one input and one output.
    def times2(x: Int): Int = 2 * x

    // Inputs can have default values, and explicitly specifying the return type is optional.
    // Note that we recommend specifying the return types to avoid surprises/bugs.
    def timesN(x: Int, n: Int = 2) = n * x

    // Call the functions listed above.
    hello1()
    hello2
    times2(4)
    timesN(4) // no need to specify n to use the default value
    timesN(4, 3) // argument order is the same as the order where the function was defined
    timesN(n = 7, x = 2) // arguments may be reordered and assigned to explicitly
  }

  def functionObjects(): Unit = {
    // These are normal functions.
    def plus1funct(x: Int): Int = x + 1
    def times2funct(x: Int): Int = x * 2

    // These are functions as vals.
    // The first one explicitly specifies the return type.
    val plus1val: Int => Int = x => x + 1
    val times2val = (x: Int) => x * 2

    // Calling both looks the same.
    plus1funct(4)
    plus1val(4)
    plus1funct(x=4)
    //plus1val(x=4) // this doesn't work
  }

  def runAll(): Unit = {

  }
}
