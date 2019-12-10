// See README.md for license details.

package combinationalLogic

import chisel3._
import chisel3.iotesters.{Driver, PeekPokeTester}

class MACTester (c : MAC) extends PeekPokeTester(c){
  val cycle = 100
  import scala.util.Random
  for(i <- 0 until cycle){
    val in_a = Random.nextInt(16)
    val in_b = Random.nextInt(16)
    val in_c = Random.nextInt(16)
    poke(c.io.a,in_a.U)
    poke(c.io.b, in_b.U)
    poke(c.io.c, in_c.U)
    expect(c.io.out, (in_a*in_b + in_c).U)
  }
}
