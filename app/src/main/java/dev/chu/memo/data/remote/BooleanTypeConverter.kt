//package dev.chu.memo.data.remote
//
//import com.google.gson.JsonDeserializationContext
//import com.google.gson.JsonDeserializer
//import com.google.gson.JsonElement
//import java.lang.reflect.Type
//
//class BooleanTypeConverter: JsonDeserializer<Boolean> {
//
//    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Boolean {
//        return if (json?.isJsonPrimitive == true) {
//            when {
//                json.asJsonPrimitive.isBoolean -> json.asJsonPrimitive.asBoolean
//                json.asJsonPrimitive.isNumber -> json.asJsonPrimitive.asInt == 1
//                json.asJsonPrimitive.isString -> json.asJsonPrimitive.asString == "y"
//                else -> throw IllegalArgumentException("Not a number or y/n or boolean type")
//            }
//        } else {
//            throw IllegalArgumentException("Not a number or y/n or boolean type")
//        }
//    }
//}