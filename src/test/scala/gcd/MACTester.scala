// See README.md for license details.

package gcd

import chisel3._
import chisel3.iotesters.{Driver, PeekPokeTester}

class MACTester (c : MAC) extends PeekPokeTester(c){
  val cycle = 100
  import scala.util.Random
  for(i <- 0 until cycle){
    val in_a = Random.nextInt(16)
    val in_b = Random.nextInt(16)
    val in_c = Random.nextInt(16)
    poke(c.io.a,in_a)
    poke(c.io.b, in_b)
    poke(c.io.c, in_c)
    expect(c.io.out, in_a*in_b + in_c)
  }

  assert(Driver(()=>new MAC){
    c => new MACTester(c)
  })

  println("SUCCESS!")
}
