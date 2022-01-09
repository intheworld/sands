import chisel3._
import chisel3.util._
import chisel3.tester._
import chisel3.tester.RawTester.test


class My4ElementFir(b0: Int, b1: Int, b2: Int, b3: Int) extends Module {
  val io = IO(new Bundle {
    val in = Input(UInt(8.W))
    val out = Output(UInt(8.W))
  })
  val x_1 = RegNext(io.in, 0.U)
  val x_2 = RegNext(x_1, 0.U)
  val x_3 = RegNext(x_2, 0.U)

  io.out := io.in * b0.U(8.W) +
    x_1 * b1.U(8.W) +
    x_2 * b2.U(8.W) +
    x_3 * b3.U(8.W)
}

object FIR {
  def main(args: Array[String]): Unit = {
    test(new My4ElementFir(1, 1, 1, 1)) { c =>
      c.io.in.poke(1.U)
      c.io.out.expect(1.U) // 1, 0, 0, 0
      c.clock.step(1)
      c.io.in.poke(4.U)
      c.io.out.expect(5.U) // 4, 1, 0, 0
      c.clock.step(1)
      c.io.in.poke(3.U)
      c.io.out.expect(8.U) // 3, 4, 1, 0
      c.clock.step(1)
      c.io.in.poke(2.U)
      c.io.out.expect(10.U) // 2, 3, 4, 1
      c.clock.step(1)
      c.io.in.poke(7.U)
      c.io.out.expect(16.U) // 7, 2, 3, 4
      c.clock.step(1)
      c.io.in.poke(0.U)
      c.io.out.expect(12.U) // 0, 7, 2, 3    }
    }
    println(getVerilogString(new My4ElementFir(1, 1, 1, 1)))
  }
}