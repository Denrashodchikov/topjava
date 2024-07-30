curl http://localhost:8080/topjava/rest/meals

curl http://localhost:8080/topjava/rest/meals/100007

curl -X POST -H 'Content-Type: application/json' -d '{"dateTime":"2024-07-30T14:00:00","description":"New desc", "calories":777}' http://localhost:8080/topjava/rest/meals/

curl -X DELETE http://localhost:8080/topjava/rest/meals/100008

curl -X PUT -H 'Content-Type: application/json' -d '{"dateTime":"2024-07-30T10:00:00","description":"Upd desc", "calories":100}' http://localhost:8080/topjava/rest/meals/100007

curl "http://localhost:8080/topjava/rest/meals/between?startDate=&endTime="

curl "http://localhost:8080/topjava/rest/meals/between?startDate=2020-01-30&endDate=2020-01-30"

curl "http://localhost:8080/topjava/rest/meals/between?startDate=2020-01-30&endDate=2020-01-30&startTime=13:00:00&endTime=19:00:00"