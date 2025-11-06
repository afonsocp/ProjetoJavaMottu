# 🚀 Guia de Deploy Gratuito - NeoMoto Fleet

Este guia apresenta as melhores opções **gratuitas** para fazer deploy do projeto Java Spring Boot.

## 📋 Opções de Deploy Gratuito

### 1. 🎯 **Render.com** (RECOMENDADO)
**Por quê:** Fácil de usar, PostgreSQL gratuito incluído, deploy automático via Git

**Limites do plano gratuito:**
- ✅ 750 horas/mês (suficiente para 24/7)
- ✅ PostgreSQL gratuito (1GB)
- ✅ Deploy automático via GitHub/GitLab
- ⚠️ Aplicação "dorme" após 15min de inatividade (acorda em ~30s)

**Passos:**
1. Criar conta em [render.com](https://render.com)
2. Conectar repositório GitHub/GitLab
3. Criar novo **Web Service**
4. Configurações:
   - **Build Command:** `./gradlew bootJar`
   - **Start Command:** `java -jar build/libs/mottu-fleet-0.0.1-SNAPSHOT.jar`
   - **Environment:** Java 21
5. Criar **PostgreSQL Database** (gratuito)
6. Adicionar variáveis de ambiente:
   ```
   SPRING_DATASOURCE_URL=jdbc:postgresql://[HOST]:5432/[DATABASE]
   SPRING_DATASOURCE_USERNAME=[USER]
   SPRING_DATASOURCE_PASSWORD=[PASSWORD]
   SPRING_PROFILES_ACTIVE=prod
   ```
7. Deploy!

---

### 2. 🚂 **Railway.app**
**Por quê:** Muito simples, PostgreSQL incluído, sem dormência

**Limites do plano gratuito:**
- ✅ $5 crédito/mês (suficiente para apps pequenos)
- ✅ PostgreSQL gratuito
- ✅ Deploy via GitHub
- ⚠️ Créditos podem acabar com alto tráfego

**Passos:**
1. Criar conta em [railway.app](https://railway.app)
2. Conectar GitHub
3. **New Project** → **Deploy from GitHub repo**
4. Adicionar **PostgreSQL** service
5. Railway detecta automaticamente o Dockerfile
6. Adicionar variáveis de ambiente (conecta automaticamente ao PostgreSQL)

---

### 3. ✈️ **Fly.io**
**Por quê:** Performance excelente, global edge network

**Limites do plano gratuito:**
- ✅ 3 VMs compartilhadas
- ✅ 3GB storage
- ✅ PostgreSQL disponível (com limites)
- ⚠️ Requer configuração via CLI

**Passos:**
1. Instalar Fly CLI: `iwr https://fly.io/install.ps1 -useb | iex` (Windows)
2. Criar conta: `fly auth signup`
3. No diretório do projeto: `fly launch`
4. Seguir prompts (escolher região, nome do app)
5. Adicionar PostgreSQL: `fly postgres create`
6. Conectar: `fly postgres attach [DB_NAME] -a [APP_NAME]`
7. Deploy: `fly deploy`

---

### 4. ☁️ **Oracle Cloud (OCI) - Always Free**
**Por quê:** Sempre gratuito, recursos generosos

**Limites:**
- ✅ 2 VMs (AMD) sempre gratuitas
- ✅ PostgreSQL disponível
- ✅ Sem expiração
- ⚠️ Requer configuração manual de servidor

**Passos:**
1. Criar conta Oracle Cloud (sempre gratuito)
2. Criar instância Compute (VM.Standard.E2.1.Micro)
3. Instalar Java 21 e Docker
4. Fazer deploy via Docker ou JAR direto

---

### 5. 🌐 **Google Cloud Run**
**Por quê:** Escala automaticamente, paga apenas pelo uso

**Limites do tier gratuito:**
- ✅ 2 milhões de requisições/mês
- ✅ 360.000 GB-segundos de CPU
- ✅ 180.000 GB-segundos de memória
- ⚠️ Requer Cloud SQL (PostgreSQL) - pode ter custos

---

## 🐳 Deploy com Docker (Todas as Plataformas)

O projeto já inclui um `Dockerfile`. Para fazer build localmente:

```bash
# Build da imagem
docker build -t mottu-fleet .

# Executar localmente
docker run -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://host:5432/db \
  -e SPRING_DATASOURCE_USERNAME=user \
  -e SPRING_DATASOURCE_PASSWORD=pass \
  mottu-fleet
```

---

## ⚙️ Configuração de Produção

### Variáveis de Ambiente Necessárias

Crie um arquivo `.env` ou configure nas plataformas:

```bash
# Database
SPRING_DATASOURCE_URL=jdbc:postgresql://[HOST]:5432/[DATABASE]
SPRING_DATASOURCE_USERNAME=[USER]
SPRING_DATASOURCE_PASSWORD=[PASSWORD]

# Server
SERVER_PORT=8080

# Profile
SPRING_PROFILES_ACTIVE=prod

# Flyway (opcional - já habilitado por padrão)
SPRING_FLYWAY_ENABLED=true
```

### Arquivo application-prod.properties

Crie `src/main/resources/application-prod.properties`:

```properties
# Production Profile
spring.datasource.url=${SPRING_DATASOURCE_URL}
spring.datasource.username=${SPRING_DATASOURCE_USERNAME}
spring.datasource.password=${SPRING_DATASOURCE_PASSWORD}

# JPA
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=false

# Flyway
spring.flyway.enabled=true
spring.flyway.locations=classpath:db/migration

# Thymeleaf Cache (habilitar em produção)
spring.thymeleaf.cache=true

# Logging
logging.level.com.mottu.fleet=INFO
logging.level.org.springframework.security=WARN
```

---

## 📝 Checklist de Deploy

- [ ] Projeto no GitHub/GitLab
- [ ] Dockerfile criado ✅
- [ ] Variáveis de ambiente configuradas
- [ ] PostgreSQL criado e conectado
- [ ] Build testado localmente
- [ ] Health check funcionando
- [ ] Logs monitorados

---

## 🔍 Troubleshooting

### Erro: "Cannot connect to database"
- Verifique variáveis de ambiente
- Confirme que PostgreSQL está acessível
- Teste conexão manualmente

### Erro: "Port already in use"
- Configure `SERVER_PORT` via variável de ambiente
- Algumas plataformas definem `PORT` automaticamente

### Aplicação não inicia
- Verifique logs: `fly logs` ou dashboard da plataforma
- Confirme que Java 21 está disponível
- Verifique se JAR foi gerado corretamente

---

## 🎯 Recomendação Final

Para começar rapidamente: **Render.com**
- Mais fácil de configurar
- PostgreSQL incluído
- Interface web intuitiva
- Deploy automático via Git

Para produção séria: **Railway.app** ou **Fly.io**
- Melhor performance
- Mais controle
- Escalabilidade

---

## 📚 Links Úteis

- [Render Docs](https://render.com/docs)
- [Railway Docs](https://docs.railway.app)
- [Fly.io Docs](https://fly.io/docs)
- [Spring Boot Deployment](https://spring.io/guides/gs/spring-boot-for-azure/)

