package com.example.formulario

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.formulario.databinding.ActivityMainBinding
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        //lista de paises
        var listaPaises = arrayOf("Argentina","Alemania","Arabia","Colimbia","Costa Rica","Mexico","Marruecos","Venezuela","Bulgaria","España","Portugal")

        var adapter = ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,listaPaises)
        binding.actvPais.setAdapter(adapter)

        binding.actvPais.setOnItemClickListener { adapterView, view, i, l ->
            binding.tieLugarN.requestFocus()
            Toast.makeText(this,listaPaises.get(i),Toast.LENGTH_LONG).show()

        }
        //Fecha
        binding.tieFecha.setOnClickListener {
            val builder = MaterialDatePicker.Builder.datePicker()
            val picker = builder.build()

            picker.addOnPositiveButtonClickListener { timeMili->
                val date = SimpleDateFormat("dd/MM/YYYY", Locale.getDefault() ).apply {
                    timeZone = TimeZone.getTimeZone("UTC")
                }.format(timeMili)
                binding.tieFecha.setText(date)
            }
            picker.show(supportFragmentManager,picker.toString())

        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //Primero detectamos que accion / opcion del menu "da" clic
        if (item.itemId == R.id.action_send){
            //Toast.makeText(this,"Clic en enviar",Toast.LENGTH_SHORT).show()

            // op 1, findviewbyID
            val name: String = findViewById<TextInputEditText>(R.id.tieNombre).text.toString().trim()
            //op 2
            val apellidos = binding.tieApellido.text.toString().trim()
            val altura = binding.tieAltura.text.toString().trim()
            val fecha = binding.tieFecha.text.toString().trim()
            val lugar = binding.tieLugarN.text.toString().trim()

            //Toast.makeText(this,joinData(name,apellidos,altura,fecha,lugar),Toast.LENGTH_SHORT).show()

            //alert Dialog
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)

            builder.setTitle(getString(R.string.confirmar_informacion))
            builder.setMessage(joinData(name,apellidos,altura,fecha,lugar))


            var dialog: AlertDialog = builder.create()
            dialog.show()
            //Programar los botones.

            //btn cancelar, limpiar el fomulario



        }else if(item.itemId == R.id.action_cancel){
            Toast.makeText(this,"CANCELAR",Toast.LENGTH_SHORT).show()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun joinData( vararg campos:String):String{
        var result = ""
        campos.forEach { campo ->
            if(campo.isNotEmpty()){
                result += "$campo \n"
            }
        }

        return result
    }

}







