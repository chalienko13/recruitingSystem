package com.netcracker.solutions.kpi.util.scheduling;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by natalya on 14.05.2016.
 */

public class AllActivityAboutFindingTeachers {
    // Пояснення параметрів, що передаємо в кожний метод:
    /*
    private static int numbOfStudents; // К-сть зареєстрованих студентів = К-сть необхідних інтерв'ю
    private static int durationOfLongIntervInMinutes; // Тривалість довшої співбесіди (Тривалість тех./софт. інтерв'ю);
    private static int durationOfShortIntervInMinutes; // Тривалість коротшої співбесіди (Тривалість софт. інтерв'ю)
    private static int numbOfAllDaysForInterviewing; // Тривалість прийомної комісії в днях
    private static int numbOfHoursForIntervForDay; // К-сть годин інтерв'ю в день
    private static int totalNumbOfRegistersdTeachersWithLongerInterv; // Загальна к-сть зареєстрованих викладачів з довшою співбесідою
    private static int totalNumbOfRegistersdTeachersWithShorterInterv; // Загальна к-сть зареєстрованих викладачів з коротшою співбесідою
    private static ArrayList<Integer> numbOfBookedPositionByLongTeacherForEachDay; // К-сть зайнятих позицій викладачами з довшою співбесідою на кожний день
    private static ArrayList<Integer> numbOfBookedPositionByShortTeacherForEachDay; //К-сть зайнятих позицій викладачами з коротою співбесідою на кожний день
    */

    private static int numbOfIntervPerDay; // К-сть необхідних співбесід в день
    //private static ArrayList<Integer> realNumOfIntervForEachDay; // Список. Реальна к-сть співбесід в кожний день, що можуть відбутися

    // Не для ініціалізації, а для розрахунків:
    private static int totalNumberOfNecessaryTeacherWithLongerInterview; // загальна необхідна к-сть викладачів з довшою співбесідою (не перераховується, а рахується лише на початку; 120%)
    private static int totalNumberOfNecessaryTeacherWithShorterInterview; // загальна необхідна к-сть викладачів з коротшою співбесідою (не перераховується, а рахується лише на початку; 120%)
    private static int N;
    private static int numOfIntervFor2MultLongIntervTimeByGroupOfTeach; // К-сть співбесід за (2*"довший час співбес.") час, що забезпечує група викладачів
    private static int numbOfIntervByGroupOfTeacherPerHour; // К-сть студентів, що група викладачів пропускає за 1 годину
    private static int numbOfIntervByNOTFullGroupOfTeacherPerHour; // К-сть студентів, що НЕПОВНА група викладачів пропускає за 1 годину
    private static int necessaryNumbOfGroupOfTeachPerDay; // Необхідна к-сть груп викладачів на день
    private static int necessaryNumbOfLongTeachPerDay; // К-сть необхідних викладачів в день, тривалість співбесід яких - довша
    private static int necessaryNumbOfShortTeachPerDay; // К-сть необхідних викладачів в день, тривалість співбесід яких - коротша
    private static List<Double> percentageOfRegisteredTeacherWithLongIntervForEachDay; // Відсоток набраних викладачів з довшою співбесідою в кожний день
    private static List<Double> percentageOfRegisteredTeacherWithShortIntervForEachDay; // Відсоток набраних викладачів з коротшою співбесідою в кожний день
    private static int necessaryNumberOfInterv; // Невистачаюча к-сть співбесід
    private static int numbOfIntervByGroupOfTeacherPerDay; // К-сть студентів, що група викладачів пропускає за день
    private static int necessaryNumbOfGroupOfTeach; // Невистачаюча к-сть груп викладачів
    private static List<Integer> necessaryNumbOfLongTeacherForEachDay; // К-сть довших викладачів, яких ще треба запросити на і-й день
    private static List<Integer> necessaryNumbOfShortTeacherForEachDay; // К-сть довших викладачів, яких ще треба запросити на і-й день
    /*private static double totalPercentageOfRegisteredTeacherWithLongInterv; // Загальний відсоток набраних викладачів з довшою співбесідою
    private static double totalPercentageOfRegisteredTeacherWithShortInterv; // Загальний відсоток набраних викладачів з коротшою співбесідою
    private static int totalNecessaryNumbOfLongTeacher; // К-сть викладачів з довшою тривалістю співбесіди, яку ще необхідну добрати, щоб к-сть набраних викладачів цього типу задовольнила необхідну к-сть на 100%
    private static int totalNecessaryNumbOfShortTeacher; // К-сть викладачів з коротшою тривалістю співбесіди, яку ще необхідну добрати, щоб к-сть набраних викладачів цього типу задовольнила необхідну к-сть на 100%*/
    private static List<Integer> numbOfAvailableTeachGroupForEachDayByRegisteredTeachers; // К-сть груп викладачів в і-й день, що можемо скласти з наявної к-сті викладачів в цей день
    private static List<Double> percentageOfAvailableIntervForEachDayByRegisteredTeachers; // Відсоток співбесід в день в і-й день, від запланованої к-сті, що можна провести за наявної к-сті викладачів
    private static List<Integer> numbOfAvailableIntervForEachDayByRegisteredTeachers; // К-сть можливих співбесід в і-й день з поточною к-стю викладачів
    private static int totalNumbOfAvailableIntervByRegisteredTeachers; // К-сть можливих співбесід, за всі дні, за поточної к-сті викладачів
    private static List<Integer> numbOfAdditionIntervForEachday; // К-сть додаткових співбесід для кожного дня, якщо адмін активує ф-цію "Самостійно дознайти викладачів"

