package icu.moondrinkwind.vueadmintemplate.entity

import com.alibaba.fastjson2.JSONObject
import com.alibaba.fastjson2.JSONWriter

/**
 * 响应实体封装类，Rest风格
 * @param code 状态码
 * @param data 响应数据
 * @param message 其他消息
 * @param T 响应数据类型
 */
class R<T> private constructor(
    var code: Int, // 状态码
    var message: String?, // 返回消息
    var data: T? // 返回数据
) {

    companion object{
        fun success(): R<Nothing?> = R(200, "success", null)
        fun <T> success(data: T): R<T> = R(200, "success", data)
        fun <T> success(data: T, message: String?): R<T> = R(200, message, data)
        fun failed(): R<Nothing?> = R(201, "failed", null)
        fun <T> failed(data: T): R<T> = R(201, "failed", data)
        fun <T> failed(data: T, message: String?): R<T> = R(201, message, data)
    }

    /**
     * 快速将数据转换成JSON字符串格式
     */
    fun asJsonString(): String = JSONObject.toJSONString(this, JSONWriter.Feature.WriteNulls)
}