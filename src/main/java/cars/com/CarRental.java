package cars.com;

import org.apache.log4j.BasicConfigurator;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
public class CarRental {

    public static void main(String[] args) {
        try {
            KieServices ks = KieServices.Factory.get();
            BasicConfigurator.configure();
            Logger.getLogger(CarRental.class).setLevel(Level.OFF);
            KieContainer kContainer = ks.getKieClasspathContainer();
            KieSession session = kContainer.newKieSession("ksession-rules");

            Client client2 = new Client(false);
            Rental rental2 = new Rental(CarCategory.CATEGORY.B, true, false, "10/11/2016", "13/11/2016", client2);
            Rental rental3 = new Rental(CarCategory.CATEGORY.A, null, false, "11/11/2016", "14/11/2016", client2);

            session.insert(rental2);
            session.fireAllRules();
            session.insert(rental3);
            session.fireAllRules();

            Client client1 = new Client(true);
            Rental rental1 = new Rental(null, true, true, "11/11/2016", "12/11/2016", client1);
            session.insert (rental1);
            session.fireAllRules();
            session.insert(new Return("12/11/2016", 66, rental1));
            session.fireAllRules();

            session.insert(new Return("12/11/2016", 100, rental2));
            session.fireAllRules();

            Client client3 = new Client(false);
            Rental rental4 = new Rental(CarCategory.CATEGORY.B, false, false, "11/11/2016", "16/11/2016", client3);
            session.insert(rental4);
            session.fireAllRules();

            session.insert(new Return("18/11/2016", 1000, rental2,2,true));
            session.fireAllRules();

            System.out.println("client1: " + client1.getBill());
            System.out.println("client2: " + client2.getBill());
            System.out.println("client3: " + client3.getBill());
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

}
