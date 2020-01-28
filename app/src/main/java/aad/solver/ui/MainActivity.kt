package aad.solver.ui

import aad.solver.R
import aad.solver.model.Matrix
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

const val ADD_GAME_REQUEST_CODE = 100


class MainActivity : AppCompatActivity() {

    private lateinit var mainActivityViewModel: MainActivityViewModel
    private val calculations = arrayListOf<Matrix>()
    private val calculationAdapter = CalculationAdapter(calculations)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { startCalculationActivity() }

        initViews()
        initViewModel()
    }


    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                ADD_GAME_REQUEST_CODE -> {
                    val calculation = data!!.getParcelableExtra<Matrix>(EXTRA_GAME)
                    mainActivityViewModel.insertMatrix(calculation)
                }
            }
        }
    }

    private fun initViews() {
        rvCalculations.layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
        rvCalculations.adapter = calculationAdapter
        rvCalculations.addItemDecoration(DividerItemDecoration(this@MainActivity, 0))
        createItemTouchHelper().attachToRecyclerView(rvCalculations)

    }

    private fun initViewModel() {
        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        mainActivityViewModel.calculations.observe(this, Observer { calculations ->
            this@MainActivity.calculations.clear()
            this@MainActivity.calculations.addAll(calculations)
            calculationAdapter.notifyDataSetChanged()
        })
    }


    private fun startCalculationActivity() {
        val intent = Intent(this, CalculationActivity::class.java)
        startActivityForResult(intent, ADD_GAME_REQUEST_CODE)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun createItemTouchHelper(): ItemTouchHelper {
        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val calculationToDelete = calculations[position]

                mainActivityViewModel.deleteMatrix(calculationToDelete)
            }
        }
        return ItemTouchHelper(callback)
    }

}
