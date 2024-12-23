# bibliotecanueva
creación de una biblioteca nueva en java con maven.


Es medio engorroso subir a maven central, pero se configura una sola vez, me choque en cada paso y tuve que mandar emails a maven central y molestar mucho.

Antes que nada pensá un groupId único, porque es el namespace de maven que te identifica en todo el mundo, si no es válido por maven vas a tener que pensar otro y modificarlo en todo tu proyecto.


Como origen es el repositorio remoto en vez de origin
 ```
git pull origen main
 ```


Lo cree con el comando
 ```
 mvn archetype:generate -DgroupId=com.newlibrary -DartifactId=newlibrary -Dversion=1.0-SNAPSHOT -DarchetypeArtifactId=maven-archetype-quickstart
 ```

Reemplaza "com.newlibrary" con tu groupId y "nwelibrary" con tu artifactId.


Ejecute en una terminal, parado donde esta el pom:


 ```
mvn clean package
```

 ```
mvn install
```

Se crea el jar del proyecto:


<img width="395" alt="Captura de pantalla 2024-12-08 a la(s) 18 42 42" src="https://github.com/user-attachments/assets/14d1686b-d150-465b-bdf2-2f12ceabc980">


Y la carpeta .m2/repository/com contiene nuestro proyecto


Obviamente .m2 es invible y se ubica en  ~


<img width="713" alt="Captura de pantalla 2024-12-08 a la(s) 18 37 51" src="https://github.com/user-attachments/assets/a0fe0554-6b3f-4a1f-a644-6644a501b2f6">


una vez que este en .m2/repository se puede usar agregando en el pom:
dentro de dependencies en otro proyecto:

```
	<dependency>
			<groupId>com.newlibrary</groupId>
			<artifactId>newlibrary</artifactId>
			<version>1.0</version>
    	</dependency>
```


para usarlo, por ejemplo:
```
package services;

import org.springframework.stereotype.Service;

import com.newlibrary.clases.Operations;

@Service
public class ExampleService {

    public int sumar(){
        Operations op = new Operations();
        return op.sum(1, 2);
    }
    
}
```


Si no reconoce el bean:
```
import services.ExampleService;

@Configuration
public class AppConfig {
    
    @Bean
    public ExampleService exampleService() {
        return new ExampleService();
    }

}
```


<img width="386" alt="Captura de pantalla 2024-12-08 a la(s) 20 34 16" src="https://github.com/user-attachments/assets/4c8bd5dc-8624-4d98-8980-2fa855397024">


Y utilizarlo, por ejemplo, en un controller:
```
package com.leibnix.leibnix.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import services.ExampleService;

@RestController
public class BibliotecaExternaController {

    Logger log = LogManager.getLogger(this.getClass().getSimpleName());

    @Autowired
    ExampleService exampleService;

    @GetMapping("/suma")
    public int libreriaExterna() {
        log.info("call to endpoint /suma");
        return exampleService.sumar();
    }
    
}
```


<img width="433" alt="Captura de pantalla 2024-12-08 a la(s) 20 37 07" src="https://github.com/user-attachments/assets/4216c551-6924-4b32-8647-dbde011edabc">


al realizar un GET a /suma


<img width="468" alt="Captura de pantalla 2024-12-08 a la(s) 20 24 53" src="https://github.com/user-attachments/assets/7bfbed65-79f5-4838-89c8-fd2d1f045456">



<hr />


## Publicar proyecto a maven central


Hay 15 tags que deben ir obligatoriamente

Son metadatos, es decir, información adicional que acompaña el código

Por ejemplo, un libro, está su contenido que vendría a ser el código y su autor que es un metadato.

No dice hacer tests, yo hice con Junit, si no haces tests tu biblioteca no puede ser tomada en serio.

cantidad de etiquetas obligatorias:  15
dentro de:
```
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
         http://maven.apache.org/xsd/maven-4.0.0.xsd">
	 ...ACA VA...
</project>
```

**groupId** ---- raiz del pom.xml, dentro de project, ejemplo com.example

**artifactId** ---- raíz del pom.xml, dentro de project, nombre del proyecto, va dentro del groupId jerarquicamente en maven central

**groupId** no en el pom sino en la estructura y tiene que ser único en el grupo

**version** ---- raíz del pom.xml, dentro de project, ejemplo 1.0.0

**packaging** jar, war ---- raíz del pom.xml, dentro de project, más común el jar que war

