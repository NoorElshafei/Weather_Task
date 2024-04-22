package com.baims.network.utils

import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * @Created_by: Noor Elshafei
 * @Date: 4/22/2024
 */


class ForceCacheInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = chain.request().newBuilder()
       /* if (!IsInternetAvailable()) {
            builder.cacheControl(CacheControl.FORCE_CACHE)
        }*/
        return chain.proceed(builder.build());
    }
}