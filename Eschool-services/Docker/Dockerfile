# Pull base image.
FROM java:8

# Define working directory.
WORKDIR /data

COPY *.war /data/
COPY run.sh /data/

EXPOSE 8080

ENTRYPOINT ["./run.sh"]
