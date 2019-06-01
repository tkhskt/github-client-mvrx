package com.gericass.githubclientmvrx.data

import android.content.Context
import android.security.KeyPairGeneratorSpec
import android.util.Base64
import androidx.core.content.edit
import com.gericass.githubclientmvrx.common.extensions.observeOnMainThread
import com.gericass.githubclientmvrx.data.model.Token
import com.gericass.githubclientmvrx.data.remote.GitHubClient
import io.reactivex.Completable
import io.reactivex.Observable
import retrofit2.Retrofit
import java.math.BigInteger
import java.security.KeyPairGenerator
import java.security.KeyStore
import java.util.*
import javax.crypto.Cipher
import javax.security.auth.x500.X500Principal

class AuthRepositoryImpl(
    private val context: Context,
    private val retrofit: Retrofit
) : AuthRepository {

    private val preferenceKey = "github_mvrx"

    private val tokenKey = "token"

    private val client by lazy { retrofit.create(GitHubClient::class.java) }

    private val sharedPreferences = context.getSharedPreferences(preferenceKey, Context.MODE_PRIVATE)

    override fun getToken(clientId: String, clientSecret: String, code: String): Observable<Token> {
        val request = mapOf(
            "client_id" to clientId,
            "client_secret" to clientSecret,
            "code" to code
        )
        return client.getToken("application/json", request)
            .observeOnMainThread()
    }

    override fun saveToken(token: String): Completable {
        return Completable.create { emitter ->
            val encrypted = Encryptor.encrypt(context, token)
            sharedPreferences.edit { putString(tokenKey, encrypted) }
            emitter.onComplete()
        }
    }

    override fun deleteToken(): Completable {
        return Completable.create { emitter ->
            sharedPreferences.edit { remove(tokenKey) }
            emitter.onComplete()
        }
    }

    override fun isLoggedIn(): Boolean {
        sharedPreferences.getString(tokenKey, "").let {
            return !it.isNullOrEmpty()
        }
    }

    private object Encryptor {
        const val PROVIDER = "AndroidKeyStore"
        const val ALGORITHM = "RSA"
        const val CIPHER_TRANSFORMATION = "RSA/ECB/PKCS1Padding"
        const val TOKEN_ALIAS = "token"

        fun encrypt(context: Context, plainText: String): String {
            val keyStore = KeyStore.getInstance(PROVIDER)
            keyStore.load(null)

            // キーペアがない場合生成
            if (!keyStore.containsAlias(TOKEN_ALIAS)) {
                val keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM, PROVIDER)
                keyPairGenerator.initialize(createKeyPairGeneratorSpec(context))
                keyPairGenerator.generateKeyPair()
            }
            val publicKey = keyStore.getCertificate(TOKEN_ALIAS).publicKey

            // 公開鍵で暗号化
            val cipher = Cipher.getInstance(CIPHER_TRANSFORMATION)
            cipher.init(Cipher.ENCRYPT_MODE, publicKey)
            val bytes = cipher.doFinal(plainText.toByteArray(Charsets.UTF_8))

            // SharedPreferencesに保存しやすいようにBase64でString化
            return Base64.encodeToString(bytes, Base64.DEFAULT)
        }

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

        /**
         * キーペアを生成する
         */
        fun createKeyPairGeneratorSpec(context: Context): KeyPairGeneratorSpec {
            val start = Calendar.getInstance()
            val end = Calendar.getInstance()
            end.add(Calendar.YEAR, 100)

            return KeyPairGeneratorSpec.Builder(context)
                .setAlias(TOKEN_ALIAS)
                .setSubject(X500Principal(String.format("CN=%s", TOKEN_ALIAS)))
                .setSerialNumber(BigInteger.valueOf(1000000))
                .setStartDate(start.time)
                .setEndDate(end.time)
                .build()
        }

    }
}