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
    }

    public void updateStats() {
        var random = new Random(Instant.now().getEpochSecond());
        //
        numOfSearches = Math.abs(random.nextGaussian(numOfSearches, 3_000));
        numOfREs = Math.abs(random.nextGaussian(numOfREs, 50));
        numOfSold = Math.abs(random.nextGaussian(numOfSold, 10));
        avgCost = Math.abs(random.nextGaussian(avgCost, 50_000));
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

    public DifferenceGrowth getMax(LinkedList<DifferenceGrowth> list) {
        var maxValue = list.getFirst();
        for (var i : list) {
            if (i.getDifference() > maxValue.getDifference()) {
                maxValue = i;
            }
        }
        return maxValue;
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

        var numOfSearchesDiffMax = getMax(numOfSearchesDiffList);

        var numOfREsDiffMax = getMax(numOfREsDiffList);

        var numOfSoldDiffMax = getMax(numOfSoldDiffList);

        var avgCostDiffMax = getMax(avgCostDiffList);

        var theBest = getMax(new LinkedList<>(List.of(numOfSearchesDiffMax, numOfREsDiffMax, numOfSoldDiffMax, avgCostDiffMax)));

        StatsSignal signal;

        if (theBest == numOfSearchesDiffMax) {
            signal = new StatsSignal(
                " broj pretraga ",
                numOfSearchesDiffMax.isGrowing(),
                numOfSearchesDiffMax.getDifference(),
                new LinkedList<>(numOfSearchesList)
            );
        }
        else if (theBest == numOfREsDiffMax) {
            signal = new StatsSignal(
                " broj izlistanih nekretnina ",
                numOfREsDiffMax.isGrowing(),
                numOfREsDiffMax.getDifference(),
                new LinkedList<>(numOfREsList)
            );
        }
        else if (theBest == numOfSoldDiffMax) {
            signal = new StatsSignal(
                " broj prodanih nekretnina ",
                numOfSoldDiffMax.isGrowing(),
                numOfSoldDiffMax.getDifference(),
                new LinkedList<>(numOfSoldList)
            );
        }
        else {
            signal = new StatsSignal(
                " prosječna cijena nekretnine ",
                avgCostDiffMax.isGrowing(),
                avgCostDiffMax.getDifference(),
                new LinkedList<>(avgCostList)
            );
        }

        numOfSearchesList.clear();
        numOfREsList.clear();
        numOfSoldList.clear();
        avgCostList.clear();

        sendBroadcast(Armando.class, signal);
    }
}
