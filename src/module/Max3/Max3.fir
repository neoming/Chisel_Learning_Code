;buildInfoPackage: chisel3, version: 3.2.1, scalaVersion: 2.12.10, sbtVersion: 1.3.2
circuit Max3 : 
  module Max3 : 
    input clock : Clock
    input reset : UInt<1>
    output io : {flip in1 : UInt<16>, flip in2 : UInt<16>, flip in3 : UInt<16>, out : UInt<16>}
    
    node _T = gt(io.in1, io.in2) @[Max3.scala 14:15]
    node _T_1 = gt(io.in1, io.in3) @[Max3.scala 14:34]
    node _T_2 = and(_T, _T_1) @[Max3.scala 14:24]
    when _T_2 : @[Max3.scala 14:44]
      io.out <= io.in1 @[Max3.scala 15:12]
      skip @[Max3.scala 14:44]
    else : @[Max3.scala 16:50]
      node _T_3 = gt(io.in2, io.in1) @[Max3.scala 16:21]
      node _T_4 = gt(io.in2, io.in3) @[Max3.scala 16:40]
      node _T_5 = and(_T_3, _T_4) @[Max3.scala 16:30]
      when _T_5 : @[Max3.scala 16:50]
        io.out <= io.in2 @[Max3.scala 17:12]
        skip @[Max3.scala 16:50]
      else : @[Max3.scala 18:15]
        io.out <= io.in3 @[Max3.scala 19:12]
        skip @[Max3.scala 18:15]
    
