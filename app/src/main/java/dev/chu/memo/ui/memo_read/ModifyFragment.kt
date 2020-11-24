package dev.chu.memo.ui.memo_read

import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
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
import androidx.recyclerview.widget.LinearLayoutManager
import dev.chu.memo.R
import dev.chu.memo.base.BaseFragment
import dev.chu.memo.common.Const
import dev.chu.memo.common.Const.usingPermissions
import dev.chu.memo.data.local.ImageData
import dev.chu.memo.databinding.FragmentModifyBinding
import dev.chu.memo.etc.extension.*
import dev.chu.memo.etc.listener.OnBackPressedListener
import dev.chu.memo.ui.memo.MemoViewModel
import dev.chu.memo.ui.memo_add.ImageModifyAdapter
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class ModifyFragment : BaseFragment<FragmentModifyBinding>(), OnBackPressedListener {
    @LayoutRes
    override fun getLayoutRes(): Int = R.layout.fragment_modify

    companion object {
        fun newInstance(memoId: Int) = ModifyFragment()
            .apply {
            arguments = Bundle().apply {
                putInt(Const.ARGS.MEMO_ID, memoId)
            }
        }
    }

    private val memoVM: MemoViewModel by viewModel()
    private val adapter: ImageModifyAdapter by inject()

    private var memoId: Int = 0
    private var title: String? = null
    private var content: String? = null
    private var listImageUrls: MutableList<ImageData> = mutableListOf()
    private var countOfImageUrls = 0

    private var photoUri: Uri? = null
    private var timeStamp: String? = null

    // lifecycle
    override fun setView(view: View, savedInstanceState: Bundle?, arguments: Bundle?) {
        Log.i(TAG, "setView")

        binding.fragment = this
        binding.viewModel = memoVM

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

        memoVM.getDataById(memoId)

        setRecyclerView()
        observeViewModel()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.i(TAG, "onActivityCreated")
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.i(TAG, "onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        Log.i(TAG, "onDetach")
    }
    // endregion

    private fun setRecyclerView() {
        adapter.setCallback(object : ImageModifyAdapter.ACallback {
            override fun onClickDeleteImage(data: ImageData) {
                activity?.confirmDialog("사진을 삭제하시겠습니까?", DialogInterface.OnClickListener { _, _ ->
                    listImageUrls.remove(data)
                    adapter.setItems(listImageUrls)
                })
            }
        })
        binding.modifyFlRvImage.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.modifyFlRvImage.adapter = adapter
    }

    private fun observeViewModel() {
        memoVM.memo.observe(viewLifecycleOwner, Observer {
            title = it.title
            content = it.content

            memoVM.title.value = it.title
            memoVM.content.value = it.content

            if(!it.imageUrls.isNullOrEmpty()) {
                listImageUrls.addAll(it.imageUrls!!)
                adapter.setItems(listImageUrls)
                countOfImageUrls = listImageUrls.size
            }
        })

        memoVM.isUpdate.observe(viewLifecycleOwner, Observer {
            if(it) {
                memoVM.isUpdate.value = false
                showToast(R.string.modify_memo)
                galleryAddPicture()
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
                if (isExternalStorageWritable()) {
                    when (which) {
                        0 -> dispatchTakePictureIntent()
                        1 -> doTakeGalleryAction()
                    }
                } else {
                    doTakeGalleryAction()
                }
                dialogInterface.dismiss()
            }
        }
        builder.show()
    }

    // region 사진 찍기
    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(activity!!.packageManager)?.also {
                val photoFile: File? = try {
                    (activity as AppCompatActivity).createImageFile()
                } catch (ex: IOException) {
                    ex.printStackTrace()
                    null
                }
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
        Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE).also { mediaScanIntent ->
            val f = File(getCurrentPhotoPath())
            mediaScanIntent.data = Uri.fromFile(f)
            activity?.sendBroadcast(mediaScanIntent)
        }
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

                listImageUrls.add(ImageData(imageUrl = photoUri.toString()))
                adapter.addItem(ImageData(imageUrl = photoUri.toString()))
            }

            Const.REQUEST_CODE_GALLERY_PERMISSION -> {
                timeStamp = SimpleDateFormat("yyyy_MM_dd", Locale("ko")).format(Date())

                photoUri = if (data?.data != null) {
                    data.data
                } else
                    null

                listImageUrls.add(ImageData(imageUrl = photoUri.toString()))
                adapter.addItem(ImageData(imageUrl = photoUri.toString()))
            }
        }
    }

    fun onClickFinish() {
        if (title != memoVM.title.value ||
            content != memoVM.content.value ||
            countOfImageUrls != listImageUrls.size) {
            context?.confirmDialog(
                getString(R.string.back_memo),
                DialogInterface.OnClickListener { _, _ ->
                    activity?.finish()
                },
                negativeTextResId = R.string.cancel
            )
        } else activity?.finish()
    }

    fun onClickUpdate() {
        memoVM.updateMemo(listImageUrls)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when(item.itemId) {
        android.R.id.home -> {
            onClickFinish()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        onClickFinish()
    }
}