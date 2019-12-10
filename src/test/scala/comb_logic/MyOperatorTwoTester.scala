
package comb_logic

import chisel3._
import chisel3.iotesters.PeekPokeTester

class MyOperatorTwoTester(c: MyOperatorTwo) extends PeekPokeTester(c) {
  for (a <- 0 to 15)
    for (b <- 0 to 15) {
      poke(c.io.input_A, a.U)
      poke(c.io.input_B, b.U)
      poke(c.io.select, true.B)
      expect(c.io.mux_result, a.U)
      poke(c.io.select, false.B)
      expect(c.io.mux_result, b.U)
      val cat_result = (a * 16) + b
      expect(c.io.cat_result, cat_result.U)
    }
}
