package com.example.stockmanagementsym.presentation

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.view.View
import androidx.fragment.app.findFragment
import androidx.lifecycle.ViewModelProvider
import com.example.stockmanagementsym.LoginActivity
import com.example.stockmanagementsym.MainActivity
import com.example.stockmanagementsym.R
import com.example.stockmanagementsym.data.CONSTANTS
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
import kotlinx.android.synthetic.main.fragment_product_list.*
import kotlinx.android.synthetic.main.fragment_sale_list.*
import kotlinx.android.synthetic.main.fragment_user.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
    private var cartLogic: CartLogic ?= null

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
    private fun getUser():User{
        if(user==null)
            user = getUserLogic().getUser()
        return user!!
    }

    fun setUserListManager() {
        val userLogicListManager = ListManager(getAndroidView().getUserListListener(),getAndroidView().getNotifierView())
        getUserLogic().setListManager(userLogicListManager)
    }

    fun createUser(user: User){
        if(getUserPrivileges()=="admin")
            getUserLogic().createUser(user)
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

    fun deleteUser(user: User){
        if(getUserPrivileges()=="admin"){
            getUserLogic().deleteUser(user)
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
            newSale = Sale(customerNewSale, dateSale, getCartLogic().getCartList())
        return newSale!!
    }

    suspend fun getSalesList(): MutableList<Sale> {
        return getSaleLogic().getSaleList()
    }

    fun setDateSale(dateSale: String) {
        this.dateSale = dateSale
    }

    fun addProductToCart(item: Product){
        getCartLogic().addProductToCart(item)
    }

    fun removeElementCart(item: Product): Boolean {
        return getCartLogic().removeElementCart(item)
    }

    fun getSaleToString(sale: Sale): String {
        getTypeConverterAdapter().setBooleanStringToUser(true)
        return "Fecha: "+sale.getDate()+"\n\n"+
               "Cliente: \n\n"+getCustomerToString(sale.getCustomer()) +"\n\n"+
               "Listado de productos: \n\n"+ getTypeConverterAdapter().productListToString(sale.getProductList())
    }

    //Cart
    fun getCartList(): MutableList<Product> {
        return getCartLogic().getCartList()
    }

    fun getTotalPriceCart(): String {
        return getCartLogic().getTotalPrice()
    }

    private fun getCartLogic(): CartLogic {
        if(cartLogic==null){
            val cartLogicListManager = ListManager(getAndroidView().getCartListener(),getAndroidView().getNotifierView())
            cartLogic = CartLogic(cartLogicListManager)
        }
        return cartLogic!!
    }

    fun setCartListManager() {
        val userLogicListManager = ListManager(getAndroidView().getCartListener(),getAndroidView().getNotifierView())
        getCartLogic().setListManager(userLogicListManager)
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
    fun createProduct(product: Product) {
        GlobalScope.launch(Dispatchers.IO){
            getAndroidView().showResultTransaction(getProductLogic().createProduct(product))
            reloadProductList()
        }
    }

    fun getPhotoCamera(context: Context) {
        getPhotoAdapter().startCamera(context)
    }

    fun getPhotoGallery(context: Context) {
        getPhotoAdapter().startGallery(context)
    }
    //BitMap generation
    fun setBitMap(bitMap: Bitmap?) {
        this.bitMap = bitMap
    }

    fun getBitMapFromString(string: String): Bitmap {
        return getBitMapAdapter().decoderStringToBitMap(string)
    }

    fun getStringFromBitMap():String{
        if(bitMap == null)
            return ""
        val stringEncoded = getBitMapAdapter().encoderBitMapToString(bitMap!!)
        stringBitmap = stringEncoded
        return stringEncoded
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

    fun searchCustomer() {
        GlobalScope.launch(Dispatchers.IO){
            getAndroidView().setListSearched(
                getCustomerLogic().searchCustomer(getAndroidView().getTextSearched()).toMutableList(),
                    R.id.customerListFragment
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
        if(userLogic==null){
            val userLogicListManager = ListManager(null,getAndroidView().getNotifierView()) // When the user login to application user list don't exist
            userLogic = UserLogic(dataBaseLogic.getUserDao(),userLogicListManager)
        }
        return userLogic!!
    }

    private fun getCustomerLogic(): CustomerLogic {
        if(customerLogic == null)
            customerLogic = CustomerLogic(dataBaseLogic.getCustomerDao())
        return customerLogic!!
    }

    private fun getSaleLogic(): SaleLogic {
        if(saleLogic==null){
            val saleLogicListManager = ListManager(getAndroidView().getSaleListListener(),getAndroidView().getNotifierView())
            saleLogic = SaleLogic(dataBaseLogic.getSaleDao(),getCartLogic(),saleLogicListManager)
        }
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
            restLogic = RESTLogic(getAndroidView().getProductListListener(),getAndroidView().getNotifierView())
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
            userLatitude = getUser().getLatitude()
            userLongitude = getUser().getLongitude()

            if(googleSingInClient!=null){
                setUserPhotoData(googleAccount!!.photoUrl.toString())
            }else{
                setUserPhotoData(getUser().getPhotoData())
            }

            val intent = Intent(login, MainActivity::class.java)
            login.startActivity(intent)
            getAndroidView().showToastMessage(login.getString(R.string.welcome)+" "+getUser().getName(),login)
        }
        else
            getAndroidView().showToastMessage("Usuario $userName no encontrado o contraseña incorrecta", login)
    }

    fun register(login: LoginActivity, userName: String, password: String) {
        dataBaseLogic = ViewModelProvider(login).get(DataBaseLogic::class.java)
        getUserLogic().createUser(User(userName, password,"admin","", CONSTANTS.DEFAULT_USER_LATITUDE, CONSTANTS.DEFAULT_USER_LONGITUDE))
    }

    suspend fun askSaveLocation(){
        val location = getAndroidView().getString(R.string.location)+" "+userLatitude+" - "+userLongitude
        withContext(Dispatchers.IO) {
            getAndroidView().dialogConfirmRegister(location,R.string.location, R.string.saveLocation)
        }
    }

    suspend fun askLogOut(){
        withContext(Dispatchers.IO) {
            getAndroidView().dialogConfirmRegister(getUser().getName(),R.string.logOut, R.string.messageLogOut)
        }
    }

    fun saveUserLocation(){
        getUser().setLatitude(userLatitude)
        getUser().setLongitude(userLongitude)
        getUserLogic().updateUser(getUser())
    }

    fun logOut(){
        user = null
        googleAccount = null
        if(googleSingInClient!=null)
            googleSingInClient!!.signOut()
        FragmentData.finish()

        val intent = Intent(getAndroidView().getContext(), LoginActivity::class.java)
        getAndroidView().getContext().startActivity(intent)
    }

    fun showToastMessage(message:String){
        getAndroidView().showToastMessage(message)
    }
    fun showToastMessage(message:String, context: Context){
        getAndroidView().showToastMessage(message,context)
    }

    fun showAlertMessage(titleID:Int, messageID: Int, context: Context) {
        getAndroidView().showAlertMessage(titleID,messageID,context)
    }

    fun getUserToString(user:User): String {
        return getTypeConverterAdapter().getUserToString(user)
    }
}
