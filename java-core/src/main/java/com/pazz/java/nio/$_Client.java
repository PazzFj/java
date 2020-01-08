package com.pazz.java.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @author: 彭坚
 * @create: 2020/1/6 21:07
 * @description: 客户端 阻塞 nio
 */
public class $_Client {

    public static void main(String[] args) throws Exception {
        sendBlockNIOClient("F:/images/tx.png");
    }

    public static void sendBlockNIOClient(String filePath) throws Exception {
        // 打开 socket 管道
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("localhost", 8899));

        // 打开 file 管道
        FileChannel fileChannel = FileChannel.open(Paths.get(filePath), StandardOpenOption.READ);

        // 创建字节缓存区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        // 循环读取利用 file 管道写 byte 缓存
        while (fileChannel.read(byteBuffer) != -1) {
            byteBuffer.flip();
            socketChannel.write(byteBuffer);
            byteBuffer.clear();
        }

        // 关闭输出
        socketChannel.shutdownOutput();

        int len;
        while ((len = socketChannel.read(byteBuffer)) != -1) {
            System.out.println(len);
            byteBuffer.flip();
            System.out.println(new String(byteBuffer.array(), 0, len));
            byteBuffer.clear();
        }
        fileChannel.close();
        socketChannel.close();
    }

}