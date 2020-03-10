package dev.chu.memo.view.activity.read

import android.content.ContentValues
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dev.chu.memo.R
import dev.chu.memo.base.BaseFragment
import dev.chu.memo.common.Const
import dev.chu.memo.common.Const.usingPermissions
import dev.chu.memo.data.local.ImageData
import dev.chu.memo.databinding.FragmentModifyBinding
import dev.chu.memo.etc.extension.*
import dev.chu.memo.view.adapter.ImageAdapter
import dev.chu.memo.view_model.RoomViewModel
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

class ModifyFragment : BaseFragment<FragmentModifyBinding>() {
    @LayoutRes
    override fun layoutRes(): Int = R.layout.fragment_modify

    companion object {
        fun newInstance(memoId: Int) = ModifyFragment().apply {
            arguments = Bundle().apply {
                putInt(Const.ARGS.MEMO_ID, memoId)
            }
        }
    }

    private val adapter by lazy { ImageAdapter(mutableListOf()) }
    private lateinit var roomVM: RoomViewModel
    private var memoId: Int = 0
    private var photoUri: Uri? = null
    private var timeStamp: String? = null

    override fun setView(view: View?, savedInstanceState: Bundle?, arguments: Bundle?) {
        Log.i(TAG, "setView")

        roomVM = activity?.let {
            ViewModelProvider(this)[RoomViewModel::class.java]
        } ?: throw Exception("Activity is null")

        binding.fragment = this
        binding.viewModel = roomVM

        setHasOptionsMenu(true)
        (activity as AppCompatActivity).setActionBarHome(binding.includeToolbar.toolbar, R.drawable.arrow_back_white)
        binding.includeToolbar.toolbarTv.text = ""
        binding.includeToolbar.toolbarTvEtc.text = getString(R.string.picture)
        binding.includeToolbar.toolbarTvEtc.setOnClickListener {
            showCameraAndGalleryDialog()
        }

        arguments?.let {
            memoId = it.getInt(Const.ARGS.MEMO_ID, 0)
        }

        roomVM.getDataById(memoId)

        setRecyclerView()
        observeViewModel()
    }

    private fun setRecyclerView() {
        binding.modifyFlRvImage.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.modifyFlRvImage.adapter = adapter
    }

    private fun observeViewModel() {
        roomVM.memo.observe(this, Observer {
            roomVM.title.value = it.title
            roomVM.content.value = it.content

            if(!it.imageUrls.isNullOrEmpty()) {
                adapter.setItems(it.imageUrls!!)
                roomVM.setAllImageUrl(it.imageUrls!!)
            }
        })

        roomVM.isUpdate.observe(this, Observer {
            if(it) {
                roomVM.isUpdate.value = false
                showToast(R.string.save_memo)
                activity?.finish()
            }
        })
    }

    private fun showCameraAndGalleryDialog() {
        if (isPermissionsVersion() && !(activity as AppCompatActivity).hasPermissions(*usingPermissions)) {
            (activity as AppCompatActivity).checkUsingPermission(usingPermissions, Const.REQUEST_CODE_PERMISSIONS)
            return
        }

        val info = arrayOf<CharSequence>(
            getString(R.string.camera),
            getString(R.string.gallery)
        )
        val builder = AlertDialog.Builder(context!!).apply {
            setTitle(getString(R.string.get_image))
            setItems(info) { dialogInterface, which ->
                when (which) {
                    0 -> dispatchTakePictureIntent()
                    1 -> doTakeGalleryAction()
                }
                dialogInterface.dismiss()
            }
        }
        builder.show()
    }

