package dev.chu.modalbottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import dev.chu.extensions.click
import dev.chu.extensions.toast
import dev.chu.modalbottomsheet.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.openModalBottomSheet.click {
            val bottomSheetDialog = BottomSheetDialog(this, R.style.Theme_Design_BottomSheetDialog)
            val bottomSheetView: View = LayoutInflater.from(this).inflate(R.layout.modal_bottom_sheet, findViewById(R.id.modalBottomSheetContainer))

            bottomSheetView.findViewById<LinearLayout>(R.id.folder).click {
                toast("Folder clicked..")
                bottomSheetDialog.dismiss()
            }

            bottomSheetView.findViewById<LinearLayout>(R.id.upload).click {
                toast("Upload clicked..")
                bottomSheetDialog.dismiss()
            }

            bottomSheetView.findViewById<LinearLayout>(R.id.scan).click {
                toast("Scan clicked..")
                bottomSheetDialog.dismiss()
            }

            bottomSheetView.findViewById<LinearLayout>(R.id.docs).click {
                toast("Google docs clicked..")
                bottomSheetDialog.dismiss()
            }

            bottomSheetView.findViewById<LinearLayout>(R.id.sheets).click {
                toast("Google sheets clicked..")
                bottomSheetDialog.dismiss()
            }

            bottomSheetView.findViewById<LinearLayout>(R.id.slides).click {
                toast("Google slides clicked..")
                bottomSheetDialog.dismiss()
            }

            bottomSheetDialog.setContentView(bottomSheetView)
            bottomSheetDialog.show()
        }
    }
}