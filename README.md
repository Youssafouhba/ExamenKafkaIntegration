# 🛍 Système de Gestion d'Inventaire et Commandes en Ligne

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.1-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Kafka](https://img.shields.io/badge/Kafka-Latest-blue.svg)](https://kafka.apache.org)
[![GraphQL](https://img.shields.io/badge/GraphQL-Latest-pink.svg)](https://graphql.org)
[![Docker](https://img.shields.io/badge/Docker-Latest-blue.svg)](https://www.docker.com)

## 📋 Table des Matières
- [Architecture](#-architecture)
- [Microservices](#-microservices)
- [Technologies](#-technologies)
- [Prérequis](#-prérequis)
- [Installation](#-installation)
- [Configuration](#-configuration)
- [API Documentation](#-api-documentation)
- [Monitoring](#-monitoring)
- [Tests](#-tests)
- [Contributing](#-contributing)

## 🏗 Architecture

Notre système est composé de trois microservices interconnectés :

![Architecture](architecture-diagram.svg)

### Communication entre Services
- **Products → Commands**: GraphQL
- **Commands → Notifications**: Kafka
- **Configuration**: Config Server centralisé

## 🔧 Microservices

### 1. Product Service (product-ms)
- Gestion du catalogue produits
- Base de données PostgreSQL
- API REST + GraphQL
- Opérations CRUD sur les produits

### 2. Order Service (order-ms)
- Gestion des commandes
- Base de données MongoDB
- Circuit Breaker (Resilience4j)
- Communication GraphQL avec product-ms
- Producteur Kafka pour les notifications

### 3. Notification Service (notification-ms)
- Gestion des notifications
- Base de données MongoDB
- Consommateur Kafka
- Système de notification asynchrone

## 💻 Technologies

- **Framework**: Spring Boot 3.2.1
- **Communication**:
  - GraphQL
  - Apache Kafka
  - REST APIs
- **Bases de données**:
  - PostgreSQL
  - MongoDB
- **Configuration**: Spring Cloud Config
- **Resilience**: Circuit Breaker (Resilience4j)
- **Containerisation**: Docker
- **Documentation**: SpringDoc OpenAPI
- **Tests**: JUnit 5, Testcontainers

## 📝 Prérequis

- JDK 17+
- Maven 3.8+
- Docker & Docker Compose
- Git

## 🚀 Installation

1. **Cloner le repository**
```bash
git clone https://github.com/your-org/inventory-order-system.git
cd inventory-order-system
```

2. **Lancer les services avec Docker Compose**
```bash
docker-compose up -d
```

3. **Vérifier les services**
```bash
docker-compose ps
```

## ⚙ Configuration

### Config Server
Le serveur de configuration centralise les propriétés des microservices :

```yaml
# application.yml exemple
spring:
  cloud:
    config:
      server:
        git:
          uri: https://github.com/your-org/config-repo
          default-label: main
```

### Circuit Breaker
Configuration Resilience4j pour order-ms :

```yaml
resilience4j:
  circuitbreaker:
    instances:
      productService:
        slidingWindowSize: 10
        failureRateThreshold: 50
        waitDurationInOpenState: 10000
```

### Kafka
Configuration des topics :

```yaml
kafka:
  bootstrap-servers: localhost:9092
  topics:
    order-notifications: order-events
```

## 📚 API Documentation

- Product Service: `http://localhost:8081/swagger-ui.html`
- Order Service: `http://localhost:8082/swagger-ui.html`
- Notification Service: `http://localhost:8083/swagger-ui.html`

### GraphQL Endpoint
```graphql
# Exemple de requête
query {
  product(id: "123") {
    name
    price
    stock
  }
}
```

## 📊 Monitoring

- **Métriques**: Prometheus + Grafana
- **Logs**: ELK Stack
- **Tracing**: Jaeger

## 🧪 Tests

```bash
# Exécuter tous les tests
mvn clean test

# Tests d'intégration
mvn verify -P integration-tests
```

## 🤝 Contributing

1. Fork le projet
2. Créer une branche (`git checkout -b feature/AmazingFeature`)
3. Commit les changements (`git commit -m 'Add AmazingFeature'`)
4. Push sur la branche (`git push origin feature/AmazingFeature`)
5. Ouvrir une Pull Request

## 📄 License

Ce projet est sous licence MIT. Voir le fichier [LICENSE](LICENSE) pour plus de détails.

## 📞 Contact

- **Email**: contact@your-org.com
- **Issues**: [GitHub Issues](https://github.com/your-org/inventory-order-system/issues)
- **Wiki**: [GitHub Wiki](https://github.com/your-org/inventory-order-system/wiki)

---

<div align="center">
  Made with ❤️ by Your Organization
</div>
