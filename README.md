# Implementação de API Spring Boot Java 21 para Integração com HubSpot

## Endpoints Implementados

### 1. **GET /v1/auth/oauth/authorize**
Retorna a URL de autorização para iniciar o fluxo OAuth com o HubSpot.

### 2. **GET /v1/auth/oauth/callback**
Recebe o código de autorização fornecido pelo HubSpot e o troca por um token de acesso (access token).
- **Importante**: É necessário passar o `clientId` e `secretID` para essa chamada, mas ambos são mascarados para não serem expostos.

### 3. **POST /v1/contacts**
Cria um novo contato no HubSpot, respeitando as políticas de rate limit.

### 4. **POST /v1/contacts/webhook**
Escuta eventos de "contact.creation" enviados pelo HubSpot e processa esses eventos.
- **Objetivo de Mock**: Ao processar os eventos, envia uma mensagem via WebSocket para a página HTML estática como confirmação visual.

### 5. **GET /v1/contacts**
Obtém uma lista dos contatos do HubSpot, respeitando as políticas de rate limit.

## Tecnologias e Ferramentas Utilizadas

- **OpenFeign** para realizar requisições HTTP.
- **SLF4J** para rastreabilidade das requisições e logs.
- **Bucket4j** para controle de rate limiting.
   - **Vantagens**: Simples, rápido, não precisa de banco externo.
   - **Limitações**: Funciona apenas na memória da aplicação, não é distribuído.
- **SSL (Certificado de Segurança)** via Google Cloud para hospedar o webhook com HTTPS.

## Implementações e Considerações

- **Mascarando Secrets**:
   - A `clientId` e `secretID` são mascaradas tanto nos logs quanto nas exceções para garantir a segurança.

- **Rate Limiting**:
   - O HubSpot impõe um limite de 110 requisições a cada 10 segundos por conta OAuth. Esse controle é implementado utilizando o Bucket4j, conforme documentação do HubSpot [aqui](https://developers.hubspot.com/docs/guides/apps/api-usage/usage-details).

- **Webhook HubSpot**:
   - O HubSpot só aceita endpoints HTTPS para webhooks.
   - Para testes, foi configurada uma VM no Google Cloud com servidor NGINX e um certificado SSL.
   - A URL de teste está hospedada em `https://financer.digital`. Todos os endpoints estão configurados no NGINX, e os testes podem ser feitos tanto pelo localhost quanto pelo ambiente online. Após criar um contato, o webhook é ativado e envia uma mensagem via WebSocket para a página HTML estática.

## Desafios e Problemas Encontrados

- **Problema com o FormData no GetAccessToken**:
   - Ao tentar trocar o código de autorização por um token de acesso, houve dificuldades por não perceber que o HubSpot enviava um `formData` no `POST` em vez de um body comum.

- **Problema ao Testar o Webhook**:
   - Inicialmente, houve erros silenciosos ao testar o webhook porque o HubSpot enviava um array de objetos, ao invés de um único objeto, como mencionado na documentação.

## Tarefas Futuras

- **Variáveis de Ambiente**:
   - As credenciais (como o `secret` e `clientId`) precisam ser movidas para variáveis de ambiente para que possam ser configuradas nas secrets do GitHub, GitLab ou outra, para que em vez de ficarem expostas no arquivo `application.yml`.

- **CI/CD**:
   - A implementação de pipelines de CI/CD precisa ser feita para automatizar o processo de deploy.

- **Containerização**:
   - Considerar a containerização da aplicação e possivelmente colocar a imagem em um DockerHub ou Google Artifacts para melhor gerenciamento e deploy.
