package org.deman.generator.tools

import java.nio.channels.FileChannel;

/**
 * Created with IntelliJ IDEA.
 * User: deman
 * Date: 05/04/13
 * Time: 21:23
 * To change this template use File | Settings | File Templates.
 */
class FS {

    public static String createFile(String where,String what){
        File outputFile = new File(where)
        File dirFile = outputFile.parentFile.absoluteFile
        if (!dirFile.exists()){
            dirFile.mkdirs()
        }
        outputFile.createNewFile()
        outputFile.write(what)
    }

    public static final void copy( File source, File destination ) {
        if( source.isDirectory() ) {
            copyDirectory( source, destination );
        } else {
            copyFile( source, destination );
        }
    }

    public static final void copy( String source, String destination ) {
        copyDirectory( new File(source),new File(destination) )
    }

    public static final void copyDirectory( File source, File destination ) {
        if( !source.isDirectory() ) {
            throw new IllegalArgumentException( "Source (" + source.getPath() + ") must be a directory." );
        }

        if( !source.exists() ) {
            throw new IllegalArgumentException( "Source directory (" + source.getPath() + ") doesn't exist." );
        }

        if( !destination.exists() ) {
            destination.mkdirs();
        }


        File[] files = source.listFiles();

        for( File file : files ) {
            if( file.isDirectory() ) {
                copyDirectory( file, new File( destination, file.getName() ) );
            } else {
                copyFile( file, new File( destination, file.getName() ) );
            }
        }
    }

    public static final void copyFile( File source, File destination ) throws IOException {
        FileChannel sourceChannel = new FileInputStream( source ).getChannel();
        FileChannel targetChannel = new FileOutputStream( destination ).getChannel();
        sourceChannel.transferTo(0, sourceChannel.size(), targetChannel);
        sourceChannel.close();
        targetChannel.close();
    }

}
