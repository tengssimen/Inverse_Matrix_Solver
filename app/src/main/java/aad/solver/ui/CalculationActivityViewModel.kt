package aad.solver.ui

import aad.solver.database.MatrixRepository
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CalculationActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val ioScope = CoroutineScope(Dispatchers.IO)
    private val calculationRepository = MatrixRepository(application.applicationContext)

    val favorite = calculationRepository.getFavoriteMatrix()

    fun updateFavorite(a11 : String, a12 : String, a21 : String, a22 : String) {
        ioScope.launch {
            calculationRepository.updateFavoriteMatrix(a11, a12, a21, a22)
        }
    }

}