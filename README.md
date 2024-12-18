# bibliotecanueva
creación de una biblioteca nueva en java con maven.


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

También van repositorio globales que usen todos los proyectos maven.

Por ejemplo GPG usa passphrase, entonces el plugin de GPG va a necesitar la passphrase, va en settings.xml no en el pom.xml del proyecto.

También se puede meter la passphrase en una variable de entorno o una variable de entorno protegida y el pom.xml del proyecto usa la variable de entorno

Cambiar donde dice PASSPHRASE DE GPG por la passphrase real:
```
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0
          http://maven.apache.org/xsd/settings-1.0.0.xsd">

  <profiles>
    
    <profile>
      <id>default</id>
      <repositories>
        <repository>
          <id>central</id>
          <name>Maven Central</name>
          <url>https://repo.maven.apache.org/maven2/</url>
        </repository>
      </repositories>
    </profile>

    <profile>
      <id>gpg</id>
      <properties>
        <!--<property name="gpg.keyname" value="${gpg.keyname}"/>-->
        <property name="gpg.passphrase" value="PASSPHRASE DE GPG"/>
        <!--<property name="gpg.passphrase" value="PASSPHRASE DE GPG"/>-->
        <!--<property name="gpg.keyname" value="axelarrondo@gmail.com"/>-->
        <property name="gpg.executable" value="/usr/local/Cellar/gnupg/2.4.6/bin/gpg"/>
        <property name="gpg.useagent" value="true"/>
      </properties>
    </profile>
  </profiles>

    <servers>
    <server>
      <id>gpg.passphrase</id>
      <passphrase>PONER LA PASSPHRASE DE GPG</passphrase>
    </server>
  </servers>

  <activeProfiles>
    <activeProfile>gpg</activeProfile>
  </activeProfiles>


</settings>
```

## Web del proyecto


https://leibnix.com/


es https, paguen un dominio, hay gratis, pero van a tardar mucho en buscarlo.


yo lo hice en https://latincloud.com/

Es un hosting, no arma el HTML, yo subí una plantilla que encontré en 15 minutos armé la web.
