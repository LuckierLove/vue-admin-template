package icu.moondrinkwind.vueadmintemplate.mapper

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import icu.moondrinkwind.vueadmintemplate.entity.User
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper

@Mapper
interface UserMapper: BaseMapper<User>