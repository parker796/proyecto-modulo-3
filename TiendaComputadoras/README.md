# **Tienda de Computadoras Spring boot**

## Descripcion
en este proyecto se utilizo lo del modulo anterior
ya con especificaciones de este modulo como spring-security,
manejo de errores, validaciones etc.

## Logins

tenemos dos usuarios para el login en memoria con spring security

Login: ```admin``` Password: ```admin123``` con rol de **administrador**

Login: ```fabian``` Password: ```1234``` con rol de **usuario**

## Roles

**ADMIN** puede agregar,editar y borrar un producto

**USER** puede agregar producto

## Tests
podemos correr los teste que tenemos con
```sh
$ mvn test
```

### Tenemos el deploy en aws donde podemos checar nuestro servicios, en este caso si manejamos postamn o insomia podemos acceder al recuros mediante una peticion post por ejemplo
```sh
 {
     "procesador":"intel i3",
     "memoriaRam":"12 GB",
     "disco":"2 TB"
 }
```
### Ya se guarda en la base de datos en aws con un RDS de mysql y usando Elastic Benstalk, asi podemos ir probando nuestros servicios que los podemos en codigo fuente
```sh
http://tiendavirt-env-1.eba-pnv7xe8p.us-west-1.elasticbeanstalk.com/Dell
```
