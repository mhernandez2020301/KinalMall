USE IN5BV_KINAL_MALL_MARLON_HERNANDEZ2020301;

DROP PROCEDURE IF EXISTS sp_ListarAdministracion;
DELIMITER $$
CREATE PROCEDURE sp_ListarAdministracion()
BEGIN
	SELECT 
		Administracion.id, 
        Administracion.direccion, 
        Administracion.telefono 
	FROM Administracion;
END $$
DELIMITER ;


DROP PROCEDURE IF EXISTS sp_BuscarAdministracion;
DELIMITER $$
CREATE PROCEDURE sp_BuscarAdministracion(IN _id INT)
BEGIN
	SELECT 
		Administracion.id, 
        Administracion.direccion, 
        Administracion.telefono 
	FROM Administracion
    WHERE id = _id;
END $$
DELIMITER ;


DROP PROCEDURE IF EXISTS sp_AgregarAdministracion; 
DELIMITER $$
CREATE PROCEDURE sp_AgregarAdministracion (
	IN _direccion VARCHAR(100), 
	IN _telefono VARCHAR(8)
)
BEGIN
	INSERT INTO Administracion (direccion, telefono) 
    VALUES (_direccion, _telefono);
END $$
DELIMITER ;


DROP PROCEDURE IF EXISTS sp_EditarAdministracion;
DELIMITER $$
CREATE PROCEDURE sp_EditarAdministracion (
	IN _id INT,
	IN _direccion VARCHAR(100),
    IN _telefono VARCHAR(8)
)
BEGIN
	UPDATE Administracion 
    SET 
		direccion = _direccion, 
		telefono = _telefono 
    WHERE id = _id;
END $$
DELIMITER ;


DROP PROCEDURE IF EXISTS sp_EliminarAdministracion;
DELIMITER $$
CREATE PROCEDURE sp_EliminarAdministracion (IN _id INT)
BEGIN
	DELETE FROM Administracion WHERE id = _id;
END $$
DELIMITER ;


-- -----------------------------------------------------
-- PROCEDURE Locales
-- -----------------------------------------------------

DROP PROCEDURE IF EXISTS sp_ListarLocales;
DELIMITER $$
CREATE PROCEDURE sp_ListarLocales()
BEGIN
	SELECT
		Locales.id,
		Locales.saldoFavor,
		Locales.saldoContra,
		Locales.mesesPendientes,
		Locales.disponibilidad,
		Locales.valorLocal,
		Locales.valorAdministracion
	FROM Locales;
END$$
DELIMITER ;


DROP PROCEDURE IF EXISTS sp_BuscarLocales;
DELIMITER $$
CREATE PROCEDURE sp_BuscarLocales(IN _id INT)
BEGIN
	SELECT
		Locales.id,
		Locales.saldoFavor,
		Locales.saldoContra,
		Locales.mesesPendientes,
		Locales.disponibilidad,
		Locales.valorLocal,
		Locales.valorAdministracion
	FROM Locales
	WHERE id = _id;
END$$
DELIMITER ;


DROP PROCEDURE IF EXISTS sp_AgregarLocales;
DELIMITER $$
CREATE PROCEDURE sp_AgregarLocales(
	IN _saldoFavor DECIMAL(11,2),
	IN _saldoContra DECIMAL(11,2),
	IN _mesesPendientes INT,
	IN _disponibilidad BOOLEAN,
	IN _valorLocal DECIMAL(11,2),
	IN _valorAdministracion DECIMAL(11,2))
BEGIN
	INSERT INTO Locales(saldoFavor, saldoContra, mesesPendientes, disponibilidad, valorLocal, valorAdministracion )
	VALUES (_saldoFavor, _saldoContra, _mesesPendientes, _disponibilidad, _valorLocal, _valorAdministracion);
END$$
DELIMITER ;


DROP PROCEDURE IF EXISTS sp_EditarLocales;
DELIMITER $$
CREATE PROCEDURE sp_EditarLocales(
	IN _id INT,
	IN _saldoFavor DECIMAL(11,2),
	IN _saldoContra DECIMAL(11,2),
	IN _mesesPendientes INT,
	IN _disponibilidad BOOLEAN,
	IN _valorLocal DECIMAL(11,2),
	IN _valorAdministracion DECIMAL(11,2))
