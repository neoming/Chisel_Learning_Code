
package firstModule

import chisel3._
import chisel3.util._
import chisel3.iotesters.{ChiselFlatSpec, Driver,PeekPokeTester}

object PassthroughTester extends App{
  def passthroughTester () : Unit = {
    val testResult = Driver(() => new Passthrough){
      c => new PeekPokeTester(c){
        poke(c.io.in,1)
        expect(c.io.out,1)
      }
    }
    assert(testResult)
    println("test Pass!")
    println(chisel3.Driver.emitVerilog(new Passthrough))
  }

  def passthroughGeneratorTester() : Unit = {
    println(chisel3.Driver.emitVerilog(new PassthroughGenerator(1)))
    println(chisel3.Driver.emitVerilog(new PassthroughGenerator(2)))
  }


  passthroughTester()
  passthroughGeneratorTester()
}