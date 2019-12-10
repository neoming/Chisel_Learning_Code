
package control_flow

import chisel3._
import chisel3.iotesters.{Driver, PeekPokeTester}
import chisel3.util._

object GradLifeExecute extends App{

  def states = Map("idle" -> 0, "coding" -> 1, "writing" -> 2, "grad" -> 3)

  // life is full of question marks
  def gradLife (state: Int, coffee: Boolean, idea: Boolean, pressure: Boolean): Int = {
    var nextState = states("idle")
    if (state == states("idle")) {
      if      (coffee) { nextState = states("coding") }
      else if (idea) { nextState = states("idle") }
      else if (pressure) { nextState = states("writing") }
    } else if (state == states("coding")) {
      if      (coffee) { nextState = states("coding") }
      else if (idea || pressure) { nextState = states("writing") }
    } else if (state == states("writing")) {
      if      (coffee || idea) { nextState = states("writing") }
      else if (pressure) { nextState = states("grad") }
    }
    nextState
  }


  // life gets hard-er
  class GradLife extends Module {
    val io = IO(new Bundle {
      val state = Input(UInt(2.W))
      val coffee = Input(Bool())
      val idea = Input(Bool())
      val pressure = Input(Bool())
      val nextState = Output(UInt(2.W))
    })

    val idle :: coding :: writing :: grad :: Nil = Enum(4)

    io.nextState := idle
    when (io.state === idle) {
      when      (io.coffee) { io.nextState := coding }
        .elsewhen (io.idea) { io.nextState := idle }
        .elsewhen (io.pressure) { io.nextState := writing }
    } .elsewhen (io.state === coding) {
      when      (io.coffee) { io.nextState := coding }
        .elsewhen (io.idea || io.pressure) { io.nextState := writing }
    } .elsewhen (io.state === writing) {
      when      (io.coffee || io.idea) { io.nextState := writing }
        .elsewhen (io.pressure) { io.nextState := grad }
    }
  }

  // verify that the hardware matches the golden model
  class GradLifeSim(c: GradLife) extends PeekPokeTester(c) {
    for (state <- 0 to 3) {
      for (coffee <- List(true, false)) {
        for (idea <- List(true, false)) {
          for (pressure <- List(true, false)) {
            poke(c.io.state, state.U)
            poke(c.io.coffee, coffee)
            poke(c.io.idea, idea)
            poke(c.io.pressure, pressure)
            expect(c.io.nextState, gradLife(state, coffee, idea, pressure))
          }
        }
      }
    }
  }

  // Test
  val works = Driver(() => new GradLife) {c => new GradLifeSim(c)}
  assert(works)        // Scala Code: if works == false, will throw an error
  println("SUCCESS!!") // Scala Code: if we get here, our tests passed!
}