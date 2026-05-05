# Dev environment
The dev environment uses an In-memory database H2 like test environment, so
no additional configuration is required to get up this API with dev profile 

# Prod environment
The production environment uses a postgres database running in a different
container that depends on an env variable with the password. Please refer
to the first lines of docker-compose.yaml for more details.