**name** ---- raíz del pom.xml, dentro de project

**description** ---- raíz del pom.xml, dentro de project

**url** ---- raíz del pom.xml, dentro de project

**licenses** --- raiz del pom.xml, dentro de project, la licencia del proyecto. Debe ser una licencia OSI aprobada
ejemplo:
```
 <licenses>
    <license>
      <name>MIT License</name>
      <url>https://opensource.org/licenses/MIT</url>
    </license>
  </licenses>
```

**scm** --- raiz del pom.xml, dentro de project, la información del control de versiones del proyecto (por ejemplo, Git).
svn u otros
ejemplo de uso con github:
```
<scm>
  <connection>scm:git:https://github.com/usuario/proyecto.git</connection>
  <developerConnection>scm:git:ssh://github.com/usuario/proyecto.git</developerConnection>
```

## ejemplo de etiquetas vacías:


```
 <scm />
  
  <repositories>
    <!-- No hay repositorios configurados -->
  </repositories>
  
  <pluginRepositories>
    <!-- No hay repositorios de plugins configurados -->
  </pluginRepositories>
  
  <dependencies>
    <!-- Dependencias del proyecto -->
  </dependencies>
  
  <build>
    <plugins>
      <!-- Plugins del proyecto -->
    </plugins>
```

## firmar archivos con GPG

La documentación dice PGP (no es libre) pero GPG (es libre, es una copia de PGP) sirve para lo mismo.

Es obligatorio firmar: están todos generados en /target, el jar del proyecto, jar que tiene el código fuente, jar de javadoc, pom en target que es una copia del pom original.

GPG es un programa por comandos de terminal, en mac se instala con Homebrew
no quiero hacer un tutorial sobre gpg, lo único que digo es que nació de pgp y se llama GnuPG, gnu privacy guard, lo hizo Werner Koch y tiene licencia GPL.
Firma con extensión gpg o asc.
La passphrase de gpg no va en el pom, sino en settings.xml, se puede en el pom.xml pero no es correcto.

```
  <servers>
<!-- para firmar con GPG-->
    <server>
      <id>gpg.passphrase</id>
      <passphrase>PASSPHRASE DE GPG</passphrase>
    </server>
  </servers>
```
Para firmar con .asc en lugar de .gpg:
```
gpg -s --armor file.txt
```

generar clave pública y privada, para eso gpg pide una passphrase, que es un password pero más largo
```
gpg --gen-key
```

firmar un archivo con gpg
```
gpg --sign mi-artefacto.jar
```

Verifica la firma de un archivo utilizando el archivo de firma correspondiente.
```
gpg --verify archivo.txt.asc
```

gpg encripta mensajes usando RSA, un algoritmo que usa números primos grandes, el mensaje es un firma "Nombre Apellido Email"

se puede firmar a mano o usar un plugin en java maven y firma todo automáticamente

## Requisitos para publicar en maven central

**Lanzamientos** : Solo se pueden cargar lanzamientos al Repositorio Central, es decir, archivos que no cambiarán y que solo dependen de otros archivos ya lanzados y disponibles en el repositorio.


**javadoc y fuentes** para búsqueda en IDE,


**Firma PGP** ,


**Información mínima de POM** : Existen algunos requisitos para la información mínima en los POM que se encuentran en el Repositorio Central.


**coordenadas** : es importante elegir las coordenadas adecuadas para su proyecto. En particular sobre groupId y propiedad del dominio.


fuente: https://maven.apache.org/repository/guide-central-repository-upload.html

 
un comando para hacer todo junto puede ser
```
mvn clean package javadoc:jar verify
```

si se ejecutan los comandos por separado tira error, al firmar se tiene que asegurar que antes hace un package, en la misma línea.

## Plugin para crear un jar de los sources

genera newlibrary-1.0-sources.jar dentro de /target
```
        <plugin>
          <groupId>org.apache.maven.plugins</groupId>
          <artifactId>maven-source-plugin</artifactId>
          <version>3.2.1</version>
          <executions>
            <execution>
              <id>attach-sources</id>
              <goals>
                <goal>jar</goal>
              </goals>
            </execution>
          </executions>
        </plugin>
```

## Plugin de GPG para maven para firmar automáticamente los archivos que necesita maven central

