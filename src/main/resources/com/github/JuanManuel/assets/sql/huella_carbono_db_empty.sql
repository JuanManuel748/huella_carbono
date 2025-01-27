DROP DATABASE IF EXISTS `huella_carbono_db`;
CREATE DATABASE `huella_carbono_db`;
USE `huella_carbono_db`;

CREATE TABLE Usuario (
    id_usuario INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    contraseña TEXT NOT NULL,
    fecha_registro DATE NOT NULL
);

CREATE TABLE Categoria (
    id_categoria INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    factor_emision DECIMAL(10, 3) NOT NULL,
    unidad VARCHAR(5) NOT NULL
);

CREATE TABLE Actividad (
    id_actividad INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    id_categoria INT,
    FOREIGN KEY (id_categoria) REFERENCES Categoria(id_categoria)
);

CREATE TABLE Huella (
    id_registro INT PRIMARY KEY AUTO_INCREMENT,
    id_usuario INT,
    id_actividad INT,
    valor DECIMAL(10, 3) NOT NULL,
    unidad VARCHAR(5) NOT NULL,
    fecha DATE NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario),
    FOREIGN KEY (id_actividad) REFERENCES Actividad(id_actividad)
);

CREATE TABLE Habito (
    id_usuario INT,
    id_actividad INT,
    frecuencia INT NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    ultima_fecha DATE NOT NULL,
    PRIMARY KEY (id_usuario, id_actividad),
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario),
    FOREIGN KEY (id_actividad) REFERENCES Actividad(id_actividad)
);

CREATE TABLE Recomendacion (
    id_recomendacion INT PRIMARY KEY AUTO_INCREMENT,
    id_categoria INT,
    descripcion TEXT NOT NULL,
    impacto_estimado DECIMAL(10, 3) NOT NULL,
    FOREIGN KEY (id_categoria) REFERENCES Categoria(id_categoria)
);