
package sequential_logic

import chisel3._
import chisel3.util._
import chisel3.iotesters.{ChiselFlatSpec, Driver, PeekPokeTester}

object MyShiftRegisterExecute extends App {

  class MyShiftRegister(val init: Int = 1) extends Module {
    val io = IO(new Bundle {
      val in = Input(Bool())
      val out = Output(UInt(4.W))
    })

    val state = RegInit(UInt(4.W), init.U)

    val reg1 = RegInit(UInt(1.W), 1.U)
    val reg2 = RegInit(UInt(1.W), 0.U)
    val reg3 = RegInit(UInt(1.W), 0.U)
    val reg4 = RegInit(UInt(1.W), 0.U)

    reg1 := io.in
    reg2 := reg1
    reg3 := reg2
    reg4 := reg3

    io.out := Cat(reg4, reg3, reg2, reg1)
  }

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

  class MyShiftRegisterTester(c: MyShiftRegister) extends PeekPokeTester(c) {
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

  print(chisel3.Driver.emitVerilog(new MyShiftRegister()))
  assert(chisel3.iotesters.Driver(() => new MyShiftRegister()) {
    c => new MyShiftRegisterTester(c)
  })
  println("SUCCESS!!")
}