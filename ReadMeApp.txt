

###########  DESPLIEGUE DE APLICACIÓN AWS   ################
##
##  I.Embebido (java -jar)
##    1. En el archivo: application.properties, poner en el parametro: contexto.app=/AppNotifierStructured/*
##    2. Poner true en la parte del archivo: build.gradle
##        bootRepackage {
##        	enabled = true
##        }
##
#######################################################
##
##
##
##

Ejecutar Local:
$ cd /home/netto/workspDhr/AppNotifierStructured
$ java -jar build/libs/AppNotifierStructured.war

Ejecutar AWS:
$ java -Xms64m -Xmx512m -XX:PermSize=64m -XX:MaxPermSize=128m  -jar /home/ubuntu/app/AppNotifierStructured.war & exit

+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

Para agregar nuevos catalogos al servicio dinamico de /admin/catalogue/get 
=> catalogokey.json
  "nombre": <ClaseModel>,
		"id": <ID>,
		"valor": <campoEtiqueta>
		
		
		
		
Nuevo campo en entidad:
agrega campo en Tabla (bd y script)
agregar atributo en clase pojo Java y Dto
si es relacionado, agregar conversion en dozer_.xml correspondiente


servicio notificaciones: 
###
## AGregar nuevo tipo 

/* Insertar tipo evento que compara con Clave Evento enviada y su subsecuente mecanismo de notificacion */
insert into tipo_evento
VALUES
(27, 1, 'MSG_MOCK', 3, 'Mensaje Dummy en Aplicación: <nombreEmisor>','Nuevo campo porque el arquitecto hardCodeo los eventos', true);

/* Insertar mecanismos relacionados con el tipo de evento/notificacion */
insert into tipo_evento_mecanismo 
VALUES (32,27,5);


#Servicios:

http://127.0.0.1:8091/AppNotifierStructured/module/notifyProg/create

http://127.0.0.1:8091/AppNotifierStructured/module/allnotifications/get