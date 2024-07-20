/*
    Christian Emanuel Itzep Lemus
    Carné: 2019528
    Código Técnico: IN5AM
    Fecha de Creación: 
		28/03/2023
    Fechas de Modificación:
		17/04/2023
        18/04/2023
        26/04/2023
        02/05/2023
        09/05/2023
        10/05/2023
        24/05/2023
        25/05/2023
        27/05/2023
        29/05/2023
        30/05/2023
        31/05/2023
        01/06/2023
        06/06/2023
*/
Drop database if exists DBTonysKinal2023;
Create database DBTonysKinal2023;
use DBTonysKinal2023;

Create table Empresas(
	codigoEmpresa int auto_increment not null,
    nombreEmpresa varchar (150) not null,
    direccion varchar (150)not null,
    telefono varchar (8),
    primary key PK_codigoEmpresa (codigoEmpresa)
);

Create table TipoEmpleado(
	codigoTipoEmpleado int not null auto_increment,
    descripcion varchar (50) not null,
    primary key PK_codigoTipoEmpleado (codigoTipoEmpleado)

);

Create table TipoPlato(
	codigoTipoPlato int not null auto_increment,
    descripcionTipo varchar (100) not null,
    primary key PK_codigoTipoPlato (codigoTipoPlato)
);

Create table Productos (
	codigoProducto int not null auto_increment,
	nombreProducto varchar (150) not null,
    cantidad int not null,
    primary key PK_codigoProducto (codigoProducto)
);

Create table Empleados(
	codigoEmpleado int not null auto_increment,
    numeroEmpleado int not null,
    apellidosEmpleado varchar (150) not null,
    nombresEmpleado varchar (150) not null,
    direccionEmpleado varchar (150) not null,
    telefonoContacto varchar (8),
    gradoCocinero varchar (50),
    codigoTipoEmpleado int not null,
    primary key PK_codigoEmpleado (codigoEmpleado),
    constraint FK_Empleados_TipoEmpleado foreign key
		(codigoTipoEmpleado) references TipoEmpleado (codigoTipoEmpleado)
	
);

Create table Servicios (
	codigoServicio int not null auto_increment,
    fechaServicio date not null,
    tipoServicio varchar (150) not null,
    horaServicio time not null,
    lugarServicio varchar (150) not null,
    telefonoServicio varchar (8) not null,
    codigoEmpresa int not null,
    primary key PK_codigoSevicio (codigoServicio),
    Constraint FK_Servicios_Empresas foreign key (codigoEmpresa)
		references Empresas (codigoEmpresa)
);

Create table Presupuesto(
	codigoPresupuesto int not null auto_increment,
    fechaSolicitud date not null,
    cantidadPresupuesto decimal(10,2) not null,
    codigoEmpresa int not null,
    primary key PK_codigoPresupuesto (codigoPresupuesto),
    constraint FK_Presupuestos_Empresas foreign key (codigoEmpresa)
		references Empresas (codigoEmpresa)
);

create table Platos(
	codigoPlato int not null auto_increment,
    cantidadPlato int not null,
    nombrePlato varchar (150) not null,
    descripcionPlato varchar (150) not null,
    precioPlato decimal (10,2) not null,
    codigoTipoPlato int not null,
    primary key	PK_codigoPlato (codigoPlato),
    constraint FK_Platos_TipoPlato foreign key (codigoTipoPlato)
		references TipoPlato (codigoTipoPlato)
); 
Create table Productos_has_Platos (
	Productos_codigoProducto int not null,
    codigoPlato int not null,
    codigoProducto int not null,
    primary key PK_Productos_codigoProducto (Productos_codigoProducto),
    constraint FK_Productos_has_Platos_Productos foreign key (codigoProducto)
		references Productos (codigoProducto),
	constraint FK_Productos_has_Platos_Platos foreign key (codigoPlato)
		references Platos (codigoPlato)
);

Create table Servicios_has_Platos(
	Servicios_codigoServicio int not null,
    codigoPlato int not null,
    codigoServicio int not null,
    primary key PK_Servicios_codigoServicio (Servicios_codigoServicio),
    constraint	FK_Servicios_has_Platos_Servicios foreign key (codigoServicio)
		references Servicios (codigoServicio),
	constraint FK_Servicios_has_Platos_Platos foreign key (codigoPlato)
		references Platos (codigoPlato)
);