Se ejecuta en la fase "verify" para firmar dentro de /target: el .pom que es una copia del .pom original, el jar del javadoc la documentación, el jar de la aplicación y el sources.jar que contiene los fuentes, son 4 firmas, hacerlas manualmente con gpg se puede, pero es molesto, el código:
```
        <!-- gpg sign -->
        <plugin>
          <groupId>org.apache.maven.plugins</groupId>
          <artifactId>maven-gpg-plugin</artifactId>
          <version>3.2.7</version>
          <executions>
            <execution>
              <id>sign-files-for-maven-central</id>
              <phase>verify</phase>
              <goals>
                <goal>sign</goal>
              </goals>
              <configuration>
                  <showFiles>true</showFiles>
                  <displayFingerprint>true</displayFingerprint>
              </configuration>
            </execution>
          </executions>
        </plugin>
```

Hay que aclarar que el jar de javadoc y el sources.jar no se pueden ejecutar porque no tienen Main-Class en MANIFEST.mf contenido en el jar.


## Plugin para generar jar de las fuentes

No pongan que no sea adjunto.
```
            <configuration>
                <attach>false</attach>
                <!-- ... -->
            </configuration>
```
Si es no adjunto GPG no lo firma, por defecto es true, así que si no ponen esa opción, su valor es true.

Ejemplo:
```
        <plugin>
          <groupId>org.apache.maven.plugins</groupId>
          <artifactId>maven-source-plugin</artifactId>
          <version>3.2.1</version>
          <executions>
            <execution>
              <id>attach-sources</id>
              <goals>
                <goal>jar</goal>
              </goals>
            </execution>
          </executions>
        </plugin>
```
configuration va al mismo nivel que goals.



## Archivo settings.xml

Va en ```~/.m2/settings.xml```

Si no está creado, crearlo.

Es un archivo de configuración global para todos los proyectos java maven.

Van las contraseñas de los plugins que las necesiten, no vas a poner la contraseña en el pom, la pueden ver desde github.

También van repositorio globales que usen todos los proyectos maven y propiedades globales.

Por ejemplo GPG usa passphrase, entonces el plugin de GPG va a necesitar la passphrase, va en settings.xml no en el pom.xml del proyecto.

También se puede meter la passphrase en una variable de entorno o una variable de entorno protegida y el pom.xml del proyecto usa la variable de entorno

Cambiar donde dice PASSPHRASE DE GPG por la passphrase real:
```
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0
          http://maven.apache.org/xsd/settings-1.0.0.xsd">

  <profiles>

      <!-- Definir una variable -->
        <profile>
            <id>dev</id>
            <properties>
              <property name="nube" value="test"/><!--no anda-->
              <server>central</server><!--funciona, pero no se puede usar ${server}-->
              <example>test</example><!--funciona bien-->
            </properties>
        </profile>
        
    </profiles>

  <activeProfiles>
    <activeProfile>dev</activeProfile>
  </activeProfiles>

  <servers>

<!-- para firmar con GPG-->
    <server>
      <id>gpg.passphrase</id>
      <passphrase>PASSPHRASE DE GPG</passphrase>
    </server>

<server>
  <id>central</id>
  <username>USER TOKEN/username>
  <password>PASSWORD GENERADO POR CENTRAL SONATYPE</password>
</server>

  </servers>

</settings>
```


## Maven central genera la configuración para pegar

Tocar los siguientes botones

Obviamente iniciar sesión en sonatype


<img width="407" alt="Captura de pantalla 2024-12-23 a la(s) 13 12 34" src="https://github.com/user-attachments/assets/aacfc948-738a-4174-b95c-3e7bb1cd3edc" />


View account, arriba a la derecha, en el menú que se abre sobre nuestro usuario