    private static double totalPercentageOfAvailableIntervByRegisteredTeachers; // ! Основний критерій для адміна: відсоток співбесід, що можна провести за наявної к-сті викладачів

    // 1.1
    private static void calculationOfNecessaryNumbOfTeacher(int numbOfStudents, int durationOfLongIntervInMinutes,
                                                            int durationOfShortIntervInMinutes,
                                                            int numbOfAllDaysForInterviewing,
                                                            int numbOfHoursForIntervForDay,
                                                            int totalNumbOfRegistersdTeachersWithLongerInterv,
                                                            int totalNumbOfRegistersdTeachersWithShorterInterv,
                                                            List<Integer> numbOfBookedPositionByLongTeacherForEachDay,
                                                            List<Integer> numbOfBookedPositionByShortTeacherForEachDay) {
        // К-сть необхідних співбесід в день
        numbOfIntervPerDay = (int) Math.ceil(numbOfStudents * 1.0 / numbOfAllDaysForInterviewing); // округлення до більшого, якщо треба

        // Список. Реальна к-сть співбесід в кожний день, що можуть відбутися (поки заповнюю одним і тим же числом)
        /*realNumOfIntervForEachDay = new ArrayList<>();
        for (int i = 0; i < numbOfAllDaysForInterviewing; i++) {
            realNumOfIntervForEachDay.add(numbOfIntervPerDay);
        }*/

        // Візьмемо за "N" відношення: Округлити до цілого по мат. правилам ("Тривалість довшої співбесіди" / "Тривалість коротшої співбесіди");
        N = (int) Math.round(durationOfLongIntervInMinutes * 1.0 / durationOfShortIntervInMinutes);

        // Назвемо терміном «Група викладачів»: "N" викладачів, тривалість інтерв'ю яких триває довше та 1-го викладача, тривалість інтерв'ю якого триває менше.

        /*
            Змоделювавши безліч ситуацій, була виведена наступна формула - "К-сть співбесід за (2*"довший час співбес.") час, що
            забезпечує група викладачів": Якщо в попередній формулі (рахуючи «N») дріб не має остачі (час ділиться націло),
            то за ("Тривалість довшої співбесіди" * 2) часу, група викладачів пропускає абсолютно точно ("N" * 2) студентів
            через співбесіди (по 2-м предметам). А якщо ж в тій формулі дріб має остачу, то пропустять не ("N" * 2) студентів, а просто "N".
         */
        // К-сть співбесід за (2*"довший час співбес.") час, що забезпечує група викладачів
        numOfIntervFor2MultLongIntervTimeByGroupOfTeach = N;
        if (durationOfLongIntervInMinutes * 1.0 % durationOfShortIntervInMinutes == 0) { // Якщо в формулі «N», дріб не має остачі
            numOfIntervFor2MultLongIntervTimeByGroupOfTeach *= 2;
        }


        // Також, враховуючи попередню формулу, було виведено, що
        // К-сть студентів, що група викладачів пропускає за 1 годину
        numbOfIntervByGroupOfTeacherPerHour = (int) 60.0 * numOfIntervFor2MultLongIntervTimeByGroupOfTeach / (durationOfLongIntervInMinutes * 2);

        // К-сть студентів, що НЕПОВНА група викладачів пропускає за 1 годину
        numbOfIntervByNOTFullGroupOfTeacherPerHour = (int) (60.0 * 1 / (durationOfLongIntervInMinutes + durationOfShortIntervInMinutes));

        // Розрахунок приблизної к-сті необхідних викладачів на день:

        // Необхідна к-сть груп викладачів на день
        necessaryNumbOfGroupOfTeachPerDay = (int) Math.ceil(numbOfIntervPerDay * 1.0 / (numbOfIntervByGroupOfTeacherPerHour * numbOfHoursForIntervForDay)); // округлення до більшого

        // К-сть необхідних викладачів в день, тривалість співбесід яких - довша
        necessaryNumbOfLongTeachPerDay = necessaryNumbOfGroupOfTeachPerDay * N;

        // К-сть необхідних викладачів в день, тривалість співбесід яких - коротша
        necessaryNumbOfShortTeachPerDay = necessaryNumbOfGroupOfTeachPerDay;

        // Адмін запрошує таку к-сть викладаіч (120% від обрахованої):
        totalNumberOfNecessaryTeacherWithLongerInterview = (int) Math.ceil(1.2 * necessaryNumbOfLongTeachPerDay *  numbOfAllDaysForInterviewing);
        totalNumberOfNecessaryTeacherWithShorterInterview = (int) Math.ceil(1.2 * necessaryNumbOfShortTeachPerDay *  numbOfAllDaysForInterviewing);
    }

