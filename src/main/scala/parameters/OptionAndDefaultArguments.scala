
package parameters

import chisel3._
import chisel3.iotesters.PeekPokeTester

object OptionAndDefaultArguments extends App {
  def useMap(): Unit = {
    val map = Map("a" -> 1)
    val a = map("a")
    println(a)
    val b = map("b")
    println(b)
  }

  def useOption(): Unit = {
    val map = Map("a" -> 1)
    val a = map.get("a")
    val b = map.get("b")
    print(s"a: ${a} b: ${b}\n")
  }

  def useGetOrElse(): Unit = {
    val some = Some(2)
    val none = None
    print(s"${some.get}\n${some.getOrElse(3)}\n${none.getOrElse(5)}、")
  }

  class DelayBy1(resetValue: Option[UInt] = None) extends Module {
    val io = IO(new Bundle() {
      val in = Input(UInt(16.W))
      val out = Output(UInt(16.W))
    })
    val reg = if (resetValue.isDefined) {
      RegInit(resetValue.get)
    } else {
      Reg(UInt())
    }

    reg := io.in
    io.out := reg
  }

  def useValueMatch(): Unit = {
    val y = 2
    val x = y match {
      case 0 => "zero"
      case 1 => "one"
      case 2 => "two"
      case _ => "many"
    }
    print(s"the x is ${x}\n")
  }

  def useMulValueMatch(): Unit = {
    def animalType(fourLeg: Boolean, eatMeat: Boolean): String = {
      (fourLeg, eatMeat) match {
        case (true, true) => "dog"
        case (true, false) => "sheep"
        case (false, true) => "shark"
        case (false, false) => "lala"
      }
    }

    for (i <- Seq(true, false))
      for (j <- Seq(true, false)) {
        print(s"the animal is ${animalType(i, j)}\n")
      }
  }


  def useTypeMatch(): Unit = {
    val sequence = Seq("a", 1, 0.0)
    sequence.foreach {
      case x@(s: String) => print(s"$x is a String\n")
      case x@(s: Int) => print(s"$x is a Int\n")
      case x@(s: Double) => print(s"$x is a Double\n")
      case x => print(s"$x is a unknown type\n")
    }
  }

  def useMulTypeMatch(): Unit = {
    val sequence = Seq("a", 1, 0.0)
    sequence.foreach {
      case x@(_: Int | _: Double) => print(s"$x is a number!\n")
      case x@(_: String) => print(s"$x is a String!\n")
      case x => print(s"$x is an unknown type!\n")
    }
  }

  useTypeMatch()


  def runAll(): Unit = {
    useMap()
    useOption()
    useGetOrElse()
    useValueMatch()
    useMulValueMatch()
    useTypeMatch()
    useMulTypeMatch()
  }

  class HalfFullAdder(val hasCarry: Boolean) extends Module {
    val io = IO(new Bundle() {
      val a = Input(UInt(1.W))
      val b = Input(UInt(1.W))
      val carryIn = if (hasCarry) Some(Input(UInt(1.W))) else None
      val carryOut = Output(UInt(1.W))
      val s = Output(UInt(1.W))
    })
    val sum = io.a +& io.b +& io.carryIn.getOrElse(0.U)
    io.s := sum(0)
    io.carryOut := sum(1)
  }

  class HalfAdderTester(c: HalfFullAdder) extends PeekPokeTester(c) {
    require(!c.hasCarry, "DUT must be half adder")
    // 0 + 0 = 0
    poke(c.io.a, 0)
    poke(c.io.b, 0)
    expect(c.io.s, 0)
    expect(c.io.carryOut, 0)
    // 0 + 1 = 1
    poke(c.io.b, 1)
    expect(c.io.s, 1)
    expect(c.io.carryOut, 0)
    // 1 + 1 = 2
    poke(c.io.a, 1)
    expect(c.io.s, 0)
    expect(c.io.carryOut, 1)
    // 1 + 0 = 1
    poke(c.io.b, 0)
    expect(c.io.s, 1)
    expect(c.io.carryOut, 0)
  }

