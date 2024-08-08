package icu.moondrinkwind.vueadmintemplate.service.impl

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import icu.moondrinkwind.vueadmintemplate.entity.dto.User
import icu.moondrinkwind.vueadmintemplate.mapper.UserMapper
import icu.moondrinkwind.vueadmintemplate.service.UserService
import org.springframework.stereotype.Service

@Service
class UserServiceImpl: ServiceImpl<UserMapper, User>(), UserService