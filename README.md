# 🍕 Food Delivery Service API

RESTful API для сервиса доставки еды с полным циклом заказа — от выбора ресторана до доставки и отзывов.

## 🚀 Возможности

- 📦 Управление заказами в реальном времени
- 🏪 Каталог ресторанов и меню
- 🛒 Умная корзина покупок
- 💌 Система уведомлений
- ⭐ Система отзывов и рейтингов
- 🚗 Управление курьерами
- 💳 Интеграция платежей
- 👤 Управление пользователями

## 📚 API Endpoints

### 👥 Пользователи

| Метод | Endpoint | Описание |
|-------|----------|----------|
| `POST` | `/users` | Регистрация нового пользователя |
| `GET` | `/users/{id}` | Получить профиль пользователя |
| `PUT` | `/users/{id}` | Обновить профиль пользователя |
| `GET` | `/users?role=...` | Фильтр пользователей по роли |
| `DELETE` | `/users/{id}` | Деактивировать пользователя |

### 🏪 Рестораны и меню

| Метод | Endpoint | Описание |
|-------|----------|----------|
| `POST` | `/restaurants` | Создать новый ресторан |
| `GET` | `/restaurants` | Список ресторанов (фильтры: кухня, рейтинг) |
| `GET` | `/restaurants/{id}` | Детальная информация о ресторане |
| `PUT` | `/restaurants/{id}` | Обновить информацию о ресторане |
| `DELETE` | `/restaurants/{id}` | Закрыть ресторан |
| `POST` | `/restaurants/{id}/menu` | Добавить блюдо в меню |
| `GET` | `/restaurants/{id}/menu` | Получить меню ресторана |
| `PUT` | `/menu/{id}` | Обновить информацию о блюде |
| `DELETE` | `/menu/{id}` | Убрать блюдо из меню |
| `PATCH` | `/menu/{id}/availability` | Изменить доступность блюда |

### 🛒 Корзина покупок

| Метод | Endpoint | Описание |
|-------|----------|----------|
| `POST` | `/cart/items` | Добавить блюдо в корзину |
| `DELETE` | `/cart/items/{id}` | Удалить блюдо из корзины |
| `PUT` | `/cart/items/{id}` | Изменить количество блюда |
| `GET` | `/cart` | Получить текущую корзину пользователя |
| `DELETE` | `/cart` | Очистить корзину |

### 📦 Заказы

| Метод | Endpoint | Описание |
|-------|----------|----------|
| `POST` | `/orders` | Создать заказ из корзины |
| `GET` | `/orders/{id}` | Получить информацию о заказе |
| `GET` | `/orders?userId=...` | Заказы конкретного пользователя |
| `PATCH` | `/orders/{id}/status` | Изменить статус заказа |
| `DELETE` | `/orders/{id}` | Отменить заказ |
| `GET` | `/orders?status=...` | Фильтр заказов по статусу |

### 🚗 Курьеры

| Метод | Endpoint | Описание |
|-------|----------|----------|
| `POST` | `/couriers` | Зарегистрировать курьера |
| `GET` | `/couriers` | Список всех курьеров |
| `PATCH` | `/couriers/{id}/assign?orderId=...` | Назначить заказ курьеру |
| `GET` | `/couriers/{id}/orders` | Активные заказы курьера |
| `PATCH` | `/couriers/{id}/status` | Изменить статус курьера (ONLINE/OFFLINE) |

### 💳 Платежи

| Метод | Endpoint | Описание |
|-------|----------|----------|
| `POST` | `/payments` | Создать платёж для заказа |
| `GET` | `/payments/{id}` | Получить статус платежа |
| `PATCH` | `/payments/{id}/status` | Обновить статус платежа |
| `GET` | `/payments?orderId=...` | Платежи по конкретному заказу |

### ⭐ Отзывы

| Метод | Endpoint | Описание |
|-------|----------|----------|
| `POST` | `/reviews` | Оставить отзыв о ресторане |
| `GET` | `/reviews?restaurantId=...` | Отзывы о конкретном ресторане |
| `PUT` | `/reviews/{id}` | Обновить отзыв |
| `DELETE` | `/reviews/{id}` | Удалить отзыв |

### 💌 Уведомления

| Метод | Endpoint | Описание |
|-------|----------|----------|
| `POST` | `/notifications` | Создать уведомление |
| `GET` | `/notifications?userId=...` | Уведомления пользователя |
| `PATCH` | `/notifications/{id}/status` | Обновить статус уведомления |

## 🛠 Технологии

- **Java 17+** - основной язык разработки
- **Spring Boot 3** - фреймворк
- **Spring Data JPA** - работа с базой данных
- **Hibernate** - ORM
- **MySQL/PostgreSQL** - базы данных
- **Maven/Gradle** - сборка проекта
- **Spring Security** - аутентификация и авторизация
- **Spring Validation** - валидация данных

## 🚀 Быстрый старт

### Требования
- Java 17+
- Maven 3.6+
- MySQL 8.0+ или PostgreSQL 14+

### Установка

1. Клонировать репозиторий:

git clone https://github.com/RedTosS39/FoodDeliveryService.git
cd food-delivery-service
```

2. Настройка базы данных:
```bash
# Для MySQL
mysql -u root -p -e "CREATE DATABASE food_delivery_db;"

# Для PostgreSQL  
createdb food_delivery_db
```

3. Настройка конфигурации:
```properties
# application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/food_delivery_db
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

4. Запуск приложения:
```bash
mvn spring-boot:run
```

Приложение будет доступно по адресу: `http://localhost:8080`

## 📋 Примеры запросов

### Регистрация пользователя
```http
POST /users
Content-Type: application/json

{
  "username": "john_doe",
  "email": "john@example.com",
  "password": "securepassword123",
  "phone": "+1234567890"
}
```

### Создание заказа
```http
POST /orders
Content-Type: application/json
Authorization: Bearer <token>

{
  "restaurantId": 1,
  "items": [
    {
      "menuItemId": 5,
      "quantity": 2
    }
  ],
  "deliveryAddress": "ул. Примерная, д. 123"
}
```

### Добавление отзыва
```http
POST /reviews
Content-Type: application/json
Authorization: Bearer <token>

{
  "restaurantId": 1,
  "rating": 5,
  "comment": "Отличная еда и быстрая доставка!",
  "orderId": 42
}
```

## 🔐 Аутентификация

API использует JWT-токены для аутентификации. Добавьте токен в заголовок:
```
Authorization: Bearer <your_jwt_token>
```

## 📊 Статусы заказов

- `PENDING` - Ожидает подтверждения
- `CONFIRMED` - Подтверждён
- `PREPARING` - Готовится
- `READY` - Готов к доставке
- `DELIVERING` - В процессе доставки
- `DELIVERED` - Доставлен
- `CANCELLED` - Отменён

## 🤝 Contributing

1. Форкните репозиторий
2. Создайте feature branch: `git checkout -b feature/amazing-feature`
3. Сделайте коммит: `git commit -m 'Add amazing feature'`
4. Запушьте ветку: `git push origin feature/amazing-feature`
5. Создайте Pull Request

## 📄 Лицензия

Этот проект лицензирован под MIT License - смотрите файл [LICENSE](LICENSE) для деталей.

## 📞 Поддержка

Если у вас есть вопросы или предложения, создайте issue в репозитории или напишите на email: support@fooddelivery.com

---

**Food Delivery Service** · Доставляем вкусное настроение! 🍔🚀
