package aad.solver.database

import aad.solver.model.Matrix
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Matrix::class], version = 1, exportSchema = false)

abstract class MatrixRoomDatabase : RoomDatabase() {

    abstract fun matrixDao(): MatrixDao

    companion object {
        private const val DATABASE_NAME = "CALCULATION_DATABASE"

        @Volatile
        private var INSTANCE: MatrixRoomDatabase? = null

        fun getDatabase(context: Context): MatrixRoomDatabase? {
            if (INSTANCE == null) {
                synchronized(MatrixRoomDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            MatrixRoomDatabase::class.java, DATABASE_NAME
                        )
                            .fallbackToDestructiveMigration()
                            .build()
                    }
                }
            }
            return INSTANCE
        }
    }
}