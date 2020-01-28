package aad.solver.database

import aad.solver.model.Matrix
import android.content.Context
import androidx.lifecycle.LiveData

class MatrixRepository(context: Context) {

    private val matrixDao: MatrixDao

    init {
        val database = MatrixRoomDatabase.getDatabase(context)
        matrixDao = database!!.matrixDao()
    }

    fun getMatrices(): LiveData<List<Matrix>> {
        return matrixDao.getMatrices()
    }

    fun getFavoriteMatrix() : LiveData<List<Matrix>> {
        return matrixDao.getFavoriteMatrix()
    }

    suspend fun insertMatrix(matrix: Matrix) {
        matrixDao.insertMatrix(matrix)
    }

    suspend fun deleteMatrix(matrix: Matrix) {
        matrixDao.deleteMatrix(matrix)
    }

    suspend fun deleteAllMatrices() {
        matrixDao.deleteAllMatrices()
    }

    fun updateFavoriteMatrix(inp_11 : String, inp_12 : String, inp_21 : String, inp_22 : String) {
        matrixDao.updateFavoriteMatrix(inp_11, inp_12, inp_21, inp_22)
    }
}