# GestionInventariosSyM

_Aplicativo para manejo de inventarios._

## Especificaciones

Proyecto realizado aplicando arquitectura tres capas y modelo MVC.

### Pre-requisitos ??

_Elementos necesarios_

```
Android studio 4.1
```
## Demo de la app ??

### Inicio de sesi車n
Funcionalidades de la aplicaci車n: 

* Registro de usuarios
* Registro de productos
* Registro de clientes
* Registro de ventas

_Todos los registros realizados se almacenan en la base de datos de la aplicaci車n_

![](DemoApp.gif)

_Para el registro de nuevos usuarios es necesario que un administrador lo registre, quien puede otorgar privilegios de admin u otro_

### Registro de productos

Es posible el registro de productos por medio del acceso a la tienda de la aplicaci車n, donde se registran los siguientes campos:

* Nombre del producto
* Precio del producto
* Descripci車n del producto
* Cantidad de unidades del producto
* Imagen del producto (opcional)

![](DemoGifs/DemoNewProduct.gif)

_El producto registrado queda almacenado en la base de datos para compras posteriores o edici車n del mismo_

### Creaci車n y eliminaci車n de usuarios, edici車n de productos

Para crear o eliminar un usuario es necesario tener el privilegio de administrador el cual lo otorga otro administrador.

Campos para crear un nuevo usuario:

* Nombre de usuario
* Contrase?a de usuario
* Privilegio de usuario

![](DemoGifs/DemoUsersAndEditProduct.gif)

### Registro de ventas en el aplicativo

Para registrar una venta es necesario tener un cliente registrado, el cual se puede crear desde la ventana de nueva venta o desde el fragmento de listado de clientes.
Desde el men迆 de inicio o el bot車n toolbar se puede acceder al men迆 de ventas registradas una vez hecha la venta, la cual muestra el detalle de la venta.

![](DemoGifs/DemoVenta.gif)

### Edici車n y eliminaci車n de clientes

![](DemoGifs/DemoCustomerEdit.gif)

## Autor ??

* **Juan Sebasti芍n S芍nchez Mancilla** - [ataches](https://github.com/Ataches)

## Expresiones de Gratitud ??

* Agradecimientos a los profesores Carlos, Jaime y Cristian, y al equipo de ScotiaBank.
