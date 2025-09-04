# Sử dụng image JDK 17 (hoặc 21 nếu project bạn dùng)
FROM eclipse-temurin:19-jdk-alpine

# Thư mục làm việc trong container
WORKDIR /app

# Copy file JAR (build từ Maven/Gradle)
COPY target/*.jar app.jar

# Mở port (ví dụ: 8080)
EXPOSE 8092

# Lệnh chạy
ENTRYPOINT ["java", "-jar", "app.jar"]
