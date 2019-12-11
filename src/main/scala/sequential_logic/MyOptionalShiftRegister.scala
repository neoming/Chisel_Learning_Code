
package sequential_logic

import chisel3._
import chisel3.util._
import chisel3.iotesters.{ChiselFlatSpec, Driver, PeekPokeTester}

object MyOptionalShiftRegisterExecute extends App{
  // n is the output width (number of delays - 1)
  // init state to init
  class MyOptionalShiftRegister(val n: Int, val init: BigInt = 1) extends Module {
    val io = IO(new Bundle {
      val en  = Input(Bool())
      val in  = Input(Bool())
      val out = Output(UInt(n.W))
    })

    val state = RegInit(init.U(n.W))
    val nexeState = (state << 1) | io.in
    when(io.en){
      state := nexeState
    }
    io.out := state
  }

  class MyOptionalShiftRegisterTester(c: MyOptionalShiftRegister) extends PeekPokeTester(c) {
    val inSeq = Seq(0, 1, 1, 1, 0, 1, 1, 0, 0, 1)
    var state = c.init
    var i = 0
    poke(c.io.en, 1.U)
    while (i < 10 * c.n) {
      // poke in repeated inSeq
      val toPoke = inSeq(i % inSeq.length)
      poke(c.io.in, toPoke.U)
      // update expected state
      state = ((state * 2) + toPoke) & BigInt("1"*c.n, 2)
      step(1)
      expect(c.io.out, state)

      i += 1
    }
  }

  // test different depths
  for (i <- Seq(3, 4, 8, 24, 65)) {
    print(s"Testing n=$i\n")
    assert(chisel3.iotesters.Driver(() => new MyOptionalShiftRegister(n = i)) {
      c => new MyOptionalShiftRegisterTester(c)
    })
  }
  print("SUCCESS!!\n")
}