package org.jdesktop.lg3d.apps.jmf23D;

import java.util.*;
import java.util.concurrent.*;

import javax.media.*;
import javax.media.protocol.*;

import net.jxta.peer.*;

import org.jdesktop.lg3d.apps.jmf23D.*;

/**
* A javax.media.DataSource that reads data from JXTA.
*
**/
public class JXTADataSource
    extends javax.media.protocol.PullBufferDataSource implements Runnable {

    public static final Object[] controls = {  };
    protected static BlockingQueue<JXTADataSource> waiting = new LinkedBlockingQueue<JXTADataSource>();
    protected static Map<PeerID, JXTADataSource> map = new HashMap<PeerID, JXTADataSource>();
    protected static ExecutorService fillThread;

    protected JXTAPullBufferStream audioStream;
    protected JXTAPullBufferStream videoStream;
    protected JXTAManager manager;
    protected MediaLocator ml;
    protected boolean started = false;
    protected boolean added = false;
    protected int numPeersExpected = 2;
    
    class JXTAPullBufferStream
        implements PullBufferStream {

        protected ContentDescriptor cd;
        protected Format format;
        protected Map<Long, SerializableBuffer> buffers;
        long sequenceNumber = 0;
	long mapSize = 0;

        JXTAPullBufferStream() {
            cd = new ContentDescriptor(ContentDescriptor.RAW);
            buffers = Collections.synchronizedMap(new WeakHashMap<Long, SerializableBuffer>());
        }

        public void addBuffer(Buffer b) {
	    if(b.getFormat()!=null)
		    format = b.getFormat();
            buffers.put(b.getSequenceNumber(), 
                        SerializableBuffer.fromBuffer(b));
	    mapSize = (mapSize>b.getSequenceNumber())?mapSize:b.getSequenceNumber();
        }


        public Format getFormat() {
	    for(int i=0;format==null&i<200;i++)
	    try{
		    Thread.sleep(20);
	    } catch(Exception e){}
            return format;
        }

        public void read(Buffer buffer) {
		try {
            SerializableBuffer sbuf = null;

	    for(int i=0;i<3;i++){
            if ((sbuf = buffers.remove(sequenceNumber)) == null) {

                try {
                    Thread.sleep(5/(mapSize-sequenceNumber+1));
                } catch (InterruptedException ie) {
                }
            } else {
		    if(sbuf.getFormat()==null)
			    sbuf.setFormat(format);
		    buffer.copy(sbuf);
		    return;
	    }
	    }
	    if(sequenceNumber<mapSize)
		    sequenceNumber++;
	    read(buffer);
		}catch(Exception e){
		e.printStackTrace();
		}

        }

        public boolean willReadBlock() {

            return true;
        }

        public boolean endOfStream() {

            return false;
        }

        public ContentDescriptor getContentDescriptor() {

            return cd;
        }

        public long getContentLength() {

            return LENGTH_UNKNOWN;
        }

        public Control getControl(String name) {

            return null;
        }

        public Control[] getControls() {

            return new Control[0];
        }
    }

    public JXTADataSource() {
    }

    public JXTADataSource(MediaLocator ml) {
        setLocator(ml);
    }

    public String getContentType() {

        return ContentDescriptor.RAW;
    }

   public void connect()
                 throws java.io.IOException {
	    if (!getLocator().getProtocol().equals("alg3d"))
		    throw new java.io.IOException("protocol not supported");
	audioStream = new JXTAPullBufferStream();
	videoStream = new JXTAPullBufferStream();
    }

    public void disconnect() {
    }

    public void start()
               throws java.io.IOException {
		       if(!added){
	try{
		waiting.put(this);
	} catch(InterruptedException ie){}
		       }
	added = true;
		    
        manager = JXTAManager.createManager(getLocator().getRemainder());
        manager.createInputPipe();
	if(fillThread ==null) {
		fillThread = Executors.newSingleThreadExecutor();
		fillThread.submit(this);
	}
	started = true;
	}

    public void stop()
              throws java.io.IOException {
	if(started);
	//started = false;
    }

    public Time getDuration() {

        return Time.TIME_UNKNOWN;
    }

    public Object[] getControls() {

        return controls;
    }

    public Object getControl(String controlType) {
        return null;
    }

    public boolean isRandomAccess() {
        return false;
    }

    public Time setPosition(Time where, int rounding) {

        Time newPosition = where;

        return newPosition;
    }

    public PullBufferStream[] getStreams() {

	    return new PullBufferStream[]{audioStream, videoStream};
    }
    
    public JXTAPullBufferStream getStream(AVType av) {
	    switch(av) {
		    case VIDEO: return videoStream;
		    case AUDIO: return audioStream;
	    }
	    return null;
    }

    enum AVType {VIDEO, AUDIO};
    public void run() {
	    
	SerializableBuffer sbuf;
        while (true) {

            try {
                sbuf = manager.waitForBuffer();
            } catch (Exception ex) {
		 ex.printStackTrace();
                continue;
            }
	    
            if (!map.containsKey(sbuf.getSenderID())) {
		    try {
		    JXTADataSource newSource = waiting.take();
		    System.out.println(newSource);
		    map.put(sbuf.getSenderID(), newSource);
		    } catch(InterruptedException ie) {
			    ie.printStackTrace();
			    continue;
		    }
            } else {
		    System.out.print(map.size()+" ");
		    
		    AVType avType = (sbuf.getFormat() instanceof javax.media.format.VideoFormat)?AVType.VIDEO:AVType.AUDIO;
		    JXTADataSource ds = map.get(sbuf.getSenderID());
		    ds.getStream(avType).addBuffer(sbuf);
		    
            }
        }
    }
}
