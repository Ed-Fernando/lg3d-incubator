package org.jdesktop.lg3d.apps.jmf23D;

import javax.media.*;
import javax.media.control.*;
import javax.media.format.*;
import javax.media.protocol.*;


public class ConferenceThread extends Thread {

    MediaLocator group;
    MediaLocator vin;
    MediaLocator ain;

    public ConferenceThread(MediaLocator group, MediaLocator vin, 
                            MediaLocator ain) {
        this.group = group;
        this.vin = vin;
        this.ain = ain;
    }

    public void run() {

        try {
	    DataSource vsource = null;
	    DataSource asource = null;
	    DataSource avsource = null;
		try{
			vsource = Manager.createDataSource(vin);
		} catch(Exception e){}
		
		try {
			asource = Manager.createDataSource(ain);
		} catch(Exception e) {}
		if(vsource != null & asource != null) {
			avsource = Manager.createMergingDataSource(
                                          new DataSource[] { vsource, asource });
		} else if( vsource == null)
			avsource = asource;
		else
			avsource = vsource;
		
            Processor proc = Manager.createProcessor(avsource);
            AlgeaUtilities.WaitListener wl = new AlgeaUtilities.WaitListener(proc);
            proc.configure();
            wl.waitForState(proc.Configured);
            proc.setContentDescriptor(new ContentDescriptor(
                                              ContentDescriptor.RAW_RTP));

            for (TrackControl trackCont: proc.getTrackControls()) {

                if (trackCont.getFormat() instanceof VideoFormat) {
                    trackCont.setCodecChain(
                            new Codec[] {
                        new com.ibm.media.codec.video.h263.NativeEncoder()
                    });
                    trackCont.setFormat(new VideoFormat(VideoFormat.H263_RTP));
                }

                if (trackCont.getFormat() instanceof AudioFormat) {
                    trackCont.setCodecChain(
                            new Codec[] {
                        new com.ibm.media.codec.audio.gsm.JavaEncoder()
                    });
                    trackCont.setFormat(new AudioFormat(AudioFormat.GSM_RTP));
                }
            }

            proc.realize();
            wl.waitForState(proc.Realized);
            proc.start();

            DataSource output = proc.getDataOutput();
            output.connect();
            output.start();

            DataSink groupSink = new JXTADataSink();
            groupSink.setOutputLocator(group);
            groupSink.setSource(output);
            groupSink.open();
            groupSink.start();
            System.out.println("starting vcon");
        } catch (Exception e) {
		e.printStackTrace();
        }

        while (true) {

            try {

                DataSource ds = new JXTADataSource(group);
                ds.connect();
		System.out.println(ds);
                ds.start();

                Player p = AlgeaUtilities.createRealizedPlayer(ds);
		p.start();
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
