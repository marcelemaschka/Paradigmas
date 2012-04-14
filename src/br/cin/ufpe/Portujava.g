grammar Portujava;


options {
  language = Java;
}

@header {
  package br.cin.ufpe;
  
  import br.cin.ufpe.ast.*;
}

@lexer::header {
  package br.cin.ufpe;
}


programa returns [Expressao rv]
  :
  exp=expressao
  { $rv = $exp.rv; }
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
  :
  (
    exp=expressao_entre_parentesis
  | exp=expressao_unaria 
  | exp=valor 
  )
  { $rv = $exp.rv; }
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
  
ESPACO_EM_BRANCO
  :
  (' ')+
  { $channel = HIDDEN; }
  ;
