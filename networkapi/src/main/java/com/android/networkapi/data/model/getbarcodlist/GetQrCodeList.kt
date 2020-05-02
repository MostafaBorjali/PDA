package com.android.networkapi.data.model.getbarcodlist

data class GetQrCodeList(
    val ad: Ad,
    val `data`: List<QRData>,
    val page: Int,
    val per_page: Int,
    val total: Int,
    val total_pages: Int
)