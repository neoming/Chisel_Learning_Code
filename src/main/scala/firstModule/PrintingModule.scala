
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

  printf(s"Print during generation: Input is ${io.in}\n")
}

