package br.com.edsb.hackathon.utils.encoding

import android.util.Base64
import br.com.edsb.hackathon.utils.DafaultException
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset


class Encoding {
    companion object {
        @Throws(DafaultException::class)
        fun decode(target: String): ByteArray {
            try {
                return target.toByteArray(charset("UTF-8"))
            } catch (e: UnsupportedEncodingException) {
                throw DafaultException("decode", e)
            }

        }

        @Throws(DafaultException::class)
        fun encode(target: ByteArray): String {
            return encode(target, "UTF-8")
        }

        @Throws(DafaultException::class)
        fun encode(target: ByteArray, encoding: String?): String {

            if (encoding == null)
                return String(target)

            try {
                return String(target, Charset.forName(encoding))
            } catch (e: UnsupportedEncodingException) {
                throw DafaultException("encode", e)
            }

        }

        @Throws(DafaultException::class)
        fun b64DecodeUTF8(target: String): String {
            return Encoding.encode(Base64.decode(target, Base64.NO_WRAP))
        }

        fun b64Decode(target: String): ByteArray {
            return Base64.decode(target, Base64.NO_WRAP)
        }

        fun b64Decode(target: ByteArray, flags: Int): ByteArray {
            return Base64.decode(target, flags)
        }

        @Throws(DafaultException::class)
        fun b64EncodeBytes(target: String): ByteArray {
            return Base64.encode(Encoding.decode(target), Base64.NO_WRAP)
        }

        @Throws(DafaultException::class)
        fun b64Encode(target: String): String {
            return b64Encode(Encoding.decode(target), Base64.NO_WRAP)
        }

        @Throws(DafaultException::class)
        fun b64Encode(target: ByteArray): String {
            return b64Encode(target, Base64.NO_WRAP)
        }

        @Throws(DafaultException::class)
        fun b64Encode(target: ByteArray, flags: Int): String {
            return Encoding.encode(Base64.encode(target, flags))
        }

        fun certHex(data: ByteArray?) = DigestHelper.certHex(data)
    }
}