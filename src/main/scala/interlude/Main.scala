
package interlude

import chisel3.{Module, _}
import chisel3.util._
import chisel3.iotesters.{Driver, PeekPokeTester}

object Main extends App {

  def runQueue(): Unit = {
    Driver(() => new Module {
      // Example circuit using a Queue
      val io = IO(new Bundle {
        val in = Flipped(Decoupled(UInt(8.W)))
        val out = Decoupled(UInt(8.W))
      })
      val queue = Queue(io.in, 2) // 2-element queue
      io.out <> queue
    }) { c =>
      new PeekPokeTester(c) {
        // Example testsequence showing the use and behavior of Queue
        poke(c.io.out.ready, 0)
        poke(c.io.in.valid, 1) // Enqueue an element
        poke(c.io.in.bits, 42)
        println(s"Starting:")
        println(s"\tio.in: ready=${peek(c.io.in.ready)}")
        println(s"\tio.out: valid=${peek(c.io.out.valid)}, bits=${peek(c.io.out.bits)}")
        step(1)

        poke(c.io.in.valid, 1) // Enqueue another element
        poke(c.io.in.bits, 43)
        // What do you think io.out.valid and io.out.bits will be?
        println(s"After first enqueue:")
        println(s"\tio.in: ready=${peek(c.io.in.ready)}")
        println(s"\tio.out: valid=${peek(c.io.out.valid)}, bits=${peek(c.io.out.bits)}")
        step(1)

        poke(c.io.in.valid, 1) // Read a element, attempt to enqueue
        poke(c.io.in.bits, 44)
        poke(c.io.out.ready, 1)
        // What do you think io.in.ready will be, and will this enqueue succeed, and what will be read?
        println(s"On first read:")
        println(s"\tio.in: ready=${peek(c.io.in.ready)}")
        println(s"\tio.out: valid=${peek(c.io.out.valid)}, bits=${peek(c.io.out.bits)}")
        step(1)

        poke(c.io.in.valid, 0) // Read elements out
        poke(c.io.out.ready, 1)
        // What do you think will be read here?
        println(s"On second read:")
        println(s"\tio.in: ready=${peek(c.io.in.ready)}")
        println(s"\tio.out: valid=${peek(c.io.out.valid)}, bits=${peek(c.io.out.bits)}")
        step(1)

        // Will a third read produce anything?
        println(s"On third read:")
        println(s"\tio.in: ready=${peek(c.io.in.ready)}")
        println(s"\tio.out: valid=${peek(c.io.out.valid)}, bits=${peek(c.io.out.bits)}")
        step(1)
      }
    }
  }

  def runArbiter(): Unit = {
    Driver(() => new Module {
      // Example circuit using a priority arbiter
      val io = IO(new Bundle {
        val in = Flipped(Vec(2, Decoupled(UInt(8.W))))
        val out = Decoupled(UInt(8.W))
      })
      // Arbiter doesn't have a convenience constructor, so it's built like any Module
      val arbiter = Module(new Arbiter(UInt(8.W), 2)) // 2 to 1 Priority Arbiter
      arbiter.io.in <> io.in
      io.out <> arbiter.io.out
    }) { c =>
      new PeekPokeTester(c) {
        poke(c.io.in(0).valid, 0)
        poke(c.io.in(1).valid, 0)
        println(s"Start:")
        println(s"\tin(0).ready=${peek(c.io.in(0).ready)}, in(1).ready=${peek(c.io.in(1).ready)}")
        println(s"\tout.valid=${peek(c.io.out.valid)}, out.bits=${peek(c.io.out.bits)}")
        poke(c.io.in(1).valid, 1) // Valid input 1
        poke(c.io.in(1).bits, 42)
        // What do you think the output will be?
        println(s"valid input 1:")
        println(s"\tin(0).ready=${peek(c.io.in(0).ready)}, in(1).ready=${peek(c.io.in(1).ready)}")
        println(s"\tout.valid=${peek(c.io.out.valid)}, out.bits=${peek(c.io.out.bits)}")
        poke(c.io.in(0).valid, 1) // Valid inputs 0 and 1
        poke(c.io.in(0).bits, 43)
        // What do you think the output will be? Which inputs will be ready?
        println(s"valid inputs 0 and 1:")
        println(s"\tin(0).ready=${peek(c.io.in(0).ready)}, in(1).ready=${peek(c.io.in(1).ready)}")
        println(s"\tout.valid=${peek(c.io.out.valid)}, out.bits=${peek(c.io.out.bits)}")
        poke(c.io.in(1).valid, 0) // Valid input 0
        // What do you think the output will be?
        println(s"valid input 0:")
        println(s"\tin(0).ready=${peek(c.io.in(0).ready)}, in(1).ready=${peek(c.io.in(1).ready)}")
        println(s"\tout.valid=${peek(c.io.out.valid)}, out.bits=${peek(c.io.out.bits)}")
      }
    }
  }