    // 1.2
    private static void calculationForStatisticData(int numbOfStudents, int durationOfLongIntervInMinutes,
                                                    int durationOfShortIntervInMinutes, int numbOfAllDaysForInterviewing,
                                                    int numbOfHoursForIntervForDay,
                                                    int totalNumbOfRegistersdTeachersWithLongerInterv,
                                                    int totalNumbOfRegistersdTeachersWithShorterInterv,
                                                    List<Integer> numbOfBookedPositionByLongTeacherForEachDay,
                                                    List<Integer> numbOfBookedPositionByShortTeacherForEachDay){
        // всі попередні розрахунки (там обраховується частина необхідних змінних)
        calculationOfNecessaryNumbOfTeacher(numbOfStudents, durationOfLongIntervInMinutes, durationOfShortIntervInMinutes,
                numbOfAllDaysForInterviewing, numbOfHoursForIntervForDay, totalNumbOfRegistersdTeachersWithLongerInterv,
                totalNumbOfRegistersdTeachersWithShorterInterv, numbOfBookedPositionByLongTeacherForEachDay,
                numbOfBookedPositionByShortTeacherForEachDay);

        //  Відсоток набраних викладачів з довшою співбесідою в кожний день:
        percentageOfRegisteredTeacherWithLongIntervForEachDay = new ArrayList<>();
        double tmp1;
        for (int i = 0; i < numbOfAllDaysForInterviewing; i++) {
            tmp1 = numbOfBookedPositionByLongTeacherForEachDay.get(i) * 100.0 / necessaryNumbOfLongTeachPerDay;
            tmp1 = roundTo2(tmp1); // округлення до 2-х знаків після коми
            percentageOfRegisteredTeacherWithLongIntervForEachDay.add(tmp1);
        }

        //  Відсоток набраних викладачів з коротшою співбесідою в кожний день:
        percentageOfRegisteredTeacherWithShortIntervForEachDay = new ArrayList<>();
        for (int i = 0; i < numbOfAllDaysForInterviewing; i++) {
            tmp1 = numbOfBookedPositionByShortTeacherForEachDay.get(i) * 100.0 / necessaryNumbOfShortTeachPerDay;
            tmp1 = roundTo2(tmp1);
            percentageOfRegisteredTeacherWithShortIntervForEachDay.add(tmp1);
        }
        /*
        // Загальний відсоток набраних викладачів з довшою співбесідою
        int sumOfAllBookedPositionByAllLongTeachers = 0;
        for (int i = 0; i < numbOfAllDaysForInterviewing; i++) {
            sumOfAllBookedPositionByAllLongTeachers += numbOfBookedPositionByLongTeacherForEachDay.get(i);
        }
        totalPercentageOfRegisteredTeacherWithLongInterv = 100.0 * sumOfAllBookedPositionByAllLongTeachers / (necessaryNumbOfLongTeachPerDay * numbOfAllDaysForInterviewing);
        totalPercentageOfRegisteredTeacherWithLongInterv = roundTo2(totalPercentageOfRegisteredTeacherWithLongInterv);

        // Загальний відсоток набраних викладачів з коротшою співбесідою
        int sumOfAllBookedPositionByAllShortTeachers = 0;
        for (int i = 0; i < numbOfAllDaysForInterviewing; i++) {
            sumOfAllBookedPositionByAllShortTeachers += numbOfBookedPositionByShortTeacherForEachDay.get(i);
        }
        totalPercentageOfRegisteredTeacherWithShortInterv = 100.0 * sumOfAllBookedPositionByAllShortTeachers / (necessaryNumbOfShortTeachPerDay * numbOfAllDaysForInterviewing);
        totalPercentageOfRegisteredTeacherWithShortInterv = roundTo2(totalPercentageOfRegisteredTeacherWithShortInterv);
        */

        /*
        // К-сть викладачів з довшою тривалістю співбесіди, яку ще необхідну добрати, щоб к-сть набраних викладачів цього типу задовольнила необхідну к-сть на 100%
        totalNecessaryNumbOfLongTeacher = (int) Math.ceil(necessaryNumbOfLongTeachPerDay * 1.0 * numbOfAllDaysForInterviewing - sumOfAllBookedPositionByAllLongTeachers);
        if(totalNecessaryNumbOfLongTeacher < 0){
            totalNecessaryNumbOfLongTeacher = 0;
        }

        // К-сть викладачів з коротшою тривалістю співбесіди, яку ще необхідну добрати, щоб к-сть набраних викладачів цього типу задовольнила необхідну к-сть на 100%
        totalNecessaryNumbOfShortTeacher = (int) Math.ceil(necessaryNumbOfShortTeachPerDay * 1.0 * numbOfAllDaysForInterviewing - sumOfAllBookedPositionByAllShortTeachers);
        if(totalNecessaryNumbOfShortTeacher < 0){
            totalNecessaryNumbOfShortTeacher = 0;
        }*/

        // К-сть груп викладачів в і-й день, що можемо скласти з наявної к-сті викладачів в цей день
        numbOfAvailableTeachGroupForEachDayByRegisteredTeachers = new ArrayList<>();
        for (int i = 0; i < numbOfAllDaysForInterviewing; i++) {
            // менше з двух
            numbOfAvailableTeachGroupForEachDayByRegisteredTeachers.add(numbOfBookedPositionByShortTeacherForEachDay.get(i) < numbOfBookedPositionByLongTeacherForEachDay.get(i) * 1.0 / N ? numbOfBookedPositionByShortTeacherForEachDay.get(i) : (int) (numbOfBookedPositionByLongTeacherForEachDay.get(i) * 1.0 / N));
        }

        // К-сть можливих співбесід в і-й день з поточною к-стю викладачів
        numbOfAvailableIntervForEachDayByRegisteredTeachers = new ArrayList<>();
        for (int i = 0; i < numbOfAllDaysForInterviewing; i++) {
            if(numbOfBookedPositionByLongTeacherForEachDay.get(i) * 1.0 % N != 0){ // якщо є неповна група

                // якщо в неповній групі є, як мінімум, по одному викладачу кожного типу (тобто залишилося перевірити, чи є лишній короткий, бо довгий лишній вже точно є)
                if(numbOfBookedPositionByShortTeacherForEachDay.get(i) > numbOfAvailableTeachGroupForEachDayByRegisteredTeachers.get(i)){
                    numbOfAvailableIntervForEachDayByRegisteredTeachers.add(numbOfAvailableTeachGroupForEachDayByRegisteredTeachers.get(i) * numbOfIntervByGroupOfTeacherPerHour * numbOfHoursForIntervForDay + numbOfIntervByNOTFullGroupOfTeacherPerHour * numbOfHoursForIntervForDay);
                } else {
                    numbOfAvailableIntervForEachDayByRegisteredTeachers.add(0);
                }
            }
            else { // якщо к-сть груп - ціла
                numbOfAvailableIntervForEachDayByRegisteredTeachers.add(numbOfAvailableTeachGroupForEachDayByRegisteredTeachers.get(i) * numbOfIntervByGroupOfTeacherPerHour * numbOfHoursForIntervForDay);
            }
        }

        // Відсоток співбесід в день в і-й день, від запланованої к-сті, що можна провести за наявної к-сті викладачів
        percentageOfAvailableIntervForEachDayByRegisteredTeachers = new ArrayList<>();
        for (int i = 0; i < numbOfAllDaysForInterviewing; i++) {

            percentageOfAvailableIntervForEachDayByRegisteredTeachers.add(roundTo2(numbOfAvailableIntervForEachDayByRegisteredTeachers.get(i) * 100.0 / numbOfIntervPerDay));
        }

        // К-сть можливих співбесід, за всі дні, за поточної к-сті викладачів
        totalNumbOfAvailableIntervByRegisteredTeachers = 0;
        for (int i = 0; i < numbOfAllDaysForInterviewing; i++) {
            totalNumbOfAvailableIntervByRegisteredTeachers += numbOfAvailableIntervForEachDayByRegisteredTeachers.get(i);
        }

        // Основний критерій для адміна: відсоток співбесід, що можна провести за наявної к-сті викладачів
        totalPercentageOfAvailableIntervByRegisteredTeachers = 100.0 * totalNumbOfAvailableIntervByRegisteredTeachers / numbOfStudents;
        totalPercentageOfAvailableIntervByRegisteredTeachers = roundTo2(totalPercentageOfAvailableIntervByRegisteredTeachers);

        // Невистачаюча к-сть співбесід
        necessaryNumberOfInterv = numbOfStudents - totalNumbOfAvailableIntervByRegisteredTeachers;

        // К-сть студентів, що група викладачів пропускає за день
        numbOfIntervByGroupOfTeacherPerDay = numbOfIntervByGroupOfTeacherPerHour * numbOfHoursForIntervForDay;

        // Невистачаюча к-сть груп викладачів
        necessaryNumbOfGroupOfTeach = (int) Math.ceil(necessaryNumberOfInterv * 1.0 / numbOfIntervByGroupOfTeacherPerDay);

        // скільки груп викладачів невистачає в і-й день (numberOfNecessaryGroupOfTeacherForEachDay)
        ArrayList<Integer> tmpCopyOfNumbOfAvailableIntervForEachDayByRegisteredTeachers = new ArrayList<>(numbOfAvailableIntervForEachDayByRegisteredTeachers); // копія списку (щоб змінювати)
        ArrayList<Integer> numberOfNecessaryGroupOfTeacherForEachDay = new ArrayList<>();
        for (int i = 0; i < numbOfAllDaysForInterviewing; i++) { // початкова ініціалізація нулями
            numberOfNecessaryGroupOfTeacherForEachDay.add(0);
        }

        for (int i = 0; i < necessaryNumbOfGroupOfTeach; i++) {

            // знаходжу день, в який найменша к-сть співбесід
            int dayWithMinNumber = 0;
            int min = tmpCopyOfNumbOfAvailableIntervForEachDayByRegisteredTeachers.get(0);
            for (int j = 1; j < numbOfAllDaysForInterviewing; j++) {
                if(tmpCopyOfNumbOfAvailableIntervForEachDayByRegisteredTeachers.get(j) < min){
                    min = tmpCopyOfNumbOfAvailableIntervForEachDayByRegisteredTeachers.get(j);
                    dayWithMinNumber = j;
                }
            }

            // збільшую в цей день к-сть невистачаючих груп викладачів на одну
            numberOfNecessaryGroupOfTeacherForEachDay.set(dayWithMinNumber,
                    numberOfNecessaryGroupOfTeacherForEachDay.get(dayWithMinNumber) + 1);
            // записую на цей день додаткову співбесіду
            tmpCopyOfNumbOfAvailableIntervForEachDayByRegisteredTeachers.set(dayWithMinNumber,
                    tmpCopyOfNumbOfAvailableIntervForEachDayByRegisteredTeachers.get(dayWithMinNumber) + 1);
        }

        // К-сть довших викладачів, яких ще треба запросити на і-й день
        necessaryNumbOfLongTeacherForEachDay = new ArrayList<>();
        // К-сть коротших викладачів, яких ще треба запросити на і-й день
        necessaryNumbOfShortTeacherForEachDay = new ArrayList<>();
        for (int i = 0; i < numbOfAllDaysForInterviewing; i++) {
            necessaryNumbOfLongTeacherForEachDay.add(numberOfNecessaryGroupOfTeacherForEachDay.get(i) * N);
            necessaryNumbOfShortTeacherForEachDay.add(numberOfNecessaryGroupOfTeacherForEachDay.get(i) * 1);
        }

        // К-сть додаткових співбесід для кожного дня, якщо адмін активує ф-цію "Самостійно дознайти викладачів"
        numbOfAdditionIntervForEachday = new ArrayList<>();
        for (int i = 0; i < numbOfAllDaysForInterviewing; i++) {
            numbOfAdditionIntervForEachday.add(numberOfNecessaryGroupOfTeacherForEachDay.get(i) * numbOfIntervByGroupOfTeacherPerDay);
        }

    }

