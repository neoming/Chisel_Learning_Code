
package control_flow

import chisel3._
import chisel3.iotesters.{PeekPokeTester,Driver}
/*this module is learning how to use when,elsewhen,otherwise to control*/
object PolynomialExecute extends App{
  def poly0(x: Int): Int = x*x - 2*x + 1
  def poly1(x: Int): Int = 2*x*x + 6*x + 3
  def poly2(x: Int): Int = 4*x*x - 10*x - 5

  def poly(select: Int, x: Int): Int = {
    if(select == 0) {
      poly0(x)
    }
    else if(select == 1) {
      poly1(x)
    }
    else {
      poly2(x)
    }
  }

  class Polynomial extends Module{
    val io = IO(new Bundle {
      val select = Input(UInt(2.W))
      val x = Input(SInt(32.W))
      val fOfX = Output(SInt(32.W))
    })

    val result = Wire(SInt(32.W))
    val square = Wire(SInt(32.W))

    square := io.x * io.x
    when(io.select === 0.U) {
      result := (square - (2.S * io.x)) + 1.S
    }.elsewhen(io.select === 1.U) {
      result := (2.S * square) + (6.S * io.x) + 3.S
    }.otherwise {
      result := (4.S * square) - (10.S * io.x) - 5.S
    }

    io.fOfX := result
  }

  // verify that the computation is correct
  class PolynomialTester(c: Polynomial) extends PeekPokeTester(c) {
    for(x <- 0 to 20) {
      for(select <- 0 to 2) {
        poke(c.io.select, select.U)
        poke(c.io.x, x.U)
        expect(c.io.fOfX, poly(select, x))
      }
    }
  }

  def runTest():Unit = {
    // Test Polynomial
    val works = Driver(() => new Polynomial) {
      c => new PolynomialTester(c)
    }
    assert(works)        // Scala Code: if works == false, will throw an error
    println("SUCCESS!!") // Scala Code: if we get here, our tests passed!
  }

  runTest()
}
