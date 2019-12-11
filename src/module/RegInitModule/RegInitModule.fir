;buildInfoPackage: chisel3, version: 3.2.1, scalaVersion: 2.12.10, sbtVersion: 1.3.2
circuit RegInitModule : 
  module RegInitModule : 
    input clock : Clock
    input reset : UInt<1>
    output io : {flip in : UInt<12>, out : UInt<12>}
    
    reg register : UInt<12>, clock with : (reset => (reset, UInt<1>("h01"))) @[RegInitModule.scala 16:27]
    node _T = add(io.in, UInt<1>("h01")) @[RegInitModule.scala 17:23]
    node _T_1 = tail(_T, 1) @[RegInitModule.scala 17:23]
    register <= _T_1 @[RegInitModule.scala 17:14]
    io.out <= register @[RegInitModule.scala 18:12]
    
