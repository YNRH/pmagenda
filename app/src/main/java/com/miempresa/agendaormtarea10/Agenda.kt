package com.miempresa.agendaormtarea10

import com.orm.dsl.Table
import java.io.Serializable

@Table
data class Agenda(
    var id: Long? = null,
    var nombre: String? = null,
    var correo: String? = null,
    var telefono: String? = null,
    var observaciones: String? = null,
    var imagen: String? = null
) : Serializable {
    constructor(
        nombre: String?,
        correo: String?,
        telefono: String?,
        observaciones: String?,
        imagen: String?
    ) : this() {
        this.nombre = nombre
        this.correo = correo
        this.telefono = telefono
        this.observaciones = observaciones
        this.imagen = imagen
    }
}
