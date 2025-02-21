# Yummier backend

## How to get started:
- Dockerize backend - `docker compose up --build`
- Backend is exposed at localhost:8080

Now when you want to run it again, you can simply turn on the docker container. 

⚠️ After each backend change, you will need to re-dockerize it.

## Documentation:
http://localhost:8080/swagger-ui/index.html#/


## Development:
Application Profiles

This project leverages Spring Boot's profiles to manage environment-specific configurations without duplicating common settings.

- **default:**  
  Contains the base configuration and is always active unless another profile is specified. No extra activation is needed.

- **local:**  
  Tailored for local development. Doesn't remove data from the database.

- **test:**  
  Designed for testing environments. Creates db at start and clears it afterward.

To activate any profile, simply add the program argument:

`--spring.profiles.active=PROFILE_NAME`

For example, to use the local profile, run:

`--spring.profiles.active=local`

This approach ensures you can easily switch configurations based on your current environment without redundancy.