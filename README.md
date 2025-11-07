# NeoMoto - Sistema de Gestão de Frotas

Sistema completo de gestão de frotas da NeoMoto desenvolvido com **Spring Boot**, **Thymeleaf**, **Flyway** e **Spring Security**.

## **Vídeo Demonstrativo**

- https://youtu.be/jWILc291OFg

## **Deploy**

- https://projetojavamottu.onrender.com/

## **Membros**

- Afonso Correia Pereira - RM557863
- Adel Mouhaidly - RM557705
- Tiago Augusto Desiderato - RM558485

## 🛠️ **Stack Técnica**

- **Java 21**
- **Spring Boot 3.3.x**
- **Spring Security 6** 
- **Spring Data JPA**
- **Thymeleaf** + Thymeleaf Security
- **Flyway** (versionamento DB)
- **H2** (desenvolvimento) / **PostgreSQL** (produção)
- **Lombok** (redução de boilerplate)
- **Maven** (build)

## 🚀 **Instalação e Execução**

### **📋 Pré-requisitos**
- ☕ **Java 21** ou superior ([Download Oracle](https://www.oracle.com/java/technologies/downloads/) | [Download OpenJDK](https://openjdk.org/))
- 📦 **Gradle 8.0+** (incluído no projeto via wrapper)
- 🐳 **Docker** (opcional para PostgreSQL em produção)
- 🌐 **Navegador moderno** (Chrome, Firefox, Safari, Edge)

### **🔧 Instalação Passo a Passo**

#### **1. Clone o Repositório**
```bash
# Clone o repositório
git clone https://github.com/seu-usuario/neomoto-fleet.git
cd neomoto-fleet

# Ou se for um arquivo ZIP, extraia e navegue até a pasta
```

#### **2. Verificar Java**
```bash
# Verificar se Java 21 está instalado
java -version

# Deve mostrar algo como:
# openjdk version "21.0.2" 2024-01-16
# OpenJDK Runtime Environment (build 21.0.2+13)
# OpenJDK 64-Bit Server VM (build 21.0.2+13, mixed mode, sharing)
```

#### **3. Executar a Aplicação**

**Windows (PowerShell/CMD):**
```bash
# Usar o wrapper do Gradle
.\gradlew.bat bootRun

# Ou se preferir usar gradle diretamente
gradle bootRun
```

**Linux/Mac:**
```bash
# Usar o wrapper do Gradle
./gradlew bootRun

# Ou se preferir usar gradle diretamente
gradle bootRun
```

#### **4. Aguardar a Inicialização**
```
# Você verá logs como:
# 2024-01-15 10:30:15.123  INFO 12345 --- [main] c.m.fleet.MottuFleetApplication : Starting MottuFleetApplication
# 2024-01-15 10:30:16.456  INFO 12345 --- [main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http)
# 2024-01-15 10:30:16.789  INFO 12345 --- [main] c.m.fleet.MottuFleetApplication : Started MottuFleetApplication in 2.5 seconds
```

### **🌐 Acesso ao Sistema**

#### **URLs de Acesso:**
- 🏠 **Sistema Principal:** http://localhost:8080
- 🔍 **Console H2 (Banco):** http://localhost:8080/h2-console
- 📊 **H2 Database URL:** `jdbc:h2:mem:neomoto`
- 👤 **Usuário H2:** `sa`
- 🔑 **Senha H2:** (deixe em branco)

#### **🔐 Credenciais de Login:**

| Perfil | E-mail | Senha | Acesso |
|--------|--------|-------|--------|
| 🔑 **ADMIN** | admin@neomoto.com | admin123 | Acesso total |
| 👨‍💼 **GERENTE** | gerente@neomoto.com | gerente123 | CRUD + Fluxos |
| 👷 **OPERADOR** | operador@neomoto.com | operador123 | Apenas Fluxos |

### **🚨 Solução de Problemas**

#### **Erro: "Java não encontrado"**
```bash
# Instalar Java 21
# Windows: Baixar do site da Oracle ou usar Chocolatey
# Linux: sudo apt install openjdk-21-jdk
# Mac: brew install openjdk@21
```

#### **Erro: "Porta 8080 em uso"**
```bash
# Verificar processos na porta 8080
netstat -ano | findstr :8080

# Parar processo (Windows)
taskkill /PID <PID_NUMBER> /F

# Ou alterar porta no application.properties
server.port=8081
```

#### **Erro: "Gradle não encontrado"**
```bash
# O projeto inclui gradle wrapper, use:
# Windows: .\gradlew.bat
# Linux/Mac: ./gradlew
```

### **📱 Primeiros Passos no Sistema**

1. **Acesse:** http://localhost:8080
2. **Faça login** com uma das credenciais acima
3. **Explore o Dashboard** com estatísticas em tempo real
4. **Teste os fluxos:**
   - Criar uma nova alocação
   - Abrir uma manutenção
   - Visualizar relatórios


## 🎯 **Funcionalidades por Perfil**

### 🔑 **ADMIN**
- ✅ **Tudo** que GERENTE pode fazer
- ✅ **Deletar** registros
- ✅ **Gerenciar usuários**
- ✅ **Acesso total** ao sistema

### 👨‍💼 **GERENTE**
- ✅ **CRUD** de motos, motoristas, pátios
- ✅ **Fluxos** de alocação e manutenção
- ❌ **Não pode** deletar registros
- ✅ **Relatórios** (quando implementados)

### 👷 **OPERADOR**
- ✅ **Visualizar** motos e motoristas (somente leitura)
- ✅ **Fluxos operacionais:**
  - 🔄 Alocar/devolver motos
  - 🔧 Abrir/fechar manutenções
- ❌ **Não pode** criar/editar registros

## 📊 **Fluxos Funcionais Implementados**

### 🔄 **1. Alocação/Devolução**
1. **Nova Alocação:**
   - Escolher moto disponível
   - Selecionar motorista ativo (CNH válida)
   - Definir pátio de origem
   - Checklist de saída
   
2. **Devolução:**
   - Checklist de devolução
   - Atualizar KM (opcional)
   - Definir pátio de devolução
   - Liberar moto para nova alocação

### 🔧 **2. Manutenção**
1. **Abrir Manutenção:**
   - Selecionar moto
   - Definir motivo
   - **Bloquear** moto para alocações
   
2. **Fechar Manutenção:**
   - Observações finais
   - **Liberar** moto para uso

## 🗄️ **Banco de Dados**

### **Flyway Migrations**
- **V1** - Criação de tabelas
- **V2** - Roles e usuários padrão  
- **V3** - Dados de exemplo
- **V4** - Índices e constraints
- **V5** - Correção de senhas

### **Entidades Principais**
- 👤 **Usuario** - Usuários do sistema
- 🏍️ **Moto** - Frota de motocicletas
- 👨‍💼 **Motorista** - Condutores
- 🏢 **Patio** - Locais de operação
- 🔄 **Alocacao** - Vínculos moto-motorista
- 🔧 **Manutencao** - Serviços nas motos

## 🏗️ **Arquitetura e Qualidade**

### **Estrutura do Projeto**
```
src/main/java/com/mottu/fleet/
├── 🔧 config/          # Configurações (Security)
├── 🌐 controller/      # Controllers Web
├── 📊 domain/          # Entidades JPA  
├── 📝 dto/             # Forms e DTOs
├── 💾 repository/      # Repositories JPA
└── ⚙️ service/         # Lógica de negócio

src/main/resources/
├── 🗃️ db/migration/    # Scripts Flyway
└── 🎨 templates/       # Templates Thymeleaf
```

### **Princípios Aplicados**
- ✅ **SOLID** - Separação clara de responsabilidades
- ✅ **DRY** - Fragmentos reutilizáveis no Thymeleaf
- ✅ **Clean Code** - Código limpo e legível
- ✅ **Tratamento de Erros** - Validações e feedback

## 🐳 **Configuração para Produção**

### **PostgreSQL com Docker**

#### **1. Subir PostgreSQL**
```bash
# Executar Docker Compose
docker-compose up -d

# Verificar se está rodando
docker ps
```

#### **2. Configurar Banco de Dados**
Edite o arquivo `src/main/resources/application.properties`:

```properties
# Comentar H2 e descomentar PostgreSQL
# spring.datasource.url=jdbc:h2:mem:neomoto
# spring.datasource.driver-class-name=org.h2.Driver
# spring.datasource.username=sa
# spring.datasource.password=

# Descomentar PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/mottu_fleet
spring.datasource.username=mottu_user
spring.datasource.password=mottu_pass
spring.datasource.driver-class-name=org.postgresql.Driver

# Alterar dialect
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

#### **3. Reiniciar Aplicação**
```bash
# Parar aplicação atual (Ctrl+C)
# Executar novamente
.\gradlew bootRun
```

### **🔧 Configurações Avançadas**

#### **Alterar Porta do Servidor**
```properties
# Em application.properties
server.port=8081
```

#### **Configurar Logs**
```properties
# Em application.properties
logging.level.com.mottu.fleet=INFO
logging.level.org.springframework.security=WARN
```

#### **Desabilitar H2 Console (Produção)**
```properties
# Em application.properties
spring.h2.console.enabled=false
```

## 🧪 **Testes e Build**

### **Executar Testes**
```bash
# Todos os testes
.\gradlew test

# Testes específicos
.\gradlew test --tests "MotoServiceTest"

# Com relatório de cobertura
.\gradlew test jacocoTestReport
```

### **Build da Aplicação**
```bash
# Build completo
.\gradlew build

# Apenas compilar
.\gradlew compileJava

# Build sem testes
.\gradlew build -x test

# Gerar JAR executável
.\gradlew bootJar
```

### **Executar JAR**
```bash
# Após gerar o JAR
java -jar build/libs/neomoto-fleet-1.0.0.jar
```

## 📊 **Monitoramento e Debug**

### **Console H2 (Desenvolvimento)**
- **URL:** http://localhost:8080/h2-console
- **JDBC URL:** `jdbc:h2:mem:neomoto`
- **User:** `sa`
- **Password:** (vazio)

### **Logs da Aplicação**
```bash
# Ver logs em tempo real
tail -f logs/application.log

# Ou no console do terminal onde executou
```

### **Health Check**
- **Status:** http://localhost:8080/actuator/health
- **Info:** http://localhost:8080/actuator/info

## 🚀 **Deploy em Produção**

### **Docker (Recomendado)**
```dockerfile
# Dockerfile (criar na raiz do projeto)
FROM openjdk:21-jre-slim
COPY build/libs/neomoto-fleet-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

```bash
# Build da imagem
docker build -t neomoto-fleet .

# Executar container
docker run -p 8080:8080 neomoto-fleet
```

### **Variáveis de Ambiente**
```bash
# Configurar via variáveis de ambiente
export SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/mottu_fleet
export SPRING_DATASOURCE_USERNAME=mottu_user
export SPRING_DATASOURCE_PASSWORD=mottu_pass
export SERVER_PORT=8080
```



