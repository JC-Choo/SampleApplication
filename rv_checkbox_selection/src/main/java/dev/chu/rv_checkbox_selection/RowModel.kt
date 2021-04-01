package dev.chu.rv_checkbox_selection

data class RowModel(
    val rowType: RowType,
    val productName: String,
    val category: String,
    var isChecked: Boolean = true
)

enum class RowType(val id: Int) {

    TopHeader(1),
    CatHeader(2),
    ProductRow(3);

}