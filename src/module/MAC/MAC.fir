;buildInfoPackage: chisel3, version: 3.2.1, scalaVersion: 2.12.10, sbtVersion: 1.3.2
circuit MAC : 
  module MAC : 
    input clock : Clock
    input reset : UInt<1>
    output io : {flip a : UInt<4>, flip b : UInt<4>, flip c : UInt<4>, out : UInt<8>}
    
    node _T = mul(io.a, io.b) @[MAC.scala 14:19]
    node _T_1 = add(_T, io.c) @[MAC.scala 14:27]
    node _T_2 = tail(_T_1, 1) @[MAC.scala 14:27]
    io.out <= _T_2 @[MAC.scala 14:10]
    
