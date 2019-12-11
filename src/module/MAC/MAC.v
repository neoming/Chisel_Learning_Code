module MAC(
  input        clock,
  input        reset,
  input  [3:0] io_a,
  input  [3:0] io_b,
  input  [3:0] io_c,
  output [7:0] io_out
);
  wire [7:0] _T; // @[MAC.scala 14:19]
  wire [7:0] _GEN_0; // @[MAC.scala 14:27]
  assign _T = io_a * io_b; // @[MAC.scala 14:19]
  assign _GEN_0 = {{4'd0}, io_c}; // @[MAC.scala 14:27]
  assign io_out = _T + _GEN_0; // @[MAC.scala 14:10]
endmodule
