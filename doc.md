#30/03/2017#
##Configurando LWJGL###
Configuramos o nosso ambiente de desenvolvimento, baixamos o Slick Utils (slick.ninjacave.com/slick-util.jar) e o LightWeight Java Game Library (https://sourceforge.net/projects/java-game-lib/files/Official%20Releases##/LWJGL%202.9.1/lwjgl-2.9.1.zip/download),criamos uma pasta chamada lib e colocamos os arquivos lwjgl_util.jar e o lwjgl dentro de uma pasta que criamos dentro da lib chamada jars, também colocamos o arquivo que baixamos o slickutil.jar tbm nessa mesma pasta, e criamos outra pasta chamada natives e colocamos os arquivos natives do lwjgl que baixamos dentro dessa mesma pasta. Após isto, inicamos o eclipse e colocamos a pasta lib dentro do nosso projeto, clicamos em nosso projeto com o botão direito do mouse e clicamos em buildpath e configurar buildpath. Clicamos no botão add Jars e adicionamos todos os jars da pasta jar e depois clicamos em Library e clicamos em natives e incluimos todos os natives do nossos respectivo SO que está dentro da pasta native que criamos.

##The Display##
**renderizador**
1. *GerenciadorDeJanelas*: Criamos 3 métodos básicos o criar,atualizar e destruir. 
**executador**
1. *Main*: Inicia o metodo criar do  *GerenciadorDeJanelas* e criamos um while no qual ficará responsável em atualizar a nossa engine passo por passo. 

##VAOs and VBOs##
**renderizador**
1. *Objeto*: Classe que contém um objeto do nosso game,contém o ID do vao que ele pertence e a quantidade de vertices que contém o objeto. 
2. *CarregadorDeObjetos*: A função principal dessa classe é receber as posições dos vertices ela cria um VAO, carrega os vertices no VBO e conecta esse VBO com o VAO em uma posição determinada e após criado retorna o Objeto objeto.
3. *Renderizador*: Limpa tudo que há no frame anterior renderizado,cria um background e Recebe o objeto ,utiliza o seu VaoID e quantidade de vertices para renderizar na tela.
 
**executador**
1. *Main*: Criamos dois Objetos das classes *CarregadorDeObjetos* e *Renderizador* e criamos um vetor que é um quadrado com todos os seus vertices. Carregamos um obj no VAO e depois dentro do while renderizamos ele , após fechar a janela liberamos todos os VAOs e VBOs da memória.

##Rendering with Index Buffers##
**renderizador**
1. *CarregadorDeObjetos*: Agora começamos a usar um novo VBO que contém os indices de cada vertice como por exemplo ao invez de repetir as posições dos vertices x,y e z da seguinte maneira (v0,v1,v3,v3,v1,v2) nós enviamos somente vertices não repetidos(v0,v1,v2,v3) e os indices de como a gpu ira renderizar(0,1,3,3,1,2) assim diminuindo a quantidade de dados que utilizaremos em nosso programa.
3. *Renderizador*: Modificamos o metodo glDrawArrays para glDrawElements com isso agora o nosso VBO de Indice funciona.
 
**executador**
1. *Main*: Não é necessário mais repetir as posições dos vertices repetidos, precisamos apenas colocar os não repetidos e adicionamos um vetor de inteiros de indices no qual diz a gpu qual vertice está conectado com qual.

##Colorindo Usando os Shaders##
**shaders**
1. *vertextShader* : Utilizando o GLSL(OpenGL Shading Language) criamos o arquivo *vertexShader* e *fragmentShader* que lida com a coloração dos vertices e os pixels entre eles. O *vertexShader* recebe como entrada as posições dos vertices, o gl_Position() dita onde os vertices irão ser renderizados e o output do *vertexShader* são os vertices para o *fragmentShader* após algum calculo necessário.
2. *fragmentShader*: Recebe como entrada a saida do *vertexShader* faz alguns calculos e retorna uma saida. O *fragmentShader* é responsavel por colorir os pixels entre os vertices e ele faz um calculo de acordo com o valor de cada pixel e sua distancia para saber qual será o valor de cada pixel.
3. *ProgramaShaderPadrao*: é uma classe abstrata que retrata o que todo Shader precisa ter,vertexShaderID,fragmentShaderID,programShaderID. Ele pega o caminho dos shaders,carrega-os e conecta-os no programa
4. *StaticShader*: É uma extensão do *ProgramaShaderPadrao* e que contém os caminhos dos vertex e fragment shaders e também conecta os shaders com o VAO. 
 
**executador**
1. *Main*: Criamos o StaticShader e antes de renderizar nos criamos o Programa de shaders e após renderizar nós Fechamos o Programa , lembrando também que ao sair do While damos o FreePrograma para liberar os dados da memória

#02/04/2017#
##Texturas##
**texturas**
1. *ModeloDeTextura* : Classe que contém todas as variáveis que contém um objeto que contém textura, ainda está muito simples e contém somente o ID da textura que estamos usando. 
**objetos**
1. *Objeto* : Movemos a classe *Objeto* do pacote **rendizador** para o pacote **objetos**
2. *ObjetoComTextura* : Contém o *Objeto* e o *ModeloDeTextura* .
**shaders**
1.*StaticShader* : Incluímos no método conectarAtributos o ID do VAO onde as coordenadas estão, no caso 1 com o in do vertexShader.
2.*vertexShader* : Recebeu uma nova in chamda coordenadasDaTextura e passa diretamente para o out , para que o fragmentShader possa lidar com a textura.
3.*fragmentShader*: Adicionamos o uniform sampler2D texturaSample a textura que ativamos no glActiveTexture no renderizador e utilizamos a função texture para colocar a textura dentro dos vertices que passamos.
**renderizador**
1.*CarregadorDeObjetos* : Criei o metodo carregarTextura no qual passa como argumento o nome do arquivo png e retorna um id que a textura terá. Também damos free na textura e criamos a lista para colocar ela dentro para dar free depois.
2.*Renderizador* : Passamos agora como argumento no renderizar um obj com textura, e damos bind glEnableVertexAttribArray na posição onde a textura esta no VAO, devemos tbm ativar a textura glBindTexture e dar bind glBindTexture e ao final dar glDisableVertexAttribArray na posição respectiva da nossa textura.
**executador**
1.*Main* : Agora passamos mais um argumento na carregarParaVAO que é a textura, chamamos o método ModeloDeTextura que carrega a textura e retorna um o id dela, após isso criamos um objcomtextura e criamos um objeto  ObjetoComTextura que contém o id do Objeto e o id da Textura. Após isso renderizamos esse objeto na classe rendizador.

##Variaveis uniformes e Matrizes de Transformação##
**shaders**
1.*ProgramaShaderPadrao* : getLocalidadeUniform retorna onde está o uniform que iremos conectar entre o programa java e o shader. Utiliza-se getLocalidadeDeTodosUniform para pegar todas as localidades dos uniforms que temos no shader.Criamos tbm métodos com o carregarFloat para carregar as variaveis que está no progama java para o shader onde nós pegamos a sua localidade no getLocalidadeUniform.
2.*StaticShader* : Pegamos a localidadae getLocalidadeDeTodosUniform e depois carregamos a matriz na variavel uniform do shader no carregarMatrizDeTransformacao.
3.*VertexShader* : pegamos o uniform mat4 matrizDeTransformacao e multiplicamos pelas posições dos vetores para transformalos.
**ferramentas**
1.*Matematica* : Criamos um método criarMatrizDeTransformacao para criar a matriz de transformação e setar os seus valores e retornar a matriz criada.

##Entidades,Matriz de Visualizacao e de Projecao##
**entidades**
1.*Entidades* : Classe responsável em criar as entidades no game, ela contém o modelo do objeto e as suas respectivas posições,rotação e escala.
2.*Camera* : Classe que contém um objeto camera e que o move de acordo com o WASD tem os atributos pitch,yaw e roll.
**ferramentas**
1.*Matematica* : Criamos o metodo criarMatrizDeVisualizacao que é responsável por movimentar o "mundo" de acordo com a movimentação da camera só que negativamente, ou seja, se a camera moveu para esquerda to do mundo fará o contrário e moverá para a direita.
**renderizador**
1.*Renderizador* : Criamos um construtor que iniciará a matriz de projeção logo que criamos o renderizador ja que ela é necessária somente uma vez ao iniciar o programa e não iremos mais utiliza-la, por isso ela é a primeira a multiplicar no *vertexShader*.
**shaders**
1.*StaticShader*: Pegamos a localidade da matriz de projeção e visualização e carregamos no shader.
2.*vertexShader* : recebe as matrizes uniforms e multiplica na gl_position.
**executador**
1.*Main* : Agora criamos um cubo ao invés de um quadrado modificando os vertices,indices e coordenadas de textura. Colocamos o renderizador para inicar apos o shader já que agora nós necessitamos do shader para iniciar a matriz de projeção logo no inicio quando o renderizador é criado, criamos o obj entidade e a camera.

Obs:A matriz de projeção é carregada no construtor do renderizador, a matriz de Visualização é carregada antes de chamar o metodo renderizar no Main e ela é criada dentro do método que carrega ela a matriz de transformação é chamada dentro do metodo renderizar.

## Importar Objetos Do Blender ##

## Entrada de Teclado e Mouse ##
**entradas**
1.*MeuMouse* : Classe responsável em detectar ações relacionadas ao uso do mouse, como qual botão o usuário esta clicando e onde está a posição do mouse na tela. Temos também metodos para saber se o botão foi pressionado, esta sendo pressionado  ou foi solto.
2.*MeuTeclado* : Assim como o mouse essa Classe é responsável em detectar o acionamento de teclas no Teclado.
**estruturasDeDaDos**
1.*Vector2f* : Classe responsável em lidar com vetores de float de duas dimensões, usadas em posicionamento 2D, como em Texturas, e Tela.
**renderizador**
1.*GerenciadorDeJanela* : Adicionamos a inicialização do Mouse e Teclado de LWJGL e seu destroy().
**executador**
1.*Main* : Colocamos uns SYSout só para testar o mouse e o teclado.

## Criação Das Estruturas De Dados que Usaremos em Nossa Engine ##

**estruturasDeDaDos**
1.*Vetor2f* : Classe responsável em lidar com vetores de float de duas dimensões, usadas em posicionamento 2D, como em Texturas, e Tela.
2.*Vetor3f* : Classe responsável em lidar com vetores de float de duas dimensões, usadas em posicionamento 3D, como posicionamento.
3.*Matriz4f* : Classe responsável em lidar com matrizes de transformação, utilizada para escalar,rotacionar e transladar um objeto, podendo tbm ser utilizada como movimentação de camera e matriz de projeção.
4.*Quaternio* : Quaternio é responsável em rotacionar um vetor ... muito complexo ainda, não sei definir muito bem o seu uso.

## Modificações necessárias para introduzir as estruturas de dados ##

Nessa atualização destruimos o package **matematica** e criamos um novo chamado **matrizesDeTransformacao** , nele está contido as matrizes de transformação, projeção e visualização. Agora também, não estou mais utilizando a biblioteca Vector nem a matrix4f da LWJGL. Estamos utilizando as nossas que criamos anteriormente.

## Luminosidade Difusa ##

**luminosidade**
1.*Difusa* : Classe responsável em criar um objeto do tipo Luminosidade que contém um vetor3f de posicao e outro de cor.
**objetos**
1.*CarregarObjeto* : No return do construtor colocamos as normais gerenciador.carregarParaVAO(vetorDeVertices, vetorDeTexturas,vetorDeNormais, vetorDeIndices);
**renderizador**
1.*GerenciadorDeObjetos* : Foi adicionado float[] normal no carregarParaVAO e carregada do VBO para o VAO
2.*Renderizador* : Aqui damos Enable no VAO da posição da Normal que é o 3 e depois de usa-lo damos desable.
**shaders**
1.*StaticShader* : Criamos variaves de localidade para a posicao da luz e sua cor que usaremos como uniform dentro do vertexshader e fragmentshader. Conectamos o in normais com o VAO 3 (NORMAL)
2.*vertexShader* : Criamos a variaves pos_mundo no qual diz em que posição real o objeto está após ter sido feito a transformação e criamos duas variaves de saida para o fragmentshader que é a normalDaSuperficie e o vetorPraLuz.
3.*fragmentShader* : Recebemos as normais da superficie o vetor da luz e os normalizamos para que quando fizermos o produto (dot) o tamanho não afete o resultado. Após fazer o dot nós limitamos para ser >=0 e damos como saida a cor da textura * vec4(cor_brilho)

## Luminosidade Especular ##

**luminosidade**
1.*Especular* : Classe responsável em criar as variáveis que farão com que o objeto tenha reflexo.
**objetos**
1.*CarregarObjeto* : No return do construtor colocamos as normais gerenciador.carregarParaVAO(vetorDeVertices, vetorDeTexturas,vetorDeNormais, vetorDeIndices);
**renderizador**
1.*Renderizador* : Carrega no shader a luminosidade Especular.
**shaders**
1.*StaticShader* : Criamos variaves de localidade para a Superficie Reflexiva e sua reflexividade que usaremos como uniform dentro do vertexshader e fragmentshader. E criamos a função para carregar a luminosidade especular para dentro do entro do vertexshader e fragmentshader
2.*vertexShader* : Criamos o vetorParaCamera que necessitaremos para dizer se a luz que reflete está perto de onde a camara está posicionada.
3.*fragmentShader* : Recebemos as variaveis uniforms SuperficieReflexiva e reflexividade , normalizamos o vetorParaCamera e criamos o vetorDaLuzNormalizada que é o inverso do vetorParaLuzNormalizada. Agora sabendo onde aponta o vetor para a camera e onde aponta o vetorDaLuz usamos a função de reflect do glsl para refletir o vetor que vem da luz na superficie ja que sabe qual é a sua normal. Após isso verificamos se a luz refletida está proximo a camera e fazemos o produto dot e virificamos caso for proximo maior será o reflexo do objeto e longe menor será. Utilizamos potencia para simular melhor o reflexo ja que diminiu muito quando é pequeno e diminui quase nada quando é grande.. No final o reflexo é (reflexo * reflexividade *corDaLuz) e somamos esse valor na cor de saida do fragmentShader.



