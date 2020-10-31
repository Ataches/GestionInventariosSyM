package com.example.stockmanagementsym.presentation

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.view.View
import androidx.fragment.app.findFragment
import androidx.lifecycle.ViewModelProvider
import com.example.stockmanagementsym.LoginActivity
import com.example.stockmanagementsym.MainActivity
import com.example.stockmanagementsym.R
import com.example.stockmanagementsym.logic.*
import com.example.stockmanagementsym.logic.adapter.bit_map.BitMapConcreteAdapter
import com.example.stockmanagementsym.logic.adapter.bit_map.IBitMapAdapter
import com.example.stockmanagementsym.logic.adapter.photo.IPhotoAdapter
import com.example.stockmanagementsym.logic.adapter.photo.PhotoConcreteAdapter
import com.example.stockmanagementsym.logic.adapter.type_converter.ITypeConverterAdapter
import com.example.stockmanagementsym.logic.adapter.type_converter.TypeConverterConcrete
import com.example.stockmanagementsym.logic.business.Customer
import com.example.stockmanagementsym.logic.business.Product
import com.example.stockmanagementsym.logic.business.Sale
import com.example.stockmanagementsym.logic.business.User
import com.example.stockmanagementsym.presentation.fragment.*
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import kotlinx.android.synthetic.main.fragment_customer_list.*
import kotlinx.android.synthetic.main.fragment_product_list.*
import kotlinx.android.synthetic.main.fragment_sale_list.*
import kotlinx.android.synthetic.main.fragment_user.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.anko.doAsync

class AndroidModel{

    private var userLatitude: Double = -1.0
    private var userLongitude: Double = -1.0
    private lateinit var dataBaseLogic: DataBaseLogic

    private var androidView: AndroidView?= null

    private var customerLogic: CustomerLogic ?= null
    private var productLogic: ProductLogic ?= null
    private var user: User? = null
    private var userLogic:UserLogic ?= null
    private var saleLogic: SaleLogic ?= null
    private var restLogic: RESTLogic ?= null

    private var googleSingInClient: GoogleSignInClient ?= null
    private var googleAccount: GoogleSignInAccount ?= null
    private lateinit var userPhotoData: String
    private lateinit var dateSale: String

    private lateinit var customerNewSale: Customer
    private var newSale: Sale ?= null

    //adapters
    private var photoConcreteAdapter: IPhotoAdapter ?= null
    private var bitMapConcreteAdapter: IBitMapAdapter ?= null
    private var typeConverterConcrete: ITypeConverterAdapter?= null

    //bitmap
    private var stringBitmap: String = ""

    private var bitMap: Bitmap ?= null
    //User
    fun getUser():User{
        if(user==null)
            user = getUserLogic().getUser()
        return user!!
    }

    suspend fun newUser(user: User): Boolean {
        return try{
            if(getUserPrivileges()!="admin")
                return false
            withContext(Dispatchers.IO) {
                getUserLogic().insertUser(user)
            }
            getAndroidView().reloadUserList()
            true
        }catch (e:Exception){
            false
        }
    }

    suspend fun deleteUser(user: User){
        try{
            if(getUserPrivileges()=="admin"){
                withContext(Dispatchers.IO) {
                    getUserLogic().deleteUser(user)
                }
                getUserLogic().setListListener(getAndroidView().getUserListListener())
            }
        }catch (e:Exception){
            getAndroidView().showResultTransaction(false)
        }
    }

    suspend fun updateUser(user: User): Boolean {
        return try{
            if(getUserPrivileges()!="admin")
                return false
            withContext(Dispatchers.IO) {
                getUserLogic().updateUser(user)
            }
            getAndroidView().reloadUserList()
            true
        }catch (e:Exception){
            false
        }
    }

    fun getUserName(): String {
        return getUser().getName()
    }

    fun getUserPrivileges(): String {
        return getUser().getPrivilege()
    }

    suspend fun getUserList(): MutableList<User> {
        var list:MutableList<User> = mutableListOf()
        withContext(Dispatchers.IO) {
            list = getUserLogic().getUserList()
        }
        return list
    }

    fun setUserLocation(latitude: Double, longitude: Double) {
        userLatitude = (latitude)
        userLongitude = (longitude)
    }

