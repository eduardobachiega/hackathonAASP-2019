package br.com.edsb.hackathon.utils.encoding

import br.com.edsb.hackathon.utils.DafaultException
import java.security.GeneralSecurityException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*
import javax.crypto.Cipher
import javax.crypto.Mac
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec


class Digest {
    private var pswdIterations = 1050
    private val keySize = 256

    enum class RETURN {
        DATA,
        HEX_STRING,
        CERT_FINGERPRINT,
        BAS64
    }

    fun setIterations(iterations: Int) {
        pswdIterations = iterations
    }

    private fun interate(password: String, salt: String): SecretKeySpec {

        try {
            val saltBytes = Encoding.decode(salt)
            val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
            val spec = PBEKeySpec(password.toCharArray(), saltBytes, pswdIterations, keySize)
            val secretKey = factory.generateSecret(spec)
            return SecretKeySpec(secretKey.getEncoded(), "AES")
        } catch (e: Exception) {
            e.printStackTrace()
            throw DafaultException("interate", e)
        }

    }

    private fun crypt(data: ByteArray, keySpec: SecretKeySpec, ivSpec: IvParameterSpec, mode: Int, instance: String): ByteArray {
        try {
            val cipher = Cipher.getInstance(instance)
            cipher.init(mode, keySpec, ivSpec)
            return cipher.doFinal(data)
        } catch (e: GeneralSecurityException) {
            e.printStackTrace()
            throw DafaultException("SimpleCrypt: " + e.message, e)
        }

    }

    fun encrypt(plainText: String, iv: String, password: String, salt: String): String {

        val data = Encoding.decode(plainText)
        val ivBytes = Arrays.copyOfRange(Encoding.decode(iv), 0, 16)

        val keySpec = interate(password, salt)
        val ivSpec = IvParameterSpec(ivBytes)
        val mode = Cipher.ENCRYPT_MODE
        val instance = "AES/CBC/PKCS5Padding"

        val encrypted = crypt(data, keySpec, ivSpec, mode, instance)
        return Encoding.b64Encode(encrypted)
    }

    fun encrypt(data: ByteArray, ivBytes: ByteArray, password: String, salt: String): String {

        val keySpec = interate(password, salt)
        val ivSpec = IvParameterSpec(ivBytes)
        val mode = Cipher.ENCRYPT_MODE
        val instance = "AES/CBC/PKCS5Padding"

        val encrypted = crypt(data, keySpec, ivSpec, mode, instance)
        return Encoding.b64Encode(encrypted)
    }

    fun decrypt(encryptedText: String, iv: String, password: String, salt: String): ByteArray {

        val data = Encoding.b64Decode(encryptedText)
        val ivBytes = Arrays.copyOfRange(Encoding.decode(iv), 0, 16)

        val keySpec = interate(password, salt)
        val ivSpec = IvParameterSpec(ivBytes)
        val mode = Cipher.DECRYPT_MODE
        val instance = "AES/CBC/PKCS5Padding"

        return crypt(data, keySpec, ivSpec, mode, instance)
    }

    fun decrypt(dataBytes: ByteArray, ivBytes: ByteArray, password: String, salt: String): ByteArray {

        val keySpec = interate(password, salt)
        val ivSpec = IvParameterSpec(ivBytes)
        val mode = Cipher.DECRYPT_MODE
        val instance = "AES/CBC/PKCS5Padding"

        return crypt(dataBytes, keySpec, ivSpec, mode, instance)
    }


    private fun parser(hashData: ByteArray, hashReturn: RETURN): Any? {
        var result: Any? = null
        when (hashReturn) {
            Digest.RETURN.DATA -> result = hashData
            Digest.RETURN.HEX_STRING -> result = Encoding.certHex(hashData)
            Digest.RETURN.CERT_FINGERPRINT -> result = Encoding.certHex(hashData)
            Digest.RETURN.BAS64 -> result = Encoding.b64Encode(hashData)
        }
        return result
    }

    private fun md5(subject: ByteArray): ByteArray {
        try {
            return MessageDigest.getInstance("MD5").digest(subject)
        } catch (e: NoSuchAlgorithmException) {
            throw DafaultException("MD5", e)
        }

    }

    @Throws(DafaultException::class)
    fun md5(subject: ByteArray, hashReturn: RETURN): Any? {
        val hashData = md5(subject)
        return parser(hashData, hashReturn)
    }

    @Throws(DafaultException::class)
    fun md5(subject: String, hashReturn: RETURN): Any? {
        val hashData = md5(Encoding.decode(subject))
        return parser(hashData, hashReturn)
    }

    private fun sha1(subject: ByteArray): ByteArray {
        try {
            return MessageDigest.getInstance("SHA-1").digest(subject)
        } catch (e: NoSuchAlgorithmException) {
            throw DafaultException("SHA-1", e)
        }

    }

    @Throws(DafaultException::class)
    fun sha1(subject: ByteArray, hashReturn: RETURN): Any? {
        val hashData = sha1(subject)
        return parser(hashData, hashReturn)
    }

    @Throws(DafaultException::class)
    fun sha1(subject: String, hashReturn: RETURN): Any? {
        val hashData = sha1(Encoding.decode(subject))
        return parser(hashData, hashReturn)
    }

    private fun sha256(subject: ByteArray): ByteArray {
        try {
            return MessageDigest.getInstance("SHA-256").digest(subject)
        } catch (e: NoSuchAlgorithmException) {
            throw DafaultException("SHA-256", e)
        }

    }

    @Throws(DafaultException::class)
    fun sha256(subject: ByteArray, hashReturn: RETURN): Any? {
        val hashData = sha256(subject)
        return parser(hashData, hashReturn)
    }

    @Throws(DafaultException::class)
    fun sha256(subject: String, hashReturn: RETURN): Any? {
        val hashData = sha256(Encoding.decode(subject))
        return parser(hashData, hashReturn)
    }

    private fun HMAC(crypto: String, keyBytes: ByteArray, text: ByteArray): ByteArray {
        try {
            val hmac = Mac.getInstance(crypto)
            val macKey = SecretKeySpec(keyBytes, "RAW")
            hmac.init(macKey)
            return hmac.doFinal(text)
        } catch (gse: GeneralSecurityException) {
            throw DafaultException("HMAC", gse)
        }

    }

    @Throws(DafaultException::class)
    fun HMAC(crypto: String, keyBytes: ByteArray, text: ByteArray, hashReturn: RETURN): Any? {
        val hashData = HMAC(crypto, keyBytes, text)
        return parser(hashData, hashReturn)
    }

    private val ALLOWED_CHARACTERS = "0123456789qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM"

    fun getRandomString(sizeOfRandomString: Int): String {
        val random = Random()
        val sb = StringBuilder(sizeOfRandomString)
        for (i in 0 until sizeOfRandomString)
            sb.append(ALLOWED_CHARACTERS[random.nextInt(ALLOWED_CHARACTERS.length)])
        return sb.toString()
    }
}