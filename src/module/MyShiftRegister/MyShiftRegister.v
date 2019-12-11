module MyShiftRegister(
  input        clock,
  input        reset,
  input        io_in,
  output [3:0] io_out
);
  reg  reg1; // @[MyShiftRegister.scala 18:23]
  reg [31:0] _RAND_0;
  reg  reg2; // @[MyShiftRegister.scala 19:23]
  reg [31:0] _RAND_1;
  reg  reg3; // @[MyShiftRegister.scala 20:23]
  reg [31:0] _RAND_2;
  reg  reg4; // @[MyShiftRegister.scala 21:23]
  reg [31:0] _RAND_3;
  wire [1:0] _T; // @[Cat.scala 29:58]
  wire [1:0] _T_1; // @[Cat.scala 29:58]
  assign _T = {reg2,reg1}; // @[Cat.scala 29:58]
  assign _T_1 = {reg4,reg3}; // @[Cat.scala 29:58]
  assign io_out = {_T_1,_T}; // @[MyShiftRegister.scala 28:12]
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
  reg1 = _RAND_0[0:0];
  `endif // RANDOMIZE_REG_INIT
  `ifdef RANDOMIZE_REG_INIT
  _RAND_1 = {1{`RANDOM}};
  reg2 = _RAND_1[0:0];
  `endif // RANDOMIZE_REG_INIT
  `ifdef RANDOMIZE_REG_INIT
  _RAND_2 = {1{`RANDOM}};
  reg3 = _RAND_2[0:0];
  `endif // RANDOMIZE_REG_INIT
  `ifdef RANDOMIZE_REG_INIT
  _RAND_3 = {1{`RANDOM}};
  reg4 = _RAND_3[0:0];
  `endif // RANDOMIZE_REG_INIT
  `endif // RANDOMIZE
end // initial
`endif // SYNTHESIS
  always @(posedge clock) begin
    reg1 <= reset | io_in;
    if (reset) begin
      reg2 <= 1'h0;
    end else begin
      reg2 <= reg1;
    end
    if (reset) begin
      reg3 <= 1'h0;
    end else begin
      reg3 <= reg2;
    end
    if (reset) begin
      reg4 <= 1'h0;
    end else begin
      reg4 <= reg3;
    end
  end
endmodule
