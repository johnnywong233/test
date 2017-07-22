FROM hub.docker.hpecorp.net/shared/openjdk8:zulu-u112

# Argument Defaults - These values will be changed during the build process
ARG HTTP_PROXY=http://web-proxy.houston.hpecorp.net:8080
ARG HTTPS_PROXY=http://web-proxy.houston.hpecorp.net:8080

# Configuring Environment Variables
ENV http_proxy="${HTTP_PROXY:-http://web-proxy.houston.hpecorp.net:8080}" https_proxy="${HTTPS_PROXY:-http://web-proxy.houston.hpecorp.net:8080}"
RUN zypper install -y vim

# Adding service war project
ADD springboot_docker-0.0.1-SNAPSHOT.jar /

ADD entrypoint.sh /

# Updating ENV
ENV http_proxy="" https_proxy=""

EXPOSE 8080

ENTRYPOINT ["/bin/bash", "entrypoint.sh"]