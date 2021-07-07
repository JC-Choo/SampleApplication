package dev.chu.twowaybinding

object StringUtils {
    @JvmStatic
    fun capitalizeFirstCharacter(stringParam:String?):String{
        var captilizedString = ""
        if (!stringParam.isNullOrBlank()) {
            captilizedString = stringParam.substring(0, 1).toUpperCase() + stringParam.substring(1)
        }
        return captilizedString
    }
}