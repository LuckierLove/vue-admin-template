package icu.moondrinkwind.vueadmintemplate.entity

import com.baomidou.mybatisplus.annotation.FieldFill
import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableId
import icu.moondrinkwind.vueadmintemplate.common.ListTypeHandler
import java.time.LocalDateTime

data class User(
    @TableId(type= IdType.NONE)
    @TableField(fill = FieldFill.INSERT)
    var id: String?,
    var username: String?,
    var password: String?,
    var email: String?,
    @TableField(typeHandler = ListTypeHandler::class)
    var role: MutableList<String>?,
    @TableField(fill = FieldFill.INSERT)
    var gmtCreate: LocalDateTime?,
    @TableField(fill = FieldFill.INSERT_UPDATE)
    var gmtUpdate: LocalDateTime?,
){
    constructor():this(null,null,null,null,null,null,null)
}