    fun getUserLatitude(): Double {
        return userLatitude
    }

    fun getUserLongitude(): Double {
        return userLongitude
    }

    fun setGoogleAccount(googleAccount: GoogleSignInAccount?) {
        this.googleAccount = googleAccount
    }

    fun setGoogleSingInClient(googleSingInClient: GoogleSignInClient?) {
        this.googleSingInClient = googleSingInClient
    }

    fun setUserPhotoData(userPhotoData: String) {
        this.userPhotoData = userPhotoData
    }

    fun getUserPhotoData(): String {
        return getUser().getPhotoData()
    }

    //Sale
    suspend fun createSale(): Boolean {
        return try{
            withContext(Dispatchers.IO) {
                getNewSale().getProductList().forEach {
                        val product  = getProductLogic().searchProducts(it.idProduct)
                        product.setQuantity(product.getQuantity() - it.getQuantity())
                        getProductLogic().updateProduct(product)
                    }
                getSaleLogic().createSale(getNewSale())
                getAndroidView().reloadCartList()
            }
            this.newSale = null
            true
        }catch(e:Exception){
            false
        }
    }

    fun getNewSale(): Sale {
        if(newSale==null)
            newSale = Sale(customerNewSale, dateSale, getSaleLogic().getCartList())
        return newSale!!
    }

    suspend fun getSalesList(): MutableList<Sale> {
        return getSaleLogic().getSaleList()
    }

    fun setDateSale(dateSale: String) {
        this.dateSale = dateSale
    }

    fun addProductToCart(item: Product){
        getSaleLogic().setListListener(getAndroidView().getCartListener())
        getSaleLogic().addProductToCart(item)

    }

    fun removeElementCart(item: Product): Boolean {
        return getSaleLogic().removeElementCart(item)
    }

    fun getSaleToString(sale: Sale): String {
        getTypeConverterAdapter().setBooleanStringToUser(true)
        return "Fecha: "+sale.getDate()+"\n\n"+
               "Cliente: \n\n"+getCustomerToString(sale.getCustomer()) +"\n\n"+
               "Listado de productos: \n\n"+ getTypeConverterAdapter().productListToString(sale.getProductList())
    }

    //Cart
    fun getCartList(): MutableList<Product> {
        return getSaleLogic().getCartList()
    }

    fun getTotalPriceCart(): String {
        return getSaleLogic().getTotalPriceCart()
    }

    fun getCartListToString(mutableList: MutableList<Product>): String {
        getTypeConverterAdapter().setBooleanStringToUser(true)
        return getTypeConverterAdapter().productListToString(mutableList)
    }

    //Customer
    suspend fun updateCustomer(customerToUpdate: Customer): Boolean {
        val customerToEdit = getAndroidView().getCustomerToEdit()
        customerToUpdate.idCustomer = customerToEdit.idCustomer
        val resultTransaction = getCustomerLogic().updateCustomer(customerToUpdate)
        if(resultTransaction)
            getAndroidView().reloadCustomerList()
        return resultTransaction
    }

    suspend fun getCustomerList(): MutableList<Customer> {
        return getCustomerLogic().getCustomerList()
    }

    fun setCustomerSelected(item: Int) {
        GlobalScope.launch (Dispatchers.IO){
            customerNewSale = getCustomerList()[item]
        }
    }

    suspend fun createCustomer(customer: Customer): Boolean {
        val resultTransaction = getCustomerLogic().createCustomer(customer)
        customerNewSale = customer //If it is a new customer register from new sale fragment
        if(resultTransaction)
            getAndroidView().reloadCustomerList()
        return resultTransaction
    }

    suspend fun deleteCustomer(customer: Customer): Boolean {
        val resultTransaction = getCustomerLogic().deleteCustomer(customer)
        getAndroidView().reloadCustomerList()
        return resultTransaction
    }

    fun getCustomerToString(customer: Customer): String {
        return getTypeConverterAdapter().customerToString(customer)
    }

    //Product
    suspend fun getProductList(): MutableList<Product> {
        return try {
            var list = mutableListOf<Product>()
            withContext(Dispatchers.IO) {
                list = getProductLogic().getProductList()
            }
            list
        }catch (e: Exception){
            mutableListOf()
        }
    }

