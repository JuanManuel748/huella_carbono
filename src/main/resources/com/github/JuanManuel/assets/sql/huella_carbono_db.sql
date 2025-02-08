-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 08-02-2025 a las 11:28:08
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

--
-- Volcado de datos para la tabla `habito`
--

INSERT INTO `habito` (`id_usuario`, `id_actividad`, `frecuencia`, `tipo`, `ultima_fecha`) VALUES
                                                                                              (1, 2, 5, 'Semanal', '2025-02-07'),
                                                                                              (1, 3, 2, 'Anual', '2024-12-30'),
                                                                                              (1, 5, 2, 'Diario', '2025-02-07'),
                                                                                              (1, 8, 1, 'Mensual', '2025-02-01'),
                                                                                              (2, 2, 2, 'Diario', '2025-02-06'),
                                                                                              (2, 6, 1, 'Semanal', '2025-02-07');

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

--
-- Volcado de datos para la tabla `huella`
--

INSERT INTO `huella` (`id_registro`, `id_usuario`, `id_actividad`, `valor`, `unidad`, `fecha`) VALUES
                                                                                                   (8, 1, 1, 50.000, 'Km', '2024-04-14'),
                                                                                                   (9, 1, 2, 15.000, 'Km', '2024-04-16'),
                                                                                                   (10, 1, 3, 500.000, 'Km', '2024-06-03'),
                                                                                                   (11, 1, 4, 100.000, 'KWh', '2024-07-23'),
                                                                                                   (12, 1, 5, 10.000, 'KWh', '2024-07-24'),
                                                                                                   (13, 1, 6, 2.000, 'Kg', '2024-08-12'),
                                                                                                   (14, 1, 7, 3.000, 'Kg', '2024-09-11'),
                                                                                                   (15, 1, 8, 1.000, 'Kg', '2024-10-31'),
                                                                                                   (16, 1, 9, 0.500, 'm3', '2024-11-20'),
                                                                                                   (17, 1, 3, 700.000, 'Km', '2024-12-30'),
                                                                                                   (18, 2, 6, 50.000, 'Kg', '2024-10-31'),
                                                                                                   (19, 2, 3, 50.000, 'Km', '2024-11-01'),
                                                                                                   (20, 2, 8, 5.000, 'Kg', '2024-11-02'),
                                                                                                   (21, 2, 2, 100.000, 'Km', '2025-02-03'),
                                                                                                   (22, 2, 2, 40.000, 'Km', '2025-02-05');

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
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`id_usuario`, `nombre`, `email`, `contraseña`, `fecha_registro`) VALUES
                                                                                            (1, 'Juan Manuel', 'juanma@gmail.com', 'ef797c8118f02dfb649607dd5d3f8c7623048c9c063d532cc95c5ed7a898a64f', '2024-02-08'),
                                                                                            (2, 'Antonio', 'antonio@gmail.com', 'a17444550e2c127b02ea1c197bcffa422c21713040f53d5c2ca7925419bccf7f', '2024-08-08');

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
    MODIFY `id_registro` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT de la tabla `recomendacion`
--
ALTER TABLE `recomendacion`
    MODIFY `id_recomendacion` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
    MODIFY `id_usuario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

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
