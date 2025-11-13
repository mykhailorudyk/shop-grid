#!/bin/bash

if [ -z "$1" ]; then
    echo "Помилка: Ви не передали JWT-токен як аргумент."
    echo "Використання: sh test_ratelimit.sh <ваш_токен>"
    exit 1
fi

TOKEN=$1

echo "Запускаю тест Rate Limiter'а (30 запитів)..."
for i in $(seq 1 30); do \
  curl -s -o /dev/null -w "%{http_code}\n" -H "Authorization: Bearer $TOKEN" http://localhost:8080/api/v1/products; \
done