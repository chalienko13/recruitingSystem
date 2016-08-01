package com.netcracker.solutions.kpi.util.scheduling;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by natalya on 14.05.2016.
 */

public class CreatingOfAllSchedules {

    // Необхідні параметри для класу AllActivityAboutFindingTeachers
    private int numbOfStudents; // К-сть зареєстрованих студентів = К-сть необхідних інтерв'ю
    private int durationOfLongIntervInMinutes; // Тривалість довшої співбесіди (Тривалість тех./софт. інтерв'ю);
    private int durationOfShortIntervInMinutes; // Тривалість коротшої співбесіди (Тривалість софт. інтерв'ю)
    private int numbOfAllDaysForInterviewing; // Тривалість прийомної комісії в днях
    private int numbOfHoursForIntervForDay; // К-сть годин інтерв'ю в день
    private int totalNumbOfRegisteredTeachersWithLongerInterv; // Загальна к-сть зареєстрованих викладачів з довшою співбесідою
    private int totalNumbOfRegisteredTeachersWithShorterInterv; // Загальна к-сть зареєстрованих викладачів з коротшою співбесідою
    private List<Integer> numbOfBookedPositionByLongTeacherForEachDay; // К-сть зайнятих позицій викладачами з довшою співбесідою на кожний день
    private List<Integer> numbOfBookedPositionByShortTeacherForEachDay; //К-сть зайнятих позицій викладачами з коротою співбесідою на кожний день

    private List<Timestamp> datesAndTimesList = new ArrayList<>(); // Список дат й годин, в які проводяться співбесіди
    private List<User> undistributedStudents = new ArrayList<>(); // Список нерозподілених студентів
    private List<Integer> realNumberOfAvailableIntervForEachDay = new ArrayList<>(); // Список. Реальна к-сть співбесід в кожний день, що можуть відбутися
    private List<Integer> timesList = new ArrayList<>(); // Список. Перелік годин, з яких студенти вибирали собі підходящі
    private List<StudentsScheduleCell> studentsSchedule = new ArrayList<>(); // розклад для студентів
    private boolean studentsScheduleIsAlreadyCreated = false; // чи вже побудований розклад для студентів
    private int numbOfSatisfiedStudents = 0; // К-сть студентів, кого розклад задовільнив
    private int numbOfStudWithScheduleOnAFewHourLater; // К-сть студентів, кого розклад не задовільнив, бо час співбесіди зсунувся на кілька годин ВПЕРЕД від бажаного
    private int numbOfStudWithScheduleOnAFewHourBefore; // К-сть студентів, кого розклад не задовільнив, бо час співбесіди зсунувся на кілька годин НАЗАд від бажаного
    private int numbOfStudWithScheduleOnAnotherDay; // К-сть студентів, кого розклад не задовільнив, бо день співбесіди - той, на який вони взагалі не вибирали години

    private List<TeachersScheduleCell> teachersSchedule = new ArrayList<>(); // розклад для викладачів
    private boolean teachersScheduleIsAlreadyCreated = false; // чи вже побудований розклад для викладачів
    private List<User> allLongTeachers = new ArrayList<>(); // список всі викладачів з довшою співбесідою
    private List<User> allShortTeachers = new ArrayList<>(); // список всі викладачів з коротшою співбесідою