  def showArbiter(): Unit = {
    class MyArbiter extends Module {
      // Example circuit using a priority arbiter
      val io = IO(new Bundle {
        val in = Flipped(Vec(2, Decoupled(UInt(8.W))))
        val out = Decoupled(UInt(8.W))
      })
      // Arbiter doesn't have a convenience constructor, so it's built like any Module
      val arbiter = Module(new Arbiter(UInt(8.W), 2)) // 2 to 1 Priority Arbiter
      arbiter.io.in <> io.in
      io.out <> arbiter.io.out
    }
    chisel3.Driver.emitVerilog(new MyArbiter)
  }

  def runPopCount(): Unit = {
    Driver(() => new Module {
      // Example circuit using Reverse
      val io = IO(new Bundle {
        val in = Input(UInt(8.W))
        val out = Output(UInt(8.W))
      })
      io.out := PopCount(io.in)
    }) { c =>
      new PeekPokeTester(c) {
        // Integer.parseInt is used create an Integer from a binary specification
        poke(c.io.in, Integer.parseInt("00000000", 2))
        println(s"in=0b${peek(c.io.in).toInt.toBinaryString}, out=${peek(c.io.out)}")

        poke(c.io.in, Integer.parseInt("00001111", 2))
        println(s"in=0b${peek(c.io.in).toInt.toBinaryString}, out=${peek(c.io.out)}")

        poke(c.io.in, Integer.parseInt("11001010", 2))
        println(s"in=0b${peek(c.io.in).toInt.toBinaryString}, out=${peek(c.io.out)}")

        poke(c.io.in, Integer.parseInt("11111111", 2))
        println(s"in=0b${peek(c.io.in).toInt.toBinaryString}, out=${peek(c.io.out)}")
      }
    }
  }

  def runReverse(): Unit = {
    Driver(() => new Module {
      // Example circuit using Reverse
      val io = IO(new Bundle {
        val in = Input(UInt(8.W))
        val out = Output(UInt(8.W))
      })
      io.out := Reverse(io.in)
    }) { c =>
      new PeekPokeTester(c) {
        // Integer.parseInt is used create an Integer from a binary specification
        poke(c.io.in, Integer.parseInt("01010101", 2))
        println(s"in=0b${peek(c.io.in).toInt.toBinaryString}, out=0b${peek(c.io.out).toInt.toBinaryString}")

        poke(c.io.in, Integer.parseInt("00001111", 2))
        println(s"in=0b${peek(c.io.in).toInt.toBinaryString}, out=0b${peek(c.io.out).toInt.toBinaryString}")

        poke(c.io.in, Integer.parseInt("11110000", 2))
        println(s"in=0b${peek(c.io.in).toInt.toBinaryString}, out=0b${peek(c.io.out).toInt.toBinaryString}")

        poke(c.io.in, Integer.parseInt("11001010", 2))
        println(s"in=0b${peek(c.io.in).toInt.toBinaryString}, out=0b${peek(c.io.out).toInt.toBinaryString}")
      }
    }
  }

