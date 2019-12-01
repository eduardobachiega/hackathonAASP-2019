package br.com.edsb.hackathon.data.model

import android.net.Uri
import com.google.android.gms.tasks.Task

data class GenericItemModel(val id: String?,
                            val photoUrl: Task<Uri>?,
                            val contentText: String?,
                            val descriptionText: String?,
                            val extraArgs: Map<String, Any?>?)