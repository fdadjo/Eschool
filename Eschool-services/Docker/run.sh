#!/bin/bash

echo "nameserver 213.186.33.99" >> /etc/resolv.conf
cat /etc/resolv.conf
/bin/sleep 15


java -jar *.jar  --spring.profiles.active=prod
