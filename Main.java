package com.mytest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static List<Instruction> buildInstructions () {

        List<Instruction> instrList = new ArrayList<>();

        final String entity1 = "foo";
        final String entity2 = "bar";
        final Double agreedFx = 1.0;
        final String currency = "SAR";
        final LocalDate instructionDate = LocalDate.now();
        final LocalDate settlementDate = LocalDate.now();
        final Integer units = 5 ;
        final Double pricePerUnit = 10.0;
        Instruction instr1 = new Instruction(entity1,
                InstrType.BUY,
                agreedFx,
                currency,
                instructionDate,
                settlementDate,
                units+3,
                pricePerUnit);

        Instruction instr2 = new Instruction(entity2,
                InstrType.BUY,
                agreedFx,
                currency,
                instructionDate,
                settlementDate,
                units+1,
                pricePerUnit);
        instrList.add(instr1);
        instrList.add(instr2);

        Instruction instr3 = new Instruction(entity2,
                InstrType.SELL,
                agreedFx,
                currency,
                instructionDate,
                settlementDate.plusDays(2),
                units+1,
                pricePerUnit);

        Instruction instr4 = new Instruction(entity2,
                InstrType.SELL,
                agreedFx,
                currency,
                instructionDate,
                settlementDate.plusDays(3),
                units+1,
                pricePerUnit);

        instrList.add(instr1);
        instrList.add(instr2);
        instrList.add(instr3);
        instrList.add(instr4);
        return instrList;
    }

    public static void main(String[] args) {

        List<Instruction> instrList = buildInstructions();


        StreamProcessor processor = new  StreamProcessor(instrList);

        System.out.println("Outgoing summary per settlementDate");
        System.out.println(processor.settledReport(InstrType.BUY));
        System.out.println("Outgoing entity ranking [Ranking, Amount]");
        System.out.println(processor.entityRanking(InstrType.BUY));
        System.out.println("Incoming summary per settlementDate" );
        System.out.println(processor.settledReport(InstrType.SELL));
        System.out.println("Incoming entity ranking [Ranking, Amount]");
        System.out.println(processor.entityRanking(InstrType.SELL));
    }
}
