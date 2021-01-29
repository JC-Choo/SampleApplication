package dev.chu.permission

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import dev.chu.permission.helper.MyPermission
import dev.chu.permission.helper.PermissionHelper


// 참고 : https://medium.com/better-programming/when-coroutines-meet-android-permissions-a1f048e70f74
// 참고 : https://github.com/sagar-viradiya/eazypermissions/blob/master/sample/src/main/res/navigation/nav_graph.xml

class MainActivity : AppCompatActivity(), PermissionHelper.Listener {

    private lateinit var toolbar: Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.navigation_view)

        val navController = findNavController(R.id.nav_host_frag)
        setSupportActionBar(toolbar)
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
        navigationView.setupWithNavController(navController)

        permission()
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(
            Navigation.findNavController(this, R.id.nav_host_frag), drawerLayout
        )
    }


    companion object {
        private val TAG by lazy { MainActivity::class.java.simpleName }
    }

    private val mPermissionsHelper by lazy {
        PermissionHelper(this)
    }

    private fun permission() {
        val permissions = arrayOf(MyPermission.FINE_LOCATION, MyPermission.READ_PHONE_STATE)
        if (!mPermissionsHelper.hasAllPermissions(permissions)) {
            mPermissionsHelper.requestAllPermissions(permissions, 1)
        }
    }

    override fun onStart() {
        super.onStart()
        mPermissionsHelper.registerListener(this)
    }

    override fun onStop() {
        super.onStop()
        mPermissionsHelper.unregisterListener(this)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        result: PermissionHelper.PermissionsResult
    ) {
        if (result.denied.isNotEmpty()) {
            // handle permission(s) that were denied
            Log.i(TAG, "handle permission(s) that were denied")
            return
        }

        if (result.granted.isNotEmpty()) {
            // handle permission(s) that were granted
            Log.i(TAG, "handle permission(s) that were granted")
            return
        }

        if (result.deniedDoNotAskAgain.isNotEmpty()) {
            // handle permissions(s) that were denied and shouldn't ask again
            Log.i(TAG, "handle permissions(s) that were denied and shouldn't ask again")
            return
        }
    }

    override fun onPermissionsRequestCanceled(requestCode: Int) {
        // handle permissions request cancellation
        Log.i(TAG, "handle permissions request cancellation")
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        // must delegate to PermissionsHelper because this object functions as a central "hub"
        // for permissions management in the scope of this Activity
        mPermissionsHelper.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}
