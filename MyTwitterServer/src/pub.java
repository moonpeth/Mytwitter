
/**
 * @author Ye SUN(SI4 IMAFA), Ying JIANG(SI4 Groupe3)
 *
 */
import java.util.Hashtable;

import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;


public class pub{

    private javax.jms.Connection connect = null;
    private javax.jms.Session sendSession = null;
    private javax.jms.MessageProducer sender = null;
    InitialContext context = null;
    public void configurer(String topicName,String text) throws JMSException {
        
        try
        {   
            Hashtable properties = new Hashtable();
            properties.put(Context.INITIAL_CONTEXT_FACTORY, 
                "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
            properties.put(Context.PROVIDER_URL, "tcp://localhost:61616");
            context = new InitialContext(properties);
            javax.jms.ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
            connect = factory.createConnection();
            this.configurerPublisher(topicName);
            //connect.start(); // on peut activer la connection. 
        } catch (javax.jms.JMSException jmse){
            jmse.printStackTrace();
        } catch (NamingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        this.writeMessage(text);
    }
    private void configurerPublisher(String topicName) throws JMSException, NamingException{
        // Dans ce programme, on decide que le producteur decouvre la queue (ce qui la cr����ra si le nom n'est pas encore utilis��) 
        // et y accedera au cours d'1 session
        sendSession = connect.createSession(false, javax.jms.Session.AUTO_ACKNOWLEDGE);
        Topic topic = (Topic) context.lookup("dynamicTopics/"+topicName);
        sender = sendSession.createProducer(topic);
        sender.setDeliveryMode(DeliveryMode.PERSISTENT);
    }
     
    private void writeMessage(String text) throws JMSException{
        TextMessage message = sendSession.createTextMessage();
        message.setText(text);
        sender.send(message);
    }
}
