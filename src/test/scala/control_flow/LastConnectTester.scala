
package control_flow

import chisel3.iotesters.PeekPokeTester

class LastConnectTester(c: LastConnect) extends PeekPokeTester(c) {
  expect(c.io.out, 4)  // Assert that the output correctly has 4
}