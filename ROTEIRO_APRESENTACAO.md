# 🎬 Roteiro de Apresentação - NeoMoto Fleet Management System

**Duração Total Estimada:** 15-20 minutos  
**Equipe:** Afonso Correia Pereira (RM557863), Adel Mouhaidly (RM557705), Tiago Augusto Desiderato (RM558485)

---

## 📋 ESTRUTURA DA APRESENTAÇÃO

### **1. Abertura e Introdução** (2 minutos)
### **2. Narrativa da Solução** (5 minutos)
### **3. Demonstração Técnica** (8-10 minutos)
### **4. Integração Multidisciplinar** (3-4 minutos)
### **5. Encerramento** (1 minuto)

---

## 🎯 PARTE 1: ABERTURA E INTRODUÇÃO (2 min)

### **Fala Inicial (Afonso - 30s)**
> "Boa tarde/tarde, professores! Somos a equipe do projeto NeoMoto, um sistema completo de gestão de frotas desenvolvido para a empresa NeoMoto. Hoje vamos apresentar nossa solução que integra conceitos de desenvolvimento web, banco de dados, segurança e boas práticas de engenharia de software."

### **Apresentação dos Membros (30s)**
- **Afonso:** "Eu sou o Afonso, RM557863, responsável por [backend/segurança/frontend]"
- **Adel:** "Eu sou o Adel, RM557705, responsável por [backend/banco de dados/frontend]"
- **Tiago:** "Eu sou o Tiago, RM558485, responsável por [backend/arquitetura/frontend]"

### **Contexto do Problema (1 min - Adel)**
> "A NeoMoto é uma empresa que precisa gerenciar uma frota de motocicletas, motoristas, alocações e manutenções. O desafio era criar um sistema que permitisse controle total sobre a operação, com diferentes níveis de acesso e fluxos operacionais bem definidos."

### **Transição (10s)**
> "Agora vamos apresentar a solução que desenvolvemos e as decisões técnicas que tomamos."

---

## 📖 PARTE 2: NARRATIVA DA SOLUÇÃO (5 min) - 20 pontos

### **2.1. Proposta da Solução (1 min - Tiago)**

**O que falar:**
> "Desenvolvemos um sistema web completo de gestão de frotas com três perfis de usuário distintos: Administrador, Gerente e Operador. O sistema permite gerenciar motos, motoristas, pátios, realizar alocações, devoluções e controlar manutenções. Tudo isso com interface intuitiva, segurança robusta e banco de dados versionado."

**Destaques:**
- ✅ Sistema completo e funcional
- ✅ Múltiplos perfis de acesso
- ✅ Fluxos operacionais completos

---

### **2.2. Decisões de Design e Escolhas Tecnológicas (3 min)**

#### **A) Stack Tecnológica (Afonso - 1 min)**

**O que falar:**
> "Escolhemos Java 21 com Spring Boot 3.3 por ser uma stack enterprise robusta e madura. Utilizamos Spring Security 6 para autenticação e autorização baseada em roles, Spring Data JPA para persistência, e Thymeleaf como engine de templates server-side, que nos permite integração nativa com Spring Security."

**Mostrar no código (se possível):**
- `build.gradle` - Dependências
- `SecurityConfig.java` - Configuração de segurança

**Justificativas:**
- ✅ **Java 21 + Spring Boot:** Padrão de mercado, grande comunidade
- ✅ **Spring Security:** Segurança robusta e testada
- ✅ **Thymeleaf:** Templates server-side com integração nativa
- ✅ **PostgreSQL:** Banco relacional robusto para produção

---

#### **B) Arquitetura e Padrões (Adel - 1 min)**

**O que falar:**
> "Aplicamos princípios SOLID e arquitetura em camadas: Controllers para requisições HTTP, Services para lógica de negócio, Repositories para acesso a dados, e Domain entities para modelagem. Utilizamos Lombok para reduzir boilerplate e Flyway para versionamento do banco de dados."

**Mostrar estrutura:**
```
src/main/java/com/mottu/fleet/
├── controller/    # Camada de apresentação
├── service/       # Lógica de negócio
├── repository/    # Acesso a dados
└── domain/       # Entidades JPA
```

**Justificativas:**
- ✅ **Separação de responsabilidades:** Facilita manutenção
- ✅ **Flyway:** Versionamento de banco de dados
- ✅ **Lombok:** Código mais limpo

---

#### **C) Segurança e Controle de Acesso (Tiago - 1 min)**

**O que falar:**
> "Implementamos controle de acesso baseado em roles com três níveis: ADMIN tem acesso total incluindo exclusão de registros, GERENTE pode criar e editar mas não deletar, e OPERADOR apenas executa fluxos operacionais como alocações e manutenções."

