;buildInfoPackage: chisel3, version: 3.2.1, scalaVersion: 2.12.10, sbtVersion: 1.3.2
circuit FindMax : 
  module FindMax : 
    input clock : Clock
    input reset : UInt<1>
    output io : {flip in : UInt<4>, max : UInt<4>}
    
    reg max : UInt<4>, clock with : (reset => (reset, UInt<4>("h04"))) @[FindMax.scala 16:22]
    node _T = gt(io.in, max) @[FindMax.scala 17:16]
    when _T : @[FindMax.scala 17:23]
      max <= io.in @[FindMax.scala 18:11]
      skip @[FindMax.scala 17:23]
    io.max <= max @[FindMax.scala 20:12]
    
