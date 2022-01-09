import chisel3._
import chisel3.util._
import chisel3.tester._
import chisel3.tester.RawTester.test

class Passthrough extends Module {
  val io = IO(new Bundle {
    val in = Input(UInt(4.W))
    val out = Output(UInt(4.W))
  })
  val two  = 1 + 1
  println(two)
  val utwo = 1.U + 1.U
  println(utwo)

  io.out := io.in
}

class PrintingModule extends Module {
  val io = IO(new Bundle {
    val in = Input(UInt(4.W))
    val out = Output(UInt(4.W))
  })
  io.out := io.in

  printf("Print during simulation: Input is %d\n", io.in)
  // chisel printf has its own string interpolator too
  printf(p"Print during simulation: IO is $io\n")

  println(s"Print during generation: Input is ${io.in}")
}

class MyOperators extends Module {
  val io = IO(new Bundle() {
    val in = Input(UInt(4.W))
    val out_add = Output(UInt(4.W))
    val out_sub = Output(UInt(4.W))
    val out_mul = Output(UInt(4.W))
  })
  io.out_add := 1.U + 4.U
  io.out_sub := 2.U - 1.U
  io.out_mul := 4.U * 2.U
}

class MyOperatorsTwo extends Module {
  val io = IO(new Bundle {
    val in = Input(UInt(4.W))
    val out_mux = Output(UInt(4.W))
    val out_cat = Output(UInt(4.W))
  })

  val s = true.B
  io.out_mux := Mux(s, 3.U, 0.U)
  io.out_cat := Cat(2.U, 1.U)
}

class MAC extends Module {
  val io = IO(new Bundle() {
    val in_a = Input(UInt(4.W))
    val in_b = Input(UInt(4.W))
    val in_c = Input(UInt(4.W))
    val out = Output(UInt(8.W))
  })
  io.out := io.in_a * io.in_b + io.in_c
}

object Main {
  def main(args: Array[String]): Unit = {
//    test(new PrintingModule ) { c =>
//      c.io.in.poke(3.U)
//      c.clock.step(5)
//      println(s"Print during testing: Input ${c.io.peek()}")
//    }
    println(getVerilogString(new MyOperators))

    test(new MyOperators) {c =>
      c.io.out_add.expect(5.U)
      c.io.out_sub.expect(1.U)
      c.io.out_mul.expect(8.U)
    }

    println(getVerilogString(new MyOperatorsTwo))

    test(new MyOperatorsTwo) { c =>
      c.io.out_mux.expect(3.U)
      c.io.out_cat.expect(5.U)
    }

    println(getVerilogString(new MAC))

    test(new MAC) { c =>
      val cycles = 100
      import scala.util.Random
      for (i <- 0 until cycles) {
        val in_a = Random.nextInt(16)
        val in_b = Random.nextInt(16)
        val in_c = Random.nextInt(16)
        c.io.in_a.poke(in_a.U)
        c.io.in_b.poke(in_b.U)
        c.io.in_c.poke(in_c.U)
        c.io.out.expect((in_a * in_b + in_c).U)
      }
    }
    println("SUCCESS!!")
  }
}