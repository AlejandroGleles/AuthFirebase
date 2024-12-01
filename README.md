AuthFirebase
Um aplicativo Android para autenticação de usuários utilizando Firebase Authentication. Ele oferece duas formas de login: por e-mail/senha e com Google, além de permitir a criação de contas. Ideal para quem busca implementar uma base funcional para autenticação em projetos Android.

📋 Funcionalidades
Login por e-mail e senha
Login com conta do Google
Criação de conta com validação de senha
Redirecionamento após login bem-sucedido
Integração com Firebase Authentication
🚀 Tecnologias
Kotlin: Linguagem utilizada para o desenvolvimento.
Firebase Authentication: Gerenciamento de autenticação.
Google Sign-In: Login rápido com contas do Google.
Android SDK: Para criar interfaces responsivas e amigáveis.
📂 Estrutura do Projeto
bash
Copiar código
AuthFirebase/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/br/gonzaga/autenticfirebase/
│   │   │   │   ├── MainActivity.kt        # Tela de login
│   │   │   │   ├── MainActivityRegistro.kt # Tela de registro de conta
│   │   │   │   ├── MainActivityhome.kt    # Tela de boas-vindas após login
│   │   │   ├── res/
│   │   │   │   ├── layout/                # Layouts XML das telas
│   │   │   │   ├── drawable/              # Recursos gráficos
│   │   │   │   ├── values/                # Strings, cores, dimensões
│   │   │   │   ├── values/strings.xml     # IDs do Firebase (substituir o placeholder `default_web_client_id`)
│   │   │   ├── AndroidManifest.xml        # Configuração do app
├── README.md                              # Documentação
📖 Como Usar
1️⃣ Clonar o Repositório
Clone este repositório no seu ambiente local:

bash
Copiar código
git clone https://github.com/AlejandroGleles/AuthFirebase.git
cd AuthFirebase
2️⃣ Configurar o Firebase
Acesse o Firebase Console.
Crie um novo projeto e configure o Firebase Authentication:
Habilite o login com e-mail e senha.
Habilite o login com Google.
Baixe o arquivo google-services.json e coloque-o no diretório app/ do projeto.
Substitua o default_web_client_id em res/values/strings.xml pelo Client ID do Firebase.
3️⃣ Abrir no Android Studio
Abra o Android Studio.
Clique em File > Open e selecione o diretório AuthFirebase.
Aguarde o Android Studio sincronizar o Gradle.
4️⃣ Executar o Aplicativo
Conecte um dispositivo Android ou inicie um emulador.
Clique em Run ou pressione Shift + F10 para compilar e executar o app.
🛠️ Personalização
Alterar cores e layouts: Edite os arquivos em res/values/ e res/layout/.
Substituir os gráficos: Adicione suas imagens no diretório res/drawable/.
📜 Licença
Este projeto está licenciado sob a MIT License.
