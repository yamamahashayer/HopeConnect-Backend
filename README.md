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
#### 1. Clone the repository:
```
git clone https://github.com/yamamahashayer/HopeConnect-Backend.git
```
#### 2. Install Dependencies:
```
mvn clean install
```
#### 3. Create The Database:
```
CREATE DATABASE hopeconnect_db;
```
#### 4. Update Database Configuration:
```
spring.datasource.url=jdbc:mysql://localhost:3306/advancesoft1
spring.datasource.username=your_mysql_username
spring.datasource.password=your_mysql_password
```
#### 5. Run The Application:
```
mvn spring-boot:run
```

<a name="coref"></a>
## 🚀 Main Features

### 🌐 User Management
- User registration, login, and profile management with JWT authentication.
- Role-based access control: Donor, Sponsor, Volunteer, Orphanage Manager, Admin.

### 🏡 Orphanage Management
- Add, update, and delete orphanages.
- Assign managers to orphanages.
- List orphans in each orphanage.

### 👥 Volunteer Activities Management
- Create, update, and assign volunteer activities.
- Track volunteer participation and hours.
- Manage activity status (Active, Completed, Cancelled).

### 🌐 Orphan Project Management
- Create and manage orphan support projects (Education, Healthcare).
- Track project progress, funding, and participation.

### 💰 Donations Management
- Create and manage donations (Money, Food, Clothes, Education).
- Secure payments with Stripe integration.
- Track donation status (Pending, Completed, Cancelled).
  
- ### 🌍 Sponsorship Management
- Create sponsorship programs for orphans.
- Track sponsorship status (Active, Expired).

### 🔑 Authentication Management
- Secure JWT-based authentication.
- Password reset via email (SendGrid).
- Role-based access control for secure APIs.

### 📧 Email Notifications (SendGrid)
- Real-time email notifications for donations, sponsorships, and activities.

### 💳 Payment System (Stripe)
- Secure payment processing via Stripe.
- Create and manage payment sessions.
- Track payment status (Pending, Completed, Failed).

### 📊 Reporting and Statistics
- Admin dashboard with real-time statistics
- Generate detailed reports for each section.

### 📝 Review System
- Allow users to review activities, projects, and services.
- Admin can monitor and manage reviews.

### 🚨 Emergency Campaigns
- Launch emergency donation campaigns.
- Notify donors and volunteers of urgent needs.

### 📍 Logistic Management
- Manage orphanage locations and activity locations.
- Track deliveries and services provided.

### 🛡️ Security and Privacy
- JWT Authentication for secure user sessions.
- Role-Based Access Control (RBAC).
- Secure HTTPS connections for API requests.

<a name="roles"></a>
## 👥 Roles:
- 👤 Donor: Can donate money, clothes, food.
- 👥 Sponsor: Can sponsor orphans.
- 👨‍⚕️ Volunteer: Can register for and participate in volunteer activities.
- 🏡 Orphanage Manager: Manages orphanages and orphans.
- 🔧 Admin: Manages the entire platform.




<a name="contact"></a>
## 📱 Contact
- Yamamah Ashayer - [yamamahashayer@gmail.com]
- Diala Shami
- Tasneem jawabrah
  



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
