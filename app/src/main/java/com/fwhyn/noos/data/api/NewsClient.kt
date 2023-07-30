package com.fwhyn.noos.data.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

class NewsClient {
    companion object {
        private const val BASE_URL = "https://newsapi.org/v2/"
        const val API_KEY = "32faed07c15f49d3862da0a8d9940430"
    }

    val retrofit: Retrofit
        get() {
            return Retrofit.Builder().baseUrl(BASE_URL)
                .client(unsafeOkHttpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

    // Create a trust manager that does not validate certificate chains
    private val unsafeOkHttpClient: OkHttpClient.Builder
        get() = try {
            // Create a trust manager that does not validate certificate chains
            val trustAllCertificates = arrayOf<TrustManager>(
                object : X509TrustManager {
                    @Throws(CertificateException::class)
                    override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
                        // No implementation
                    }

                    @Throws(CertificateException::class)
                    override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
                        // No implementation
                    }

                    override fun getAcceptedIssuers(): Array<X509Certificate> {
                        return arrayOf()
                    }
                }
            )

            // Install the all-trusting trust manager
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCertificates, SecureRandom())

            // Create an ssl socket factory with our all-trusting manager
            val sslSocketFactory = sslContext.socketFactory
            val builder = OkHttpClient.Builder()
            builder
                .sslSocketFactory(sslSocketFactory, trustAllCertificates[0] as X509TrustManager)
                .hostnameVerifier { _, _ -> true }

            builder
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
}