**Mostrar configuração:**
- `SecurityConfig.java` - Regras de autorização
- Explicar como funciona o controle por roles

**Justificativas:**
- ✅ **Segurança em camadas:** Proteção de endpoints
- ✅ **Princípio do menor privilégio:** Cada perfil tem apenas o necessário

---

### **2.3. Originalidade e Criatividade (1 min - Afonso)**

**O que falar:**
> "A originalidade da nossa solução está na integração completa de múltiplos conceitos: sistema de alocação com checklist de saída e devolução, controle de manutenção que bloqueia automaticamente a moto para novas alocações, validação de CNH dos motoristas antes de permitir alocação, e dashboard com estatísticas em tempo real."

**Destaques de criatividade:**
- ✅ **Fluxo de alocação completo:** Checklist de saída e devolução
- ✅ **Bloqueio automático:** Moto em manutenção não pode ser alocada
- ✅ **Validação de CNH:** Sistema verifica validade antes de alocar
- ✅ **Dashboard dinâmico:** Estatísticas atualizadas em tempo real

---

## 💻 PARTE 3: DEMONSTRAÇÃO TÉCNICA (8-10 min) - 40 pontos

### **3.1. Acesso ao Sistema Deployado (1 min - Adel)**

**O que fazer:**
1. Abrir navegador
2. Acessar URL do deploy (Render.com)
3. Mostrar que está online e funcionando

**O que falar:**
> "A aplicação está deployada e rodando online no Render.com. Vamos navegar pelos principais fluxos do sistema."

**Pontos a destacar:**
- ✅ Sistema online e acessível
- ✅ Deploy funcional em produção

---

### **3.2. Tela de Login e Segurança (1 min - Tiago)**

**O que fazer:**
1. Mostrar tela de login
2. Fazer login como ADMIN
3. Explicar os três perfis

**O que falar:**
> "Temos três perfis de usuário: ADMIN com acesso total, GERENTE que pode criar e editar mas não deletar, e OPERADOR que apenas executa fluxos operacionais. Vou fazer login como administrador para demonstrar todas as funcionalidades."

**Credenciais para mostrar:** PAREI AQUI
- Admin: admin@neomoto.com / admin123
- Gerente: gerente@neomoto.com / gerente123
- Operador: operador@neomoto.com / operador123

**Pontos a destacar:**
- ✅ Autenticação funcionando
- ✅ Diferentes níveis de acesso

---

### **3.3. Dashboard e UI/UX (1 min - Afonso)**

**O que fazer:**
1. Mostrar dashboard com estatísticas
2. Destacar design e navegação
3. Mostrar responsividade (se possível)

**O que falar:**
> "O dashboard apresenta estatísticas em tempo real: total de motos, motoristas, alocações ativas e manutenções abertas. A interface foi desenvolvida com foco em usabilidade, com navegação intuitiva e feedback visual claro para o usuário."

**Pontos a destacar:**
- ✅ **UI moderna:** Interface limpa e profissional
- ✅ **UX intuitiva:** Navegação fácil
- ✅ **Estatísticas dinâmicas:** Dados em tempo real
- ✅ **Feedback visual:** Mensagens de sucesso/erro

---

### **3.4. Fluxo 1: Gerenciamento de Motos (2 min - Adel)**

**O que fazer:**
1. Ir em "Motos" → "Nova Moto"
2. Criar uma nova moto
3. Mostrar lista de motos
4. Mostrar detalhes de uma moto
5. Editar uma moto
6. Mostrar que apenas ADMIN pode deletar

**O que falar:**
> "Vamos criar uma nova moto. O sistema valida todos os campos, incluindo placa única. Após criar, podemos visualizar, editar e, se formos ADMIN, deletar. O sistema mantém histórico com timestamps de criação e atualização."

**Conceitos a destacar:**
- ✅ **CRUD completo:** Create, Read, Update, Delete
- ✅ **Validação de dados:** Campos obrigatórios e únicos
- ✅ **Controle de acesso:** Apenas ADMIN deleta
- ✅ **Auditoria:** Timestamps automáticos

---

### **3.5. Fluxo 2: Alocação de Moto (2 min - Tiago)**

**O que fazer:**
1. Ir em "Alocações" → "Nova Alocação"
2. Selecionar moto disponível
3. Selecionar motorista com CNH válida
4. Preencher checklist de saída
5. Criar alocação
6. Mostrar que moto mudou status para "ALOCADA"

**O que falar:**
> "O fluxo de alocação é um dos mais importantes. O sistema valida que a moto está disponível, que o motorista está ativo e com CNH válida. Após criar a alocação, a moto automaticamente muda de status para ALOCADA, impedindo nova alocação."