Create table Servicios_has_Empleados(
	Servicios_codigoServicio int not null,
    codigoServicio int not null,
    codigoEmpleado  int not null,
    fechaEvento date not null,
    horaEvento time not null,
    lugarEvento varchar (150) not null,
    primary key PK_Servicios_codigoServicio (Servicios_codigoServicio),
    constraint FK_Servicios_has_Empleados_Servicios foreign key (codigoServicio)
		references Servicios (codigoServicio),
	constraint FK_Servicios_has_Empleados_Empleados foreign key (codigoEmpleado)
		references Empleados (codigoEmpleado)
);

Create table Usuario(
	codigoUsuario int not null auto_increment,
    nombreUsuario varchar (100) not null,
    apellidoUsuario varchar (100) not null,
    usuarioLogin varchar (50) not null,
    contrasena varchar (50) not null,
    primary key PK_CodigoUsuario (codigoUsuario)
);

Create table login(
	usuarioMaster varchar (50),
    passwordLogin varchar (50),
    primary key PK_usuarioMaster (usuarioMaster)
);


-- ------------------------------------------------------------------ PROCEDIMIENTOS ALCAMCENADOS -------------------------------------------------------------------------------------
-- ----------------------------------------------------------------Procedimientos de  Usuario ------------------------------------------------------------------------------------------
-- -------------------- Agregar Usuario ---------------------------
Delimiter //
	Create Procedure sp_AgregarUsuario (in nombreUsuario varchar (100), in apellidoUsuario varchar (100), in usuarioLogin varchar (50),
		in contrasena varchar (50))
        Begin
			Insert into Usuario (nombreUsuario, apellidoUsuario, usuarioLogin, contrasena)
				values (nombreUsuario, apellidoUsuario, usuarioLogin, contrasena);
        End //
        
Delimiter ;
call sp_AgregarUsuario('Christian', 'Itzep', 'Citzep','12345');
call sp_AgregarUsuario('Pedro', 'Armas', 'parmas', 'parmas');
-- -------------------------Listar Usuario -----------------------------------------
Delimiter //
	create procedure sp_ListarUsuarios()
		Begin
			Select
				U.codigoUsuario,
                U.nombreUsuario,
                U.apellidoUsuario,
                U.usuarioLogin,
                U.contrasena
                from Usuario U;
        End //
Delimiter ;
call sp_ListarUsuarios();
-- --------------------------------------------------------------- Procedimientos de Empresas -------------------------------------------------------------------------------------
-- ------------------- Agregar Empresa ------------------------------
Delimiter //
	Create procedure sp_AgregarEmpresa (in nombreEmpresa varchar (150), in direccion varchar (150), in telefono varchar (8))
    Begin 
		Insert into Empresas (nombreEmpresa, direccion, telefono)
			values (nombreEmpresa,direccion, telefono);
    End //
Delimiter ;
call sp_AgregarEmpresa ('Walmart','Roosevelt 26-95, Cdad. de Guatemala','34652312');
call sp_AgregarEmpresa('Cemaco','Roosevelt 25-50 Zona 7,Cdad. de Guatemala','24569078');
call sp_AgregarEmpresa('Intelaf','Las Plazas, Cdad. de Guatemala 01012','56892176');
call sp_AgregarEmpresa('Claro','7A Avenida 12-39, Cdad. de Guatemala 01001','12234354');
call sp_AgregarEmpresa('Kinal',' 6A Avenida 13-54, Cdad. de Guatemala 01007','90785432');
-- ---------------------Editar Empresa -----------------------------
Delimiter // 
	Create procedure sp_EditarEmpresa (in codEmpresa int, in nomEmpresa varchar (150), in dire varchar(150), in tele varchar (8))
    Begin
		Update  Empresas E set
			E.nombreEmpresa = nomEmpresa,
            E.direccion = dire,
            E.telefono = tele
            where E.codigoEmpresa = codEmpresa;
    End//
Delimiter ;
-- -------------------Listar Empresas -----------------------------
Delimiter //
	Create procedure sp_ListarEmpresas ()
	Begin
		Select
			E.codigoEmpresa,
            E.nombreEmpresa,
            E.direccion,
            E.telefono
            from Empresas E;
    End //
Delimiter ;
call sp_ListarEmpresas();
-- -----------------Buscar Empresa  -------------------------------
Delimiter //
	Create procedure sp_BuscarEmpresa(in codEmpresa int)
    Begin
		select 
			E.codigoEmpresa,
            E.nombreEmpresa,
            E.direccion,
            E.telefono
            from Empresas E
            Where E.codigoEmpresa = codEmpresa;
    End //
Delimiter ;

