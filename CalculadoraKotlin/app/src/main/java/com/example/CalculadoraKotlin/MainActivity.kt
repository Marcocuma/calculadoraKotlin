package com.example.CalculadoraKotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var operador: String = ""
    private var tieneOperador: Boolean = false
    var puntoOp1: Boolean = false
    var puntoOp2: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        b_0.setOnClickListener(listenerBoton)
        b_1.setOnClickListener(listenerBoton)
        b_2.setOnClickListener(listenerBoton)
        b_3.setOnClickListener(listenerBoton)
        b_4.setOnClickListener(listenerBoton)
        b_5.setOnClickListener(listenerBoton)
        b_6.setOnClickListener(listenerBoton)
        b_7.setOnClickListener(listenerBoton)
        b_8.setOnClickListener(listenerBoton)
        b_9.setOnClickListener(listenerBoton)
        b_suma.setOnClickListener(listenerBoton)
        b_resta.setOnClickListener(listenerBoton)
        b_multi.setOnClickListener(listenerBoton)
        b_div.setOnClickListener(listenerBoton)
        b_coma.setOnClickListener(listenerBoton)
        b_igual.setOnClickListener(listenerBoton)
        b_igual.isEnabled = false
        b_limpiar.setOnClickListener(listenerBoton)
        b_borrar.setOnClickListener(listenerBoton)
    }
    var listenerBoton = View.OnClickListener {
        var valor : Int = -1
        when(it) {
            b_0 -> valor = 0
            b_1 -> valor = 1
            b_2 -> valor = 2
            b_3 -> valor = 3
            b_4 -> valor = 4
            b_5 -> valor = 5
            b_6 -> valor = 6
            b_7 -> valor = 7
            b_8 -> valor = 8
            b_9 -> valor = 9
            b_suma -> anadirOperador("+")
            b_resta -> anadirOperador("-")
            b_multi -> anadirOperador("*")
            b_div -> anadirOperador("/")
            b_coma -> anadirPunto()
            b_igual -> calcular()
            b_limpiar -> limpiar()
            b_borrar -> borrar()
        }
        if (valor != -1) {
            textView_result.text = textView_result.text.toString().plus(valor)
            if(tieneOperador)
                b_igual.isEnabled = true
        }
    }
    fun anadirOperador(op : String){
        var excepciones : String = "+-*/."
        //Si el texto no esta vacio y si el campo anterior no es otro operador o un punto, escribe el operador
        if (textView_result.text.isNotEmpty() && !excepciones.contains(textView_result.text.last())) {
            if(!tieneOperador) {
                tieneOperador = true
            } else {
                calcular()
                tieneOperador = true
            }

            textView_result.text = textView_result.text.toString().plus(op)
            operador = op
        }

    }
    fun anadirPunto(){
        var ultimoChar = textView_result.text.toString().get(textView_result.text.toString().length-1)
        if (!puntoOp1 && !tieneOperador && textView_result.text.isNotEmpty()) {
            puntoOp1 = true
            textView_result.text = textView_result.text.toString().plus(".")
        }else if (!puntoOp2 && tieneOperador && ultimoChar.toString() != operador) {
            puntoOp2 = true
            textView_result.text = textView_result.text.toString().plus(".")
        }
    }
    fun calcular(){
        var operando1 = textView_result.text.toString().substringBeforeLast(operador).toDouble()
        var operando2 = textView_result.text.toString().substringAfterLast(operador).toDouble()
        var result = 0.0
        when(operador) {
            "+" -> result = operando1 + operando2
            "-" -> result = operando1 - operando2
            "/" -> result = operando1 / operando2
            "*" -> result = operando1 * operando2
        }
        //Una vez calculado, comprueba que el resultado no tenga punto para poner la variable en su estado
        //ademas de cambiar las diferentes variables controladoras de valor
        if(result.toString().indexOf('.') != -1)
            puntoOp1 = true
        tieneOperador = false
        b_igual.isEnabled = false
        puntoOp2 = false
        textView_result.text = result.toString()
    }
    fun limpiar(){
        textView_result.text = ""
        tieneOperador = false
        puntoOp1 = false
        puntoOp2 = false
        operador = ""
    }
    fun borrar(){
        if(textView_result.text.toString().isNotEmpty()) {
            if (textView_result.text.toString().length >= 2) {
                var operadores: String = "+-*/"
                var valorAnterior =
                    textView_result.text.toString().get(textView_result.text.toString().length - 1)
                var valorAnteriorAnterior =
                    textView_result.text.toString().get(textView_result.text.toString().length - 2)
                //Si el caracter borrado es un punto, comprueba de que operador era
                if (valorAnterior == '.')
                    if (tieneOperador)
                        puntoOp2 = false
                    else
                        puntoOp1 = false
                //Si es un operador, cambia su booleano
                else if (operadores.indexOf(valorAnterior) != -1)
                    tieneOperador = false
                //Si el valor anterior al borrado es un operador o un punto, desactiva la tecla igual
                else if (operadores.indexOf(valorAnteriorAnterior) != -1 || valorAnteriorAnterior == '.')
                    b_igual.isEnabled = false
            }
            textView_result.text = textView_result.text.toString().substring(0,textView_result.text.toString().length-1)
        }
    }
}