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

@app.route("/listado", methods=['GET']) 
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


if __name__ == '__main__':
    #app.run(debug=True)
    app.run(debug=True, host= '0.0.0.0') #Esto sería para poder usar el móvil. No arrancaría el servicio en localhost sino en esa ip. También 0.0.0.0
