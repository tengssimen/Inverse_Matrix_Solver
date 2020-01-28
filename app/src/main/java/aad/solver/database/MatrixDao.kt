package aad.solver.database

import aad.solver.model.Matrix
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MatrixDao {
    @Insert
    suspend fun insertMatrix(matrix: Matrix)

    @Query("SELECT * FROM Matrices ORDER BY id")
    fun getMatrices(): LiveData<List<Matrix>>

    @Delete
    suspend fun deleteMatrix(matrix: Matrix)

    @Query("DELETE FROM Matrices")
    suspend fun deleteAllMatrices()

    @Query("SELECT * FROM Matrices WHERE isFav = 1")
    fun getFavoriteMatrix() : LiveData<List<Matrix>>

    @Query("UPDATE Matrices SET a11 = :inp_11, a12 = :inp_12, a21 = :inp_21, a22 = :inp_22 WHERE isFav = 1")
    fun updateFavoriteMatrix(inp_11 : String, inp_12 : String, inp_21 : String, inp_22 : String)
}