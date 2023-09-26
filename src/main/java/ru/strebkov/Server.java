package ru.strebkov;

import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

public class Server {
    public static final Integer PORT = 7879;
    public static Queue<String> question;

    public static void main(String[] args) {
        question = new LinkedList<>();
        question.add("Напишите свое имя русскими буквами: ");
        question.add("Проверим не робот ли ты? Напиши 777 ");
        question.add(" напиши ты мужчина (мальчик) - М или женщина (девочка) - Ж ?");
        question.add(" тебе больше 18 лет?");

        System.out.println("Server начал работу");

        try (ServerSocket server = new ServerSocket(PORT);
             Socket client = server.accept();
             // каналы записи и чтения в сокет
             DataOutputStream out = new DataOutputStream(client.getOutputStream());
             DataInputStream in = new DataInputStream(client.getInputStream())
        ) {

            out.writeUTF(question.poll());
            out.flush();

            List ansver = new List();

            while (!client.isClosed()) {
                System.out.println("Server написал клиенту");
                // сервер ждёт  получения данных клиента
                String entry = in.readUTF();
                ansver.add(entry);

                if (entry.equalsIgnoreCase("Нет") || entry.equalsIgnoreCase("нет")) {
                    out.writeUTF("ок " + ansver.getItem(0) + ", добро пожаловать на Детский сайт\n приятного просмотра");
                    out.flush();
                } else if (entry.equalsIgnoreCase("Да") || entry.equalsIgnoreCase("да")) {
                    out.writeUTF("ок " + ansver.getItem(0) + ", добро пожаловать на Взрослый сайт\n хорошего дня!");
                    out.flush();
                } else if (entry.equalsIgnoreCase("М") || entry.equalsIgnoreCase("м")) {
                    out.writeUTF("Отлично " + ansver.getItem(0) + ", просмотрите эту ссылу '1111111'\n "
                            + ansver.getItem(0) + " " + question.poll());
                    out.flush();
                } else if (entry.equalsIgnoreCase("Ж") || entry.equalsIgnoreCase("ж")) {
                    out.writeUTF("Отлично  " + ansver.getItem(0) + ", просмотрите эту ссылу '8888888'\n"
                            + ansver.getItem(0) + " " + question.poll());
                    out.flush();
                } else if (entry.equalsIgnoreCase("777")) {
                    out.writeUTF("Отлично  " + ansver.getItem(0) + ", вы человек\n"
                            + ansver.getItem(0) + " " + question.poll());
                    out.flush();
                } else {
                    out.writeUTF("Очень приятно " + ansver.getItem(0) + ", " + question.poll());
                    out.flush();

//                } else {
//                    out.writeUTF(ansver.getItem(0) + ", ты не ответил на вопрос правильно ");
//                    out.flush();
                }

            }
            System.out.println("Closing connections & channels - DONE.");

        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }
}