    // 1.3
    public static int getNumberOfRedundantStudents(int numbOfStudents, int durationOfLongIntervInMinutes,
                                                   int durationOfShortIntervInMinutes, int numbOfAllDaysForInterviewing,
                                                   int numbOfHoursForIntervForDay,
                                                   int totalNumbOfRegistersdTeachersWithLongerInterv,
                                                   int totalNumbOfRegistersdTeachersWithShorterInterv,
                                                   List<Integer> numbOfBookedPositionByLongTeacherForEachDay,
                                                   List<Integer> numbOfBookedPositionByShortTeacherForEachDay){
        calculationForStatisticData(numbOfStudents, durationOfLongIntervInMinutes, durationOfShortIntervInMinutes,
                numbOfAllDaysForInterviewing, numbOfHoursForIntervForDay, totalNumbOfRegistersdTeachersWithLongerInterv,
                totalNumbOfRegistersdTeachersWithShorterInterv, numbOfBookedPositionByLongTeacherForEachDay,
                numbOfBookedPositionByShortTeacherForEachDay);
        /*double percentage = 100.0; // На стільки відсотків має задовільняти наявна к-сть викладачів студентів, що залишаться після відінмання цих
        return (int) (numbOfStudents - Math.ceil(1.0 * totalNumbOfAvailableIntervByRegisteredTeachers * percentage / totalPercentageOfAvailableIntervByRegisteredTeachers));*/
        return (numbOfStudents - totalNumbOfAvailableIntervByRegisteredTeachers) >= 0 ? numbOfStudents - totalNumbOfAvailableIntervByRegisteredTeachers : 0;
    }

