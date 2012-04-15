grammar Portujava;

options {
  language  = Java;
  backtrack = true;
}

@header {
package br.cin.ufpe;
  
  import java.util.ArrayList;
  import br.cin.ufpe.ast.*;
}

@lexer::header {
package br.cin.ufpe;
}

programa returns [Programa rv]
@init {
ArrayList<Comando> comandos = new ArrayList<Comando>();
}
  :
  (comando 
          {
           comandos.add($comando.rv);
          })* 
             {
              rv = new Programa(comandos);
             }
  ;

bloco returns [Bloco rv]
@init {
ArrayList<Comando> comandos = new ArrayList<Comando>();
}
  :
  '{' (comando 
              {
               comandos.add($comando.rv);
              })* '}' 
                     {
                      rv = new Bloco(comandos);
                     }
  ;

comando returns [Comando rv]
  :
  (
     cmd=comando_pontoVirgula|cmd=comando_fluxo
     
     
  )
  
  {
   $rv = $cmd.rv;
  }
  ;

comando_pontoVirgula returns[Comando rv]
  :
  (cmd=comando_execucao ';') 
  
                            {
                             $rv = $cmd.rv;
                            }
  ;

comando_execucao returns [Comando rv]
  :
  (
    cmd=retornar
    | cmd=atribuicao
    | cmd=comando_expressao
  )
  
  {
   $rv = $cmd.rv;
  }
  ;

comando_fluxo returns [Comando rv]
  :
  (
    cmd=declaracao_de_funcao
    | cmd=se
    | cmd=enquanto
    | cmd=para
  )
  
  {
   $rv = $cmd.rv;
  }
  ;

comando_expressao returns [Comando rv]
  :
  expressao 
           {
            $rv = $expressao.rv;
           }
  ;

retornar returns [Comando rv]
  :
  'retornar' (exp=expressao)? 
                             {
                              $rv = new Retornar($exp.rv);
                             }
  ;

declaracao_de_funcao returns [Comando rv]
@init {
ArrayList<String> parametros = new ArrayList<String>();
}
  :
  ('funcao' nome=identificador '(' (p1=identificador 
                                                    {
                                                     parametros.add($p1.rv.getNome());
                                                    }
      (',' pn=identificador 
                           {
                            parametros.add($pn.rv.getNome());
                           })*)? ')' bloco) 
                                           {
                                            $rv = new DeclaracaoDeFuncao($nome.rv, parametros, $bloco.rv);
                                           }
  ;

atribuicao returns [Atribuicao rv]
  :
  (identificador '=' expressao) 
                               {
                                $rv = new Atribuicao($identificador.rv, $expressao.rv);
                               }
  ;

se returns [Comando rv]
  :
  (
    'se' (exp=expressao_entre_parentesis bloco1=bloco) ('senao' bloco2=bloco)?
  )
  
  {
   $rv = new Se($exp.rv, $bloco1.rv, $bloco2.rv);
  }
  ;

enquanto returns [Comando rv]
  :
  ('enquanto' exp=expressao_entre_parentesis bloco) 
                                                   {
                                                    $rv = new Enquanto($exp.rv, $bloco.rv);
                                                   }
  ;

para returns [Comando rv]
  :
  ('para(' atr=atribuicao ';' exp=comparacao ';' com=comando_execucao ')' bl=bloco) 
                                                                                   {
                                                                                    $rv = new Para($atr.rv, $exp.rv, $com.rv, $bl.rv);
                                                                                   }
  ;

// Para facilitar a leitura, definimos as expressoes em ordem inversa de precedencia

expressao returns [Expressao rv]
  :
  (
    exp=funcao
    | exp=expressao_binaria
  )
  
  {
   $rv = $exp.rv;
  }
  ;

funcao returns [Expressao rv]
@init {
ArrayList<String> parametros = new ArrayList<String>();
}
  :
  ('funcao' '(' (p1=identificador 
                                 {
                                  parametros.add($p1.rv.getNome());
                                 }
      (',' pn=identificador 
                           {
                            parametros.add($pn.rv.getNome());
                           })*)? ')' bloco) 
                                           {
                                            $rv = new Funcao(parametros, $bloco.rv);
                                           }
  ;

expressao_binaria returns [Expressao rv]
  :
  exp=disjuncao 
               {
                $rv = $exp.rv;
               }
  ;

disjuncao returns [Expressao rv]
  :
  (esq=conjuncao 
                {
                 $rv = $esq.rv;
                }) (op='||' dir=conjuncao 
                                         {
                                          $rv = new ExpressaoBinaria($rv, $dir.rv, $op.text);
                                         })*
  ;

conjuncao returns [Expressao rv]
  :
  (esq=comparacao 
                 {
                  $rv = $esq.rv;
                 }) (op='&&' dir=comparacao 
                                           {
                                            $rv = new ExpressaoBinaria($rv, $dir.rv, $op.text);
                                           })*
  ;

comparacao returns [Expressao rv]
  :
  (esq=bitwise 
           {
            $rv = $esq.rv;
           })
  (
    op=
    (
      '>='
      | '>'
      | '<='
      | '<'
      | '=='
      | '!='
    )
    dir=bitwise 
            {
             $rv = new ExpressaoBinaria($rv, $dir.rv, $op.text);
            }
  )*
  ;
  
bitwise returns [Expressao rv]
:
(esq=soma 
           {
            $rv = $esq.rv;
           })
  (
    op=
    (
      '&'
      |'|'
      |'>>'
      |'<<'
    )
    dir=soma 
            {
             $rv = new ExpressaoBinaria($rv, $dir.rv, $op.text);
            }
  )*
  ;


