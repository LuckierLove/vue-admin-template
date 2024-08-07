package icu.moondrinkwind.vueadmintemplate.entity

class R private constructor(
    private var code: Int, // 状态码
    private var message: String, // 返回消息
    private var data: Any? // 返回数据
) {

    companion object{
        fun success(): R = R(200, "success", null)
        fun success(data: Any?): R = R(200, "success", data)
        fun success(data: Any?, message: String): R = R(200, message, data)
        fun failed(): R = R(201, "failed", null)
        fun failed(data: Any?): R = R(201, "failed", data)
        fun failed(data: Any?, message: String): R = R(201, message, data)
    }
}