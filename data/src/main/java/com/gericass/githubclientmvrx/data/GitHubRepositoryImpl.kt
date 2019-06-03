package com.gericass.githubclientmvrx.data

import android.content.Context
import android.util.Base64
import com.gericass.githubclientmvrx.common.extensions.observeOnMainThread
import com.gericass.githubclientmvrx.data.model.Event
import com.gericass.githubclientmvrx.data.model.LoginUser
import com.gericass.githubclientmvrx.data.model.ReceiveEvent
import com.gericass.githubclientmvrx.data.remote.GitHubClient
import io.reactivex.Observable
import retrofit2.Retrofit
import timber.log.Timber
import java.security.KeyStore
import javax.crypto.Cipher

class GitHubRepositoryImpl(
    context: Context,
    private val retrofit: Retrofit
) : GitHubRepository {

    private val client by lazy { retrofit.create(GitHubClient::class.java) }

    private val pref = context.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE)

    override fun getEvents(): Observable<List<Event>> {
        return client.getEvents(token())
            .observeOnMainThread()
    }

    override fun getReceiveEvents(userName: String): Observable<List<ReceiveEvent>> {
        return client.getReceiveEvents(token(), userName)
            .observeOnMainThread()
    }

    override fun getLoginUser(): Observable<LoginUser> {
        return client.getLoginUser(token())
            .observeOnMainThread()
    }

    private fun token(): String {
        val encrypted = pref.getString(TOKEN_KEY, "")
        encrypted?.let {
            Decryptor.decrypt(it)?.let { token ->
                Timber.tag("TIMTIM").d(token)
                return "token $token"
            } ?: return ""
        } ?: return ""
    }

    private object Decryptor {

        fun decrypt(encryptedText: String): String? {
            val keyStore = KeyStore.getInstance(PROVIDER)
            keyStore.load(null)
            if (!keyStore.containsAlias(TOKEN_ALIAS)) {
                return null
            }
            // 秘密鍵で復号化
            val privateKey = keyStore.getKey(TOKEN_ALIAS, null)
            val cipher = Cipher.getInstance(CIPHER_TRANSFORMATION)
            cipher.init(Cipher.DECRYPT_MODE, privateKey)
            val bytes = Base64.decode(encryptedText, Base64.DEFAULT)

            val b = cipher.doFinal(bytes)
            return String(b)
        }

    }
}