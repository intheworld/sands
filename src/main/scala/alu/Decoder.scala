package alu

import chisel3._
import chisel3.util._

class Decoder extends Module {
  val io = IO(new Bundle {
    val result = Output(UInt(4.W))
    val sel = Input(UInt(2.W))
  })
  io.result := 0.U
  switch(io.sel) {
    is ("b00".U) { io.result := "b0001".U }
    is ("b01".U) { io.result := "b0010".U }
    is ("b10".U) { io.result := "b0100".U }
    is ("b11".U) { io.result := "b1000".U }
  }
}
