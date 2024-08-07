package icu.moondrinkwind.vueadmintemplate.common

import org.apache.ibatis.type.BaseTypeHandler
import org.apache.ibatis.type.JdbcType
import java.sql.CallableStatement
import java.sql.PreparedStatement
import java.sql.ResultSet

class ListTypeHandler: BaseTypeHandler<MutableList<String>>() {
    override fun setNonNullParameter(
        ps: PreparedStatement?,
        i: Int,
        parameter: MutableList<String>?,
        jdbcType: JdbcType?
    ) {
        var result = ""
        parameter?.forEach {
            result += it
            result +=","
        }
        ps?.setString(i, result)
    }

    override fun getNullableResult(rs: ResultSet?, columnName: String?): MutableList<String>? {
        val str = rs?.getString(columnName)
        return str?.split(",")?.toMutableList()
    }

    override fun getNullableResult(rs: ResultSet?, columnIndex: Int): MutableList<String>? {
        val str = rs?.getString(columnIndex)
        return str?.split(",")?.toMutableList()
    }

    override fun getNullableResult(cs: CallableStatement?, columnIndex: Int): MutableList<String>? {
        val str = cs?.getString(columnIndex)
        return str?.split(",")?.toMutableList()
    }
}