;buildInfoPackage: chisel3, version: 3.2.1, scalaVersion: 2.12.10, sbtVersion: 1.3.2
circuit RegNextModule : 
  module RegNextModule : 
    input clock : Clock
    input reset : UInt<1>
    output io : {flip in : UInt<12>, out : UInt<12>}
    
    node _T = add(io.in, UInt<1>("h01")) @[RegNextModule.scala 17:29]
    node _T_1 = tail(_T, 1) @[RegNextModule.scala 17:29]
    reg _T_2 : UInt, clock @[RegNextModule.scala 17:22]
    _T_2 <= _T_1 @[RegNextModule.scala 17:22]
    io.out <= _T_2 @[RegNextModule.scala 17:12]
    