-- --------------- Eliminar Empresa ------------------------------
Delimiter //
	Create procedure sp_EliminarEmpresa (in codEmpresa int)
    Begin
		Delete from Empresas
			where codigoEmpresa = codEmpresa;
    End //
Delimiter ;

-- ----------------------------------------------------- Procedimientos de TipoEmpleado ------------------------------------------------------------------------
-- -----------------Agregar TipoEmpleado ---------------------------------
Delimiter //
	Create procedure sp_AgregarTipoEmpleado (in descripcion varchar (50))
    Begin
		insert into TipoEmpleado (descripcion)
			values (descripcion);
    End //
Delimiter ;
call sp_AgregarTipoEmpleado ('Chef');
call sp_AgregarTipoEmpleado('Recepcionista');
call sp_AgregarTipoEmpleado('Maitre');
call sp_AgregarTipoEmpleado('Pastelero');
call sp_AgregarTipoEmpleado('Mesero');
-- --------------- Editar TipoEmpleado ----------------------------------
Delimiter //
	Create procedure sp_EditarTipoEmpleado (in codTipo int, in descri varchar (50))
		Begin
			update TipoEmpleado T set
				T.descripcion = descri
				where T.codigoTipoEmpleado = codTipo;
        End //
Delimiter ;

-- -------------- Listar TipoEmpleados -----------------------------------
Delimiter //
	Create procedure sp_ListarTipoEmpleados ()
	Begin
		Select 
			T.codigoTipoEmpleado,
            T.descripcion
            from TipoEmpleado T;
    End //
Delimiter ;
call sp_ListarTipoEmpleados();
-- -------------- Buscar TipoEmpleado -----------------------------------
Delimiter //
	Create procedure sp_BuscarTipoEmpleado (in codTipo int)
    Begin
		Select 
			T.codigoTipoEmpleado,
            T.descripcion
            from TipoEmpleado T
            where T.codigoTipoEmpleado = codTipo;
    End //
Delimiter ;
-- -------------- Eliminar TipoEmpleado --------------------------------
Delimiter //
	Create procedure sp_EliminarTipoEmpleado (in codTipo int)
	Begin
		Delete from TipoEmpleado
			Where codTipo = codigoTipoEmpleado;
    End //
Delimiter ;

-- ----------------------------------------------------- Procedimientos de TipoPlato ---------------------------------------------------------------------------
-- -----------------Agregar TipoPlato ---------------------------------
Delimiter //
	Create procedure sp_AgregarTipoPlato (in descripcionTipo varchar (50))
    Begin
		insert into TipoPlato (descripcionTipo)
			values (descripcionTipo);
    End //
Delimiter ;
call sp_AgregarTipoPlato('Postres');
call sp_AgregarTipoPlato('Mariscos');
call sp_AgregarTipoPlato('Sopas');
call sp_AgregarTipoPlato('Occidental');
call sp_AgregarTipoPlato('Italiana');
-- --------------- Editar TipoPlato ----------------------------------
Delimiter //
	Create procedure sp_EditarTipoPlato (in codTipo int, in descri varchar (50))
		Begin
			update TipoPlato T set
				T.descripcionTipo = descri
				where T.codigoTipoPlato = codTipo;
        End //
Delimiter ;

-- -------------- Listar TipoPlatos -----------------------------------
Delimiter //
	Create procedure sp_ListarTipoPlatos ()
	Begin
		Select 
			T.codigoTipoPlato,
            T.descripcionTipo
            from TipoPlato T;
    End //
Delimiter ;
call sp_ListarTipoPlatos();
-- -------------- Buscar TipoPlato -----------------------------------
Delimiter //
	Create procedure sp_BuscarTipoPlato (in codTipo int)
    Begin
		Select 
			T.codigoTipoPlato,
            T.descripcionTipo
            from TipoPlato T
            where T.codigoTipoPlato = codTipo;
    End //
Delimiter ;
-- -------------- Eliminar TipoPlato --------------------------------
Delimiter //
	Create procedure sp_EliminarTipoPlato (in codTipo int)
	Begin
		Delete from TipoPlato
			Where codTipo = codigoTipoPlato;
    End //
Delimiter ;
-- ----------------------------------------------------- Procedimientos de Productos ---------------------------------------------------------------------------
-- ------------- Agregar Producto ----------------------------------
Delimiter //
	create procedure sp_AgregarProducto (in nombreProducto varchar (150), in cantidad int)
    begin
		insert into Productos (nombreProducto, cantidad )
			values (nombreProducto, cantidad);
    end //
