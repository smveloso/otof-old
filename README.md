# otof-old
Original otof project from bitbucket.

## Projetos relacionados

https://github.com/smveloso/otof-h2

## Operação

* Criar o banco 

`mvn -e -Pcreatedb -Pprod compile`

* Testes de integração

`mvn -e -Pdbtest clean verify`

* Rodar em produção

`mvn -e -Pprod clean package exec:exec`
