package dev.chu.memo.ui.permission

/**
 * 권한들로 뭘 할것인지에 대한 요청사항
 */
enum class BusinessRequest {
    ADD_PHOTO_TO_ADVERT_FROM_CAMERA,
    READ_QR_FROM_CAMERA,
    TAKE_PHOTO_FOR_FIRM_LOGO_FROM_CAMERA,
    ADD_PHOTO_TO_ADVERT_FROM_GALLERY,
    PICK_PHOTO_FOR_FIRM_LOGO_FROM_GALLERY,
    LOCATION_TO_SHOW_ON_MAP,
    VIDEO_CALL
}