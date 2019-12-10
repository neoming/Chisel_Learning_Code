// See README.md for license details.
package comb_logic

import chisel3._
/*this module is to learn how to using chisel to generate parameterized module*/
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
