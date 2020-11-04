package com.example.stockmanagementsym.presentation

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.lifecycle.ViewModelProvider
import com.example.stockmanagementsym.LoginActivity
import com.example.stockmanagementsym.MainActivity
import com.example.stockmanagementsym.R
import com.example.stockmanagementsym.data.CONSTANTS
import com.example.stockmanagementsym.logic.AdapterClient
import com.example.stockmanagementsym.logic.CartLogicFactory
import com.example.stockmanagementsym.logic.DataBaseLogic
import com.example.stockmanagementsym.logic.ListLogicFactory
import com.example.stockmanagementsym.logic.business.Customer
import com.example.stockmanagementsym.logic.business.Product
import com.example.stockmanagementsym.logic.business.Sale
import com.example.stockmanagementsym.logic.business.User
import com.example.stockmanagementsym.logic.cart.AbstractCartLogic
import com.example.stockmanagementsym.logic.classes.AbstractListLogic
import com.example.stockmanagementsym.logic.classes.AbstractLogicFactory
import com.example.stockmanagementsym.logic.classes.LogicAbstractionNames
import com.example.stockmanagementsym.logic.list_manager.ConcreteCreatorListManager
import com.example.stockmanagementsym.logic.list_manager.CreatorListManager
import com.example.stockmanagementsym.logic.list_manager.IListManager
import com.example.stockmanagementsym.logic.list_manager.ListManagerInstances
import com.example.stockmanagementsym.presentation.fragment.FragmentData
import com.example.stockmanagementsym.presentation.fragment.ICart
import com.example.stockmanagementsym.presentation.fragment.IListListener
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import org.jetbrains.anko.doAsyncResult
import org.jetbrains.anko.uiThread

class AndroidModel {

    private var adapterClient: AdapterClient? = null
    private var userListManager: IListManager? = null
    private var productListManager: IListManager? = null
    private var cartListManager: IListManager? = null
    private var saleListManager: IListManager? = null
    private var customerListManager: IListManager? = null
    private var userLatitude: Double = -1.0

    private var userLongitude: Double = -1.0
    private lateinit var dataBaseLogic: DataBaseLogic

    private var androidView: AndroidView? = null

    private var cartLogic: AbstractCartLogic? = null

    private var listFactory: AbstractLogicFactory = ListLogicFactory()
    private var cartFactory: AbstractLogicFactory = CartLogicFactory()

    private var userLogic: AbstractListLogic? = null
    private var customerLogic: AbstractListLogic? = null
    private var saleLogic: AbstractListLogic? = null
    private var productLogic: AbstractListLogic? = null

    private var user: User? = null

    private var googleSingInClient: GoogleSignInClient? = null
    private var googleAccount: GoogleSignInAccount? = null
    private lateinit var userPhotoData: String

