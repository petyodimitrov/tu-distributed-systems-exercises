package bg.tusofia.cst.ds.camel;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

    public static void main(String[] args) throws Exception {
        AbstractApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext-camel.xml");
        ctx.start();

        System.out.println("Running Camel example...");
        Thread.sleep(5 * 60 * 1000);

        ctx.stop();
        ctx.close();
    }
}