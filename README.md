
<a name="intro"></a>
## 🌟 About the Project
<strong>HopeConnect</strong> is a backend API designed to facilitate donations, sponsorships, and support services for orphaned children in Gaza after the war. The platform connects donors, sponsors, and volunteers with orphanages and children in need, ensuring transparency, security, and efficiency in managing resources.
<br><br>

<details>
  <summary><h2>💳 Table of Contents</h2></summary>
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

<a name="bw"></a>
## 🔨 Built With
- **Spring Boot** - Java framework for creating scalable backend API.
- **MySQL** - Relational database management system.
- **Stripe** - Secure online payment processing.
- **SendGrid** - Email notifications and alerts.
- **Postman** - API testing and documentation.
- **GitHub** - Version control and collaboration.
<br><br>

<a name="gs"></a>
## 🚀 Getting Started
### ⚙️ Running the project
1. **Clone the Repository**:
```bash
git clone https://github.com/yamamahashayer/HopeConnect-Backend.git
```
2. **Install Dependencies**:
```bash
mvn clean install
```
3. **Create The Database**:
```bash
CREATE DATABASE advancesoft1;
```
4. **Configure Database Connection**:
```yaml
spring.datasource.url=jdbc:mysql://localhost:3306/advancesoft1
spring.datasource.username=your_mysql_username
spring.datasource.password=your_mysql_password
```
5. **Run The Application**:
```bash
mvn spring-boot:run
```

<a name="coref"></a>
## 🚀 Main Features 
- 🌐 **User Management:** Secure user registration, login, and role-based access (Admin, Donor, Sponsor, Volunteer, Orphanage Manager).
- 🏡 **Orphanage Management:** Manage orphanages, including adding, updating, listing, and assigning managers.
- 👥 **Volunteer Activities Management:** Create, assign, and track volunteer activities with real-time status updates.
- 🌐 **Orphan Project Management:** Create and manage support projects for orphans (education, healthcare, financial support).
- 💰 **Donations Management:** Secure and track donations (money, food, clothes) with Stripe integration.
- 🌍 **Sponsorship Management:** Manage orphan sponsorships, monitor status, and notify sponsors.
- 🔑 **Authentication Management:** Secure JWT-based authentication for user sessions.
- 📧 **Email Notifications:** Real-time email notifications (donations, sponsorship updates) via SendGrid.
- 💳 **Payment System:** Secure online payments with Stripe for donations and sponsorships.
- 📊 **Reporting and Statistics:** Visual dashboard for admins with donation and volunteer statistics.
- 📝 **Review System:** Allow users to review activities and services, with admin management.
- 🚨 **Emergency Campaigns:** Launch urgent donation campaigns with automated notifications.
- 📍 **Logistic Management:** Manage locations for orphanages and activities, including delivery tracking.
- 🛡️ **Security and Privacy:** JWT authentication, HTTPS for secure API access.

<a name="roles"></a>
## 👥 Roles:
- 👤 Donor: Can donate money, clothes, food.
- 👥 Sponsor: Can sponsor orphans.
- 👨‍⚕️ Volunteer: Can register for and participate in volunteer activities.
- 🏡 Orphanage Manager: Manages orphanages and orphans.
- 🔧 Admin: Manages the entire platform.

<a name="API"></a>
## 📝 API Documentation
Access the complete API documentation for HopeConnect here: [HopeConnect API Documentation](https://documenter.getpostman.com/view/42761338/2sB2qWG4BW#intro)


<a name="demo"></a>
## 📸 Demo
- 🚀 [View Demo]

<a name="contact"></a>
## 📱 Contact
- Yamamah Hashayer - [yamamahashayer@gmail.com]
- Diala Shami - [shamidyala@gmail.com]
- Tasneem Jawabrah 

<p align="center">
  <a href="https://github.com/yamamahashayer/HopeConnect-Backend/graphs/contributors">
    <img src="https://contrib.rocks/image?repo=yamamahashayer/HopeConnect-Backend" />
  </a>
</p>
