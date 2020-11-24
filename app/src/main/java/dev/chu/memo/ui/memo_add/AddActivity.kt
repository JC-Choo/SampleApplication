package dev.chu.memo.ui.memo_add

import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.MenuItem
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import dev.chu.memo.R
import dev.chu.memo.base.BaseActivity
import dev.chu.memo.common.Const
import dev.chu.memo.common.Const.usingPermissions
import dev.chu.memo.data.local.ImageData
import dev.chu.memo.databinding.ActivityAddBinding
import dev.chu.memo.etc.extension.*
import dev.chu.memo.ui.memo.MemoViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class AddActivity : BaseActivity<ActivityAddBinding>() {
    @LayoutRes
    override fun getLayoutRes(): Int = R.layout.activity_add

    private val memoVM: MemoViewModel by viewModel()
    private val adapter: ImageModifyAdapter by inject()

    private var photoUri: Uri? = null
    private var timeStamp: String? = null
    private var isBackPress: Boolean = false
    private var isSave: Boolean = false
    private var listImageUrls: MutableList<ImageData> = mutableListOf()

    // region lifeCycle
    override fun initView(savedInstanceState: Bundle?) {
        Log.i(TAG, "initView")

        binding.activity = this
        binding.viewModel = memoVM

        setActionBarHome(binding.includeToolbar.toolbar, R.drawable.arrow_back_white)
        binding.includeToolbar.toolbarTv.text = ""
        binding.includeToolbar.toolbarTvEtc.text = getString(R.string.picture)
        binding.includeToolbar.toolbarTvEtc.setOnClickListener {
            showCameraAndGalleryDialog()
        }

        val isWritingMemo = intent.getBooleanExtra(Const.EXTRA.IS_WRITING_MEMO, false)
        if (isWritingMemo) {
            memoVM.title.value = getPrefString(Const.PREF.MEMO_TITLE, "")
            memoVM.content.value = getPrefString(Const.PREF.MEMO_CONTENT, "")
        }

        setRecyclerView()
        observeViewModel()
    }

    override fun onRestart() {
        super.onRestart()
        Log.i(TAG, "onRestart")
        removePref(Const.PREF.MEMO_TITLE)
        removePref(Const.PREF.MEMO_CONTENT)
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
        Log.i(TAG, "onStop isBackPress = $isBackPress, isSave = $isSave")

        if (!isBackPress && !isSave) {
            setPrefString(Const.PREF.MEMO_TITLE, memoVM.title.value)
            setPrefString(Const.PREF.MEMO_CONTENT, memoVM.content.value)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy")
    }
    // endregion

    private fun showCameraAndGalleryDialog() {
        if (isPermissionsVersion() && !hasPermissions(*usingPermissions)) {
            checkUsingPermission(usingPermissions, Const.REQUEST_CODE_PERMISSIONS)
            return
        }

        val info =
            if (isExternalStorageWritable()) arrayOf<CharSequence>(
                getString(R.string.camera),
                getString(R.string.gallery)
            )
            else arrayOf<CharSequence>(getString(R.string.gallery))
        val builder = AlertDialog.Builder(this).apply {
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
        /**
         * 카메라 앱으로 사진 촬영
         * startActivityForResult() 메서드는 resolveActivity()를 호출하는 조건에 의해 보호되며 이 함수는 인텐트를 처리할 수 있는 첫 번째 활동 구성요소를 반환합니다.
         * 이 확인 절차가 중요한 이유는 앱이 처리할 수 없는 인텐트를 사용하여 startActivityForResult()를 호출하면 앱이 비정상 종료되기 때문입니다.
         */
//        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
//            takePictureIntent.resolveActivity(packageManager)?.also {
//                startActivityForResult(takePictureIntent, Const.REQUEST_CODE_CAMERA_PERMISSION)
//            }
//        }

        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    // Error occurred while creating the File
                    ex.printStackTrace()
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    /**
                     * getUriForFile() -> content:// URI를 반환
                     * Android 7.0(API 레벨 24) 이상을 타겟팅하는 앱이 패키지 경계를 넘어 file:// URI를 전달하면 FileUriExposedException 이 발생합니다.
                     * 따라서 FileProvider 를 사용하여 이미지를 저장하는 방법인 manifest 에 FileProvider 를 구성해야 한다.
                     *
                     * 구성 후 권한 문자열 두번째 인수는 getUriForFile() 에 일치시켜야 함
                     * 제공자 정의의 meta-data 섹션에서 제공자가 전용 리소스 파일(res/xml/file_paths.xml)에 적합한 경로 구성
                     */
                    photoUri = FileProvider.getUriForFile(this, "$packageName.fileprovider", it)
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
        /**
         * 갤러리에 사진 추가
         * 사진을 getExternalFilesDir()에서 제공한 디렉터리에 저장했다면 미디어 스캐너는 파일이 앱 이외에는 비공개이기 때문에 파일에 액세스할 수 없습니다.
         */
        Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE).also { mediaScanIntent ->
            val f = File(getCurrentPhotoPath())
            mediaScanIntent.data = Uri.fromFile(f)
            sendBroadcast(mediaScanIntent)
        }
    }
    // endregion

    private fun setRecyclerView() {
        adapter.setCallback(object : ImageModifyAdapter.ACallback {
            override fun onClickDeleteImage(data: ImageData) {
                confirmDialog("사진을 삭제하시겠습니까?", DialogInterface.OnClickListener { _, _ ->
                    listImageUrls.remove(data)
                    adapter.setItems(listImageUrls)
                })
            }
        })
        binding.writeRvImage.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.writeRvImage.adapter = adapter
    }

    private fun observeViewModel() {
        memoVM.isSave.observe(this, Observer {
            if (it) {
                isSave = it
                removePref(Const.PREF.MEMO_TITLE)
                removePref(Const.PREF.MEMO_CONTENT)
                galleryAddPicture()
                showToast(R.string.save_memo)
                memoVM.isSave.value = false
                finish()
            }
        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            Const.REQUEST_CODE_PERMISSIONS -> {
                if (grantResults.isNotEmpty() && grantResults.size == permissions.size) {
                    var isPermissionGranted = true
                    for (i in grantResults.indices) {
                        if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                            isPermissionGranted = false
                            break
                        }
                    }

                    if (isPermissionGranted) {
                        showCameraAndGalleryDialog()
                    } else {
                        alertDialog(R.string.please_need_permissions)
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

        if (resultCode != RESULT_OK) {
            photoUri = null
            return
        }

        when (requestCode) {
            Const.REQUEST_CODE_CAMERA_PERMISSION -> {
                /**
                 * 미리 보기 이미지 가져오기
                 * "data"에서 가져온 미리보기 이미지는 아이콘으로 사용하기에는 좋지만, 그 이상은 아닙니다. 원본 크기의 이미지를 처리하려면 추가 작업이 필요합니다.
                 */
//                val imageBitmap = data?.extras?.get("data") as Bitmap
//                imageView.setImageBitmap(imageBitmap)

                timeStamp = SimpleDateFormat("yyyy_MM_dd", Locale("ko")).format(Date())

                adapter.addItem(ImageData(imageUrl = photoUri.toString()))
                listImageUrls.add(ImageData(imageUrl = photoUri.toString()))
            }

            Const.REQUEST_CODE_GALLERY_PERMISSION -> {
                timeStamp = SimpleDateFormat("yyyy_MM_dd", Locale("ko")).format(Date())

                photoUri =
                    if (data?.data != null) data.data
                    else null

                adapter.addItem(ImageData(imageUrl = photoUri.toString()))
                listImageUrls.add(ImageData(imageUrl = photoUri.toString()))
            }
        }
    }

    // region onClickEvent
    fun onClickSaveMemo() {
        memoVM.saveMemo(listImageUrls)
    }

    fun onClickFinish() { onBackPressed() }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        android.R.id.home -> {
            onBackPressed()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (!memoVM.title.value.isNullOrEmpty() ||
            !memoVM.content.value.isNullOrEmpty()) {
            confirmDialog(
                getString(R.string.back_memo),
                DialogInterface.OnClickListener { _, _ ->
                    isBackPress = true
                    finish()
                },
                negativeTextResId = R.string.cancel
            )
        } else finish()
    }
    // endregion
}