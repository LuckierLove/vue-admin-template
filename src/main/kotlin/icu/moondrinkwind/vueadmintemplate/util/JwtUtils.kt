package icu.moondrinkwind.vueadmintemplate.util

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.interfaces.DecodedJWT
import jakarta.annotation.Resource
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.max

/**
 * 用于处理Jwt令牌的工具类
 */
@Component
class JwtUtils {
    //用于给Jwt令牌签名校验的秘钥
    @Value("\${spring.security.jwt.key}")
    private lateinit var key: String
    //令牌的过期时间，以小时为单位
    @Value("\${spring.security.jwt.expire}")
    private var expire = 0
    //为用户生成Jwt令牌的冷却时间，防止刷接口频繁登录生成令牌，以秒为单位
    @Value("\${spring.security.jwt.limit.base}")
    private var limitBase = 0
    //用户如果继续恶意刷令牌，更严厉的封禁时间
    @Value("\${spring.security.jwt.limit.upgrade}")
    private var limitUpgrade = 0
    //判定用户在冷却时间内，继续恶意刷令牌的次数
    @Value("\${spring.security.jwt.limit.frequency}")
    private var limitFrequency = 0
    @Resource
    private lateinit var template: StringRedisTemplate
    @Resource
    private lateinit var flow: FlowUtils

    /**
     * 验证Token是否被列入Redis黑名单
     * @param uuid 令牌ID
     * @return 是否成功
     */
    private fun isInvalidToken(uuid: String) = true.equals(template.hasKey(Const.JWT_BLACK_LIST + uuid))

    /**
     * 将Token列入Redis黑名单
     * @param uuid 令牌ID
     * @param time 过期时间
     * @return 是否成功
     */
    private fun deleteToken(uuid: String, time: Date): Boolean{
        if(this.isInvalidToken(uuid)) return false

        val now = Date()
        val expire: Long = max(time.time - now.time, 0)
        template.opsForValue().set(Const.JWT_BLACK_LIST + uuid, "", expire, TimeUnit.MILLISECONDS)
        return true
    }

    /**
     * 校验并转换请求头中的Token令牌
     * @param headerToken 请求头中的Token
     * @return 转换后的令牌
     */
    private fun convertToken(headerToken: String?): String?{
        if(headerToken == null || !headerToken.startsWith("Bearer ")) return null
        return headerToken.substring(7)
    }

    /**
     * 将Jwt对象当中的用户ID提取出来
     * @param jwt 已解析的Jwt对象
     * @return 用户ID
     */
    fun toId(jwt: DecodedJWT) = jwt.claims["id"]?.asString()

    /**
     * 将Jwt对象的内容封装成UserDetails
     * @param jwt 已解析的Jwt对象
     * @return UserDetails
     */
    fun toUser(jwt: DecodedJWT): UserDetails{
        val claims = jwt.claims
        val authorities = claims["authorities"]?.asList(String::class.java)?.map { SimpleGrantedAuthority(it) }
        return User
            .withUsername(claims["username"].toString())
            .password("******")
            .authorities(authorities)
            .build()
    }

    /**
     * 根据UserDetails生成对应的Jwt令牌
     * @param user 用户信息
     * @return Jwt令牌
     */
    fun createJwt(user: UserDetails, username: String, userId: String): String?{
        if(this.frequencyCheck(userId)){
            val algorithm = Algorithm.HMAC256(key)
            val expire = this.expireTime()
            return JWT.create()
                .withJWTId(UUID.randomUUID().toString())
                .withClaim("id", userId)
                .withClaim("name", username)
                .withClaim("authorities", user.authorities.map(GrantedAuthority::getAuthority))
                .withExpiresAt(expire)
                .withIssuedAt(Date())
                .sign(algorithm)
        }else{
            return null
        }
    }

    /**
     * 频率检测，防止高频申请Jwt令牌，并采用阶段封禁机制
     * 如果在已经无法登录的情况下继续刷，则封禁更长时间
     * @param userId 用户ID
     * @return 是否通过频率检测
     */
    private fun frequencyCheck(userId: String) = flow.limitOnceUpgradeCheck(key, limitFrequency, limitBase, limitUpgrade)


    /**
     * 让指定Jwt令牌失效
     * @param headerToken 请求头中携带的令牌
     * @return 是否成功
     */
    fun invalidateJwt(headerToken: String): Boolean{
        val token = this.convertToken(headerToken)
        val algorithm = Algorithm.HMAC256(key)
        val jwtVerifier = JWT.require(algorithm).build()
        try{
            val decodedJWT = jwtVerifier.verify(token)
            return deleteToken(decodedJWT.id, decodedJWT.expiresAt)
        }catch (e: JWTVerificationException){
            return false
        }
    }

    /**
     * 根据配置快速计算过期时间
     * @return 过期时间
     */
    fun expireTime(): Date{
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.HOUR, expire)
        return calendar.time
    }
}