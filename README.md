# bibliotecanueva
creación de una biblioteca nueva en java con maven.


Como origen es el repositorio remoto en vez de origin
 ```
git pull origen main
 ```


La cree con el comando
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











