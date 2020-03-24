/*
 * JLayerMP3Player.java
 *
 * Created on 2006/09/12, 12:26
 * Original Version Created on 2003/11/26, 17:32
 *
 */

package org.jdesktop.lg3d.apps.trumplayer.mp3player;

import java.io.*;
import java.net.*;

/* ***** move from JMF to JLayer 1.0 *****
import javax.media.*;
import javax.media.Player;
import javax.media.Manager;
import javax.media.bean.playerbean.MediaPlayer;
import javax.media.protocol.URLDataSource;
import javax.media.control.*;
import javax.media.format.*;
*/

import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;
import javazoom.jl.player.AudioDevice;
import javazoom.jl.player.FactoryRegistry;
import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.Header;

import java.util.*;
import org.jdesktop.lg3d.apps.trumplayer.mp3util.ID3Tag;
import org.jdesktop.lg3d.apps.trumplayer.mp3util.ID3TagReader;

/**
 *
 * @author Yasuhiro Fujitsuki (thaniwa)
 */
public class JLayerMP3Player {

    public final static int STOP = 0;
    public final static int PLAY = 1;
    public final static int PAUSE = 2;

    // private AdvancedPlayer player = null;
    private Player player = null;
    private int state = STOP;

    private ID3Tag id3tag = null;
    private String filename = null;
    private float volume = (float) 3.0;

    private int filesize = 0;
    private int bitrate = 0;
    private int bitrates[] = new int[] { 0, 32, 40, 48, 56, 64, 80, 96, 112, 128, 160, 192, 224, 256, 320, 0, 0 };

    private AbstractList<FileInfoBean> playList = new ArrayList<FileInfoBean>();
    private int current = 0;
    
    private int frame = 0;
    
    // charactor encode information of ID3Tag
    private String encode = null;

    public AbstractList<FileInfoBean> getPlayList(){ return playList; }
    public void setPlayList(AbstractList<FileInfoBean> list){ this.playList = list; }
    public int getCurrent(){ return current; }
    public void setCurrent(int i){ current = i; }


    /** Creates a new instance of MP3Player
     */
    public JLayerMP3Player() {
    }
    
    /** Creates a new instance of MP3Player with charactor encode information
     */
    public JLayerMP3Player(String encode){
        this();
        this.encode = encode;
    }

    /** Play previous music file. 
     */
    public void playPrev(){
        if( current - 1 >= 0 ){
            current --;
            play(playList,current);
        }
    }

    /** Play next music file. 
     */
    public void playNext(){
        for(int i=0; i < playList.size(); i++){
            FileInfoBean f = playList.get(i);
            // System.out.println(f.getFilePath() );
        }

        if( current + 1 < playList.size() ){
            current ++;
            play(playList,current);
        }
    }

    /** Play current music file.
     */
    public void play(){
        play(playList,current);
    }

    /** Play music file list
     */
    public void play(AbstractList<FileInfoBean> list){
        play(list, 0);
    }

    /** Play music.
     */
    public void play(AbstractList<FileInfoBean> list, int number){
        if( list != null ){
            this.playList = list;
            this.current = number;
            if( this.current >= this.playList.size() ) this.current = 0;
            if( this.current < 0 ) this.current = 0;

            if( playList.size() > 0){
                FileInfoBean fib = (FileInfoBean) this.playList.get(current);
                String filename = fib.getFilePath();

                if(this.state == PAUSE){
                    this.player.close();
                }
                
                if( filename == null || filename.equals("") ){
                    String res = prefetch( fib.getURL() );
                    // System.out.println( res );
                    if( res == null || res.equals("") ){
                        playList.remove( current );
                        play( playList, current );
                    } else {
                        fib.setFilePath( res );
                        playList.set( current, fib );
                        playFile( res );
                        state = PLAY;
                        prefetchNext();
                    }
                } else {
                    playFile( filename );
                    state = PLAY;
                    if(this.id3tag != null)
                        fib.setID3Tag(id3tag);
                    prefetchNext();
                }
            }
        }

    }


    /** Prefetch next music file.
     */
    public void prefetchNext(){
        int next = current + 1;
        if( next < playList.size() ){
            FileInfoBean fib = (FileInfoBean) playList.get( next );
            String url = fib.getURL();
            String filename = fib.getFilePath();
            if( filename == null || filename.equals("") ){
                String res = prefetch( fib.getURL() );
                if( res == null ){
                    playList.remove( next );
                    prefetchNext();
                } else {
                    fib.setFilePath( res );
                    playList.set( next, fib );
                }
            }
        }
    }

    /** Prefetch next music file from URL.
     */
    public String prefetch(String url_string){
        /* 
         * delete prefetch func.
         * if you want to need prefetch func,
         * reffer MP3Player.java .
         * In default usage, player does not use this function.
         * This function needs to play mp3 file via http.
         */
        return null;
    }

    private class SkipAdvancedPlayer extends AdvancedPlayer {
        private java.io.InputStream is;
        public SkipAdvancedPlayer(java.io.InputStream is) throws javazoom.jl.decoder.JavaLayerException {
            super(is);
            this.is = is;
        }
        public void skipStart(int num) throws javazoom.jl.decoder.JavaLayerException {
            Bitstream bitstream = new Bitstream(is);
            Header header = bitstream.readFrame();
            float ms_per_frame = header.ms_per_frame();
            int frame = (int)(num/ms_per_frame);
            // System.out.println(num + " / "+ ms_per_frame + "=" +frame);
            for(int i=0; i<frame; i++)
                this.skipFrame();
            play();
            bitstream.close();
        }
    }
    