    private static double roundTo2(double numb){
        numb = (int) (numb * 100);
        numb = numb / 100.0;
        return numb;
    }

    // загальна необхідна к-сть викладачів з довшою співбесідою (не перераховується, а рахується лише на початку; 120%)
    // якщо студенти й викладачі вже почнуть набиратися, ці дані залишаться НЕЗМІННИМИ - скільки адміну треба було запросити відпочатку
    // Це інфа для першої розсилки запрошень викладачам
    public static int getTotalNumberOfNecessaryTeacherWithLongerInterview(int numbOfStudents,
                                                                          int durationOfLongIntervInMinutes,
                                                                          int durationOfShortIntervInMinutes,
                                                                          int numbOfAllDaysForInterviewing,
                                                                          int numbOfHoursForIntervForDay,
                                                                          int totalNumbOfRegistersdTeachersWithLongerInterv,
                                                                          int totalNumbOfRegistersdTeachersWithShorterInterv,
                                                                          List<Integer> numbOfBookedPositionByLongTeacherForEachDay,
                                                                          List<Integer> numbOfBookedPositionByShortTeacherForEachDay){
        calculationOfNecessaryNumbOfTeacher(numbOfStudents, durationOfLongIntervInMinutes, durationOfShortIntervInMinutes,
                numbOfAllDaysForInterviewing, numbOfHoursForIntervForDay, totalNumbOfRegistersdTeachersWithLongerInterv,
                totalNumbOfRegistersdTeachersWithShorterInterv, numbOfBookedPositionByLongTeacherForEachDay,
                numbOfBookedPositionByShortTeacherForEachDay);
        return totalNumberOfNecessaryTeacherWithLongerInterview;
    }

    // загальна необхідна к-сть викладачів з коротшою співбесідою (не перераховується, а рахується лише на початку; 120%)
    // якщо студенти й викладачі вже почнуть набиратися, ці дані залишаться НЕЗМІННИМИ - скільки адміну треба було запросити відпочатку
    // Це інфа для першої розсилки запрошень викладачам
    public static int getTotalNumberOfNecessaryTeacherWithShorterInterview(int numbOfStudents,
                                                                           int durationOfLongIntervInMinutes,
                                                                           int durationOfShortIntervInMinutes,
                                                                           int numbOfAllDaysForInterviewing,
                                                                           int numbOfHoursForIntervForDay,
                                                                           int totalNumbOfRegistersdTeachersWithLongerInterv,
                                                                           int totalNumbOfRegistersdTeachersWithShorterInterv,
                                                                           List<Integer> numbOfBookedPositionByLongTeacherForEachDay,
                                                                           List<Integer> numbOfBookedPositionByShortTeacherForEachDay){
        calculationOfNecessaryNumbOfTeacher(numbOfStudents, durationOfLongIntervInMinutes, durationOfShortIntervInMinutes,
                numbOfAllDaysForInterviewing, numbOfHoursForIntervForDay, totalNumbOfRegistersdTeachersWithLongerInterv,
                totalNumbOfRegistersdTeachersWithShorterInterv, numbOfBookedPositionByLongTeacherForEachDay,
                numbOfBookedPositionByShortTeacherForEachDay);
        return totalNumberOfNecessaryTeacherWithShorterInterview;
    }

