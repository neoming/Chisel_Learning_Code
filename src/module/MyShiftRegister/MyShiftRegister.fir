;buildInfoPackage: chisel3, version: 3.2.1, scalaVersion: 2.12.10, sbtVersion: 1.3.2
circuit MyShiftRegister : 
  module MyShiftRegister : 
    input clock : Clock
    input reset : UInt<1>
    output io : {flip in : UInt<1>, out : UInt<4>}
    
    reg state : UInt<4>, clock with : (reset => (reset, UInt<1>("h01"))) @[MyShiftRegister.scala 16:24]
    reg reg1 : UInt<1>, clock with : (reset => (reset, UInt<1>("h01"))) @[MyShiftRegister.scala 18:23]
    reg reg2 : UInt<1>, clock with : (reset => (reset, UInt<1>("h00"))) @[MyShiftRegister.scala 19:23]
    reg reg3 : UInt<1>, clock with : (reset => (reset, UInt<1>("h00"))) @[MyShiftRegister.scala 20:23]
    reg reg4 : UInt<1>, clock with : (reset => (reset, UInt<1>("h00"))) @[MyShiftRegister.scala 21:23]
    reg1 <= io.in @[MyShiftRegister.scala 23:10]
    reg2 <= reg1 @[MyShiftRegister.scala 24:10]
    reg3 <= reg2 @[MyShiftRegister.scala 25:10]
    reg4 <= reg3 @[MyShiftRegister.scala 26:10]
    node _T = cat(reg2, reg1) @[Cat.scala 29:58]
    node _T_1 = cat(reg4, reg3) @[Cat.scala 29:58]
    node _T_2 = cat(_T_1, _T) @[Cat.scala 29:58]
    io.out <= _T_2 @[MyShiftRegister.scala 28:12]
    
