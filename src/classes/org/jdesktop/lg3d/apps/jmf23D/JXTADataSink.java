package org.jdesktop.lg3d.apps.jmf23D;

import java.util.*;
import java.util.concurrent.*;

import javax.media.*;
import javax.media.protocol.*;

import net.jxta.id.*;


/**
* A javax.media.DataSource that writes data to JXTA.
*
**/
public class JXTADataSink implements DataSink,
                                     Runnable {

    JXTAManager manager;
    boolean stop = false;
    DataSource ds;
    SourceStream[] bufferStreams;
    MediaLocator outputMediaLocator;
    ExecutorService exs;
    int counter = 0;

    public JXTADataSink() {
    }

    public Object getControl(String str) {

        return null;
    }

    //parse groupName from locator
    public void setOutputLocator(javax.media.MediaLocator mediaLocator) {
        outputMediaLocator = mediaLocator;

        if (!mediaLocator.getProtocol().equals("alg3d"))

            return;
        else {

            String groupName = mediaLocator.getRemainder();
            manager = JXTAManager.createManager(groupName);
        }
    }

    public void setSource(javax.media.protocol.DataSource dataSource)
                   throws java.io.IOException, 
                          javax.media.IncompatibleSourceException {

        try {

            if (dataSource instanceof PullBufferDataSource) {
                ds = dataSource;
                bufferStreams = ((PullBufferDataSource)dataSource).getStreams();
            } else if (dataSource instanceof PushBufferDataSource) {
                ds = dataSource;
                bufferStreams = ((PushBufferDataSource)dataSource).getStreams();
            }
        } catch (ClassCastException cce) {
            throw new IncompatibleSourceException(cce.getMessage());
        }
    }

    public void removeDataSinkListener(javax.media.datasink.DataSinkListener dataSinkListener) {
    }

    public void addDataSinkListener(javax.media.datasink.DataSinkListener dataSinkListener) {
    }

    public void stop()
              throws java.io.IOException {
        stop = true;
    }

    public void run() {

        SerializableBuffer buffer = new SerializableBuffer();

        while (!stop) {

            //send a Buffer of Datafile
            SourceStream pbs;

            try {

                int n = 10;

                for (int i = 0; i < bufferStreams.length;) {
                    pbs = bufferStreams[i++];

                    if (pbs instanceof PullBufferStream)
                        ((PullBufferStream)pbs).read(buffer);

                    if (pbs instanceof PushBufferStream)
                        ((PushBufferStream)pbs).read(buffer);

                    manager.sendBuffer(buffer);
                    Thread.yield();
                }

                counter++;
                counter %= n;
            } catch (java.io.IOException io) {
            }
        }
    }

    public void start()
               throws java.io.IOException {
        exs.submit(this);
    }

    public void open()
              throws java.io.IOException, SecurityException {
        exs = Executors.newSingleThreadExecutor();
        manager.createOutputPipe();
    }

    public MediaLocator getOutputLocator() {

        return outputMediaLocator;
    }

    public Object[] getControls() {

        return null;
    }

    public String getContentType() {

        return ContentDescriptor.RAW_RTP;
    }

    public void close() {
    }
}