# 🛒 E-Commerce Microservices Platform

Une plateforme e-commerce complète basée sur une architecture **microservices** avec **Spring Cloud**, **Spring Security** et **JWT**.

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2-green)
![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-2023.0-blue)
![Docker](https://img.shields.io/badge/Docker-Compose-blue)
![JWT](https://img.shields.io/badge/Security-JWT-orange)

---

## 📋 Table des Matières

- [Architecture](#-architecture)
- [Services](#-services)
- [Prérequis](#-prérequis)
- [Installation & Lancement](#-installation--lancement)
- [Authentification JWT](#-authentification-jwt)
- [Endpoints API](#-endpoints-api)
- [Tests](#-tests)

---

## 🏗 Architecture

```
                                    ┌─────────────────┐
                                    │  Config Server  │
                                    │    (Port 8888)  │
                                    └────────┬────────┘
                                             │
                    ┌────────────────────────┼────────────────────────┐
                    │                        │                        │
            ┌───────▼───────┐       ┌───────▼───────┐       ┌───────▼───────┐
            │   Discovery   │       │    Gateway    │       │   Front API   │
            │    Service    │◄──────│    Service    │       │  (Port 8083)  │
            │  (Port 8761)  │       │  (Port 8080)  │       └───────┬───────┘
            └───────┬───────┘       └───────┬───────┘               │
                    │                       │                       │
            ┌───────┴───────────────────────┴───────────────────────┤
            │                                                       │
    ┌───────▼───────┐                                      ┌───────▼───────┐
    │    Product    │                                      │     Order     │
    │    Service    │                                      │    Service    │
    │  (Port 8081)  │                                      │  (Port 8082)  │
    └───────────────┘                                      └───────────────┘
```

### Flux d'authentification JWT

```
1. Client ──► POST /auth/login ──► Front API ──► Retourne JWT Token
2. Client ──► Request + Bearer Token ──► Gateway ──► Valide Token ──► Route vers Service
```

---

## 🔧 Services

| Service | Port | Description |
|---------|------|-------------|
| **Config Server** | 8888 | Serveur de configuration centralisée |
| **Discovery Service** | 8761 | Eureka - Registre des services |
| **Gateway Service** | 8080 | API Gateway avec filtrage JWT |
| **Front API** | 8083 | Agrégateur + Authentification JWT |
| **Product Service** | 8081 | Gestion des produits |
| **Order Service** | 8082 | Gestion des commandes |

---

## 📦 Prérequis

- **Docker** & **Docker Compose** installés
- **Java 17+** (optionnel, pour développement local)
- **Maven 3.8+** (optionnel, pour compilation locale)

---

## 🚀 Installation & Lancement

### Option 1 : Avec Docker (Recommandé)

```bash
# 1. Cloner le projet
git clone https://github.com/kaizokuni/real-estate-cloud.git
cd real-estate-cloud

# 2. Compiler le projet (si nécessaire)
docker run --rm -v ${PWD}:/app -w /app maven:3.8.7-eclipse-temurin-17 mvn clean install -DskipTests

# 3. Lancer tous les services
docker-compose up -d

# 4. Vérifier que tous les services sont démarrés
docker ps
```

### Option 2 : Sans Docker (Développement local)

```bash
# Compiler
mvn clean install -DskipTests

# Lancer dans l'ordre :
# Terminal 1 - Config Server
cd config-server && mvn spring-boot:run

# Terminal 2 - Discovery Service
cd discovery-service && mvn spring-boot:run

# Terminal 3+ - Autres services
cd gateway-service && mvn spring-boot:run
cd product-service && mvn spring-boot:run
cd order-service && mvn spring-boot:run
cd front-api && mvn spring-boot:run
```

### Vérification du démarrage

Attendez ~2 minutes puis vérifiez :
- **Eureka Dashboard** : http://localhost:8761
- Tous les services doivent apparaître comme `UP`

---

## 🔐 Authentification JWT

### Obtenir un Token

```bash
# PowerShell
$body = @{username='admin'; password='password'} | ConvertTo-Json
$token = (Invoke-RestMethod -Uri http://localhost:8083/auth/login -Method Post -Body $body -ContentType "application/json").token

# Bash / curl
curl -X POST http://localhost:8083/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"password"}'
```

**Réponse :**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6..."
}
```

### Utiliser le Token

```bash
# PowerShell
Invoke-RestMethod -Uri http://localhost:8080/products -Headers @{Authorization="Bearer $token"}

# Bash / curl
curl http://localhost:8080/products -H "Authorization: Bearer <votre_token>"
```

### Credentials par défaut

| Username | Password |
|----------|----------|
| `admin` | `password` |

---

## 📡 Endpoints API

### Authentification (Front API - Port 8083)

| Méthode | Endpoint | Description | Auth |
|---------|----------|-------------|------|
| POST | `/auth/login` | Obtenir un JWT token | ❌ |

### Via Gateway (Port 8080) - Protégé par JWT

| Méthode | Endpoint | Description | Auth |
|---------|----------|-------------|------|
| GET | `/products` | Liste des produits | ✅ JWT |
| GET | `/orders/**` | Commandes | ✅ JWT |
| GET | `/full-orders/**` | Commandes avec détails produits | ✅ JWT |

### Accès Direct (Sans Gateway)

| Service | Endpoint | Port |
|---------|----------|------|
| Front API | `/full-orders` | 8083 |
| Product Service | `/products` | 8081 |
| Order Service | `/orders/recent` | 8082 |

---

## 🧪 Tests

### Test 1 : Accès sans token (doit échouer)

```bash
curl http://localhost:8080/products
# Réponse: {"status":401,"error":"Unauthorized"}
```

### Test 2 : Login et accès avec token

```powershell
# 1. Obtenir le token
$body = @{username='admin'; password='password'} | ConvertTo-Json
$token = (Invoke-RestMethod -Uri http://localhost:8083/auth/login -Method Post -Body $body -ContentType "application/json").token

# 2. Accéder aux ressources protégées
Invoke-RestMethod -Uri http://localhost:8080/products -Headers @{Authorization="Bearer $token"}
```

### Test 3 : Accès direct à l'API d'agrégation

```bash
curl http://localhost:8083/full-orders
```

**Réponse attendue :**
```json
[
  {
    "id": 2,
    "clientId": 1,
    "productId": 2,
    "quantity": 1,
    "totalPrice": 2499.0,
    "date": "2025-12-24",
    "status": "PENDING",
    "productDetails": {
      "name": "MacBook Pro M3",
      "description": "Professional laptop with M3 chip",
      "category": "Electronics",
      "price": 2499.0
    }
  }
]
```

---

## 📁 Structure du Projet

```
real-estate-cloud/
├── config-server/          # Serveur de configuration
├── discovery-service/      # Eureka Server
├── gateway-service/        # API Gateway + JWT Filter
│   └── filter/
│       ├── AuthenticationFilter.java
│       ├── JwtUtil.java
│       └── RouteValidator.java
├── front-api/              # API d'agrégation + Auth
│   ├── security/
│   │   ├── JwtUtil.java
│   │   └── SecurityConfig.java
│   └── web/
│       ├── AuthController.java
│       └── FrontApiController.java
├── product-service/        # Service Produits
├── order-service/          # Service Commandes
├── config-repo/            # Fichiers de configuration YAML
└── docker-compose.yml      # Orchestration Docker
```

---

## ⚙️ Configuration

Les configurations sont centralisées dans le dossier `config-repo/` :

- `application.yml` - Configuration globale
- `discovery-service.yml` - Eureka Server
- `gateway-service.yml` - Routes et filtres JWT
- `product-service.yml` - Service Produits
- `order-service.yml` - Service Commandes
- `front-api.yml` - API Front

---

## 🛠 Technologies Utilisées

- **Spring Boot 3.2**
- **Spring Cloud 2023.0**
  - Spring Cloud Config
  - Spring Cloud Netflix Eureka
  - Spring Cloud Gateway
  - Spring Cloud OpenFeign
- **Spring Security**
- **JWT (JJWT 0.11.5)**
- **H2 Database** (en mémoire)
- **Docker & Docker Compose**
- **Maven**

---

## 👤 Auteur

Développé avec ❤️ pour démontrer une architecture microservices sécurisée.

---

## 📄 Licence

Ce projet est sous licence MIT.
