;buildInfoPackage: chisel3, version: 3.2.1, scalaVersion: 2.12.10, sbtVersion: 1.3.2
circuit RegisterModule : 
  module RegisterModule : 
    input clock : Clock
    input reset : UInt<1>
    output io : {flip in : UInt<12>, out : UInt<12>}
    
    reg register : UInt<12>, clock @[RegisterModule.scala 16:23]
    node _T = add(io.in, UInt<1>("h01")) @[RegisterModule.scala 17:23]
    node _T_1 = tail(_T, 1) @[RegisterModule.scala 17:23]
    register <= _T_1 @[RegisterModule.scala 17:14]
    io.out <= register @[RegisterModule.scala 18:12]
    
