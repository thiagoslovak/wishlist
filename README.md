# Wishlist

### Serviço desenvolvido utilizando:

- Java 11
- Spring
- Maven
- MongoDb
- Keycloak
- Docker

### Instruções de uso da aplicação:

1. Importe a collection que se encontra no path wishlist/collection no Postman.
2. Para se usar a aplicação deverá subir dois containers, um do mongoDb e outro do Keycloak.
    1. Para subir o container do Keycloack use:
        - ```docker run -p 9990:8080 --name keycloak-wishlist -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:18.0.0 start-dev  ```
    2. Para subir o mongoDb no path princial \wishlist\ use:
        - ``docker-compose up``
3. Com o container do Keycloak e do mongoDb rodando, apenas dar RUN na IDE que irá subir a aplicação.
4. Com tudo rodando, para se utilizar  a aplicação tem que pegar o token no Keycloak, para isso:
    1. Import o [realm](https://github.com/thiagoslovak/wishlist/tree/master/keycloak) no Keycloak indo em:
        - Dado que esteje no link: http://localhost:9990/, então vá em "Administration Console", então coloque o usuario e senha que é "admin", então para falicitar vá até o path: http://localhost:9990/admin/master/console/#/create/realm para adicionar o realm, então clique em "Select file" e então vá no projeto no path wishlist\keycloak que o realm estará lá, então clique em "Create".
        - Com o realm criado, acesse o menu esquerdo em "Clients", então procure na lista por "wishlist", então acesse no meu "credentials", então cliquem em "Regenerate Secret", então copie essa Secred e vá no Postman na pasta token, e gere o token de acordo com o print:
            - ![print1](https://github.com/thiagoslovak/wishlist/blob/master/prints/Screenshot_1.png)
        - Após clicar em "Get new access token", copie o token conforme o print:
            - ![print2](https://github.com/thiagoslovak/wishlist/blob/master/prints/Screenshot_2.png)
5. Com o token gerado, para fazer as requisições tem que colocar o token em no meu "Authorization", então coloque o "Type" e então cole o Token, conforme o print:
    - ![print3](https://github.com/thiagoslovak/wishlist/blob/master/prints/Screenshot_3.png)
6. Depois de tanto trabalho kk, não desista. Para se utilizar a aplicação via Postman o ideal seria:
    - Criar os Produtos, depois criar os clientes, depois criar a Wishlist com os Produtos e o Cliente.
7. Seguindo as instruções do teste seria:
    - Adicionar um produto na Wishlist do cliente:
        - Para cria uma Wishlist:
            - Foi criado um end point onde é possivel criar Wishlist sem o cliente e sem os produtos.
            - Criar a Wishlist com o cliente e sem os produtos.
            - E também criar a Wishlist com o cliente e com os produtos.
            - ![print4](https://github.com/thiagoslovak/wishlist/blob/master/prints/Screenshot_4.png)

        - Para adicionar um produto na Wishlist do cliente:
            - Dado que eu tenha uma Wishlist, ir no wishlist/PUT - updating e passar a Wishlist, com os produtos e com o cliente.
            - ![print](https://github.com/thiagoslovak/wishlist/blob/master/prints/Screenshot_5.png)

    - Remover um produto da Wishlist do cliente:
        - Para remover um produto vá em wishlist/PUT - updating e passar a Wishlist, com os produtos que deseja que fique e com o cliente.
    - Consultar todos os produtos da Wishlist do cliente:
        - Temos os wishlist/findingById e wishlist/findingAllWishlist
    - Consultar se um determinado produto está na Wishlist do cliente:
        - Para isso vá em wishlist/findByProductsAndClient e passe o id do cliente e do produto no parms que ele irá buscar uma Wishlist que tem o produto e o clinte informado, conforme o print:
        - ![print6](https://github.com/thiagoslovak/wishlist/blob/master/prints/Screenshot_6.png)

    ### Algumas Observações
   - Wishlist:
     - Não é possível criar duas Wishlist com mesmo nome.
     - As Wishlist também tem um index unico para o Cliente então cada Cliente tem apenas uma Wishlist.
     - A Wishlist tem um limite de 20 produtos.
   - Cliente:
     - Não é permitido criar dois Cliente com o mesmo CPF.
   - Produtos:
     - Não é permitido criar dois Produtos com o mesmo nome.   
