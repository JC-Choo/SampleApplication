package dev.chu.permission

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import dev.chu.permission.common.model.PermissionResult
import kotlinx.coroutines.*

class MainFragment : Fragment() {

    private val parentJob = Job()
    private val coroutineScope = CoroutineScope(parentJob + Dispatchers.Default)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.location_permission_btn).setOnClickListener {
            coroutineScope.launch {
                withContext(Dispatchers.Main) {
                    handleResult(
                        PermissionManager.requestPermissions(
                            this@MainFragment, 1,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        )
                    )
                }
            }
        }

        view.findViewById<Button>(R.id.contact_permission_btn).setOnClickListener {
            coroutineScope.launch {
                withContext(Dispatchers.Main) {
                    handleResult(
                        PermissionManager.requestPermissions(
                            this@MainFragment, 2,
                            Manifest.permission.READ_CONTACTS
                        )
                    )
                }
            }
        }

        view.findViewById<Button>(R.id.camera_permission_btn).setOnClickListener {
            coroutineScope.launch {
                withContext(Dispatchers.Main) {
                    handleResult(
                        PermissionManager.requestPermissions(
                            this@MainFragment, 3,
                            Manifest.permission.CAMERA
                        )
                    )
                }
            }
        }

        view.findViewById<Button>(R.id.location_contact_camera_permission_btn).setOnClickListener {
            coroutineScope.launch {
                withContext(Dispatchers.Main) {
                    handleResult(
                        PermissionManager.requestPermissions(
                            this@MainFragment, 4,
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.READ_CONTACTS,
                            Manifest.permission.CAMERA
                        )
                    )
                }
            }
        }

    }

    private fun handleResult(permissionResult: PermissionResult) {
        when (permissionResult) {
            is PermissionResult.PermissionGranted -> {
                Toast.makeText(requireContext(), "Granted", Toast.LENGTH_SHORT).show()
            }
            is PermissionResult.PermissionDenied -> {
                Toast.makeText(requireContext(), "Denied", Toast.LENGTH_SHORT).show()
                permissionResult.test()
            }
            is PermissionResult.ShowRational -> {
                val alertDialog = AlertDialog.Builder(requireContext())
                    .setMessage("We need permission")
                    .setTitle("Rational")
                    .setPositiveButton("OK") { _, _ ->
                        when (permissionResult.requestCode) {
                            1 -> {
                                coroutineScope.launch(Dispatchers.Main) {
                                    handleResult(
                                        PermissionManager.requestPermissions(
                                            this@MainFragment,
                                            1,
                                            Manifest.permission.ACCESS_FINE_LOCATION
                                        )
                                    )
                                }
                            }
                            2 -> {
                                coroutineScope.launch(Dispatchers.Main) {
                                    handleResult(
                                        PermissionManager.requestPermissions(
                                            this@MainFragment,
                                            2,
                                            Manifest.permission.READ_CONTACTS
                                        )
                                    )
                                }
                            }
                            3 -> {
                                coroutineScope.launch(Dispatchers.Main) {
                                    handleResult(
                                        PermissionManager.requestPermissions(
                                            this@MainFragment,
                                            3,
                                            Manifest.permission.CAMERA
                                        )
                                    )
                                }
                            }
                            4 -> {
                                coroutineScope.launch(Dispatchers.Main) {
                                    handleResult(
                                        PermissionManager.requestPermissions(
                                            this@MainFragment,
                                            4,
                                            Manifest.permission.ACCESS_FINE_LOCATION,
                                            Manifest.permission.READ_CONTACTS,
                                            Manifest.permission.CAMERA
                                        )
                                    )
                                }
                            }
                        }

                    }
                    .setNegativeButton("Cancel") { dialog, _ ->
                        dialog.dismiss()
                    }.create()
                alertDialog.show()
            }
            is PermissionResult.PermissionDeniedPermanently -> {
                Toast.makeText(requireContext(), "Denied permanently", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        parentJob.cancel()
    }
}
