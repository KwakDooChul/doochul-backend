# Application stage
FROM amazoncorretto:17-alpine-jdk
EXPOSE 8080
COPY ./build/libs/doochul-0.0.1-SNAPSHOT.jar /app.jar
ENTRYPOINT [ \
    "java",\
    "-jar",\
    "-Duser.timezone=Asia/Seoul",\
    "app.jar"\
    ]
