DROP DATABASE IF EXISTS IN5BV_KINAL_MALL_MARLON_HERNANDEZ2020301;
CREATE DATABASE IN5BV_KINAL_MALL_MARLON_HERNANDEZ2020301;


USE IN5BV_KINAL_MALL_MARLON_HERNANDEZ2020301;

DROP TABLE IF EXISTS Departamentos;
CREATE TABLE IF NOT EXISTS Departamentos(
	id INT NOT NULL AUTO_INCREMENT,
	nombre VARCHAR(45) NOT NULL,
	PRIMARY KEY PK_Departamentos (id)
);
DROP TABLE IF EXISTS Cargos;
CREATE TABLE IF NOT EXISTS Cargos(
	id INT NOT NULL AUTO_INCREMENT,
	nombre VARCHAR(45) NOT NULL,
	PRIMARY KEY PK_Cargos (id)
);
DROP TABLE IF EXISTS Horarios;
CREATE TABLE IF NOT EXISTS Horarios(
	id INT NOT NULL AUTO_INCREMENT,
	horarioEntrada TIME NOT NULL,
	horarioSalida TIME NOT NULL,
	lunes BOOLEAN,
	martes BOOLEAN,
	miercoles BOOLEAN,
	jueves BOOLEAN,
	viernes BOOLEAN,
	PRIMARY KEY PK_Horarios (id)
);
DROP TABLE IF EXISTS Administracion;
CREATE TABLE IF NOT EXISTS Administracion(
	id INT NOT NULL AUTO_INCREMENT,
    direccion VARCHAR(100) NOT NULL,
    telefono VARCHAR(8),
    PRIMARY KEY PK_Administracion (id)
);
DROP TABLE IF EXISTS Empleados;
CREATE TABLE IF NOT EXISTS Empleados(
	id INT NOT NULL AUTO_INCREMENT,
	nombre VARCHAR(45) NOT NULL,
	apellido VARCHAR(45) NOT NULL,
	email VARCHAR(45) NOT NULL,
	telefono VARCHAR(8) NOT NULL,
	fechaContratacion DATE NOT NULL,
	sueldo DECIMAL(11,2) NOT NULL,
    idDepartamento INT,
    idCargo INT,
    idHorario INT,
    idAdministracion INT,
    PRIMARY KEY PK_Empleados (id),
    CONSTRAINT FK_Empleados_Departamentos
		FOREIGN KEY (idDepartamento) 
        REFERENCES Departamentos (id),      
    CONSTRAINT FK_Empleados_Cargos
		FOREIGN KEY (idCargo) 
        REFERENCES Cargos (id),      
    CONSTRAINT FK_Empleados_Horarios
		FOREIGN KEY (idHorario) 
        REFERENCES Horarios (id),     
    CONSTRAINT FK_Empleados_Administracion
		FOREIGN KEY (idAdministracion) 
        REFERENCES Administracion (id)    
);
DROP TABLE IF EXISTS TipoCliente;
CREATE TABLE IF NOT EXISTS TipoCliente (
	id INT NOT NULL AUTO_INCREMENT,
	descripcion VARCHAR(45) NOT NULL,
	PRIMARY KEY PK_TipoCliente (id)
);
DROP TABLE IF EXISTS Locales;
CREATE TABLE IF NOT EXISTS Locales (
	id INT NOT NULL AUTO_INCREMENT,
	saldoFavor DECIMAL(11,2) DEFAULT 0.0,
	saldoContra DECIMAL(11,2) DEFAULT 0.0,
	mesesPendientes INT,
	disponibilidad BOOLEAN NOT NULL,
	valorLocal DECIMAL(11,2) NOT NULL,
	valorAdministracion DECIMAL(11,2) NOT NULL,
	PRIMARY KEY PK_Locales (id)
);
DROP TABLE IF EXISTS Clientes;
CREATE TABLE IF NOT EXISTS Clientes (
id INT NOT NULL AUTO_INCREMENT,
nombres VARCHAR(45) NOT NULL,
apellidos VARCHAR(45) NOT NULL,
telefono VARCHAR(8) NOT NULL,
direccion VARCHAR(60) NOT NULL,
email VARCHAR(45) NOT NULL,
idLocal INT,
idTipoCliente INT,
idAdministracion INT,
PRIMARY KEY PK_Clientes (id),
	CONSTRAINT FK_Clientes_Locales
		FOREIGN KEY (idLocal) 
        REFERENCES Locales (id), 
    CONSTRAINT FK_Clientes_TipoCliente
		FOREIGN KEY (idTipoCliente) 
        REFERENCES TipoCliente (id),     
	  CONSTRAINT FK_Clientes_Administracion
		FOREIGN KEY (idAdministracion) 
        REFERENCES Administracion (id)    
);
DROP TABLE IF EXISTS Proveedores;
CREATE TABLE IF NOT EXISTS Proveedores (
	id INT NOT NULL AUTO_INCREMENT,
	nit VARCHAR(45) NOT NULL,
	servicioPrestado VARCHAR(45) NOT NULL,
	telefono VARCHAR(45) NOT NULL,
	direccion VARCHAR(60) NOT NULL,
	saldoFavor DECIMAL(11,2) NOT NULL,
	saldoContra DECIMAL(11,2) NOT NULL,
	idAdministracion INT,
	PRIMARY KEY PK_Proveedores (id),
		CONSTRAINT FK_Proveedores_Administracion
			FOREIGN KEY (idAdministracion) 
			REFERENCES Administracion (id)

);
DROP TABLE IF EXISTS CuentasPorCobrar;
CREATE TABLE IF NOT EXISTS CuentasPorCobrar (
	id INT NOT NULL AUTO_INCREMENT,
	numeroFactura VARCHAR(45) NOT NULL,
	anio YEAR(4) NOT NULL,
	mes INT(2) NOT NULL,
	valorNetoPago DECIMAL(11,2) NOT NULL,
	estadoPago VARCHAR(45) NOT NULL,
	idCliente INT,
	idLocal INT,
	idAdministracion INT,
	PRIMARY KEY PK_CuentasPorCobrar (id),
		CONSTRAINT FK_CuentasPorCobrar_Cliente
			FOREIGN KEY (idCliente) 
			REFERENCES Clientes (id),
		CONSTRAINT FK_CuentasPorCobrar_Local
			FOREIGN KEY (idLocal) 
			REFERENCES Locales (id),    
		CONSTRAINT FK_CuentasPorCobrar_Administracion
			FOREIGN KEY (idAdministracion) 
			REFERENCES Administracion (id)
);
DROP TABLE IF EXISTS CuentasPorPagar;
CREATE TABLE IF NOT EXISTS CuentasPorPagar(
	id INT NOT NULL AUTO_INCREMENT,
	numeroFactura VARCHAR(45) NOT NULL,
	fechaLimitePago DATE NOT NULL,
	estadoPago VARCHAR(45) NOT NULL,
	valorNetoPago DECIMAL(11,2) NOT NULL,
	idAdministracion INT,
	idProveedores INT,
	PRIMARY KEY PK_CentasPorPagar (id),
		CONSTRAINT FK_CentasPorPagar_Administracion
		  FOREIGN KEY (idAdministracion)
		  REFERENCES Administracion (id),
		CONSTRAINT FK_CentasPorPagar_Proveedores
		  FOREIGN KEY (idProveedores)
		  REFERENCES Proveedores (id)

);