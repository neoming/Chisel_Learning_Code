
package firstModule

import chisel3._
import chisel3.iotesters.PeekPokeTester

class PrintingModule extends Module {
  val io = IO(new Bundle {
    val in = Input(UInt(4.W))
    val out = Output(UInt(4.W))
  })
  io.out := io.in

  printf("Print during simulation: Input is %d\n", io.in)
  // chisel printf has its own string interpolator too
  printf(p"Print during simulation: IO is $io\n")

  println(s"Print during generation: Input is ${io.in}")
}

class PrintingModuleTester(c: PrintingModule) extends PeekPokeTester(c) {
  poke(c.io.in, 3)
  step(5) // circuit will print

  println(s"Print during testing: Input is ${peek(c.io.in)}")
}

object Main extends App{
  chisel3.iotesters.Driver( () => new PrintingModule ) { c => new PrintingModuleTester(c) }
}
