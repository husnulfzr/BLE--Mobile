package co.igloohome.igloohome_sdk_demo

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import co.igloohome.igloohome_sdk_demo.database.StoredKey
import co.igloohome.igloohome_sdk_demo.database.StoredKeyDao
import co.igloohome.igloohome_sdk_demo.database.StoredKeyDatabase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class StoredKeyEntityReadWriteTest {
    private lateinit var storedKeyDao: StoredKeyDao
    private lateinit var db: StoredKeyDatabase

    private val sampleLockName = "lockName"
    private val sampleKey = StoredKey(sampleLockName, "aaabbbccc", "111222333")
    private val keyWithSameName = StoredKey(sampleLockName, "aaabbbcccddd", "111222333444")
    private val anotherSampleKey = StoredKey("sampleName", "xxxyyyzzz", "qqqwwweee")

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, StoredKeyDatabase::class.java).build()
        storedKeyDao = db.storedKeyDatabaseDao
        runBlocking {
            storedKeyDao.insert(sampleKey)
        }
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeStoredKeyAndReadInList() {
        runBlocking {
            val byName = storedKeyDao.getStoredKey("lockName")
            assert(byName!! == sampleKey)
        }
    }

    @Test
    @Throws(Exception::class)
    fun writeDuplicateStoredKey() {
        runBlocking {
            storedKeyDao.insert(keyWithSameName)
            val count = storedKeyDao.getAll()?.size
            assert(count == 1)
        }

    }

    @Test
    @Throws(Exception::class)
    fun updateMasterPin() {
        runBlocking {
            val newMasterPin = "333222111"
            storedKeyDao.updateMasterPin(sampleLockName, newMasterPin)
            val byName = storedKeyDao.getStoredKey("lockName")
            assert(byName!!.masterPin == newMasterPin)
        }
    }

    @Test
    @Throws(Exception::class)
    fun getAllKeys() {
        runBlocking {
            storedKeyDao.insert(anotherSampleKey)
            val count = storedKeyDao.getAll()?.size
            assert(count == 2)
        }
    }

    @Test
    @Throws(Exception::class)
    fun deleteKey() {
        runBlocking {
            storedKeyDao.deleteStoredKey(sampleLockName)
            val byName = storedKeyDao.getStoredKey("lockName")
            assert(byName == null)
        }
    }
}