    public CreatingOfAllSchedules(int durationOfLongIntervInMinutes, int durationOfShortIntervInMinutes,
                                  int totalNumbOfRegisteredTeachersWithLongerInterv,
                                  int totalNumbOfRegisteredTeachersWithShorterInterv,
                                  List<Integer> numbOfBookedPositionByLongTeacherForEachDay,
                                  List<Integer> numbOfBookedPositionByShortTeacherForEachDay,
                                  List<Timestamp> datesAndTimesList, List<User> undistributedStudents,
                                  List<User> allLongTeachers, List<User> allShortTeachers) {
        this.datesAndTimesList = datesAndTimesList;
        Collections.sort(datesAndTimesList); // на всякий випадок - сортую

        // Список. Перелік годин, з яких студенти вибирали собі підходящі
        // створюю список годин (вважаю, що в кожний день доступні однакові годині)
        int firstDay = datesAndTimesList.get(0).getDay();
        for (int i = 0; i < datesAndTimesList.size(); i++) {
            if(datesAndTimesList.get(i).getDay() == firstDay){ // якщо дивлюся дані саме першого дня (головне щоб одного й того ж)
                timesList.add(datesAndTimesList.get(i).getHours()); // витягую годину інтерв'ю
            }
        }
        Collections.sort(timesList);

        // К-сть годин інтерв'ю в день
        ArrayList<Integer> tmp = new ArrayList<>(); // список днів інтерв'ю (без повторень)
        this.numbOfAllDaysForInterviewing = 1;
        for (Timestamp dateAndTime: datesAndTimesList) {
            if(!tmp.contains(dateAndTime.getDay())){
                tmp.add(dateAndTime.getDay());
            }
        }
        this.numbOfAllDaysForInterviewing = tmp.size();

        this.undistributedStudents = undistributedStudents;
        this.numbOfStudents = undistributedStudents.size();
        this.durationOfLongIntervInMinutes = durationOfLongIntervInMinutes;
        this.durationOfShortIntervInMinutes = durationOfShortIntervInMinutes;
        this.numbOfHoursForIntervForDay = timesList.size();
        this.totalNumbOfRegisteredTeachersWithLongerInterv = totalNumbOfRegisteredTeachersWithLongerInterv;
        this.totalNumbOfRegisteredTeachersWithShorterInterv = totalNumbOfRegisteredTeachersWithShorterInterv;
        this.numbOfBookedPositionByLongTeacherForEachDay = numbOfBookedPositionByLongTeacherForEachDay;
        this.numbOfBookedPositionByShortTeacherForEachDay = numbOfBookedPositionByShortTeacherForEachDay;

        // todo Поки просто витягаю з класу AllActivityAboutFindingTeachers, а взагалі треба звіряти з БД (якщо буде доданий складніший функціонал для адміна)
        this.realNumberOfAvailableIntervForEachDay =
                AllActivityAboutFindingTeachers.getNumbOfAvailableIntervForEachDayByRegisteredTeachers(numbOfStudents,
                        durationOfLongIntervInMinutes, durationOfShortIntervInMinutes, numbOfAllDaysForInterviewing,
                        numbOfHoursForIntervForDay, totalNumbOfRegisteredTeachersWithLongerInterv,
                        totalNumbOfRegisteredTeachersWithShorterInterv, numbOfBookedPositionByLongTeacherForEachDay,
                        numbOfBookedPositionByShortTeacherForEachDay);

        this.allLongTeachers = allLongTeachers;
        this.allShortTeachers = allShortTeachers;
    }

    // todo Така перевірка неправильна, якщо адмін вирішив сам дознайти викладачів (якщо цей функціонла буде додано). Тоді треба перевіряти в БД, чи був такий запис.
    // Перевірка достатньості к-сті викладачів
    private boolean checkAdequacyOfNumberOfTeachers(){
        double mainPercent = AllActivityAboutFindingTeachers.getTotalPercentageOfAvailableIntervByRegisteredTeachers(numbOfStudents,
                durationOfLongIntervInMinutes, durationOfShortIntervInMinutes, numbOfAllDaysForInterviewing,
                numbOfHoursForIntervForDay, totalNumbOfRegisteredTeachersWithLongerInterv,
                totalNumbOfRegisteredTeachersWithShorterInterv, numbOfBookedPositionByLongTeacherForEachDay,
                numbOfBookedPositionByShortTeacherForEachDay);
        return mainPercent >= 80.0;
    }