BEGIN
	UPDATE Locales
	SET
		saldoFavor = _saldoFavor,
		saldoContra = _saldoContra,
		mesesPendientes = _mesesPendientes,
		disponibilidad = _disponibilidad,
		valorLocal = _valorLocal,
		valorAdministracion = _valorAdministracion
	WHERE id = _id;
END$$
DELIMITER ;


DROP PROCEDURE IF EXISTS sp_EliminarLocales;
DELIMITER $$
CREATE PROCEDURE sp_EliminarLocales(IN _id INT)
BEGIN
	DELETE FROM Locales WHERE id = _id;
END$$
DELIMITER ;


-- -----------------------------------------------------
-- PROCEDURE TipoCliente
-- -----------------------------------------------------

DROP PROCEDURE IF EXISTS sp_ListarTipoCliente;
DELIMITER $$
CREATE PROCEDURE sp_ListarTipoCliente()
BEGIN
	SELECT
		TipoCliente.id,
		TipoCliente.descripcion
	FROM TipoCliente;
END$$
DELIMITER ;


DROP PROCEDURE IF EXISTS sp_BuscarTipoCliente;
DELIMITER $$
CREATE PROCEDURE sp_BuscarTipoCliente(IN _id INT)
BEGIN
	SELECT
		TipoCliente.id,
		TipoCliente.descripcion
	FROM TipoCliente
	WHERE id = _id;
END$$
DELIMITER ;


DROP PROCEDURE IF EXISTS sp_AgregarTipoCliente;
DELIMITER $$
CREATE PROCEDURE sp_AgregarTipoCliente(
	IN _descripcion VARCHAR(45)
)
BEGIN
	INSERT INTO TipoCliente(descripcion)
	VALUES (_descripcion);
END$$
DELIMITER ;


DROP PROCEDURE IF EXISTS sp_EditarTipoCliente;
DELIMITER $$
CREATE PROCEDURE sp_EditarTipoCliente(
	IN _id INT,
	IN _descripcion VARCHAR(45)
)
BEGIN
	UPDATE TipoCliente
	SET
		descripcion = _descripcion
	WHERE id = _id;
END$$
DELIMITER ;


DROP PROCEDURE IF EXISTS sp_EliminarTipoCliente;
DELIMITER $$
CREATE PROCEDURE sp_EliminarTipoCliente(IN _id INT)
BEGIN
	DELETE FROM TipoCliente WHERE id = _id;
END$$
DELIMITER ;


-- -----------------------------------------------------
-- PROCEDURE Cliente
-- -----------------------------------------------------

DROP PROCEDURE IF EXISTS sp_ListarClientes;
DELIMITER $$
CREATE PROCEDURE sp_ListarClientes()
BEGIN
	SELECT 
		Clientes.id,
        Clientes.nombres,
        Clientes.apellidos,
        Clientes.telefono,
        Clientes.email,
		Clientes.direccion,
        Clientes.idTipoCliente
	FROM Clientes;
END $$
DELIMITER ;


DROP PROCEDURE IF EXISTS sp_BuscarClientes;
DELIMITER $$
CREATE PROCEDURE sp_BuscarClientes(IN _id INT)
BEGIN
	SELECT 
		Clientes.id,
        Clientes.nombres,
        Clientes.apellidos,
        Clientes.telefono,
        Clientes.email,
                Clientes.direccion,
        Clientes.idTipoCliente
	FROM Clientes
	WHERE id = _id;
END$$
DELIMITER ;


DROP PROCEDURE IF EXISTS sp_AgregarClientes;
DELIMITER $$
CREATE PROCEDURE sp_AgregarClientes(
    IN _nombres VARCHAR(45),
    IN _apellidos VARCHAR(45),
    IN _telefono VARCHAR(8),
    IN _email VARCHAR(45),
	IN _direccion VARCHAR(100),
    IN _idTipoCliente INT
)
BEGIN
	INSERT INTO Clientes(nombres, apellidos, telefono, direccion, email, idTipoCliente)
	VALUES (_nombres, _apellidos, _telefono, _email, _direccion, _idTipoCliente);
