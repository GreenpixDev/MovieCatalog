package ru.greenpix.moviecatalog.repository.impl

import android.content.Context
import ru.greenpix.moviecatalog.R
import ru.greenpix.moviecatalog.repository.JwtRepository

class JwtRepositoryImpl(
    private val context: Context
) : JwtRepository {

    override fun getToken(): String? {
        return context.getSharedPreferences(
            context.getString(R.string.preference_jwt),
            Context.MODE_PRIVATE
        )?.getString(
            context.getString(R.string.key_token),
            null
        )
    }

    override fun saveToken(token: String) {
        val sharedPref = context.getSharedPreferences(
            context.getString(R.string.preference_jwt),
            Context.MODE_PRIVATE
        ) ?: return
        with (sharedPref.edit()) {
            putString(
                context.getString(R.string.key_token),
                token
            )
            apply()
        }
    }

    override fun deleteToken() {
        val sharedPref = context.getSharedPreferences(
            context.getString(R.string.preference_jwt),
            Context.MODE_PRIVATE
        ) ?: return
        with (sharedPref.edit()) {
            remove(context.getString(R.string.key_token))
            apply()
        }
    }
}