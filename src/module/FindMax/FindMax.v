module FindMax(
  input        clock,
  input        reset,
  input  [3:0] io_in,
  output [3:0] io_max
);
  reg [3:0] max; // @[FindMax.scala 16:22]
  reg [31:0] _RAND_0;
  wire  _T; // @[FindMax.scala 17:16]
  assign _T = io_in > max; // @[FindMax.scala 17:16]
  assign io_max = max; // @[FindMax.scala 20:12]
`ifdef RANDOMIZE_GARBAGE_ASSIGN
`define RANDOMIZE
`endif
`ifdef RANDOMIZE_INVALID_ASSIGN
`define RANDOMIZE
`endif
`ifdef RANDOMIZE_REG_INIT
`define RANDOMIZE
`endif
`ifdef RANDOMIZE_MEM_INIT
`define RANDOMIZE
`endif
`ifndef RANDOM
`define RANDOM $random
`endif
`ifdef RANDOMIZE_MEM_INIT
  integer initvar;
`endif
`ifndef SYNTHESIS
initial begin
  `ifdef RANDOMIZE
    `ifdef INIT_RANDOM
      `INIT_RANDOM
    `endif
    `ifndef VERILATOR
      `ifdef RANDOMIZE_DELAY
        #`RANDOMIZE_DELAY begin end
      `else
        #0.002 begin end
      `endif
    `endif
  `ifdef RANDOMIZE_REG_INIT
  _RAND_0 = {1{`RANDOM}};
  max = _RAND_0[3:0];
  `endif // RANDOMIZE_REG_INIT
  `endif // RANDOMIZE
end // initial
`endif // SYNTHESIS
  always @(posedge clock) begin
    if (reset) begin
      max <= 4'h4;
    end else if (_T) begin
      max <= io_in;
    end
  end
endmodule
