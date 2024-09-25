# Microsserviço de Agendamento

<div>
  <img alt="Java" src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white">
  <img alt="SpringBoot" src="https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white">
  <img alt="PostgreSQL" src="https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white">
  <img alt="Docker" src="https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white">
</div>

## Descrição

Este projeto engloba um simples microsserviço para gerenciar o agendamento de manutenções em veículos.

## Instruções de instalação

### Pré-requisitos

Para instalar e usar o projeto tenha em sua máquina os seguintes requisitos:

- Git
- Java 17
- Docker

### Etapas de instalação

1. Crie uma pasta e clone o projeto para sua máquina:

```bash
  mkdir microsservicoAgendamento/
  cd microsservicoAgendamento/
  git clone git@github.com:rafaelmagalhaesguedes/MicrosservicoAgendamentos.git
```

2. Rodando o projeto com Docker:

Primeiro verifique se as portas 8080 (web service) e 5432 (postgres) não estão em uso em seu sistema.

Agora, suba os serviços docker com o comando abaixo

```bash
  docker compose up -d
```

## Instruções de uso

O serviço estará disponível na porta 8080 em seu localhost

1. Abra seu navegador ou API client e acesse http://localhost:8080/

## Tecnologias utilizadas

- Java 17

- Spring Boot

- Postgres

- Docker

- Swagger (Para documentação da API)

- IntelliJ (IDE de desenvolvimento)

- Beekeeper Studio (Interface para gerenciar banco de dados)

- Insomnia Client (API Client)

## Documentação

