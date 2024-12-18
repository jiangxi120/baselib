package com.rocky.demo

import android.net.Uri
import android.util.Log


object TestUriParse {

    fun test() {
//        val uri = Uri.parse("http://www.baidu.com/path1/path2?key1=value1&key2=http%3A%2F%2Fimg.vidmatefilm.org%2Fd4%2Fpic%2Fcms%2Fwelcome_ad_wall%2F1677500433.95.png%3Fxxx%3Dyyy")
//        val uri = Uri.parse("www.baidu.com/path1/path2?key1=value1&key2=http%3A%2F%2Fimg.vidmatefilm.org%2Fd4%2Fpic%2Fcms%2Fwelcome_ad_wall%2F1677500433.95.png%3Fxxx%3Dyyy")
        val uri = Uri.parse("https://m.youtube.com/live/lvbdhV87EDY?si=ict3igD3otI4_dqT#")
        Log.d(
            "testdemo",
            """
                uri=${uri}
                host=${uri.host}
                path=${uri.path}
                port=${uri.port}
                authority=${uri.authority}
                query=${uri.query}
                fragment=${uri.fragment}
                pathSegments=${uri.pathSegments}
                scheme=${uri.scheme}
                queryParameterNames=${uri.queryParameterNames}
                value1=${uri.getQueryParameter("key1")}
                value2=${uri.getQueryParameter("key2")}
                value3=${uri.getQueryParameter("key3")}
                xxx=${uri.getQueryParameter("xxx")}
                ${uri.normalizeScheme()}
                encodedPath=${uri.encodedPath}
                encodedQuery=${uri.encodedQuery}
            """.trimIndent()
        )
    }

    fun String.urlHostPath(): String {
        val index = this.indexOf("?")
        return if(index < 0){
            this
        } else {
            this.substring(0, index)
        }
    }

}