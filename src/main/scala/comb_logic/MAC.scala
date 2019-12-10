// See README.md for license details.

package comb_logic

import chisel3._

class MAC extends Module {
  val io = IO(new Bundle() {
    val a = Input(UInt(4.W))
    val b = Input(UInt(4.W))
    val c = Input(UInt(4.W))
    val out = Output(UInt(8.W))
  })
  io.out := (io.a * io.b) + io.c
}
