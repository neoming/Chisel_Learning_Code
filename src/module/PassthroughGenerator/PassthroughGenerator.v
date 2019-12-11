module PassthroughGenerator(
  input   clock,
  input   reset,
  input   io_in,
  output  io_out
);
  assign io_out = io_in; // @[PassthroughGenerator.scala 10:10]
endmodule
