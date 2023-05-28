package com.miempresa.agendaormtarea10

import android.content.Intent
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import java.io.Serializable

class AdaptadorAgenda(val listaAgendas: ArrayList<Agenda>) : RecyclerView.Adapter<AdaptadorAgenda.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val fNombre: TextView = itemView.findViewById(R.id.lblNombre)
        val fCorreo: TextView = itemView.findViewById(R.id.lblCorreo)
        val fTelefono: TextView = itemView.findViewById(R.id.lblTelefono)
        val fImagen: ImageView = itemView.findViewById(R.id.imgFoto)
        val fEliminar: ImageButton = itemView.findViewById(R.id.btnEliminar)
        val fEditar: ImageButton = itemView.findViewById(R.id.btnEditar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.vista_agenda, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var ag = this.listaAgendas.get(position)
        holder.fNombre.text = listaAgendas[position].nombre
        holder.fCorreo.text = listaAgendas[position].correo
        holder.fTelefono.text = listaAgendas[position].telefono

        val imgFoto: CircleImageView = holder.itemView.findViewById(R.id.imgFoto)

        if (ag.imagen != null) {
            val imageBytes = Base64.decode(ag.imagen, Base64.DEFAULT)
            val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
            Glide.with(holder.itemView.context).load(bitmap).into(imgFoto)
        } else {
            imgFoto.setImageResource(R.drawable.ic_launcher_background)
        }

        holder.fEliminar.setOnClickListener(){
            var agrepo = AgendaRepositorio()
            agrepo.borrar(ag.id!!)
            this.listaAgendas.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, itemCount)
        }

        holder.fEditar.setOnClickListener(){
            var agrepo = AgendaRepositorio()
            var agenda = agrepo.leer(ag.id!!)

            var editarAgenda = Intent(holder.itemView.context, RegistroAgendas::class.java)
            editarAgenda.putExtra("agenda", agenda as Serializable)
            holder.itemView.context.startActivity(editarAgenda)
        }
    }

    override fun getItemCount(): Int {
        return listaAgendas.size
    }
}
