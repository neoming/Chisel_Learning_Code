;buildInfoPackage: chisel3, version: 3.2.1, scalaVersion: 2.12.10, sbtVersion: 1.3.2
circuit PassthroughGenerator : 
  module PassthroughGenerator : 
    input clock : Clock
    input reset : UInt<1>
    output io : {flip in : UInt<1>, out : UInt<1>}
    
    io.out <= io.in @[PassthroughGenerator.scala 10:10]
    