END$$
DELIMITER ;


DROP PROCEDURE IF EXISTS sp_EditarClientes;
DELIMITER $$
CREATE PROCEDURE sp_EditarClientes(
	IN _id INT,
    IN _nombres VARCHAR(45),
    IN _apellidos VARCHAR(45),
    IN _telefono VARCHAR(8),
    IN _email VARCHAR(45),
	IN _direccion VARCHAR(100),
    IN _idTipoCliente INT
)
BEGIN
	UPDATE Clientes
	SET
		nombres = _nombres, 
        apellidos = _apellidos, 
        telefono = _telefono,
        email = _email, 
        direccion = _direccion,
        idTipoCliente = _idTipoCliente
	WHERE id = _id;
END$$
DELIMITER ;


DROP PROCEDURE IF EXISTS sp_EliminarClientes;
DELIMITER $$
CREATE PROCEDURE sp_EliminarClientes(IN _id INT)
BEGIN
	DELETE FROM Clientes WHERE id = _id;
END$$
DELIMITER ;

-- -----------------------------------------------------
-- PROCEDURE Departamentos
-- -----------------------------------------------------

DROP PROCEDURE IF EXISTS sp_ListarDepartamentos;
DELIMITER $$
CREATE PROCEDURE sp_ListarDepartamentos()
BEGIN
	SELECT 
		Departamentos.id,
        Departamentos.nombre
	FROM Departamentos;
END $$
DELIMITER ;


DROP PROCEDURE IF EXISTS sp_BuscarDepartamentos;
DELIMITER $$
CREATE PROCEDURE sp_BuscarDepartamentos(IN _id INT)
BEGIN
	SELECT 
		Departamentos.id,
        Departamentos.nombre
	FROM Departamentos
	WHERE id = _id;
END$$
DELIMITER ;


DROP PROCEDURE IF EXISTS sp_AgregarDepartamentos;
DELIMITER $$
CREATE PROCEDURE sp_AgregarDepartamentos(
    IN _nombre VARCHAR(45)
)
BEGIN
	INSERT INTO Departamentos(nombre)
	VALUES (_nombre);
END$$
DELIMITER ;


DROP PROCEDURE IF EXISTS sp_EditarDepartamentos;
DELIMITER $$
CREATE PROCEDURE sp_EditarDepartamentos(
	IN _id INT,
    IN _nombre VARCHAR(45)
)
BEGIN
	UPDATE Departamentos
	SET
		nombre = _nombre
	WHERE id = _id;
END$$
DELIMITER ;


DROP PROCEDURE IF EXISTS sp_EliminarDepartamentos;
DELIMITER $$
CREATE PROCEDURE sp_EliminarDepartamentos(IN _id INT)
BEGIN
	DELETE FROM Departamentos WHERE id = _id;
END$$
DELIMITER ;

-- -----------------------------------------------------
-- PROCEDURE Cuentas por Cobrar
-- -----------------------------------------------------

DROP PROCEDURE IF EXISTS sp_ListarCuentasPorCobrar;
DELIMITER $$
CREATE PROCEDURE sp_ListarCuentasPorCobrar()
BEGIN
	SELECT 
		CuentasPorCobrar.id,
        CuentasPorCobrar.numeroFactura,
        CuentasPorCobrar.anio,
        CuentasPorCobrar.mes,
        CuentasPorCobrar.valorNetoPago,
		CuentasPorCobrar.estadoPago,
        CuentasPorCobrar.idAdministracion,
        CuentasPorCobrar.idCliente,
        CuentasPorCobrar.idLocal
	FROM CuentasPorCobrar;
END $$
DELIMITER ;


