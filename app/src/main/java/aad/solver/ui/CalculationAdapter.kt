package aad.solver.ui

import aad.solver.model.Matrix
import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.content_item.view.*


class CalculationAdapter(private val matrices: List<Matrix>) : RecyclerView.Adapter<CalculationAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(aad.solver.R.layout.content_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return matrices.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(matrices[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SimpleDateFormat")
        fun bind(matrix: Matrix) {

            if(matrix.isFav == 1) {
                itemView.materialCardView.setBackgroundColor(Color.parseColor("#0E9DF0"))
            }

            itemView.tv_a11.text = ("" + "%.1f".format(matrix.a11))
            itemView.tv_a12.text = ("" + "%.1f".format(matrix.a12))
            itemView.tv_a21.text = ("" + "%.1f".format(matrix.a21))
            itemView.tv_a22.text = ("" + "%.1f".format(matrix.a22))

            val inv : List<Double> = calculateInverse(matrix.a11, matrix.a12, matrix.a21, matrix.a22)

            if(inv[1] == -9.9 && inv[2] == -9.9 && inv[0] == -9.9 && inv[3] == -9.9) {
                itemView.tv_b11.text = ("/")
                itemView.tv_b12.text = ("/")
                itemView.tv_b21.text = ("/")
                itemView.tv_b22.text = ("/")
            }
            else {
                itemView.tv_b11.text = ("" + "%.1f".format(inv[0]))
                itemView.tv_b12.text = ("" + "%.1f".format(inv[1]))
                itemView.tv_b21.text = ("" + "%.1f".format(inv[2]))
                itemView.tv_b22.text = ("" + "%.1f".format(inv[3]))
            }
        }
    }

    private fun calculateInverse(a : Double, b : Double, c : Double, d : Double): List<Double> {

        val num = (a*d - b*c)

        if(num == 0.0) {
            return listOf(-9.9,-9.9,-9.9,-9.9)
        }else {

            val constant = 1/num

            val new_11 = constant * d
            val new_12 = constant * -b
            val new_21 = constant * -c
            val new_22 = constant * a

            println(constant)
            return listOf(new_11, new_12, new_21, new_22)
        }
    }

}