**Conceitos a destacar:**
- ✅ **Validação de regras de negócio:** Moto disponível, CNH válida
- ✅ **Transações:** Mudança de status automática
- ✅ **Integridade referencial:** Foreign keys
- ✅ **Fluxo completo:** Saída → Devolução

---

### **3.6. Fluxo 3: Devolução de Moto (1 min - Afonso)**

**O que fazer:**
1. Ir em "Alocações" → Selecionar alocação ativa
2. Clicar em "Devolver"
3. Preencher checklist de devolução
4. Atualizar KM (opcional)
5. Confirmar devolução
6. Mostrar que moto voltou para "DISPONIVEL"

**O que falar:**
> "Na devolução, o sistema registra o checklist, permite atualizar a quilometragem e automaticamente libera a moto para nova alocação, mudando seu status de volta para DISPONIVEL."

**Conceitos a destacar:**
- ✅ **Fluxo completo:** Fechamento de ciclo
- ✅ **Atualização de estado:** Status automático
- ✅ **Rastreabilidade:** Histórico completo

---

### **3.7. Fluxo 4: Manutenção (1.5 min - Adel)**

**O que fazer:**
1. Ir em "Manutenções" → "Abrir Manutenção"
2. Selecionar moto
3. Preencher motivo
4. Abrir manutenção
5. Mostrar que moto mudou para "MANUTENCAO"
6. Tentar alocar a moto (deve falhar)
7. Fechar manutenção
8. Mostrar que moto voltou para "DISPONIVEL"

**O que falar:**
> "Quando abrimos uma manutenção, a moto automaticamente é bloqueada para novas alocações. Isso garante que uma moto em manutenção não seja alocada acidentalmente. Ao fechar a manutenção, a moto volta para disponível."

**Conceitos a destacar:**
- ✅ **Bloqueio automático:** Integridade de dados
- ✅ **Validação de estado:** Não permite alocar moto em manutenção
- ✅ **Rastreabilidade:** Histórico de manutenções

---

### **3.8. Demonstração de Perfis (1 min - Tiago)**

**O que fazer:**
1. Fazer logout
2. Fazer login como OPERADOR
3. Tentar criar uma moto (deve dar erro de acesso negado)
4. Mostrar que pode apenas visualizar e executar fluxos
5. Fazer login como GERENTE
6. Mostrar que pode criar mas não deletar

**O que falar:**
> "Vamos demonstrar o controle de acesso. Como OPERADOR, não posso criar registros, apenas visualizar e executar fluxos operacionais. Como GERENTE, posso criar e editar, mas não deletar. Apenas ADMIN tem acesso total."

**Conceitos a destacar:**
- ✅ **Controle de acesso:** Spring Security funcionando
- ✅ **Autorização por roles:** Diferentes níveis
- ✅ **Segurança em camadas:** Proteção de endpoints

---

## 🔗 PARTE 4: INTEGRAÇÃO MULTIDISCIPLINAR (3-4 min) - 20 pontos

### **4.1. Banco de Dados e SQL (1.5 min - Adel)**

**O que mostrar:**
1. Abrir arquivos de migração Flyway
2. Mostrar estrutura das tabelas
3. Mostrar relacionamentos (Foreign Keys)
4. Mostrar índices e constraints

**O que falar:**
> "Utilizamos Flyway para versionamento do banco de dados. Temos 6 migrações que criam as tabelas, índices, constraints e dados iniciais. O banco foi modelado com relacionamentos bem definidos: motos, motoristas, pátios, alocações e manutenções. Implementamos índices para melhorar performance em consultas frequentes."

**Arquivos para mostrar:**
- `V1__create_tables.sql` - Estrutura do banco
- `V4__indexes_constraints.sql` - Otimizações
- `V6__ensure_correct_passwords.sql` - Dados iniciais

**Conceitos aplicados:**
- ✅ **Modelagem relacional:** Tabelas e relacionamentos
- ✅ **Índices:** Performance
- ✅ **Constraints:** Integridade de dados
- ✅ **Versionamento:** Flyway migrations

---

### **4.2. Engenharia de Software e Arquitetura (1 min - Tiago)**

**O que mostrar:**
1. Estrutura de pastas do projeto
2. Separação de camadas
3. Princípios SOLID aplicados

**O que falar:**
> "Aplicamos princípios de engenharia de software: separação de responsabilidades em camadas (Controller, Service, Repository), uso de DTOs para transferência de dados, injeção de dependências com Spring, e tratamento de erros centralizado."

**Conceitos aplicados:**
- ✅ **Arquitetura em camadas:** MVC
- ✅ **SOLID:** Single Responsibility, Dependency Inversion
- ✅ **DRY:** Don't Repeat Yourself
- ✅ **Injeção de dependências:** Spring IoC