  def runPriorityMux(): Unit = {
    Driver(() => new Module {
      // Example circuit using PriorityMux
      val io = IO(new Bundle {
        val in_sels = Input(Vec(2, Bool()))
        val in_bits = Input(Vec(2, UInt(8.W)))
        val out = Output(UInt(8.W))
      })
      io.out := PriorityMux(io.in_sels, io.in_bits)
    }) { c =>
      new PeekPokeTester(c) {
        poke(c.io.in_bits(0), 10)
        poke(c.io.in_bits(1), 20)

        // Select higher index only
        poke(c.io.in_sels(0), 0)
        poke(c.io.in_sels(1), 1)
        println(s"in_sels=${peek(c.io.in_sels)}, out=${peek(c.io.out)}")

        // Select both - arbitration needed
        poke(c.io.in_sels(0), 1)
        poke(c.io.in_sels(1), 1)
        println(s"in_sels=${peek(c.io.in_sels)}, out=${peek(c.io.out)}")

        // Select lower index only
        poke(c.io.in_sels(0), 1)
        poke(c.io.in_sels(1), 0)
        println(s"in_sels=${peek(c.io.in_sels)}, out=${peek(c.io.out)}")
      }
    }
  }

  def runMux1H(): Unit = {
    Driver(() => new Module {
      // Example circuit using Mux1H
      val io = IO(new Bundle {
        val in_sels = Input(Vec(2, Bool()))
        val in_bits = Input(Vec(2, UInt(8.W)))
        val out = Output(UInt(8.W))
      })
      io.out := Mux1H(io.in_sels, io.in_bits)
    }) { c =>
      new PeekPokeTester(c) {
        poke(c.io.in_bits(0), 10)
        poke(c.io.in_bits(1), 20)

        // Select index 1
        poke(c.io.in_sels(0), 0)
        poke(c.io.in_sels(1), 1)
        println(s"in_sels=${peek(c.io.in_sels)}, out=${peek(c.io.out)}")

        // Select index 0
        poke(c.io.in_sels(0), 1)
        poke(c.io.in_sels(1), 0)
        println(s"in_sels=${peek(c.io.in_sels)}, out=${peek(c.io.out)}")

        // Select none (invalid)
        poke(c.io.in_sels(0), 0)
        poke(c.io.in_sels(1), 0)
        println(s"in_sels=${peek(c.io.in_sels)}, out=${peek(c.io.out)}")

        // Select both (invalid)
        poke(c.io.in_sels(0), 1)
        poke(c.io.in_sels(1), 1)
        println(s"in_sels=${peek(c.io.in_sels)}, out=${peek(c.io.out)}")
      }
    }
  }

  def runCounter(): Unit = {
    Driver(() => new Module {
      // Example circuit using Mux1H
      val io = IO(new Bundle {
        val count = Input(Bool())
        val out = Output(UInt(2.W))
      })
      val counter = Counter(3) // 3-count Counter (outputs range [0...2])
      when(io.count) {
        counter.inc()
      }
      io.out := counter.value
    }) { c =>
      new PeekPokeTester(c) {
        poke(c.io.count, 1)
        println(s"start: counter value=${peek(c.io.out)}")

        step(1)
        println(s"step 1: counter value=${peek(c.io.out)}")

        step(1)
        println(s"step 2: counter value=${peek(c.io.out)}")

        poke(c.io.count, 0)
        step(1)
        println(s"step without increment: counter value=${peek(c.io.out)}")

        poke(c.io.count, 1)
        step(1)
        println(s"step again: counter value=${peek(c.io.out)}")
      }
    }
  }

  def runAll(): Unit = {
    //runQueue()
    //runArbiter()
    //showArbiter()
    //runPopCount()
    //runReverse()
    runPriorityMux()
    runMux1H()
    runCounter()
  }

  runAll()
}
