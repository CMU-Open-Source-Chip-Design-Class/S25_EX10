// Spicy Macros necessairy to make scala-cli work :)

//> using scala "2.13.12"
//> using dep "org.chipsalliance::chisel::6.2.0"
//> using plugin "org.chipsalliance:::chisel-plugin::6.2.0"
//> using options "-unchecked", "-deprecation", "-language:reflectiveCalls", "-feature", "-Xcheckinit", "-Xfatal-warnings", "-Ywarn-dead-code", "-Ywarn-unused", "-Ymacro-annotations"

// Importing the Chisel Library and other modules. Remember, 
// we are writing Scala code here :)

import chisel3._
import _root_.circt.stage.ChiselStage
import chisel3.experimental.BundleLiterals._
import chisel3.simulator.EphemeralSimulator._
import chisel3.util._



class Neuron_Generator (num_of_inputs : Int, width : Int, activation_fn : SInt => SInt) extends Module {
    val io = IO(new Bundle {
        val inputs  = Input(Vec(num_of_inputs, UInt(width.W)))
        val weights = Input(Vec(num_of_inputs, UInt(width.W)))
        val out     = Output(UInt((2*width).W))
        val accum   = Output(UInt((2*width).W))
    })

        // TODO: Write your code here!
}


object Main extends App {
    val ReLU : SInt => SInt = x => // Write your code here!
    val Step : SInt => SInt = x => // Write your code here!

    println(
        ChiselStage.emitSystemVerilog(
            gen = new Neuron_Generator(4, 8, ReLU),
            firtoolOpts = Array("-disable-all-randomization", "-strip-debug-info")
        )
    )
}