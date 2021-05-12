package com.swvalerian.crud.view;

import com.swvalerian.crud.controller.SkillController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SkillView {

    public void showMenu() { // думаю этот метод надо сделать final
        try (BufferedReader bufRead = new BufferedReader(new InputStreamReader(System.in))) {
            String str;
            ConsoleOut out = new ConsoleOut();
            System.out.println(out.getHello() + out.userComSkill);

            SkillController sc = new SkillController();

            while (true) {
                str = bufRead.readLine();

                // команды: list, get, create, update, delete, exit
                String[] com = str.split(" ");
                switch (com[0]) {
                    case "list" : System.out.println("\nВывод всех записей из файла: \n");
                        sc.getAll();
                        break;
                    case "create" : sc.create(Integer.decode(com[1]), com[2]);
                        System.out.println("\n Успешно создали новую запись в файле!");
                        break;
                    case "get" : System.out.println("Получили следующую запись из файла: \n");
                        sc.read(Integer.decode(com[1]));
                        break;
                    case "update" : sc.update(Integer.decode(com[1]), com[2]);
                        System.out.println("\nЗапись в файле обновлена");
                        break;
                    case "delete" : sc.delete(Integer.decode(com[1]));
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
        } catch (IOException ex) {
            System.err.println("ошибка ввода - вывода");
        } catch (IndexOutOfBoundsException ex) {
            System.err.println("Обращение к несуществующему элементу, введите команду верно!");
        }
    }
}