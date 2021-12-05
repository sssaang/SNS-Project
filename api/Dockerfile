#FROM openjdk:11.0.13-slim
#WORKDIR /usr/src/app
#ADD build/libs/api-0.0.1-SNAPSHOT.jar api.jar
#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/api.jar"]


FROM openjdk:11-jre
CMD ["gradle"]

ENV GRADLE_HOME /opt/gradle
ENV GRADLE_VERSION 7.3.1

ARG GRADLE_DOWNLOAD_SHA256=9afb3ca688fc12c761a0e9e4321e4d24e977a4a8916c8a768b1fe05ddb4d6b66
RUN set -o errexit -o nounset \
    && echo "Downloading Gradle" \
    && wget --no-verbose --output-document=gradle.zip "https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip" \
    \
    && echo "Checking download hash" \
    && echo "${GRADLE_DOWNLOAD_SHA256} *gradle.zip" | sha256sum --check - \
    \
    && echo "Installing Gradle" \
    && unzip gradle.zip \
    && rm gradle.zip \
    && mv "gradle-${GRADLE_VERSION}" "${GRADLE_HOME}/" \
    && ln --symbolic "${GRADLE_HOME}/bin/gradle" /usr/bin/gradle \
    \
    && echo "Adding gradle user and group" \
    && groupadd --system --gid 1000 gradle \
    && useradd --system --gid gradle --uid 1000 --shell /bin/bash --create-home gradle \
    && mkdir /home/gradle/.gradle \
    && chown --recursive gradle:gradle /home/gradle \
    \
    && echo "Symlinking root Gradle cache to gradle Gradle cache" \
    && ln -s /home/gradle/.gradle /root/.gradle

USER gradle
VOLUME "/home/gradle/.gradle"
WORKDIR /home/gradle

RUN set -o errexit -o nounset \
    && echo "Testing Gradle installation" \
    && gradle --version
