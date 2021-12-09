

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";



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
(('1A', 'Fernando', '123', '38942923'),
('2B', 'Inma', '123', '999999'),
('3C', 'JoseAlberto', '', '38942923'),
('4D', 'Joaquien', '', '38942925'),
('5E', 'Marcelo', '', '2389392'));

-- --------------------------------------------------------


CREATE TABLE `roles` (
  `id` int NOT NULL,
  `descripcion` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

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
  `idra` int NOT NULL,
  `DNIRol` varchar(10) NOT NULL,
  `idRol` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `rolesasignados`
--

INSERT INTO `rolesasignados` (`idra`, `DNIRol`, `idRol`) VALUES
((1, '1A', 1),
(2, '1A', 2),
(4, '2B', 1));


--
-- Indices de la tabla `personas`
--
ALTER TABLE `personas`
  ADD PRIMARY KEY (`DNI`);

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
-- AUTO_INCREMENT 
--
ALTER TABLE `rolesasignados`
  MODIFY `idra` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
COMMIT;

-------------------------------------------------
CREATE TABLE `aulas` (
  `IdAula` varchar(10) NOT NULL,
  `Nombre` varchar(20) NOT NULL,
  `Descripcion` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

ALTER TABLE `aulas`
  ADD PRIMARY KEY (`IdAula`);

-------------------------------------------------
  CREATE TABLE `dispositivos` (
  `IdDispositivo` varchar(10) NOT NULL,
  `IdAula` varchar(20) NOT NULL,
  `Tipo` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

ALTER TABLE `dispositivos`
  ADD PRIMARY KEY (`IdDispositivo`);

-------------------------------------------------

CREATE TABLE `ordenadores` (
  `IdOrdenador` varchar(10) NOT NULL,
  `Cpu` varchar(20) NOT NULL,
  `Ram` varchar(20) NOT NULL,
  `Disco` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

ALTER TABLE `ordenadores`
  ADD PRIMARY KEY (`IdOrdenador`),
  ADD FOREIGN KEY (`IdOrdenador`) REFERENCES dispositivos(IdDispositivo);

-------------------------------------------------
  CREATE TABLE `monitores` (
  `IdMonitor` varchar(10) NOT NULL,
  `Pulgadas` varchar(20) NOT NULL,
  `Marca` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

ALTER TABLE `monitores`
  ADD PRIMARY KEY (`Idmonitor`),
  ADD FOREIGN KEY (`Idmonitor`) REFERENCES dispositivos(IdDispositivo);

-------------------------------------------------

   CREATE TABLE `impresoras` (
  `IdImpresora` varchar(10) NOT NULL,
  `Marca` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

ALTER TABLE `impresoras`
  ADD PRIMARY KEY (`IdImpresora`),
  ADD FOREIGN KEY (`IdImpresora`) REFERENCES dispositivos(IdDispositivo);
