package com.example.getir.presention.cartView

import android.content.SharedPreferences
import com.example.getir.domain.card.CartItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

class CartPrefs @Inject constructor(
    private val sharedPrefs: SharedPreferences,
    private val gson: Gson
) {
    fun saveCart(items: List<CartItem>) {
        val json = gson.toJson(items)
        sharedPrefs.edit().putString("saved_cart", json).apply()
    }

    fun getCart(): List<CartItem> {
        val json = sharedPrefs.getString("saved_cart", null) ?: return emptyList()
        val type = object : TypeToken<List<CartItem>>() {}.type
        return gson.fromJson(json, type)
    }
}