  class FullAdderTester(c: HalfFullAdder) extends PeekPokeTester(c) {
    require(c.hasCarry, "DUT must be half adder")
    poke(c.io.carryIn.get, 0)
    // 0 + 0 + 0 = 0
    poke(c.io.a, 0)
    poke(c.io.b, 0)
    expect(c.io.s, 0)
    expect(c.io.carryOut, 0)
    // 0 + 0 + 1 = 1
    poke(c.io.b, 1)
    expect(c.io.s, 1)
    expect(c.io.carryOut, 0)
    // 0 + 1 + 1 = 2
    poke(c.io.a, 1)
    expect(c.io.s, 0)
    expect(c.io.carryOut, 1)
    // 0 + 1 + 0 = 1
    poke(c.io.b, 0)
    expect(c.io.s, 1)
    expect(c.io.carryOut, 0)

    poke(c.io.carryIn.get, 1)
    // 1 + 0 + 0 = 1
    poke(c.io.a, 0)
    poke(c.io.b, 0)
    expect(c.io.s, 1)
    expect(c.io.carryOut, 0)
    // 1 + 0 + 1 = 2
    poke(c.io.b, 1)
    expect(c.io.s, 0)
    expect(c.io.carryOut, 1)
    // 1 + 1 + 1 = 3
    poke(c.io.a, 1)
    expect(c.io.s, 1)
    expect(c.io.carryOut, 1)
    // 1 + 1 + 0 = 2
    poke(c.io.b, 0)
    expect(c.io.s, 0)
    expect(c.io.carryOut, 1)
  }

  case class BinaryMealyParams(
                                nStates: Int,
                                s0: Int,
                                stateTransition: (Int, Boolean) => Int,
                                output: (Int, Boolean) => Int
                              ) {
    require(nStates >= 0)
    require(s0 < nStates && s0 >= 0)
  }

  class BinaryMealy(val mp : BinaryMealyParams)extends Module{
    val io = IO(new Bundle {
      val in = Input(Bool())
      val out = Output(UInt())
    })

    val state = RegInit(UInt(),mp.s0.U)
    io.out := 0.U
    for(i<-0 until mp.nStates){
      when(state === i.U){
        when(io.in){
          state := mp.stateTransition(i,true).U
          io.out := mp.output(i,true).U
        }.otherwise{
          state := mp.stateTransition(i,false).U
          io.out := mp.output(i,false).U
        }
      }
    }
  }


  val nStates = 3
  val s0 = 2
  def stateTransition(state: Int, in: Boolean): Int = {
    if (in) {
      1
    } else {
      0
    }
  }
  def output(state: Int, in: Boolean): Int = {
    if (state == 2) {
      return 0
    }
    if ((state == 1 && !in) || (state == 0 && in)) {
      return 1
    } else {
      return 0
    }
  }

  class BinaryMealyTester(c : BinaryMealy) extends PeekPokeTester(c){
    poke(c.io.in, false)
    expect(c.io.out, 0)
    step(1)
    poke(c.io.in, false)
    expect(c.io.out, 0)
    step(1)
    poke(c.io.in, false)
    expect(c.io.out, 0)
    step(1)
    poke(c.io.in, true)
    expect(c.io.out, 1)
    step(1)
    poke(c.io.in, true)
    expect(c.io.out, 0)
    step(1)
    poke(c.io.in, false)
    expect(c.io.out, 1)
    step(1)
    poke(c.io.in, true)
    expect(c.io.out, 1)
    step(1)
    poke(c.io.in, false)
    expect(c.io.out, 1)
    step(1)
    poke(c.io.in, true)
    expect(c.io.out, 1)
  }

  def runBinaryMealyTest():Unit={
    val testParams = BinaryMealyParams(nStates, s0, stateTransition, output)
    val works = iotesters.Driver(()=>new BinaryMealy(testParams)){c=>new BinaryMealyTester(c)}
    assert(works)
  }
  runBinaryMealyTest()
}
