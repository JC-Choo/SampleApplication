package dev.chu.memo.view.activity

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import android.util.Log
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import dev.chu.memo.R
import dev.chu.memo.base.BaseActivity
import dev.chu.memo.databinding.ActivityMapsBinding
import dev.chu.memo.etc.extension.TAG
import dev.chu.memo.etc.extension.getDrawableById
import dev.chu.memo.etc.extension.showToast
import java.io.IOException
import java.util.*

class MapsActivity : BaseActivity<ActivityMapsBinding>(), OnMapReadyCallback {

    @LayoutRes
    override fun getLayoutRes(): Int = R.layout.activity_maps

    private lateinit var mMap: GoogleMap

    // 앱을 실행하기 위해 필요한 퍼미션을 정의합니다.
    private lateinit var mCurrentLocation: Location
    private lateinit var currentPosition: LatLng
    private lateinit var location: Location
    private lateinit var locationRequest: LocationRequest
    private var mFusedLocationClient: FusedLocationProviderClient? = null
    private val geocoder: Geocoder by lazy { Geocoder(this) }

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

                Log.i(TAG, "지도 현재 위치 onLocationResult : $markerSnippet")

                //현재 위치에 마커 생성하고 이동
                setCurrentLocation(location, markerTitle, markerSnippet)
                mCurrentLocation = location
            }
        }
    }
    // endregion

    override fun initView() {
        Log.i(TAG, "initView")

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
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
//        requestPermission()

        mMap.apply {
            uiSettings?.isMyLocationButtonEnabled = false    // 우측 상단에 위치 버튼
            uiSettings?.isZoomControlsEnabled = false     // +,- 버튼
            animateCamera(CameraUpdateFactory.zoomTo(15f))
            setOnMarkerClickListener {
                // 마커 클릭 리스너
                Log.i(TAG, "marker = ${it.title}, ${it.position}, ${it.id}")
//                bottom_tv_name.text = it.title
//                bottom_tv_address.text = it.title
//
//                bottomSheetBehavior?.state = BottomSheetBehavior.STATE_COLLAPSED
//                main_bt.visibility = View.VISIBLE
//
//                // recyclerView 적용
//                bottom_rv_left.apply {
//                    this.adapter = MainAdapter(getMonToThur(), this@MainActivity)
//                    this.layoutManager = LinearLayoutManager(this@MainActivity)
//                }
//                bottom_rv_right.apply {
//                    this.adapter = MainAdapter(getFriToSun(), this@MainActivity)
//                    this.layoutManager = LinearLayoutManager(this@MainActivity)
//                }

                true
            }
        }

//        // Add a marker in Sydney and move the camera
//        val sydney = LatLng(-34.0, 151.0)
//        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
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

        currentMarker = mMap.addMarker(markerOptions)

        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(DEFAULT_LOCATION, 15f)
        mMap.moveCamera(cameraUpdate)
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

//            val bitmapDraw = getDrawableById(R.drawable.ic_avaliable) as BitmapDrawable
//            val bitmap = bitmapDraw.bitmap
//            val smallMarker = Bitmap.createScaledBitmap(bitmap, 32.toDp, 41.toDp, false)
//            icon(BitmapDescriptorFactory.fromBitmap(smallMarker))
        }
        currentMarker = mMap.addMarker(markerOptions)

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
//            presenter.showDialogForLocationServiceSetting(this)
        } else {
            val hasFineLocationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            val hasCoarseLocationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
            if (hasFineLocationPermission != PackageManager.PERMISSION_GRANTED ||
                hasCoarseLocationPermission != PackageManager.PERMISSION_GRANTED) {
                Log.i(TAG, "startLocationUpdates : 퍼미션 안가지고 있음")
                return
            }

            Log.i(TAG, "현재 위치 업데이트")
            mFusedLocationClient?.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
            if (checkPermission())
                mMap?.isMyLocationEnabled = true
        }
    }
    // endregion

    private fun checkPermission(): Boolean {
        val hasFineLocationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        val hasCoarseLocationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)

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
}
