package com.example.onlinestorekotlin1

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.e_product_row.view.*

//recycler view
class EProductAdapter(var context: Context, var arrayList: ArrayList<EProduct>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val productView = LayoutInflater.from(context).inflate(R.layout.e_product_row, parent, false)
        return ProductViewHolder(productView)

    }

    override fun getItemCount(): Int {

        return arrayList.size

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        (holder as ProductViewHolder).initializeRowUIComponents(arrayList.get(position).id,
            arrayList.get(position).name, arrayList.get(position).price, arrayList.get(position).pictureName)

    }

    inner class ProductViewHolder(pView: View) : RecyclerView.ViewHolder(pView){

        fun initializeRowUIComponents(id: Int, name: String, price: Int, picName: String){
        //by using this parameters we need to initialize ourproduct of eproduct row
            //itemview is referring to the row that will be inflated in recycler view
            itemView.txtId.text = id.toString()
            itemView.txtName.text = name
            itemView.txtPrice.text = price.toString()

            //images from server
            //folder in htdocs project folder and searched on URL
            var picURL = "http://192.168.100.68/OnlineStoreApp/osimages/"
            //if there is a space in url this will show:
            picURL = picURL.replace(" ", "%20")
            //by using picasso library we can load our pics from server
            Picasso.get().load(picURL + picName).into(itemView.imgProduct)

            //the add button we added so it iwll go to cart for temporary orders
            itemView.imgAdd.setOnClickListener {
                //getting product id for add to cart as we dont have to show there on screen when dialog pops up
                //keeping track of product id
                Person.addToCartProductID = id
                //calling dialog fragment on add button so make sure Amount fragment class extends DialogFragment
            var amountFragment = AmountFragment()
                //casting context as activity
            var fragmentManager = (itemView.context as Activity).fragmentManager
                amountFragment.show(fragmentManager, "TAG")



            }


        }

    }

}