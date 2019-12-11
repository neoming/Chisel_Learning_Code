;buildInfoPackage: chisel3, version: 3.2.1, scalaVersion: 2.12.10, sbtVersion: 1.3.2
circuit MyOperators : 
  module MyOperators : 
    input clock : Clock
    input reset : UInt<1>
    output io : {flip op_a : UInt<4>, flip op_b : UInt<4>, out_add : UInt<4>, out_sub : UInt<4>, out_mul : UInt<8>}
    
    node _T = add(io.op_a, io.op_b) @[MyOperators.scala 17:25]
    node _T_1 = tail(_T, 1) @[MyOperators.scala 17:25]
    io.out_add <= _T_1 @[MyOperators.scala 17:14]
    node _T_2 = sub(io.op_a, io.op_b) @[MyOperators.scala 18:25]
    node _T_3 = tail(_T_2, 1) @[MyOperators.scala 18:25]
    io.out_sub <= _T_3 @[MyOperators.scala 18:14]
    node _T_4 = mul(io.op_a, io.op_b) @[MyOperators.scala 19:25]
    io.out_mul <= _T_4 @[MyOperators.scala 19:14]
    
