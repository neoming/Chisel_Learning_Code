
package sequential_logic

import chisel3._
import chisel3.util._
import chisel3.iotesters.{ChiselFlatSpec, Driver, PeekPokeTester}

object RegisterModuleExecute extends App {

  class RegisterModule extends Module {
    val io = IO(new Bundle {
      val in = Input(UInt(12.W))
      val out = Output(UInt(12.W))
    })

    val register = Reg(UInt(12.W))
    register := io.in + 1.U
    io.out := register
  }

  class RegisterModuleTester(c: RegisterModule) extends PeekPokeTester(c) {
    println(chisel3.Driver.emitVerilog(new RegisterModule))
    for (i <- 0 until 100) {
      poke(c.io.in, i.U)
      step(1)
      expect(c.io.out, i + 1)
    }
  }

  assert(Driver(() => new RegisterModule) { c => new RegisterModuleTester(c) })
  println("SUCCESS!!")
}
