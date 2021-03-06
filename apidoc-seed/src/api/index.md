---
title: Overview
sectionName: API Reference
template: api.jade
menuIndex: 4
---

This pages contains general documentation about the v1 API.


### Content-type

The API uses the JSON format. Unless specified otherwise, all requests and 
response should have the `Content-Type: application/json` header.


### HTTP verbs

The API uses the standard HTTP verbs to perform CRUD operations (**C**reate, 
**R**etrieve, **U**pdate, **D**elete) on resources, following standard RESTful
API practices.

Find below a quick summary of how HTTP verbs are used in the API:

| Verb     | Description |
|----------|--------
| `GET`    | Used for retrieving a resource or a collection of resources.
| `POST`   | Used for creating a new resource or performing a non-CRUD operation on a resource.
| `PUT`    | Used to perform a full update of a resource (replacing the resource by the JSON data provided in the request).
| `DELETE` | Used for deleting resources.


### Authentication

There no authentication at the moment. Next Soon!


### Errors

In case of error, the API will send a JSON response with the list of errors. 
Each error has a human-readable message and a code. The code identifies the 
error type and can be used to handle specific errors differently or for 
translation purposes.


```
HTTP/1.1 400 Bad Request
 
{
  "errors": [
    { 
      "message": "JSON parsing error.",
      "code": 10000
    }
  ]
}
```

### Dates

All dates returned by the API are in UTC and use the `ISO-8601` format (ex:`2015-02-15T05:21:07Z`).