O projeto utiliza o Swagger para documentação dos endpoints, acesse [neste link](http://localhost:8080/swagger-ui/index.html#/).

## Proteção de dados

O projeto utiliza encriptação para os dados sensíveis da aplicação, para facilitar o processo utilizamos a biblioteca [JASYPT](https://mvnrepository.com/artifact/org.jasypt/jasypt).

## 📍 Principais Endpoints

Lista com os principais endpoints da aplicação

| Rota                                             | Descrição                                          
|--------------------------------------------------|-----------------------------------------------------
| <kbd>POST /customers</kbd>                       | Cria um novo cliente
| <kbd>POST /customers/{customerId}/vehicle</kbd>  | Cria um veículo associado a um cliente
| <kbd>POST /customers/{customerId}/schedule</kbd> | Cria um agendamento associado a um cliente
| <kbd>GET /customers/{customerId}/schedule</kbd>  | Lista os agendamento associados a um cliente
| <kbd>GET /customers/{customerId}</kbd>           | Busca os dados de um cliente
| <kbd>PUT /customers/{customerId}/address</kbd>   | Atualiza um endereço associado a um cliente
| <kbd>PUT /scheduling</kbd>                       | Atualiza o Status da reserva
| <kbd>GET /scheduling</kbd>                       | Lista os agendamentos com paginação
| <kbd>GET /scheduling/status</kbd>                | Lista agendamentos filtrados por status com paginação


## Cria um cliente
#### POST /customers

**REQUEST**
```json
{
  "name": "Rafa Guedes",
  "email": "rafa@email.com",
  "numberPhone": "31997785451",
  "document": "12345678910",
  "cep": "31510090"
}
```

**RESPONSE**
```json
{
  "id": 1,
  "name": "Rafa Guedes",
  "email": "rafa@email.com",
  "numberPhone": "31997785451",
  "document": "12345678910",
  "address": {
    "cep": "31510-090",
    "logradouro": "Rua Anita Garibaldi",
    "bairro": "Candelária",
    "localidade": "Belo Horizonte",
    "uf": "MG"
  },
  "vehicles": [],
  "schedules": []
}
```

## Cria um veículo associado a um cliente
#### POST /customers/{customerId}/vehicle

**REQUEST**
```json
{
  "licensePlate": "RWT9988",
  "model": "HB20",
  "make": "Hyundai",
  "year": 2022
}
```

**RESPONSE**
```json
{
  "id": 1,
  "licensePlate": "RWT9988",
  "model": "HB20",
  "make": "Hyundai",
  "year": 2022
}
```

## Cria um agendamento associado a um cliente
#### POST /customers/{customerId}/schedule

**REQUEST**
```json
{
  "dateSchedule": "2024-10-14T10:10:23.828952827",
  "descriptionService": "Manutenção preventiva e substituição da bomba d'água e válvula termostática."
}
```

**RESPONSE**
```json
{
  "id": 1,
  "dateSchedule": "2024-10-14T10:10:23",
  "descriptionService": "Manutenção preventiva e substituição da bomba d'água e válvula termostática.",
  "status": "PENDENTE",
  "customer": {
    "id": 1,
    "name": "Rafa Guedes",
    "email": "rafa@email.com",
    "numberPhone": "31997785451",
    "vehicles": [
      {
        "model": "HB20",
        "licensePlate": "RWT9988"
      }
    ]
  }
}
```
## Lista todos os agendamentos associados a um cliente
#### GET /customers/{customerId}/schedule

**RESPONSE**
```json
[
  {
    "id": 1,
    "dateSchedule": "2024-10-14T10:10:23",
    "descriptionService": "Manutenção preventiva e substituição da bomba d'água e válvula termostática.",
    "status": "PENDENTE",
    "customer": {
      "id": 1,
      "name": "Rafa Guedes",
      "email": "rafa@email.com",
      "numberPhone": "31997785451",
      "vehicles": [
        {
          "model": "HB20",
          "licensePlate": "RWT9988"
        }
      ]
    }
  },
  {
    "id": 2,
    "dateSchedule": "2024-10-13T10:00:00",
    "descriptionService": "Alinhamento, balancemanto e troca de pastilhas de freio.",
    "status": "PENDENTE",
    "customer": {
      "id": 1,
      "name": "Rafa Guedes",
      "email": "rafa@email.com",
      "numberPhone": "31997785451",
      "vehicles": [
        {
          "model": "HB20",
          "licensePlate": "RWT9988"
        }
      ]
    }
  }
]
```

## Busca um cliente por ID
#### GET /customers/{customerId}

**RESPONSE**
```json
{
  "id": 1,
  "name": "Rafael Guedes",
  "email": "rafa@email.com",
  "numberPhone": "31997785451",
  "document": "12345678910",
  "address": {
    "cep": "31510-090",
    "logradouro": "Rua Anita Garibaldi",
    "bairro": "Candelária",
    "localidade": "Belo Horizonte",
    "uf": "MG"
  },
  "vehicles": [
    {
      "id": 1,
      "licensePlate": "RWT9988",
      "model": "HB20",
      "make": "Hyundai",
      "year": 2022
    }
  ],
  "schedules": [
    {
      "id": 1,
      "dateSchedule": "2024-10-14T10:10:23",
      "descriptionService": "Manutenção preventiva e substituição da bomba d'água e válvula termostática.",
      "status": "PENDENTE"
    },
    {
      "id": 2,
      "dateSchedule": "2024-10-14T10:10:00",
      "descriptionService": "Alinhamento, balancemanto e troca de pastilhas de freio.",
      "status": "PENDENTE"
    }
  ]
}
```

## Atualiza um endereço associado a um cliente
#### PUT /customers/{customerId}/address

**REQUEST**
```json
{
  "cep": "45810-000",
  "logradouro": "Rua Carlos C Silva",
  "bairro": "Parque Ecológico",
  "localidade": "Porto Seguro",
  "uf": "BA"
}
```

**RESPONSE**
```json
{
  "id": 1,
  "cep": "45810-000",
  "logradouro": "Rua Carlos C Silva",
  "bairro": "Parque Ecológico",
  "localidade": "Porto Seguro",
  "uf": "BA"
}
```

## Atualiza o Status de um agendamento
#### PUT /scheduling/{scheduleId}

**REQUEST**
```json
{
  "status": "REALIZADO"
}
```

**RESPONSE**
```json
{
  "id": 1,
  "dateSchedule": "2024-10-14T10:10:23",
  "descriptionService": "Manutenção preventiva e substituição da bomba d'água e válvula termostática.",
  "status": "REALIZADO",
  "customer": {
    "id": 1,
    "name": "Rafa Guedes",
    "email": "rafa@email.com",
    "numberPhone": "31997785451",
    "vehicles": [
      {
        "model": "HB20",
        "licensePlate": "RWT9988"
      }
    ]
  }
}
```

# Licença

Livre para usar, estudar, brincar, trabalhar, etc.

# Developed By

Rafa Guedes
