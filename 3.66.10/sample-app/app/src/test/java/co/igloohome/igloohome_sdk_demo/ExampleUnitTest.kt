package co.igloohome.igloohome_sdk_demo

import java.util.Base64
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    fun arrayInt2Base64String(ints: IntArray) : String? {
        val bytes =
            ints.foldIndexed(ByteArray(ints.size)) { i, a, v -> a.apply { set(i, v.toByte()) } }
        val base64String: String? = Base64.getEncoder().encodeToString(bytes)
        return base64String
    }

    @Test
    fun adminKeyConversion_isCorrect() {
        val ints = intArrayOf(105,187,125,184,162,3,26,190,229,74,178,136,80,126,48,58,58,148,223,230,138,206,134,147,241,95,233,165,156,165,39,252,78,142,249,224,247,91,14,22,149,65,129,18,58,206,12,14,42,96,85,81,137,87,82,13,2,97,25,42,70,16,38,65,35,147,232,106,182,55,174,101,178,251,171,41,173,143,235,47,11,210,146,40,10,31,133,149,230,145,85,242,112,31,187,105,240,212,77,220,240,219,222,209,88,133,1,236,45,164,126,23,129,92,115,241,114,18,182,3,11,70,19,201,23,121,160,20,23,8,226,185,219,72,208,170,142,51,39)
        val base64String = arrayInt2Base64String(ints)
        assertNotNull(base64String)
        println(base64String)
    }
}
