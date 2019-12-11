
package comb_logic

import chisel3.assert
import chisel3.iotesters.Driver

object combinationalLogicMain extends App {

  def testMyOperators(): Unit = {
    println(chisel3.Driver.emitVerilog(new MyOperators))
    val testResult = Driver(() => new MyOperators) { c => new MyOperatorsTester(c) }
    assert(testResult)
    printf("MyOperators test pass!\n")
  }

  def testMyOperatorsTwo(): Unit = {
    println(chisel3.Driver.emitVerilog(new MyOperatorTwo))
    val testResult = Driver(() => new MyOperatorTwo) { c => new MyOperatorTwoTester(c) }
    assert(testResult)
    printf("MyOperators test pass!\n")
  }


  def testMac(): Unit = {
    println(chisel3.Driver.emitVerilog(new MAC))
    val testResult = Driver(() => new MAC) { c => new MACTester(c) }
    assert(testResult)
    printf("MAC test pass!\n")
  }

  def testParameterizedAdder(): Unit = {
    for (b: Boolean <- Seq(true, false)) {
      println(chisel3.Driver.emitVerilog(new ParameterizedAdder(b)))
      val testResult = Driver(() => new ParameterizedAdder(b)){ c => new ParameterizedAdderTester(c,b)}
      assert(testResult)
      printf("ParameterizedAdder test pass!\n")
    }
  }

  def runAllTest():Unit = {
    testMyOperatorsTwo()
    testMyOperators()
    testMac()
    testParameterizedAdder()
  }

  runAllTest()
}
