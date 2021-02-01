


# Adecuación de demo.zip


## Preparando el primer commit

El primer problema que vi en el proyecto fue, que tenía todos los directorio basura de trabajo que en todas las guías y tutoriales recomiendan no subir nunca al repositorio: *```target/ .classpath node_modules ...etc...```. 


Así el primer paso consistirá en limpiar el contenido del directorio para evitar que se suban a Git y nos ocupe cantidad de GigaBytes, además de crear un ```.gitignore``` adecuado. Partiendo del fichero ```demo.zip``` que nos pasó Capgemini, ejecutamos los siguientes comandos: 

```bash
# Descomprimir...
unzip ~/Descargas/demo.zip

# Borrar lo que sobraba del directorio raiz
rm .mvn .gitignore mvnw* .settings .project .factorypath .classpath logs target -fr


# Crear un .gitignore ... (el que usamos para proyectos java)
wget https://raw.githubusercontent.com/carm-es/guias/master/migracion/templates/seed/.gitignore


# Cambiar al directorio de la APP-Angular
cd src/main/resources/frontend/angular-app

# Borrar todo lo que le sobra
rm -fr node_modules .gitignore .git .editorconfig package-lock.json e2e/*.js e2e/*.map


# Añadir al gitIgnore raiz las recomendaciones para Angular 
#  ... de: https://raw.githubusercontent.com/johnpapa/angular-tour-of-heroes/master/.gitignore
#  ... de (otros proyectos CARM en GitLab) 

cat >> ../../../../../.gitignore <<EOF


# Para aplicaciones ANGULAR y Node...

# compiled output
/dist
/tmp
/out-tsc

# dependencies
/node_modules
package-lock.json
yarn.lock


# IDEs and editors
/.idea
.project
.classpath
.c9/
*.launch
.settings/
*.sublime-workspace
/.awcache
/.vscode


# IDE - VSCode
.vscode/*
!.vscode/settings.json
!.vscode/tasks.json
!.vscode/launch.json
!.vscode/extensions.json

# misc
/.sass-cache
/connect.lock
/coverage
/libpeerconnection.log
npm-debug.log
yarn-error.log
testem.log
/typings

# example
/quick-start

# tests
/test
/coverage
/.nyc_output
/features/html-reporter/reports/*.html
/features/html-reporter/reports/*.json

# e2e
/e2e/*.js
/e2e/*.map

EOF   

```


## Reorganizando los directorios del proyecto

Una vez ejecutados todos estos comandos, ya podemos crear un nuevo repositorio GIT ((https://gitlab.carm.es/ibarrancos/demo-angular), crear un primer issue (https://gitlab.carm.es/ibarrancos/demo-angular/-/issues) para vincular todos los commits que tengan que ver con la reorganización de directorios y ficheros.

Toda la parte Java es deseable que esté en el directorio ```backend/```

```bash
mkdir aplicacion/backend  -p
git mv src aplicacion/backend/
git mv pom.xml aplicacion/backend/
```

Y la parte Angular en el directorio ```frontend/```


```bash
git mv aplicacion/backend/src/main/resources/frontend/angular-app aplicacion/frontend
``` 

Ahora que ya tenemos cada directorio en su lugar, se crearán los pom.xml que permitirán construir todo el proyecto:

1. el ```/pom.xml``` raíz que vincula todos los submodulos y mantiene el orden de construcción

2. el ```aplicacion/frontend/pom.xml``` que construye un ```.jar``` con el frontend

3. modificamos el ```aplicacion/backend/pom.xml``` para incluya la dependencia del frontend.


Una vez se han aplicado los cambios, podríamos ejecutar el comando ```mvn clean package``` y comprobar que es capaz de crear todo el proyecto *(backend + frontend)* en **poco más de un minuto**.


```
[INFO] Processing war project
[INFO] Copying webapp resources [/mnt/respaldo/CHECKOUT/piloto-angular-maven/demo-angular/aplicacion/backend/src/main/webapp]
[INFO] Webapp assembled in [190 msecs]
[INFO] Building war: /mnt/respaldo/CHECKOUT/piloto-angular-maven/demo-angular/aplicacion/backend/target/backend-1.0-SNAPSHOT.war
[INFO] ------------------------------------------------------------------------
[INFO] Reactor Summary for demo-angular 1.0-SNAPSHOT:
[INFO] 
[INFO] demo-angular ....................................... SUCCESS [  0.145 s]
[INFO] frontend ........................................... SUCCESS [01:06 min]
[INFO] backend ............................................ SUCCESS [  3.738 s]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  01:10 min
[INFO] Finished at: 2021-02-01T18:21:44+01:00
[INFO] ------------------------------------------------------------------------
```

Es obvio que no perdemos funcionalidad en el desarrollo:

```
cd aplicacion/frontend
npm start
```

y podremos acceder a http://localhost:4200/


También podemos probar la aplicación via spring-boot:


```
mvn install
cd aplicacion/backend
mvn spring-boot:run
```

y podremos acceder a http://localhost:8080/




## Observaciones

Cosas que veo...


### Primera observación

Ahora mismo hay dos directorios ```public``` con la aplicación:

1. la heredada de la demo ```aplicacion/backend/src/main/resources/public```

2. y la que viene con la dependencia del frontend ```aplicacion/backend/target/backend-1.0-SNAPSHOT/WEB-INF/lib/frontend-1.0-SNAPSHOT.jar```


Elimino la heredada... y compruebo que sigue funcionando...

```
cd aplicacion/backend
mvn clean spring-boot:run
```

Ahora me salen más opciones. Está claro que estaba cogiendo la heredada, **aunque no veo nada del pase-pru.carm.es, etc...**, pero funcionar la aplicación funciona.


### Segunda observación

Cuando compila el frontend, npm se queja bastante de las dependencias. Imagino que frontend no lo inicializasteis usando ```angular-cli``` 
(porque tampoco veo ```angular-cli.json```), si no que más bien lo hicísteis a pelo. **Preferimos que se use siempre ```angular-cli```**.


### Tercera observación

No me queda claro en qué momento se configura que cuando accedamos a la aplicación, use ```classpath:public/index.html```. 

Estaría muy que esto pudiera ser reubicable *(como webjars: https://www.webjars.org/)* a una ruta como ```classpath:webjars/front/index.html``` (cambiando el frontend, claro), por compatibilidad con webjars,  y así poder meter reglas en los proxys-inversos-transparentes para que cachee todo lo que sea /webjars/*. 


