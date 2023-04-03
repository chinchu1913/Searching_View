package com.example.search_image.data.remote.dto


import com.google.gson.annotations.SerializedName

data class AncestryDto(
    @SerializedName("category")
    val category: CategoryDto?,
    @SerializedName("subcategory")
    val subcategory: SubcategoryDto?,
    @SerializedName("type")
    val type: TypeDto?
)