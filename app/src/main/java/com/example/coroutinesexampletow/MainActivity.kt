package com.example.coroutinesexampletow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*
import kotlin.Exception
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity() {

    val TAG = "giangtd"

    var job1: Job? = null

    var job2: Job = SupervisorJob()
    val myScop = CoroutineScope(Dispatchers.Main + job2)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val cancel: TextView = findViewById(R.id.cancel)
        val start: TextView = findViewById(R.id.start)


        start.setOnClickListener {
            lifecycleScope.launch {
//                job1 = scop1()
//                job1?.join()
                job2 = scop2()
            }
        }
        
        cancel.setOnClickListener {             // cancel 1 coroutines
//            job1?.cancel()                     // cách 1
            job2.cancelChildren()               // cách 2
            Log.d(TAG, "onCreate: cancel")
        }

//        Log.d(TAG, "onCreate: start")                           // hỏi lại
//        CoroutineScope(Dispatchers.Main).launch {
//            delay(2000)
//            Log.d(TAG, "onCreate: coroutines")
//        }
//        Thread.sleep(3000)
//        Log.d(TAG, "onCreate: end")
        
    }

    fun scop1() = lifecycleScope.launch(Dispatchers.Default) {      // scop của lifecycle
        val a = withContext(Dispatchers.Default) { sumA() }
        val b = sumB()
        val c = async { sumC() }
        val d = async(Dispatchers.Default) { sumD() }
        sum(a, b, c.await(), d.await())
    }

    fun scop2() = myScop.launch(Dispatchers.Default) {
        try {
            val a = withContext(Dispatchers.Default) { sumA() }
            val b = sumB()
            val c = async { sumC() }
            val d = async(Dispatchers.Default) { sumD() }
            sum(a, b, c.await(), d.await())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun sumA(): Int {
//        try {                                                                     // cần hỏi: bắt try carch ở đây được không
        delay(1000)
        Log.d(TAG, "sumA: ")
        return 1
//        }catch (e: Exception){
//            e.printStackTrace()
//        }finally {
//            return -1
//        }
    }

    suspend fun sumB(): Int = withContext(Dispatchers.Default) {                    // cần hỏi: 2 withcontxet có khác nhau không
        delay(1000)
        Log.d(TAG, "sumB: ")
        2
    }

    suspend fun sumC(): Int = withContext(Dispatchers.Default) {
        delay(1000)
        Log.d(TAG, "sumC: ")
        3
    }

    suspend fun sumD(): Int {
        delay(1000)
        Log.d(TAG, "sumD: ")
        return 4
    }

    fun sum(a: Int, b: Int, c: Int, d: Int) {
        val result = a + b + c + d
        Log.d(TAG, "sum: a + b: " + result)
    }
}