soma returns [Expressao rv]
  :
  (esq=multiplicacao 
                    {
                     $rv = $esq.rv;
                    })
  (
    op=
    (
      '+'
      | '-'
    )
    dir=multiplicacao 
                     {
                      $rv = new ExpressaoBinaria($rv, $dir.rv, $op.text);
                     }
  )*
  ;

multiplicacao returns [Expressao rv]
  :
  (esq=expressao_primaria 
                         {
                          $rv = $esq.rv;
                         })
  (
    op=
    (
      '*'
      | '/'
    )
    dir=expressao_primaria 
                          {
                           $rv = new ExpressaoBinaria($rv, $dir.rv, $op.text);
                          }
  )*
  ;

expressao_primaria returns [Expressao rv]
  // regra que serve para identificar as expressoes de maior precedencia na linguagem
  // por enquanto somente �tomos e chamadas de funcao est�o nesta categoria, futuramente
  // pode ser usada para outras opera�oes, tipo accessar o atributos de uma instancia
  :
  atomo 
       {
        $rv = $atomo.rv;
       }
  ( ('(' l=lista_de_expressoes ')' 
                                  {
                                   $rv = new ChamadaDeFuncao($rv, $l.rv);
                                  }))*
  ;

lista_de_expressoes returns [List < Expressao > rv]
@init {
ArrayList<Expressao> expressoes = new ArrayList<Expressao>();
}
  :
  (exp1=expressao 
                 {
                  expressoes.add($exp1.rv);
                 }
    (',' expn=expressao 
                       {
                        expressoes.add($expn.rv);
                       })*)? 
                            {
                             $rv = expressoes;
                            }
  ;

atomo returns [Expressao rv]
  // Nessa regra temos que atribuir o valor de retorno dentro de cada caso
  // pois a regra 'identificador' retorna um objeto do tipo 'Identificador'
  // e as outras regras retornam objetos do tipo 'Expressao'. Normalmente isso
  // nao seria problema pois um 'Identificador' � tambem uma 'Expressao' mas
  // o antlr nao aceita isso
  :
  (
    (expressao_entre_parentesis 
                               {
                                $rv = $expressao_entre_parentesis.rv;
                               })
    | (expressao_unaria 
                       {
                        $rv = $expressao_unaria.rv;
                       })
    | (valor 
            {
             $rv = $valor.rv;
            })
    | (identificador 
                    {
                     $rv = $identificador.rv;
                    })
  )
  ;

expressao_entre_parentesis returns [Expressao rv]
  :
  '(' exp=expressao_binaria ')' 
                               {
                                $rv = $exp.rv;
                               }
  ;

expressao_unaria returns [Expressao rv]
  :
  (
    op=
    (
      '+'
      | '-'
      | '!'
    )
    exp=atomo 
             {
              $rv = new ExpressaoUnaria($op.text, $exp.rv);
             }
  )
  ;

valor returns [Expressao rv]
  :
  (
    exp=decimal
    | exp=inteiro
    | exp=texto
    | exp=booleano
  )
  
  {
   $rv = $exp.rv;
  }
  ;

identificador returns [Identificador rv]
  :
  (t=IDENTIFICADOR) 
                   {
                    $rv = new Identificador($t.text);
                   }
  ;

decimal returns [Expressao rv]
  :
  t=DECIMAL 
           {
            $rv = new Decimal($t.text);
           }
  ;

inteiro returns [Expressao rv]
  :
  t=INTEIRO 
           {
            $rv = new Inteiro($t.text);
           }
  ;

texto returns [Expressao rv]
  :
  t=TEXTO 
         {
          $rv = new Texto($t.text);
         }
  ;

booleano returns [Expressao rv]
  :
  (
    t=VERDADEIRO
    | t=FALSO
  )
  
  {
   $rv = new Booleano($t.text);
  }
  ;

DECIMAL
  :
  ('0'..'9')+ '.' ('0'..'9')*
  ;

INTEIRO
  :
  ('0'..'9')+
  ;

TEXTO
@init {
StringBuilder sb = new StringBuilder();
}
  :
  (
    '\''
    (
      (
        c=
        (
          '\\'
          (
            ('n' 
                {
                 sb.append("\n");
                })
            | ('\'' 
                   {
                    sb.append("'");
                   })
            | ('\\' 
                   {
                    sb.append("\\");
                   })
          )
        )
      )
      |
      (
        c=
        ~(
          '\''
          | '\\'
          | '\n'
         )
        
        {
         sb.appendCodePoint($c);
        }
      )
    )*
    '\''
  )
  
  {
   setText(sb.toString());
  }
  ;

VERDADEIRO
  :
  'verdadeiro'
  ;

FALSO
  :
  'falso'
  ;

IDENTIFICADOR
  :
  (
    'a'..'z'
    | 'A'..'Z'
  )
  (
    'a'..'z'
    | 'A'..'Z'
    | '0'..'9'
  )*
  ;

ESPACO_EM_BRANCO
  :

  (' '|'\n'|'\r'|'\t')+
  { $channel = HIDDEN; }
  ;

  
 

COMENTARIO_LINHA
  :
  (
    '//'
    ~(
      '\n'
      | '\r'
     )*
    '\r'? '\n'
  )
  
  {
   $channel = HIDDEN;
  }
  ;

COMENTARIO_BLOCO
  :
  ('/*' .* '*/') 
                {
                 $channel = HIDDEN;
                }
  ;
