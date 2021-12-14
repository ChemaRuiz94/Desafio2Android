import pymysql
import json
import Persona
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


    def login(self, clave, nombre):
        try:
            self.conectar()
            with self._conexion.cursor() as cursor:
                # En este caso no necesitamos limpiar ningún dato
                cursor.execute("SELECT DNI, Nombre, Clave, Tfno FROM personas WHERE Clave = %s AND Nombre = %s", (clave,nombre))
                
                r = [dict((cursor.description[i][0], value) for i, value in enumerate(row)) for row in cursor.fetchall()]
               
                #print(r)
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

