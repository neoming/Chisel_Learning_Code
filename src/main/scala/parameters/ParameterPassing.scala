
package parameters

import chisel3._
import chisel3.iotesters.{Driver,PeekPokeTester}
object Run extends App{
  class ParameterizedScalaObject(param1: Int, param2: String) {
    println(s"I have parameters: param1 = $param1 and param2 = $param2")
  }
  val obj1 = new ParameterizedScalaObject(4,     "Hello")
  val obj2 = new ParameterizedScalaObject(4 + 2, "World")

  class ParameterizedWidthAdder(in0Width: Int, in1Width: Int, sumWidth: Int) extends Module {
    require(in0Width >= 0)
    require(in1Width >= 0)
    require(sumWidth >= 0)
    val io = IO(new Bundle {
      val in0 = Input(UInt(in0Width.W))
      val in1 = Input(UInt(in1Width.W))
      val sum = Output(UInt(sumWidth.W))
    })
    // a +& b includes the carry, a + b does not
    io.sum := io.in0 +& io.in1
  }

  chisel3.Driver.emitVerilog(new ParameterizedWidthAdder(1, 4, 6))

  /** Sort4 sorts its 4 inputs to its 4 outputs */
  class Sort4(ascending: Boolean) extends Module {
    val io = IO(new Bundle {
      val in0 = Input(UInt(16.W))
      val in1 = Input(UInt(16.W))
      val in2 = Input(UInt(16.W))
      val in3 = Input(UInt(16.W))
      val out0 = Output(UInt(16.W))
      val out1 = Output(UInt(16.W))
      val out2 = Output(UInt(16.W))
      val out3 = Output(UInt(16.W))
    })

    // this comparison funtion decides < or > based on the module's parameterization
    def comp(l: UInt, r: UInt): Bool = {
      if (ascending) {
        l < r
      } else {
        l > r
      }
    }

    val row10 = Wire(UInt(16.W))
    val row11 = Wire(UInt(16.W))
    val row12 = Wire(UInt(16.W))
    val row13 = Wire(UInt(16.W))

    when(comp(io.in0, io.in1)) {
      row10 := io.in0            // preserve first two elements
      row11 := io.in1
    }.otherwise {
      row10 := io.in1            // swap first two elements
      row11 := io.in0
    }

    when(comp(io.in2, io.in3)) {
      row12 := io.in2            // preserve last two elements
      row13 := io.in3
    }.otherwise {
      row12 := io.in3            // swap last two elements
      row13 := io.in2
    }

    val row21 = Wire(UInt(16.W))
    val row22 = Wire(UInt(16.W))

    when(comp(row11, row12)) {
      row21 := row11            // preserve middle 2 elements
      row22 := row12
    }.otherwise {
      row21 := row12            // swap middle two elements
      row22 := row11
    }

    val row31 = Wire(UInt(16.W))
    val row32 = Wire(UInt(16.W))
    when(comp(row10, row13)) {
      row31 := row10            // preserve middle 2 elements
      row32 := row13
    }.otherwise {
      row31 := row13            // swap middle two elements
      row32 := row10
    }

    when(comp(row10, row21)) {
      io.out0 := row31            // preserve first two elements
      io.out1 := row21
    }.otherwise {
      io.out0 := row21            // swap first two elements
      io.out1 := row31
    }

    when(comp(row22, row13)) {
      io.out2 := row22            // preserve first two elements
      io.out3 := row32
    }.otherwise {
      io.out2 := row32            // swap first two elements
      io.out3 := row22
    }
  }

  // verify the inputs are sorted
  class Sort4AscendingTester(c: Sort4) extends PeekPokeTester(c) {
    poke(c.io.in0, 3.U)
    poke(c.io.in1, 6.U)
    poke(c.io.in2, 9.U)
    poke(c.io.in3, 12.U)
    expect(c.io.out0, 3)
    expect(c.io.out1, 6)
    expect(c.io.out2, 9)
    expect(c.io.out3, 12)

    poke(c.io.in0, 13.U)
    poke(c.io.in1, 4.U)
    poke(c.io.in2, 6.U)
    poke(c.io.in3, 1.U)
    expect(c.io.out0, 1)
    expect(c.io.out1, 4)
    expect(c.io.out2, 6)
    expect(c.io.out3, 13)

    poke(c.io.in0, 13.U)
    poke(c.io.in1, 6.U)
    poke(c.io.in2, 4.U)
    poke(c.io.in3, 1.U)
    expect(c.io.out0, 1)
    expect(c.io.out1, 4)
    expect(c.io.out2, 6)
    expect(c.io.out3, 13)

  }
  class Sort4DescendingTester(c: Sort4) extends PeekPokeTester(c) {
    poke(c.io.in0, 3.U)
    poke(c.io.in1, 6.U)
    poke(c.io.in2, 9.U)
    poke(c.io.in3, 12.U)
    expect(c.io.out0, 12)
    expect(c.io.out1, 9)
    expect(c.io.out2, 6)
    expect(c.io.out3, 3)

    poke(c.io.in0, 13.U)
    poke(c.io.in1, 4.U)
    poke(c.io.in2, 6.U)
    poke(c.io.in3, 1.U)
    expect(c.io.out0, 13)
    expect(c.io.out1, 6)
    expect(c.io.out2, 4)
    expect(c.io.out3, 1)

    poke(c.io.in0, 1.U)
    poke(c.io.in1, 6.U)
    poke(c.io.in2, 4.U)
    poke(c.io.in3, 13.U)
    expect(c.io.out0, 13)
    expect(c.io.out1, 6)
    expect(c.io.out2, 4)
    expect(c.io.out3, 1)

  }

  // Here are the testers
  val worksAscending = iotesters.Driver(() => new Sort4(true)) { c => new Sort4AscendingTester(c) }
  val worksDescending = iotesters.Driver(() => new Sort4(false)) { c => new Sort4DescendingTester(c) }
  assert(worksAscending && worksDescending) // Scala Code: if works == false, will throw an error
  println("SUCCESS!!") // Scala Code: if we get here, our tests passed!
}
