package com.mattam.template.config

import android.util.Log
import com.mattam.template.config.ApplicationClass.Companion.SharedPreferences
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class ReceivedCookiesInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain):Response{
        val originalResponse: Response = chain.proceed(chain.request())

        if (originalResponse.headers("Set-Cookie").isNotEmpty()) {

            val cookies = HashSet<String>()
//            "loginId=id+01; Max-Age=1000000; Expires=Sat, 10-Jul-2021 18:15:16 GMT"
            for (header in originalResponse.headers("Set-Cookie")) {
                cookies.add(header)
                Log.d("_ssafy", "intercept: $header")
            }
            
            // cookie 내부 데이터에 저장
            SharedPreferences.addUserCookie(cookies)
        }
        return originalResponse
    }
}