Delimiter ;
call sp_AgregarProducto('Carne de res', 20);
call sp_AgregarProducto('Camarones', 30);
call sp_AgregarProducto('Fideos', 10);
call sp_AgregarProducto('Pepperoni', 25);
call sp_AgregarProducto('Pan', 40);
-- ------------- Editar Producto ----------------------------------
Delimiter //
	Create procedure sp_EditarProducto (in codProducto int, in nomProducto varchar (150), in cant int)
    Begin
		Update Productos P set 
			P.nombreProducto  = nomProducto,
            P.cantidad = cant
            where P.codigoProducto = codProducto;
			
    End //
Delimiter ;
-- ------------- Listar Productos ----------------------------------
Delimiter //
	Create procedure sp_ListarProductos ()
	Begin
        Select 
			P.codigoProducto,
            P.nombreProducto,
            P.cantidad
            from Productos P;
	End //
Delimiter ;
call sp_ListarProductos();
-- ------------- Buscar Producto ----------------------------------
Delimiter //
	Create procedure sp_BuscarProducto (in codProducto int)
    Begin 
		Select 
			P.codigoProducto,
            P.nombreProducto,
            P.cantidad
            from Productos P
			Where P.codigoProducto = codProducto;
    End //
Delimiter ;
-- ------------- Eliminar Producto ----------------------------------
Delimiter //
	Create procedure sp_EliminarProducto (in codProducto int)
    Begin
		Delete  from Productos
			where codProducto = codigoProducto;
    End //
Delimiter ;
-- ----------------------------------------------------- Procedimientos de Empleados ---------------------------------------------------------------------------
-- ------------- Agregar Empleado ----------------------------------
Delimiter //
	Create procedure sp_AgregarEmpleado (in numeroEmpleado int, in apellidosEmpleado varchar (150), in nombresEmpleado varchar (150), 
		in direccionEmpleado varchar (150),in  telefonoContacto varchar (8),in  gradoCocinero varchar (50), in codigoTipoEmpleado int)
			Begin
				Insert into Empleados (numeroEmpleado, apellidosEmpleado, nombresEmpleado, direccionEmpleado, telefonoContacto, gradoCocinero, 
					codigoTipoEmpleado)
						Values (numeroEmpleado, apellidosEmpleado, nombresEmpleado, direccionEmpleado, telefonoContacto, gradoCocinero, 
							codigoTipoEmpleado);
            End //
Delimiter ;
call sp_AgregarEmpleado(1, 'Itzep Lemus', 'Christian Emanuel', 'Zona 7 Bethania', '12345678', 'Sous-chef ', 1);
call sp_AgregarEmpleado(2, 'Rodríguez Sánchez','María Alejandra','57 Calle 35-67 Zona 24','43217812','Chef de partie', 1);
call sp_AgregarEmpleado(3, 'Ramírez Fernández','Juan Fernando',' 3 Av, 3-32, zona 13','59123456','Chef de cuisine', 4);
call sp_AgregarEmpleado(4, 'González García','Ana Luisa','7 calle, 10-49, zona 8','90871234','Cuisinier', 1);
call sp_AgregarEmpleado(5, 'López Martínez','Pedro Saúl','4 Av 9-12, zona 10','73217821','Commis', 4);
-- ------------- Editar Empleado ----------------------------------
Delimiter //
	Create procedure sp_EditarEmpleado(in codEmpleado int,in numEmpleado int, in apellidos varchar (150), in nombres varchar (150), 
		in direccion varchar (150), in teleContacto varchar (8), in graCocinero varchar (50), in codTipo int)
		Begin
			Update Empleados E set
				E.codigoEmpleado = codEmpleado,
                E.numeroEmpleado = numEmpleado,
                E.apellidosEmpleado = apellidos,
                E.nombresEmpleado = nombres,
                E.direccionEmpleado = direccion,
                E.telefonoContacto = teleContacto,
                E.gradoCocinero = graCocinero,
                E.codigoTipoEmpleado = codEmpleado
                Where E.codigoEmpleado = codEmpleado;
		End // 
Delimiter ;
-- ------------- Listar Empleados ----------------------------------
Delimiter //
	Create procedure sp_ListarEmpleados ()
    Begin
		Select
			E.codigoEmpleado,
			E.numeroEmpleado,
			E.apellidosEmpleado,
			E.nombresEmpleado,
			E.direccionEmpleado,
			E.telefonoContacto,
			E.gradoCocinero, 
			E.codigoTipoEmpleado
			From Empleados E;
    End //
