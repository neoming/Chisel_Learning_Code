// See README.md for license details.
package comb_logic

import chisel3._
import chisel3.util._
import chisel3.iotesters.{Driver, PeekPokeTester}
/* this is about to learn the Chisel operators ps:mul */
class MyOperators extends Module {
  val io = IO(new Bundle {
    val op_a = Input(UInt(4.W))
    val op_b = Input(UInt(4.W))
    val out_add = Output(UInt(4.W))
    val out_sub = Output(UInt(4.W))
    val out_mul = Output(UInt(8.W))
  })

  io.out_add := io.op_a + io.op_b
  io.out_sub := io.op_a - io.op_b
  io.out_mul := io.op_a * io.op_b
}