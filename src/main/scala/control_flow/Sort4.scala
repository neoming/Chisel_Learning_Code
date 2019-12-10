
package control_flow
import chisel3._
/*this module is to learn how to use the wire connection using chisel*/
class Sort4 extends Module{
  val io = IO(new Bundle {
    val in0: UInt = Input(UInt(16.W))
    val in1: UInt = Input(UInt(16.W))
    val in2: UInt = Input(UInt(16.W))
    val in3: UInt = Input(UInt(16.W))
    val out0: UInt = Output(UInt(16.W))
    val out1: UInt = Output(UInt(16.W))
    val out2: UInt = Output(UInt(16.W))
    val out3: UInt = Output(UInt(16.W))
  })

  val row10: UInt = Wire(UInt(16.W))
  val row11: UInt = Wire(UInt(16.W))
  val row12: UInt = Wire(UInt(16.W))
  val row13: UInt = Wire(UInt(16.W))

  when(io.in0 > io.in1){
    row10 := io.in1
    row11 := io.in0
  }.otherwise{
    row10 := io.in0
    row11 := io.in1
  }

  when(io.in2 > io.in3){
    row12:= io.in3
    row13 := io.in2
  }.otherwise{
    row12:= io.in2
    row13 := io.in3
  }

  val row20: UInt = Wire(UInt(16.W))
  val row21: UInt = Wire(UInt(16.W))
  val row22: UInt = Wire(UInt(16.W))
  val row23: UInt = Wire(UInt(16.W))

  when(row10 > row13){
    row20 := row13
    row23 := row10
  }.otherwise{
    row20 := row10
    row23 := row13
  }

  when(row11 > row12){
    row21 := row12
    row22 := row11
  }.otherwise{
    row21 := row11
    row22 := row12
  }

  when(row20 > row21){
    io.out0 := row21
    io.out1 := row20
  }.otherwise{
    io.out0 := row20
    io.out1 := row21
  }

  when(row22 > row23){
    io.out2 := row23
    io.out3 := row22
  }.otherwise{
    io.out2 := row22
    io.out3 := row23
  }
}
