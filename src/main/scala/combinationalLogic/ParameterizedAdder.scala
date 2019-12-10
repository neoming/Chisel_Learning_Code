
package combinationalLogic

import chisel3._

class ParameterizedAdder(saturate: Boolean) extends Module {
  val io = IO(new Bundle {
    val in_a = Input(UInt(4.W))
    val in_b = Input(UInt(4.W))
    val out = Output(UInt(4.W))
  })

  val sum = io.in_a +& io.in_b
  if (saturate) {
    io.out := Mux(sum > 15.U, 15.U, sum)
  } else {
    io.out := sum
  }
}
