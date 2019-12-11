;buildInfoPackage: chisel3, version: 3.2.1, scalaVersion: 2.12.10, sbtVersion: 1.3.2
circuit LastConnect : 
  module LastConnect : 
    input clock : Clock
    input reset : UInt<1>
    output io : {flip in : UInt<4>, out : UInt<4>}
    
    io.out <= UInt<1>("h01") @[LastConnect.scala 14:10]
    io.out <= UInt<2>("h02") @[LastConnect.scala 15:10]
    io.out <= UInt<2>("h03") @[LastConnect.scala 16:10]
    io.out <= UInt<3>("h04") @[LastConnect.scala 17:10]
    
