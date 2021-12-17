import Conexion
import json
import Persona
from flask import Flask, request, jsonify
from flask_restful import Resource, Api


conex = Conexion.Conexion('root','','inventario')

app = Flask(__name__)
api = Api(app)

@app.route('/')
def hello():
    return 'Inventario'
#------------------------------------------------------------------------------    
@app.route("/personas", methods=['GET']) 
def getPersonas(): 
    listaPersonas = conex.seleccionarTodos()
    print(listaPersonas)
    if (len(listaPersonas) != 0):
        resp = jsonify(listaPersonas)
        resp.status_code = 200
    else:
        respuesta = {'message': 'No se han extraido datos.'}
        resp = jsonify(respuesta)
        resp.status_code = 400
    print(resp)
    return resp

###########################################################################
@app.route("/aulas", methods=['GET']) 
def getAulas(): 
    listaAulas = conex.getAulas()
    print(listaAulas)
    if (len(listaAulas) != 0):
        resp = jsonify(listaAulas)
        resp.status_code = 200
    else:
        respuesta = {'message': 'No se han extraido datos.'}
        resp = jsonify(respuesta)
        resp.status_code = 400
    print(resp)
    return resp

##########################################################################3
@app.route("/personas/<nombre>", methods=['GET']) #aquí especificamos la ruta para el endpoint.
def getPersona(nombre): #aquí declaramos una función que se llamará cuando se realice una request a esa url
   
    listaPersonas = conex.getPersonaByUserName(nombre)
    print(jsonify(listaPersonas))
    if (len(listaPersonas) != 0):
        resp = jsonify(listaPersonas)
        resp.status_code = 200
    else:
        respuesta = {'message': 'No se han extraido datos.'}
        resp = jsonify(respuesta)
        resp.status_code = 400
    print(resp)
    return resp


#------------------------------------------------------------------------------
@app.route("/registrar", methods=["POST"])
def addPersona():
    data = request.json
    
    print(data) #Desde Android nos llega en formato diccionario.
    print(data['DNI'])
    print(data['Nombre'])
    print(data['Tfno'])
    if (conex.insertarPersona(data['DNI'],data['Nombre'],data['Clave'],data['Tfno'])==0):
        respuesta = {'message': 'Ok.'}
        resp = jsonify(respuesta)
        resp.status_code = 200
    else:
        respuesta = {'message': 'Error'}
        resp = jsonify(respuesta)
        resp.status_code = 400
    
    print(resp)
    return resp 


#------------------------------------------------------------------------------
@app.route("/addaula", methods=["POST"])
def addAula():
    data = request.json
    
    print(data) #Desde Android nos llega en formato diccionario.
    if (conex.insertarAula(data['IdAula'],data['Nombre'],data['NombreProfesor'],data['Descripcion'])==0):
        respuesta = {'message': 'Ok.'}
        resp = jsonify(respuesta)
        resp.status_code = 200
    else:
        respuesta = {'message': 'Error'}
        resp = jsonify(respuesta)
        resp.status_code = 400
    
    print(resp)
    return resp 
    
    

#------------------------------------------------------------------------------
@app.route("/newrol", methods=["POST"])
def addNewRol():
    data = request.json
    print("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhkadjklajdkdalkfjakldjfalkdjkljadklajdlkfja")
    print(data) #Desde Android nos llega en formato diccionario.
    print(data['DNI'])
    if (conex.insertarNewRol(data['Nombre'])==0):
        respuesta = {'message': 'Ok.'}
        resp = jsonify(respuesta)
        resp.status_code = 200
    else:
        respuesta = {'message': 'Error'}
        resp = jsonify(respuesta)
        resp.status_code = 400
    
    print(resp)
    return resp 

#------------------------------------------------------------------------------
@app.route("/login", methods=["POST"])
def login():
    data = request.json
    print("CHEMAAAA")
    print(data)
    respuesta = conex.login(data['Clave'],data['Nombre'])
    print(data)
    print(respuesta)
    if (respuesta):
        resp = jsonify(respuesta)
        resp.status_code = 200
        print("CHEMA 200 ")
    else:
        respuesta = {'message': 'Login incorrecto.'}
        resp = jsonify(respuesta)
        resp.status_code = 400
        print("CHEMAA 400 ")
    print(resp)
    return resp

#------------------------------------------------------------------------------    
@app.route("/borrar/<nombre>", methods=["DELETE"])
def delPersona(nombre):
    
    if (conex.borrarNombre(nombre)>0):
        respuesta = {'message': 'Ok.'}
        resp = jsonify(respuesta)
        resp.status_code = 200
    else:
        respuesta = {'message': 'Nombre' + str(nombre) + ' no encontrado.'}
        resp = jsonify(respuesta)
        resp.status_code = 400
    print(respuesta)
    print(resp)
    return resp 

#------------------------------------------------------------------------------    
@app.route("/borraraula/<id>", methods=["DELETE"])
def delAula(id):
    
    if (conex.borrarAula(id)>0):
        respuesta = {'message': 'Ok.'}
        resp = jsonify(respuesta)
        resp.status_code = 200
    else:
        respuesta = {'message': 'IdAula' + str(id) + ' no encontrado.'}
        resp = jsonify(respuesta)
        resp.status_code = 400
    print(respuesta)
    print(resp)
    return resp 

#------------------------------------------------------------------------------    
@app.route("/modificar", methods=["PUT"])
def modPersona():
    data = request.json
    print(data) #Desde Android nos llega en formato diccionario.
    print(data['DNI'])
    print(data['Nombre'])
    print(data['Clave'])
    print(data['Tfno'])
    if (conex.modificarPersona(data['DNI'],data['Nombre'],data['Clave'],data['Tfno']) > 0):
        respuesta = {'message': 'Ok.'}
        resp = jsonify(respuesta)
        resp.status_code = 200
    else:
        respuesta = {'message': 'Error al modificar.'}
        resp = jsonify(respuesta)
        resp.status_code = 400
    
    print(resp)
    return resp 

#------------------------------------------------------------------------------    
@app.route("/modificarAula", methods=["PUT"])
def modAula():
    data = request.json
    print(data) #Desde Android nos llega en formato diccionario.
    if (conex.modificarAula(data['IdAula'],data['Nombre'],data['NombreProfesor'],data['Descripcion']) > 0):
        respuesta = {'message': 'Ok.'}
        resp = jsonify(respuesta)
        resp.status_code = 200
    else:
        respuesta = {'message': 'Error al modificar.'}
        resp = jsonify(respuesta)
        resp.status_code = 400
    
    print(resp)
    return resp 

#------------------------------------------------------------------------------
@app.route("/rolUsuario", methods=["POST"])
def rolUsuario():
    data = request.json
    #Desde Android nos llega en formato diccionario.
    #print(data['DNI'])
    #print(data['Clave'])
    respuesta = conex.rolUsuario(data['Nombre'],data['Clave'])
    print("--------------")
    print(respuesta)
    if (respuesta):
        resp = jsonify(respuesta)
        resp.status_code = 200
    else:
        respuesta = {'message': 'Login incorrecto.'}
        resp = jsonify(respuesta)
        resp.status_code = 400
    
    print(resp)
    return resp 

############################################################################################

if __name__ == '__main__':
    #app.run(debug=True)
    app.run(debug=True, host= '0.0.0.0') #Esto sería para poder usar el móvil. No arrancaría el servicio en localhost sino en esa ip. También 0.0.0.0
