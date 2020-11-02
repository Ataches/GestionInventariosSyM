package com.example.stockmanagementsym.presentation.view_holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.stockmanagementsym.R
import com.example.stockmanagementsym.data.CONSTANTS
import com.example.stockmanagementsym.logic.business.Product
import com.example.stockmanagementsym.presentation.fragment.FragmentData
import kotlinx.android.synthetic.main.item_product.view.*
import java.text.DecimalFormat

class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(product: Product) {
        val df = DecimalFormat("$###,###,###")
        itemView.textViewName.text = product.getName()
        itemView.textViewDescription.text = product.getDescription()
        itemView.textViewPrice.text = df.format(product.getPrice())
        itemView.textViewQuantity.text = """${product.getQuantity()}"""

        FragmentData.paintPhoto(
            product.getStringBitMap(),
            itemView.imageViewProduct,
            R.drawable.ic_image
        )

        if (product.idProduct == CONSTANTS.ID_PRODUCT_DEFAULT)  //If the id product has a default ID means that comes from REST search
            itemView.constraintLayoutCard.setBackgroundResource(R.color.buttonSecondary) // The product frame changes the color to show to the user the product from REST search
        else
            itemView.constraintLayoutCard.setBackgroundResource(R.color.cardBackground)

        itemView.buttonAddProductToCart.setOnClickListener {
            if (product.idProduct != CONSTANTS.ID_PRODUCT_DEFAULT) {   //If the id product has a default ID means that comes from REST search
                FragmentData.addProductToCart(product)
            } else {
                FragmentData.showToastMessage(R.string.productNotRegisted)
                FragmentData.confirmNewProduct(product)
            }
        }
        itemView.buttonEditProduct.setOnClickListener {
            FragmentData.updateProduct(product, true)
        }
        itemView.buttonDeleteProduct.setOnClickListener {
            FragmentData.askDeleteProduct(product)
        }
    }
}