Delimiter ;
call sp_ListarEmpleados(); 
-- ------------- Buscar Empleado ----------------------------------
Delimiter //
	Create procedure sp_BuscarEmpleado (in codEmpleado int)
	Begin
		Select
			E.codigoEmpleado,
			E.numeroEmpleado,
			E.apellidosEmpleado,
			E.nombresEmpleado,
			E.direccionEmpleado,
			E.telefonoContacto,
			E.gradoCocinero, 
			E.codigoTipoEmpleado
			From Empleados E
            Where E.codigoEmpleado = codEmpleado;
    End //
Delimiter ;
-- ------------- Eliminar Empleado ----------------------------------
Delimiter //
	Create procedure sp_EliminarEmpleado (in codEmpleado int)
    Begin
		Delete from Empleados
			Where codigoEmpleado = codEmpleado;
    End //
Delimiter ;
-- ----------------------------------------------------- Procedimientos de Servicios ---------------------------------------------------------------------------
-- ------------- Agregar Servicio ----------------------------------
Delimiter //
	Create procedure sp_AgregarServicio (in fechaServicio date , in tipoServicio varchar (150),in  horaServicio time, in lugarServicio varchar (150), 
		in telefonoServicio varchar (8), in codigoEmpresa int)
		Begin
			insert into Servicios (fechaServicio, tipoServicio, horaServicio, lugarServicio, telefonoServicio, codigoEmpresa)
				Values (fechaServicio, tipoServicio, horaServicio, lugarServicio, telefonoServicio, codigoEmpresa);
        End //
Delimiter ;
call sp_AgregarServicio('2023-03-28', 'Boda', '11:30', 'San Lucas', '12345678', 1 );
call sp_AgregarServicio('2023-06-12','Aniversario','12:00','Mixco','24568912', 2);
call sp_AgregarServicio('2023-07-03','Cumpleaños','9:45','Ciudad de Guatemala','78651254',5);
call sp_AgregarServicio('2023-12-25','Convivio','8:30','Antigua Guatemala','12563289',4);
call sp_AgregarServicio('2023-11-21','Cumpleaños','9:00','Mixco','98127612',3);
-- ------------- Editar Servicio ----------------------------------
Delimiter //
	Create procedure sp_EditarServicio (in codServicio int, in fecha date , in tipo varchar (150),in  hora time, in lugar varchar (150), 
    in telefono varchar (8))
        Begin 
			Update Servicios S set
                S.fechaServicio= fecha, 
                S.tipoServicio = tipo, 
                S.horaServicio = hora, 
                S.lugarServicio = lugar, 
                S.telefonoServicio = telefono
                Where S.codigoServicio = codServicio;
        End //
Delimiter ;
-- ------------- Listar Servicios ----------------------------------
Delimiter //
	Create procedure sp_ListarServicios ()
		Begin
			Select 
				S.codigoServicio, 
                S.fechaServicio, 
                S.tipoServicio, 
                S.horaServicio, 
                S.lugarServicio, 
                S.telefonoServicio, 
                S.codigoEmpresa
				From Servicios S;
        End //
Delimiter ;
call sp_ListarServicios();
-- ------------- Buscar Servicio ----------------------------------
Delimiter //
	Create procedure sp_BuscarServicio (in codServicio int)
		Begin
			Select
				S.codigoServicio, 
                S.fechaServicio, 
                S.tipoServicio, 
                S.horaServicio, 
                S.lugarServicio, 
                S.telefonoServicio, 
                S.codigoEmpresa
				From Servicios S
                Where codigoServicio = codServicio;
        End //
Delimiter ;

-- ------------- Eliminar Servicio ----------------------------------
Delimiter //
	Create procedure sp_EliminarServicio (in codServicio int)
		Begin
			Delete from Servicios 
				where codigoServicio =  codServicio;
        End //
Delimiter ;
-- ----------------------------------------------------- Procedimientos de Presupuesto --------------------------------------------------------------------------
-- ------------- Agregar Presupuesto ----------------------------------
Delimiter // 
	Create procedure sp_AgregarPresupuesto (in fechaSolicitud date , in cantidadPresupuesto decimal (10,2), in codigoEmpresa  int)
    begin
		Insert into Presupuesto (fechaSolicitud, cantidadPresupuesto, codigoEmpresa)
			values (fechaSolicitud, cantidadPresupuesto, codigoEmpresa);
    End //
