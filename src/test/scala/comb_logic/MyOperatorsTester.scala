
package comb_logic

import chisel3._
import chisel3.iotesters.PeekPokeTester

class MyOperatorsTester(c: MyOperators) extends PeekPokeTester(c) {
  def add(a: Int, b: Int): Int = a + b

  def sub(a: Int, b: Int): Int = a - b

  def mul(a: Int, b: Int): Int = a * b

  poke(c.io.op_a, 2.U)
  poke(c.io.op_b, 1.U)
  expect(c.io.out_add, add(2, 1))
  expect(c.io.out_sub, sub(2, 1))
  expect(c.io.out_mul, mul(2, 1))
}
