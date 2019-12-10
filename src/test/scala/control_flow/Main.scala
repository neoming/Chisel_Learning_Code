
package control_flow

import chisel3.iotesters.Driver

object Main extends App {

  def lastConnectTest(): Unit = {
    printf(chisel3.Driver.emitVerilog(new LastConnect))
    //  Test LastConnect
    val testResult = Driver(() => new LastConnect) { c => new LastConnectTester(c) }
    assert(testResult)
    printf("lastConnect test pass!!\n")
  }

  def max3Test():Unit = {
    printf(chisel3.Driver.emitVerilog(new Max3))
    //  Test LastConnect
    val testResult = Driver(() => new Max3) { c => new Max3Tester(c) }
    assert(testResult)
    printf("max3 test pass!!\n")
  }

  def sort4Test():Unit = {
    printf(chisel3.Driver.emitVerilog(new Sort4))
    //  Test LastConnect
//    val testResult = Driver(() => new Sort4) { c => new Sort4Tester(c) }
    val testResult = Driver(() => new Sort4) { c => new BetterSort4Tester(c) }
    assert(testResult)
    printf("sort4 test pass!!\n")
  }

  def runAllTest():Unit = {
    lastConnectTest()
    max3Test()
    sort4Test()
  }

  sort4Test()
}
