package com.ebner.roomdatabasebackup.sample.database.main

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.ebner.roomdatabasebackup.sample.database.table.Fruit.Fruit
import com.ebner.roomdatabasebackup.sample.database.table.Fruit.FruitDao
import java.util.concurrent.Executors

/**
 * Created by raphael on 14.06.2020.
 * Android Room Database Backup Created in com.ebner.roomdatabasebackup.sample.database.main
 */
@Database(
    entities = [
        Fruit::class],
    version = 1
)
abstract class FruitDatabase : RoomDatabase() {
    abstract fun fruitDao(): FruitDao

    companion object {

        const val DATABASE_NAME = "fruitdb"

        @Volatile
        private var INSTANCE: FruitDatabase? = null

        /*---------------------Create one (only one) instance of the Database--------------------------*/

        fun getInstance(context: Context): FruitDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext, FruitDatabase::class.java, DATABASE_NAME)
                //Delete Database, when something changed
                .fallbackToDestructiveMigration()
                // prepopulate the database after onCreate was called
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        // insert the data on the IO Thread
                        ioThread {
                            //Some sample data
                            getInstance(context).fruitDao().insert(Fruit(name = "Apple"))
                            getInstance(context).fruitDao().insert(Fruit(name = "Banana"))
                            getInstance(context).fruitDao().insert(Fruit(name = "Lime"))
                            getInstance(context).fruitDao().insert(Fruit(name = "Grapes"))
                            getInstance(context).fruitDao().insert(Fruit(name = "Lemon"))
                            getInstance(context).fruitDao().insert(Fruit(name = "Cherry"))
                            getInstance(context).fruitDao().insert(Fruit(name = "Blueberry"))
                            getInstance(context).fruitDao().insert(Fruit(name = "Watermelon"))
                            getInstance(context).fruitDao().insert(Fruit(name = "Peach"))
                            getInstance(context).fruitDao().insert(Fruit(name = "Pineapple"))
                            getInstance(context).fruitDao().insert(Fruit(name = "Orange"))
                            getInstance(context).fruitDao().insert(Fruit(name = "Strawberry"))
                            getInstance(context).fruitDao().insert(Fruit(name = "Coconut"))
                            getInstance(context).fruitDao().insert(Fruit(name = "Raspberry"))
                            getInstance(context).fruitDao().insert(Fruit(name = "Mandarine"))
                            getInstance(context).fruitDao().insert(Fruit(name = "Grapefruit"))
                            getInstance(context).fruitDao().insert(Fruit(name = "Plum"))
                            getInstance(context).fruitDao().insert(Fruit(name = "Pear"))
                            getInstance(context).fruitDao().insert(Fruit(name = "Passionfruit"))

                        }
                    }
                })
                .build()

        /*---------------------This runs a background task--------------------------*/
        private val IO_EXECUTOR = Executors.newSingleThreadExecutor()

        /**
         * Utility method to run blocks on a dedicated background thread, used for io/database work.
         */
        fun ioThread(f: () -> Unit) {
            IO_EXECUTOR.execute(f)
        }

    }
}