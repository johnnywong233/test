package io.nio;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Author: Johnny
 * Date: 2017/10/23
 * Time: 20:27
 * NIO里的内存映射
 */
public class MemoryMappingExample {

    public static void main(String[] args) throws Exception {
        String fn = "example_memory_mapped_file.txt";
        RandomAccessFile memoryMappedFile = new RandomAccessFile(fn, "rw");

        //Mapping a file into memory
        int mem_map_size = 20 * 1024 * 1024;
        MappedByteBuffer out = memoryMappedFile.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, mem_map_size);

        //Writing into Memory Mapped File
        for (int i = 0; i < mem_map_size; i++) {
            out.put((byte) 'A');
        }
        System.out.println("File '" + fn + "' is now " + Integer.toString(mem_map_size) + " bytes full.");

        // Read from memory-mapped file.
        for (int i = 0; i < 30; i++) {
            System.out.print((char) out.get(i));
        }
        System.out.println("\nReading from memory-mapped file '" + fn + "' is complete.");
        memoryMappedFile.close();
    }
}
