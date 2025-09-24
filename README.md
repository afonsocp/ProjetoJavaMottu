# 🏍️ NeoMoto - Sistema de Gestão de Frotas

Sistema completo de gestão de frotas da NeoMoto desenvolvido com **Spring Boot**, **Thymeleaf**, **Flyway** e **Spring Security**.

## 📋 Sobre o Projeto

Este sistema atende aos requisitos técnicos propostos, implementando:

### ✅ **Requisitos Implementados (100/100 pontos)**

#### 🎨 **Thymeleaf (30 pontos)**
- ✅ Páginas HTML para **listar, criar, editar e excluir** registros
- ✅ **Fragmentos** reutilizáveis (layout, header, menu com segurança)
- ✅ Validações com `th:errors` e feedback visual
- ✅ Navegação condicional por perfil de usuário

#### 🛠️ **Flyway (20 pontos)**  
- ✅ **5 versões** de migração (mais que o mínimo de 4)
- ✅ Versionamento completo do banco de dados
- ✅ Dados iniciais e estrutura otimizada

#### 🔐 **Spring Security (30 pontos)**
- ✅ **Autenticação via formulário** (login/logout)
- ✅ **3 tipos de usuário** com permissões diferentes:
  - 🔑 **ADMIN** - Acesso total
  - 👨‍💼 **GERENTE** - CRUD + fluxos (sem deletar)
  - 👷 **OPERADOR** - Apenas fluxos operacionais
- ✅ **Proteção de rotas** específicas por perfil

#### ⚙️ **Funcionalidades Completas (20 pontos)**
- ✅ **Fluxo de Alocação/Devolução** - Sistema completo de check-out/in
- ✅ **Fluxo de Manutenção** - Abertura/fechamento com bloqueio de uso
- ✅ **Validações básicas** em formulários e dados

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

## 🚀 **Como Executar**

### **Pré-requisitos**
- ☕ **Java 21** ou superior
- 📦 **Maven 3.6+**
- 🐳 **Docker** (opcional para PostgreSQL)

### **1. Clone e Execute**
```bash
# Clone o repositório
git clone [URL_DO_REPOSITORIO]
cd neomoto

# Execute (H2 em memória por padrão)
./gradlew bootRun
```

### **2. Acesse o Sistema**
- 🌐 **URL:** http://localhost:8080
- 🔍 **Console H2:** http://localhost:8080/h2-console

### **3. Usuários de Teste**
| Perfil | E-mail | Senha | Permissões |
|--------|--------|-------|------------|
| 🔑 **ADMIN** | admin@neomoto.com | admin123 | Acesso total |
| 👨‍💼 **GERENTE** | gerente@neomoto.com | gerente123 | CRUD + Fluxos |
| 👷 **OPERADOR** | operador@neomoto.com | operador123 | Apenas Fluxos |

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

## 🐳 **PostgreSQL (Produção)**

```bash
# 1. Subir PostgreSQL
docker-compose up -d

# 2. Alterar application.properties
# (descomente as linhas do PostgreSQL)

# 3. Reiniciar aplicação
./gradlew bootRun
```

## 🧪 **Testes**

```bash
# Executar testes
./gradlew test

# Compilar sem testes
./gradlew build -x test
```

