
package sequential_logic

import chisel3._
import chisel3.util._
import chisel3.iotesters.{ChiselFlatSpec, Driver, PeekPokeTester}

object FindMaxExecute extends App {

  class FindMax extends Module {
    val io = IO(new Bundle {
      val in = Input(UInt(4.W))
      val max = Output(UInt(4.W))
    })

    val max = RegInit(4.U(4.W))
    when(io.in > max) {
      max := io.in
    }
    io.max := max
  }

  class FindMaxTester(c: FindMax) extends PeekPokeTester(c) {
    for (i <- 0 to 15) {
      poke(c.io.in, i.U)
      step(1)
      if (i <= 4) {
        expect(c.io.max, 4.U)
      }
      else {
        expect(c.io.max, i.U)
      }
    }
  }

  print(chisel3.Driver.emitVerilog(new FindMax))
  val testResult = Driver(() => new FindMax) { c => new FindMaxTester(c) }
  assert(testResult)
  print("FindMax test pass!!\n")
}

