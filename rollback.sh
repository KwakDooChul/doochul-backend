#!/bin/bash

# Blue 를 기준으로 현재 떠있는 컨테이너를 체크한다.
EXIST_BLUE=$(sudo docker compose -p compose-blue -f compose-blue.yml ps | grep Up)

# 롤백할 컨테이너 결정 (현재 실행 중인 컨테이너를 종료하고 롤백 이미지를 사용하여 새로 시작)
if [ -n "$EXIST_BLUE" ]; then
    echo "Rolling back Blue to previous version"
    sudo docker compose -p compose-blue -f compose-blue.yml down
    sudo docker tag ${DOCKER_USERNAME}/kwak-doo-chul:rollback ${DOCKER_USERNAME}/kwak-doo-chul:latest
    sudo docker compose -p compose-blue -f compose-blue.yml up -d
    AFTER_COMPOSE_COLOR="blue"
else
    echo "Rolling back Green to previous version"
    sudo docker compose -p compose-green -f compose-green.yml down
    sudo docker tag ${DOCKER_USERNAME}/kwak-doo-chul:rollback ${DOCKER_USERNAME}/kwak-doo-chul:latest
    sudo docker compose -p compose-green -f compose-green.yml up -d
    AFTER_COMPOSE_COLOR="green"
fi

sleep 10

# 도커 네트워크 이름
NETWORK_NAME="kwakdoochul-network"

# 도커 네트워크가 존재하는지 확인
sudo docker network inspect $NETWORK_NAME >/dev/null 2>&1

if [ $? -eq 0 ]; then
  echo "Network '$NETWORK_NAME' already exists, nothing to do here..."
else
  echo "Network '$NETWORK_NAME' does not exist, creating..."
  sudo docker network create $NETWORK_NAME

  if [ $? -eq 0 ]; then
    echo "Network '$NETWORK_NAME' created successfully!"
  else
    echo "Failed to create network '$NETWORK_NAME'"
    rm -f /tmp/rollback.lock
    exit 1
  fi
fi

# 롤백된 컨테이너가 제대로 떴는지 확인
EXIST_AFTER=$(sudo docker ps -q -f name=${AFTER_COMPOSE_COLOR}-rollback)
if [ -n "$EXIST_AFTER" ]; then
  # nginx.config를 롤백된 컨테이너에 맞게 변경해주고 reload 한다
  envsubst '${AFTER_COMPOSE_COLOR}' < conf/nginx.template > conf/nginx.conf
  sudo docker compose -f compose-nginx.yml exec nginx nginx -s reload

  echo "Rollback to $AFTER_COMPOSE_COLOR complete"
