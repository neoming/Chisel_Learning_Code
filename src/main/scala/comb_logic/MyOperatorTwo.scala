// See README.md for license details.
package comb_logic
import chisel3._
import chisel3.util._
/*this module is to learn the usage of function`Mux` and `cat`*/
class MyOperatorTwo extends Module{
  val io = IO(new Bundle() {
    val select = Input(Bool())
    val input_A = Input(UInt(4.W))
    val input_B = Input(UInt(4.W))
    val mux_result = Output(UInt(4.W))
    val cat_result = Output(UInt(8.W))
  })

  io.mux_result := Mux(io.select,io.input_A,io.input_B)
  io.cat_result := Cat(io.input_A,io.input_B)
}
