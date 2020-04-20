# Repair Shop
This is an application for managing the repair-shop. It can handle any kind of repair shop for example phone, computer or bike.
It can help you with managing a stock and budget. Clients can also check their repair status.

## Motivation
My main objective is to create a free and open-source app for every kind of repair shop.
It will have an option for dynamically generate PDFs forms for clients which can save a lot of time.

## Note
This REST API has corresponding front-end based on Angular. You can find it here: [repair-shop-angular](https://github.com/marcinadd/repair-shop-angular).

## Getting Started
1. Clone repository
1. Import project to your favourite IDE
### Running via Docker
1. Make sure that profile is set to **docker** in application.properties
1. After changes run `./build_image.sh` in project root directory to build application image.
1. Run`docker-compose up` in project root directory and enjoy.

### Setup project manually
1. Make sure that profile is set to **development** in application.properties 
1. **Remember to edit application-development.properties**
    ```
    spring.datasource.url=jdbc:mysql://db_host:db_port/db_name
    spring.datasource.username=db_user
    spring.datasource.password=db_password
    ```
    Make sure that this user have full access to database
1. Build the app, run it and enjoy

## Prerequisites
* JRE ≥ 1.8
* MySQL 8.0 compatible database

## License
Repair Shop is licensed under MIT License.
