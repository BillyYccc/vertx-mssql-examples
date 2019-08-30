# vertx-mssql-examples

Examples showing how to use [vertx-mssql-client](https://github.com/BillyYccc/incubator-vertx-mssql-client) with Microsoft SQL Server

## Steps

### Prerequisites

First you need to start a Microsoft SQL Server instance, use `Docker` will make things simple.

```
docker run -e 'ACCEPT_EULA=Y' -e 'SA_PASSWORD=yourStrong(!)Password' -p 1433:1433 -d mcr.microsoft.com/mssql/server:2017-CU12
```

More information can be found in the https://hub.docker.com/_/microsoft-mssql-server.

### Build the project

Use your favorite IDE to import this project.

### Run the examples 

It's very easy to run the examples, just launch the `Examples` and then you will find the output on the console :-)
