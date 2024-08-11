package icu.moondrinkwind.vueadmintemplate.util

import jakarta.annotation.Resource
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * 限流通用工具
 */
@Component
class FlowUtils {
    @Resource
    private lateinit var template: StringRedisTemplate

    /**
     * 针对单次频率限制，请求成功后，在冷却时间内不得再次进行请求
     * @param key 键
     * @param blockTime 限制时间
     * @return 是否通过限流检查
     */
    fun limitOnceCheck(key: String, blockTime: Int): Boolean =
        internalCheck(key, 1, blockTime, object: LimitAction{
            override fun run(overclock: Boolean): Boolean {
                return false
            }
        })

    /**
     * 内部使用请求限制主要逻辑
     * @param key 计数键
     * @param frequency 请求频率
     * @param period 请求周期
     * @param action 限制行为与策略
     * @return 是否通过限流检查
     */
    private fun internalCheck(key: String, frequency: Int, period: Int, action: LimitAction): Boolean {
        val count: String? = template.opsForValue().get(key)
        if(count != null){
            val value: Long = Optional.ofNullable(template.opsForValue().increment(key)).orElse(0L)
            var c: Int = value.toInt()
            if(value.toInt() != c + 1){
                template.expire(key, period.toLong(), TimeUnit.SECONDS)
            }
            return action.run(value > frequency)
        }else{
            template.opsForValue().set(key, "1", period.toLong(), TimeUnit.SECONDS)
            return true
        }
    }

    /**
     * 针对单次频率限制，请求成功后，在冷却时间内不得再次请求
     * 否则将延长封禁时间
     * @param key 键
     * @param frequency 请求频率
     * @param baseTime 基础请求时间
     * @param upgradeTime 升级限制时间
     * @return 是否通过限流检查
     */
    fun limitOnceUpgradeCheck(key: String, frequency: Int, baseTime: Int, upgradeTime: Int) =
        this.internalCheck(key, frequency, baseTime, object : LimitAction{
            override fun run(overclock: Boolean): Boolean {
                if(overclock) template.opsForValue().set(key, "1", upgradeTime.toLong(), TimeUnit.SECONDS)
                return false
            }
        })


    /**
     * 内部使用，限制行为与策略
     */
    private interface LimitAction {
        fun run(overclock: Boolean): Boolean
    }
}