Delimiter ;
call sp_AgregarPresupuesto ('2023-02-08', 2000.50, 1);
call sp_AgregarPresupuesto('2023-06-05', 1505.33, 4);
call sp_AgregarPresupuesto('2023-05-01', 950.95, 5);
call sp_AgregarPresupuesto('2023-12-02', 7500.55, 3);
call sp_AgregarPresupuesto('2023-10-12', 2105.11, 2);
-- ------------- Editar Presupuesto ----------------------------------
Delimiter //
	Create procedure sp_EditarPresupuesto (in codPresupuesto int,in fecha date, in cantidad decimal (10,2))
    Begin
		Update Presupuesto P set
			P.codigoPresupuesto = codPresupuesto,
            P.fechaSolicitud = fecha,
            P.cantidadPresupuesto = cantidad
            where P.codigoPresupuesto = codPresupuesto;
    End //
Delimiter ;	
-- ------------- Listar Presupuestos ----------------------------------
Delimiter //
	Create procedure sp_ListarPresupuestos ()
		Begin
			Select
				P.codigoPresupuesto, P.fechaSolicitud, P.cantidadPresupuesto, P.codigoEmpresa
					from Presupuesto P;
        End //
Delimiter ;
call sp_ListarPresupuestos();
-- ------------- Buscar Presupuesto ----------------------------------
Delimiter //
	Create procedure sp_BuscarPresupuesto(in codPresupuesto int)
		Begin
			Select
				P.codigoPresupuesto,
                P.fechaSolicitud,
                P.cantidadPresupuesto,
                P.codigoEmpresa
				from Presupuesto P
                Where codigoPresupuesto = codPresupuesto;
        End //
Delimiter ;
-- ------------- Eliminar Presupuesto ----------------------------------
Delimiter //
	Create procedure sp_EliminarPresupuesto (in codPresupuesto int)
    Begin
		delete from Presupuesto
			Where codigoPresupuesto = codPresupuesto;
    End //
Delimiter ;
-- ----------------------------------------------------- Procedimientos de Platos -------------------------------------------------------------------------------
-- ------------- Agregar Plato ----------------------------------
Delimiter //
	Create procedure sp_AgregarPlato (in cantidadPlato int, in nombrePlato varchar (150), in descripcionPlato varchar (150), 
		in precioPlato decimal (10,2), in codigoTipoPlato int)
        Begin
			Insert into Platos (cantidadPlato, nombrePlato, descripcionPlato, precioPlato, codigoTipoPlato)
				values (cantidadPlato, nombrePlato, descripcionPlato, precioPlato, codigoTipoPlato);
        End //
Delimiter ;
call sp_AgregarPlato(30, 'Pastel','Pastel de fresas con crema', 46.89,1);
call sp_AgregarPlato(24, 'Lasaña', 'Pasta para lasaña, carne de res molida, salsa De Tomate, hojas de laurel,queso parmesano', 54.99, 5);
call sp_AgregarPlato(50, 'Camarones fritos', 'Camarones, rodajas de limón, cebolla, perejil', 20.49, 2);
call sp_AgregarPlato(40, 'Pollo kung pao', 'Pechuga de pollo, chiles secos, pimiento morrón verde y rojo, cebollín', 30.12, 4);
call sp_AgregarPlato(21, 'Sopa Ramen', 'caldo de cerdo, pollo y verduras con salsa de soja, alga kombu, bonito seco y katsuobushi', 25.98, 3);
-- ------------- Editar Plato ----------------------------------
Delimiter //
	Create procedure sp_EditarPlato (in codPlato int, in cantPlato int, in nomPlato varchar (150), in descriPlato varchar (150), 
		in precio decimal (10,2))
		Begin
			Update Platos P set 
				P.cantidadPlato = cantPlato,
                P.nombrePlato = nomPlato,
                P.descripcionPlato = descriPlato,
                P.precioPlato = precio
                Where P.codigoPlato = codPlato; 
        End //
Delimiter ;
-- ------------- Listar Platos ----------------------------------
Delimiter //
	Create procedure sp_ListarPlatos ()
		Begin
			Select 
				P.codigoPlato,
                P.cantidadPlato,
                P.nombrePlato,
                P.descripcionPlato,
                P.precioPlato,
                P.codigoTipoPlato
                From Platos P;
         End //
Delimiter ;
call sp_ListarPlatos();
-- ------------- Buscar Plato ----------------------------------
Delimiter //
	Create procedure sp_BuscarPlato (in codPlato int)
    Begin 
		Select
			P.codigoPlato,
			P.cantidadPlato,
			P.nombrePlato,
			P.descripcionPlato,
			P.precioPlato,
			P.codigoTipoPlato
			From Platos P
			Where codigoPlato = codPlato;
    End //
