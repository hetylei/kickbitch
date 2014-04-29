//$Id: UUIDHexGenerator.java,v 1.10 2004/06/04 01:27:40 steveebersole Exp $
package com.winnie.pub.utils;

import java.net.InetAddress;

/**
 * <b>uuid.hex</b><br>
 * <br>
 * A <tt>UUIDGenerator</tt> that returns a string of length 32,
 * This string will consist of only hex digits. Optionally, 
 * the string may be generated with seperators between each 
 * component of the UUID.
 *
 * @author Gavin King
 */

public class UUIDHexGenerator {
    private static final int IP;
    //private static final char sep = '^';
    static {
        int ipadd;
        try {
            ipadd = BytesHelper.toInt( InetAddress.getLocalHost().getAddress() );
        }
        catch (Exception e) {
            ipadd = 0;
        }
        IP = ipadd;
    }
    private static short counter = (short) 0;
    private static final int JVM = (int) ( System.currentTimeMillis() >>> 8 );


    protected int getJVM() {
        return JVM;
    }

    /**
     * Unique in a millisecond for this JVM instance (unless there
     * are > Short.MAX_VALUE instances created in a millisecond)
     */
    protected short getCount() {
        synchronized(UUIDHexGenerator.class) {
            if (counter<0) counter=0;
            return counter++;
        }
    }

    /**
     * Unique in a local network
     */
    protected int getIP() {
        return IP;
    }

    /**
     * Unique down to millisecond
     */
    protected short getHiTime() {
        return (short) ( System.currentTimeMillis() >>> 32 );
    }
    protected int getLoTime() {
        return (int) System.currentTimeMillis();
    }

	//private String sep = "";
	
	protected String format(int intval) {
		String formatted = Integer.toHexString(intval);
		StringBuffer buf = new StringBuffer("00000000");
		buf.replace( 8-formatted.length(), 8, formatted );
		return buf.toString();
	}
	
	protected String format(short shortval) {
		String formatted = Integer.toHexString(shortval);
		StringBuffer buf = new StringBuffer("0000");
		buf.replace( 4-formatted.length(), 4, formatted );
		return buf.toString();
	}
	
	public String generate() {
		return new StringBuffer(36)
		.append( format( getIP() ) )//.append(sep)
		.append( format( getJVM() ) )//.append(sep)
		.append( format( getHiTime() ) )//.append(sep)
		.append( format( getLoTime() ) )//.append(sep)
		.append( format( getCount() ) )
		.toString();
	}


	public static void main( String[] args ) throws Exception {
		UUIDHexGenerator gen1 = new UUIDHexGenerator();

        long l1 = System.currentTimeMillis();
        for (int i=0;i<10;i++){
            System.out.println(gen1.generate());
        }
        long l2 = System.currentTimeMillis();
        System.out.println(" " + (l2-l1));

	}
}






