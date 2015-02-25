package com.nmarina.dataobject;


import java.io.FileNotFoundException;
import java.util.*;

public class Atm {

    private static Atm atm;

    private Atm() {
        atmNotes = new TreeMap<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2.compareTo(o1);
            }
        });
        atmNotes.put(500, 2);
        atmNotes.put(200, 4);
        atmNotes.put(100, 7);
        atmNotes.put(50, 10);
    }

    /**
     * Получить объект
     * @return
     */
    public static Atm getAtmInstance() {
        if (atm == null) {
            atm = new Atm();
        }
        return atm;
    }

    /** Деньги банкомата */
    private Map<Integer, Integer> atmNotes;
    /** Шаблон хранения купюр у банкомата */
    private final static Map<Integer, Integer> notesPattern;

    static {
        Map<Integer, Integer> tempNotesPattern = new TreeMap<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2.compareTo(o1);
            }
        });
        tempNotesPattern.put(500, 2);
        tempNotesPattern.put(200, 4);
        tempNotesPattern.put(100, 7);
        tempNotesPattern.put(50, 10);
        notesPattern = Collections.unmodifiableMap(tempNotesPattern);
    }

    /**
     * Добавить деньги в банкомат
     * @param note - деньги
     * @return
     */
    public boolean addCashToAtm(Integer note) {
        boolean result = false;
        try {
            if (note != null && note > 0) {
                for (Map.Entry<Integer, Integer> notePattern : notesPattern.entrySet()) {
                    if (notePattern.getKey().equals(note)) {
                        int atmNoteCount = atmNotes.get(notePattern.getKey());
                        if (atmNoteCount < notePattern.getValue()) {
                            atmNotes.put(notePattern.getKey(), ++atmNoteCount);
                            result = true;
                            break;
                        }
                    }
                }
            }
            if (result == false) {
                throw new UnsupportedOperationException();
            }
        } catch (UnsupportedOperationException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    /**
     * Получить баланс денежных средств банкомата
     * @return
     */
    public int getAtmBalance() {
        int atmBalance = 0;
        for (Map.Entry<Integer, Integer> atmNote : atmNotes.entrySet()) {
            atmBalance += atmNote.getKey() * atmNote.getValue();
        }
        return atmBalance;
    }

    /**
     * Снять деньги с банкомата
     * @param moneyAmount - снимаемая сумма
     * @return - снимаемая сумма или -1(ошибка)
     */
    public int getCashFromAtm(Integer moneyAmount) {
        int result = -1;
        if (moneyAmount != null && moneyAmount > 0 && moneyAmount <= this.getAtmBalance() && moneyAmount % 50 == 0) {
            Map<Integer, Integer> copyAtmNotes = new TreeMap<>(atmNotes);
            int withdrawMoney = moneyAmount;
            for (Map.Entry<Integer, Integer> atmNote : atmNotes.entrySet()) {
                int note = atmNote.getKey();
                int noteCount = atmNote.getValue();
                while ((withdrawMoney - note >= 0) && noteCount > 0) {
                    withdrawMoney -= note;
                    atmNote.setValue(--noteCount);
                }
            }
            if (withdrawMoney == 0) {
                result = moneyAmount;
            } else {
                atmNotes = copyAtmNotes;
            }
        }
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Atm{");
        sb.append("atmNotes=").append(atmNotes);
        sb.append('}');
        return sb.toString();
    }
}
