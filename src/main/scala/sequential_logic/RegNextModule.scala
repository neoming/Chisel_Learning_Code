
package sequential_logic

import chisel3._
import chisel3.util._
import chisel3.iotesters.{ChiselFlatSpec, Driver, PeekPokeTester}

object RegNextModuleExecute extends App {

  class RegNextModule extends Module {
    val io = IO(new Bundle {
      val in  = Input(UInt(12.W))
      val out = Output(UInt(12.W))
    })

    // register bitwidth is inferred from io.out
    io.out := RegNext(io.in + 1.U)
  }

  class RegNextModuleTester(c: RegNextModule) extends PeekPokeTester(c) {
    for (i <- 0 until 100) {
      poke(c.io.in, i.U)
      step(1)
      expect(c.io.out, i+1)
    }
  }
  println(chisel3.Driver.emitVerilog(new RegNextModule))
  assert(chisel3.iotesters.Driver(() => new RegNextModule) { c => new RegNextModuleTester(c) })
  println("SUCCESS!!")
}
