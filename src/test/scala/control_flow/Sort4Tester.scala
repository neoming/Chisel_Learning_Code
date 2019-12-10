
package control_flow

import chisel3._
import chisel3.iotesters.PeekPokeTester

// verify the inputs are sorted
class Sort4Tester(c: Sort4) extends PeekPokeTester(c) {
  poke(c.io.in0, 3.U)
  poke(c.io.in1, 6.U)
  poke(c.io.in2, 9.U)
  poke(c.io.in3, 12.U)
  expect(c.io.out0, 3)
  expect(c.io.out1, 6)
  expect(c.io.out2, 9)
  expect(c.io.out3, 12)

  poke(c.io.in0, 13.U)
  poke(c.io.in1, 4.U)
  poke(c.io.in2, 6.U)
  poke(c.io.in3, 1.U)
  expect(c.io.out0, 1)
  expect(c.io.out1, 4)
  expect(c.io.out2, 6)
  expect(c.io.out3, 13)

  poke(c.io.in0, 13.U)
  poke(c.io.in1, 6.U)
  poke(c.io.in2, 4.U)
  poke(c.io.in3, 1.U)
  expect(c.io.out0, 1)
  expect(c.io.out1, 4)
  expect(c.io.out2, 6)
  expect(c.io.out3, 13)
}

// verify the all possible ordering of 4 numbers are sorted
class BetterSort4Tester(c: Sort4) extends PeekPokeTester(c) {
  List(1, 2, 3, 4).permutations.foreach { case i0 :: i1 :: i2 :: i3 :: Nil =>
    poke(c.io.in0, i0.U)
    poke(c.io.in1, i1.U)
    poke(c.io.in2, i2.U)
    poke(c.io.in3, i3.U)
    expect(c.io.out0, 1)
    expect(c.io.out1, 2)
    expect(c.io.out2, 3)
    expect(c.io.out3, 4)
    println(s"Sorting $i0 $i1 $i2 $i3\n")
  }
}