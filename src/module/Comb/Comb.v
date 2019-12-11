module Comb(
  input         clock,
  input         reset,
  input  [11:0] io_in,
  output [11:0] io_out
);
  reg [11:0] delay; // @[Comb.scala 15:26]
  reg [31:0] _RAND_0;
  wire [11:0] _T_1; // @[Comb.scala 17:21]
  assign _T_1 = $signed(io_in) - $signed(delay); // @[Comb.scala 17:21]
  assign io_out = $signed(_T_1); // @[Comb.scala 17:12]
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
  delay = _RAND_0[11:0];
  `endif // RANDOMIZE_REG_INIT
  `endif // RANDOMIZE
end // initial
`endif // SYNTHESIS
  always @(posedge clock) begin
    delay <= io_in;
  end
endmodule
