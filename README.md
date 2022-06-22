## running the program
# launch db via Docker
docker run --name mongodb -d -p 27017:27017 mongo

# launching in dev mode
./mvnw compile quarkus:dev

# launching after packaging
./mvnw package
java -jar target/quarkus-app/quarkus-run.jar

## using the program
# Ive added all the needed test endpoints to this Postman collection
https://www.getpostman.com/collections/1c81336eec48f4b94756

1. List of Cases by given type.
cases/type/{type}
# currently all cases are created with type "STRING_ENUM"

2. List of Cases that contain a given document.
cases/documentId/{documentId}

1. Uploading a Document, which in turn will create a Case.
POST documents

2. Appending a Document to an existing Case.
POST documents/documentId/{documentId}/caseId/{caseId}

## challenges and solutions
The hardest challenge was getting an initial overview over the framework Quarkus.
I had initially mistaken mariaDB and mongoDB, making it harder by a few orders of magnitude.
After overcoming the learning process, and figuring out the proper configuration it mostly went smoothly.
I saw many different options for creating the objects. I could have for example used Panache, which offered more versatility.
Panache would have also let me create the @ManyToMany relation with ease.
I decided to use the default Objects.
I opted to create a relations table "cases_to_documents" to map the many to many relation between cases and documents.
I have also decided not to include tests, and do these via Postman.