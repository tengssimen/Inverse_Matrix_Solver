package aad.solver.ui

import aad.solver.R
import aad.solver.model.Matrix
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_calculate.*
import kotlinx.android.synthetic.main.content_calculate.*
import java.lang.Exception

const val EXTRA_GAME = "EXTRA_GAME"


class CalculationActivity : AppCompatActivity() {

    private lateinit var calculationActivityViewModel: CalculationActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculate)
        setSupportActionBar(toolbar)

        initViews()
    }

    private fun initViews() {
        fab.setOnClickListener { onSaveClick() }
        make_fav.setOnClickListener { makeFavorite() }
        make_fav.setTextColor(Color.WHITE)
        load_fav.setOnClickListener { loadFavorite() }
        load_fav.setTextColor(Color.WHITE)
    }

    private fun validateEmptyFields(): Boolean {
        if (add_a11.text.toString().isBlank()) {
            Toast.makeText(this,"enter a11"
                , Toast.LENGTH_SHORT).show()
            return false
        }
        if (add_a12.text.toString().isBlank()) {
            Toast.makeText(this,"enter a12"
                , Toast.LENGTH_SHORT).show()
            return false
        }
        if (add_a21.text.toString().isBlank()) {
            Toast.makeText(this,"enter a21"
                , Toast.LENGTH_SHORT).show()
            return false
        }
        if (add_a22.text.toString().isBlank()) {
            Toast.makeText(this,"enter a22"
                , Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun onSaveClick() {
        println("You clicked calculate.")

        if(validateEmptyFields()) {

            val input_a = add_a11.text.toString().toDouble()
            val input_b = add_a12.text.toString().toDouble()
            val input_c = add_a21.text.toString().toDouble()
            val input_d = add_a22.text.toString().toDouble()

            try {

                val calculation = Matrix(input_a, input_b, input_c, input_d, 0)
                val resultIntent = Intent()

                resultIntent.putExtra(EXTRA_GAME, calculation)
                setResult(Activity.RESULT_OK, resultIntent)

                finish()
            } catch (e : Exception) {
                Toast.makeText(this,"Please"
                    , Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun loadFavorite() {

        calculationActivityViewModel = ViewModelProviders.of(this).get(CalculationActivityViewModel::class.java)

        calculationActivityViewModel.favorite.observe(this, Observer { favorite ->

            println(favorite.size)


            if (favorite.isNotEmpty()) {
                add_a11.setText(favorite[0].a11.toString())
                add_a12.setText(favorite[0].a12.toString())
                add_a21.setText(favorite[0].a21.toString())
                add_a22.setText(favorite[0].a22.toString())
            } else {
                Toast.makeText(
                    this, "You have not registered a favorite matrix"
                    , Toast.LENGTH_SHORT
                ).show()
            }

        })
    }

    private fun makeFavorite()  {

        if(validateEmptyFields()) {

            calculationActivityViewModel = ViewModelProviders.of(this).get(CalculationActivityViewModel::class.java)
            calculationActivityViewModel.favorite.observe(this, Observer { favorite ->

                if (favorite.isEmpty()) {

                    val input_a = add_a11.text.toString().toDouble()
                    val input_b = add_a12.text.toString().toDouble()
                    val input_c = add_a21.text.toString().toDouble()
                    val input_d = add_a22.text.toString().toDouble()

                    try {
                        val calculation = Matrix(input_a, input_b, input_c, input_d, 1)
                        val resultIntent = Intent()

                        resultIntent.putExtra(EXTRA_GAME, calculation)
                        setResult(Activity.RESULT_OK, resultIntent)

                        finish()
                    } catch (e: Exception) {
                        Toast.makeText(
                            this, "Something went wrong."
                            , Toast.LENGTH_SHORT
                        ).show()
                    }

                } else {
                    calculationActivityViewModel.updateFavorite(add_a11.text.toString(), add_a12.text.toString(), add_a21.text.toString(), add_a22.text.toString())
                    finish()
                }
            })
        }
    }
}