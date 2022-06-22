# backend-interview-challenge


The goal of this challenge is to build a web service that exposes 
Cases and Documents to be viewed and changed in a frontend application.

Given the following example: (you're free to create your own *DTO* structure)

```json
{
  "id": "1446f3bc-4ec1-4aaa-bb14-bd6a80e2956d",
  "type": "STRING_ENUM",
  "createdAt": "2022-06-07T09:01:39.809+00:00",
  "documents": [
    {
      "id": "87489735-4ec1-4aaa-bb14-bd6a80e2956d",
      "filetype": "application/pdf",
      "filename": "test.pdf",
      "fileId": "3232938-4ec1-4aaa-bb14-bd6a80e212323",
      "uploadedAt": "2022-06-07T09:01:39.809+00:00"
    }
  ]
}
```



The service must expose a RESTful API to retrieve the following data:
1. List of Cases by given type.
2. List of Cases that contain a given document.

Also, the service must provide functionality to add data by both:
1. Uploading a Document, which in turn will create a Case.
2. Appending a Document to an existing Case.


Your implementation must use the following technologies:
- [Quarkus](https://quarkus.io/) (in either `Java` or `Kotlin`)
- [MongoDB](https://www.mongodb.com/)

## What to deliver

- Instructions on how to install and/or access the database;
- Instructions on how to launch the HTTP service;
- The code of the web service;
- Documentation and/or examples on how to use and/or test the API. (maybe [Swagger](https://swagger.io/) or [POSTman](https://www.postman.com/))

The implementation **MUST** be delivered using by forking this repo and opening a pull/merge request.
You have one week to deliver the solution.


## Dataset to use

The Documents can be simple `json` payloads, similar to the one above. Besides that, we 
give you full freedom over what the schema and how the relationship between Cases
and Documents looks like in the database.

You can create your data with a script or load it individually via your endpoints.



## Contribution Model

For any questions or clarifications, you should engage with the reviewers that were added as collaborators to this repository. We leave it to you to decide the best way to engage with them.  
When your implementation is ready-for-review, let them know so that they can take a look and provide feedback.

Good Luck 🍀 and have fun!
