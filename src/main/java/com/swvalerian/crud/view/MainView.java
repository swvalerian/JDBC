package com.swvalerian.crud.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainView {
    final private String caseMe = "С какой сущностью будем иметь дело? \n" +
                    "1 - Skill\n" +
                    "2 - Developer\n" +
                    "3 - Team\n" +
                    "exit - выход из программы";

    public MainView() {
        System.out.println(caseMe);

        try (BufferedReader bufRead = new BufferedReader(new InputStreamReader(System.in))) {
            String str;

            while (true) {
                str = bufRead.readLine();

                String[] com = str.split(" ");
                switch (com[0]) {
                    case "1" : System.out.println("\n Работаем со Skills \n");
                        SkillView skillView = new SkillView();
                        skillView.showMenu();
                        break;
                    case "2" : System.out.println("\n Работаем с Developers \n");
                        DevView devView = new DevView();
                        devView.showMenu();
                        break;
                    case "3" : System.out.println("\n Работаем с Team \n");
                        TeamView teamView = new TeamView();
                        teamView.showMenu();
                        break;
                    case "help" :
                        System.out.println("Помощь уже в пути! Ожидайте...."); // пока так, потом допишу хелп для мейнвиев
                        break;
                    case "exit" :
                        System.out.println("Программа корректно завершается");
                        return;

                    default :
                        System.out.println("Введите верную команду! \n" + caseMe);
                }
            }
        } catch (IOException ex) {
            System.err.println("ошибка ввода - вывода. from MainView");
        } catch (IndexOutOfBoundsException ex) {
            System.err.println("Обращение к несуществующему элементу, введите команду верно!");
        }
    }

    public void showAll () {

    }
}
