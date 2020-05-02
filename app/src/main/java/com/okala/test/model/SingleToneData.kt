package com.okala.test.model

object SingleToneData {
    var personalDigitalAssistantId = 0
    var quantity = 10
    var isComplete = false
    var mode = 0

    fun clean() {
        isComplete = false
        quantity = 0
        personalDigitalAssistantId = 0
    }
}
