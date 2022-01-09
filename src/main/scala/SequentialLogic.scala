import chisel3._
import chisel3.util._
import chisel3.tester._
import chisel3.tester.RawTester.test

class RegisterModule extends Module {
  val io = IO(new Bundle() {
    val in = Input(UInt(12.W))
    val out = Output(UInt(12.W))
  })
  val register = Reg(UInt(12.W))
  register := io.in + 1.U
  io.out := register
}

class FindMax extends Module {
  val io = IO(new Bundle() {
    val in = Input(UInt(10.W))
    val max = Output(UInt(10.W))
  })
  val max = RegInit(0.U(10.W))
  when (io.in > max) {
    max := io.in
  }
  io.max := max
}

object SequentialLogic{
  def main(args: Array[String]): Unit = {
    test(new RegisterModule) { c =>
      for (i <- 0 until 100) {
        c.io.in.poke(i.U)
        c.clock.step(1)
        c.io.out.expect((i + 1).U)
      }
      println(getVerilogString(new RegisterModule))
      println("SUCCESS!!")
    }

    test(new FindMax) { c =>
      c.io.max.expect(0.U)
      c.io.in.poke(1.U)
      c.clock.step(1)
      c.io.max.expect(1.U)
      c.io.in.poke(3.U)
      c.clock.step(1)
      c.io.max.expect(3.U)
      println(getVerilogString(new FindMax))
    }
  }
}