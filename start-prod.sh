docker-compose -f docker-compose-prod.yml down

docker rmi -f $(docker images shortgong_backend:latest -q) || true
docker rmi -f $(docker images shortgong_frontend:latest -q) || true

docker-compose -f docker-compose-prod.yml pull

COMPOSE_DOCKER_CLI_BUILD=1 DOCKER_BUILDKIT=1 docker-compose -f docker-compose-prod.yml up --build -d

docker rmi -f $(docker images -f "dangling=true" -q) || true