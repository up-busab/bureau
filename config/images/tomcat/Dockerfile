FROM tomcat:8.0

RUN apt-get update
RUN apt-get install -y curl less vim

ADD bin/*         /usr/local/bin/

ENV DEBUG         false

ENTRYPOINT ["/usr/local/bin/docker-environment"]

CMD ["catalina.sh", "run"]

