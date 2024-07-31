### API Calories management

#### Get all meals:
<pre>curl http://localhost:8080/topjava/rest/meals</pre>
#### Get meal with id:
<pre>curl http://localhost:8080/topjava/rest/meals/{id}</pre>
#### Create meal:
<pre>curl -X POST -H 'Content-Type: application/json' -d '{"dateTime":"2024-07-30T14:00:00","description":"New desc", "calories":777}' http://localhost:8080/topjava/rest/meals/</pre>
#### Delete meal with id:
<pre>curl -X DELETE http://localhost:8080/topjava/rest/meals/{id}</pre>
#### Update meal with id:
<pre>curl -X PUT -H 'Content-Type: application/json' -d '{"dateTime":"2024-07-30T10:00:00","description":"Upd desc", "calories":100}' http://localhost:8080/topjava/rest/meals/{id}</pre>
#### Get all meals with empty filter:
<pre>curl "http://localhost:8080/topjava/rest/meals/between?startDate=&endTime="</pre>
#### Get all meals with date filter:
<pre>curl "http://localhost:8080/topjava/rest/meals/between?startDate=2020-01-30&endDate=2020-01-30"</pre>
#### Get all meals with date and time filter:
<pre>curl "http://localhost:8080/topjava/rest/meals/between?startDate=2020-01-30&endDate=2020-01-30&startTime=13:00:00&endTime=19:00:00"</pre>