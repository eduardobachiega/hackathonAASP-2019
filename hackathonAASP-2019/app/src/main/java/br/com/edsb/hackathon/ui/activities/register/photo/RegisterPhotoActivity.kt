package br.com.edsb.hackathon.ui.activities.register.photo

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.MenuItem
import br.com.edsb.hackathon.R
import br.com.edsb.hackathon.ui.activities.base.BaseActivity
import br.com.edsb.hackathon.ui.activities.menu.MenuActivity
import com.bumptech.glide.Glide
import com.karumi.dexter.Dexter
import kotlinx.android.synthetic.main.activity_edit_user.*
import kotlinx.android.synthetic.main.content_register_photo.*
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.single.PermissionListener
import com.karumi.dexter.listener.PermissionRequest
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.ItemListener
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition

class RegisterPhotoActivity : BaseActivity(), RegisterPhotoInterfaces.View {
    private val presenter = RegisterPhotoPresenter(this)
    private val GALLERY = 1
    private val CAMERA = 2
    private var selectedImage: Bitmap? = null
    private var isFromMenu = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_photo)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        fabAddPhoto.setOnClickListener {
            showOptionsDialog(getString(R.string.photo_origin), listOf(getString(R.string.gallery),
                    getString(R.string.camera)), object : ItemListener {
                override fun invoke(dialog: MaterialDialog, index: Int, text: String) {
                    when (index) {
                        0 -> openGallery()
                        1 -> openCamera()
                    }
                }
            })
        }

        if (intent.getBooleanExtra("fromMenu", false)) {
            isFromMenu = true
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        fab.setOnClickListener {
            if (selectedImage != null)
                presenter.uploadPhoto(selectedImage)
            else
                proceedToMenu()

        }

        presenter.loadPhoto()
    }

    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

        startActivityForResult(galleryIntent, GALLERY)
    }

    private fun openCamera() {
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(object : PermissionListener {
                    override fun onPermissionGranted(response: PermissionGrantedResponse) {
                        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        startActivityForResult(intent, CAMERA)
                    }

                    override fun onPermissionDenied(response: PermissionDeniedResponse) {

                    }

                    override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest,
                                                                    token: PermissionToken) {

                    }
                }).check()
    }

    override fun onBackPressed() {
        if (isFromMenu) {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        } else {
            super.onBackPressed()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun proceedToMenu() = startActivity(Intent(this,
            MenuActivity::class.java))

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GALLERY) {
            if (data != null) {
                val contentURI = data.data

                Glide.with(this).asBitmap().load(contentURI).into<SimpleTarget<Bitmap>>(object : SimpleTarget<Bitmap>() {
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        fabAddPhoto.setImageResource(R.drawable.ic_edit)
                        ivProfilePicture.setImageBitmap(resource)
                        selectedImage = resource
                    }
                })
            }
        } else if (requestCode == CAMERA) {
            if (data != null) {
                val thumbnail = data.extras!!.get("data") as Bitmap
                Glide.with(this).load(thumbnail).into(ivProfilePicture)
                fabAddPhoto.setImageResource(R.drawable.ic_edit)
                selectedImage = thumbnail
            }
        }
    }

    override fun setImage(uri: Uri) {
        Glide.with(this)
                .asBitmap()
                .load(uri)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into<SimpleTarget<Bitmap>>(object : SimpleTarget<Bitmap>() {
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        fabAddPhoto.setImageResource(R.drawable.ic_edit)
                        ivProfilePicture.setImageBitmap(resource)
                    }
                })
    }
}
