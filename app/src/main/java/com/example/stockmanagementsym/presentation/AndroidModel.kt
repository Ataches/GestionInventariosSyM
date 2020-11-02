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
import com.example.stockmanagementsym.logic.list_manager.ConcreteCreatorListManager
import com.example.stockmanagementsym.logic.list_manager.CreatorListManager
import com.example.stockmanagementsym.logic.list_manager.IListManager
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
import org.jetbrains.anko.doAsync

class AndroidModel {

    private var userListManager: IListManager? = null
    private var productListManager: IListManager? = null
    private var cartListManager: IListManager? = null
    private var saleListManager: IListManager? = null
    private var customerListManager: IListManager? = null

    private var userLatitude: Double = -1.0
    private var userLongitude: Double = -1.0
    private lateinit var dataBaseLogic: DataBaseLogic

    private var androidView: AndroidView? = null

    private var customerLogic: CustomerLogic? = null
    private var productLogic: ProductLogic? = null
    private var user: User? = null
    private var userLogic: UserLogic? = null
    private var saleLogic: SaleLogic? = null
    private var cartLogic: CartLogic? = null

    private var googleSingInClient: GoogleSignInClient? = null
    private var googleAccount: GoogleSignInAccount? = null
    private lateinit var userPhotoData: String

    private var newSaleDate: String? = null
    private var newSaleCustomer: Customer? = null
    private var newSale: Sale? = null

    //adapters
    private var photoConcreteAdapter: IPhotoAdapter? = null
    private var bitMapConcreteAdapter: IBitMapAdapter? = null
    private var typeConverterConcrete: ITypeConverterAdapter? = null

    //bitmap
    private var stringBitmap: String = ""

    private var bitMap: Bitmap ?= null
    //User
    private fun getUser():User{
        if(user==null)
            user = getUserLogic().getUser()
        return user!!
    }

    fun createUser(user: User){
        if(getUserPrivileges()=="admin")
            getUserLogic().createUser(user)
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

    private fun setUserPhotoData(userPhotoData: String) {
        this.userPhotoData = userPhotoData
    }

    fun getUserPhotoData(): String {
        return getUser().getPhotoData()
    }

    //Sale
    fun createSale() {
        val saleNew = getNewSale()
        if (saleNew == null) {
            showToastMessage(getAndroidView().getString(R.string.emptyData))
            return
        } else {
            doAsync {
                getProductLogic().decreaseProductListQuantity(saleNew.getProductList())
                getSaleLogic().createSale(saleNew)
                getCartLogic().clearCart()
                newSale = null
                newSaleDate = null
                newSaleCustomer = null
            }
        }
    }

    fun getNewSale(): Sale? {
        if ((newSaleDate == null) || (newSaleCustomer == null)) {
            showToastMessage(getAndroidView().getString(R.string.emptyData))
            return null
        }
        if (newSale == null)
            newSale = Sale(newSaleCustomer!!, newSaleDate!!, getCartLogic().getCartList())
        return newSale!!
    }

    fun getSalesList(): MutableList<Sale> {
        return getSaleLogic().getSaleList()
    }

    fun setDateSale(dateSale: String) {
        this.newSaleDate = dateSale
    }

    fun addProductToCart(item: Product) {
        getCartLogic().addProductToCart(item)
    }

    fun removeElementCart(item: Product) {
        getCartLogic().removeElementCart(item)
    }

    fun getSaleToString(sale: Sale): String {
        getTypeConverterAdapter().setBooleanStringToUser(true)
        return "Fecha: " + sale.getDate() + "\n\n" +
                "Cliente: \n\n" + getCustomerToString(sale.getCustomer()) + "\n\n" +
                "Listado de productos: \n\n" + getTypeConverterAdapter().productListToString(sale.getProductList())
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
            val cartLogicListManager = listManager(R.string.cartList)
            cartLogic = CartLogic(cartLogicListManager)
        }
        return cartLogic!!
    }


    fun getCartListToString(mutableList: MutableList<Product>): String {
        getTypeConverterAdapter().setBooleanStringToUser(true)
        return getTypeConverterAdapter().productListToString(mutableList)
    }

