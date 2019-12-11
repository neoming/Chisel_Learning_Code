;buildInfoPackage: chisel3, version: 3.2.1, scalaVersion: 2.12.10, sbtVersion: 1.3.2
circuit Comb : 
  module Comb : 
    input clock : Clock
    input reset : UInt<1>
    output io : {flip in : SInt<12>, out : SInt<12>}
    
    reg delay : SInt<12>, clock @[Comb.scala 15:26]
    delay <= io.in @[Comb.scala 16:11]
    node _T = sub(io.in, delay) @[Comb.scala 17:21]
    node _T_1 = tail(_T, 1) @[Comb.scala 17:21]
    node _T_2 = asSInt(_T_1) @[Comb.scala 17:21]
    io.out <= _T_2 @[Comb.scala 17:12]
    
