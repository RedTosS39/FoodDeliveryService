Структура API:

Пользователи
1. POST /users — регистрация пользователя.
2. GET /users/{id} — профиль пользователя.
3. PUT /users/{id} — обновление профиля.
4. GET /users?role=... — фильтр по роли.
5. DELETE /users/{id} — деактивация.

Рестораны и меню
7. POST /restaurants — создать ресторан.
8. GET /restaurants — список ресторанов с фильтрами по кухне и рейтингу.
9. GET /restaurants/{id} — детально.
10. PUT /restaurants/{id} — обновить ресторан.
11. DELETE /restaurants/{id} — закрыть ресторан.
12. POST /restaurants/{id}/menu — добавить блюдо.
13. GET /restaurants/{id}/menu — список блюд.
14. PUT /menu/{id} — обновить блюдо.
15. DELETE /menu/{id} — убрать блюдо.
16. PATCH /menu/{id}/availability — изменить доступность блюда

Корзина
18. POST /cart/items — добавить блюдо в корзину.
19. DELETE /cart/items/{id} — удалить блюдо.
20. PUT /cart/items/{id} — изменить количество.
21. GET /cart — текущая корзина пользователя.
22. DELETE /cart — очистить корзину.

Заказы
24. POST /orders — создать заказ из корзины.
25. GET /orders/{id} — получить заказ.
26. GET /orders?userId=... — заказы пользователя.
27. PATCH /orders/{id}/status — изменить статус (для ресторана/курьера).
28. DELETE /orders/{id} — отмена заказа.
29. GET /orders?status=... — фильтр по статусу.

Курьеры
31. POST /couriers — зарегистрировать курьера.
32. GET /couriers — список курьеров.
33. PATCH /couriers/{id}/assign?orderId=... — назначить заказ курьеру.
34. GET /couriers/{id}/orders — активные заказы курьера.
35. PATCH /couriers/{id}/status — (например, ONLINE/OFFLINE).

Платежи
37. POST /payments — создать платёж.
38. GET /payments/{id} — статус платежа.
39. PATCH /payments/{id}/status — обновить (например, для webhook).
40. GET /payments?orderId=... — платежи по заказу.

Отзывы
42. POST /reviews — оставить отзыв ресторану.
43. GET /reviews?restaurantId=... — отзывы о ресторане.
44. PUT /reviews/{id} — обновить отзыв.
45. DELETE /reviews/{id} — удалить отзыв.

Уведомления
47. POST /notifications — создать уведомление.
48. GET /notifications?userId=... — уведомления пользователя.
49. PATCH /notifications/{id}/status — обновить статус.
