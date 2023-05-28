package com.miempresa.agendaormtarea10

import com.orm.SugarRecord

class AgendaRepositorio {

    fun crear(nombre: String, correo: String, telefono: String, observaciones: String, imagen: String) {
        val ag = Agenda(nombre, correo, telefono, observaciones, imagen)
        SugarRecord.save(ag)
    }

    fun listar(): List<Agenda> {
        val agendas = SugarRecord.listAll(Agenda::class.java)
        return agendas
    }

    fun borrar(id: Long) {
        val ag: Agenda? = SugarRecord.findById(Agenda::class.java, id)
        ag?.let {
            SugarRecord.delete(ag)
        }
    }

    fun leer(id: Long): Agenda? {
        return SugarRecord.findById(Agenda::class.java, id)
    }

    fun actualizar(
        id: Long,
        nombre: String,
        correo: String,
        telefono: String,
        observaciones: String,
        imagen: String?
    ) {
        val agenda = SugarRecord.findById(Agenda::class.java, id)
        if (agenda != null) {
            agenda.nombre = nombre
            agenda.correo = correo
            agenda.telefono = telefono
            agenda.observaciones = observaciones
            agenda.imagen = imagen
            SugarRecord.save(agenda)
        }
    }
}
