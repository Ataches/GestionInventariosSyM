# GestionInventariosSyM

_Aplicativo para manejo de inventarios._

## Especificaciones

Proyecto realizado aplicando arquitectura tres capas y modelo MVC.

### Pre-requisitos 📋

_Elementos necesarios_

```
Android studio 4.1
```
## Demo de la app ⚙️

### Inicio de sesión
Funcionalidades de la aplicación: 

* Registro de usuarios
* Registro de productos
* Registro de clientes
* Registro de ventas

_Todos los registros realizados se almacenan en la base de datos de la aplicación_

![](DemoApp.gif)

_Para el registro de nuevos usuarios es necesario que un administrador lo registre, quien puede otorgar privilegios de admin u otro_

### Registro de productos

Es posible el registro de productos por medio del acceso a la tienda de la aplicación, donde se registran los siguientes campos:

* Nombre del producto
* Precio del producto
* Descripción del producto
* Cantidad de unidades del producto
* Imagen del producto (opcional)

![](DemoGifs/DemoNewProduct.gif)

_El producto registrado queda almacenado en la base de datos para compras posteriores o edición del mismo_

### Creación y eliminación de usuarios, edición de productos

Para crear o eliminar un usuario es necesario tener el privilegio de administrador el cual lo otorga otro administrador.

Campos para crear un nuevo usuario:

* Nombre de usuario
* Contraseña de usuario
* Privilegio de usuario

![](DemoGifs/DemoUsersAndEditProduct.gif)

### Registro de ventas en el aplicativo

Para registrar una venta es necesario tener un cliente registrado, el cual se puede crear desde la ventana de nueva venta o desde el fragmento de listado de clientes.
Desde el menú de inicio o el botón toolbar se puede acceder al menú de ventas registradas una vez hecha la venta, la cual muestra el detalle de la venta.

![](DemoGifs/DemoVenta.gif)

### Edición y eliminación de clientes

Desde la sección de listado de clientes es posible la edición y eliminación de clientes, al igual que los productos es posible editar los datos o suprimir los registros. 

![](DemoGifs/DemoCustomerEdit.gif)

## Autor ✒️

* **Juan Sebastián Sánchez Mancilla** - [ataches](https://github.com/Ataches)

_Aplicación creada a partir de auto aprendizaje y aplicando los conocimientos adquiridos en el curso de desarrollo Android con Kotlin dirigido por ScotiaBank_
