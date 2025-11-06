# ⚡ Deploy Rápido - 5 Minutos

## 🎯 Opção Mais Rápida: Render.com

### Passo 1: Preparar Repositório
```bash
# Certifique-se de que seu código está no GitHub/GitLab
git add .
git commit -m "Preparar para deploy"
git push
```

### Passo 2: Criar Conta e Deploy
1. Acesse: https://render.com
2. Clique em **"New +"** → **"Web Service"**
3. Conecte seu repositório GitHub/GitLab
4. Selecione o repositório do projeto

### Passo 3: Configurar Build
- **Name:** `mottu-fleet` (ou qualquer nome)
- **Environment:** `Java`
- **Build Command:** `./gradlew bootJar`
- **Start Command:** `java -jar build/libs/mottu-fleet-0.0.1-SNAPSHOT.jar`
- **Plan:** `Free`

### Passo 4: Criar Banco de Dados
1. No dashboard, clique em **"New +"** → **"PostgreSQL"**
2. Nome: `mottu-fleet-db`
3. Plan: `Free`
4. Anote as credenciais

### Passo 5: Configurar Variáveis de Ambiente
No seu Web Service, vá em **"Environment"** e adicione:

```
SPRING_PROFILES_ACTIVE=prod
SPRING_DATASOURCE_URL=jdbc:postgresql://[HOST_DO_DB]:5432/[NOME_DB]
SPRING_DATASOURCE_USERNAME=[USER_DO_DB]
SPRING_DATASOURCE_PASSWORD=[PASSWORD_DO_DB]
```

**Dica:** Render conecta automaticamente se você usar o nome do database no `render.yaml`

### Passo 6: Deploy!
Clique em **"Manual Deploy"** → **"Deploy latest commit"**

Aguarde ~5 minutos e sua aplicação estará no ar! 🚀

---

## 🔗 Outras Opções Rápidas

### Railway.app (2 minutos)
1. Acesse: https://railway.app
2. **New Project** → **Deploy from GitHub**
3. Adicione **PostgreSQL**
4. Railway detecta automaticamente e faz deploy!

### Fly.io (5 minutos)
```bash
# Instalar CLI
iwr https://fly.io/install.ps1 -useb | iex

# Login
fly auth signup

# Deploy
fly launch
fly postgres create
fly deploy
```

---

## ✅ Verificação Pós-Deploy

1. Acesse a URL fornecida pela plataforma
2. Teste login: `admin@neomoto.com` / `admin123`
3. Verifique se o banco está funcionando (criar uma moto, por exemplo)

---

## 🆘 Problemas Comuns

**Erro: "Cannot connect to database"**
→ Verifique se as variáveis de ambiente estão corretas

**Erro: "Port already in use"**
→ Algumas plataformas usam variável `PORT` - adicione no código:
```properties
server.port=${PORT:8080}
```

**Build falha**
→ Verifique se Java 21 está disponível na plataforma

---

## 📞 Suporte

- Render: https://render.com/docs
- Railway: https://docs.railway.app
- Fly.io: https://fly.io/docs

