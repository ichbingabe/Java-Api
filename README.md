## Spring Boot Rest API
Api para gerenciamento de estacionamento de condominios.

#### Estrutura do Projeto

| Nome       | Descrição                                                            |
|------------|----------------------------------------------------------------------|
| model      | classes de modelo da aplicação                                       |
| dto        | camada de tráfego de dados da aplicação                              |
| repository | recursos de acesso a dados em uma base relacional da aplicação       |
| service    | camada de regra de negócios da aplicação                             |
| controller | serviços http baseados na arquitetura REST                           |
| Projections| define entidades e colunas que são retornadas por uma query          |


#### Funcionalidades
1. Cadastra um carro a uma vaga
```
POST: http://localhost:8080/parking-spot
```
```
{
	"parkingSpotNumber":"0121",
	"carModel":
		{
			"licensePlateCar":"mpq9981",
			"brandCar":"Fiat",
			"colorCar":"Branco desbotado",
			"modelCar":"Uno de firma com escada",
			"yearCar":"1998"
		},
	"responsibleName":"Rodrigo",
	"apartment":"204",
	"block":"c"
}
```

2. Listagem das vagas ocupadas
```
GET: http://localhost:8080/parking-spot
```

3. Listagem de uma vaga
```
GET: http://localhost:8080/parking-spot/id
```

4. Exclusão de Vaga
```
DELETE: http://localhost:8080/parking-spot/id
```

5. Alteração de vaga
```
PUT: http://localhost:8080/parking-spot/id
```
```
{
	"parkingSpotNumber":"0121",
	"carModel":
		{
			"licensePlateCar":"mpq9981",
			"brandCar":"Fiat",
			"colorCar":"Branco desbotado",
			"modelCar":"Uno de firma com escada",
			"yearCar":"1998"
		},
	"responsibleName":"Pedro",
	"apartment":"204",
	"block":"d"
}
```

6. Busca de um Carro
```
GET: http://localhost:8080/parking-spot/responsible-name/name/car
```

7. Alteração de um carro
```
PUT: http://localhost:8080/parking-spot/responsible-name/name/car
```
```
{
	"parkingSpotNumber":"0121",
	"carModel":
		{
			"licensePlateCar":"kpq2001",
			"brandCar":"Ferrari",
			"colorCar":"vermelho",
			"modelCar":"LaFerrari",
			"yearCar":"2020"
		},
	"responsibleName":"Pedro",
	"apartment":"204",
	"block":"d"
}
```

#### Referências
* https://github.com/glysns/my-friends-rest-api
* https://spring.io/guides/tutorials/rest/
* https://github.com/MichelliBrito/parking-control-api

