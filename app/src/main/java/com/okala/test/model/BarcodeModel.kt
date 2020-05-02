package com.okala.test.model

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
class BarcodeModel(
    @Id var id: Long = 0,
    var barcodeId: String,
    var barcode: String,
    var productId: String,
    var wholeQuntitys: String,
    var quntity: String,
    var mode : Int
)
