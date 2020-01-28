package aad.solver.ui

import aad.solver.database.MatrixRepository
import aad.solver.model.Matrix
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {
    private val ioScope = CoroutineScope(Dispatchers.IO)
    private val matrixRepository = MatrixRepository(application.applicationContext)

    val calculations: LiveData<List<Matrix>> = matrixRepository.getMatrices()

    //TODO change name
    fun insertMatrix(matrix: Matrix) {
        ioScope.launch {
            matrixRepository.insertMatrix(matrix)
        }
    }

    fun deleteMatrix(matrix: Matrix) {
        ioScope.launch {
            matrixRepository.deleteMatrix(matrix)
        }
    }

    fun deleteAllCalculations() {
        ioScope.launch {
            matrixRepository.deleteAllMatrices()
        }
    }

}