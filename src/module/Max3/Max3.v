module Max3(
  input         clock,
  input         reset,
  input  [15:0] io_in1,
  input  [15:0] io_in2,
  input  [15:0] io_in3,
  output [15:0] io_out
);
  wire  _T; // @[Max3.scala 14:15]
  wire  _T_1; // @[Max3.scala 14:34]
  wire  _T_2; // @[Max3.scala 14:24]
  wire  _T_3; // @[Max3.scala 16:21]
  wire  _T_4; // @[Max3.scala 16:40]
  wire  _T_5; // @[Max3.scala 16:30]
  wire [15:0] _GEN_0; // @[Max3.scala 16:50]
  assign _T = io_in1 > io_in2; // @[Max3.scala 14:15]
  assign _T_1 = io_in1 > io_in3; // @[Max3.scala 14:34]
  assign _T_2 = _T & _T_1; // @[Max3.scala 14:24]
  assign _T_3 = io_in2 > io_in1; // @[Max3.scala 16:21]
  assign _T_4 = io_in2 > io_in3; // @[Max3.scala 16:40]
  assign _T_5 = _T_3 & _T_4; // @[Max3.scala 16:30]
  assign _GEN_0 = _T_5 ? io_in2 : io_in3; // @[Max3.scala 16:50]
  assign io_out = _T_2 ? io_in1 : _GEN_0; // @[Max3.scala 15:12 Max3.scala 17:12 Max3.scala 19:12]
endmodule
