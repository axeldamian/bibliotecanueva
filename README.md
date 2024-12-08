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
dentro de dependencies:

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







