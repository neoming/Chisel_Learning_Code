
package firstModule

import chisel3._
import chisel3.iotesters.PeekPokeTester

class PrintingModuleTester(c: PrintingModule) extends PeekPokeTester(c) {
  poke(c.io.in, 3.U)
  step(5) // circuit will print

  println(s"Print during testing: Input is ${peek(c.io.in)}\n")
}
