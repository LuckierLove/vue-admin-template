package icu.moondrinkwind.vueadmintemplate.entity.dto

import com.baomidou.mybatisplus.annotation.*
import icu.moondrinkwind.vueadmintemplate.common.ListTypeHandler
import java.time.LocalDateTime

/**
 * 数据库中的用户信息
 */
@TableName(value = "user", autoResultMap = true)
data class User(
    @TableId(type= IdType.NONE)
    @TableField(fill = FieldFill.INSERT)
    var id: String?,
    var username: String?,
    var password: String?,
    var email: String?,
    @TableField(typeHandler = ListTypeHandler::class,

        )
    var role: MutableList<String>?,
    @TableField(fill = FieldFill.INSERT)
    var gmtCreate: LocalDateTime?,
    @TableField(fill = FieldFill.INSERT_UPDATE)
    var gmtUpdate: LocalDateTime?,
){
    constructor():this(null,null,null,null,null,null,null)
}
