package dev.chu.memo.ui.map

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import dev.chu.memo.R
import dev.chu.memo.base.BaseActivity
import dev.chu.memo.databinding.ActivityMapsBinding
import dev.chu.memo.etc.extension.TAG
import dev.chu.memo.etc.extension.alertDialog
import dev.chu.memo.etc.extension.bitmapDescriptorFromVector
import dev.chu.memo.etc.extension.showToast
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.IOException
import java.util.*

class CoronaActivity : BaseActivity<ActivityMapsBinding>(), OnMapReadyCallback {

    @LayoutRes
    override fun getLayoutRes(): Int = R.layout.activity_maps

    private val coronaVM: CoronaViewModel by viewModel()

    private val UPDATE_INTERVAL_MS = 60_000  // 60초
    private val FASTEST_UPDATE_INTERVAL_MS = 60_000 // 10초

    private lateinit var mMap: GoogleMap
    private lateinit var mCurrentLocation: Location
    private lateinit var currentPosition: LatLng
    private lateinit var location: Location
    private lateinit var locationRequest: LocationRequest
    private var mFusedLocationClient: FusedLocationProviderClient? = null
    private var currentMarker: Marker? = null

    // region location call back
    private var locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            super.onLocationResult(locationResult)

            val locationList = locationResult!!.locations
            if (locationList.size > 0) {
                location = locationList[locationList.size - 1]
                //location = locationList.get(0);

                currentPosition = LatLng(location.latitude, location.longitude)
                Log.i(TAG, "onLocationResult currentPosition = $currentPosition")

                val markerTitle = getCurrentAddress(currentPosition)
                val markerSnippet = "위도 : ${location.latitude}, 경도 : ${location.longitude}"
                coronaVM.getStoresByGeo(location.latitude, location.longitude, 1000)

                Log.i(TAG, "지도 현재 위치 onLocationResult : $markerSnippet")

                //현재 위치에 마커 생성하고 이동
                setCurrentLocation(location, markerTitle, markerSnippet)
                mCurrentLocation = location
            }
        }
    }
    // endregion

    override fun initView(savedInstanceState: Bundle?) {
        Log.i(TAG, "initView")

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
        )

        locationRequest = LocationRequest()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval(UPDATE_INTERVAL_MS.toLong())
            .setFastestInterval(FASTEST_UPDATE_INTERVAL_MS.toLong())

