import chisel3._
import chisel3.util._
import chisel3.tester._
import chisel3.tester.RawTester.test

class LastConnect extends Module {
  val io = IO(new Bundle {
    val in = Input(UInt(4.W))
    val out = Output(UInt(4.W))
  })
  io.out := 1.U
  io.out := 2.U
  io.out := 3.U
  io.out := 4.U
}

class Max3 extends Module {
  val io = IO(new Bundle {
    val in1 = Input(UInt(16.W))
    val in2 = Input(UInt(16.W))
    val in3 = Input(UInt(16.W))
    val out = Output(UInt(16.W))
  })
  when (io.in1 >= io.in2 && io.in1 >= io.in3) {
    io.out := io.in1
  }.elsewhen(io.in2 >= io.in3) {
    io.out := io.in2
  }.otherwise {
    io.out := io.in3
  }
}

object Tester {
  def main(args: Array[String]): Unit = {
    test(new LastConnect) {
      c => c.io.out.expect(4.U)
    }
    test(new Max3) { c =>
      c.io.in1.poke(6.U)
      c.io.in2.poke(4.U)
      c.io.in3.poke(2.U)
      c.io.out.expect(6.U)
    }
    println("SUCCESS!!")
  }
}