DROP PROCEDURE IF EXISTS sp_BuscarCuentasPorCobrar;
DELIMITER $$
CREATE PROCEDURE sp_BuscarCuentasPorCobrar(IN _id INT)
BEGIN
	SELECT 
		CuentasPorCobrar.id,
        CuentasPorCobrar.numeroFactura,
        CuentasPorCobrar.anio,
        CuentasPorCobrar.mes,
        CuentasPorCobrar.valorNetoPago,
		CuentasPorCobrar.estadoPago,
        CuentasPorCobrar.idAdministracion,
        CuentasPorCobrar.idCliente,
        CuentasPorCobrar.idLocal
	FROM CuentasPorCobrar
	WHERE id = _id;
END$$
DELIMITER ;


DROP PROCEDURE IF EXISTS sp_AgregarCuentasPorCobrar;
DELIMITER $$
CREATE PROCEDURE sp_AgregarCuentasPorCobrar(
    IN _numeroFactura VARCHAR(45),
    IN _anio YEAR,
    IN _mes INT,
    IN _valorNetoPago DECIMAL(11,2),
	IN _estadoPago VARCHAR(45),
    IN _idAdministracion INT,
	IN _idCliente INT,
	IN _idLocal INT
   
)
BEGIN
	INSERT INTO CuentasPorCobrar(numeroFactura, anio, mes, valorNetoPago, estadoPago, idAdministracion, idCliente, idLocal)
	VALUES (_numeroFactura, _anio, _mes, _valorNetoPago, _estadoPago, _idAdministracion, _idCliente, _idLocal);
END$$
DELIMITER ;


DROP PROCEDURE IF EXISTS sp_EditarCuentasPorCobrar;
DELIMITER $$
CREATE PROCEDURE sp_EditarCuentasPorCobrar(

	In _id INT,
    IN _numeroFactura VARCHAR(45),
    IN _anio YEAR,
    IN _mes INT,
    IN _valorNetoPago DECIMAL(11,2),
	IN _estadoPago VARCHAR(45),
    IN _idAdministracion INT,
    IN _idCliente INT,
    IN _idLocal INT
    
)
BEGIN
	UPDATE CuentasPorCobrar
	SET
	numeroFactura = _numeroFactura,
    anio = _anio,
    mes = _mes,
    valorNetoPago = _valorNetoPago,
    estadoPago = _estadoPago,
    idAdministracion = _idAdministracion,
    idCliente = _idCliente,
    idLocal = _idLocal
    
	WHERE id = _id;
END$$
DELIMITER ;


DROP PROCEDURE IF EXISTS sp_EliminarCuentasPorCobrar;
DELIMITER $$
CREATE PROCEDURE sp_EliminarCuentasPorCobrar(IN _id INT)
BEGIN
	DELETE FROM CuentasPorCobrar WHERE id = _id;
END$$
DELIMITER ;

-- -----------------------------------------------------
-- PROCEDURE Proveedores
-- -----------------------------------------------------

DROP PROCEDURE IF EXISTS sp_ListarProveedores;
DELIMITER $$
CREATE PROCEDURE sp_ListarProveedores()
BEGIN
	SELECT 
		Proveedores.id,
        Proveedores.nit,
        Proveedores.servicioPrestado,
        Proveedores.telefono,
        Proveedores.direccion,
		Proveedores.saldoFavor,
        Proveedores.saldoContra
	FROM Proveedores;
END $$
DELIMITER ;


DROP PROCEDURE IF EXISTS sp_BuscarProveedores;
DELIMITER $$
CREATE PROCEDURE sp_BuscarProveedores(IN _id INT)
BEGIN
	SELECT 
		Proveedores.id,
        Proveedores.nit,
        Proveedores.servicioPrestado,
        Proveedores.telefono,
        Proveedores.direccion,
		Proveedores.saldoFavor,
        Proveedores.saldoContra
	FROM Proveedores
	WHERE id = _id;
END$$
DELIMITER ;


DROP PROCEDURE IF EXISTS sp_AgregarProveedores;
DELIMITER $$
CREATE PROCEDURE sp_AgregarProveedores(
    IN _nit VARCHAR(45),
    IN _servicioPrestado VARCHAR(45),
    IN _telefono VARCHAR(45),
    IN _direccion VARCHAR(45),
	IN _saldoFavor DECIMAL(11,2),
    IN _saldoContra DECIMAL(11,2)
)
BEGIN
	INSERT INTO Proveedores(nit, servicioPrestado, telefono, direccion, saldoFavor, saldoContra)
	VALUES (_nit, _servicioPrestado, _telefono, _direccion, _saldoFavor, _saldoContra);
