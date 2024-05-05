
package org.example.namedlockaop.lock

import com.zaxxer.hikari.HikariDataSource
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

@Configuration
class DatasourceConfig {

    @Primary
    @Bean
    @ConfigurationProperties("spring.datasource.hikari")
    fun dataSource(): HikariDataSource {
        return DataSourceBuilder.create()
            .type(HikariDataSource::class.java)
            .build()
    }

    @Bean
    @ConfigurationProperties("userlock.datasource.hikari")
    fun userLockDataSource(): HikariDataSource {
        return DataSourceBuilder.create()
            .type(HikariDataSource::class.java)
            .build()
    }

    @Bean
    fun namedLockTemplate(): LockTemplate {
        return NamedLockTemplate(userLockDataSource())
    }
}
