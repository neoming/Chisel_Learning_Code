module ParameterizedAdder(
  input        clock,
  input        reset,
  input  [3:0] io_in_a,
  input  [3:0] io_in_b,
  output [3:0] io_out
);
  wire [4:0] sum; // @[ParameterizedAdder.scala 13:21]
  assign sum = io_in_a + io_in_b; // @[ParameterizedAdder.scala 13:21]
  assign io_out = sum[3:0]; // @[ParameterizedAdder.scala 17:12]
endmodule
