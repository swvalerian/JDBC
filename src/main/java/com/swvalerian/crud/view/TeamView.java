package com.swvalerian.crud.view;

import com.swvalerian.crud.controller.TeamController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TeamView {
    public void showMenu() { // думаю этот метод надо сделать final
        try (BufferedReader bufRead = new BufferedReader(new InputStreamReader(System.in))) {
            String str;
            ConsoleOut out = new ConsoleOut();
            System.out.println(out.getHello() + out.userComTeam);

            TeamController tc = new TeamController();

            while (true) {

                // ошибка тут! строка ниже вызовет IOException
                str = bufRead.readLine();

                // команды: list, get, create, update, delete, exit
                String[] com = str.split(" ");
                switch (com[0]) {
                    case "listT" : System.out.println("\nВывод всех записей из файла: \n");
                        tc.getAll();
                        break;
                    case "createT" : tc.create(Integer.decode(com[1]), com[2]);
                        System.out.println("\n Успешно создали новую запись в файле!");
                        break;
                    case "getT" : System.out.println("Получили следующую запись из файла: \n");
                        tc.read(Integer.decode(com[1]));
                        break;
                    case "updateT" : tc.update(Integer.decode(com[1]), com[2]);
                        System.out.println("\nЗапись в файле обновлена");
                        break;
                    case "deleteT" : tc.delete(Integer.decode(com[1]));
                        System.out.println("\nЗапись номер " + com[1] + " удалена");
                        break;
                    case "help" :
                        System.out.println(out.getExampleCom());
                        break;
                    case "exit" :
                        System.out.println(ConsoleOut.goodBy);
                        return;

                    default :
                        System.out.println("Введите верную команду!");
                }
            }
            // может и тут что-то ?
        } catch (IOException ex) {
            System.err.println("ошибка ввода - вывода. from TeamView");
        } catch (IndexOutOfBoundsException ex) {
            System.err.println("Обращение к несуществующему элементу, введите команду верно!");
        }
     }
}
