package com.meteoro.omdbarch.persistance

import android.content.SharedPreferences
import android.content.SharedPreferences.Editor

internal object FakePreferences : SharedPreferences {

    val UNSUPPORTED_OPERATION = IllegalAccessError("You are not supposed to call this method")
    var brokenMode = false

    val storage = mutableSetOf<String>()

    override fun edit(): Editor = FakeEditor

    override fun getStringSet(key: String?, defValues: MutableSet<String>?): MutableSet<String>? =
        if (!brokenMode) storage else null

    override fun unregisterOnSharedPreferenceChangeListener(
        listener: SharedPreferences.OnSharedPreferenceChangeListener?
    ) = throw UNSUPPORTED_OPERATION

    override fun registerOnSharedPreferenceChangeListener(
        listener: SharedPreferences.OnSharedPreferenceChangeListener?
    ) = throw UNSUPPORTED_OPERATION

    override fun contains(key: String?): Boolean = throw UNSUPPORTED_OPERATION
    override fun getBoolean(key: String?, defValue: Boolean): Boolean = throw UNSUPPORTED_OPERATION
    override fun getInt(key: String?, defValue: Int): Int = throw UNSUPPORTED_OPERATION
    override fun getAll(): MutableMap<String, *> = throw UNSUPPORTED_OPERATION
    override fun getLong(key: String?, defValue: Long): Long = throw UNSUPPORTED_OPERATION
    override fun getFloat(key: String?, defValue: Float): Float = throw UNSUPPORTED_OPERATION
    override fun getString(key: String?, defValue: String?): String? = throw UNSUPPORTED_OPERATION

    object FakeEditor : Editor {

        private var tempValues: MutableSet<String>? = null

        override fun putStringSet(key: String?, values: MutableSet<String>?): Editor {
            tempValues = values
            return this
        }

        override fun commit(): Boolean =
            tempValues
                ?.let {
                    storage.clear()
                    storage.addAll(it)
                    true
                } ?: false

        override fun clear(): Editor = throw UNSUPPORTED_OPERATION
        override fun putLong(key: String?, value: Long): Editor = throw UNSUPPORTED_OPERATION
        override fun putInt(key: String?, value: Int): Editor = throw UNSUPPORTED_OPERATION
        override fun remove(key: String?): Editor = throw UNSUPPORTED_OPERATION
        override fun putBoolean(key: String?, value: Boolean): Editor = throw UNSUPPORTED_OPERATION
        override fun putFloat(key: String?, value: Float): Editor = throw UNSUPPORTED_OPERATION
        override fun apply() = throw UNSUPPORTED_OPERATION
        override fun putString(key: String?, value: String?): Editor = throw UNSUPPORTED_OPERATION
    }
}