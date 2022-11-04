package ru.greenpix.moviecatalog.util

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import java.util.*

fun Gson.decodeJwt(jwtToken: String): JwtToken {
    val split = jwtToken.split(".")
    val body = String(Base64.getDecoder().decode(split[1]), Charsets.UTF_8)
    return fromJson(body, JwtToken::class.java)
}

data class JwtToken(
    @SerializedName("unique_name")
    val uniqueName: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("nfg")
    val notBefore: Int,

    @SerializedName("exp")
    val expirationTime: Int,

    @SerializedName("iat")
    val issuedAt: Int,

    @SerializedName("iss")
    val issuer: String,

    @SerializedName("aud")
    val audience: String
)