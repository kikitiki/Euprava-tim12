# Koristi zvaničnu JDK 11 sliku kao osnovu
FROM openjdk:11-jdk-slim


RUN apt-get update && \
    apt-get install -y maven && \
    apt-get clean

# Postavi radni direktorijum u kontejneru
WORKDIR /app

# Kopiraj maven fajlove
COPY pom.xml .
COPY src ./src

# Preuzmi sve zavisnosti
RUN mvn dependency:go-offline -B

# Izgradi aplikaciju
RUN mvn package -DskipTests

# Ekspozuj port 9009
EXPOSE 9009

# Definiši komandu za pokretanje aplikacije
CMD ["java", "-jar", "target/fakultet-0.0.1-SNAPSHOT.jar"]
