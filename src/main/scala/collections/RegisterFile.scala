
package collections

import chisel3._

class RegisterFile(readPorts: Int) extends Module {
  require(readPorts >= 0)
  val io = IO(new Bundle {
    val wen   = Input(Bool())
    val waddr = Input(UInt(5.W))
    val wdata = Input(UInt(32.W))
    val raddr = Input(Vec(readPorts, UInt(5.W)))
    val rdata = Output(Vec(readPorts, UInt(32.W)))
  })

  // A Register of a vector of UInts
  val reg = RegInit(VecInit(Seq.fill(32)(0.U(32.W))))

  when(io.wen){
    reg(io.waddr) := io.wdata
  }
  for(i <- 0 until readPorts){
    when(io.raddr(i) === 0.U){
      io.rdata(i) := 0.U
    }.otherwise{
      io.rdata(i) := reg(io.raddr(i))
    }
  }
}

object Run extends App{
  chisel3.Driver.emitVerilog(new RegisterFile(1))
}