    // Складання розкладу для студентів
    private boolean calcucateStudentsSchedule(){
        if(!checkAdequacyOfNumberOfTeachers()){
            return false;
        }

        // складаємо пустий розклад
        int currentDay = datesAndTimesList.get(0).getDay(); // поточний день
        int iter = 0;
        for (Timestamp datesAndTimes : datesAndTimesList) {

            if(datesAndTimes.getDay() != currentDay){ // якщо перейшли на наступний день
                iter++;
                currentDay = datesAndTimes.getDay();
            }

            StudentsScheduleCell studentsScheduleCell = new StudentsScheduleCell();
            studentsScheduleCell.setDateAndHour(datesAndTimes);

            // Хоча й так має націло завжди ділитися
            studentsScheduleCell.setNumberOfFreePlace((int) Math.ceil(realNumberOfAvailableIntervForEachDay.get(iter) * 1.0 / numbOfHoursForIntervForDay));

            studentsSchedule.add(studentsScheduleCell);
        }

        // Цикл 1:
        for (Timestamp curTimestamp :datesAndTimesList) { // ми проходимося по кожній годині (для кожного дня)

            ArrayList<User> willingStudents = new ArrayList<>(); // студенти, що вибрали цю поточну годину
            for (User curStudent :undistributedStudents) { // шукаємо всіх студентів із «Список нерозподілених студентів», що вказали її як бажану
                if(curStudent.getTimesAndDates().contains(curTimestamp)){
                    willingStudents.add(curStudent); // записую цього студента в список
                }
            }


            // Шукаю цей же час й день в розкладі, щоб витягнути навантеженість
            for (int i = 0; i < studentsSchedule.size(); i++) {
                Timestamp curTimestampFromSchedule = studentsSchedule.get(i).getDateAndHour();
                if(curTimestampFromSchedule.equals(curTimestamp)){ // якщо поточний час в розкладі той самий, з яким ми працюємо в глобальному циклі
                    if(willingStudents.size() <= studentsSchedule.get(i).getNumberOfFreePlace()){ // Якщо на цю, конкретну годину, "К-сть студентів, що згодні на поточну годину" <= Відповідне значення з" Список. Реальна к-сть співбесід в кожний день, що можуть відбутися "/”К-сть вільних місць на цю годину”(для цього дня)
                        if(willingStudents.size() != 0){ // якщо є такі студенти
                            // записуємо цих студентів в список «Розклад для студентів» на поточну дату й час
                            studentsSchedule.get(i).setStudents(willingStudents);
                            // віднімаємо від значення поля «К-сть вільних місць на цю годину» списку «Розклад для студентів» значення «К-сть студентів, що згодні на поточну годину»,
                            studentsSchedule.get(i).setNumberOfFreePlace(studentsSchedule.get(i).getNumberOfFreePlace() - willingStudents.size());
                            // збільшуємо значення поля «К-сть студентів, кого розклад задовільнив» на довжину списку «Студенти, що вибрали цю поточну годину»
                            numbOfSatisfiedStudents += willingStudents.size();
                            // видаляємо цих студентів зі «Список нерозподілених студентів»
                            for (User curStudent: willingStudents){
                                undistributedStudents.remove(undistributedStudents.indexOf(curStudent));
                            }
                        }
                    }
                    break;
                }
            }

        }

        // Створюємо список «Тимчасовий список студентів для 2-го циклу» зі «Список нерозподілених студентів»
        ArrayList<User> tmpStudListForSecondLoop = new ArrayList<>();
        tmpStudListForSecondLoop.addAll(undistributedStudents);

        // Цикл 2:
        while(tmpStudListForSecondLoop.size() > 0){ // поки в тимчасовому списку ще є елементи
            User curStudent = tmpStudListForSecondLoop.get(0); // поточний студент
            int minNumbOfVariants = tmpStudListForSecondLoop.get(0).getTimesAndDates().size(); // к-сть варіантів, що він вибрав

            if(tmpStudListForSecondLoop.size() > 1){ // якщо ще є студенти, то шукаємо того, в кого найменша к-сть варіантів (він в пріоритеті)
                for (int i = 1; i < tmpStudListForSecondLoop.size(); i++) {
                    if(tmpStudListForSecondLoop.get(i).getTimesAndDates().size() < minNumbOfVariants){
                        minNumbOfVariants = tmpStudListForSecondLoop.get(i).getTimesAndDates().size();
                        curStudent = tmpStudListForSecondLoop.get(i);
                    }
                }

            }

            // маємо в змінній curStudent студента з мінімальною к-стю варіантів й, відповідно, з найбільшим пріоритетом

            boolean foundAppropriatePlace = false;
            // Проходимося по списку «Розклад для студентів»
            for (int i = 0; i < studentsSchedule.size(); i++) {
                foundAppropriatePlace = false;
                // Якщо для поточного об’єкту з цього списку значення в полі «К-сть вільних місця на цю годину» більша
                // за нуль і якщо в списку «Список днів й годин, які поточний студент вибрав» є таке ж значення поля
                // «Дата» й «Година», як і значення полів  «Дата» й «Година» в поточного об’єкта списку «Розклад для студентів»
                if(studentsSchedule.get(i).getNumberOfFreePlace() > 0 && curStudent.getTimesAndDates().contains(studentsSchedule.get(i).getDateAndHour())) {
                    foundAppropriatePlace = true;
                    // записуємо цього студента в список «Розклад для студентів» на поточну дату й час
                    studentsSchedule.get(i).getStudents().add(curStudent);
                    // віднімаємо від значення поля «К-сть вільних місць на цю годину» списку «Розклад для студентів» одиницю,
                    studentsSchedule.get(i).setNumberOfFreePlace(studentsSchedule.get(i).getNumberOfFreePlace() - 1);
                    // збільшуємо значення поля «К-сть студентів, кого розклад задовільнив» на одиницю,
                    numbOfSatisfiedStudents++;
                    // видаляємо цього студента зі списку «Список нерозподілених студентів» й зі списку «Тимчасовий список студентів для 2-го циклу
                    tmpStudListForSecondLoop.remove(tmpStudListForSecondLoop.indexOf(curStudent));
                    undistributedStudents.remove(undistributedStudents.indexOf(curStudent));
                    break;
                }

            }
            // якщо такого часу не знайшли, то також видаляємо цього поточного студента з тимчасового списку
            if(!foundAppropriatePlace){
                // видаляємо цього студента зі списку «Тимчасовий список студентів для 2-го циклу
                tmpStudListForSecondLoop.remove(tmpStudListForSecondLoop.indexOf(curStudent));
            }
        }

        // Цикл 3:
        for (int iter1 = 0; iter1 < timesList.size()-1; iter1++) { // проходжуся по всім циклам звдигу на «+Х» годин

            // Створюємо список «Тимчасовий список студентів для 3-го циклу» зі «Список нерозподілених студентів»
            ArrayList<User> tmpStudListForThirdLoop = new ArrayList<>();
            tmpStudListForThirdLoop.addAll(undistributedStudents);

            ArrayList<Integer> inappropriateHours = new ArrayList<>(); // Список. Непідходящі години

            // заповнюю список, які години не підходять для здвигу в поточному циклі
            for (int iter2 = timesList.size(); iter2 > timesList.size() - iter1-1; iter2--) {
                inappropriateHours.add(timesList.get(iter2-1));
            }

            // Проходимося по списку «Список нерозподілених студентів» й витягуємо з поточного об’єкта список «Список днів й годин, які поточний студент вибрав»
            while (tmpStudListForThirdLoop.size() > 0){

                boolean foundAppropriatePlace = false;
                List<Timestamp> curTimestamp = tmpStudListForThirdLoop.get(0).getTimesAndDates();
                // Проходимося по списку «Список днів й годин, які поточний студент вибрав»
                for (int j = 0; j < curTimestamp.size(); j++) {

                    // Якщо в полі «Година» стоїть значення, якого немає в списку «Список. Непідходящі години»
                    if(!inappropriateHours.contains(curTimestamp.get(j).getHours()) && !foundAppropriatePlace){
                        // Проходимося по списку «Розклад для студентів»
                        for (int k = 0; k < studentsSchedule.size(); k++) {
                            // Знаходимо всі об’єкти зі списку «Розклад для студентів», в яких
                            // в полі «Дата» стоїть те ж саме значення, що й в полі «Дата» в поточному об’єкті зі списку «Список днів й годин, які поточний студент вибрав»,
                            // а в полі «Година» - стоїть те значення, що й в списку «Список. Перелік годин, з яких студенти вибирали собі підходящі» на iter1 позицій вище.
                            // І якщо для поточного об’єкту зі списку «Розклад для студентів» значення в полі «К-сть вільних місць на цю годину» більша за нуль
                            int tmp = timesList.indexOf(curTimestamp.get(j).getHours());// поточна позиція вибраного студентом часу в списку всіх можливих годин
                            if(studentsSchedule.get(k).getDateAndHour().getDate() == curTimestamp.get(j).getDate() &&
                                    studentsSchedule.get(k).getDateAndHour().getHours() == timesList.get(tmp + iter1 +1) &&
                                    studentsSchedule.get(k).getNumberOfFreePlace() > 0){

                                // записуємо цього студента (поточного зі списку «Список нерозподілених студентів») в список «Розклад для студентів» на поточну «Дата» й «Час»
                                // (що взяті зі списку «Список днів й годин, які поточний студент вибрав» (ЧАС на iter1 більший))
                                studentsSchedule.get(k).getStudents().add(tmpStudListForThirdLoop.get(0));

                                // віднімаємо від значення поля «К-сть вільних місць на цю годину» поточного об’єкту списку «Розклад для студентів» одиницю,
                                studentsSchedule.get(k).setNumberOfFreePlace(studentsSchedule.get(k).getNumberOfFreePlace() -1);

                                // збільшуємо значення поля «К-сть студентів, кого розклад не задовільнив, бо час співбесіди зсунувся на кілька годин ВПЕРЕД від бажаного» на одиницю,
                                numbOfStudWithScheduleOnAFewHourLater++;

                                // видаляємо цього студента (поточного зі списку «Список нерозподілених студентів») зі списку «Список нерозподілених студентів»
                                undistributedStudents.remove(undistributedStudents.indexOf(tmpStudListForThirdLoop.get(0)));

                                // видаляємо цього студента з тимчасового списку для цього циклу
                                tmpStudListForThirdLoop.remove(0);
                                foundAppropriatePlace = true; // щоб не заходити в зовнішній цикл (перебір інших варіантів годин, що вибрав поточний студент)
                                break; // виходимо з цього циклу (перебір днів в рокладі в пошуках підходящого)
                            }
                        }
                    }
                }
                if(!foundAppropriatePlace){ // якщо перебрали всі варіанти, але так і не знайшли підходящого
                    // видаляємо цього студента з тимчасового списку для цього циклу
                    tmpStudListForThirdLoop.remove(0);
                }
            }
        }

        // Створюємо список «Тимчасовий список студентів для 4-го циклу» зі «Список нерозподілених студентів»
        ArrayList<User> tmpStudListFor4thLoop = new ArrayList<>();
        tmpStudListFor4thLoop.addAll(undistributedStudents);

        // Цикл 4:
        while(tmpStudListFor4thLoop.size() > 1) { // Проходимося по тимчасовому списку студентів
            boolean foundAppropriatePlace = false;

            List<Timestamp> curTimeAndDate = tmpStudListFor4thLoop.get(0).getTimesAndDates(); // й витягуємо з поточного об’єкта список «Список днів й годин, які поточний студент вибрав»
            for (int i = 0; i < curTimeAndDate.size(); i++) { // Проходимося по списку «Список днів й годин, які поточний студент вибрав
                for (int j = 0; j < studentsSchedule.size(); j++) { // Проходимося по списку «Розклад для студентів».

                    // Якщо для поточного об’єкту з цього списку
                    // значення в полі «К-сть вільних місця на цю годину» більша за нуль
                    // і якщо в списку «Список днів й годин, які поточний студент вибрав» є таке ж значення поля «Дата», як і значення поля  «Дата» в поточного об’єкта списку «Розклад для студентів»
                    if (studentsSchedule.get(j).getNumberOfFreePlace() > 0 &&
                            curTimeAndDate.get(i).getDate() == studentsSchedule.get(j).getDateAndHour().getDate()) {
                        // Тобто ми вибираємо ті дні, на які у студентом були вибрані хоч якісь години. По суті, враховуючи, що вже відпрацював 3-й цикл, зараз це те ж саме, що віднімати години.

                        // записуємо цього студента (поточного зі списку «Список нерозподілених студентів») в список «Розклад для студентів» на поточну «Дата» й «Час» (що взяті зі списку «Список днів й годин, які поточний студент вибрав»)
                        studentsSchedule.get(j).getStudents().add(tmpStudListFor4thLoop.get(0));
                        // віднімаємо від значення поля «К-сть вільних місць на цю годину» поточного об’єкту списку «Розклад для студентів» одиницю
                        studentsSchedule.get(j).setNumberOfFreePlace(studentsSchedule.get(j).getNumberOfFreePlace() - 1);
                        // збільшуємо значення поля «К-сть студентів, кого розклад не задовільнив, бо час співбесіди зсунувся на кілька годин НАЗАД від бажаного» на одиницю
                        // видаляємо цього студента (поточного зі списку «Список нерозподілених студентів») зі списку «Список нерозподілених студентів»
                        undistributedStudents.remove(undistributedStudents.indexOf(tmpStudListFor4thLoop.get(0)));
                        tmpStudListFor4thLoop.remove(0);
                        numbOfStudWithScheduleOnAFewHourBefore++;
                        foundAppropriatePlace = true;
                        break;
                    }
                }

                if(foundAppropriatePlace){
                    break;
                } else {
                    tmpStudListFor4thLoop.remove(0); // видаляю студента з тимчасового списку, бо ми його вже обробили, хоча й не знайли місця
                }
            }
        }

        // Після цього залишилися лише ті студенти, бажання яких не задоволено й варіанти залишилися в ті дні, в які вони вказали,
        // що не можуть взагалі. Далі вже дорозкидуємо просто на всі місця, що залишилися.

        // Цикл 5:
        while(undistributedStudents.size()>0){ // Проходимося по списку "Список нерозподілених студентів"
            boolean foundAppropriatePlace = false;

            for (int j = 0; j < studentsSchedule.size(); j++) { // Проходимося по списку «Розклад для студентів»
                // Якщо для поточного об’єкту з цього списку значення в полі «К-сть вільних місця на цю годину» більша за нуль
                if(studentsSchedule.get(j).getNumberOfFreePlace() > 0){
                    foundAppropriatePlace = true;
                    studentsSchedule.get(j).getStudents().add(undistributedStudents.get(0));
                    studentsSchedule.get(j).setNumberOfFreePlace(studentsSchedule.get(j).getNumberOfFreePlace() -1);
                    undistributedStudents.remove(0);
                    numbOfStudWithScheduleOnAnotherDay++;
                    break;
                }
            }
            if(!foundAppropriatePlace){
                return false; // значить в розкладі вже немає вільних місць
            }
        }

        studentsScheduleIsAlreadyCreated = true;
        return true;
    }