END$$
DELIMITER ;


DROP PROCEDURE IF EXISTS sp_EditarProveedores;
DELIMITER $$
CREATE PROCEDURE sp_EditarProveedores(

	In _id INT,
    IN _nit VARCHAR(45),
    IN _servicioPrestado VARCHAR(45),
    IN _telefono VARCHAR(45),
    IN _direccion VARCHAR(45),
	IN _saldoFavor DECIMAL(11,2),
    IN _saldoContra DECIMAL(11,2)
)
BEGIN
	UPDATE Proveedores
	SET
	nit = _nit,
    servicioPrestado = _servicioPrestado,
    telefono = _telefono,
    direccion = _direccion,
    saldoFavor = _saldoFavor,
    saldoContra = _saldoContra
	WHERE id = _id;
END$$
DELIMITER ;


DROP PROCEDURE IF EXISTS sp_EliminarProveedores;
DELIMITER $$
CREATE PROCEDURE sp_EliminarProveedores(IN _id INT)
BEGIN
	DELETE FROM Proveedores WHERE id = _id;
END$$
DELIMITER ;

-- -----------------------------------------------------
-- PROCEDURE Cuentas por Pagar
-- -----------------------------------------------------

DROP PROCEDURE IF EXISTS sp_ListarCuentasPorPagar;
DELIMITER $$
CREATE PROCEDURE sp_ListarCuentasPorPagar()
BEGIN
	SELECT 
    
		CuentasPorPagar.id,
        CuentasPorPagar.numeroFactura,
        CuentasPorPagar.fechaLimitePago,
        CuentasPorPagar.estadoPago,
        CuentasPorPagar.valorNetoPago,
		CuentasPorPagar.idAdministracion,
        CuentasPorPagar._idProveedores
        
	FROM CuentasPorPagar;
END $$
DELIMITER ;


DROP PROCEDURE IF EXISTS sp_BuscarCuentasPorPagar;
DELIMITER $$
CREATE PROCEDURE sp_BuscarCuentasPorPagar(IN _id INT)
BEGIN
	SELECT 
    
		CuentasPorPagar.id,
        CuentasPorPagar.numeroFactura,
        CuentasPorPagar.fechaLimitePago,
        CuentasPorPagar.estadoPago,
        CuentasPorPagar.valorNetoPago,
		CuentasPorPagar.idAdministracion,
        CuentasPorPagar.idProveedores
        
	FROM CuentasPorPagar
	WHERE id = _id;
END$$
DELIMITER ;


DROP PROCEDURE IF EXISTS sp_AgregarCuentasPorPagar;
DELIMITER $$
CREATE PROCEDURE sp_AgregarCuentasPorPagar(
    IN _numeroFactura VARCHAR(45),
    IN _fechaLimitePago DATE,
    IN _estadoPago VARCHAR(45),
    IN _valorNetoPago DECIMAL(11,2),
	IN _idAdministracion INT,
    IN _idProveedores INT
)
BEGIN
	INSERT INTO CuentasPorPagar(numeroFactura, fechaLimitePago, estadoPago, valorNetoPago, idAdministracion, idProveedores)
	VALUES (_numeroFactura, _fechaLimitePago, _estadoPago, _valorNetoPago, _idAdministracion, _idProveedores);
END$$
DELIMITER ;


DROP PROCEDURE IF EXISTS sp_EditarCuentasPorPagar;
DELIMITER $$
CREATE PROCEDURE sp_EditarCuentasPorPagar(

	In _id INT,
    IN _numeroFactura VARCHAR(45),
    IN _fechaLimitePago DATE,
    IN _estadoPago VARCHAR(45),
    IN _valorNetoPago DECIMAL(11,2),
	IN _idAdministracion INT,
    IN _idProveedores INT
)
BEGIN
	UPDATE CuentasPorPagar
	SET
	numeroFactura = _numeroFactura,
    fechaLimitePago = _fechaLimitePago,
    estadoPago = _estadoPago,
    valorNetoPago = _valorNetoPago,
    idAdministracion = _idAdministracion,
    idProveedores = _idProveedores
	WHERE id = _id;
