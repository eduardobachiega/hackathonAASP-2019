package br.com.edsb.hackathon.data.model

import android.net.Uri
import com.google.android.gms.tasks.Task

data class User(var id: String?,
                var name: String?,
                var lastName: String?,
                var email: String?,
                var photo: Task<Uri>?,
                var admin: Boolean,
                var teamReference: String?) {

    constructor() : this(null, null, null, null, null, false, null)
}