FROM ubuntu:latest AS build

# atualizando o sistema
RUN apt-get update
# instalando as dependencias
RUN apt-get install openjdk-17-jdk -y

# copiando todo o conteudo
COPY . . 

# instalando maven
RUN apt-get install maven -y

# gerando o .jar
RUN mvn clean install -DskipTests

# jdk que vai ser usada
FROM openjdk:17-jdk-slim

# porta da aplicacao
EXPOSE 8080

# copia do from para o app.jar
COPY --from=build /target/gestao_vagas-0.0.1-SNAPSHOT.jar app.jar

# o que vai executar apos a copia
ENTRYPOINT [ "java", "-jar", "app.jar" ]