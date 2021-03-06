
/**
 * @author Ye SUN(SI4 IMAFA), Ying JIANG(SI4 Groupe3)
 *
 */
import java.util.Hashtable;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class sub implements javax.jms.MessageListener{

    private javax.jms.Connection connect = null;
    private javax.jms.Session receiveSession = null;
    InitialContext context = null;
    static String s=null;
    public String configurer(String topicName, String ClientID) throws JMSException {

        try
        {   // Create a connection
            Hashtable properties = new Hashtable();
            properties.put(Context.INITIAL_CONTEXT_FACTORY, 
                "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
            properties.put(Context.PROVIDER_URL, "tcp://localhost:61616");

            context = new InitialContext(properties);

            javax.jms.ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
            connect = factory.createConnection();
            //for durable connection
            connect.setClientID(ClientID);
            this.configurerSouscripteur(topicName,ClientID); 
            connect.start(); // on peut activer la connection. 
        } catch (javax.jms.JMSException jmse){
            jmse.printStackTrace();
        } catch (NamingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return s;
    }
    private void configurerSouscripteur(String topicName, String ClientID) throws JMSException, NamingException{
        // Pour consommer, il faudra simplement ouvrir une session 
        receiveSession = connect.createSession(false,javax.jms.Session.AUTO_ACKNOWLEDGE);  
        // et dire dans cette session quelle queue(s) et topic(s) on acc�dera et dans quel mode
        Topic topic = (Topic) context.lookup("dynamicTopics/"+topicName);
       // durable receiver
        javax.jms.MessageConsumer topicReceiver = receiveSession.createDurableSubscriber(topic, ClientID);
        topicReceiver.setMessageListener(this); 
        //ESSAI d'une reception synchrone
        connect.start(); // on peut activer la connection. 
/*
        while (true){
            Message msg= topicReceiver.receive();
//          System.out.println("new tweet: "); 
            //onMessage(msg);
            TextMessage textMessage = (TextMessage) msg;
            String s= textMessage.getText();
            return s;
        }
*/
    }

    @Override
    public void onMessage(Message message) {
        try {
            TextMessage textMessage = (TextMessage) message;
            s = textMessage.getText();    
            System.out.println(textMessage.getText());
        } catch (JMSException jmse) {
            jmse.printStackTrace();
        }
    }

    public String getMessage(){
        return s;
    }

}