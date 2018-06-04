package com.mytest;

import com.sun.tools.javac.util.Pair;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class StreamProcessor {

    private final List<Instruction> instructionsList;

    public StreamProcessor(List<Instruction> instructionsList) {
        this.instructionsList = instructionsList.stream().map( instr -> correctDate(instr)).collect(Collectors.toList());
    }

    public Map<LocalDate,Double> settledReport(InstrType instType) {

        return instructionsList.stream().filter(instr -> instr.instrType().compareTo(instType) == 0 ).collect(
                Collectors.groupingBy(
                        Instruction::getSettlementDate,
                        Collectors.summingDouble(Instruction::getUSDAmountOfTrade)));
    }

    public Map<String, Pair<Integer, Double>> entityRanking(InstrType instType) {

        Map<String, Double> entityToOutgoingMap = instructionsList.stream()
                .filter(instr -> instr.instrType().compareTo(instType) == 0 ).collect(
                Collectors.groupingBy(
                        Instruction::getEntity,
                        Collectors.summingDouble(Instruction::getUSDAmountOfTrade)));

        Map<String, Double> entityToOutgoingMapSorted = sortByValue(entityToOutgoingMap);
        Map<String, Pair<Integer, Double>> entityToRankingMap = new HashMap<>();

        int rank = 0;
        for (Map.Entry<String, Double> entry : entityToOutgoingMapSorted.entrySet()) {
            entityToRankingMap.put(entry.getKey(), new Pair<>(rank, entry.getValue()));
            rank++;
        }

        return entityToRankingMap;
    }

    private static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> unsortMap) {

        List<Map.Entry<K, V>> list =
                new LinkedList<Map.Entry<K, V>>(unsortMap.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                return (o1.getValue()).compareTo(o2.getValue()) * (-1);
            }
        });

        Map<K, V> result = new LinkedHashMap<K, V>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }

    private Instruction correctDate(final Instruction instr) {

        if (instr == null) {
            return instr;
        }

        LocalDate date = instr.getSettlementDate();

        if (instr.getCurrency().equals("AED") || instr.getCurrency().equals("SAR")) {
            if ( date.getDayOfWeek().equals(DayOfWeek.FRIDAY)) {
                instr.setSettlementDate(date.plusDays(2));
            }

            if ( date.getDayOfWeek().equals(DayOfWeek.SATURDAY)) {
                instr.setSettlementDate(date.plusDays(1));
            }

            return instr;
        }

        if ( date.getDayOfWeek().equals(DayOfWeek.SATURDAY)) {
            instr.setSettlementDate(date.plusDays(2));
        }

        if ( date.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
            instr.setSettlementDate(date.plusDays(1));
        }

        return instr;
    }
}
