package br.com.edsb.hackathon.ui.fragments.pills

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
import br.com.edsb.hackathon.data.model.Pill
import br.com.edsb.hackathon.data.model.Road
import br.com.edsb.hackathon.ui.adapters.PillRecyclerAdapter
import br.com.edsb.hackathon.ui.adapters.RoadRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_initial.*
import kotlinx.android.synthetic.main.fragment_initial.view.*

class PillsFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_pill, container, false)

        val recycler = view.rvRoad
        recycler.layoutManager = LinearLayoutManager(context)

        val adapter = PillRecyclerAdapter(context!!, arrayListOf(
                Pill("AASP defende em audiência pública uma lei de custas que garanta o acesso à justiça",
                        R.drawable.ic_video),
                Pill("STF considera legítimo compartilhamento de dados bancários e fiscais com Ministério Público",
                        R.drawable.ic_video),
                Pill("Colegiado de direito público vai julgar responsabilidade de Junta Comercial no registro fraudulento de empresa",
                        R.drawable.ic_document),
                Pill("Com metodologia inovadora, AASP promove curso para discutir proteção de dados",R.drawable.ic_document),
                Pill("CJF libera R\$1,4 bilhão em RPVs autuadas em outubro de 2019",R.drawable.ic_document),
                Pill("Podcast Direito + Tecnologia",R.drawable.ic_audio),
                Pill("Podcast FGV - O direito 4.0",R.drawable.ic_audio)
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
