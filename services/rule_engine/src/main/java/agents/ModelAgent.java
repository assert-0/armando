package agents;

import java.time.Instant;
import java.util.Random;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import lombok.*;

import signals.StatsSignal;


@Getter
@Setter
public class ModelAgent extends AbstractAgent {
    public static String ID = "ModelAgent";

    private static final int LAST = 30;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DifferenceGrowth {
        private double difference;
        private boolean growing;
    }

    private Date lastUpdateTime = new Date();
    private Date lastCheckTime = new Date();

    private double numOfSearches = 20_000;
    private double numOfREs = 300;
    private double numOfSold = 20;
    private double avgCost = 100_000;

    private LinkedList<Double> numOfSearchesList = new LinkedList<>();
    private LinkedList<Double> numOfREsList = new LinkedList<>();
    private LinkedList<Double> numOfSoldList = new LinkedList<>();
    private LinkedList<Double> avgCostList = new LinkedList<>();

    private LinkedList<DifferenceGrowth> numOfSearchesDiffList = new LinkedList<>();
    private LinkedList<DifferenceGrowth> numOfREsDiffList = new LinkedList<>();
    private LinkedList<DifferenceGrowth> numOfSoldDiffList = new LinkedList<>();
    private LinkedList<DifferenceGrowth> avgCostDiffList = new LinkedList<>();

    public ModelAgent() {
        super(ID);
        for (int i = 0; i < 100; ++i) updateStats(); // prefill
    }

    public void updateStats() {
        var random = new Random();
        //
        numOfSearches = Math.abs(random.nextGaussian(numOfSearches, 3));
        numOfREs = Math.abs(random.nextGaussian(numOfREs, 0.5));
        numOfSold = Math.abs(random.nextGaussian(numOfSold, 0.5));
        avgCost = Math.abs(random.nextGaussian(avgCost, 50));
        //
        numOfSearchesList.add(numOfSearches);
        numOfREsList.add(numOfREs);
        numOfSoldList.add(numOfSold);
        avgCostList.add(avgCost);
    }

    public DifferenceGrowth calculateDiff(LinkedList<Double> list) {
        var diff = Math.abs(1 - list.getLast() / list.getFirst());
        return new DifferenceGrowth(diff, list.getLast() > list.getFirst());
    }

    public DifferenceGrowth getMax(LinkedList<DifferenceGrowth> list, int last) {
        var maxValue = list.getFirst();
        for (int i = Math.abs(list.size() - last); i < list.size(); ++i) {
            if (list.get(i).getDifference() > maxValue.getDifference()) {
                maxValue = list.get(i);
            }
        }
        return maxValue;
    }

    public Double getMaxDouble(LinkedList<Double> list, int last) {
        var maxValue = list.getFirst();
        for (int i = Math.abs(list.size() - last); i < list.size(); ++i) {
            if (list.get(i) > maxValue) {
                maxValue = list.get(i);
            }
        }
        return maxValue;
    }

    public Double getMinDouble(LinkedList<Double> list, int last) {
        var minValue = list.getFirst();
        for (int i = Math.abs(list.size() - last); i < list.size(); ++i) {
            if (list.get(i) < minValue) {
                minValue = list.get(i);
            }
        }
        return minValue;
    }

    public void checkStats() {
        var numOfSearchesDiff = calculateDiff(numOfSearchesList);
        numOfSearchesDiffList.add(numOfSearchesDiff);

        var numOfREsDiff = calculateDiff(numOfREsList);
        numOfREsDiffList.add(numOfREsDiff);

        var numOfSoldDiff = calculateDiff(numOfSoldList);
        numOfSoldDiffList.add(numOfSoldDiff);

        var avgCostDiff = calculateDiff(avgCostList);
        avgCostDiffList.add(avgCostDiff);

        var numOfSearchesDiffMax = getMax(numOfSearchesDiffList, LAST);

        var numOfREsDiffMax = getMax(numOfREsDiffList, LAST);

        var numOfSoldDiffMax = getMax(numOfSoldDiffList, LAST);

        var avgCostDiffMax = getMax(avgCostDiffList, LAST);

        var theBest = getMax(
            new LinkedList<>(List.of(numOfSearchesDiffMax, numOfREsDiffMax, numOfSoldDiffMax, avgCostDiffMax)),
            LAST);

        StatsSignal signal;

        if (theBest == numOfSearchesDiffMax) {
            signal = new StatsSignal(
                " broj pretraga ",
                numOfSearchesDiffMax.isGrowing(),
                numOfSearchesDiffMax.getDifference(),
                numOfSearchesList,
                getMaxDouble(numOfSearchesList, LAST),
                getMinDouble(numOfSearchesList, LAST)
            );
        }
        else if (theBest == numOfREsDiffMax) {
            signal = new StatsSignal(
                " broj izlistanih nekretnina ",
                numOfREsDiffMax.isGrowing(),
                numOfREsDiffMax.getDifference(),
                numOfREsList,
                getMaxDouble(numOfREsList, LAST),
                getMinDouble(numOfREsList, LAST)
            );
        }
        else if (theBest == numOfSoldDiffMax) {
            signal = new StatsSignal(
                " broj prodanih nekretnina ",
                numOfSoldDiffMax.isGrowing(),
                numOfSoldDiffMax.getDifference(),
                numOfSoldList,
                getMaxDouble(numOfSoldList, LAST),
                getMinDouble(numOfSoldList, LAST)
            );
        }
        else {
            signal = new StatsSignal(
                " prosječna cijena nekretnine ",
                avgCostDiffMax.isGrowing(),
                avgCostDiffMax.getDifference(),
                avgCostList,
                getMaxDouble(avgCostList, LAST),
                getMinDouble(avgCostList, LAST)
            );
        }

        sendBroadcast(Armando.class, signal);
    }
}
