#!/bin/bash

ENDPOINT_URL="http://localhost:8080/api/v1/auth/register"
USERNAME="cb-test-$(date +%s)"
JSON_PAYLOAD="{\"username\":\"$USERNAME\",\"email\":\"$USERNAME@example.com\",\"password\":\"password123\",\"firstName\":\"CB\",\"lastName\":\"Test\"}"

echo
echo "Зупиняємо Keycloak, щоб симулювати збій..."
docker-compose -f docker-compose-keycloak-local.yaml stop keycloak
echo "Чекаємо 5 секунд, поки сервіс зупиниться..."
sleep 5

echo
echo "Надсилаємо 12 запитів, щоб "розімкнути" вимикач (поріг 50% з 10)."
echo "Очікуємо, що перші ~5-6 запитів будуть ПОВІЛЬНИМИ (таймаут з'єднання),"
echo "а наступні будуть МИТТЄВИМИ (помилка 503)."
echo "-------------------------------------------------------------------"

for i in $(seq 1 12); do
    echo -n "Запит $i: "
    curl -s -m 5 -o /dev/null -w "HTTP %{http_code} | Час: %{time_total}s\n" \
         -X POST \
         -H "Content-Type: application/json" \
         -d "$JSON_PAYLOAD" \
         "$ENDPOINT_URL"
done

echo "-------------------------------------------------------------------"
echo "Вимикач має бути 'РОЗІМКНЕНИЙ' (Open) протягом 10 секунд."
echo "Надсилаємо ще один запит. Він має повернути 503 МИТТЄВО."

curl -s -m 5 -o /dev/null -w "HTTP %{http_code} | Час: %{time_total}s\n" \
     -X POST \
     -H "Content-Type: application/json" \
     -d "$JSON_PAYLOAD" \
     "$ENDPOINT_URL"

echo
echo "Тест завершено. Повертаємо Keycloak до роботи..."
docker-compose -f docker-compose-keycloak-local.yaml start keycloak