# SecondHand

SecondHand is a web application designed to facilitate the buying and selling of second-hand items. Users can list their products, browse available listings, and communicate with sellers to complete transactions.

## Features
- User authentication and authorization
- Listing second-hand products with images and descriptions
- Browsing and searching for items
- Contacting sellers via messages
- Responsive UI for a seamless experience on different devices

## Technologies Used
- **Frontend:** Angular, TypeScript, HTML, CSS
- **Backend:** Java Spring Boot, MySQL
- **Authentication:** JWT (JSON Web Token)
- **API Testing:** Postman, Swagger
- **Containerization:** Docker

## Installation & Setup

### Prerequisites
- Node.js and npm installed
- Angular CLI installed
- Java JDK 17+ installed
- MySQL installed and running
- Docker (optional, for containerized deployment)

### Backend Setup
1. Clone the repository:
   ```sh
   git clone https://github.com/ozanefeozdemir/secondhand.git
   cd secondhand/backend
   ```
2. Configure database connection in `application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/secondhand_db
   spring.datasource.username=root
   spring.datasource.password=your_password
   ```
3. Build and run the application:
   ```sh
   mvn clean install
   mvn spring-boot:run
   ```

### Frontend Setup
1. Navigate to the frontend directory:
   ```sh
   cd secondhand/frontend
   ```
2. Install dependencies:
   ```sh
   npm install
   ```
3. Start the development server:
   ```sh
   ng serve
   ```
4. Open the application in your browser: `http://localhost:4200`

## Usage
- Sign up or log in to your account.
- Create a new listing by uploading images and adding descriptions.
- Browse listings and search for specific items.
- Contact sellers to negotiate and finalize transactions.

## Contributing
Contributions are welcome! Feel free to fork the repository and submit a pull request.

## License
This project is licensed under the MIT License.

## Contact
For any inquiries or issues, please contact [ozanefeozdemir](https://github.com/ozanefeozdemir).

