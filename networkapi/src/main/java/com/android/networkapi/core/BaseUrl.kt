package com.android.networkapi.core

enum class UrlType {
    Public,
    Local

}

 object BaseUri {


    var baseHost = "baseUrl"//baseUrl
    var baseUrl = "https://$baseHost"//baseUrl


}

class Server {

    companion object {

        fun setServerType(urlType: UrlType) {

            when (urlType) {

                UrlType.Public -> {
                    BaseUri.baseHost = "reqres.in/api/"
                    BaseUri.baseUrl = "https://${BaseUri.baseHost}"//baseUrl

                }
                UrlType.Local -> {

                    BaseUri.baseHost = "reqres.in/api/"
                    BaseUri.baseUrl = "http://${BaseUri.baseHost}"//baseUrl
                }
            }

        }
    }


}