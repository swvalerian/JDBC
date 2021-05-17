package com.swvalerian.crud.view;

import com.swvalerian.crud.controller.DevController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

public class DevView {
    public void showMenu() { // думаю этот метод надо сделать final
        try (BufferedReader bufRead = new BufferedReader(new InputStreamReader(System.in))) {
            String str;
            ConsoleOut out = new ConsoleOut();
            System.out.println(out.getHello() + out.userComDev);

            DevController dc = new DevController();

            while (true) {
                str = bufRead.readLine();

                // команды: list, get, create, update, delete, exit
                String[] com = str.split(" ");
                switch (com[0]) {
                    case "listDev":
                        System.out.println("\nВывод всех записей из файла: \n");
                        dc.getAll();
                        break;
                    case "createDev":
                        dc.create(Integer.decode(com[1]), com[2], com[3]);
                        System.out.println("\n Успешно создали новую запись в файле!");
                        break;
                    case "getDev":
                        System.out.println("Получили следующую запись из файла: \n");
                        dc.read(Long.decode(com[1]));
                        break;
                    case "updateDev":
                        dc.update(Integer.decode(com[1]), com[2], com[3]);
                        System.out.println("\nЗапись в файле обновлена");
                        break;
                    case "deleteDev":
                        dc.delete(Long.decode(com[1]));
                        System.out.println("\nЗапись номер " + com[1] + " удалена");
                        break;
                    case "help":
                        System.out.println(out.getExampleCom());
                        break;
                    case "exit":
                        System.out.println(ConsoleOut.goodBy);
                        bufRead.close();
                        return;

                    default:
                        System.out.println("Введите верную команду!");
                }
            }
        } catch (IOException ex) {
            System.err.println("ошибка ввода - вывода. form DevView");
        } catch (IndexOutOfBoundsException ex) {
            System.err.println("Обращение к несуществующему элементу, введите команду верно!");
        }
    }
}
