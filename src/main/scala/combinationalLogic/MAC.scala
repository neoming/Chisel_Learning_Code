// See README.md for license details.

package combinationalLogic

import chisel3._
/**
 * Compute GCD using subtraction method.
 * Subtracts the smaller from the larger until register y is zero.
 * value in register x is then the GCD
 */
class MAC extends Module {
  val io = IO(new Bundle() {
    val a = Input(UInt(4.W))
    val b = Input(UInt(4.W))
    val c = Input(UInt(4.W))
    val out = Output(UInt(8.W))
  })
  io.out := (io.a * io.b) + io.c
}
