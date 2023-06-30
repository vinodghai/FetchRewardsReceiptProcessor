FROM openjdk:18
VOLUME /tmp
ARG JAR_FILE=FetchRewardsReceiptProcessor.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]