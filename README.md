# ProjectGithubLabs

Esse projeto trata-se de um aplicativo para consultar a API do GitHub e trazer os repositórios mais
populares de Java. 

<img src="https://github.com/VanderleiLopesO/ProjectGithubLabs/blob/main/image00.png" width="320" height="480"> <img src="https://github.com/VanderleiLopesO/ProjectGithubLabs/blob/main/image01.png" width="320" height="480"> <img src="https://github.com/VanderleiLopesO/ProjectGithubLabs/blob/main/image02.png" width="320" height="480"> <img src="https://github.com/VanderleiLopesO/ProjectGithubLabs/blob/main/image03.png" width="320" height="480">

# Arquitetura

Foi utilizado como arquitetura:

- Arquitetura de apresentação MVVM (padrão Google)
- Clean Architecture
- Jetpack: Architecture Componentes (ViewModel, LiveData)
- Saved State Handle (Para guardar o estado do ViewModel de acordo com o Lifecycle)

# Network

- Retrofit
- OkHttp (loga-se as request no console)
- Gson (unparser do JSON)
- Glide (download de imagens, além de a library ter mecanismo de cache e placeholder)
- Programação reativa: RxJava2

# Injeção de dependência

Injeção de dependências está sendo feito com a library Koin (foi optado pela agilidade e pouco boilerplate)

# Endless Scroll

Foi utilizado uma RecyclerView com um Scroll Listener customizado

- O scroll listener checa a possibilidade de possuir uma nova página e delega ao ViewModel que através de LiveDatas se responsabiliza em executar a request

# Testes automatizados

Para fins de testes foram utilizados:

- Testes unitários: JUnit, MockK e Robolectric
- Testes de UI: JUnit e Robolectric (Views)

# Observações

- Pelo tamanho da app do desafio não acredito na necessidade em modularizar, o clean foi separado em pacotes em um módulo. Além disso, por conta de estar apenas em um módulo (:app) não foi criado interfaces (IoC, que existem no clean) na camada de repository.

- Foi utilizado ConstraintLayout para desenhar os layouts por prover melhor performance e um XML mais limpo. Foi verificado e validado a possibilidade de overdraw e não existem nos layouts.
<img src="https://github.com/VanderleiLopesO/ProjectGithubLabs/blob/main/image04.png" width="320" height="480">

- Por conta de não existir nenhum processamento de apresentação dos dados providos pela API, optei por transitar o mesmo model (Item, Repository, PullRequestItem) entre Repository/UseCase/ViewModel/ViewHolder/Adapter. Uma melhor opção que poderia ser levada em conta no futuro seria possuir um segundo model que seria responsável apenas pelos dados de apresentação.
