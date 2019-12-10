
package control_flow

import chisel3._
import chisel3.iotesters.PeekPokeTester

// verify that the max of the three inputs is correct
class Max3Tester(c: Max3) extends PeekPokeTester(c) {
  poke(c.io.in1, 6.U)
  poke(c.io.in2, 4.U)
  poke(c.io.in3, 2.U)
  expect(c.io.out, 6)  // input 1 should be biggest
  poke(c.io.in2, 7.U)
  expect(c.io.out, 7)  // now input 2 is
  poke(c.io.in3, 11.U)
  expect(c.io.out, 11) // and now input 3
  poke(c.io.in3, 3.U)
  expect(c.io.out, 7)  // show that decreasing an input works as well
}