    private var newSaleDate: String? = null
    private var newSaleCustomer: Customer? = null
    private var newSale: Sale? = null

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
            getUserLogic().insert(user)
    }

    fun deleteUser(user: User){
        if(getUserPrivileges()=="admin"){
            getUserLogic().delete(user)
        }
    }

    fun getUserName(): String {
        return getUser().getName()
    }

    fun getUserPrivileges(): String {
        return getUser().getPrivilege()
    }

    fun getUserToString(user: User): String {
        return getAdapterClient().getUserToString(user)
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
    fun insertSale() {
        val saleNew = getNewSale()
        if (saleNew == null) {
            showToastMessage(getAndroidView().getString(R.string.emptyData))
            return
        } else {
            notifySaleLogic(null) // To show the transaction result sale logic needs a notifier instance, isn't necessary a list listener
            doAsyncResult {
                getSaleLogic().insert(saleNew)
                getProductLogic().decreaseMutableListQuantity(
                        saleNew.getProductList().toMutableList()
                )
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
            newSale = Sale(newSaleCustomer!!, newSaleDate!!, getCartLogic().getList())
        return newSale!!
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
        getAdapterClient().setBooleanStringToUser(true)
        return "Fecha: " + sale.getDate() + "\n\n" +
                "Cliente: \n\n" + getCustomerToString(sale.getCustomer()) + "\n\n" +
                "Listado de productos: \n\n" + getAdapterClient().productListToString(sale.getProductList())
    }

    //Cart
    fun getCartList(): MutableList<Product> {
        return getCartLogic().getList()
    }

    fun getTotalPriceCart(): String {
        return getCartLogic().getTotalPrice()
    }

    private fun getCartLogic(): AbstractCartLogic {
        if (cartLogic == null) {
            cartLogic = cartFactory.createCartList()
            cartLogic!!.setListManager(listManager(ListManagerInstances.CartList))
        }
        return cartLogic!!
    }


    fun getCartListToString(mutableList: MutableList<Product>): String {
        getAdapterClient().setBooleanStringToUser(true)
        return getAdapterClient().productListToString(mutableList)
    }

    //Customer
    fun updateCustomer(customerToUpdate: Customer) {
        val customerToEdit = getAndroidView().getCustomerToEdit()
        customerToUpdate.idCustomer = customerToEdit.idCustomer
        getCustomerLogic().update(customerToUpdate)
    }

    fun getCustomerList(): MutableList<Any> {
        return getCustomerLogic().getList()
    }

    fun setCustomerSelected(item: Int) {
        newSaleCustomer = (getCustomerList() as MutableList<Customer>)[item]
    }

    fun createCustomer(customer: Customer) {
        getCustomerLogic().insert(customer)
        newSaleCustomer = customer //If it is a new customer register from new sale fragment
    }

    fun deleteCustomer(customer: Customer) {
        getCustomerLogic().delete(customer)
    }

    fun getCustomerToString(customer: Customer): String {
        return getAdapterClient().customerToString(customer)
    }

    //Product
    fun updateProduct(productToUpdate: Product) {
        val productToEdit = getAndroidView().getProductToEdit()
        productToUpdate.idProduct = productToEdit.idProduct
        getProductLogic().update(productToUpdate)
    }

    fun getProductList(): MutableList<Any> {
        return getProductLogic().getList()
    }

    fun deleteProduct(product: Product) {
        getProductLogic().delete(product)
    }

    fun getProductToString(product: Product): String {
        return getAdapterClient().productToString(product)
    }

    //   New product creation
    fun createProduct(product: Product) {
        getProductLogic().insert(product)
    }

    fun getPhotoCamera(context: Context) {
        getAdapterClient().startCamera(context)
    }

    fun getPhotoGallery(context: Context) {
        getAdapterClient().startGallery(context)
    }
    //BitMap generation
    fun setBitMap(bitMap: Bitmap?) {
        this.bitMap = bitMap
    }

    fun getBitMapFromString(string: String): Bitmap {
        return getAdapterClient().decoderStringToBitMap(string)
    }

    /**
     *  Method to convert the BitMap to string, it calls an adapter that converts an bitmap to image
     *  this method is only called when user do a register to BD.
      */
    fun getStringFromBitMap():String{
        if(getAndroidView().getStringBitMapProductToEdit()!=CONSTANTS.STRING_VOID_ELEMENT) // If the user is editing a product this line will get the product to edit string bit map
            return getAndroidView().getStringBitMapProductToEdit()
        if(bitMap == null)
            return CONSTANTS.STRING_VOID_ELEMENT
        val stringEncoded = getAdapterClient().encoderBitMapToString(bitMap!!)
        stringBitmap = stringEncoded
        bitMap=null // Function get string from bitmap is only is called when you
                    // do a register or update, so is necessary remove the bitmap.
                    // In a future the image will be loaded from product or user data
        return stringEncoded
    }

    //Searches
    fun searchSale() {
        getSaleLogic().searchTextInMutableList(getAndroidView().getTextSearched())
    }

    fun searchCustomer() {
        getCustomerLogic().searchTextInMutableList(getAndroidView().getTextSearched())
    }

    fun searchProduct() {
        getProductLogic().searchTextInMutableList(getAndroidView().getTextSearched())
    }

    fun searchUser() {
        getUserLogic().searchTextInMutableList(getAndroidView().getTextSearched())
    }

    /**
     * Abstract factory method to create the logic classes that do transactions with the BD and
     * have functions to control the program execution.
     * Receive a Logic Abstraction enum that indicates the logic needed, return an abstract
     * list logic.
     */
    private fun getAbstractListLogicFactory(logicAbstraction: LogicAbstractionNames): AbstractListLogic {

        return when (logicAbstraction) {
            LogicAbstractionNames.User -> {
                if (userLogic == null) {
                    userLogic = listFactory.createUserList()
                    userLogic!!.setListManager(listManager(ListManagerInstances.UserList))
                    userLogic!!.setUserDao(dataBaseLogic.getUserDao())
                }
                userLogic!!
            }
            LogicAbstractionNames.Customer -> {
                if (customerLogic == null) {
                    customerLogic = listFactory.createCustomerList()
                    customerLogic!!.setCustomerDao(dataBaseLogic.getCustomerDao())
                    customerLogic!!.setListManager(listManager(ListManagerInstances.CustomerList))
                }
                customerLogic!!
            }
            LogicAbstractionNames.Sale -> {
                if (saleLogic == null) {
                    saleLogic = listFactory.createSaleList()
                    saleLogic!!.setSaleDao(dataBaseLogic.getSaleDao())
                    saleLogic!!.setListManager(listManager(ListManagerInstances.SaleList))
                }
                saleLogic!!
            }
            LogicAbstractionNames.Product -> {
                if (productLogic == null) {
                    productLogic = listFactory.createProductList()
                    productLogic!!.setProductDao(dataBaseLogic.getProductDao())
                    productLogic!!.setListManager(listManager(ListManagerInstances.ProductList))
                }
                productLogic!!
            }
        }
    }

    /**
     * Logic classes, they do the transactions with the database and have the logic of each fragment
     * Needs a corresponding dao to get transactions from BD.
     * Need a list manager who notifies the changes to the view and the result of transactions.
     */
    private fun getUserLogic(): AbstractListLogic {
        if (userLogic == null)
            userLogic = getAbstractListLogicFactory(LogicAbstractionNames.User)
        return userLogic!!
    }

    private fun getCustomerLogic(): AbstractListLogic {
        if (customerLogic == null)
            customerLogic = getAbstractListLogicFactory(LogicAbstractionNames.Customer)
        return customerLogic!!
    }

    private fun getSaleLogic(): AbstractListLogic {
        if (saleLogic == null)
            saleLogic = getAbstractListLogicFactory(LogicAbstractionNames.Sale)
        return saleLogic!!
    }

    private fun getProductLogic(): AbstractListLogic {
        if (productLogic == null)
            productLogic = getAbstractListLogicFactory(LogicAbstractionNames.Product)
        return productLogic!!
    }

    private fun getAndroidView(): AndroidView {
        if (androidView == null)
            androidView = AndroidView(this)
        return androidView!!
    }

    private fun getAdapterClient(): AdapterClient{
        if(adapterClient==null)
            adapterClient = AdapterClient()
        return adapterClient!!
    }

    /**
     * List manager method, returns a LisManager created in a factory method.
     * With de list ID makes a list manager that allows to notify or reload lists.
     */
    private fun listManager(listManagerInstance: ListManagerInstances): IListManager {
        val concreteCreatorListManager: CreatorListManager = ConcreteCreatorListManager()
        when (listManagerInstance) {
            ListManagerInstances.ProductList -> {
                if (productListManager == null)
                    productListManager = concreteCreatorListManager.factoryMethod()
                return productListManager!!
            }
            ListManagerInstances.CustomerList -> {
                if (customerListManager == null)
                    customerListManager = concreteCreatorListManager.factoryMethod()
                return customerListManager!!
            }
            ListManagerInstances.SaleList -> {
                if (saleListManager == null)
                    saleListManager = concreteCreatorListManager.factoryMethod()
                return saleListManager!!
            }
            ListManagerInstances.UserList -> {
                if (userListManager == null)
                    userListManager = concreteCreatorListManager.factoryMethod()
                return userListManager!!
            }
            ListManagerInstances.CartList -> {
                if (cartListManager == null)
                    cartListManager = concreteCreatorListManager.factoryMethod()
                return cartListManager!!
            }
            else -> return concreteCreatorListManager.factoryMethod()
        }
    }

    /**
     * Methods to notify or start logic classes.
     * Optional a list listener that are methods from fragment lists that allow to the logic classes reload views.
     * Optional a notifier that show to the user the result of transactions
     */
    fun notifyProductLogic(listListener: IListListener?) {
        listManager(ListManagerInstances.ProductList).setListListener(listListener)
        listManager(ListManagerInstances.ProductList).setNotifier(getAndroidView().getNotifierView())
        getProductLogic().loadData()
    }

    fun notifySaleLogic(listListener: IListListener?) {
        listManager(ListManagerInstances.SaleList).setListListener(listListener)
        listManager(ListManagerInstances.SaleList).setNotifier(getAndroidView().getNotifierView())
        getSaleLogic().loadData()
    }

    fun notifyCustomerLogic(listListener: IListListener?) {
        listManager(ListManagerInstances.CustomerList).setListListener(listListener)
        listManager(ListManagerInstances.CustomerList).setNotifier(getAndroidView().getNotifierView())
        getCustomerLogic().loadData()
    }

    fun notifyUserLogic(listListener: IListListener?) {
        listManager(ListManagerInstances.UserList).setListListener(listListener)
        listManager(ListManagerInstances.UserList).setNotifier(getAndroidView().getNotifierView())
        getUserLogic().loadData()
    }

    fun notifyCartLogic(listListener: IListListener?, iCart: ICart) {
        listManager(ListManagerInstances.CartList).setListListener(listListener)
        listManager(ListManagerInstances.CartList).setNotifier(getAndroidView().getNotifierView())
        getProductLogic().setCartListener(iCart)
    }

    fun confirmLogin(login: LoginActivity, userName: String, password: String) {
        dataBaseLogic = ViewModelProvider(login).get(DataBaseLogic::class.java)
        doAsyncResult {
            getUserLogic().confirmLogin(userName, password)
            uiThread {
                if (getUser().getName() != CONSTANTS.STRING_VOID_ELEMENT) {
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
                } else{
                    user = null // Allows to user to try again inserting a new user and password
                    getAndroidView().showToastMessage(
                        "Usuario $userName no encontrado o contraseña incorrecta",
                        login
                    )
                }
            }
        }
    }

    fun register(login: LoginActivity, userName: String, password: String) {
        dataBaseLogic = ViewModelProvider(login).get(DataBaseLogic::class.java)
        getUserLogic().insert(
            User(
                userName,
                password,
                "admin",
                "",
                CONSTANTS.DEFAULT_USER_LATITUDE,
                CONSTANTS.DEFAULT_USER_LONGITUDE
            )
        )
    }

    fun askSaveLocation() {
        val location =
            getAndroidView().getString(R.string.location) + " " + userLatitude + " - " + userLongitude
        getAndroidView().dialogConfirmRegister(location, R.string.location, R.string.saveLocation)
    }

    fun saveUserLocation() {
        getUser().setLatitude(userLatitude)
        getUser().setLongitude(userLongitude)
        getUserLogic().update(getUser())
    }

    fun logOut() {
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
}
