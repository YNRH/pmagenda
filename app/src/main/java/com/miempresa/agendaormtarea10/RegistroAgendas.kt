package com.miempresa.agendaormtarea10

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.widget.Toast
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_registro_agendas.*
import java.io.ByteArrayOutputStream

class RegistroAgendas : AppCompatActivity() {

    companion object {
        private const val REQUEST_IMAGE_CAPTURE = 1
    }

    var edita: Boolean = false
    var id: Long = -1

    private var selectedImageBitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_agendas)

        val imgFoto: CircleImageView = findViewById(R.id.imgFoto)
        imgFoto.setImageResource(R.drawable.ag1) // Reemplaza "default_image"
        imgFoto.setOnClickListener {
            dispatchTakePictureIntent()
        }

        btnRegistrar.setOnClickListener {
            val nombre = txtNombre.text.toString()
            val correo = txtCorreo.text.toString()
            val telefono = txtTelefono.text.toString()
            val observaciones = txtObservaciones.text.toString()
            val imagenBytes = obtenerImagenBytes(selectedImageBitmap)
            val imagen = imagenBytes ?: "@drawable/ag1"

            if (nombre.isEmpty() || correo.isEmpty() || telefono.isEmpty() || observaciones.isEmpty()) {
                Toast.makeText(this, "Algunos campos están vacíos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val agendaRepositorio = AgendaRepositorio()

            if (edita) {
                agendaRepositorio.actualizar(id, nombre, correo, telefono, observaciones, imagen)
            } else {
                agendaRepositorio.crear(nombre, correo, telefono, observaciones, imagen)
            }
            finish()
        }

        val recibidos: Bundle? = intent.extras
        if (recibidos != null) {
            val agenda = recibidos.get("agenda") as Agenda
            edita = true
            id = agenda.id!!
            txtNombre.setText(agenda.nombre)
            txtCorreo.setText(agenda.correo)
            txtTelefono.setText(agenda.telefono)
            txtObservaciones.setText(agenda.observaciones)
        }
    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            selectedImageBitmap = imageBitmap
            val imgFoto: CircleImageView = findViewById(R.id.imgFoto)
            imgFoto.setImageBitmap(imageBitmap)
        }
    }

    private fun obtenerImagenBytes(bitmap: Bitmap?): String? {
        if (bitmap != null) {
            val outputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            val imageBytes = outputStream.toByteArray()
            return Base64.encodeToString(imageBytes, Base64.DEFAULT)
        }
        return null
    }
}
