package dev.chu.memo.data.remote

import dev.chu.memo.data.response.SaleRes
import dev.chu.memo.data.response.StoreRes
import dev.chu.memo.data.response.StoresByGeoRes
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    // 약국, 우체국, 농협 등의 마스크 판매처 정보 제공 (마스크 재고 관련 정보는 제공하지 않음)
    // page : 페이지 번호
    // perPage : 한 페이지당 출력할 판매처 수[default: 500, min:500, max:5000]
    @GET("/stores/json")
    fun getStores(@Query("page") page: Int, @Query("perPage") perPage: Int) : Single<StoreRes>

    // 마스크 재고 상태 등의 판매 정보 제공(판매처 관련 정보는 제공하지 않음)
    @GET("/sales/json")
    fun getSales(@Query("page") page: Int, @Query("perPage") perPage: Int) : Single<SaleRes>

    // 중심 좌표(위/경도)를 기준으로 반경(미터단위) 안에 존재하는 판매처 및 재고 상태 등의 판매 정보 제공
    // lat : 위도(wgs84 좌표계) / 최소:33.0, 최대:43.0
    // lng : 경도(wgs84 표준) / 최소:124.0, 최대:132.0
    // m : 반경(미터) / 최대 5000(5km)까지 조회 가능
    @GET("/storesByGeo/json")
    fun getStoresByGeo(@Query("lat") lat: Double, @Query("lng") lng: Double, @Query("m") m: Int) : Single<StoresByGeoRes>

    // 주소를 기준으로 해당 구 또는 동내에 존재하는 판매처 및 재고 상태 등의 판매 정보 제공.
    // 예- '서울특별시 강남구' or '서울특별시 강남구 논현동'
    // ('서울특별시' 와 같이 '시'단위만 입력하는 것은 불가능합니다.)
    @GET("/storesByAddr/json")
    fun getStoresByAddr(@Query("address") address: String)
}