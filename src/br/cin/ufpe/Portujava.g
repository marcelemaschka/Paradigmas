grammar Portujava;


options {
  language = Java;
}

@header {
  package br.cin.ufpe;
  
  import java.util.ArrayList;
  import br.cin.ufpe.ast.*;
}

@lexer::header {
  package br.cin.ufpe;
}


programa returns [Bloco rv]
  :
  exp=bloco
  { $rv = $exp.rv; }
  ;
  
bloco returns [Bloco rv]
@init {
  ArrayList<Comando> comandos = new ArrayList<Comando>();
}
  :
  '{'
  (comando { comandos.add($comando.rv); })*
  '}'
  { rv = new Bloco(comandos); }
  ;
  
comando returns [Comando rv]
  :
  ( 
    cmd=atribuicao
  | cmd=se
  )
  { $rv = $cmd.rv; }
  ;
  
atribuicao returns [Comando rv]
  :
  (identificador '=' expressao ';')
  { $rv = new Atribuicao($identificador.rv, $expressao.rv); }
  ;
  
se returns [Comando rv]
  :
  ('se' exp=expressao_entre_parentesis bloco )
  { $rv = new Se($exp.rv, $bloco.rv); }
  ;
  
// Para facilitar a leitura, definimos as expressoes em ordem inversa de precedencia
expressao returns [Expressao rv]
  :
  (   
    exp=expressao_binaria  
  )
  { $rv = $exp.rv; }
  ;
  
expressao_binaria returns [Expressao rv]
  :
  exp=disjuncao
  { $rv = $exp.rv; }
  ;
  
disjuncao returns [Expressao rv]
  :
  (esq=conjuncao { $rv = $esq.rv; })
  (op='||' dir=conjuncao { $rv = new ExpressaoBinaria($rv, $dir.rv, $op.text); })*
  ;
  
conjuncao returns [Expressao rv]
  :
  (esq=comparacao { $rv = $esq.rv; })
  (op='&&' dir=comparacao { $rv = new ExpressaoBinaria($rv, $dir.rv, $op.text); })*
  ;
  
comparacao returns [Expressao rv]
  :
  (esq=soma { $rv = $esq.rv; })
  (op=('>='|'>'|'<='|'<'|'=='|'!=') dir=soma { $rv = new ExpressaoBinaria($rv, $dir.rv, $op.text); })?
  ;
  
soma returns [Expressao rv]
  :
  (esq=multiplicacao { $rv = $esq.rv; }) 
  (op=('+'|'-') dir=multiplicacao { $rv = new ExpressaoBinaria($rv, $dir.rv, $op.text) ;})*
  ;  

multiplicacao returns [Expressao rv]
  :
  (esq=atomo { $rv = $esq.rv; }) 
  (op=('*'|'/') dir=atomo { $rv = new ExpressaoBinaria($rv, $dir.rv, $op.text) ;})* 
  ;  
  
atomo returns [Expressao rv] 
// Nessa regra temos que atribuir o valor de retorno dentro de cada caso
// pois a regra 'identificador' retorna um objeto do tipo 'Identificador'
// e as outras regras retornam objetos do tipo 'Expressao'. Normalmente isso
// nao seria problema pois um 'Identificador' é tambem uma 'Expressao' mas
// o antlr nao aceita isso   
  :  
  (
    (expressao_entre_parentesis  { $rv = $expressao_entre_parentesis.rv; })
  | (expressao_unaria  { $rv = $expressao_unaria.rv; })
  | (valor  { $rv = $valor.rv; })
  | (identificador  { $rv = $identificador.rv; })
  )
 
  ; 
       
expressao_entre_parentesis returns [Expressao rv]
  :
  '(' exp=expressao_binaria ')'
  { $rv = $exp.rv; }
  ;
  
expressao_unaria returns [Expressao rv]
  :
  (
    op=('+'|'-') exp=atomo
    { $rv = new ExpressaoUnaria($op.text, $exp.rv); }
  )
  ;
     
valor returns [Expressao rv]
  :
  (exp=decimal|exp=inteiro|exp=texto|exp=booleano) 
  { $rv = $exp.rv; } 
  ;
  
identificador returns [Identificador rv]
  :
  (t=IDENTIFICADOR)
  { $rv = new Identificador($t.text); }
  ;
  
decimal returns [Expressao rv]
  :
  t=DECIMAL
  { $rv = new Decimal($t.text); }
  ;
  
inteiro returns [Expressao rv]
  :
  t=INTEIRO
  { $rv = new Inteiro($t.text); }
  ;
  
texto returns [Expressao rv]
  :
  t=TEXTO
  { $rv = new Texto($t.text); }
  ;
  
booleano returns [Expressao rv]
  :
  (t=VERDADEIRO|t=FALSO)
  { $rv = new Booleano($t.text); }
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
            ('n' { sb.append("\n"); })
          | ('\'' { sb.append("'"); })
          | ('\\' { sb.append("\\"); })
          )
        )
      )
    | (c=~('\''|'\\'|'\n') { sb.appendCodePoint($c); })
    )*
    '\''
  )
  { setText(sb.toString()); }
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
  ('a'..'z'|'A'..'Z')
  ('a'..'z'|'A'..'Z'|'0..9')*
  ;
  
ESPACO_EM_BRANCO
  :
  (' ')+
  { $channel = HIDDEN; }
  ;
