;buildInfoPackage: chisel3, version: 3.2.1, scalaVersion: 2.12.10, sbtVersion: 1.3.2
circuit MyOperatorTwo : 
  module MyOperatorTwo : 
    input clock : Clock
    input reset : UInt<1>
    output io : {flip select : UInt<1>, flip input_A : UInt<4>, flip input_B : UInt<4>, mux_result : UInt<4>, cat_result : UInt<8>}
    
    node _T = mux(io.select, io.input_A, io.input_B) @[MyOperatorTwo.scala 15:23]
    io.mux_result <= _T @[MyOperatorTwo.scala 15:17]
    node _T_1 = cat(io.input_A, io.input_B) @[Cat.scala 29:58]
    io.cat_result <= _T_1 @[MyOperatorTwo.scala 16:17]
    
