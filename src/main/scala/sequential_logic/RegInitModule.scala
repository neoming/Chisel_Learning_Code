
package sequential_logic

import chisel3._
import chisel3.util._
import chisel3.iotesters.{ChiselFlatSpec, Driver, PeekPokeTester}

object RegInitModuleExecute extends App{
  class RegInitModule extends Module {
    val io = IO(new Bundle {
      val in  = Input(UInt(12.W))
      val out = Output(UInt(12.W))
    })

    //val register = RegInit(0.U(12.W))
    val register = RegInit(UInt(12.W),1.U)
    register := io.in + 1.U
    io.out := register
  }

  class RegInitModuleTester(c : RegInitModule) extends PeekPokeTester(c){
    expect(c.io.out,1.U)
    poke(c.io.in,1.U)
    step(1)
    expect(c.io.out,2.U)
  }

  print(chisel3.Driver.emitVerilog(new RegInitModule))
  val testResult = Driver(() => new RegInitModule){ c => new RegInitModuleTester(c) }
  assert(testResult)
  print("RegInit test pass!!\n")
}
