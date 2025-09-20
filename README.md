# 🍔 Food Delivery Service API

Микросервис для доставки еды с полным циклом от заказа до доставки. Основан на Spring Boot с автоматической документацией Swagger/OpenAPI.

## 📚 API Документация

После запуска приложения полная документация доступна по адресу:  
**`http://localhost:8080/swagger-ui.html`**

## 🚀 Основные endpoints

### 👥 Пользователи (Profile API)
- **GET** `/users/{id}` - Получить профиль пользователя
- **PUT** `/users/{id}` - Обновить профиль  
- **GET** `/users` - Список всех пользователей
- **GET** `/users/?role={role}` - Фильтр по роли (BUYER/COURIER)
- **POST** `/users` - Создать нового пользователя
- **DELETE** `/users/{id}` - Деактивация пользователя

### 🏪 Рестораны (Restaurant API)
- **GET** `/restaurants` - Список всех ресторанов
- **GET** `/restaurants/{id}` - Детали ресторана
- **POST** `/restaurants` - Создать ресторан (с фильтрами по типу и рейтингу)
- **PUT** `/restaurants/{id}` - Обновить ресторан
- **DELETE** `/restaurants/{id}` - Закрыть ресторан

### 🍽️ Меню (Dish API)
- **GET** `/restaurants/{id}/menu` - Получить меню ресторана
- **POST** `/restaurants/{id}/menu` - Добавить блюдо в меню
- **PUT** `/restaurants/{id}/menu` - Обновить блюдо
- **DELETE** `/restaurants/{id}/menu/{menu_id}` - Удалить блюдо

### 🛒 Корзина (Cart API)
- **GET** `/cart?personId={id}` - Получить корзину пользователя
- **POST** `/cart/items` - Добавить блюдо в корзину (restaurantId, dishId, userId)
- **PUT** `/cart/items/{id}` - Изменить количество (personId)
- **DELETE** `/cart/items/{id}` - Удалить блюдо из корзины

### 📦 Заказы (Order API)
- **GET** `/orders?userId={id}` - Заказы пользователя
- **GET** `/orders/{id}` - Детали заказа
- **POST** `/orders` - Создать заказ из корзины
- **PATCH** `/orders/{id}/status` - Обновить статус заказа (NEW/READY/CONFIRMED/DELIVERED/CANCELLED)

### 🚴 Курьеры (Courier API)
- **GET** `/couriers` - Список всех курьеров
- **GET** `/couriers/{id}` - Профиль курьера
- **POST** `/couriers` - Зарегистрировать курьера
- **PATCH** `/couriers/{id}/assign` - Назначить заказ курьеру (orderId)
- **PUT** `/couriers/{id}` - Обновить данные курьера
- **DELETE** `/couriers/{id}` - Удалить курьера

## 🎯 Статусы заказов
- `NEW` - Новый заказ
- `READY` - Готов к выдаче
- `CONFIRMED` - Подтвержден
- `DELIVERED` - Доставлен
- `CANCELLED` - Отменен

## 👤 Роли пользователей
- `BUYER` - Покупатель
- `COURIER` - Курьер

## 🏪 Типы ресторанов
- `FAST_FOOD` - Фастфуд
- `ASIAN` - Азиатская кухня
- `ITALIAN` - Итальянская кухня
- `TRADITIONAL` - Традиционная кухня

## 📦 Модели данных

### FoodDishDTO
```json
{
  "dishName": "string",
  "dishPrice": 0,
  "dishQuantity": 0,
  "sum": 0
}
```

### RestaurantDTO
```json
{
  "id": 0,
  "name": "string",
  "restaurantType": "FAST_FOOD",
  "rating": 0,
  "active": true
}
```

### FoodOrderDTO
```json
{
  "foodDishDTOList": [],
  "status": "NEW",
  "sum": 0
}
```

### ProfileDTO
```json
{
  "name": "string",
  "role": "BUYER",
  "status": "ONLINE",
  "updatedDate": "2024-01-01T00:00:00Z",
  "active": true
}
```

## 🛠️ Технологии

- **Java 17+** + Spring Boot 3.x
- **Spring Data JPA** + Hibernate
- **PostgreSQL** - реляционная база данных
- **Swagger/OpenAPI 3.1** - автоматическая документация
- **REST API** - JSON communication

## 📦 Установка и запуск

1. Клонировать репозиторий:
```bash
git clone https://github.com/your-username/food-delivery-service.git
```



## 🔄 Примеры запросов

### Создание заказа
```bash
POST /orders?id={userId}
```

### Добавление в корзину
```bash
POST /cart/items?restaurantId=1&dishId=5&userId=10
```

### Назначение курьера
```bash
PATCH /couriers/15/assign?orderId=42
```

---

**Разработано с использованием Spring Boot и Swagger для удобства интеграции** 🚀
