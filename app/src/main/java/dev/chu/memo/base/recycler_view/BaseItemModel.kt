package dev.chu.basemodule.recycler_view

abstract class BaseItemModel(val id: String)

//abstract class BaseItemModel(@NonNull id: String?) {
//    @get:NonNull
//    @NonNull
//    val id: String
//
//    override fun equals(@Nullable obj: Any?): Boolean {
//        if (obj == null) {
//            return false
//        }
//        // check BaseItemModel is a superclass of obj's Class
//        return if (!BaseItemModel::class.java.isAssignableFrom(obj.javaClass)) {
//            false
//        } else id == (obj as BaseItemModel).id
//    }
//
//    init {
//        this.id = Objects.requireNonNull(id, "id must not be null")
//    }
//}