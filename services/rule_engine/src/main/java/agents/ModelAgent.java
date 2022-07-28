package agents;

import java.time.Instant;
import java.util.Random;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import lombok.*;

import signals.StatsSignal;
import com.mindsmiths.ruleEngine.util.Log;


@Getter
@Setter
public class ModelAgent extends AbstractAgent {
    public static String ID = "ModelAgent";

    private static final int LAST_DIFF = 10;
    private static final int LAST_MAX = 30;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DifferenceGrowth {
        private double difference;
        private boolean growing;
    }

    private Date lastUpdateTime = new Date();
    private Date lastCheckTime = new Date();

    private double numOfSearches = 2_000;
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
        for (int i = 0; i < 200; ++i) updateStats(); // prefill
    }

    public void updateStats() {
        var random = new Random();
        //
        numOfSearches = Math.abs(random.nextGaussian(numOfSearches, numOfSearches * 0.07));
        numOfREs = Math.abs(random.nextGaussian(numOfREs, numOfREs * 0.07));
        numOfSold = Math.abs(random.nextGaussian(numOfSold, numOfSold * 0.07));
        avgCost = Math.abs(random.nextGaussian(avgCost, avgCost * 0.07));
        //
        numOfSearchesList.add(numOfSearches);
        numOfREsList.add(numOfREs);
        numOfSoldList.add(numOfSold);
        avgCostList.add(avgCost);
    }

    private static int clamp(int value) {
        return value >= 0 ? value : 0;
    }

    public DifferenceGrowth calculateDiff(LinkedList<Double> list, int lastAmount) {
        var last = list.getLast();
        var first = list.get(clamp(list.size() - lastAmount));
        double diff; 
        boolean growing;
        if (last < first) {
            diff = 1 - (last / first);
            growing = false;
        }
        else {
            diff = (last / first) - 1;
            growing = true;
        }
        return new DifferenceGrowth(diff, growing);
    }

    public DifferenceGrowth getMax(LinkedList<DifferenceGrowth> list, int last) {
        var maxValue = list.getFirst();
        for (int i = clamp(list.size() - last); i < list.size(); ++i) {
            if (list.get(i).getDifference() > maxValue.getDifference()) {
                maxValue = list.get(i);
            }
        }
        return maxValue;
    }

    public DifferenceGrowth getMin(LinkedList<DifferenceGrowth> list, int last) {
        var minValue = list.getFirst();
        for (int i = clamp(list.size() - last); i < list.size(); ++i) {
            if (list.get(i).getDifference() < minValue.getDifference()) {
                minValue = list.get(i);
            }
        }
        return minValue;
    }

    public Double getMaxDouble(LinkedList<Double> list, int last) {
        var maxValue = list.getFirst();
        for (int i = clamp(list.size() - last); i < list.size(); ++i) {
            if (list.get(i) > maxValue) {
                maxValue = list.get(i);
            }
        }
        return maxValue;
    }

    public Double getMinDouble(LinkedList<Double> list, int last) {
        var minValue = list.getFirst();
        for (int i = clamp(list.size() - last); i < list.size(); ++i) {
            if (list.get(i) < minValue) {
                minValue = list.get(i);
            }
        }
        return minValue;
    }

    public void checkStats() {
        var numOfSearchesDiff = calculateDiff(numOfSearchesList, LAST_DIFF);
        numOfSearchesDiffList.add(numOfSearchesDiff);
        if (numOfSearchesDiffList.size() > 300) numOfSearchesDiffList.removeFirst();
        var numOfREsDiff = calculateDiff(numOfREsList, LAST_DIFF);
        numOfREsDiffList.add(numOfREsDiff);
        if (numOfREsDiffList.size() > 300) numOfREsDiffList.removeFirst();
        var numOfSoldDiff = calculateDiff(numOfSoldList, LAST_DIFF);
        numOfSoldDiffList.add(numOfSoldDiff);
        if (numOfSoldDiffList.size() > 300) numOfSoldDiffList.removeFirst();
        var avgCostDiff = calculateDiff(avgCostList, LAST_DIFF);
        avgCostDiffList.add(avgCostDiff);
        if (avgCostDiffList.size() > 300) avgCostDiffList.removeFirst();

        var numOfSearchesDiffMax = getMax(numOfSearchesDiffList, LAST_MAX);

        var numOfREsDiffMax = getMax(numOfREsDiffList, LAST_MAX);

        var numOfSoldDiffMax = getMax(numOfSoldDiffList, LAST_MAX);

        var avgCostDiffMax = getMax(avgCostDiffList, LAST_MAX);

        Log.info("numOfSearchesDiff " + String.valueOf(numOfSearchesDiff.getDifference()));
        Log.info("numOfREsDiff " + String.valueOf(numOfREsDiff.getDifference()));
        Log.info("numOfSoldDiff " + String.valueOf(numOfSoldDiff.getDifference()));
        Log.info("avgCostDiff " + String.valueOf(avgCostDiff.getDifference()));
        Log.info("numOfSearchesDiffMax " + String.valueOf(numOfSearchesDiffMax.getDifference()));
        Log.info("numOfREsDiffMax " + String.valueOf(numOfREsDiffMax.getDifference()));
        Log.info("numOfSoldDiffMax " + String.valueOf(numOfSoldDiffMax.getDifference()));
        Log.info("avgCostDiffMax " + String.valueOf(avgCostDiffMax.getDifference()));

        var theBest = getMax(
            new LinkedList<>(List.of(numOfSearchesDiffMax, numOfREsDiffMax, numOfSoldDiffMax, avgCostDiffMax)),
            LAST_MAX);
        Log.info(List.of(numOfSearchesDiffMax, numOfREsDiffMax, numOfSoldDiffMax, avgCostDiffMax));
        Log.info(theBest);

        StatsSignal signal;

        if (theBest == numOfSearchesDiffMax) {
            signal = new StatsSignal(
                " broj pretraga ",
                numOfSearchesDiffMax.isGrowing(),
                numOfSearchesDiffMax.getDifference(),
                numOfSearchesList,
                getMaxDouble(numOfSearchesList, LAST_MAX),
                getMinDouble(numOfSearchesList, LAST_MAX)
            );
        }
        else if (theBest == numOfREsDiffMax) {
            signal = new StatsSignal(
                " broj izlistanih nekretnina ",
                numOfREsDiffMax.isGrowing(),
                numOfREsDiffMax.getDifference(),
                numOfREsList,
                getMaxDouble(numOfREsList, LAST_MAX),
                getMinDouble(numOfREsList, LAST_MAX)
            );
        }
        else if (theBest == numOfSoldDiffMax) {
            signal = new StatsSignal(
                " broj prodanih nekretnina ",
                numOfSoldDiffMax.isGrowing(),
                numOfSoldDiffMax.getDifference(),
                numOfSoldList,
                getMaxDouble(numOfSoldList, LAST_MAX),
                getMinDouble(numOfSoldList, LAST_MAX)
            );
        }
        else {
            signal = new StatsSignal(
                " prosječna cijena nekretnine ",
                avgCostDiffMax.isGrowing(),
                avgCostDiffMax.getDifference(),
                avgCostList,
                getMaxDouble(avgCostList, LAST_MAX),
                getMinDouble(avgCostList, LAST_MAX)
            );
        }
        sendBroadcast(Armando.class, signal);
    }
}
