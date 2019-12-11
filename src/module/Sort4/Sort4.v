module Sort4(
  input         clock,
  input         reset,
  input  [15:0] io_in0,
  input  [15:0] io_in1,
  input  [15:0] io_in2,
  input  [15:0] io_in3,
  output [15:0] io_out0,
  output [15:0] io_out1,
  output [15:0] io_out2,
  output [15:0] io_out3
);
  wire  _T; // @[Sort4.scala 22:15]
  wire [15:0] row10; // @[Sort4.scala 22:24]
  wire [15:0] row11; // @[Sort4.scala 22:24]
  wire  _T_1; // @[Sort4.scala 30:15]
  wire [15:0] row12; // @[Sort4.scala 30:24]
  wire [15:0] row13; // @[Sort4.scala 30:24]
  wire  _T_2; // @[Sort4.scala 43:14]
  wire [15:0] row20; // @[Sort4.scala 43:22]
  wire [15:0] row23; // @[Sort4.scala 43:22]
  wire  _T_3; // @[Sort4.scala 51:14]
  wire [15:0] row21; // @[Sort4.scala 51:22]
  wire [15:0] row22; // @[Sort4.scala 51:22]
  wire  _T_4; // @[Sort4.scala 59:14]
  wire  _T_5; // @[Sort4.scala 67:14]
  assign _T = io_in0 > io_in1; // @[Sort4.scala 22:15]
  assign row10 = _T ? io_in1 : io_in0; // @[Sort4.scala 22:24]
  assign row11 = _T ? io_in0 : io_in1; // @[Sort4.scala 22:24]
  assign _T_1 = io_in2 > io_in3; // @[Sort4.scala 30:15]
  assign row12 = _T_1 ? io_in3 : io_in2; // @[Sort4.scala 30:24]
  assign row13 = _T_1 ? io_in2 : io_in3; // @[Sort4.scala 30:24]
  assign _T_2 = row10 > row13; // @[Sort4.scala 43:14]
  assign row20 = _T_2 ? row13 : row10; // @[Sort4.scala 43:22]
  assign row23 = _T_2 ? row10 : row13; // @[Sort4.scala 43:22]
  assign _T_3 = row11 > row12; // @[Sort4.scala 51:14]
  assign row21 = _T_3 ? row12 : row11; // @[Sort4.scala 51:22]
  assign row22 = _T_3 ? row11 : row12; // @[Sort4.scala 51:22]
  assign _T_4 = row20 > row21; // @[Sort4.scala 59:14]
  assign _T_5 = row22 > row23; // @[Sort4.scala 67:14]
  assign io_out0 = _T_4 ? row21 : row20; // @[Sort4.scala 60:13 Sort4.scala 63:13]
  assign io_out1 = _T_4 ? row20 : row21; // @[Sort4.scala 61:13 Sort4.scala 64:13]
  assign io_out2 = _T_5 ? row23 : row22; // @[Sort4.scala 68:13 Sort4.scala 71:13]
  assign io_out3 = _T_5 ? row22 : row23; // @[Sort4.scala 69:13 Sort4.scala 72:13]
endmodule