END$$
DELIMITER ;


DROP PROCEDURE IF EXISTS sp_EliminarCuentasPorPagar;
DELIMITER $$
CREATE PROCEDURE sp_EliminarCuentasPorPagar(IN _id INT)
BEGIN
	DELETE FROM CuentasPorPagar WHERE id = _id;
END$$
DELIMITER ;

-- -----------------------------------------------------
-- PROCEDURE Horarios
-- -----------------------------------------------------

DROP PROCEDURE IF EXISTS sp_ListarHorarios;
DELIMITER $$
CREATE PROCEDURE sp_ListarHorarios()
BEGIN
	SELECT 
    
		Horarios.id,
        Horarios.horarioEntrada,
        Horarios.horarioSalida,
        Horarios.lunes,
        Horarios.martes,
		Horarios.miercoles,
        Horarios.jueves,
        Horarios.viernes
        
	FROM Horarios;
END $$
DELIMITER ;


DROP PROCEDURE IF EXISTS sp_BuscarHorarios;
DELIMITER $$
CREATE PROCEDURE sp_BuscarHorarios(IN _id INT)
BEGIN
	SELECT 
    
		Horarios.id,
        Horarios.horarioEntrada,
        Horarios.horarioSalida,
        Horarios.lunes,
        Horarios.martes,
		Horarios.miercoles,
        Horarios.jueves,
        Horarios.viernes
        
	FROM Horarios
	WHERE id = _id;
END$$
DELIMITER ;


DROP PROCEDURE IF EXISTS sp_AgregarHorarios;
DELIMITER $$
CREATE PROCEDURE sp_AgregarHorarios(
    IN _horarioEntrada TIME,
    IN _horarioSalida TIME,
    IN _lunes BOOLEAN,
    IN _martes BOOLEAN,
	IN _miercoles BOOLEAN,
    IN _jueves BOOLEAN,
    IN _viernes BOOLEAN
)
BEGIN
	INSERT INTO Horarios(horarioEntrada, horarioSalida, lunes, martes, miercoles, jueves, viernes)
	VALUES (_horarioEntrada, _horarioSalida, _lunes, _martes, _miercoles, _jueves, _viernes);
END$$
DELIMITER ;


DROP PROCEDURE IF EXISTS sp_EditarHorarios;
DELIMITER $$
CREATE PROCEDURE sp_EditarHorarios(

	In _id INT,
    IN _horarioEntrada TIME,
    IN _horarioSalida TIME,
    IN _lunes BOOLEAN,
    IN _martes BOOLEAN,
	IN _miercoles BOOLEAN,
    IN _jueves BOOLEAN,
    IN _viernes BOOLEAN
)
BEGIN
	UPDATE Horarios
	SET
	horarioEntrada = _horarioEntrada,
    horarioSalida = _horarioSalida,
    lunes = _lunes,
    martes = _martes,
    miercoles = _miercoles,
    jueves = _jueves,
    viernes = _viernes
	WHERE id = _id;
END$$
DELIMITER ;


DROP PROCEDURE IF EXISTS sp_EliminarHorarios;
DELIMITER $$
CREATE PROCEDURE sp_EliminarHorarios(IN _id INT)
BEGIN
	DELETE FROM Horarios WHERE id = _id;
END$$
DELIMITER ;

-- -----------------------------------------------------
-- PROCEDURE Cargos
-- -----------------------------------------------------

DROP PROCEDURE IF EXISTS sp_ListarCargos;
DELIMITER $$
CREATE PROCEDURE sp_ListarCargos()
BEGIN
	SELECT 
		Cargos.id,
        Cargos.nombre
	FROM Cargos;
END $$
DELIMITER ;


DROP PROCEDURE IF EXISTS sp_BuscarCargos;
DELIMITER $$
CREATE PROCEDURE sp_BuscarCargos(IN _id INT)
BEGIN
	SELECT 
		Cargos.id,
        Cargos.nombre
	FROM Cargos
	WHERE id = _id;
