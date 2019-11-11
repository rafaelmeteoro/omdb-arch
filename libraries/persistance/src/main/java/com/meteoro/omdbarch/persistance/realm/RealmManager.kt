package com.meteoro.omdbarch.persistance.realm

import android.util.LongSparseArray
import io.realm.Realm
import java.io.Closeable
import java.lang.ref.WeakReference

class RealmManager : Closeable {

    companion object {
        fun <T> run(func: RealmManager.() -> T): T = RealmManager().use { it.run(func) }
    }

    private val realmInstance = RealmInstance()

    val realm: Realm by lazy {
        realmInstance.get()
    }

    override fun close() {
        realmInstance.close()
    }

    private class RealmInstance : LongSparseArray<WeakReference<Realm>>() {

        private val instanceCount = LongSparseArray<Long>()

        fun get() = synchronized(instanceCount) {
            val threadId = Thread.currentThread().id
            var ref: WeakReference<Realm>? = get(threadId)

            if (ref?.get() != null && !ref.get()!!.isClosed) {
                val count = instanceCount.get(threadId) + 1
                instanceCount.put(threadId, count)
            } else {
                if (ref != null) ref.clear()

                ref = WeakReference(Realm.getDefaultInstance())
                instanceCount.put(threadId, 1L)
                put(threadId, ref)
            }
            ref.get()!!
        }

        fun close() {
            synchronized(instanceCount) {
                val threadId = Thread.currentThread().id
                var count: Long? = instanceCount.get(threadId, null)
                if (count != null) {
                    if (count > 1) {
                        instanceCount.put(threadId, --count)
                    } else {
                        instanceCount.remove(threadId)
                        if (get(threadId) != null) {
                            val ref = super.get(threadId)
                            if (ref != null) {
                                if (ref.get() != null) {
                                    if (!ref.get()!!.isClosed)
                                        ref.get()!!.close()
                                    ref.clear()
                                }
                            }
                            remove(threadId)
                        }
                    }
                }
            }
        }
    }
}