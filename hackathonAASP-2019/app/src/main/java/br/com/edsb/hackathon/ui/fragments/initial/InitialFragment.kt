package br.com.edsb.hackathon.ui.fragments.initial

import ai.api.android.AIService
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.edsb.hackathon.R
import br.com.edsb.hackathon.data.model.Road
import br.com.edsb.hackathon.ui.adapters.RoadRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_initial.*
import kotlinx.android.synthetic.main.fragment_initial.view.*

class InitialFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_initial, container, false)

        val recycler = view.rvRoad
        recycler.layoutManager = LinearLayoutManager(context)

        val adapter = RoadRecyclerAdapter(context!!, arrayListOf(
                Road("Tipos de dados e o conceito de dado pessoal",R.drawable.ic_accounts, true),
                Road("Princípios da proteção de dados pessoais",R.drawable.ic_lock, true),
                Road("Permissões e exceções para o uso de dados",R.drawable.ic_permission, true),
                Road("Transferência internacional de dados",R.drawable.ic_world, true),
                Road("Pedidos judiciais e administrativos de disponibilização de dados",R.drawable.ic_order, false),
                Road("Responsabilidade jurídica e vazamento de dados",R.drawable.ic_data_leak, false),
                Road("Aprendizado de máquina e decisões automatizadas",R.drawable.ic_computer, false)
        ))

        recycler.adapter = adapter

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() +
                    " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }
}