    // Відсоток набраних викладачів з довшою співбесідою в кожний день
    public static List<Double> getPercentageOfRegisteredTeacherWithLongIntervForEachDay(int numbOfStudents,
                                                                                             int durationOfLongIntervInMinutes,
                                                                                             int durationOfShortIntervInMinutes,
                                                                                             int numbOfAllDaysForInterviewing,
                                                                                             int numbOfHoursForIntervForDay,
                                                                                             int totalNumbOfRegistersdTeachersWithLongerInterv,
                                                                                             int totalNumbOfRegistersdTeachersWithShorterInterv,
                                                                                             List<Integer> numbOfBookedPositionByLongTeacherForEachDay,
                                                                                             List<Integer> numbOfBookedPositionByShortTeacherForEachDay){
        calculationForStatisticData(numbOfStudents, durationOfLongIntervInMinutes, durationOfShortIntervInMinutes,
                numbOfAllDaysForInterviewing, numbOfHoursForIntervForDay, totalNumbOfRegistersdTeachersWithLongerInterv,
                totalNumbOfRegistersdTeachersWithShorterInterv, numbOfBookedPositionByLongTeacherForEachDay,
                numbOfBookedPositionByShortTeacherForEachDay);
        return percentageOfRegisteredTeacherWithLongIntervForEachDay;
    }

    // Відсоток набраних викладачів з коротшою співбесідою в кожний день
    public static List<Double> getPercentageOfRegisteredTeacherWithShortIntervForEachDay(int numbOfStudents,
                                                                                              int durationOfLongIntervInMinutes,
                                                                                              int durationOfShortIntervInMinutes,
                                                                                              int numbOfAllDaysForInterviewing,
                                                                                              int numbOfHoursForIntervForDay,
                                                                                              int totalNumbOfRegistersdTeachersWithLongerInterv,
                                                                                              int totalNumbOfRegistersdTeachersWithShorterInterv,
                                                                                              List<Integer> numbOfBookedPositionByLongTeacherForEachDay,
                                                                                              List<Integer> numbOfBookedPositionByShortTeacherForEachDay){
        calculationForStatisticData(numbOfStudents, durationOfLongIntervInMinutes, durationOfShortIntervInMinutes,
                numbOfAllDaysForInterviewing, numbOfHoursForIntervForDay, totalNumbOfRegistersdTeachersWithLongerInterv,
                totalNumbOfRegistersdTeachersWithShorterInterv, numbOfBookedPositionByLongTeacherForEachDay,
                numbOfBookedPositionByShortTeacherForEachDay);
        return percentageOfRegisteredTeacherWithShortIntervForEachDay;
    }
    /*
    // К-сть викладачів з довшою тривалістю співбесіди, яку ще необхідну добрати, щоб к-сть набраних викладачів цього типу задовольнила необхідну к-сть на 80%
    public static int getTotalNecessaryNumbOfLongTeacher(int numbOfStudents, int durationOfLongIntervInMinutes, int durationOfShortIntervInMinutes, int numbOfAllDaysForInterviewing, int numbOfHoursForIntervForDay, int totalNumbOfRegistersdTeachersWithLongerInterv, int totalNumbOfRegistersdTeachersWithShorterInterv, ArrayList<Integer> numbOfBookedPositionByLongTeacherForEachDay, ArrayList<Integer> numbOfBookedPositionByShortTeacherForEachDay){
        calculationForStatisticData(numbOfStudents, durationOfLongIntervInMinutes, durationOfShortIntervInMinutes, numbOfAllDaysForInterviewing, numbOfHoursForIntervForDay, totalNumbOfRegistersdTeachersWithLongerInterv, totalNumbOfRegistersdTeachersWithShorterInterv, numbOfBookedPositionByLongTeacherForEachDay, numbOfBookedPositionByShortTeacherForEachDay);
        return totalNecessaryNumbOfLongTeacher;
    }
    // К-сть викладачів з коротшою тривалістю співбесіди, яку ще необхідну добрати, щоб к-сть набраних викладачів цього типу задовольнила необхідну к-сть на 80%
    public static int getTotalNecessaryNumbOfShortTeacher(int numbOfStudents, int durationOfLongIntervInMinutes, int durationOfShortIntervInMinutes, int numbOfAllDaysForInterviewing, int numbOfHoursForIntervForDay, int totalNumbOfRegistersdTeachersWithLongerInterv, int totalNumbOfRegistersdTeachersWithShorterInterv, ArrayList<Integer> numbOfBookedPositionByLongTeacherForEachDay, ArrayList<Integer> numbOfBookedPositionByShortTeacherForEachDay){
        calculationForStatisticData(numbOfStudents, durationOfLongIntervInMinutes, durationOfShortIntervInMinutes, numbOfAllDaysForInterviewing, numbOfHoursForIntervForDay, totalNumbOfRegistersdTeachersWithLongerInterv, totalNumbOfRegistersdTeachersWithShorterInterv, numbOfBookedPositionByLongTeacherForEachDay, numbOfBookedPositionByShortTeacherForEachDay);
        return totalNecessaryNumbOfShortTeacher;
    }*/

