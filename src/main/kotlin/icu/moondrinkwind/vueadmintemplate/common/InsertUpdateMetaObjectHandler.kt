package icu.moondrinkwind.vueadmintemplate.common

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler
import org.apache.ibatis.reflection.MetaObject
import org.springframework.stereotype.Component
import java.time.LocalDateTime


@Component
class InsertUpdateMetaObjectHandler: MetaObjectHandler {
    override fun insertFill(metaObject: MetaObject?) {
        this.strictInsertFill(metaObject, "gmtCreate", LocalDateTime::class.java, LocalDateTime.now())
        this.strictInsertFill(metaObject, "gmtUpdate", LocalDateTime::class.java, LocalDateTime.now())
    }

    override fun updateFill(metaObject: MetaObject?) {
        this.strictInsertFill(metaObject, "gmtCreate", LocalDateTime::class.java, LocalDateTime.now())
    }
}