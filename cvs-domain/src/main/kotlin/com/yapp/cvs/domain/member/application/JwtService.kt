package com.yapp.cvs.domain.member.application

import com.yapp.cvs.domain.member.entity.Member
import com.yapp.cvs.exception.BadTokenException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.lang.Exception
import java.nio.charset.StandardCharsets
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

@Service
class JwtService(
    @Value("\${jwt.secret-key}")
    private val secretKey: String
) {
    fun generate(member: Member): String  {
        val key = Keys.hmacShaKeyFor(secretKey.toByteArray(StandardCharsets.UTF_8))
        return Jwts.builder()
            .setSubject(member.memberId!!.toString())
            .setIssuedAt(Date.from(LocalDateTime.now().atZone(ZoneId.of("Asia/Seoul")).toInstant()))
            .setExpiration(Date.from(LocalDateTime.now().plusYears(1L).atZone(ZoneId.of("Asia/Seoul")).toInstant()))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()
    }

    fun parse(token: String): Long {
        val key = Keys.hmacShaKeyFor(secretKey.toByteArray(StandardCharsets.UTF_8))

        val parsedObject = try {
             Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
        } catch (e: Exception) {
            throw BadTokenException("잘못된 토큰입니다")
        }

        if(parsedObject.body.expiration < Date.from(LocalDateTime.now().atZone(ZoneId.of("Asia/Seoul")).toInstant())) {
            throw BadTokenException("만료된 토큰입니다")
        }

        return parsedObject.body.subject.toLong()
    }
}