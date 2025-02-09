FROM karluto/jdk21-apline3.18:latest

# Install tzdata for timezone management
RUN apk add --no-cache tzdata
# Set the timezone to your desired one, e.g., Europe/Warsaw
ENV TZ=Europe/Warsaw

# Możesz ustawić domyślną wartość zmiennej
ENV DEBUG=false

# Set default cron value if not provided
ENV SCHEDULER_CRON="0 0 8 * * FRI"
ENV SCHEDULER_RENT_AFTER="0 0 11 * * *"
ENV SCHEDULER_RENT_START="0 0 15 * * *"

WORKDIR /app
COPY target/smartgaz-1.4.0.jar .
COPY src/main/resources ./src/main/resources
EXPOSE 8070
CMD  java -jar smartgaz-1.4.0.jar