END$$
DELIMITER ;


DROP PROCEDURE IF EXISTS sp_AgregarCargos;
DELIMITER $$
CREATE PROCEDURE sp_AgregarCargos(
    IN _nombre VARCHAR(45)
)
BEGIN
	INSERT INTO Cargos(nombre)
	VALUES (_nombre);
END$$
DELIMITER ;


DROP PROCEDURE IF EXISTS sp_EditarCargos;
DELIMITER $$
CREATE PROCEDURE sp_EditarCargos(
	IN _id INT,
    IN _nombre VARCHAR(45)
)
BEGIN
	UPDATE Cargos
	SET
		nombre = _nombre
	WHERE id = _id;
END$$
DELIMITER ;


DROP PROCEDURE IF EXISTS sp_EliminarCargos;
DELIMITER $$
CREATE PROCEDURE sp_EliminarCargos(IN _id INT)
BEGIN
	DELETE FROM Cargos WHERE id = _id;
END$$
DELIMITER ;

-- -----------------------------------------------------
-- PROCEDURE Empleados
-- -----------------------------------------------------

DROP PROCEDURE IF EXISTS sp_ListarEmpleados;
DELIMITER $$
CREATE PROCEDURE sp_ListarEmpleados()
BEGIN
	SELECT 
		Empleados.id,
        Empleados.nombre,
        Empleados.apellido,
        Empleados.email,
        Empleados.telefono,
        Empleados.fechaContratacion,
        Empleados.sueldo,
		Empleados.idDepartamento,
		Empleados.idCargo,
		Empleados.idHorario,
		Empleados.idAdministracion
	FROM Empleados;
END $$
DELIMITER ;


DROP PROCEDURE IF EXISTS sp_BuscarEmpleados;
DELIMITER $$
CREATE PROCEDURE sp_BuscarEmpleados(IN _id INT)
BEGIN
	SELECT 
		Empleados.id,
        Empleados.nombre,
        Empleados.apellido,
        Empleados.email,
        Empleados.telefono,
        Empleados.fechaContratacion,
        Empleados.sueldo,
		Empleados.idDepartamento,
		Empleados.idCargo,
		Empleados.idHorario,
		Empleados.idAdministracion
	FROM Empleados
	WHERE id = _id;
END$$
DELIMITER ;


DROP PROCEDURE IF EXISTS sp_AgregarEmpleados;
DELIMITER $$
CREATE PROCEDURE sp_AgregarEmpleados(
    IN _nombres VARCHAR(45),
    IN _apellidos VARCHAR(45),
    IN _email VARCHAR(45),
    IN _telefono VARCHAR(8),
    IN _fechaContratacion DATE,
    IN _sueldo DECIMAL(11,2),
    IN _idDepartamento INT,
    IN _idCargo INT,
    IN _idHorario INT,
    IN _idAdministracion INT
)
BEGIN
	INSERT INTO Empleados(nombre, apellido, email, telefono, fechaContratacion, sueldo, idDepartamento, idCargo, idHorario, idAdministracion)
	VALUES (_nombres, _apellidos, _email, _telefono, _fechaContratacion, _sueldo, _idDepartamento, _idCargo, _idHorario, _idAdministracion);
END$$
DELIMITER ;


DROP PROCEDURE IF EXISTS sp_EditarEmpleados;
DELIMITER $$
CREATE PROCEDURE sp_EditarEmpleados(
	IN _id INT,
	IN _nombres VARCHAR(45),
    IN _apellidos VARCHAR(45),
    IN _email VARCHAR(45),
    IN _telefono VARCHAR(8),
    IN _fechaContratacion DATE,
    IN _sueldo DECIMAL(11,2),
    IN _idDepartamento INT,
    IN _idCargo INT,
    IN _idHorario INT,
    IN _idAdministracion INT
)
BEGIN
	UPDATE Empleados
	SET
		nombre = _nombres,
        apellido = _apellidos,
        email = _email,
        telefono = _telefono,
        fechaContratacion = _fechaContratacion,
        sueldo = _sueldo,
        idDepartamento = _idDepartamento,
        idCargo = _idCargo,
        idHorario = _idHorario,
        idAdministracion = _idAdministracion
	WHERE id = _id;
