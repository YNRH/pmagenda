package com.miempresa.agendaormtarea10

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lista.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        lista.layoutManager = LinearLayoutManager(this);

        //obtenemos lista de datos guardados en SugarORM
        var agendarepo = AgendaRepositorio()
        var listaAngenda = agendarepo.listar()

        val adapter = AdaptadorAgenda(listaAngenda as ArrayList<Agenda>)
        lista.adapter = adapter

        btnAgregar.setOnClickListener {
            val intent = Intent(this, RegistroAgendas::class.java)
            startActivity(intent)
        }
    }

    override fun onRestart() {
        super.onRestart()
        var agendarepo = AgendaRepositorio()
        var listaAgendas = agendarepo.listar()

        val adapter = AdaptadorAgenda(listaAgendas as ArrayList<Agenda>)
        lista.adapter = adapter
    }
}