Delimiter ;
-- ------------- Eliminar Plato ----------------------------------
Delimiter //
	Create procedure sp_EliminarPlato(in codPlato int)
		Begin
			Delete from Platos
				Where codigoPlato = codPlato;
		End //
Delimiter ;
-- ----------------------------------------------------- Procedimientos de Productos_has_Platos ------------------------------------------------------------------
-- ------------- Agregar Productos_has_Platos ---------------------------------
Delimiter //
	Create procedure sp_AgregarProductos_has_Platos (in Productos_codigoProducto int, in codigoPlato int, in codigoProducto int)
		Begin
			insert into Productos_has_Platos (Productos_codigoProducto,codigoPlato, codigoProducto)
				values (Productos_codigoProducto,codigoPlato, codigoProducto);
        End //
Delimiter ;
call sp_AgregarProductos_has_Platos(1,1,1);
call sp_AgregarProductos_has_Platos(2, 3,5);
call sp_AgregarProductos_has_Platos(3, 2,2);
call sp_AgregarProductos_has_Platos(4, 5,4);
call sp_AgregarProductos_has_Platos(5, 4,3);
-- ------------- Listar Productos_has_Platos ----------------------------------
Delimiter //
	Create procedure sp_ListarProductos_has_Platos ( )
		begin
			select 
				PP.Productos_codigoProducto,
				PP.codigoPlato,
                PP.codigoProducto
                from Productos_has_Platos PP;
        End //
Delimiter ;
call sp_ListarProductos_has_Platos();
-- ------------- Buscar Productos_has_Platos ----------------------------------
Delimiter //
	Create procedure sp_BuscarProductos_has_Platos (in Produc_cod int)
    Begin
		Select	
			PP.Productos_codigoProducto,
			PP.codigoPlato,
			PP.codigoProducto
			from Productos_has_Platos PP
			Where Productos_codigoProducto = Produc_cod;
    End //
Delimiter ;

-- ------------- Eliminar Productos_has_Platos ----------------------------------
Delimiter //
	Create procedure sp_EliminarProductos_has_Platos(in Produc_cod int )
		Begin
			Delete from Productos_has_Platos
				where Productos_codigoProducto = Produc_cod;
        End //
Delimiter ;
-- ----------------------------------------------------- Procedimientos de Servicios_has_Platos ------------------------------------------------------------------
-- ------------- Agregar Servicios_has_Platos ----------------------------------
Delimiter //
	Create procedure sp_AgregarServicios_has_Platos (in Servicios_codigoServicio int,in codigoPlato int, in codigoServicio int)
		Begin
			insert into Servicios_has_Platos (Servicios_codigoServicio,codigoPlato, codigoServicio)
				values (Servicios_codigoServicio,codigoPlato, codigoServicio);
        End //
Delimiter ;
call sp_AgregarServicios_has_Platos (1,1,1);
call sp_AgregarServicios_has_Platos (2, 4, 2);
call sp_AgregarServicios_has_Platos (3, 5, 3);
call sp_AgregarServicios_has_Platos (4, 2, 5);
call sp_AgregarServicios_has_Platos (5, 3, 4);
-- ------------- Listar Servicios_has_Platos ----------------------------------
Delimiter //
	Create procedure sp_ListarServicios_has_Platos ()
		begin
			select 
				SP.Servicios_codigoServicio,
				SP.codigoPlato,
                SP.codigoServicio
                from Servicios_has_Platos SP;
        End //
Delimiter ;
call sp_ListarServicios_has_Platos();
-- ------------- Buscar Servicios_has_Platos ----------------------------------
Delimiter //
	Create procedure sp_BuscarServicios_has_Platos (in Servi_cod int)
    Begin
		Select	
			SP.Servicio_codigoServicio,
			SP.codigoPlato,
			SP.codigoServicio
			from Servicios_has_Platos SP
			Where Servicio_codigoServicio = Servi_cod;
    End //
Delimiter ;
-- ------------- Eliminar Servicios_has_Platos ---------------------------------
Delimiter //
	Create procedure sp_EliminarServicios_has_Platos(in Servi_cod int )
		Begin
			Delete from Servicios_has_Platos
				where Servicio_codigoServicio = Servi_cod;
        End //