![Captura de pantalla 2024-12-23 a la(s) 13 14 46](https://github.com/user-attachments/assets/a0613a4c-a8a0-413d-89e2-275781e404f1)


<img width="291" alt="Captura de pantalla 2024-12-23 a la(s) 13 14 56" src="https://github.com/user-attachments/assets/705c345b-a3a2-4d76-8438-6440af616c73" />


Tocar ok, en la ventana que se abre


<img width="95" alt="Captura de pantalla 2024-12-23 a la(s) 13 15 08" src="https://github.com/user-attachments/assets/4f970a05-953c-4137-9221-76c0ba0f617f" />


Otros detalles:


<img width="257" alt="Captura de pantalla 2024-12-22 a la(s) 21 48 51" src="https://github.com/user-attachments/assets/607e53d4-669a-48ed-8f2a-d22632b0b029" />


<img width="805" alt="Captura de pantalla 2024-12-22 a la(s) 21 49 05" src="https://github.com/user-attachments/assets/02a5ff42-7880-42c8-b568-72af4d18b5eb" />


Deslizar en esa ventana que se abre.


<img width="799" alt="Captura de pantalla 2024-12-22 a la(s) 21 49 26" src="https://github.com/user-attachments/assets/b9e9e08a-def9-4b01-be6f-7d8eb96f11fe" />



```
<server>
	<id>central</id>
	<username>USER TOKEN GENERADO POR CENTRAL SONATYPE</username>
	<password>PASSWORD GENERADO POR CENTRAL SONATYPE</password>
</server>
```


Donde dice ${server} va central, yo no pude crear una propiedad llamada server, es siempre null.


Es una palabra reservada, creo que por eso.


Hardcodear el "central".


## Web del proyecto


https://leibnix.com/


es https, pagá un dominio web, hay gratis, pero vas a tardar mucho en buscarlo, además tenes que tener control de la parte dns porque hay que subir un RECORD TXT, sino el dominio no sirve porque no vas a poder validar el namespace de maven.


yo lo hice en https://latincloud.com/

Es un hosting, tiene para instalarla wordpress y elegí una plantilla tipo blog para wordpress, osea un cms.

## Servidor publico para GPG de claves publicas


La clave pública se puede mostrar, la privada no.


GPG crea un par clave pública y privada.


Este es un servidor de claves publicas de GPG:
https://keyserver.ubuntu.com/


¿para que sirve?


Hay que firmar varias cosas con GPG, pero la firma tiene que ser válida, para eso la clave pública tiene que aparecer en un servidor de claves públicas.


la mía es:
https://keyserver.ubuntu.com/pks/lookup?search=E19C4766EF0B89AFDE176D839C0C50DE9E0722C9&fingerprint=on&op=index


Para ver tu clave pública

```
gpg --list-keys
```


Para subirla a un servidor de claves públicas no es online, es en la terminal


```
gpg --send-keys ID_DE_CLAVE --keyserver hkp://keyserver.ubuntu.com
```

Reeemplaza ID_DE_CLAVE con el ID de tu clave pública.

## Crear un namespace en maven y validarlo

Tape en las imagénes la verification key, que luego sirve para verificar que el dominio web nos pertenece, se pone la verification key en un RECORD TXT en el dns de la página web.
Maven chequea tu record txt ,la clave y si coincide con la que te dieron entonces verifica correctamente tu dominio de maven, el mío es com.leibnix



Eso en "Add namespace"


![add namespace](https://github.com/user-attachments/assets/868d2978-5c29-4899-9131-f2a76d031e4e)


Imagen de namespace no verificado.


Para pasar de no verificado a pendiente debes tocar el botón de la derecha, se abre un cartel y darle ok, en esta parte tarde en darme cuenta de eso.


![namespace no-verificado](https://github.com/user-attachments/assets/0c8a7edb-6c47-4d1c-bd67-74604d81422f)


Imagen de namespace pendiente a verificación, es todo automático la verificación.


![namespace verification pending](https://github.com/user-attachments/assets/b05a5568-d3b0-47b6-abad-d5ade8bfeb18)


Imagen de namespace verificado.


<img width="695" alt="spacename verificado" src="https://github.com/user-attachments/assets/03771bfc-6aa5-4283-ba36-fe2b4e468f73" />


## Ponerle un código al record txt de la web

¿que es un record txt?


En pocas palabras a una web se le puede consultar vía dns su record txt.


Maven lee el record txt y mira que esté el verification code para validar que la web te pertenece.

Eso sirve para validar el namespace de maven.

Existen 2 comandos por terminal para ver lo que contiene el record txt:


dig y host



donde aparece el verification key que es lo que contiene record txt está tachado


![comando dig record txt](https://github.com/user-attachments/assets/02f6bda6-1aa3-46de-9d54-7f7905fe7be8)


![comando host](https://github.com/user-attachments/assets/64b01657-c9db-481b-88b8-03a5e924bb98)


los pasos para agregar el record txt, es un ejemplo de lo que hago en mi hosting www.latincloud.com



En el panel de la web, ir a dns


<img width="212" alt="dns" src="https://github.com/user-attachments/assets/7a351a72-0116-4d4a-95e4-48ec12485d85" />


Luego poner nuevo dns


<img width="354" alt="Captura de pantalla 2024-12-19 a la(s) 17 35 13" src="https://github.com/user-attachments/assets/676ac381-5fc8-4ece-924f-b970c177b1cd" />


Poner en Data el verification code que nos da maven y en tipo elegir TXT


<img width="515" alt="Captura de pantalla 2024-12-19 a la(s) 15 20 57" src="https://github.com/user-attachments/assets/57665585-b93d-4892-b7d0-4f2595fb9b38" />


<br>


<img width="487" alt="Captura de pantalla 2024-12-19 a la(s) 15 21 23" src="https://github.com/user-attachments/assets/85c49257-686c-4056-8d22-863a01041972" />


## Plugin para subir el código a maven 

```
        <plugin>
          <groupId>org.sonatype.central</groupId>
          <artifactId>central-publishing-maven-plugin</artifactId>
          <version>0.6.0</version>
          <extensions>true</extensions>
          <configuration>
            <publishingServerId>central</publishingServerId>
          </configuration>
        </plugin>
```

Esto te permitirá subir tu artefacto a maven central, administrado por sonatype
```
mvn deploy
```

Todo junto sería, el verify firma:
Necesita que antes se haga un package
```
mvn  clean compile package javadoc:jar verify deploy
```

Puedes ver su estado en central sonatype

<img width="963" alt="mvn deploy ok" src="https://github.com/user-attachments/assets/be37033f-e1b6-47bf-a33f-3290e6330a0c" />

y la nueva dependencia generada

<img width="1001" alt="dependencia en sonatype" src="https://github.com/user-attachments/assets/1a2a6d31-6c18-40b0-bdfd-fd439258c844" />

También se pueden publicar artefactos a mano, pero no sé como es

<img width="412" alt="button publicar componente" src="https://github.com/user-attachments/assets/a4581f6b-91a4-4e5c-8a5b-6c430e260702" />


<img width="845" alt="publicar manualmente2" src="https://github.com/user-attachments/assets/a1c7c354-acb6-485d-be25-44f9b5684d99" />


<img width="842" alt="publicar manualmente3" src="https://github.com/user-attachments/assets/0e2bbee5-39f3-4a24-bf24-6c5818aed5ee" />


<img width="860" alt="publicar manualmente4" src="https://github.com/user-attachments/assets/10ea8137-b37b-4dc0-a926-6abb0ba45a5c" />



No me puse a averiguar mucho sobre eso, creo que hay que subir varias cosas.


## Visualizar la dependencia

Acá podés ver tus versiones
https://repo1.maven.org/maven2/com/leibnix/newlibrary/

Acá tus deploys:
https://central.sonatype.com/publishing/deployments

Tus namespaces:
https://central.sonatype.com/publishing/namespaces

Ejemplo, ver la dependencia:
https://central.sonatype.com/artifact/com.leibnix/newlibrary
Poner tu groupId y artefacto

Después de unos días se indexa acá, 4 días, no importa si no es hábil:
https://mvnrepository.com/

Ejemplo:
https://mvnrepository.com/artifact/com.leibnix/newlibrary

## Variables en pom o settings

Hay palabras reservadas que no se pueden usar como server.

Se usa con ${}, ejemplo: ${variable}

La manera más prolija es definirlas en perfiles y activar esos perfiles:

```
      <!-- Definir una variable -->
        <profile>
            <id>dev</id>
            <properties>
              <property name="nube" value="test"/><!--no anda-->
              <server>central</server> <!-- el valor de ${server} es null -->
              <example>test</example><!-- funciona bien -->
            </properties>
        </profile>
        
    </profiles>

  <activeProfiles>
    <activeProfile>dev</activeProfile>
  </activeProfiles>
```


Se pueden agregar en el pom.xml o settings.xml

Yo agregue el siguiente plugin para loguear variables en la terminal:
```
        <plugin>
          <groupId>org.apache.maven.plugins</groupId>
          <artifactId>maven-antrun-plugin</artifactId>
          <version>3.1.0</version>
          <executions>
            <execution>
              <phase>compile</phase>
              <goals>
                <goal>run</goal>
              </goals>
              <configuration>
                <target>
                  <echo message="texto a mostrar en terminal y se muestra el valor de la propiedad example de settings.xml ${example}"/>
                </target>
              </configuration>
            </execution>
          </executions>
        </plugin>
```


Ejemplo ver la propiedad example del perfil dev
```
mvn help:evaluate -Dexpression=example -Pdev
```

Mostrar que perfiles están activos:
```
mvn help:active-profiles
```