    suspend fun updateProduct(productToUpdate: Product):Boolean {
        return try{
            withContext(Dispatchers.IO) {
                val productToEdit = getAndroidView().getProductToEdit()
                productToUpdate.idProduct = productToEdit.idProduct
                getProductLogic().updateProduct(productToUpdate)
                reloadProductList()
            }
            true
        }catch (e: Exception){
            false
        }
    }

    suspend fun deleteProduct(product: Product): Boolean {
        return try{
            withContext(Dispatchers.IO) {
                getProductLogic().deleteProduct(product)
                reloadProductList()
            }
            true
        }catch (e: Exception){
            false
        }
    }

    fun getProductToString(product: Product): String {
        return getTypeConverterAdapter().productToString(product)
    }

    fun loadProductListFromREST(){
        getRESTLogic().getProductList()
    }

    //   New product creation
    fun createProduct(product: Product,context: Context) {
        GlobalScope.launch(Dispatchers.IO){
            getAndroidView().showResultTransaction(getProductLogic().createProduct(product))
            reloadProductList()
        }
    }

    fun getPhotoCamera(activity: Activity, context: Context) {
        getPhotoAdapter().startCamera(activity,context)
    }

    fun getPhotoGallery(activity: Activity, context: Context) {
        getPhotoAdapter().startGallery(activity,context)
    }
    //BitMap generation
    fun setBitMap(bitMap: Bitmap) {
        this.bitMap = bitMap
    }

    fun getBitMapFromstring(string: String): Bitmap {
        return getBitMapAdapter().decoderStringToBitMap(string)
    }

    fun getStringFromBitMap():String{ //This function only is called when you do a register or update, so if you call that you wont need string bitmap again
        if(bitMap == null)
            return ""
        val stringEncoded = getBitMapAdapter().encoderBitMapToString(bitMap!!)
        stringBitmap = stringEncoded
        return stringEncoded
    }
    fun addProductListREST(list:List<Product>){ //Called from rest
        doAsync{
            getAndroidView().addProductListToProductFragment(list)
            getProductLogic().addProductListREST(list.toMutableList())
        }
    }
    private fun reloadProductList() {
        getAndroidView().reloadProductList()
    }

    //Searches
    fun searchSale(view: View) {
        GlobalScope.launch(Dispatchers.IO){
            val saleListFragment = view.findFragment<SaleListFragment>()
            saleListFragment.setList(
                getSaleLogic().searchSales(saleListFragment.editTextSearchSaleList.text.toString())
                    .toMutableList()
            )
        }
    }

    fun searchCustomer(viewElement: View) {
        GlobalScope.launch(Dispatchers.IO){
            val view = viewElement.findFragment<CustomerListFragment>()
            view.setList(
                getCustomerLogic().searchCustomer(view.editTextSearchCustomerList.text.toString())
                    .toMutableList()
            )
        }
    }

    fun searchProduct(viewElement: View) {
        GlobalScope.launch(Dispatchers.IO){
            val view = viewElement.findFragment<ProductListFragment>()
            view.setList(
                getProductLogic().searchProduct(view.editTextSearchProductList.text.toString())
                    .toMutableList()
            )
        }
    }

    fun searchUser(view: View) {
        GlobalScope.launch(Dispatchers.IO){
            val userListFragment = view.findFragment<UserListFragment>()
            userListFragment.setList(
                getUserLogic().searchUser(userListFragment.editTextSearchUserList.text.toString())
                    .toMutableList()
            )
        }
    }
    //Logic classes
    private fun getUserLogic(): UserLogic {
        if(userLogic==null)
            userLogic = UserLogic(dataBaseLogic.getUserDao(),getAndroidView().getNotifier())
        return userLogic!!
    }

    private fun getCustomerLogic(): CustomerLogic {
        if(customerLogic == null)
            customerLogic = CustomerLogic(dataBaseLogic.getCustomerDao())
        return customerLogic!!
    }

    private fun getSaleLogic(): SaleLogic {
        if(saleLogic==null)
            saleLogic = SaleLogic(dataBaseLogic.getSaleDao(),getAndroidView().getNotifier())
        return saleLogic!!
    }