END$$
DELIMITER ;


DROP PROCEDURE IF EXISTS sp_EliminarEmpleados;
DELIMITER $$
CREATE PROCEDURE sp_EliminarEmpleados(IN _id INT)
BEGIN
	DELETE FROM Empleados WHERE id = _id;
END$$
DELIMITER ;

-- -----------------------------------------------------
-- Prueba
-- -----------------------------------------------------

DELIMITER $$
    CREATE PROCEDURE sp_CalcularLiquido(in id int)
        Begin 
            SELECT L.saldoFavor - L.saldoContra FROM Locales L WHERE id = id;
        End$$
DELIMITER ;


DROP PROCEDURE IF EXISTS sp_CalcularLocalesDisponibles;
DELIMITER $$
    CREATE PROCEDURE sp_CalcularLocalesDisponibles()
BEGIN
	SELECT COUNT(disponibilidad) AS cantidas
    FROM Locales
    WHERE disponibilidad = TRUE;
END$$
DELIMITER ;
-- -----------------------------------------------------
-- Datos de prueba
-- -----------------------------------------------------

CALL sp_AgregarAdministracion("Diagonal 6 12-42, Zona 10, Ciudad de Guatemala", "45623589");
CALL sp_AgregarAdministracion("12 Calle 1-25, Zona 10, Ciudad de Guatemala", "79654875");
CALL sp_AgregarAdministracion("9na calle 15-77, Zona 7, Ciudad de Guatemala", "79654875");
CALL sp_AgregarAdministracion("3ra Avenida 8-05, Bosques de San Nicolás, Zona 8, Guatemala", "79654875");

CALL sp_AgregarLocales(5000.00, 7000.00, 1, false, 20000.00, 1000.00);
CALL sp_AgregarLocales(10000.00, 10000.00, 0, false, 5000.00, 1000.00);
CALL sp_AgregarLocales(0.00, 30000.00, 6, false, 5000.00, 1000.00);

CALL sp_AgregarTipoCliente("Bronce");
CALL sp_AgregarTipoCliente("Plata");
CALL sp_AgregarTipoCliente("Oro");
CALL sp_AgregarTipoCliente("Diamante");

CALL sp_AgregarClientes("Marlon", "Hernandez", "45236598", "Zona 18", "Mhe@gmail.com", 1);
CALL sp_AgregarClientes("Andres", "Herrera", "65987465", "Zona 17", "AndresH@gmail.com", 2);
CALL sp_AgregarClientes("Maria", "Lopez", "75648934", "Zona 21", "MariaLo@gmail.com", 4);

CALL sp_AgregarDepartamentos("Elbaroacion y Gestion");
CALL sp_AgregarCuentasPorCobrar("N-30458", 2021, 12, 5000.30, "Pendiente", 1, 1, 1);
CALL sp_AgregarProveedores("123456-7", "Seguridad", "45236178", "ZOna 06, Mixco Guatemala",0.00, 0.00);
CALL sp_AgregarCuentasPorPagar("M-5558", '2021-12-01', "Pendiente", 1230.00, "1", "1");
CALL sp_AgregarHorarios('08:00:00', '18:00:00', false, false, true, false, false);
CALL sp_AgregarCargos("Gerente General");
CALL sp_AgregarEmpleados("Marlon", "Hernandez", "mh@gmail.com", "20314569", '2020-07-13', 10000.00, 1, 1, 1, 1);

CALL sp_ListarAdministracion;
CALL sp_ListarLocales;
CALL sp_ListarTipoCliente;
CALL sp_ListarClientes;
CALL sp_ListarDepartamentos;
CALL sp_CalcularLiquido;
CALL sp_ListarCuentasPorCobrar;
CALL sp_ListarProveedores;
CALL sp_ListarCuentasPorPagar;
CALL sp_ListarHorarios;
CALL sp_ListarCargos;
CALL sp_ListarEmpleados;