    // К-сть можливих співбесід в і-й день з поточною к-стю викладачів
    public static List<Integer> getNumbOfAvailableIntervForEachDayByRegisteredTeachers(int numbOfStudents,
                                                                                            int durationOfLongIntervInMinutes,
                                                                                            int durationOfShortIntervInMinutes,
                                                                                            int numbOfAllDaysForInterviewing,
                                                                                            int numbOfHoursForIntervForDay,
                                                                                            int totalNumbOfRegistersdTeachersWithLongerInterv,
                                                                                            int totalNumbOfRegistersdTeachersWithShorterInterv,
                                                                                            List<Integer> numbOfBookedPositionByLongTeacherForEachDay,
                                                                                            List<Integer> numbOfBookedPositionByShortTeacherForEachDay){
        calculationForStatisticData(numbOfStudents, durationOfLongIntervInMinutes, durationOfShortIntervInMinutes,
                numbOfAllDaysForInterviewing, numbOfHoursForIntervForDay, totalNumbOfRegistersdTeachersWithLongerInterv,
                totalNumbOfRegistersdTeachersWithShorterInterv, numbOfBookedPositionByLongTeacherForEachDay,
                numbOfBookedPositionByShortTeacherForEachDay);
        return numbOfAvailableIntervForEachDayByRegisteredTeachers;
    }

    // Відсоток співбесід в день в і-й день, від запланованої к-сті, що можна провести за наявної к-сті викладачів
    public static List<Double> getPercentageOfAvailableIntervForEachDayByRegisteredTeachers(int numbOfStudents,
                                                                                                 int durationOfLongIntervInMinutes,
                                                                                                 int durationOfShortIntervInMinutes,
                                                                                                 int numbOfAllDaysForInterviewing,
                                                                                                 int numbOfHoursForIntervForDay,
                                                                                                 int totalNumbOfRegistersdTeachersWithLongerInterv,
                                                                                                 int totalNumbOfRegistersdTeachersWithShorterInterv,
                                                                                                 List<Integer> numbOfBookedPositionByLongTeacherForEachDay,
                                                                                                 List<Integer> numbOfBookedPositionByShortTeacherForEachDay){
        calculationForStatisticData(numbOfStudents, durationOfLongIntervInMinutes, durationOfShortIntervInMinutes,
                numbOfAllDaysForInterviewing, numbOfHoursForIntervForDay, totalNumbOfRegistersdTeachersWithLongerInterv,
                totalNumbOfRegistersdTeachersWithShorterInterv, numbOfBookedPositionByLongTeacherForEachDay,
                numbOfBookedPositionByShortTeacherForEachDay);
        return percentageOfAvailableIntervForEachDayByRegisteredTeachers;
    }

    // !!! Основний критерій для адміна: відсоток співбесід, що можна провести за наявної к-сті викладачів
    public static double getTotalPercentageOfAvailableIntervByRegisteredTeachers(int numbOfStudents,
                                                                                 int durationOfLongIntervInMinutes,
                                                                                 int durationOfShortIntervInMinutes,
                                                                                 int numbOfAllDaysForInterviewing,
                                                                                 int numbOfHoursForIntervForDay,
                                                                                 int totalNumbOfRegistersdTeachersWithLongerInterv,
                                                                                 int totalNumbOfRegistersdTeachersWithShorterInterv,
                                                                                 List<Integer> numbOfBookedPositionByLongTeacherForEachDay,
                                                                                 List<Integer> numbOfBookedPositionByShortTeacherForEachDay){
        calculationForStatisticData(numbOfStudents, durationOfLongIntervInMinutes, durationOfShortIntervInMinutes,
                numbOfAllDaysForInterviewing, numbOfHoursForIntervForDay, totalNumbOfRegistersdTeachersWithLongerInterv,
                totalNumbOfRegistersdTeachersWithShorterInterv, numbOfBookedPositionByLongTeacherForEachDay,
                numbOfBookedPositionByShortTeacherForEachDay);
        return totalPercentageOfAvailableIntervByRegisteredTeachers;
    }

    // К-сть можливих співбесід, за всі дні, за поточної к-сті викладачів
    public static int getTotalNumbOfAvailableIntervByRegisteredTeachers(int numbOfStudents, int durationOfLongIntervInMinutes,
                                                                        int durationOfShortIntervInMinutes,
                                                                        int numbOfAllDaysForInterviewing,
                                                                        int numbOfHoursForIntervForDay,
                                                                        int totalNumbOfRegistersdTeachersWithLongerInterv,
                                                                        int totalNumbOfRegistersdTeachersWithShorterInterv,
                                                                        List<Integer> numbOfBookedPositionByLongTeacherForEachDay,
                                                                        List<Integer> numbOfBookedPositionByShortTeacherForEachDay){
        calculationForStatisticData(numbOfStudents, durationOfLongIntervInMinutes, durationOfShortIntervInMinutes,
                numbOfAllDaysForInterviewing, numbOfHoursForIntervForDay, totalNumbOfRegistersdTeachersWithLongerInterv,
                totalNumbOfRegistersdTeachersWithShorterInterv, numbOfBookedPositionByLongTeacherForEachDay,
                numbOfBookedPositionByShortTeacherForEachDay);
        return totalNumbOfAvailableIntervByRegisteredTeachers;
    }

