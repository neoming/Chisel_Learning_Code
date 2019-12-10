
package comb_logic

import chisel3._
import chisel3.iotesters.PeekPokeTester

class ArbiterTester(c: Arbiter) extends PeekPokeTester(c){
  import scala.util.Random
  val data = Random.nextInt(65536)
  poke(c.io.fifo_data,data.U)

  for (i <- 0 until 8) {
    poke(c.io.fifo_valid, ((i>>0)%2).U)
    poke(c.io.pe0_ready,  ((i>>1)%2).U)
    poke(c.io.pe1_ready,  ((i>>2)%2).U)

    expect(c.io.fifo_ready, i>1)
    expect(c.io.pe0_valid,  i==3 || i==7)
    expect(c.io.pe1_valid,  i==5)

    if (i == 3 || i ==7) {
      expect(c.io.pe0_data, data)
    } else if (i == 5) {
      expect(c.io.pe1_data, data)
    }
  }
}
