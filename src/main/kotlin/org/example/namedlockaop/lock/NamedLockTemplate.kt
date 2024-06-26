package org.example.namedlockaop.lock

import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import java.sql.Connection
import java.sql.PreparedStatement
import javax.sql.DataSource

private const val GET_LOCK = "SELECT GET_LOCK(?, ?)"
private const val RELEASE_LOCK = "SELECT RELEASE_LOCK(?)"
private const val EXCEPTION_MESSAGE = "LOCK 을 수행하는 중에 오류가 발생하였습니다."

@Component
class NamedLockTemplate(
    @Qualifier("userLockDataSource")
    private val dataSource: DataSource
) : LockTemplate {

    private val log = KotlinLogging.logger { }

    override fun execute(lockKey: String, timeout: Int, action: () -> Any?): Any? {
        dataSource.connection.use { con ->
            try {
                lock(con, lockKey, timeout)
                log.info { "Get Lock - $lockKey, (timeout=$timeout)" }
                return action()
            } finally {
                log.info { "Release Lock - $lockKey"}
                unlock(con, lockKey)
            }
        }
    }

    private fun lock(con: Connection, lockName: String, timeoutMills: Int) {
        con.prepareStatement(GET_LOCK)
            .use { ps ->
                ps.setString(1, lockName)
                ps.setInt(2, timeoutMills)

                ps.executeAndCheck()
            }
    }

    fun unlock(con: Connection, lockKey: String) {
        con.prepareStatement(RELEASE_LOCK)
            .use { ps ->
                ps.setString(1, lockKey)

                ps.executeAndCheck()
            }
    }

    private fun PreparedStatement.executeAndCheck() {
        executeQuery().use { rs ->
            check(rs.next()) { EXCEPTION_MESSAGE }
            check(rs.getInt(1) == 1) { EXCEPTION_MESSAGE }
        }
    }
}

