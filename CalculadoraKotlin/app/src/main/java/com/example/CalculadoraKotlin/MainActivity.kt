package com.example.CalculadoraKotlin

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var operador: String = ""
    private var tieneOperador: Boolean = false
    var puntoOp1: Boolean = false
    var puntoOp2: Boolean = false
    var tipoNumero: String = "decimal"
    var girado: Boolean = false

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
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            b_decimal.setOnClickListener(listenerCambiarTipo)
            b_binario.setOnClickListener(listenerCambiarTipo)
            b_hexadecimal.setOnClickListener(listenerCambiarTipo)
            b_a.setOnClickListener(listenerBoton)
            b_b.setOnClickListener(listenerBoton)
            b_c.setOnClickListener(listenerBoton)
            b_d.setOnClickListener(listenerBoton)
            b_e.setOnClickListener(listenerBoton)
            b_f.setOnClickListener(listenerBoton)
            b_a.isEnabled = false
            b_b.isEnabled = false
            b_c.isEnabled = false
            b_d.isEnabled = false
            b_e.isEnabled = false
            b_f.isEnabled = false
            girado = true
        }
    }

    //Listener botones de operaciones
    var listenerBoton = View.OnClickListener {
        var valor : Int = -1
        var hexa : Boolean = false
        if (girado)
            mostrarCambioTipo()
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
            b_a -> {
                anadirHexa("a")
                hexa = true
            }
            b_b -> {
                anadirHexa("b")
                hexa = true
            }
            b_c -> {
                anadirHexa("c")
                hexa = true
            }
            b_d -> {
                anadirHexa("d")
                hexa = true
            }
            b_e -> {
                anadirHexa("e")
                hexa = true
            }
            b_f -> {
                anadirHexa("f")
                hexa = true
            }
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
        if(hexa){
            b_igual.isEnabled = true
        }
    }
    //Listener botones cambiar tipo dato
    var listenerCambiarTipo = View.OnClickListener {
        when(it.id){
            R.id.b_decimal -> {
                if (!tipoNumero.equals("decimal") && textView_result.text.toString().isNotEmpty()) {
                    if(!tieneOperador)
                        textView_result.text = convertirDecimal(textView_result.text.toString())
                    else{
                        textView_result.text = convertirDecimal(textView_result.text.split(operador)[0])+operador+
                                convertirDecimal(textView_result.text.split(operador)[1])
                    }
                }
                botonesDecimal()
                tipoNumero = "decimal"
            }
            R.id.b_binario -> {
                if (!tipoNumero.equals("binario") && textView_result.text.toString().isNotEmpty()) {
                    if(!tieneOperador) {
                        textView_result.text = convertirBinario(textView_result.text.toString())
                    } else {
                        textView_result.text = convertirBinario(textView_result.text.split(operador)[0].toString())+operador+
                                convertirBinario(textView_result.text.split(operador)[1].toString())
                    }
                }
                botonesBinario()
                tipoNumero = "binario"
            }
            R.id.b_hexadecimal -> {
                if (!tipoNumero.equals("hexadecimal") && textView_result.text.toString().isNotEmpty()) {
                    if(!tieneOperador)
                        textView_result.text = convertirHexadecimal(textView_result.text.toString())
                    else
                        textView_result.text = convertirHexadecimal(textView_result.text.split(operador)[0].toString())+operador+
                                convertirHexadecimal(textView_result.text.split(operador)[1].toString())
                }
                botonesHexadecimal()
                tipoNumero = "hexadecimal"
            }
        }
    }
    fun anadirHexa(letra : String){
        if(tipoNumero == "hexadecimal"){
            textView_result.text = textView_result.text.toString().plus(letra)
        }
    }
    fun convertirBinario(numero:String): String {
        when(tipoNumero) {
            "decimal" -> {
                var numeroBinario =numero.toDouble().toInt()
                var binario = Integer.toBinaryString(numeroBinario)
                return binario
            }
            "hexadecimal" -> {
                var numeroBinario = Integer.parseInt(numero, 16)
                var binario = Integer.toBinaryString(numeroBinario)
                return binario
            }
        }
        return ""
    }
    fun ocultarCambioTipo(){
        b_decimal.isEnabled = false
        b_binario.isEnabled = false
        b_hexadecimal.isEnabled = false
    }
    fun mostrarCambioTipo(){
        b_decimal.isEnabled = true
        b_binario.isEnabled = true
        b_hexadecimal.isEnabled = true
    }
    fun botonesBinario(){
        b_0.isEnabled = true
        b_1.isEnabled = true
        b_2.isEnabled = false
        b_3.isEnabled = false
        b_4.isEnabled = false
        b_5.isEnabled = false
        b_6.isEnabled = false
        b_7.isEnabled = false
        b_8.isEnabled = false
        b_9.isEnabled = false
        b_a.isEnabled = false
        b_b.isEnabled = false
        b_c.isEnabled = false
        b_d.isEnabled = false
        b_e.isEnabled = false
        b_f.isEnabled = false
        b_suma.isEnabled = true
        b_resta.isEnabled = true
        b_multi.isEnabled = true
        b_div.isEnabled = true
        b_coma.isEnabled = false
        b_igual.isEnabled = true
        b_limpiar.isEnabled = true
        b_borrar.isEnabled = true
    }
    fun botonesDecimal(){
        b_0.isEnabled = true
        b_1.isEnabled = true
        b_2.isEnabled = true
        b_3.isEnabled = true
        b_4.isEnabled = true
        b_5.isEnabled = true
        b_6.isEnabled = true
        b_7.isEnabled = true
        b_8.isEnabled = true
        b_9.isEnabled = true
        b_a.isEnabled = false
        b_b.isEnabled = false
        b_c.isEnabled = false
        b_d.isEnabled = false
        b_e.isEnabled = false
        b_f.isEnabled = false
        b_suma.isEnabled = true
        b_resta.isEnabled = true
        b_multi.isEnabled = true
        b_div.isEnabled = true
        b_coma.isEnabled = true
        b_igual.isEnabled = true
        b_limpiar.isEnabled = true
        b_borrar.isEnabled = true
    }
    fun botonesHexadecimal(){
        b_0.isEnabled = true
        b_1.isEnabled = true
        b_2.isEnabled = true
        b_3.isEnabled = true
        b_4.isEnabled = true
        b_5.isEnabled = true
        b_6.isEnabled = true
        b_7.isEnabled = true
        b_8.isEnabled = true
        b_9.isEnabled = true
        b_a.isEnabled = true
        b_b.isEnabled = true
        b_c.isEnabled = true
        b_d.isEnabled = true
        b_e.isEnabled = true
        b_f.isEnabled = true
        b_suma.isEnabled = true
        b_resta.isEnabled = true
        b_multi.isEnabled = true
        b_div.isEnabled = true
        b_coma.isEnabled = true
        b_igual.isEnabled = true
        b_limpiar.isEnabled = true
        b_borrar.isEnabled = true
    }
    fun convertirHexadecimal(numero:String): String {
        when(tipoNumero){
            "decimal" -> {
                var numeroBinario = numero.toDouble().toInt()
                var numeroHexadecimal = Integer.toHexString(numeroBinario)
                return numeroHexadecimal
            }
            "binario" -> {
                var numeroBinario = Integer.parseInt(numero,2)
                var numeroHexadecimal = Integer.toHexString(numeroBinario)
                return numeroHexadecimal
            }
        }
        return ""
    }
    fun convertirDecimal(numero:String): String {
        when(tipoNumero){
            "hexadecimal" -> {
                var numerodecimal = Integer.parseInt(numero,16)
                var decimal = Integer.toString(numerodecimal)
                return decimal
            }
            "binario" -> {
                var numerodecimal = Integer.parseInt(numero,2)
                var decimal = Integer.toString(numerodecimal)
                println(decimal)
                return decimal
            }
        }
        return ""
    }
    //Guarda las variables al rotar la pantalla
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("OPERADOR", operador)
        outState.putBoolean("TIENEOPERADOR", tieneOperador)
        outState.putBoolean("PUNTOOP1", puntoOp1)
        outState.putBoolean("PUNTOOP2", puntoOp2)
        outState.putString("TIPONUMERO", tipoNumero)
        outState.putString("TEXTOESCRITO", textView_result.text.toString())
    }
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        textView_result.text = savedInstanceState.getString("TEXTOESCRITO")
        operador = savedInstanceState.getString("OPERADOR").toString()
        tieneOperador = savedInstanceState.getBoolean("TIENEOPERADOR")
        puntoOp1 = savedInstanceState.getBoolean("PUNTOOP1")
        puntoOp2 = savedInstanceState.getBoolean("PUNTOOP2")
        tipoNumero = savedInstanceState.getString("TIPONUMERO").toString()
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            if (!tipoNumero.equals("decimal")){
                if(textView_result.text.isNotEmpty()) {
                    if (!tieneOperador) {
                        textView_result.text = convertirDecimal(textView_result.text.toString())
                        tipoNumero = "decimal"
                    } else {
                        textView_result.text =
                            convertirDecimal(textView_result.text.split(operador)[0]) + operador +
                                    convertirDecimal(textView_result.text.split(operador)[1])
                    }
                }
            }
        }
    }
    fun anadirOperador(op : String){
        var excepciones : String = "+-*/."
        //Si el texto no esta vacio y si el campo anterior no es otro operador o un punto, escribe el operador
        if (textView_result.text.isNotEmpty() && !excepciones.contains(textView_result.text.last())) {
            if(!tieneOperador) {
                tieneOperador = true
                b_igual.isEnabled = false
            } else {
                calcular()
                tieneOperador = true
            }

            textView_result.text = textView_result.text.toString().plus(op)
            operador = op
            if (girado)
                ocultarCambioTipo()
        }

    }
    fun anadirPunto(){
        if(tipoNumero == "decimal") {
            var ultimoChar =
                textView_result.text.toString().get(textView_result.text.toString().length - 1)
            if (!puntoOp1 && !tieneOperador && textView_result.text.isNotEmpty()) {
                puntoOp1 = true
                textView_result.text = textView_result.text.toString().plus(".")
                b_igual.isEnabled = false
                if (girado)
                    ocultarCambioTipo()
            } else if (!puntoOp2 && tieneOperador && ultimoChar.toString() != operador) {
                puntoOp2 = true
                textView_result.text = textView_result.text.toString().plus(".")
                b_igual.isEnabled = false
                if (girado)
                    ocultarCambioTipo()
            }
        }
    }
    fun calcular(){
        if(tipoNumero == "decimal") {
            var operando1 = textView_result.text.toString().substringBeforeLast(operador).toDouble()
            var operando2 = textView_result.text.toString().substringAfterLast(operador).toDouble()
            var result = 0.0
            when (operador) {
                "+" -> result = operando1 + operando2
                "-" -> result = operando1 - operando2
                "/" -> result = operando1 / operando2
                "*" -> result = operando1 * operando2
            }
            //Una vez calculado, comprueba que el resultado no tenga punto para poner la variable en su estado
            //ademas de cambiar las diferentes variables controladoras de valor
            if (result.toString().indexOf('.') != -1)
                puntoOp1 = true
            tieneOperador = false
            b_igual.isEnabled = false
            puntoOp2 = false
            textView_result.text = result.toString()
        }else{
            calcularNoDecimal()
        }
    }
    fun calcularNoDecimal(){
        if(tipoNumero == "binario") {
            val operando1 = convertirDecimal(textView_result.text.toString().substringBeforeLast(operador)).toInt()
            val operando2 = convertirDecimal(textView_result.text.toString().substringAfterLast(operador)).toInt()
            var result = 0
            when (operador) {
                "+" -> result = operando1 + operando2
                "-" -> result = operando1 - operando2
                "/" -> result = operando1 / operando2
                "*" -> result = operando1 * operando2
            }
            //Una vez calculado, comprueba que el resultado no tenga punto para poner la variable en su estado
            //ademas de cambiar las diferentes variables controladoras de valor
            if (result.toString().indexOf('.') != -1)
                puntoOp1 = true
            tieneOperador = false
            b_igual.isEnabled = false
            puntoOp2 = false
            textView_result.text = Integer.toBinaryString(result.toInt())
            puntoOp1 = false
        } else if (tipoNumero == "hexadecimal"){
            val operando1 = convertirDecimal(textView_result.text.toString().substringBeforeLast(operador)).toDouble()
            val operando2 = convertirDecimal(textView_result.text.toString().substringAfterLast(operador)).toDouble()
            var result = 0.0
            when (operador) {
                "+" -> result = operando1 + operando2
                "-" -> result = operando1 - operando2
                "/" -> result = operando1 / operando2
                "*" -> result = operando1 * operando2
            }
            //Una vez calculado, comprueba que el resultado no tenga punto para poner la variable en su estado
            //ademas de cambiar las diferentes variables controladoras de valor
            if (result.toString().indexOf('.') != -1)
                puntoOp1 = true
            tieneOperador = false
            b_igual.isEnabled = false
            puntoOp2 = false
            textView_result.text = Integer.toHexString(result.toInt())
            puntoOp1 = false
        }
    }
    fun limpiar(){
        textView_result.text = ""
        tieneOperador = false
        puntoOp1 = false
        puntoOp2 = false
        operador = ""
        b_igual.isEnabled = false
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