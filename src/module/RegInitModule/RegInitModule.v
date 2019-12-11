module RegInitModule(
  input         clock,
  input         reset,
  input  [11:0] io_in,
  output [11:0] io_out
);
  reg [11:0] register; // @[RegInitModule.scala 16:27]
  reg [31:0] _RAND_0;
  wire [11:0] _T_1; // @[RegInitModule.scala 17:23]
  assign _T_1 = io_in + 12'h1; // @[RegInitModule.scala 17:23]
  assign io_out = register; // @[RegInitModule.scala 18:12]
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
  register = _RAND_0[11:0];
  `endif // RANDOMIZE_REG_INIT
  `endif // RANDOMIZE
end // initial
`endif // SYNTHESIS
  always @(posedge clock) begin
    if (reset) begin
      register <= 12'h1;
    end else begin
      register <= _T_1;
    end
  end
endmodule
