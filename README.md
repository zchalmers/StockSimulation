# Stock Trading Simulation Platform

A full-stack web application for simulating stock trading with real-time data scraping, portfolio management, and trading analytics. Built with Spring Boot backend and Webpack frontend, this platform allows users to practice trading strategies in a risk-free environment.

## Project Overview

This stock simulation platform provides:
- Real-time stock data scraping from FinViz
- Buy/sell signal detection and tracking
- Portfolio management and performance tracking
- User authentication and account management
- Historical trade analysis
- Profit/loss calculations

Users can simulate trading high-volume stocks, track their portfolio performance, and learn trading strategies without financial risk.

## Features

### Trading & Market Data
- 📈 **Real-Time Stock Data** - Scrapes latest stock information from FinViz
- 📊 **Buy/Sell Signals** - Automated signal detection
- 💹 **High Volume Stocks** - Focus on actively traded stocks
- 🔄 **Automatic Updates** - Scheduled data refresh
- 📉 **Price Tracking** - Historical price data

### Portfolio Management
- 💼 **Portfolio Dashboard** - View all holdings and performance
- 💰 **Virtual Trading** - Simulate buy/sell transactions
- 📊 **Performance Analytics** - Track gains/losses
- 📈 **Trade History** - Complete transaction log
- 💵 **Profit/Loss Calculation** - Real-time P&L tracking

### User Features
- 🔐 **User Authentication** - Secure login system
- 👤 **Account Management** - Personal trading accounts
- 📱 **Responsive UI** - Works on desktop and mobile
- 🎨 **Bootstrap Interface** - Clean, professional design

## Technologies Used

### Backend
- **Spring Boot 2.6.3** - Application framework
- **Spring Data JPA** - Database ORM
- **Spring Data REST** - REST repository exposure
- **Spring Web** - REST controllers
- **Thymeleaf** - Server-side templating
- **JSoup** - Web scraping library
- **MySQL** - Production database
- **Java 11** - Programming language

### Frontend
- **Webpack 4** - Module bundler
- **Bootstrap 3** - UI framework
- **Axios** - HTTP client
- **Toastify.js** - Notifications
- **JavaScript ES6** - Client-side logic

### Build Tools
- **Gradle** - Build automation
- **Yarn/NPM** - Frontend package management

### Additional
- **Spring DevTools** - Hot reload during development
- **Spring Scheduling** - Automated tasks
- **Async Processing** - Non-blocking operations

## Project Structure

```
StockSimulation/
├── Application/                           # Backend Spring Boot app
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/zchalmers/stocks/highVolumeStocks/
│   │   │   │       ├── HighVolumeStocksApplication.java  # Main app
│   │   │   │       ├── controller/                       # REST controllers
│   │   │   │       │   ├── StockController.java         # Stock endpoints
│   │   │   │       │   ├── PortfolioController.java     # Portfolio mgmt
│   │   │   │       │   ├── MakeMoneyController.java     # Trading logic
│   │   │   │       │   └── LoginController.java         # Auth
│   │   │   │       ├── service/                         # Business logic
│   │   │   │       │   ├── StockService.java
│   │   │   │       │   ├── FinVizScraper.java          # Web scraper
│   │   │   │       │   └── [Other services]
│   │   │   │       ├── repositories/                    # Data access
│   │   │   │       │   ├── model/                      # Entity models
│   │   │   │       │   └── [Repository interfaces]
│   │   │   │       └── Converter/                      # Data converters
│   │   │   └── resources/
│   │   │       ├── application.properties              # Config
│   │   │       ├── static/                             # Built frontend
│   │   │       └── templates/                          # Thymeleaf views
│   │   └── test/                                       # Unit tests
│   └── build.gradle                                    # Backend build
│
├── Frontend/                                           # Frontend app
│   ├── src/                                           # Source files
│   │   ├── mainPage.js                                # Entry point
│   │   └── [Other JS files]
│   ├── package.json                                   # NPM dependencies
│   ├── webpack.config.js                              # Webpack config
│   └── build.gradle                                   # Frontend build
│
├── yaml/                                              # Configuration files
├── build.gradle                                       # Root build config
├── settings.gradle                                    # Gradle settings
├── gradlew                                           # Gradle wrapper
└── README.md
```