    private fun getProductLogic(): ProductLogic {
        if(productLogic==null){
            productLogic = ProductLogic(dataBaseLogic.getProductDao())
        }
        return productLogic!!
    }

    private fun getAndroidView(): AndroidView {
        if(androidView == null)
            androidView = AndroidView(this)
        return androidView!!
    }
    //REST
    private fun getRESTLogic(): RESTLogic {
        if(restLogic==null)
            restLogic = RESTLogic(this)
        return restLogic!!
    }

    //Adapters
    private fun getPhotoAdapter(): IPhotoAdapter {
        if(photoConcreteAdapter==null)
            photoConcreteAdapter = PhotoConcreteAdapter()
        return photoConcreteAdapter!!
    }
    private fun getBitMapAdapter(): IBitMapAdapter {
        if(bitMapConcreteAdapter==null)
            bitMapConcreteAdapter = BitMapConcreteAdapter()
        return bitMapConcreteAdapter!!
    }
    private fun getTypeConverterAdapter():ITypeConverterAdapter{
        if(typeConverterConcrete==null)
            typeConverterConcrete = TypeConverterConcrete()
        return typeConverterConcrete!!
    }

    suspend fun confirmLogin(login: LoginActivity, userName: String, password: String) {
        dataBaseLogic = ViewModelProvider(login).get(DataBaseLogic::class.java)
        //getUserLogic().insertTest(userName,password,googleAccount?.photoUrl.toString())
        if(getUserLogic().confirmLogin(userName, password)){
            getAndroidView().showToastMessage(login.getString(R.string.welcome)+" "+getUser().getName(),login)

            userLatitude = getUser().getLatitude()
            userLongitude = getUser().getLongitude()

            if(googleSingInClient!=null){
                setUserPhotoData(googleAccount!!.photoUrl.toString())
            }else{
                setUserPhotoData(getUser().getPhotoData())
            }

            val intent = Intent(login, MainActivity::class.java)
            login.startActivity(intent)
        }
        else
            getAndroidView().showToastMessage("Usuario $userName no encontrado o contraseña incorrecta", login)
    }

    suspend fun register(login: LoginActivity, userName: String, password: String) {
        dataBaseLogic = ViewModelProvider(login).get(DataBaseLogic::class.java)
        getUserLogic().insertUser(userName,password)
    }

    suspend fun askSaveLocation(context: Context){
        val location = context.getString(R.string.location)+" "+userLatitude+" - "+userLongitude
        val activity = context as Activity
        withContext(Dispatchers.IO) {
            getAndroidView().dialogConfirmRegister(location,context.getString(R.string.location), context.getString(R.string.saveLocation),activity)
        }
    }

    suspend fun askLogOut(context: Context){
        val activity = context as Activity
        withContext(Dispatchers.IO) {
            getAndroidView().dialogConfirmRegister(getUser().getName(),context.getString(R.string.logOut), context.getString(R.string.messageLogOut),activity)
        }
    }

    suspend fun saveUserLocation(context: Context){
        user!!.setLatitude(userLatitude)
        user!!.setLongitude(userLongitude)
        withContext(Dispatchers.IO) {
            getAndroidView().showResultTransaction(getUserLogic().updateUser(user!!))
        }
    }

    fun logOut(context: Context){
        user = null
        googleAccount = null
        if(googleSingInClient!=null)
            googleSingInClient!!.signOut()
        FragmentData.finish()

        val intent = Intent(context, LoginActivity::class.java)
        context.startActivity(intent)
    }

    fun showToastMessage(message:String){
        getAndroidView().showToastMessage(message)
    }
    fun showToastMessage(message:String, context: Context){
        getAndroidView().showToastMessage(message,context)
    }

    fun showAlertMessage(title:String,message: String, context: Context) {
        getAndroidView().showAlertMessage(title,message,context)
    }
    fun showAlertMessage(title:Int,message: Int) {
        getAndroidView().showAlertMessage(title,message)
    }

    fun getUserToString(user:User): String {
        return getTypeConverterAdapter().getUserToString(user)
    }
}
