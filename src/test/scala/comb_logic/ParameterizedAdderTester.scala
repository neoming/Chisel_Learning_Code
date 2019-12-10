
package comb_logic


import chisel3._
import chisel3.iotesters.PeekPokeTester

class ParameterizedAdderTester(p: ParameterizedAdder, saturate: Boolean) extends PeekPokeTester(p) {
  val cycle = 100

  import scala.util.Random
  import scala.math.min

  for (i <- 0 to cycle) {
    val a = Random.nextInt(16)
    val b = Random.nextInt(16)
    poke(p.io.in_a, a.U)
    poke(p.io.in_b, b.U)
    if (saturate) {
      expect(p.io.out, min(a + b, 15).U)
    } else {
      expect(p.io.out, ((a + b) % 16).U)
    }
  }

  // ensure we test saturation vs. truncation
  poke(p.io.in_a, 15.U)
  poke(p.io.in_b, 15.U)
  if (saturate) {
    expect(p.io.out, 15)
  } else {
    expect(p.io.out, 14)
  }
}