    // region 사진 찍기
    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(activity!!.packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    (activity as AppCompatActivity).createImageFile()
                } catch (ex: IOException) {
                    // Error occurred while creating the File
                    ex.printStackTrace()
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    photoUri = FileProvider.getUriForFile(context!!, "dev.chu.memo.fileprovider", it)
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                    startActivityForResult(takePictureIntent, Const.REQUEST_CODE_CAMERA_PERMISSION)
                }
            }
        }
    }
    // endregion

    // region 앨범에서 가져오기
    private fun doTakeGalleryAction() {
        startActivityForResult(Intent(Intent.ACTION_PICK).apply {
            type = "image/*"
            type = MediaStore.Images.Media.CONTENT_TYPE
        }, Const.REQUEST_CODE_GALLERY_PERMISSION)
    }
    // endregion

    // region 앨범에 사진 저장
    private fun galleryAddPicture() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val values = ContentValues().apply {
//                put(MediaStore.Audio.Media.RELATIVE_PATH, "DCIM/Camera")     // 파일이 저장되는 위치
                put(MediaStore.Images.Media.DISPLAY_NAME, File(getCurrentPhotoPath()).name)
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpg")
                put(MediaStore.Images.Media.IS_PENDING, 1)      // 이 속성은 아직 내가 파일을 write하지 않았으니, 다른 곳에서 이 데이터를 요구하면 무시하라는 의미입니다. 파일을 모두 write한 뒤에 이 속성을 0으로 update해줘야 합니다.
            }

            val collection = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            val item = activity?.contentResolver?.insert(collection, values)!!

            activity?.contentResolver?.openFileDescriptor(item, "w", null).use {
                // write something to OutputStream
                FileOutputStream(it!!.fileDescriptor).use { outputStream ->
//                    val imageInputStream = resources.openRawResource(R.raw.my_image)
                    val imageInputStream: InputStream = FileInputStream(getCurrentPhotoPath())
//                    while (true) {
                    val data = imageInputStream.read()
//                        if (data == -1) {
//                            break
//                        }
                    outputStream.write(data)
//                    }
                    imageInputStream.close()
                    outputStream.close()
                }
            }

            values.clear()
            values.put(MediaStore.Images.Media.IS_PENDING, 0)
            activity?.contentResolver?.update(item, values, null, null)
        } else {
            Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE).also { mediaScanIntent ->
                val f = File(getCurrentPhotoPath())
                mediaScanIntent.data = Uri.fromFile(f)
                activity?.sendBroadcast(mediaScanIntent)
            }
        }


        roomVM.addImageUrl(ImageData(imageUrl = photoUri.toString()))
        adapter.addItem(ImageData(imageUrl = photoUri.toString()))
        showToast(R.string.save_image)
    }
    // endregion

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode) {
            Const.REQUEST_CODE_PERMISSIONS -> {
                if(grantResults.isNotEmpty() && grantResults.size == permissions.size) {
                    var isPermissionGranted = true
                    for (i in grantResults.indices) {
                        if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                            isPermissionGranted = false
                            break
                        }
                    }

                    if(isPermissionGranted) {
                        showCameraAndGalleryDialog()
                    } else {
                        context?.alertDialog(R.string.please_need_permissions)
                    }
                }

                return
            }
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != AppCompatActivity.RESULT_OK) {
            photoUri = null
            return
        }

        when (requestCode) {
            Const.REQUEST_CODE_CAMERA_PERMISSION -> {
                timeStamp = SimpleDateFormat("yyyy_MM_dd", Locale("ko")).format(Date())
                galleryAddPicture()
            }

            Const.REQUEST_CODE_GALLERY_PERMISSION -> {
                timeStamp = SimpleDateFormat("yyyy_MM_dd", Locale("ko")).format(Date())

                photoUri = if (data?.data != null) {
                    data.data
                } else
                    null

                roomVM.addImageUrl(ImageData(imageUrl = photoUri.toString()))
                adapter.addItem(ImageData(imageUrl = photoUri.toString()))
            }
        }
    }

    fun onClickFinish() {
        if (!binding.modifyFlEtTitle.text.isNullOrEmpty()) {
            context?.confirmDialog(
                getString(R.string.back_memo),
                DialogInterface.OnClickListener { _, _ -> activity?.finish() },
                negativeTextResId = R.string.cancel
            )
        } else activity?.finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when(item.itemId) {
        android.R.id.home -> {
            activity?.finish()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}