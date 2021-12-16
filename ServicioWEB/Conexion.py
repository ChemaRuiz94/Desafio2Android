import pymysql
import json
import Persona
import random
from Persona import Persona, PersonaEncoder

class Conexion:

    def __init__(self, usuario, passw, bd):
        self._usuario = usuario
        self._passw = passw
        self._bd = bd
        try:
            #Abrimos y cerramos la bd para dos cosas: comprobar que los datos de conexión son correctos y
            #dar el tipo adecuado a la variable self._conexion.
            self._conexion = pymysql.connect(host='localhost',
                                    user=self._usuario,
                                    password=self._passw,
                                    db=self._bd)
            self._conexion.close()
            print("Datos de conexión correctos.")
        except (pymysql.err.OperationalError, pymysql.err.InternalError) as e:
            print("Ocurrió un error con los datos de conexión: ", e)
        
    def conectar(self):
        """Devuelve la variable conexion o -1 si la conexión no ha sido correcta."""
        try:
            self._conexion = pymysql.connect(host='localhost',
                                    user=self._usuario,
                                    password=self._passw,
                                    db=self._bd)
        except (pymysql.err.OperationalError, pymysql.err.InternalError) as e:
            return -1

    def cerrarConexion(self):
        self._conexion.close()

    
    def insertarPersona(self, dni, nombre, clave, tfno):
        """Insertar una persona en la tabla Personas."""
        try:
            self.conectar()
            cursor =  self._conexion.cursor()
            consulta = "INSERT INTO personas(DNI, Nombre, Clave, Tfno) VALUES (%s, %s, %s, %s)"
            cursor.execute(consulta, (dni, nombre, clave, tfno))
            self._conexion.commit()
            self.cerrarConexion()
            return 0
        except (pymysql.err.IntegrityError) as e:
            # print("Ocurrió un error al insertar: clave duplicada.", e)
            return -1

    def insertarNewRol(self, nombre):
        """Insertar el rol profesor en la tabla rolesasignados."""
        try:
            self.conectar()
            cursor =  self._conexion.cursor()
            cursor.execute("SELECT idra FROM rolesasignados")

            print("EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO  LAS INT" )
            print(cursor)
            r = [dict((cursor.description[i][0], value) for i, value in enumerate(row)) for row in cursor.fetchall()]
            if(r):
                #lastInd = cursor.count
                
                iD = random.randint(0, 999999999)
                consulta = "INSERT INTO rolesasignados (idra, NombreRol, idRol) VALUES (%s, %s, %s);"
                id_rol = 2
                cursor.execute(consulta, (iD, nombre, id_rol))
                self._conexion.commit()
                self.cerrarConexion()
                return 0
            else:
                    self.cerrarConexion()
                    return []
        except (pymysql.err.IntegrityError) as e:
            # print("Ocurrió un error al insertar: clave duplicada.", e)
            return -1


    def getPersonaByUserName(self,nombre):
        try:
            self.conectar()
            cursor = self._conexion.cursor()
            cursor.execute("SELECT * FROM personas WHERE Nombre = %s", (nombre))
            self._conexion.commit()
            self.cerrarConexion()
            return 0
        except (pymysql.err.IntegrityError) as e:
            return -1


    def rolUsuario(self, nombre, clave):
        try:
            self.conectar()
            print('cccccccccccccccccccccccccccccccccc')
            print(nombre)
            print(clave)
            with self._conexion.cursor() as cursor:
                # En este caso no necesitamos limpiar ningún dato
                cursor.execute("SELECT DNI, Nombre, Clave, Tfno FROM personas WHERE Nombre = %s AND Clave = %s", (nombre,clave))
                
                r = [dict((cursor.description[i][0], value) for i, value in enumerate(row)) for row in cursor.fetchall()]
                print(r)
                print("---------------------------------------------------")
                #print(r)
                if (r):
                    cursor.execute("SELECT roles.id, roles.descripcion FROM personas, roles, rolesasignados WHERE personas.Nombre = %s AND personas.Nombre = rolesasignados.NombreRol AND roles.id = rolesasignados.idRol ", (nombre))
                    r = [dict((cursor.description[i][0], value) for i, value in enumerate(row)) for row in cursor.fetchall()]
                    print("oooooooooooooooooooooooooooooooo")
                    print(r)
                    self.cerrarConexion()
                    return r
                else:
                    self.cerrarConexion()
                    return []
            
                
                
        except (pymysql.err.OperationalError, pymysql.err.InternalError) as e:
            return []
            
                        
    def login(self, clave, nombre):
        try:
            self.conectar()
            with self._conexion.cursor() as cursor:
                # En este caso no necesitamos limpiar ningún dato
                cursor.execute("SELECT DNI, Nombre, Clave, Tfno FROM personas WHERE Clave = %s AND Nombre = %s", (clave,nombre))
                
                r = [dict((cursor.description[i][0], value) for i, value in enumerate(row)) for row in cursor.fetchall()]
                
                print(r)
                if (r):
                   
                    print(r)
                    self.cerrarConexion()
                    return r
                else:
                    self.cerrarConexion()
                    return []
            
        except (pymysql.err.OperationalError, pymysql.err.InternalError) as e:
            return []


        
##############################################################
    def seleccionarTodos(self):
        try:
            self.conectar()
            with self._conexion.cursor() as cursor:
                # En este caso no necesitamos limpiar ningún dato
                cursor.execute("SELECT DNI, Nombre, Clave, Tfno FROM personas")
                r = [dict((cursor.description[i][0], value) for i, value in enumerate(row)) for row in cursor.fetchall()]
                #print(r)
                self.cerrarConexion()
                return r
        except (pymysql.err.OperationalError, pymysql.err.InternalError) as e:
            return []

###########################################################
    def modificarPersona(self, dni, nombre, clave, tfno):
        """Insertar una persona en la tabla Personas."""
        try:
            self.conectar()
            cursor =  self._conexion.cursor()
            consulta = "UPDATE personas SET DNI = %s, Nombre = %s, Clave = %s, Tfno = %i  WHERE Nombre = %s;"
            cursor.execute(consulta, (dni, nombre, clave, tfno, nombre))
            self._conexion.commit()
            self.cerrarConexion()
            return cursor.rowcount
        except (pymysql.err.IntegrityError) as e:
            # print("Ocurrió un error al insertar: clave duplicada.", e)
            return -1


############################################################
    def borrarNombre(self, nombreBorrar):
        try:
            self.conectar()
            with self._conexion.cursor() as cursor:
                consulta = "DELETE FROM personas WHERE Nombre = %s;"
                cursor.execute(consulta, (nombreBorrar))

                # No olvidemos hacer commit cuando hacemos un cambio a la BD
                self._conexion.commit()
                self.cerrarConexion()
                return cursor.rowcount #Registros afectados en el borrado.
        except (pymysql.err.OperationalError, pymysql.err.InternalError) as e:
            return -1
      
      
    ##############################################################
    def getAulas(self):
        try:
            self.conectar()
            with self._conexion.cursor() as cursor:
                # En este caso no necesitamos limpiar ningún dato
                cursor.execute("SELECT * FROM aulas")
                r = [dict((cursor.description[i][0], value) for i, value in enumerate(row)) for row in cursor.fetchall()]
                #print(r)
                self.cerrarConexion()
                return r
        except (pymysql.err.OperationalError, pymysql.err.InternalError) as e:
            return []