Delimiter ;
-- ----------------------------------------------------- Procedimientos de Servicios_has_Empleados -----------------------------------------------------------------
-- ------------- Agregar Servicios_has_Empleados ----------------------------------
Delimiter //
	Create procedure sp_AgregarServicios_has_Empleados (in Servicios_codigoServicio int,in codigoServicio int , in codigoEmpleado int, 
		in fechaEvento date, in horaEvento time , in lugarEvento varchar (150))
        Begin
			insert into Servicios_has_Empleados (Servicios_codigoServicio,codigoServicio, codigoEmpleado, fechaEvento, horaEvento, lugarEvento)
				values (Servicios_codigoServicio,codigoServicio, codigoEmpleado, fechaEvento, horaEvento, lugarEvento);
        End //
Delimiter ;
call sp_AgregarServicios_has_Empleados (1,1,1, '2023-05-24', '9:30', 'Antigua Guatemala');
call sp_AgregarServicios_has_Empleados(2, 4, 3, '2023-07-21', '11:15', 'Mixco');
call sp_AgregarServicios_has_Empleados(3, 2, 4, '2023-09-11', '8:45', 'Ciudad de Guatemala');
call sp_AgregarServicios_has_Empleados(4, 5, 2, '2023-10-09', '10:30', 'San Lucas');
call sp_AgregarServicios_has_Empleados(5, 3, 5, '2023-11-21', '9:05', 'Bethania');
-- ------------- Editar Servicios_has_Empleados ----------------------------------
Delimiter //
	Create procedure sp_EditarServicios_has_Empleados (in Servi_cod int, in fecha date,  in hora time , in lugar varchar (150))
    Begin
		update Servicios_has_Empleados SE set
			SE.fechaEvento = fecha, 
            SE.horaEvento =  hora, 
            SE.lugarEvento =  lugar
            Where SE.Servicios_codigoServicio = Servi_cod;
    End //
Delimiter ;
-- ------------- Listar Servicios_has_Empleados ----------------------------------
Delimiter //
	Create procedure sp_ListarServicios_has_Empleados ()
    Begin
		Select
			SE.Servicios_codigoServicio, 
            SE.codigoServicio, 
            SE.codigoEmpleado, 
            SE.fechaEvento, 
            SE.horaEvento, 
            SE.lugarEvento
            from Servicios_has_Empleados SE;
	End //
Delimiter ;
call sp_ListarServicios_has_Empleados();
-- ------------- Buscar Servicios_has_Empleados ----------------------------------
Delimiter //
	create procedure sp_BuscarServicios_has_Empleados(in Servi_cod int)
    Begin
		Select
			SE.Servicios_codigoServicio, 
			SE.codigoServicio, 
			SE.codigoEmpleado, 
			SE.fechaEvento, 
			SE.horaEvento, 
			SE.lugarEvento
			from Servicios_has_Empleados SE
            where Servicios_codigoServicio = Servi_cod;
	End //
	Delimiter ;
-- ------------- Eliminar Servicios_has_Empleados ----------------------------------
Delimiter //
	Create procedure sp_EliminarServicios_has_Empleados (in Servi_cod int)
    Begin 
		Delete from Servicios_has_Empleados 
			where Servicios_codigoServicio = Servi_cod;
    End //
Delimiter ;

-- -----------------------------------Reporte General ---------------------------------------------------
Delimiter //
	create procedure sp_ReporteGeneral (in cod int)
		Begin
			Select
				E.nombreEmpresa, E.direccion, PR.cantidadPresupuesto, S.tipoServicio,EM.numeroEmpleado,EM.apellidosEmpleado, EM.nombresEmpleado,TE.descripcion, P.nombrePlato,
					P.cantidadPlato, P.precioPlato, TP.descripcionTipo, PD.nombreProducto, PD.cantidad
						from Empresas E inner join Presupuesto PR on E.codigoEmpresa =  PR.codigoEmpresa inner join Servicios S on 
							E.codigoEmpresa = S.codigoEmpresa inner join Servicios_has_Empleados SE on S.codigoServicio = SE.codigoServicio 
								inner join Empleados EM on SE.codigoEmpleado = EM.codigoEmpleado inner join TipoEmpleado TE on EM.codigoTipoEmpleado = 
									TE.codigoTipoEmpleado inner join Servicios_has_Platos SP on S.codigoServicio = SP.codigoServicio inner join Platos P
										on SP.codigoPlato = P.codigoPlato inner join TipoPlato TP on TP.codigoTipoPlato = P.codigoTipoPlato inner join
											Productos_has_Platos PP on P.codigoPlato = PP.codigoPlato inner join Productos PD on PP.codigoProducto = 
												PD.codigoProducto 
													where E.codigoEmpresa =  cod;
		End//
Delimiter ;
call sp_ReporteGeneral(1);
-- ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'admin';