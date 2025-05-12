<a name="intro"></a>
## 🌟 About the Project
<strong>HopeConnect </strong>  is a backend API designed to facilitate donations, sponsorships, and support services for orphaned children in Gaza after the war. The platform connects donors, sponsors, and volunteers with orphanages and children in need, ensuring transparency, security, and efficiency in managing resources.
<br>
<br>
<br>


<details>
  <summary><h2>💳 Table of Contents<h2\></summary>
  <ol>
    <li><a href="#intro">Introduction (What's HopeConnect project?)</a></li>
    <li><a href="#bw">Built With</a></li>
    <li><a href="#gs">Getting Started</a></li>
    <li><a href="#coref">Main Features</a></li>
    <li><a href="#roles">Roles</a></li>
    <li><a href="#API">API Documentation</a></li>
    <li><a href="#demo">Demo</a></li>
    <li><a href="#contact">Contact</a></li>
  </ol>
</details>
 <br>
 <br>
 <br>


<a name="bw"></a>
## 🔨 Built With
* [![SpringBoot][Spring-boot]][SpringURL] <br>An open-source Java framework for creating stand-alone, production-grade applications.
* [![MySQL][MySQL]][MySQLURL] <br>A reliable, open-source relational database management system commonly used for storing and managing data in web applications.
* [![Postman][Postman]][PostmanURL] <br>A collaboration platform for designing, testing, and documenting APIs.
* [![Github][Github]][GithubURL] <br>A web-based platform for version control and collaboration using Git.
<br>
<br>



<a name="gs"></a>
## 🚀 Getting Started
### ⚙️ Running the project
#### To get started with the project:
##### 1. Clone the repository:
> [![Github][Github]][wewe]
>
> sh
> git clone https://github.com/yamamahashayer/HopeConnect-Backend.git
> 
##### 2. Install Dependencies
Make sure you have Maven installed. Run the following command to install the necessary dependencies:
>
> sh
> mvn clean install
> 
##### 3. Create The Database:
* Make sure MySQL is installed and running on your local machine.
* Create a new database for the project:
>
> sh
> CREATE DATABASE advancesoft1;
> 
* Update the application.properties or application.yml file in the src/main/resources directory with your MySQL database credentials (username and password).
>
> sh
> spring.datasource.url=jdbc:mysql://localhost:8090/advancesoft1
> spring.datasource.username=your_mysql_username
> spring.datasource.password=your_mysql_password
> 
##### 4. Run The Application:
>
> sh
> mvn spring-boot:run
> 
<br>
<br>
<br>




[MySQL]: https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white
[MySQLURL]: https://www.mysql.com/
[Spring-boot]: https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white
[SpringURL]: https://spring.io/projects/spring-boot
[GithubURL]: https://github.com/
[Postman]: https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white
[PostmanURL]: https://www.postman.com/
[wewe]: https://github.com/shahdyaseen/Advanced-Software.git
[JQuery-url]: https://jquery.com 
[Github]: https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=github&logoColor=white
