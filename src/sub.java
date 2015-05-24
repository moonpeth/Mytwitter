import java.util.Hashtable;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class sub implements MessageListener{

    private javax.jms.Connection connect = null;
    private javax.jms.Session receiveSession = null;
    InitialContext context = null;
    public void configurer(String topicName) throws JMSException {
        
        try
        {   // Create a connection
            Hashtable properties = new Hashtable();
            properties.put(Context.INITIAL_CONTEXT_FACTORY, 
                "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
            properties.put(Context.PROVIDER_URL, "tcp://localhost:61616");

            context = new InitialContext(properties);

            javax.jms.ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
            connect = factory.createConnection();
        
            this.configurerSouscripteur(topicName); 
            connect.start(); // on peut activer la connection. 
        } catch (javax.jms.JMSException jmse){
            jmse.printStackTrace();
        } catch (NamingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    private void configurerSouscripteur(String topicName) throws JMSException, NamingException{
        // Pour consommer, il faudra simplement ouvrir une session 
        receiveSession = connect.createSession(false,javax.jms.Session.AUTO_ACKNOWLEDGE);  
        // et dire dans cette session quelle queue(s) et topic(s) on acc��dera et dans quel mode
        Topic topic = (Topic) context.lookup("dynamicTopics/"+topicName);
        System.out.println("Following twitter profile: " + topic.getTopicName());
        javax.jms.MessageConsumer topicReceiver = receiveSession.createConsumer(topic);//,"Conso");//,"typeMess = 'important'");
        //topicReceiver.setMessageListener(this); 
        //ESSAI d'une reception synchrone
        connect.start(); // on peut activer la connection. 
        while (true){
            Message m= topicReceiver.receive();
            System.out.println("new tweet: "); onMessage(m);
        }
    }

    @Override
    public void onMessage(Message message) {
        try {
            TextMessage textMessage = (TextMessage) message;
            System.out.println(textMessage.getText());
        } catch (JMSException jmse) {
            jmse.printStackTrace();
        }
    }

}