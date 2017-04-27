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



