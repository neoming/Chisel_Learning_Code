
package sequential_logic

import chisel3._
import chisel3.util._
import chisel3.iotesters.{ChiselFlatSpec, Driver, PeekPokeTester}

object CombExecute extends App{
  class Comb extends Module {
    val io = IO(new Bundle {
      val in  = Input(SInt(12.W))
      val out = Output(SInt(12.W))
    })

    val delay: SInt = Reg(SInt(12.W))
    delay := io.in
    io.out := io.in - delay
  }
  print(chisel3.Driver.emitVerilog(new Comb))
}
