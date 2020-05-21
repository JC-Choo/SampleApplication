//package dev.chu.memo.data.remote
//
//import okhttp3.ResponseBody
//import retrofit2.Converter
//import retrofit2.Retrofit
//import java.io.EOFException
//import java.lang.reflect.Type
//
//class NullOrEmptyConverterFactory: Converter.Factory() {
//
//    override fun responseBodyConverter(type: Type, annotations: Array<Annotation>, retrofit: Retrofit): Converter<ResponseBody, *>? {
//        val delegate = retrofit.nextResponseBodyConverter<Any>(this, type, annotations)
//        return Converter<ResponseBody, Any?> {
//            try {
//                if (it.contentLength() != 0L) {
//                    delegate.convert(it)
//                } else {
//                    null
//                }
//            } catch (e: EOFException) {
//                null
//            }
//        }
//    }
//}