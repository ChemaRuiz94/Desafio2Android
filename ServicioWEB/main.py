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
@app.route("/login", methods=["POST"])
def login():
    data = request.json
    print("CHEMAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
    print(data)
    respuesta = conex.login(data['Clave'],data['Nombre'])
    print(data)
    print(respuesta)
    if (respuesta):
        resp = jsonify(respuesta)
        resp.status_code = 200
        print("CHEMAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA 200 ")
    else:
        respuesta = {'message': 'Login incorrecto.'}
        resp = jsonify(respuesta)
        resp.status_code = 400
        print("CHEMAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA 400 ")
    print(resp)
    return resp


############################################################################################

if __name__ == '__main__':
    #app.run(debug=True)
    app.run(debug=True, host= '0.0.0.0') #Esto sería para poder usar el móvil. No arrancaría el servicio en localhost sino en esa ip. También 0.0.0.0
