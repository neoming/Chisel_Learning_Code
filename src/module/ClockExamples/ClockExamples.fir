;buildInfoPackage: chisel3, version: 3.2.1, scalaVersion: 2.12.10, sbtVersion: 1.3.2
circuit ClockExamples : 
  module ClockExamples : 
    input clock : Clock
    input reset : UInt<1>
    output io : {flip in : UInt<10>, flip alternateReset : UInt<1>, flip alternateClock : Clock, outImplicit : UInt, outAlternateReset : UInt, outAlternateClock : UInt, outAlternateBoth : UInt}
    
    reg imp : UInt<10>, clock with : (reset => (reset, UInt<10>("h00"))) @[ClockExamples.scala 24:22]
    imp <= io.in @[ClockExamples.scala 25:9]
    io.outImplicit <= imp @[ClockExamples.scala 26:20]
    reg _T : UInt<10>, clock with : (reset => (io.alternateReset, UInt<10>("h00"))) @[ClockExamples.scala 30:27]
    _T <= io.in @[ClockExamples.scala 31:14]
    io.outAlternateReset <= _T @[ClockExamples.scala 32:28]
    reg _T_1 : UInt<10>, io.alternateClock with : (reset => (reset, UInt<10>("h00"))) @[ClockExamples.scala 36:27]
    _T_1 <= io.in @[ClockExamples.scala 37:14]
    io.outAlternateClock <= _T_1 @[ClockExamples.scala 38:28]
    reg _T_2 : UInt<10>, io.alternateClock with : (reset => (io.alternateReset, UInt<10>("h00"))) @[ClockExamples.scala 42:24]
    _T_2 <= io.in @[ClockExamples.scala 43:11]
    io.outAlternateBoth <= _T_2 @[ClockExamples.scala 44:27]
    
