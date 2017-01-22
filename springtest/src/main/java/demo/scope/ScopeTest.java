package demo.scope;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

//@Scope("singleton")
@Component
@Scope("prototype")
public class ScopeTest {

}
