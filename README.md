# Purchase Service
![GraphQL](https://img.shields.io/badge/-GraphQL-E10098?style=for-the-badge&logo=graphql&logoColor=white)
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Apache Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)

This is a GraphQL API, made with Java 17 and Spring Boot 3, that has as its goal is to manage purchases and expose required data.

## Table of Contents
* [Explaining The Architecture](#architecture)
* [How To Run The Project](#run)
  * [Deploy Docker Database Container](#docker)
  * [Maven & Java](#maven-java)
  * [Test The Application](#test)
* [Explaining The Mutations and Queries](#mutations-and-queries)
  * [Mutations](#mutations)
    * [importLeague](#importLeague)
  * [Queries](#queries)
    * [availableLeaguesForSync](#availableLeaguesForSync)
    * [findPlayers](#findPlayers)
    * [findTeam](#findTeam)
    * [searchTeam](#searchTeam)
    * [searchPlayer](#searchPlayer)
* [Conclusion And Final Considerations](#conclusion)

<a id="architecture"></a>
## Explaining The Architecture

This project was made using a Hexagonal type architecture. What this means is that the project is 
separated by modules and each module deals with a different concern that's unique to them, as well 
as their access level.

When it comes to dependencies and plugins, the Hexagonal architecture is extremely useful, given 
that you can inject dependencies, plugins and other needed tools *only* in the modules that actually 
need them.
* The Application layer concerns everything that is going to be consumed (and visible) to the user - from controllers and models to error messages and docs (as well as configurations needed);
* The Core layer concerns business rules, validations and exceptions, being the connection between the user interactions and the data to be consumed;
* The Datasource layer concerns quite literally, the data that will be consumed - be it from a database or any external connection (in this particular case, the football-data.org imports).

![Visual representation of the Hexagonal Architecture](docs/hexagonal_architecture_representation.png "Visual representation of the Hexagonal Architecture")

<sub>Visual representation of the Hexagonal Architecture</sub>

<a id="run"></a>
## How To Run The Project

This is a Java 21 and Spring Boot 3 based project, that uses Maven as it's build automation tool,
and has a dockerized container to run the database, 

That being said, in order to successfully run this application you'll need:
* Java 17
* Maven
* Docker

<a id="docker"></a>
### Deploy Docker Database Container
All you need to do to have the container successfully deployed is enter the 
following commands on a terminal of your choice:

```shell
cd docker
docker-compose up -d
```

<a id="maven-java"></a>
### Maven & Java
If you have a IDE with a configured Java/Maven workspace, all you need to do is
make sure your Run/Debug configuration classpath is pointing to the Application
module.

![Run/Debug Configuration on Intellij IDEA](docs/run_config.png "Run/Debug Configuration on Intellij IDEA")
<sub>In this example, I'm using the Intellij IDEA IDE.</sub>

If you don't have an IDE configured, you can use the following commands in your
terminal:

```shell
mvn clean
mvn install
cd application/
mvn spring-boot:run
```
<sub>note: Make sure you navigate to the application module before running spring
boot.</sub>

<a id="test"></a>
### Test The Application

If you followed the steps above correctly, you'll be able to access the GraphQL
playground locally through [http://localhost:8080/playground](http://localhost:8080/v1/playground) :tada:

<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>






<a id="mutations-and-queries"></a>
## Explaining The Mutations and Queries
<a id="mutations"></a>
### Mutations
___
<a id="importLeague"></a>
#### importLeague
**Receives**: **leagueCode** String as argument.
<br />
<sub>note: you can use the [availableLeaguesForSync](#availableLeaguesForSync) query to see a list of available League codes that can be used</sub>

**Sample request**
```graphql
mutation ImportLeague {
  importLeague(leagueCode: "WC") {
    id
    code
    name
  }
}
```
**Returns**: Competition object with the complete tree of content that has been imported to the database.

**Sample response**
```json
{
  "data": {
    "importLeague": {
      "code": "WC",
      "id": "2000",
      "name": "FIFA World Cup"
    }
  }
}
```
___
<a id="queries"></a>
### Queries
___
<a id="availableLeaguesForSync"></a>
#### availableLeaguesForSync

**Sample request**
```graphql
query AvailableLeagues {
    availableLeaguesForSync {
        code
        name
    }
}
```
**Returns** a list of available Leagues that can be imported using the 
[importLeague](#importLeague) Mutation

**Sample response**
```json
{
  "data": {
    "availableLeaguesForSync": [
      {
        "code": "WC",
        "name": "FIFA World Cup"
      },
      {
        "code": "CL",
        "name": "UEFA Champions League"
      }
    ]
  }
}
```

___
<a id="findPlayers"></a>
#### findPlayers
**Receives**: **leagueCode** String as argument and **teamName** (optional).
<br />
<sub>note: The league provided has to be already synced, otherwise it will not return
the expected result.</sub>
**Sample request**
```graphql
query FindPlayers {
    findPlayers(leagueCode: "CL", teamName: "Arsenal") {
        id
        person {
            dateOfBirth
            gamePosition
            id
            name
            nationality
        }
    }
}
```
**Returns**: A List of players.

<a id="playersResponse"></a>
**Sample response**
```json
{
  "data": {
    "findPlayers": [
      {
        "id": "4832",
        "person": {
          "dateOfBirth": "1995-09-15",
          "gamePosition": "Goalkeeper",
          "id": "4707",
          "name": "David Raya",
          "nationality": "Spain"
        }
      },
      {
        "id": "5530",
        "person": {
          "dateOfBirth": "1998-05-14",
          "gamePosition": "Goalkeeper",
          "id": "4890",
          "name": "Aaron Ramsdale",
          "nationality": "England"
        }
      }
      // so it goes...
    ]
  }
}
```
___
<a id="findTeam"></a>
#### findTeam
**Receives**: **teamName** String as argument and **sortPlayers** (optional)

**Sample request**
```graphql
query FindTeamInformation {
    findTeam(teamName: "Brazil", sortPlayers: true) {
        id
        name
        shortName
        tla
        address
        area {
            code
            flag
            id
            name
        }
        coach {
            id
            person {
                name
                dateOfBirth
                nationality
            }
        }
        players {
            id
            person {
                name
                nationality
                dateOfBirth
                gamePosition
            }
        }
    }
}
```
**Returns**: A Team object with a list of players, sorted or unsorted depending on the sortPlayers argument.

<a id="teamResponse"></a>
**Sample response**
```json
{
  "data": {
    "findTeam": {
      "id": "764",
      "name": "Brazil",
      "shortName": "Brazil",
      "tla": "BRA",
      "address": "Rua Victor Civita 66 Bloco 1, Edifico 5 Rio de Janeiro, RJ 22775-044",
      "area": {
        "code": "BRA",
        "flag": "https://crests.football-data.org/764.svg",
        "id": "2032",
        "name": "Brazil"
      },
      "coach": {
        "id": "11165",
        "person": {
          "name": "Fernando Diniz",
          "dateOfBirth": "1974-03-27",
          "nationality": "Brazil"
        }
      },
      "players": [
        {
          "id": "2028",
          "person": {
            "name": "Alex Sandro",
            "nationality": "Brazil",
            "dateOfBirth": "1991-01-26",
            "gamePosition": "Defence"
          }
        },
        {
          "id": "15904",
          "person": {
            "name": "Alex Telles",
            "nationality": "Brazil",
            "dateOfBirth": "1992-12-15",
            "gamePosition": "Defence"
          }
        },
        {
          "id": "1795",
          "person": {
            "name": "Alisson",
            "nationality": "Brazil",
            "dateOfBirth": "1992-10-02",
            "gamePosition": "Goalkeeper"
          }
        },
        {
          "id": "97085",
          "person": {
            "name": "Antony",
            "nationality": "Brazil",
            "dateOfBirth": "2000-02-24",
            "gamePosition": "Offence"
          }
        }
        // so it goes...
      ]
    }
  }
}
```
___
<a id="searchPlayer"></a>
#### searchPlayer
An alternative way of searching for a player, using instead the name of the
player as Argument, and returning a list of players as the result.

This can be used as an auto-complete, given a *like* sql query was used.

**Receives**: **playerName** String as Argument.

**Sample request**
```graphql
query SearchPlayer {
    searchPlayer(playerName: "An") {
        id
        person {
            name
            dateOfBirth
            gamePosition
            nationality
        }
    }
}
```
**Returns**: A list of players (same as in [findPlayers](#findPlayers))
___
<a id="searchTeam"></a>
#### searchTeam
An alternative way of searching for a team, using instead the name of the
team as Argument, and returning a list of team as the result.

This can be used as an auto-complete, given a *like* sql query was used.

**Receives**: **teamName** String as Argument.

**Sample request**
```graphql
query SearchTeam {
    searchTeam(teamName: "Ar") {
        id
        name
        shortName
        tla
        address
    }
}
```
**Returns**: A list of teams (same object as in [findTeam](#findTeam) - but listed)
___

<a id="conclusion"></a>
## Conclusion And Final Considerations

I sure hope you can see that I'm very passionate about the work I do and I hope it shows in my code :blush:

This project was started on 2023-12-15, and given it's short time I wasn't able to finish the unit tests, other than that, I'm very proud of it :heart_eyes:

If you have any questions, feel free to reach out on any of my socials:

<a href="https://linkedin.com/in/cmdrlias/"><img align="left" src="docs/linkedin.png" alt="Larissa Silva | LinkedIn" width="21px"/></a>
<a href="https://twitter.com/nickeldumbb"><img align="left" src="docs/twitter.png" alt="nickeldumbb | Twitter" width="21px"/></a>
<a href="https://instagram.com/larssslv"><img align="left" src="docs/instagram.png" alt="larssslv | Instagram" width="21px"/></a>
<a href="https://twitch.tv/nickeldumb"><img align="left" src="docs/twitch.png" alt="larssslv | twitch" width="21px"/></a>

<br />
<sub>This and other Projects are available on my github page!</sub>

[![GitHub](https://img.shields.io/badge/github-%23121011.svg?style=for-the-badge&logo=github&logoColor=white)](https://github.com/laasilva)
