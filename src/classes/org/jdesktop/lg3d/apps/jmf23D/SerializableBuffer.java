package org.jdesktop.lg3d.apps.jmf23D;

import java.io.*;

import net.jxta.peer.PeerID;

/**
* A marshallable javax.media.Buffer that travels the JXTA network.
**/
public class SerializableBuffer
    extends javax.media.Buffer
    implements Serializable {

    protected PeerID senderID;
    static final long serialVersionUID = -7357981555693972991L;
    public SerializableBuffer() {
    }

    public static SerializableBuffer fromBuffer(javax.media.Buffer buffer) {

        if (buffer instanceof SerializableBuffer)

            return (SerializableBuffer)buffer;

        SerializableBuffer sbuf = new SerializableBuffer();
        sbuf.copy(buffer);

        return sbuf;
    }

    public void setSenderID(PeerID senderID) {
        this.senderID = senderID;
    }

    public PeerID getSenderID() {

        return senderID;
    }

    private void writeObject(java.io.ObjectOutputStream out)
                      throws IOException {
        out.defaultWriteObject();
        out.writeObject(getFormat());
        //out.writeObject(getHeader());
        out.writeInt(getOffset());
        out.writeInt(getLength());
        out.writeLong(getSequenceNumber());
        out.writeInt(getFlags());
        out.writeObject(getData());
    }

    private void readObject(java.io.ObjectInputStream in)
                     throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        setFormat((javax.media.Format)in.readObject());
        //setHeader(in.readObject());
        setOffset(in.readInt());
        setLength(in.readInt());
        setSequenceNumber(in.readLong());
        setFlags(in.readInt());
        setData(in.readObject());
    }
    
    @Override public int hashCode(){
	    return (int) getSequenceNumber();
    }
}
