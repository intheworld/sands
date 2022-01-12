package alu

import chisel3._

class Timer extends Module {
  var io = IO(new Bundle {
    val din = Input(UInt(8.W))
    val load = Input(UInt(1.W))
    val done = Output(UInt(1.W))
  })

  val cntReg = RegInit(0.U(8.W))
  val next = WireInit(0.U)
  io.done := (cntReg === 0.U)
  when (io.load === 1.U) {
    next := io.din
  } .elsewhen(!io.done) {
    next := cntReg - 1.U
  }
  cntReg := next
}