## Installation & Setup

### Prerequisites

1. **Java Development Kit (JDK) 11+**
   - Download from [Oracle](https://www.oracle.com/java/technologies/downloads/) or [OpenJDK](https://openjdk.org/)
   - Verify: `java -version`

2. **MySQL** (for production) or use embedded H2 for development
   - Download from [MySQL Downloads](https://dev.mysql.com/downloads/)

3. **Node.js & NPM/Yarn** (for frontend)
   - Download from [Node.js](https://nodejs.org/)
   - Yarn: `npm install -g yarn`

4. **Gradle** (optional, uses wrapper)
   - Gradle wrapper included (`./gradlew`)

### Setup Instructions

1. **Clone the Repository**
   ```bash
   git clone https://github.com/zchalmers/StockSimulation.git
   cd StockSimulation
   ```

2. **Configure Database**
   
   Create MySQL database:
   ```sql
   CREATE DATABASE stock_simulation;
   ```

   Update `Application/src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/stock_simulation
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   spring.jpa.hibernate.ddl-auto=update
   ```

3. **Install Frontend Dependencies**
   ```bash
   cd Frontend
   yarn install
   # or: npm install
   ```

4. **Build the Project**
   ```bash
   # From root directory
   ./gradlew build
   ```

   This will:
   - Build the frontend (Webpack)
   - Copy frontend dist to backend static resources
   - Compile backend Java code
   - Run tests
   - Create executable JAR

5. **Run the Application**
   ```bash
   ./gradlew :Application:bootRun
   ```

   Or run the JAR:
   ```bash
   java -jar Application/build/libs/Application-0.0.1-SNAPSHOT.jar
   ```

6. **Access the Application**
   - Open browser to: `http://localhost:8080`

## Usage

### Creating an Account

1. Navigate to the login page
2. Click "Register" or "Sign Up"
3. Enter your credentials
4. Create your account

### Simulating Trades

1. **View Stock List**
   - Browse available stocks
   - See buy/sell signals
   - Check current prices

2. **Execute Trade**
   - Click "Buy" or "Sell" on a stock
   - Enter number of shares
   - Confirm transaction

3. **Monitor Portfolio**
   - View all holdings
   - See profit/loss per stock
   - Track total portfolio value

### Updating Stock Data

The application automatically scrapes latest stock data on a schedule, or manually trigger:
- Navigate to admin panel (if implemented)
- Click "Refresh Stock Data"
- Data pulled from FinViz

## API Endpoints

### Stock Endpoints

**Get Stock Table Data**
```
GET /stocks/table/{option}
Parameters: option = "All" | "Buys" | "Sales"
Returns: List of StockRecord
```

**Save Stock Record**
```
POST /stocks
Body: StockRecord JSON
Returns: Success message
```

**Save Latest Stocks**
```
GET /stocks/saveAllLatest
Scrapes and saves latest buy/sell signals
Returns: Success message
```

### Portfolio Endpoints

```
GET /portfolio/{userId}
Returns: User's portfolio with holdings

POST /portfolio/buy
Body: { userId, stockSymbol, shares, price }
Returns: Updated portfolio

POST /portfolio/sell
Body: { userId, stockSymbol, shares, price }
Returns: Updated portfolio
```

### Authentication

```
POST /login
Body: { username, password }
Returns: User session token

POST /register
Body: { username, email, password }
Returns: New user account
```

## Configuration

### Application Properties

Edit `Application/src/main/resources/application.properties`:

```properties
# Server Configuration
server.port=8080

# Database
spring.datasource.url=jdbc:mysql://localhost:3306/stock_simulation
spring.datasource.username=root
spring.datasource.password=password

# JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Scheduling (for auto-refresh)
stock.update.cron=0 */30 * * * *  # Every 30 minutes
```

### Environment Profiles

**Development** (`application-local.properties`):
```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.h2.console.enabled=true
```

**Production** (`application-prod.properties`):
```properties
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASS}
```

## Development

### Running in Development Mode

```bash
# Run with local profile
./gradlew :Application:bootRunDev
```

### Frontend Development

```bash
cd Frontend

# Development server with hot reload
yarn start
# or: npm start

# Build for production
yarn build
# or: npm run build
```

### Running Tests

```bash
# All tests
./gradlew test

# Backend only
./gradlew :Application:test

# With coverage
./gradlew test jacocoTestReport
```

## Web Scraping

The application uses JSoup to scrape stock data from FinViz:

**FinVizScraper Service:**
- Scrapes buy/sell signals
- Extracts stock symbols, prices, volume
- Parses technical indicators
- Updates database with latest data

**Ethical Scraping:**
- Respects robots.txt
- Implements rate limiting
- Caches data to minimize requests
- For educational purposes only

## Troubleshooting

### Common Issues

**Database Connection Failed**
- Verify MySQL is running
- Check credentials in `application.properties`
- Ensure database exists: `CREATE DATABASE stock_simulation;`

**Frontend Not Loading**
- Run `yarn build` in Frontend directory
- Verify files copied to `Application/src/main/resources/static`
- Check browser console for errors

**Port Already in Use**
- Change port in `application.properties`: `server.port=8081`
- Or kill process: `lsof -ti:8080 | xargs kill -9`

**Build Failed**
- Clean build: `./gradlew clean build`
- Update dependencies: `./gradlew --refresh-dependencies`
- Check Java version: `java -version` (should be 11+)

**Scraper Not Working**
- Check internet connection
- Verify FinViz website structure hasn't changed
- Check for rate limiting or blocking

## Future Enhancements

- [ ] Real-time WebSocket price updates
- [ ] Advanced charting (TradingView integration)
- [ ] Technical analysis indicators (RSI, MACD, etc.)
- [ ] Watchlist functionality
- [ ] Multiple portfolio support
- [ ] Paper trading competition
- [ ] Mobile app (React Native or Flutter)
- [ ] Stop-loss and limit orders
- [ ] Dividend tracking
- [ ] Tax calculation for trades
- [ ] Social features (follow traders)
- [ ] Leaderboard/rankings
- [ ] Educational resources
- [ ] API for third-party integrations

## Deployment

### Docker Deployment

Create `Dockerfile`:
```dockerfile
FROM openjdk:11-jdk-slim
COPY Application/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

Build and run:
```bash
docker build -t stock-simulation .
docker run -p 8080:8080 stock-simulation
```

### Heroku Deployment

```bash
heroku create stock-simulation-app
heroku addons:create cleardb:ignite
git push heroku main
```

### AWS Deployment

1. Build JAR: `./gradlew build`
2. Upload to EC2 or Elastic Beanstalk
3. Configure RDS for MySQL
4. Set environment variables
5. Run application

## Legal & Disclaimer

⚠️ **Educational Purpose Only**
- This is a simulation platform for learning
- No real money is involved
- Not financial advice
- Consult a financial advisor for real trading

**Data Sources:**
- Stock data scraped from FinViz for educational purposes
- Respect website terms of service
- Use responsibly and ethically

## Contributing

Contributions welcome! Please:
1. Fork the repository
2. Create feature branch
3. Make changes with tests
4. Submit pull request

## License

This project is available for educational purposes.

## Contact

- **GitHub**: [@zchalmers](https://github.com/zchalmers)

## Acknowledgments

- Spring Boot team for excellent framework
- FinViz for stock data
- Bootstrap for UI components
- Open source community

## Related Projects

- [LearnSwiftBackend](https://github.com/zchalmers/LearnSwiftBackend) - Spring Boot API example
- [CSE360-TeamProject](https://github.com/zchalmers/CSE360-TeamProject) - Java application

---

**Happy (Simulated) Trading! 📈**
