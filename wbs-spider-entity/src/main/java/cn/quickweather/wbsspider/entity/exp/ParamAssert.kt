@file:JvmName("CommonUtils")

package cn.quickweather.wbsspider.entity.exp

import com.alibaba.fastjson.JSONObject
import java.io.Closeable
import java.util.regex.Pattern

/**
 * Created by maweihao on 5/20/21
 */
private val PATTERN_IP_ADDRESS = Pattern.compile(
    "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$")!!

fun isBlank(str: String?): Boolean {
    return str?.isBlank() ?: true
}

fun nullToEmpty(s: String?): String {
    return s ?: ""
}

fun equals(a: Int?, b: Int?): Boolean {
    return a?.equals(b) ?: false
}

fun isPositive(d: Double?): Boolean {
    return d != null && d > 0
}

fun isIpAddressValid(ip: String?): Boolean {
    if (ip.isNullOrBlank()) {
        return false
    }
    val matcher = PATTERN_IP_ADDRESS.matcher(ip)
    return matcher.matches()
}

fun isNumeric(str: String?): Boolean {
    return str?.toCharArray()?.all(Character::isDigit) ?: false
}

fun Closeable?.close() {
    try {
        this?.close()
    } catch (ignored: Exception) {}
}

fun <T> lazyUnsafe(initializer: () -> T): Lazy<T> = lazy(LazyThreadSafetyMode.NONE, initializer)

fun Any.toJsonStr(): String {
    return JSONObject.toJSONString(this)
}