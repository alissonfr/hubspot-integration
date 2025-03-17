# Meetime + Hubspot Integration

## Requisitos

- [Java 21](https://openjdk.java.net/)
- [Docker](https://www.docker.com/get-started)
- [IDE de sua escolha](https://www.jetbrains.com/idea/) (opcional)

## Subindo o APP via IDE

## Opção 1: Subindo o App via IDE

Se você prefere trabalhar em um ambiente de desenvolvimento integrado, siga as etapas abaixo para executar o aplicativo diretamente na sua IDE.

### Passos:

1. **Abrir o projeto na sua IDE**:
  - Abra o projeto Spring Boot na sua IDE de sua escolha.

2. **Verifique as dependências**:
  - Certifique-se de que o arquivo `pom.xml` está configurado corretamente com todas as dependências necessárias. Para este exemplo, o Spring Boot é a única dependência necessária.

3. **Configuração de Java**:
  - Certifique-se de que o JDK 21 está configurado corretamente na sua IDE.

4. **Rodar o aplicativo**:
  - Localize a classe principal do Spring Boot, `HubSpotIntegrationApplication`. O método `main()` desta classe será usado para iniciar o aplicativo.
  - Clique em **Run** ou **Debug** na sua IDE.

5. **Acessar a aplicação**:
  - O aplicativo será iniciado na porta padrão `8090`. Acesse a aplicação no navegador em:
    ```
    http://localhost:8090
    ```

## Opção 2: Subindo o App via Docker

Para rodar o aplicativo Spring Boot em um container Docker sem a necessidade de configurar diretamente o ambiente local, você pode usar o Docker.

### Passos:

1. **Construir e rodar o container**:
  - No terminal, navegue até o diretório onde o arquivo `docker-compose.yml` está localizado e execute o seguinte comando para construir e rodar o container:

    ```bash
    docker-compose up -d
    ```

2**Acessar a aplicação**:
  - O Docker irá construir a imagem e iniciar o aplicativo. Após o processo de build ser concluído, o aplicativo estará acessível em:
    ```
    http://localhost:8090
    ```









### Passo 1: Criar um novo projeto Spring Boot

## Endpoints Implementados

### 1. **GET /v1/auth/oauth/authorize**
Retorna a URL de autorização para iniciar o fluxo OAuth com o HubSpot.

### 2. **GET /v1/auth/oauth/callback**
Recebe o código de autorização fornecido pelo HubSpot e o troca por um token de acesso.

### 3. **POST /v1/contacts**
Cria um novo contato no HubSpot, respeitando as políticas de rate limit.
- **Importante**: É necessário passar o token de acesso do request anterior para essa chamada!

### 4. **POST /v1/contacts/webhook**
Escuta eventos de "contact.creation" enviados pelo HubSpot e processa esses eventos.
- **Objetivo de Mock**: Ao processar os eventos, envia uma mensagem via WebSocket para a página HTML estática como confirmação visual.

### 5. **GET /v1/contacts (adicional)**
Retorna a lista de contatos do HubSpot.
- **Importante**: É necessário passar o token de acesso do request anterior para essa chamada!

## Tecnologias e Ferramentas Utilizadas

- **OpenFeign** para realizar requisições HTTP.
- **SLF4J** para rastreabilidade das requisições e logs.
- **Bucket4j** para controle de rate limiting: Inicialmente, considerei utilizar o Redis para essa funcionalidade, mas, para evitar a necessidade de instalar e configurar o Redis nas máquinas dos desenvolvedores ou criar um container Docker para isso, optei por usar o Bucket4j. Ele é uma solução simples, que funciona em memória e depende apenas de uma biblioteca, facilitando a implementação sem a necessidade de configurações extras.
   - **Vantagens**: Simples, rápido, não precisa de banco externo.
   - **Limitações**: Funciona apenas na memória da aplicação, não é distribuído.
- **SSL (Certificado de Segurança)** Para hospedar a API com HTTPS.
- **Google Cloud**: Utilizado para deployar o APP, testar e aprender sobre a plataforma, pois vi que vocês a utilizam 👀.

## Implementações e Considerações

- **Mascarando Secrets**:
   - A `clientId` e `secretID` são mascaradas tanto nos logs quanto nas exceções para garantir a segurança.

- **Rate Limiting**:
   - O HubSpot impõe um limite de 110 requisições a cada 10 segundos por conta OAuth. Esse controle é implementado utilizando o Bucket4j, conforme documentação do HubSpot [aqui](https://developers.hubspot.com/docs/guides/apps/api-usage/usage-details).

- **Webhook HubSpot**:
   - O HubSpot só aceita endpoints HTTPS para webhooks.
   - Para testes, foi configurada uma VM no Google Cloud com servidor NGINX e um certificado SSL.
   - A URL de teste está hospedada em `https://financer.digital` (já tinha esse domínio comprado 🤣). Os testes podem ser feitos tanto pelo localhost quanto pelo ambiente online. Após criar um contato, o webhook é ativado e envia uma mensagem via WebSocket para a página HTML estática.

- **Injeção e Inversão de Dependências**:
   - Está sendo utilizada injeção de dependências para as camadas de serviço, com injeção via construtor em vez de @Autowired, pois acredito que essa abordagem é mais robusta e facilita o teste das classes, além de ser a prática recomendada no Spring.
   - Também estou invertendo todas as camadas de serviço, para que a camada de controle não tenha conhecimento de detalhes de implementação das camadas inferiores.

- **Separação de Responsabilidades**:
   - Todas as camadas do sistema seguem a separação de responsabilidades, garantindo que a camada de controle seja desacoplada das camadas de serviço, o que facilita a manutenção, teste e escalabilidade da aplicação, além de ter desacoplado todos os magic numbers e strings, para facilitar a manutenção e a tradução do código.

- **Tratamento de Exceções**:
  - O tratamento de exceções é centralizado e consiste em garantir que os erros sejam capturados de forma consistente e não exponham informações sensíveis. Isso assegura que o comportamento da aplicação seja previsível, seguro e facilmente monitorável, mantendo a experiência do usuário e a integridade do sistema.

## Desafios e Problemas Encontrados

- **Problema com o FormData para obter o token de acesso**:
   - Ao tentar trocar o código de autorização por um token de acesso, houve dificuldades por não perceber que o HubSpot enviava um `formData` no `POST` em vez de um body comum.

- **Problema ao Testar o Webhook**:
   - Inicialmente, houve erros silenciosos ao testar o webhook porque o HubSpot enviava um array de objetos, ao invés de um único objeto, como mencionado na documentação.

## Tarefas Futuras

Embora as anotações sobre tarefas futuras não sejam essenciais para uma aplicação pequena como esta, elas são fundamentais em uma aplicação real com um grande número de usuários. Abaixo estão as melhorias que seriam necessárias para garantir a escalabilidade, segurança e robustez da aplicação em um ambiente de produção (isso não se aplica aos testes unitários, pois por mais que seja um app pequeno, eles deveriam estar presentes caso ele fosse para produção, mas acabou não dando tempo 😢).

### **1. Variáveis de Ambiente**
- As credenciais, como `secretId` e `clientId`, precisam ser movidas para variáveis de ambiente. Isso permitirá configurá-las de maneira segura nas *secrets* do GitHub, GitLab ou outra plataforma similar, evitando que fiquem expostas no arquivo `application.yml`.

### **2. CI/CD**
- A implementação de pipelines de CI/CD (Integração Contínua/Deploy Contínuo) deve ser realizada para automatizar o processo de deploy, garantindo agilidade, consistência e controle nas versões da aplicação.

### **3. Containerização**
- Estou utilizando Docker para evitar problemas com o Maven no deploy, mas pode ser estudada a possibilidade de armazenar a imagem em um repositório como DockerHub ou Google Artifacts, facilitando o gerenciamento e o deploy contínuo.

### **4. Rate Limiting - Redis**
- Em vez de utilizar o **Bucket4j** para o controle de rate limit, seria melhor substituir por **Redis**. O Redis oferece uma solução distribuída e escalável, o que permite que o controle de rate limit seja compartilhado entre múltiplos servidores, garantindo maior flexibilidade e robustez na gestão de tráfego em aplicações de grande porte.
- Além disso, o código apresenta um acoplamento claro entre a lógica de rate limiting e as regras de negócio (ContactService), o que pode ser aprimorado. Embora tenha sido implementado dessa forma devido a limitações de tempo, ao migrar para o Redis, seria importante desacoplar a lógica de rate limit das regras de negócio. Isso não só melhora a manutenibilidade e a testabilidade do código, mas também protege melhor o domínio da aplicação, permitindo uma arquitetura mais limpa e escalável.

### **5. Testes**
- Adicionar testes unitários e de integração para garantir que os componentes da aplicação funcionem como esperado, e que qualquer mudança futura no código não quebre funcionalidades existentes. Isso também ajudará a melhorar a confiabilidade da aplicação ao longo do tempo, permitindo detectar regressões mais rapidamente e reduzir o risco de falhas em produção.