    //Customer
    fun updateCustomer(customerToUpdate: Customer) {
        val customerToEdit = getAndroidView().getCustomerToEdit()
        customerToUpdate.idCustomer = customerToEdit.idCustomer
        getCustomerLogic().updateCustomer(customerToUpdate)
    }

    fun getCustomerList(): MutableList<Customer> {
        return getCustomerLogic().getCustomerList()
    }

    fun setCustomerSelected(item: Int) {
        GlobalScope.launch(Dispatchers.IO) {
            newSaleCustomer = getCustomerList()[item]
        }
    }

    fun createCustomer(customer: Customer) {
        getCustomerLogic().createCustomer(customer)
        newSaleCustomer = customer //If it is a new customer register from new sale fragment
    }

    fun deleteCustomer(customer: Customer) {
        getCustomerLogic().deleteCustomer(customer)
    }

    fun getCustomerToString(customer: Customer): String {
        return getTypeConverterAdapter().customerToString(customer)
    }

    //Product
    suspend fun updateProduct(productToUpdate: Product): Boolean {
        return try {
            withContext(Dispatchers.IO) {
                val productToEdit = getAndroidView().getProductToEdit()
                productToUpdate.idProduct = productToEdit.idProduct
                getProductLogic().updateProduct(productToUpdate)
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
            }
            true
        }catch (e: Exception){
            false
        }
    }

    fun getProductToString(product: Product): String {
        return getTypeConverterAdapter().productToString(product)
    }

