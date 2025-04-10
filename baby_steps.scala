// Spicy Macros necessary to make scala-cli work :)

//> using scala "2.13.12"
//> using dep "org.chipsalliance::chisel::6.7.0"
//> using plugin "org.chipsalliance:::chisel-plugin::6.7.0"
//> using options "-unchecked" "-deprecation" "-language:reflectiveCalls" "-feature" "-Xcheckinit" "-Xfatal-warnings" "-Ywarn-dead-code" "-Ywarn-unused" "-Ymacro-annotations"

// Importing the Chisel Library and other modules. Remember, 
// we are writing Scala code here :)

import chisel3._
import _root_.circt.stage.ChiselStage
import chisel3.experimental.BundleLiterals._
import chisel3.simulator.EphemeralSimulator._


// Module Header. width here works like a System Verilog 
// parameter

class ALU_Generator (width: Int) extends Module {
    val io = IO( new Bundle {
        val op1, op2 = Input(UInt(width.W))
        val select   = Input(UInt(4.W))
        val out      = Output(UInt(width.W))
        val error    = Output(Bool())
    })

    // TODO: Write your code here!

    // Hints:
    // -> val a := b means "a is connected to b"
    // -> val reg = Reg() instantiates a register
    // -> when, elsewhen, and otherwise work like if-else statements

}


// Main function, DO NOT EDIT
object Main extends App {

    // Printing the System Verilog representation
    // of the circuit
    println(
        ChiselStage.emitSystemVerilog(
        gen = new ALU_Generator(4),
        firtoolOpts = Array("-disable-all-randomization", "-strip-debug-info")
        )
    )

    // A non-exhaustive testbench for your design.
    // The syntax here is a bit weird. Don't worry, you don't need
    // to change anything. However, here are some interesting things 
    // to note:
    //
    // ---> poke(value) can be used to set inputs of your circuit to specific values
    // ---> step(n) can be used to move the clock by n cycles
    // ---> peek() returns the value of selected signal / val

    simulate (new ALU_Generator(16)) { c =>

        println("Starting Tests...")
        // Setting up register values 
        // and stepping clock
        c.io.op1.poke(5.U)
        c.io.op2.poke(3.U)
        c.io.select.poke(1.U)
        c.clock.step(1)


        // Test for addition
        var out = c.io.out.peek().litValue
        var error = c.io.error.peek().litToBoolean
        if (out != 8) {println("Incorrect addition")}
        if (error != false) println("Unexpected error in addition")


        // Test for bitwise OR
        c.io.select.poke(2.U)
        out = c.io.out.peek().litValue
        error = c.io.error.peek().litToBoolean
        if (out != 7)   println("Incorrect OR")
        if (error != false) println("Unexpected error in OR")    

        // Test for bitwise XOR
        c.io.select.poke(4.U)
        out = c.io.out.peek().litValue
        error = c.io.error.peek().litToBoolean
        if (out != 6)   println("Incorrect XOR")
        if (error != false) println("Unexpected error in XOR")  

        // Test for bitwise AND
        c.io.select.poke(8.U)
        out = c.io.out.peek().litValue
        error = c.io.error.peek().litToBoolean
        if (out != 1)   println("Incorrect AND")
        if (error != false) println("Unexpected error in AND")  

        // Test for error output
        c.io.select.poke(9.U)
        error = c.io.error.peek().litToBoolean
        if (error != true) println("Expected error") 

        println("Tests DONE!")
    }
}

