# retail-manager
A small prototype of a retail shops manager API

## Usage
To run the tests:

`./gradlew test`

To run the application:

`GOOGLE_MAPS_API_KEY=<here-your-api-key> ./gradlew bootRun`

To create a shop:
```
curl -XPOST http://localhost:8080/shops -H "Content-Type: application/json" -d '
{
	"shopName": "shop Name",
	"shopAddress": {
		"number": 123,
		"postcode": "SW15 1RS"
	}
}
'
``` 