    //   New product creation
    fun createProduct(product: Product) {
        getProductLogic().createProduct(product)
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

    //Searches
    fun searchSale(view: View) {
        GlobalScope.launch(Dispatchers.IO){
            val saleListFragment = view.findFragment<SaleListFragment>()
            saleListFragment.reloadList(
                getSaleLogic().searchSales(saleListFragment.editTextSearchSaleList.text.toString())
                    .toMutableList()
            )
        }
    }

    fun searchCustomer() {
        getCustomerLogic().searchCustomer(getAndroidView().getTextSearched())
    }

    fun searchProduct(viewElement: View) {
        GlobalScope.launch(Dispatchers.IO){
            val view = viewElement.findFragment<ProductListFragment>()
            view.reloadList(
                getProductLogic().searchProduct(view.editTextSearchProductList.text.toString())
                    .toMutableList()
            )
        }
    }

    fun searchUser(view: View) {
        GlobalScope.launch(Dispatchers.IO) {
            val userListFragment = view.findFragment<UserListFragment>()
            userListFragment.reloadList(
                getUserLogic().searchUser(userListFragment.editTextSearchUserList.text.toString())
                    .toMutableList()
            )
        }
    }

    /*
        Logic classes, they do the transactions with the database and have the logic of each
        Need a list manager who notifies the changes to the view and needs a corresponding dao to get transactions from BD
     */
    private fun getUserLogic(): UserLogic {
        if (userLogic == null) {
            userLogic = UserLogic(dataBaseLogic.getUserDao(), listManager(R.string.userList))
        }
        return userLogic!!
    }

    private fun getCustomerLogic(): CustomerLogic {
        if (customerLogic == null) {
            customerLogic =
                CustomerLogic(dataBaseLogic.getCustomerDao(), listManager(R.string.customerList))
        }
        return customerLogic!!
    }

    private fun getSaleLogic(): SaleLogic {
        if (saleLogic == null)
            saleLogic = SaleLogic(dataBaseLogic.getSaleDao(), listManager(R.string.saleList))
        return saleLogic!!
    }

    private fun getProductLogic(): ProductLogic {
        if (productLogic == null)
            productLogic =
                ProductLogic(dataBaseLogic.getProductDao(), listManager(R.string.productList))
        return productLogic!!
    }

    private fun getAndroidView(): AndroidView {
        if(androidView == null)
            androidView = AndroidView(this)
        return androidView!!
    }

    //Adapters
    private fun getPhotoAdapter(): IPhotoAdapter {
        if(photoConcreteAdapter==null)
            photoConcreteAdapter = PhotoConcreteAdapter()
        return photoConcreteAdapter!!
    }
    private fun getBitMapAdapter(): IBitMapAdapter {
        if (bitMapConcreteAdapter == null)
            bitMapConcreteAdapter = BitMapConcreteAdapter()
        return bitMapConcreteAdapter!!
    }

    private fun getTypeConverterAdapter(): ITypeConverterAdapter {
        if (typeConverterConcrete == null)
            typeConverterConcrete = TypeConverterConcrete()
        return typeConverterConcrete!!
    }

    /*
        List manager method, returns a LisManager created in a factory method.
        With de list ID makes a list manager that allows to notify or reload lists.
     */
    private fun listManager(managerNeededID: Int): IListManager {
        val concreteCreatorListManager: CreatorListManager = ConcreteCreatorListManager()
        when (managerNeededID) {
            R.string.productList -> {
                if (productListManager == null)
                    productListManager = concreteCreatorListManager.factoryMethod()
                return productListManager!!
            }
            R.string.customerList -> {
                if (customerListManager == null)
                    customerListManager = concreteCreatorListManager.factoryMethod()
                return customerListManager!!
            }
            R.string.saleList -> {
                if (saleListManager == null)
                    saleListManager = concreteCreatorListManager.factoryMethod()
                return saleListManager!!
            }
            R.string.userList -> {
                if (userListManager == null)
                    userListManager = concreteCreatorListManager.factoryMethod()
                return userListManager!!
            }
            R.string.cartList -> {
                if (cartListManager == null)
                    cartListManager = concreteCreatorListManager.factoryMethod()
                return cartListManager!!
            }
            else -> return concreteCreatorListManager.factoryMethod()
        }
    }

    /*
        Methods to notify or start logic classes.
        Needs a list listener that are methods from fragment lists that allow to the logic classes reload views.
        Needs a notifier that show to the user the result of transactions
     */
    fun notifyProductLogic(listListener: IListListener) {
        listManager(R.string.productList).setListListener(listListener)
        listManager(R.string.productList).setNotifier(getAndroidView().getNotifierView())
        getProductLogic().loadData()
    }

    fun notifySaleLogic(listListener: IListListener) {
        listManager(R.string.saleList).setListListener(listListener)
        listManager(R.string.saleList).setNotifier(getAndroidView().getNotifierView())
        getSaleLogic().loadData()
    }

    fun notifyCustomerLogic(listListener: IListListener) {
        listManager(R.string.customerList).setListListener(listListener)
        listManager(R.string.customerList).setNotifier(getAndroidView().getNotifierView())
        getCustomerLogic().loadData()
    }

    fun notifyUserLogic(listListener: IListListener) {
        listManager(R.string.userList).setListListener(listListener)
        listManager(R.string.userList).setNotifier(getAndroidView().getNotifierView())
        getUserLogic().loadData()
    }

    fun notifyCartLogic(listListener: IListListener, iCart: ICart) {
        listManager(R.string.cartList).setListListener(listListener)
        listManager(R.string.cartList).setNotifier(getAndroidView().getNotifierView())
        getProductLogic().setCartListener(iCart)
    }

    suspend fun confirmLogin(login: LoginActivity, userName: String, password: String) {
        dataBaseLogic = ViewModelProvider(login).get(DataBaseLogic::class.java)
        if (getUserLogic().confirmLogin(userName, password)) {
            getAndroidView().showToastMessage(
                login.getString(R.string.welcome) + " " + getUser().getName(),
                login
            )

            userLatitude = getUser().getLatitude()
            userLongitude = getUser().getLongitude()

            if (googleSingInClient != null) {
                setUserPhotoData(googleAccount!!.photoUrl.toString())
            } else {
                setUserPhotoData(getUser().getPhotoData())
            }

            val intent = Intent(login, MainActivity::class.java)
            login.startActivity(intent)
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
        getAndroidView().showToastMessage(message, context)
    }

    fun showAlertMessage(titleID: Int, messageID: Int, context: Context) {
        getAndroidView().showAlertMessage(titleID, messageID, context)
    }

    fun getUserToString(user: User): String {
        return getTypeConverterAdapter().getUserToString(user)
    }

    fun getProductList(): MutableList<Product> {
        return getProductLogic().getProductList()
    }
}
