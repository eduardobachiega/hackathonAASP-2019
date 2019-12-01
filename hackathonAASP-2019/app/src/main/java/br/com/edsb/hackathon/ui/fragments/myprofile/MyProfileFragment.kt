package br.com.edsb.hackathon.ui.fragments.myprofile

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.edsb.hackathon.R
import br.com.edsb.hackathon.data.firebase.UserFirebaseHelper
import br.com.edsb.hackathon.data.preferences.AppPreferences
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.fragment_myprofile.*
import kotlinx.android.synthetic.main.fragment_myprofile.view.*

class MyProfileFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_myprofile, container, false)

        UserFirebaseHelper.getProfilePictureDownloadURL().addOnSuccessListener {
            if (it != null)
                Glide.with(this)
                        .load(it)
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                        .into(view.civUser)
        }

        view.tvUserName.text = AppPreferences.getUserName()

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
