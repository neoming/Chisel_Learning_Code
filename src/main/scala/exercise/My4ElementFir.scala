
package exercise
import chisel3._
import chisel3.iotesters.{Driver,PeekPokeTester}

class My4ElementFir(b0: Int, b1: Int, b2: Int, b3: Int) extends Module {
  val io = IO(new Bundle {
    val in = Input(UInt(8.W))
    val out = Output(UInt(8.W))
  })
  val z1 = RegNext(io.in,0.U)
  val z2 = RegNext(z1,0.U)
  val z3 = RegNext(z2,0.U)

  val result1 = io.in * b0.U(8.W)
  val result2 = z1 * b1.U(8.W)
  val result3 = z2 * b2.U(8.W)
  val result4 = z3 * b3.U(8.W)
  io.out := result1 + result2 + result3 + result4
}

object runTest extends App{
  Driver(() => new My4ElementFir(0, 0, 0, 0)) {
    c => new PeekPokeTester(c) {
      poke(c.io.in, 0.U)
      expect(c.io.out, 0)
      step(1)
      poke(c.io.in, 4.U)
      expect(c.io.out, 0)
      step(1)
      poke(c.io.in, 5.U)
      expect(c.io.out, 0)
      step(1)
      poke(c.io.in, 2.U)
      expect(c.io.out, 0)
    }
  }
  // Simple 4-point moving average
  Driver(() => new My4ElementFir(1, 1, 1, 1)) {
    c => new PeekPokeTester(c) {
      poke(c.io.in, 1.U)
      expect(c.io.out, 1.U)  // 1, 0, 0, 0
      step(1)
      poke(c.io.in, 4.U)
      expect(c.io.out, 5.U)  // 4, 1, 0, 0
      step(1)
      poke(c.io.in, 3.U)
      expect(c.io.out, 8.U)  // 3, 4, 1, 0
      step(1)
      poke(c.io.in, 2.U)
      expect(c.io.out, 10.U)  // 2, 3, 4, 1
      step(1)
      poke(c.io.in, 7.U)
      expect(c.io.out, 16.U)  // 7, 2, 3, 4
      step(1)
      poke(c.io.in, 0.U)
      expect(c.io.out, 12.U)  // 0, 7, 2, 3
    }
  }

  val tick :Int = 1
  val b0 :Int = 1
  val b1 :Int = 2
  val b2 :Int = 3
  val b3 :Int = 4
  // Nonsymmetric filter
  Driver(() => new My4ElementFir(b0, b1, b2, b3)) {
    c => new PeekPokeTester(c) {
      poke(c.io.in, 1.U)
      expect(c.io.out, 1.U)  // 1*1, 0*2, 0*3, 0*4
      step(tick)
      poke(c.io.in, 4.U)
      expect(c.io.out, 6.U)  // 4*1, 1*2, 0*3, 0*4
      step(tick)
      poke(c.io.in, 3.U)
      expect(c.io.out, 14.U)  // 3*1, 4*2, 1*3, 0*4
      step(tick)
      poke(c.io.in, 2.U)
      expect(c.io.out, 24.U)  // 2*1, 3*2, 4*3, 1*4
      step(tick)
      poke(c.io.in, 7.U)
      expect(c.io.out, 36.U)  // 7*1, 2*2, 3*3, 4*4
      step(tick)
      poke(c.io.in, 0.U)
      expect(c.io.out, 32.U)  // 0*1, 7*2, 2*3, 3*4
    }
  }
}