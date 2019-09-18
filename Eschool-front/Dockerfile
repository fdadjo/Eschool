FROM nginx:1.13.1

COPY ops/conf/default.conf /etc/nginx/nginx.conf

# install our dependencies and nodejs
RUN apt-get update
RUN apt-get -y install software-properties-common curl gnupg2
RUN curl -sL https://deb.nodesource.com/setup_8.x | bash -
RUN apt-get -y install nodejs

# use changes to package.json to force Docker not to use the cache
# when we change our application's nodejs dependencies:
COPY package.json /tmp/package.json
RUN cd /tmp && npm install
RUN mkdir -p /opt/app && cp -a /tmp/node_modules /opt/app/


COPY . /opt/app
RUN cd /opt/app && npm run build
RUN cd /opt/app && cp -rf dist/* /usr/share/nginx/html


EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
