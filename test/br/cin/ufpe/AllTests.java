package br.cin.ufpe;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TestComandos.class, TestExpressoes.class, TestComentarios.class, TestFuncoes.class })
public class AllTests {

}
