;buildInfoPackage: chisel3, version: 3.2.1, scalaVersion: 2.12.10, sbtVersion: 1.3.2
circuit ParameterizedAdder : 
  module ParameterizedAdder : 
    input clock : Clock
    input reset : UInt<1>
    output io : {flip in_a : UInt<4>, flip in_b : UInt<4>, out : UInt<4>}
    
    node sum = add(io.in_a, io.in_b) @[ParameterizedAdder.scala 13:21]
    io.out <= sum @[ParameterizedAdder.scala 17:12]
    
