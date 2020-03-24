package org.jdesktop.lg3d.apps.jmf23D;

import java.io.*;

import java.security.*;

import java.util.Hashtable;

import javax.media.Buffer;

import net.jxta.document.*;

import net.jxta.endpoint.*;

import net.jxta.id.*;

import net.jxta.peer.*;

import net.jxta.peergroup.*;

import net.jxta.pipe.*;

import net.jxta.platform.*;

import net.jxta.protocol.*;

/**
* Handles sending and receiving video/audio data 
* to and from a jxta Peergroup using JXTA's <code>PropagatePipe</code>s.
* @see <a href="http://jxme.jxta.org/nonav/cdc/javadoc/net/jxta/peergroup/PeerGroup.html">net.jxta.peergroup.Peergroup</a>
**/
public class JXTAManager {

    static protected PeerGroup appGroup;
    static protected Hashtable<String, JXTAManager> managers = new Hashtable<String, JXTAManager>();
    PeerGroup myGroup;
    PeerAdvertisement myAdv;
    PipeAdvertisement myPipeAdvertisement;
    InputPipe inputPipe;
    OutputPipe outputPipe;
    PeerID peerID;
    String groupName;
    ObjectOutputStream oos;
    ByteArrayOutputStream bas;
    ObjectInputStream ois;
    ByteArrayInputStream bis;

    public JXTAManager(String groupName) {

        try {

            if (appGroup == null)
                appGroup = PeerGroupFactory.newNetPeerGroup();

            bas = new ByteArrayOutputStream(1024);
            this.groupName = groupName;

            PeerGroupID groupID = (PeerGroupID)createPeerGroupID(appGroup.getPeerGroupID(), 
                                                                 Math.E + 
                                                                 groupName + 
                                                                 Math.PI);
            PeerGroupAdvertisement myAdv = (PeerGroupAdvertisement)new LightWeightPeerGroup(appGroup.getPeerGroupAdvertisement()).getPeerGroupAdvertisement();
            myAdv.setName(groupName);
            myAdv.setDescription("PeerGroup for video conferencing");
            myAdv.setPeerGroupID(groupID);
            myGroup = appGroup.newGroup(myAdv);

            //myGroup.init(appGroup, groupID, myAdv);
            //myGroup.startApp(null);
            peerID = myGroup.getPeerID();
            myGroup.publishGroup(myGroup.getPeerGroupName(), 
                                 myGroup.getPeerGroupName());

            PipeID pid = createPipeID(groupID, "dataPropagatePipe");
            myPipeAdvertisement = (PipeAdvertisement)AdvertisementFactory.newAdvertisement(PipeAdvertisement.getAdvertisementType());
            myPipeAdvertisement.setPipeID(pid);
            myPipeAdvertisement.setType(PipeService.PropagateType);
            myPipeAdvertisement.setName(groupName + "/DataPipe");
            myPipeAdvertisement.setDescription("A Propagate pipe.");
            myGroup.getDiscoveryService().remotePublish(myPipeAdvertisement);
        } catch (net.jxta.exception.PeerGroupException pge) {
            pge.printStackTrace();
        }
         catch (IOException io) {
            io.printStackTrace();
        }
    }

    public void createInputPipe()
                         throws IOException {

        if (inputPipe == null) {
            inputPipe = myGroup.getPipeService().createInputPipe(
                                myPipeAdvertisement);
        }
    }

    public void createOutputPipe()
                          throws IOException {

        if (outputPipe == null) {
            outputPipe = myGroup.getPipeService().createOutputPipe(
                                 myPipeAdvertisement, 1000);
        }
    }

    public static JXTAManager createManager(String groupName) {

        JXTAManager val = managers.get(groupName);

        if (val == null) {
            val = new JXTAManager(groupName);
            managers.put(groupName, val);
        }

        return (JXTAManager)val;
    }

    public ModuleSpecID createModuleSpecID() {

        try {

            ModuleClassID classID = IDFactory.newModuleClassID();
            ModuleSpecID moduleSpecID = IDFactory.newModuleSpecID(classID);
            ModuleClassAdvertisement mcadv = (ModuleClassAdvertisement)AdvertisementFactory.newAdvertisement(ModuleClassAdvertisement.getAdvertisementType());
            mcadv.setName("JXTAMOD:" + groupName);
            mcadv.setDescription(
                    "Tutorial example to use JXTA module advertisement Framework");
            mcadv.setModuleClassID(classID);
            appGroup.getDiscoveryService().publish(mcadv);
            appGroup.getDiscoveryService().remotePublish(mcadv);

            ModuleSpecAdvertisement mdadv = (ModuleSpecAdvertisement)AdvertisementFactory.newAdvertisement(ModuleSpecAdvertisement.getAdvertisementType());
            mdadv.setName("JXTASPEC:" + groupName);
            mdadv.setVersion("Version 1.0");
            mdadv.setCreator("sun.com");
            mdadv.setModuleSpecID(moduleSpecID);
            mdadv.setSpecURI("http://lg3d.dev.java.net/Algea3D");

            return moduleSpecID;
        } catch (IOException io) {
            io.printStackTrace();
        }

        return null;
    }

    //DataSink will send each Buffer of video there
    public void sendBuffer(Buffer buffer)
                    throws IOException {
        bas.reset();

        try {
            oos = new ObjectOutputStream(bas);
        } catch (IOException io) {
            io.printStackTrace();
        }

        SerializableBuffer sbuf = SerializableBuffer.fromBuffer(buffer);
        sbuf.setSenderID(peerID);
        oos.writeObject(sbuf);
        oos.flush();
        oos.close();

        byte[] objectData = bas.toByteArray();
        Message message = new Message();
        MessageElement element = new ByteArrayMessageElement(
                                         "serialized video data", null, 
                                         objectData, null);
        message.addMessageElement(element);
        outputPipe.send(message);
    }

    //DataSource will poll each Buffer of video from here
    public SerializableBuffer waitForBuffer()
                                     throws IOException, InterruptedException {

        Message message = inputPipe.poll(Integer.MAX_VALUE);
        ByteArrayMessageElement bame = (ByteArrayMessageElement)message.getMessageElement(
                                               "serialized video data");
        ois = new ObjectInputStream(bame.getStream());

        SerializableBuffer sbuf = null;

        try {
            sbuf = (SerializableBuffer)ois.readObject();
        } catch (ClassNotFoundException cnf) {
        }

        return sbuf;
    }

    public static final PeerGroupID createPeerGroupID(PeerGroupID parentPeerGroupID, 
                                                      String clearTextID) {

        byte[] digest = generateHash(clearTextID);
        PeerGroupID peerGroupID = IDFactory.newPeerGroupID(parentPeerGroupID, 
                                                           digest);

        return peerGroupID;
    }

    public static final byte[] generateHash(String clearTextID) {

        String id = clearTextID;
        byte[] buffer = id.getBytes();
        MessageDigest algorithm = null;

        try {
            algorithm = MessageDigest.getInstance("MD5");
        } catch (Exception e) {

            return null;
        }

        // Generate the digest.
        algorithm.reset();
        algorithm.update(buffer);

        try {

            byte[] digest1 = algorithm.digest();

            return digest1;
        } catch (Exception de) {

            return null;
        }
    }

    public static final net.jxta.pipe.PipeID createPipeID(net.jxta.peergroup.PeerGroupID peerGroupID, 
                                                          String clearTextID) {

        byte[] digest = generateHash(clearTextID);

        return (net.jxta.pipe.PipeID)net.jxta.id.IDFactory.newPipeID(
                       peerGroupID, digest);
    }
}