-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 08-02-2025 a las 10:44:16
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `huella_carbono_db`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `actividad`
--

CREATE TABLE `actividad` (
                             `id_actividad` int(11) NOT NULL,
                             `nombre` varchar(100) NOT NULL,
                             `id_categoria` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `actividad`
--

INSERT INTO `actividad` (`id_actividad`, `nombre`, `id_categoria`) VALUES
                                                                       (1, 'Conducir coche', 1),
                                                                       (2, 'Usar transporte público', 1),
                                                                       (3, 'Viajar en avión', 1),
                                                                       (4, 'Consumo eléctrico', 2),
                                                                       (5, 'Consumo de gas', 2),
                                                                       (6, 'Comer carne de res', 3),
                                                                       (7, 'Comer alimentos vegetarianos', 3),
                                                                       (8, 'Generar residuos domésticos', 4),
                                                                       (9, 'Consumo de agua potable', 5);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `categoria`
--

CREATE TABLE `categoria` (
                             `id_categoria` int(11) NOT NULL,
                             `nombre` varchar(100) NOT NULL,
                             `factor_emision` decimal(10,3) NOT NULL,
                             `unidad` varchar(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `categoria`
--

INSERT INTO `categoria` (`id_categoria`, `nombre`, `factor_emision`, `unidad`) VALUES
                                                                                   (1, 'Transporte', 0.210, 'Km'),
                                                                                   (2, 'Energía', 0.233, 'KWh'),
                                                                                   (3, 'Alimentación', 2.500, 'Kg'),
                                                                                   (4, 'Residuos', 0.410, 'Kg'),
                                                                                   (5, 'Agua', 0.350, 'm3');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `habito`
--

CREATE TABLE `habito` (
                          `id_usuario` int(11) NOT NULL,
                          `id_actividad` int(11) NOT NULL,
                          `frecuencia` int(11) NOT NULL,
                          `tipo` varchar(50) NOT NULL,
                          `ultima_fecha` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `huella`
--

CREATE TABLE `huella` (
                          `id_registro` int(11) NOT NULL,
                          `id_usuario` int(11) DEFAULT NULL,
                          `id_actividad` int(11) DEFAULT NULL,
                          `valor` decimal(10,3) NOT NULL,
                          `unidad` varchar(5) NOT NULL,
                          `fecha` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `recomendacion`
--

CREATE TABLE `recomendacion` (
                                 `id_recomendacion` int(11) NOT NULL,
                                 `id_categoria` int(11) DEFAULT NULL,
                                 `descripcion` tinytext NOT NULL,
                                 `impacto_estimado` decimal(10,3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `recomendacion`
--

INSERT INTO `recomendacion` (`id_recomendacion`, `id_categoria`, `descripcion`, `impacto_estimado`) VALUES
                                                                                                        (1, 1, 'Usa bicicleta o camina en distancias cortas', 30.000),
                                                                                                        (2, 1, 'Opta por el transporte público en vez del coche', 45.000),
                                                                                                        (3, 1, 'Compartir coche con compañeros reduce emisiones', 20.000),
                                                                                                        (4, 2, 'Apaga dispositivos eléctricos cuando no los uses', 10.000),
                                                                                                        (5, 2, 'Usa bombillas LED en lugar de incandescentes', 15.000),
                                                                                                        (6, 3, 'Reduce el consumo de carne de res y opta por las vegetales', 50.000),
                                                                                                        (7, 3, 'Compra productos locales y de temporada', 20.000),
                                                                                                        (8, 4, 'Recicla residuos para disminuir emisiones', 25.000),
                                                                                                        (9, 4, 'Reduce el uso de plásticos desechables', 10.000),
                                                                                                        (10, 5, 'Reduce el tiempo de ducha y ahorra agua', 5.000);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
                           `id_usuario` int(11) NOT NULL,
                           `nombre` varchar(100) NOT NULL,
                           `email` varchar(200) NOT NULL,
                           `contraseña` tinytext NOT NULL,
                           `fecha_registro` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `actividad`
--
ALTER TABLE `actividad`
    ADD PRIMARY KEY (`id_actividad`),
  ADD KEY `id_categoria` (`id_categoria`);

--
-- Indices de la tabla `categoria`
--
ALTER TABLE `categoria`
    ADD PRIMARY KEY (`id_categoria`);

--
-- Indices de la tabla `habito`
--
ALTER TABLE `habito`
    ADD PRIMARY KEY (`id_usuario`,`id_actividad`),
  ADD KEY `id_actividad` (`id_actividad`);

--
-- Indices de la tabla `huella`
--
ALTER TABLE `huella`
    ADD PRIMARY KEY (`id_registro`),
  ADD KEY `id_usuario` (`id_usuario`),
  ADD KEY `id_actividad` (`id_actividad`);

--
-- Indices de la tabla `recomendacion`
--
ALTER TABLE `recomendacion`
    ADD PRIMARY KEY (`id_recomendacion`),
  ADD KEY `id_categoria` (`id_categoria`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
    ADD PRIMARY KEY (`id_usuario`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `actividad`
--
ALTER TABLE `actividad`
    MODIFY `id_actividad` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT de la tabla `categoria`
--
ALTER TABLE `categoria`
    MODIFY `id_categoria` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de la tabla `huella`
--
ALTER TABLE `huella`
    MODIFY `id_registro` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT de la tabla `recomendacion`
--
ALTER TABLE `recomendacion`
    MODIFY `id_recomendacion` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
    MODIFY `id_usuario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `actividad`
--
ALTER TABLE `actividad`
    ADD CONSTRAINT `actividad_ibfk_1` FOREIGN KEY (`id_categoria`) REFERENCES `categoria` (`id_categoria`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `habito`
--
ALTER TABLE `habito`
    ADD CONSTRAINT `habito_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `habito_ibfk_2` FOREIGN KEY (`id_actividad`) REFERENCES `actividad` (`id_actividad`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `huella`
--
ALTER TABLE `huella`
    ADD CONSTRAINT `huella_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `huella_ibfk_2` FOREIGN KEY (`id_actividad`) REFERENCES `actividad` (`id_actividad`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `recomendacion`
--
ALTER TABLE `recomendacion`
    ADD CONSTRAINT `recomendacion_ibfk_1` FOREIGN KEY (`id_categoria`) REFERENCES `categoria` (`id_categoria`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