---

### **4.3. Interface e Experiência do Usuário (1 min - Afonso)**

**O que mostrar:**
1. Templates Thymeleaf
2. Layout responsivo
3. Mensagens de feedback
4. Navegação intuitiva

**O que falar:**
> "Desenvolvemos uma interface focada em usabilidade: layout consistente com navegação clara, mensagens de feedback para todas as ações, validação de formulários no frontend e backend, e design que facilita a navegação entre diferentes seções do sistema."

**Conceitos aplicados:**
- ✅ **UI/UX:** Interface intuitiva
- ✅ **Feedback visual:** Mensagens de sucesso/erro
- ✅ **Validação:** Frontend e backend
- ✅ **Acessibilidade:** Navegação clara

---

### **4.4. Evidências e Documentação (30s - Adel)**

**O que mostrar:**
1. README.md completo
2. Documentação de deploy
3. Estrutura do projeto
4. Scripts SQL organizados

**O que falar:**
> "Mantivemos documentação completa: README com instruções de instalação, guia de deploy, documentação de variáveis de ambiente, e código comentado. Todos os scripts SQL estão versionados e organizados."

**Evidências:**
- ✅ **README.md:** Documentação completa
- ✅ **Scripts SQL:** Organizados e versionados
- ✅ **Código comentado:** Facilita manutenção

---

## 🎬 PARTE 5: ENCERRAMENTO (1 min)

### **Resumo Final (Afonso - 30s)**
> "Em resumo, desenvolvemos um sistema completo de gestão de frotas que integra conceitos de desenvolvimento web, banco de dados, segurança e engenharia de software. A solução está deployada, funcional e pronta para uso em produção."

### **Agradecimento (Tiago - 20s)**
> "Agradecemos a atenção e estamos abertos para perguntas. O sistema está disponível online e podemos demonstrar qualquer funcionalidade adicional que desejarem."

### **Convite para Perguntas (Adel - 10s)**
> "Temos alguma pergunta?"

---

## 📝 CHECKLIST PRÉ-APRESENTAÇÃO

### **Antes de Começar:**
- [ ] Sistema deployado e funcionando
- [ ] Todos os membros presentes
- [ ] Navegador aberto na URL do deploy
- [ ] Código aberto no IDE (para mostrar estrutura)
- [ ] README.md aberto (para mostrar documentação)
- [ ] Testar login com todos os perfis
- [ ] Ter dados de exemplo no banco (motos, motoristas, etc.)
- [ ] Testar todos os fluxos antes da apresentação

### **Durante a Apresentação:**
- [ ] Fazer transições suaves entre os membros
- [ ] Não ter pausas longas
- [ ] Manter tom profissional mas descontraído
- [ ] Olhar para a câmera quando falar
- [ ] Demonstrar, não apenas falar
- [ ] Destacar conceitos técnicos aplicados

### **Pontos Críticos a Não Esquecer:**
- ✅ **Deploy online** (40 pontos)
- ✅ **Navegação pelos fluxos** (40 pontos)
- ✅ **Conceitos aplicados** (40 pontos)
- ✅ **UI/UX** (40 pontos)
- ✅ **Decisões técnicas justificadas** (20 pontos)
- ✅ **Originalidade** (20 pontos)
- ✅ **Integração multidisciplinar** (20 pontos)
- ✅ **Todos participam** (10 pontos)

---

## 🎯 DICAS FINAIS

1. **Praticar antes:** Façam pelo menos 2 ensaios completos
2. **Dividir bem o tempo:** Não ultrapassem 20 minutos
3. **Ter backup:** Se algo falhar, tenham planos B
4. **Demonstrar, não apenas falar:** Mostrem o sistema funcionando
5. **Destacar conceitos:** Sempre relacionem com a disciplina
6. **Trabalho em equipe:** Todos devem participar igualmente
7. **Clareza:** Falem pausadamente e com clareza
8. **Confiança:** Mostrem domínio sobre o projeto

---

## 📊 DISTRIBUIÇÃO DE FALAS SUGERIDA

| Membro | Tempo Total | Principais Responsabilidades |
|--------|-------------|------------------------------|
| **Afonso** | ~5 min | Abertura, Stack técnica, Originalidade, Dashboard, Devolução, UI/UX, Encerramento |
| **Adel** | ~6 min | Contexto, Arquitetura, Acesso ao deploy, Motos, Manutenção, Banco de dados, Evidências |
| **Tiago** | ~6 min | Apresentação, Proposta, Segurança, Login, Alocação, Perfis, Engenharia de Software |

**Total:** ~17 minutos + 3 minutos de margem para perguntas

---

## 🚀 BOA SORTE! 🚀