    private boolean calculateTeachersSchedule(){
        if(!studentsScheduleIsAlreadyCreated){ // якщо розклад для студентів ще не створений, то створюємо його
            if(!calcucateStudentsSchedule()){ // створюємо його й перевіряємо булівський результат
                return false;
            }
        }

        // складаємо пустий розклад
        int currentDay = datesAndTimesList.get(0).getDay(); // поточний день
        TeachersScheduleCell teachersScheduleCell = new TeachersScheduleCell();
        teachersScheduleCell.setDate(datesAndTimesList.get(0));
        teachersSchedule.add(teachersScheduleCell);
        for (Timestamp datesAndTimes : datesAndTimesList) {
            if(datesAndTimes.getDay() != currentDay){ // якщо перейшли на наступний день
                TeachersScheduleCell teachersScheduleCellNew = new TeachersScheduleCell();
                teachersScheduleCellNew.setDate(datesAndTimes);
                teachersSchedule.add(teachersScheduleCellNew);
                currentDay = datesAndTimes.getDay();
            }
        }

        // Рахую фактичну к-сть співбесід в день
        ArrayList<Integer> realNumbOfIntervFromStudentsScheduleForEachDay = new ArrayList<>();
        int curDay = studentsSchedule.get(0).getDateAndHour().getDay();
        int numbOfInterv = 0;
        for (int i = 1; i < studentsSchedule.size(); i++) {
            if(studentsSchedule.get(i).getDateAndHour().getDay() != curDay){
                realNumbOfIntervFromStudentsScheduleForEachDay.add(numbOfInterv);
                curDay = studentsSchedule.get(i).getDateAndHour().getDay();
                numbOfInterv = 0;
            }
            numbOfInterv += studentsSchedule.get(i).getStudents().size();
        }
        realNumbOfIntervFromStudentsScheduleForEachDay.add(numbOfInterv); // останній в циклі не запишеть, а лише нарахується

        // к-сть інтерв'ю групою викладачів в день
        int numbOfIntervByGroupOfTeacherPerDay = AllActivityAboutFindingTeachers.getNumbOfIntervByGroupOfTeacherPerDay(numbOfStudents,
                durationOfLongIntervInMinutes, durationOfShortIntervInMinutes, numbOfAllDaysForInterviewing,
                numbOfHoursForIntervForDay, totalNumbOfRegisteredTeachersWithLongerInterv,
                totalNumbOfRegisteredTeachersWithShorterInterv, numbOfBookedPositionByLongTeacherForEachDay,
                numbOfBookedPositionByShortTeacherForEachDay);

        // Рахую необхідну к-сть груп викладачів (округлити до більшого) на кожний день:
        ArrayList<Integer> necessaryNumbOfGroupOfTeacherForEachDay = new ArrayList<>();
        for (int i = 0; i < realNumbOfIntervFromStudentsScheduleForEachDay.size(); i++) {
            necessaryNumbOfGroupOfTeacherForEachDay.add((int) Math.ceil(realNumbOfIntervFromStudentsScheduleForEachDay.get(i) * 1.0 /  numbOfIntervByGroupOfTeacherPerDay));
        }

        // відношення викладачів в групі
        int N = AllActivityAboutFindingTeachers.getN(numbOfStudents, durationOfLongIntervInMinutes,
                durationOfShortIntervInMinutes, numbOfAllDaysForInterviewing, numbOfHoursForIntervForDay,
                totalNumbOfRegisteredTeachersWithLongerInterv, totalNumbOfRegisteredTeachersWithShorterInterv,
                numbOfBookedPositionByLongTeacherForEachDay, numbOfBookedPositionByShortTeacherForEachDay);

        // Рахую к-сть викладчів з довшою й коротшою співбесідою на кожний день
        ArrayList<Integer> necessaryNumbOfLongTeacherForEachday = new ArrayList<>();
        ArrayList<Integer> necessaryNumbOfShortTeacherForEachday = new ArrayList<>();
        for (int i = 0; i < necessaryNumbOfGroupOfTeacherForEachDay.size(); i++) {
            necessaryNumbOfLongTeacherForEachday.add(necessaryNumbOfGroupOfTeacherForEachDay.get(i) * N);
            necessaryNumbOfShortTeacherForEachday.add(necessaryNumbOfGroupOfTeacherForEachDay.get(i) * 1);
        }

        // додаю в розклад викладачів з довшою співбесідою
        ArrayList<User> tmpLongTeachersList = new ArrayList<>();
        tmpLongTeachersList.addAll(allLongTeachers);
        for (int k = 0; k < teachersSchedule.size(); k++) { // проходжуся по розкладу
            for (int i = 0; i < tmpLongTeachersList.size(); i++) { // по викладачам

                // якщо поточний викладач вибрав собі цей день (перевірка року, місяя й дня)
                List<Timestamp> timestampsCurTeacher = tmpLongTeachersList.get(i).getTimesAndDates();
                for (int j = 0; j < timestampsCurTeacher.size(); j++) {

                    if(timestampsCurTeacher.get(j).getYear() == teachersSchedule.get(k).getDate().getYear() &&
                            timestampsCurTeacher.get(j).getMonth() == teachersSchedule.get(k).getDate().getMonth() &&
                            timestampsCurTeacher.get(j).getDay() == teachersSchedule.get(k).getDate().getDay()){

                        // і якщо в цей день ще потрібні викладачі
                        if(necessaryNumbOfLongTeacherForEachday.get(k) > 0){
                            // записую викладача в розклад
                            teachersSchedule.get(k).getTeachers().add(tmpLongTeachersList.get(i));
                            // зменшую к-сть необхідних викладачів в день
                            necessaryNumbOfLongTeacherForEachday.set(k, necessaryNumbOfLongTeacherForEachday.get(k) -1);
                        }

                        // видаляю з тимчасового списку цей вибір викладача, бо якщо ми сюди дійшли, то вже перевірили його
                        tmpLongTeachersList.get(i).getTimesAndDates().remove(j);
                        j--;
                        break;
                    }

                }
            }
        }

        // додаю в розклад викладачів з коротшою співбесідою
        ArrayList<User> tmpShortTeachersList = new ArrayList<>();
        tmpShortTeachersList.addAll(allShortTeachers);

        for (int k = 0; k < teachersSchedule.size(); k++) { // проходжуся по розкладу
            for (int i = 0; i < tmpShortTeachersList.size(); i++) { // по викладачам
                // якщо поточний викладач вибрав собі цей день (перевірка року, місяя й дня)
                List<Timestamp> timestampsCurTeacher = tmpShortTeachersList.get(i).getTimesAndDates();

                for (int j = 0; j < timestampsCurTeacher.size(); j++) {

                    if(timestampsCurTeacher.get(j).getYear() == teachersSchedule.get(k).getDate().getYear() &&
                            timestampsCurTeacher.get(j).getMonth() == teachersSchedule.get(k).getDate().getMonth() &&
                            timestampsCurTeacher.get(j).getDay() == teachersSchedule.get(k).getDate().getDay()){

                        // і якщо в цей день ще потрібні викладачі
                        if(necessaryNumbOfShortTeacherForEachday.get(k) > 0){
                            // записую викладача в розклад
                            teachersSchedule.get(k).getTeachers().add(tmpShortTeachersList.get(i));
                            // зменшую к-сть необхідних викладачів в день
                            necessaryNumbOfShortTeacherForEachday.set(k, necessaryNumbOfShortTeacherForEachday.get(k) -1);
                        }

                        // видаляю з тимчасового списку цей вибір викладача, бо якщо ми сюди дійшли, то вже перевірили його
                        tmpShortTeachersList.get(i).getTimesAndDates().remove(j);
                        j--;
                        break;
                    }

                }
            }
        }

        teachersScheduleIsAlreadyCreated = true;
        return true;
    }

    public List<StudentsScheduleCell> getStudentsSchedule() {
        if(!studentsScheduleIsAlreadyCreated){ // якщо розклад для студентів ще не створений
            if(!calcucateStudentsSchedule()){ // створюємо його й перевіряємо булівський результат
                return null;
            }
        }
        return studentsSchedule; // якщо сюди дійшли, значить все норм
    }

    public int getNumbOfSatisfiedStudents() {
        return numbOfSatisfiedStudents;
    }

    public int getNumbOfStudWithScheduleOnAFewHourLater() {
        return numbOfStudWithScheduleOnAFewHourLater;
    }

    public int getNumbOfStudWithScheduleOnAFewHourBefore() {
        return numbOfStudWithScheduleOnAFewHourBefore;
    }

    public int getNumbOfStudWithScheduleOnAnotherDay() {
        return numbOfStudWithScheduleOnAnotherDay;
    }

    public List<TeachersScheduleCell> getTeachersSchedule() {
        if(!teachersScheduleIsAlreadyCreated){ // якщо розклад для викладачів ще не створений
            if(!calculateTeachersSchedule()){ // створюємо його й перевіряємо булівський результат
                return null;
            }
        }
        return teachersSchedule; // якщо сюди дійшли, значить все норм
    }
}
