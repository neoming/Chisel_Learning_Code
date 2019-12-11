module MyOperators(
  input        clock,
  input        reset,
  input  [3:0] io_op_a,
  input  [3:0] io_op_b,
  output [3:0] io_out_add,
  output [3:0] io_out_sub,
  output [7:0] io_out_mul
);
  assign io_out_add = io_op_a + io_op_b; // @[MyOperators.scala 17:14]
  assign io_out_sub = io_op_a - io_op_b; // @[MyOperators.scala 18:14]
  assign io_out_mul = io_op_a * io_op_b; // @[MyOperators.scala 19:14]
endmodule
