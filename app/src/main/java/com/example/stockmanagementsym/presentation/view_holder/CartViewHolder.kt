package com.example.stockmanagementsym.presentation.view_holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.stockmanagementsym.logic.business.Product
import com.example.stockmanagementsym.presentation.fragment.ListListener
import com.example.stockmanagementsym.presentation.view.FragmentData
import kotlinx.android.synthetic.main.item_cart.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private lateinit var productList: List<Product>

    init {
        GlobalScope.launch(Dispatchers.IO){
            productList = FragmentData.getProductList()
        }
    }

    fun bind(item: Product, listener: ListListener) {
        getProductList()
        val product = productList.filter { it.idProduct == item.idProduct }[0]
        itemView.textViewName.text = item.getName()
        itemView.textViewPrice.text = "$"+item.getPrice()
        itemView.textViewDescription.text = item.getDescription()
        itemView.textViewQuantity.text = "Cantidad: ${item.getQuantity()}"
        itemView.editTextQuantity.setText("""${item.getQuantity()}""")
        itemView.textViewProdRealQuantity.text = "Disponibles: ${(product.getQuantity()-item.getQuantity())}, total: ${product.getQuantity()}"
        itemView.buttonRemoveCart.setOnClickListener{
            FragmentData.removeElementCart(it.context,item)
            GlobalScope.launch(Dispatchers.IO){
                listener.reloadList()
            }
        }
        itemView.buttonAddQuantityProdCart.setOnClickListener{
            val quantity:Int = itemView.editTextQuantity.text.toString().toInt()
            if((product.getQuantity()>=quantity)&&(quantity > 0)){
                item.setQuantity(quantity)
                GlobalScope.launch(Dispatchers.IO){
                    listener.reloadList()
                }
            }else{
                FragmentData.showMessage(it.context, "Digite un numero correcto")
            }
        }
    }
    private fun getProductList() {
        GlobalScope.launch(Dispatchers.IO){
            productList = FragmentData.getProductList()
        }
    }

}