//        val builder = LocationSettingsRequest.Builder()
//        builder.addLocationRequest(locationRequest)

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        observeViewModel()
    }

    private fun observeViewModel() {
        coronaVM.refresh.observe(this, Observer {
            binding.mapsPb.visibility = if(it) View.VISIBLE else View.GONE
//            if(it) showProgress()
//            else hideProgress()
        })

        coronaVM.storeByGeoList.observe(this, Observer {
            val stores = it.stores
            if (stores.isNotEmpty()) {
                for (i in stores.indices) {
                    val point = LatLng(stores[i].lat, stores[i].lng)       // 좌표(위도, 경도) 생성
                    // 마커 생성
//                    val remainState = if(stores[i].remain_stat == "plenty") "100개 이상" else if(stores[i].remain_stat == "some") "30개 이상 100개 미만" else "2개 이상 30개 미만"

                    val mOptions = MarkerOptions()
                    mOptions.title(stores[i].name + "]" + stores[i].addr + "]" + stores[i].stock_at + "]" + stores[i].remain_stat + "]" + stores[i].created_at+"]"+stores[i].type)
                    mOptions.snippet("거리")
//                    mOptions.snippet("이름 : ${stores[i].name}\n주소 : ${stores[i].addr}\n입고 시간 : ${stores[i].stock_at}\n수량 : $remainState")
                    mOptions.position(point)

                    if (!stores[i].remain_stat.isNullOrBlank()) {
                        when (stores[i].remain_stat) {
                            "some" -> {
                                mOptions.icon(bitmapDescriptorFromVector(R.drawable.location_some))
                                mMap.addMarker(mOptions)
                            }

                            "few" -> {
                                mOptions.icon(bitmapDescriptorFromVector(R.drawable.location_few))
                                mMap.addMarker(mOptions)
                            }

                            "plenty" -> {
                                mOptions.icon(bitmapDescriptorFromVector(R.drawable.location_plenty))
                                mMap.addMarker(mOptions)
                            }
                            else -> {
                            }
                        }
                    }
                }
            }
        })
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // 런타임 퍼미션 요청 대화상자나 GPS 활성 요청 대화상자 보이기전에 지도의 초기위치를 서울로 이동
        initLocation()
        requestPermission()
//        setMarkerToMaskPosition()

        mMap.apply {
            uiSettings?.isMyLocationButtonEnabled = true    // 우측 상단에 위치 버튼
            uiSettings?.isZoomControlsEnabled = false     // +,- 버튼
            animateCamera(CameraUpdateFactory.zoomTo(15f))
            setOnMarkerClickListener {
                // 마커 클릭 리스너
                Log.i(TAG, "marker = ${it.title}, ${it.position}, ${it.id}")
                val text = it.title.split("]")
                val remainState = if(text[3] == "plenty") "100개 이상" else if(text[3] == "some") "30개 이상 100개 미만" else "2개 이상 30개 미만"
                alertDialog("이름 : ${text[0]}\n주소 : ${text[1]}\n입고 시간 : ${text[2]}\n수량 : $remainState")
                true
            }
        }
    }

    // region 초기 위치 설정
    private fun initLocation() {
        //디폴트 위치, Seoul
        val DEFAULT_LOCATION = LatLng(37.56, 126.97)
        Log.i(TAG, "지도 기본 위치 설정")

        currentPosition = DEFAULT_LOCATION
        Log.i(TAG, "setDefaultLocation currentPosition = $currentPosition")

        currentMarker?.remove()

        val markerOptions = MarkerOptions().apply {
            position(DEFAULT_LOCATION)
            title(getString(R.string.marker_title))
            snippet(getString(R.string.marker_snippet))
            draggable(true)
            icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
        }

//        currentMarker = mMap.addMarker(markerOptions)

        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(DEFAULT_LOCATION, 15f)
        mMap.moveCamera(cameraUpdate)
    }
    // endregion

    // region ted permission
    private fun requestPermission() {
        val permissionListener = object : PermissionListener {
            override fun onPermissionGranted() {
                startLocationUpdates()
            }

            override fun onPermissionDenied(deniedPermissions: ArrayList<String>?) {
                showToast("권한 거부\n${deniedPermissions.toString()}")
            }
        }

        TedPermission.with(this)
            .setPermissionListener(permissionListener)
            .setDeniedMessage("왜 거부하셨어요...\n하지만 [설정] > [권한] 에서 권한을 허용할 수 있어요.")
            .setPermissions(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            .check()
    }
    // endregion

    // region 현재 위치 설정
    private fun setCurrentLocation(location: Location, markerTitle: String, markerSnippet: String) {
        if (currentMarker != null) currentMarker?.remove()

        Log.i(TAG, "지도 현재 위치 설정")
        val currentLatLng = LatLng(location.latitude, location.longitude)
        val markerOptions = MarkerOptions().apply {
            position(currentLatLng)
            title(markerTitle)
            snippet(markerSnippet)
            draggable(true)
            icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
        }
//        currentMarker = mMap.addMarker(markerOptions)

        val cameraUpdate = CameraUpdateFactory.newLatLng(currentLatLng)
        mMap.moveCamera(cameraUpdate)
    }
    // endregion

    // 현재 위치 받아오기
    private fun getCurrentAddress(latlng: LatLng): String {
        // 지오코더... GPS를 주소로 변환
        val geoCoder = Geocoder(this, Locale.getDefault())
        val addresses: List<Address>?

        try {
            addresses = geoCoder.getFromLocation(latlng.latitude, latlng.longitude, 1)
        } catch (ioException: IOException) {
            //네트워크 문제
            showToast(getString(R.string.toast_impossible_gps_service))
            return getString(R.string.toast_impossible_gps_service)
        } catch (illegalArgumentException: IllegalArgumentException) {
            showToast(getString(R.string.toast_incorrect_gps))
            return getString(R.string.toast_incorrect_gps)
        }

        return if (addresses == null || addresses.isEmpty()) {
            showToast(getString(R.string.toast_undiscovered_gps))
            getString(R.string.toast_undiscovered_gps)
        } else {
            val address = addresses[0]
            address.getAddressLine(0).toString()
        }
    }

    // region 현재 위치 업데이트
    private fun startLocationUpdates() {
        if (!checkLocationServicesStatus()) {
            AlertDialog.Builder(this).apply {
                setTitle(R.string.alert_dialog_title_gps)
                setMessage(R.string.alert_dialog_message_gps)
                setCancelable(true)
                setPositiveButton(R.string.setting) { _, _ ->
                    startActivityForResult(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), 1000)
                }
                setNegativeButton(R.string.cancel) { dialog, _ -> dialog.cancel() }
            }.create().show()
        } else {
            val hasFineLocationPermission =
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            val hasCoarseLocationPermission =
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
            if (hasFineLocationPermission != PackageManager.PERMISSION_GRANTED ||
                hasCoarseLocationPermission != PackageManager.PERMISSION_GRANTED
            ) {
                Log.i(TAG, "startLocationUpdates : 퍼미션 안가지고 있음")
                return
            }

            Log.i(TAG, "현재 위치 업데이트")
            mFusedLocationClient?.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.myLooper()
            )
            if (checkPermission())
                mMap.isMyLocationEnabled = true
        }
    }
    // endregion

    private fun checkPermission(): Boolean {
        val hasFineLocationPermission =
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        val hasCoarseLocationPermission =
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)

        val result = hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED

        Log.i(TAG, "checkPermission result = $result")
        return result
    }

    private fun checkLocationServicesStatus(): Boolean {
        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

//    private fun setMarkerToMaskPosition() {
//        storeVM.getStore(50)
//    }
}