    private class PlayerThread extends Thread {
        private SkipAdvancedPlayer player;
        private int f;
        public PlayerThread(java.io.InputStream is){
            try{
                player = new SkipAdvancedPlayer(is);
                player.setPlayBackListener(new PlaybackListener(){
                    public void playbackFinished(PlaybackEvent evt){
                        // frame = evt.getFrame();
                        frame += evt.getFrame();
                        if(state == PLAY)
                            playNext();
                    }
                    public void playbackStarted(PlaybackEvent evt){                    
                        // System.out.println(evt.getFrame());
                    }
                });
            } catch(Exception e){ e.printStackTrace(); }
        }
        
        public SkipAdvancedPlayer getAdvancedPlayer() { return player; }
        public PlayerThread(SkipAdvancedPlayer player){
            this.player = player;
        }

        public void setFrame(int frame){ this.f = frame; }
        public void stopPlayer(){
            try{
                player.stop();
            } catch (Exception e){ e.printStackTrace(); }
            // closePlayer();
        }
        public void closePlayer(){
            player.close();
        }        
        public void run(){
            if(player != null){
                try{
                    // player.play();
                    if(f == 0){
                        frame = 0;
                        player.play();
                    }
                    else
                        player.skipStart(frame);
                        // player.play(frame, Integer.MAX_VALUE);
                } catch (Exception e){ e.printStackTrace(); }
            }
        }
    }

    private class Player {
        private File file = null;
        private PlayerThread thread = null;
        public Player(File file){ this.file = file; }

        public void play(){
            play(0);
        }
        
        public void play(int frame){
            if(thread != null){
                thread.closePlayer();
            }
            try{
                FileInputStream fis = new FileInputStream(file);
                thread = new PlayerThread( fis );
                thread.setFrame(frame);
                thread.start();
            } catch (java.io.IOException e){ e.printStackTrace(); }
        }
        
        public void close(){
            thread.closePlayer();
        }
        
        public void stop(){
            thread.stopPlayer();
        }
    }


    public boolean playFile(String filename){
        try{

            this.filename = filename;

            // load a music file
            File file = new File( filename );

            FileInputStream fis = new FileInputStream( file );
            byte b[] = new byte[4];
            fis.read(b);
            // System.out.println(b[0] + " " + b[1] + " " + b[2]);
            int bit = 16 + b[2]/16;
            if( bit > 15 )
                bitrate = 0;
            else
                bitrate = bitrates[bit];

            // get ID3Tag information
            this.id3tag = ID3TagReader.getID3Tag( filename , encode);
            // file size
            if( this.id3tag == null )
                filesize = (int)file.length() * 8;
            else
                filesize = ((int)file.length() - 128) * 8;

            fis.close();
            fis = new FileInputStream( file );
            
            // AdvancedPlayer p = new AdvancedPlayer(fis);
            // Player p = new Player(fis);
            Player p = new Player( file );

            // play music
            if(player != null){
                player.close();
            }
            player=p;

            /* *****
            player.setPlayBackListener(new PlaybackListener(){
                public void playbackFinished(PlaybackEvent evt){
                    System.out.println(evt.toString());
                    frame = evt.getFrame();
                    if(state == PLAY)
                        playNext();
                }
                public void playbackStarted(PlaybackEvent evt){                    
                }
            });
             ***** */
            
            player.play();
            // volume(this.volume);

            return true;

        } catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

    /** pause */
    public boolean pause(){
        if(player != null){
            if(state == PLAY){
                state = PAUSE;
                player.stop();
                //player.pause();
            } else {
                state = PLAY;
                player.play(frame);
                //player.resume();
            }
        }
        return true;
    }

    /** stop */
    public boolean stop(){
        if( player != null){
            // player.stop();
            player.close();
        }
        state = STOP;
        return true;
    }

    /** change playing time of current music */
    public boolean move(double sector){
        /*
        Time t = new Time(sector);
        player.setMediaTime(t);
        player.play( (int)sector );
        return true;
         */
        return false;
    }

    /** change volume */
    public boolean volume(float volume){
        // System.out.println("Vol : " + volume);
        // player.setVolumeLevel( Float.toString(volume) );
        this.volume = volume;
        // volume does not supported now
        /* 
        try{
            GainControl gc = player.getGainControl();
            if( gc != null )
                gc.setLevel( volume );
        } catch (Exception e){
        }
        return true;
         */
        return false;
    }

    /** get ID3Tag */
    public ID3Tag getID3Tag(){
        return this.id3tag;
    }

    /** get file name */
    public String getFilename(){
        return this.filename;
    }

    /** get time length of playing file. */
    public double getStopTime(){

        double stoptime = 0;

        if(bitrate != 0)
            stoptime = filesize / (bitrate * 1024);

        return stoptime;
    }

    /** get current time of music */
    public double getCurrentTime(){
        /* 
        Time time = player.getMediaTime();

        if( bitrate == 0 )
            return 0.0;
        else if( time == null )
            return 0.0;
        else
            return time.getSeconds();
         */ 
        return 0.0;
    }

    public void close(){
        player.close();
    }

}
