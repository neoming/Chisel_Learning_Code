
package first_module

import chisel3._

class Passthrough extends Module{
  val io = IO(new Bundle() {
    val in = Input(UInt(4.W))
    val out = Output(UInt(4.W))
  })

  io.out := io.in
}