    // К-сть довших викладачів, яких ще треба запросити на і-й день
    public static List<Integer> getNecessaryNumbOfLongTeacherForEachDay(int numbOfStudents,
                                                                             int durationOfLongIntervInMinutes,
                                                                             int durationOfShortIntervInMinutes,
                                                                             int numbOfAllDaysForInterviewing,
                                                                             int numbOfHoursForIntervForDay,
                                                                             int totalNumbOfRegistersdTeachersWithLongerInterv,
                                                                             int totalNumbOfRegistersdTeachersWithShorterInterv,
                                                                             List<Integer> numbOfBookedPositionByLongTeacherForEachDay,
                                                                             List<Integer> numbOfBookedPositionByShortTeacherForEachDay){
        calculationForStatisticData(numbOfStudents, durationOfLongIntervInMinutes, durationOfShortIntervInMinutes,
                numbOfAllDaysForInterviewing, numbOfHoursForIntervForDay, totalNumbOfRegistersdTeachersWithLongerInterv,
                totalNumbOfRegistersdTeachersWithShorterInterv, numbOfBookedPositionByLongTeacherForEachDay,
                numbOfBookedPositionByShortTeacherForEachDay);
        return necessaryNumbOfLongTeacherForEachDay;
    }

    // К-сть коротших викладачів, яких ще треба запросити на і-й день
    public static List<Integer> getNecessaryNumbOfShortTeacherForEachDay(int numbOfStudents,
                                                                              int durationOfLongIntervInMinutes,
                                                                              int durationOfShortIntervInMinutes,
                                                                              int numbOfAllDaysForInterviewing,
                                                                              int numbOfHoursForIntervForDay,
                                                                              int totalNumbOfRegistersdTeachersWithLongerInterv,
                                                                              int totalNumbOfRegistersdTeachersWithShorterInterv,
                                                                              List<Integer> numbOfBookedPositionByLongTeacherForEachDay,
                                                                              List<Integer> numbOfBookedPositionByShortTeacherForEachDay){
        calculationForStatisticData(numbOfStudents, durationOfLongIntervInMinutes, durationOfShortIntervInMinutes,
                numbOfAllDaysForInterviewing, numbOfHoursForIntervForDay, totalNumbOfRegistersdTeachersWithLongerInterv,
                totalNumbOfRegistersdTeachersWithShorterInterv, numbOfBookedPositionByLongTeacherForEachDay,
                numbOfBookedPositionByShortTeacherForEachDay);
        return necessaryNumbOfShortTeacherForEachDay;
    }

    /*
    // К-сть додаткових співбесід для кожного дня, якщо адмін активує ф-цію "Самостійно дознайти викладачів" (записати в БД)
    public static ArrayList<Integer> getnumbOfAdditionIntervForEachday(int numbOfStudents, int durationOfLongIntervInMinutes, int durationOfShortIntervInMinutes, int numbOfAllDaysForInterviewing, int numbOfHoursForIntervForDay, int totalNumbOfRegistersdTeachersWithLongerInterv, int totalNumbOfRegistersdTeachersWithShorterInterv, ArrayList<Integer> numbOfBookedPositionByLongTeacherForEachDay, ArrayList<Integer> numbOfBookedPositionByShortTeacherForEachDay){
        calculationForStatisticData(numbOfStudents, durationOfLongIntervInMinutes, durationOfShortIntervInMinutes, numbOfAllDaysForInterviewing, numbOfHoursForIntervForDay, totalNumbOfRegistersdTeachersWithLongerInterv, totalNumbOfRegistersdTeachersWithShorterInterv, numbOfBookedPositionByLongTeacherForEachDay, numbOfBookedPositionByShortTeacherForEachDay);
        return numbOfAdditionIntervForEachday;
    }*/

    public static int getNumbOfIntervByGroupOfTeacherPerDay(int numbOfStudents, int durationOfLongIntervInMinutes,
                                                            int durationOfShortIntervInMinutes, int numbOfAllDaysForInterviewing,
                                                            int numbOfHoursForIntervForDay,
                                                            int totalNumbOfRegistersdTeachersWithLongerInterv,
                                                            int totalNumbOfRegistersdTeachersWithShorterInterv,
                                                            List<Integer> numbOfBookedPositionByLongTeacherForEachDay,
                                                            List<Integer> numbOfBookedPositionByShortTeacherForEachDay) {
        calculationForStatisticData(numbOfStudents, durationOfLongIntervInMinutes, durationOfShortIntervInMinutes,
                numbOfAllDaysForInterviewing, numbOfHoursForIntervForDay, totalNumbOfRegistersdTeachersWithLongerInterv,
                totalNumbOfRegistersdTeachersWithShorterInterv, numbOfBookedPositionByLongTeacherForEachDay,
                numbOfBookedPositionByShortTeacherForEachDay);
        return numbOfIntervByGroupOfTeacherPerDay;
    }

    public static int getN(int numbOfStudents, int durationOfLongIntervInMinutes, int durationOfShortIntervInMinutes,
                           int numbOfAllDaysForInterviewing, int numbOfHoursForIntervForDay,
                           int totalNumbOfRegistersdTeachersWithLongerInterv,
                           int totalNumbOfRegistersdTeachersWithShorterInterv,
                           List<Integer> numbOfBookedPositionByLongTeacherForEachDay,
                           List<Integer> numbOfBookedPositionByShortTeacherForEachDay) {
        calculationOfNecessaryNumbOfTeacher(numbOfStudents, durationOfLongIntervInMinutes, durationOfShortIntervInMinutes,
                numbOfAllDaysForInterviewing, numbOfHoursForIntervForDay, totalNumbOfRegistersdTeachersWithLongerInterv,
                totalNumbOfRegistersdTeachersWithShorterInterv, numbOfBookedPositionByLongTeacherForEachDay,
                numbOfBookedPositionByShortTeacherForEachDay);
        return N;
    }
}
