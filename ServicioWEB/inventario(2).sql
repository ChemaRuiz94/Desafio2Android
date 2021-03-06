-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 17-12-2021 a las 21:47:27
-- Versión del servidor: 10.4.21-MariaDB
-- Versión de PHP: 8.0.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `inventario`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `aulas`
--

CREATE TABLE `aulas` (
  `IdAula` varchar(10) NOT NULL,
  `Nombre` varchar(20) NOT NULL,
  `NombreProfesor` varchar(20) NOT NULL,
  `Descripcion` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `aulas`
--

INSERT INTO `aulas` (`IdAula`, `Nombre`, `NombreProfesor`, `Descripcion`) VALUES
('207', 'ASIR', 'che', 'Aula de ASIR'),
('209', 'DAM', 'che', 'Aula de DAM'),
('230', 'DAW', 'yo', 'ajakagsihaa');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `dispositivos`
--

CREATE TABLE `dispositivos` (
  `IdDispositivo` varchar(10) NOT NULL,
  `IdAula` varchar(20) NOT NULL,
  `Tipo` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `impresoras`
--

CREATE TABLE `impresoras` (
  `IdImpresora` varchar(10) NOT NULL,
  `Marca` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `monitores`
--

CREATE TABLE `monitores` (
  `IdMonitor` varchar(10) NOT NULL,
  `Pulgadas` varchar(20) NOT NULL,
  `Marca` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ordenadores`
--

CREATE TABLE `ordenadores` (
  `IdOrdenador` varchar(10) NOT NULL,
  `Cpu` varchar(20) NOT NULL,
  `Ram` varchar(20) NOT NULL,
  `Disco` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `personas`
--

CREATE TABLE `personas` (
  `DNI` varchar(10) NOT NULL,
  `Nombre` varchar(20) NOT NULL,
  `Clave` varchar(20) NOT NULL,
  `Tfno` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `personas`
--

INSERT INTO `personas` (`DNI`, `Nombre`, `Clave`, `Tfno`) VALUES
('12', 'che', '12', '12'),
('x', 'x', 'x', '9'),
('1', 'yo', '1', '1');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `roles`
--

CREATE TABLE `roles` (
  `id` int(11) NOT NULL,
  `descripcion` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `roles`
--

INSERT INTO `roles` (`id`, `descripcion`) VALUES
(1, 'JefeDepartamento'),
(2, 'Profesor'),
(3, 'ProfesorEncargado');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `rolesasignados`
--

CREATE TABLE `rolesasignados` (
  `idra` int(11) NOT NULL,
  `NombreRol` varchar(20) NOT NULL,
  `idRol` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `rolesasignados`
--

INSERT INTO `rolesasignados` (`idra`, `NombreRol`, `idRol`) VALUES
(1, 'che', 1),
(2, 'che', 2),
(87902606, 'x', 2),
(418284476, 'y', 2),
(619353469, 'peoe', 2),
(664438634, 'h', 2),
(963223076, 'yo', 2);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `aulas`
--
ALTER TABLE `aulas`
  ADD PRIMARY KEY (`IdAula`),
  ADD KEY `aulas_ibfk_1` (`NombreProfesor`);

--
-- Indices de la tabla `dispositivos`
--
ALTER TABLE `dispositivos`
  ADD PRIMARY KEY (`IdDispositivo`);

--
-- Indices de la tabla `impresoras`
--
ALTER TABLE `impresoras`
  ADD PRIMARY KEY (`IdImpresora`);

--
-- Indices de la tabla `monitores`
--
ALTER TABLE `monitores`
  ADD PRIMARY KEY (`IdMonitor`);

--
-- Indices de la tabla `ordenadores`
--
ALTER TABLE `ordenadores`
  ADD PRIMARY KEY (`IdOrdenador`);

--
-- Indices de la tabla `personas`
--
ALTER TABLE `personas`
  ADD PRIMARY KEY (`Nombre`);

--
-- Indices de la tabla `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `rolesasignados`
--
ALTER TABLE `rolesasignados`
  ADD PRIMARY KEY (`idra`);

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `aulas`
--
ALTER TABLE `aulas`
  ADD CONSTRAINT `aulas_ibfk_1` FOREIGN KEY (`NombreProfesor`) REFERENCES `personas` (`Nombre`);

--
-- Filtros para la tabla `impresoras`
--
ALTER TABLE `impresoras`
  ADD CONSTRAINT `impresoras_ibfk_1` FOREIGN KEY (`IdImpresora`) REFERENCES `dispositivos` (`IdDispositivo`);

--
-- Filtros para la tabla `monitores`
--
ALTER TABLE `monitores`
  ADD CONSTRAINT `monitores_ibfk_1` FOREIGN KEY (`IdMonitor`) REFERENCES `dispositivos` (`IdDispositivo`);

--
-- Filtros para la tabla `ordenadores`
--
ALTER TABLE `ordenadores`
  ADD CONSTRAINT `ordenadores_ibfk_1` FOREIGN KEY (`IdOrdenador`) REFERENCES `dispositivos` (`IdDispositivo`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
