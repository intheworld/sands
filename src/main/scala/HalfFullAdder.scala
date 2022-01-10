import chisel3._
import chisel3.util._
import chisel3.tester._
import chisel3.tester.RawTester.test

class HalfFullAdder(val hasCarry: Boolean) extends Module {
  val io = IO(new Bundle() {
    val a = Input(UInt(1.W))
    val b = Input(UInt(1.W))
    val carryIn = if (hasCarry) Some(Input(UInt(1.W))) else None
    val s = Output(UInt(1.W))
    val carryOut = Output(UInt(1.W))
  })
  val sum = io.a +& io.b +& io.carryIn.getOrElse(0.U)
  io.s := sum(0)
  io.carryOut := sum(1)
}

object Option {
  def main(args: Array[String]): Unit = {
    test(new HalfFullAdder(false)) { c =>
      require(!c.hasCarry, "DUT must be half adder")
      // 0 + 0 = 0
      c.io.a.poke(0.U)
      c.io.b.poke(0.U)
      c.io.s.expect(0.U)
      c.io.carryOut.expect(0.U)
    }
    println(getVerilogString(new HalfFullAdder(false)))
  }
}