
package sequential_logic

import chisel3._
import chisel3.util._
import chisel3.iotesters.{ChiselFlatSpec, Driver, PeekPokeTester}

object BetterShiftRegisterExecute extends App {

  class BetterMyShiftRegister(val init: Int = 1) extends Module {
    val io = IO(new Bundle {
      val in = Input(Bool())
      val out = Output(UInt(4.W))
    })

    val state = RegInit(UInt(4.W), init.U)
    val nextState = (state << 1) | io.in
    state := nextState
    io.out := state
  }

  class BetterShiftRegisterTester(c: BetterMyShiftRegister) extends PeekPokeTester(c) {
    var state = c.init
    for (i <- 0 until 10) {
      // poke in LSB of i (i % 2)
      poke(c.io.in, (i % 2).U)
      // update expected state
      state = ((state * 2) + (i % 2)) & 0xf
      step(1)
      expect(c.io.out, state)
    }
  }

  print(chisel3.Driver.emitVerilog(new BetterMyShiftRegister()))
  assert(chisel3.iotesters.Driver(() => new BetterMyShiftRegister()) {
    c => new BetterShiftRegisterTester(c)
  })
  println("SUCCESS!!")
}